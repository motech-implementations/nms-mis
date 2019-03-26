CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariAggregateBeneficiariesWeek`()
BEGIN
DECLARE dt DATETIME;
DECLARE curdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 65);

SET curdate = (SELECT DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY));

WHILE dt < curdate DO

truncate table agg_aggregate_beneficiaries_temp;

insert into agg_aggregate_beneficiaries_temp(state_id, district_id, block_id, subcentre_id, date,self_deactivated, no_answer_deactivation, low_listener_deactivation, system_deactivation, mother_completed,child_completed,joined_subscription, subscriptions_rejected)

select state_id, district_id, healthBlock_id, healthSubFacility_id, dt as date, SUM(self_deactivated) as self_deactivated, SUM(no_answer_deactivation) as no_answer_deactivation, SUM(low_listener_deactivation) as low_listener_deactivation, SUM(system_deactivation) as system_deactivation, SUM(mother_completed) as mother_completed, SUM(child_completed) as child_completed, SUM(joined_subscription) as joined_subscription, SUM(subscriptions_rejected) as subscriptions_rejected from
(select b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id, dt as date,
count(distinct case when s.subscription_status = 'DEACTIVATED' and s.deactivation_reason = 'DEACTIVATED_BY_USER' and s.enddate >= dt and s.enddate < DATE_ADD(dt, INTERVAL 1 WEEK) then b.id end) as self_deactivated,
count(distinct case when s.subscription_status = 'DEACTIVATED' and s.deactivation_reason in ('MISCARRIAGE_OR_ABORTION', 'MCTS_UPDATE', 'LIVE_BIRTH', 'STILL_BIRTH', 'CHILD_DEATH', 'MATERNAL_DEATH') and s.enddate >= dt and s.enddate < DATE_ADD(dt, INTERVAL 1 WEEK) then b.id end) as system_deactivation,
count(distinct case when s.subscriptionPack_id = 1 and s.subscription_status = 'COMPLETED' and s.enddate >= dt and s.enddate < DATE_ADD(dt, INTERVAL 1 WEEK) then b.id end) as mother_completed,
count(distinct case when s.subscriptionPack_id = 2 and s.subscription_status = 'COMPLETED' and s.enddate >= dt and s.enddate < DATE_ADD(dt, INTERVAL 1 WEEK) then b.id end) as child_completed,
count(distinct case when b.creationdate >= dt and b.creationdate < DATE_ADD(dt, INTERVAL 1 WEEK) then b.id end) as joined_subscription,
'0' as 'no_answer_deactivation',
'0' as 'low_listener_deactivation',
'0' as subscriptions_rejected
from subscriptions s LEFT JOIN beneficiary_tracker b on b.id =s.beneficiaryTracking_id   
group by b.state_id,b.district_id, b.healthBlock_id, b.healthSubFacility_id

UNION ALL

select state_id, district_id, block_id as healthBlock_id, healthSubFacility_id, dt as date, 
'0' as 'self_deactivated', '0' as 'system_deactivation', '0' as 'mother_completed', '0' as 'child_completed', '0' as 'joined_subscription',
count(distinct case when deactivation_reason = 'WEEKLY_CALLS_NOT_ANSWERED' and deactivation_date >= dt and deactivation_date < DATE_ADD(dt, INTERVAL 1 WEEK) then beneficiaryTracking_id end) as no_answer_deactivation,
count(distinct case when deactivation_reason = 'LOW_LISTENERSHIP' and deactivation_date >= dt and deactivation_date < DATE_ADD(dt, INTERVAL 1 WEEK) then beneficiaryTracking_id end) as low_listener_deactivation, '0' as subscriptions_rejected
from kilkari_manual_deactivations 
group by state_id, district_id, block_id, healthSubFacility_id

UNION ALL

select state_id, district_id, health_block_id as healthBlock_id, subcentre_id, dt as date, '0' as 'self_deactivated', '0' as 'system_deactivation', '0' as 'mother_completed', '0' as 'child_completed', '0' as 'joined_subscription',
'0' as no_answer_deactivation,
'0' as low_listener_deactivation, COUNT(case when rejection_reason not in ('INVALID_LMP_DATE', 'ACTIVE_CHILD_PRESENT', 'ABORT_STILLBIRTH_DEATH', 'UPDATED_RECORD_ALREADY_EXISTS') THEN id END) as 'subscriptions_rejected' from mother_import_rejection where creation_date >= dt and creation_date < DATE_ADD(dt, INTERVAL 1 WEEK)  and accepted = 0 group by state_id, district_id, health_block_id, subcentre_id

UNION ALL

select state_id, district_id, health_block_id as healthBlock_id, subcentre_id, dt as date, '0' as 'self_deactivated', '0' as 'system_deactivation', '0' as 'mother_completed', '0' as 'child_completed', '0' as 'joined_subscription',
'0' as no_answer_deactivation,
'0' as low_listener_deactivation, COUNT(case when rejection_reason not in ('INVALID_DOB', 'UPDATED_RECORD_ALREADY_EXISTS') THEN id END) as 'subscriptions_rejected' from child_import_rejection where creation_date >= dt and creation_date < DATE_ADD(dt, INTERVAL 1 WEEK) and accepted = 0 group by state_id, district_id, health_block_id, subcentre_id) as a group by state_id, district_id, healthBlock_id, healthSubFacility_id;


insert into agg_aggregate_beneficiaries (location_type, location_id, date, self_deactivated, no_answer_deactivation, low_listener_deactivation, system_deactivation, mother_completed,child_completed,joined_subscription,subscriptions_rejected,period_type)

select 'STATE' as location_type, state_id as location_id, date as date, sum(self_deactivated), sum(no_answer_deactivation), sum(low_listener_deactivation), sum(system_deactivation), sum(mother_completed),sum(child_completed),sum(joined_subscription), SUM(subscriptions_rejected), 'WEEK' as period_type from agg_aggregate_beneficiaries_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id as location_id, date as date, sum(self_deactivated), sum(no_answer_deactivation), sum(low_listener_deactivation), sum(system_deactivation), sum(mother_completed),sum(child_completed),sum(joined_subscription), SUM(subscriptions_rejected), 'WEEK' as period_type from agg_aggregate_beneficiaries_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, block_id as location_id, date as date, sum(self_deactivated), sum(no_answer_deactivation), sum(low_listener_deactivation), sum(system_deactivation), sum(mother_completed),sum(child_completed),sum(joined_subscription), SUM(subscriptions_rejected), 'WEEK' as period_type from agg_aggregate_beneficiaries_temp group by block_id

UNION ALL
select 'SUBCENTRE' as location_type, subcentre_id as location_id, date as date, sum(self_deactivated), sum(no_answer_deactivation), sum(low_listener_deactivation), sum(system_deactivation), sum(mother_completed),sum(child_completed),sum(joined_subscription), SUM(subscriptions_rejected), 'WEEK' as period_type from agg_aggregate_beneficiaries_temp group by subcentre_id;

SET dt = DATE_ADD(dt, INTERVAL 1 WEEK);

update ETL_info_table set last_etl_time = dt where table_id = 65;

END WHILE;

END
