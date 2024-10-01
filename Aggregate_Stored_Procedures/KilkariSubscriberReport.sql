CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariSubscriberReport`()
BEGIN
DECLARE dt DATETIME;
DECLARE curdate DATETIME;

DECLARE i INT;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 36);

SET curdate = (select DATE_ADD(curdate(), INTERVAL 1 DAY));

WHILE dt < curdate DO

TRUNCATE table agg_kilkari_subscriber_day_wise_temp;

INSERT INTO agg_kilkari_subscriber_day_wise_temp (state_id, district_id, healthBlock_id, subcentre_id, date, total_records_received_MCTS_RCH, eligible_for_subscriptions, total_subscriptions_accepted, total_subscriptions_completed, total_subscriptions)

select a.state_id, a.district_id, a.healthBlock_id, a.subcentre_id, a.date as date, SUM(subscriptions_received) as subscriptions_received, SUM(eligible_for_subscriptions) as eligible_for_subscriptions, SUM(subscriptions_accepted) as subscriptions_accepted, SUM(subscriptions_completed) as subscriptions_completed, SUM(total_subscriptions) as total_subscriptions from

(select b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id as subcentre_id, dt as date, COUNT(case when a.creationdate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.creationdate < dt THEN a.subscription_id END) as 'Subscriptions_Received', 0 as eligible_for_subscriptions, COUNT(case when a.creationdate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.creationdate < dt THEN a.subscription_id END) as 'subscriptions_accepted', COUNT(case when a.subscription_status='COMPLETED' and a.enddate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.enddate < dt THEN a.subscription_id END) as 'subscriptions_completed', COUNT(distinct case when a.creationdate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.creationdate < dt THEN a.subscription_id END) as 'total_subscriptions' from subscriptions a LEFT JOIN beneficiary_tracker b on a.beneficiaryTracking_id=b.id where ((a.creationdate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.creationdate < dt) or (a.enddate >= DATE_ADD(dt, INTERVAL -1 DAY) and a.enddate < dt)) group by b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id

UNION ALL
select state_id, district_id, health_block_id as healthBlock_id, subcentre_id, dt as date, count(*) as subscriptions_received, COUNT(case when rejection_reason != 'INVALID_LMP_DATE' THEN id END) as 'eligible_for_subscriptions', 0 as subscriptions_accepted, 0 as subscriptions_completed, 0 as total_subscriptions from mother_import_rejection where creation_date >= DATE_ADD(dt, INTERVAL -1 DAY) and creation_date < dt  and accepted = 0 group by state_id, district_id, health_block_id, subcentre_id

UNION ALL
select state_id, district_id, health_block_id as healthBlock_id, subcentre_id, dt as date, count(*) as subscriptions_received, COUNT(case when rejection_reason != 'INVALID_DOB' THEN id END) as 'eligible_for_subscriptions', 0 as subscriptions_accepted, 0 as subscriptions_completed, 0 as total_subscriptions from child_import_rejection where creation_date >= DATE_ADD(dt, INTERVAL -1 DAY) and creation_date < dt and accepted = 0 group by state_id, district_id, health_block_id, subcentre_id) as a group by a.state_id, a.district_id, a.healthBlock_id, a.subcentre_id;

truncate table agg_kilkari_subscriber_day_wise;

INSERT INTO agg_kilkari_subscriber_day_wise (location_type, location_id, date, total_records_received_MCTS_RCH, eligible_for_subscriptions, total_subscriptions_accepted, total_subscriptions_completed, total_subscriptions)

select 'STATE' as location_type, state_id, dt, SUM(total_records_received_MCTS_RCH), SUM(eligible_for_subscriptions), SUM(total_subscriptions_accepted), SUM(total_subscriptions_completed), SUM(total_subscriptions) from agg_kilkari_subscriber_day_wise_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id, date, SUM(total_records_received_MCTS_RCH), SUM(eligible_for_subscriptions), SUM(total_subscriptions_accepted), SUM(total_subscriptions_completed), SUM(total_subscriptions) from agg_kilkari_subscriber_day_wise_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, healthBlock_id, date, SUM(total_records_received_MCTS_RCH), SUM(eligible_for_subscriptions), SUM(total_subscriptions_accepted), SUM(total_subscriptions_completed), SUM(total_subscriptions) from agg_kilkari_subscriber_day_wise_temp group by healthBlock_id

UNION ALL
select 'SUBCENTRE' as location_type, subcentre_id, date, SUM(total_records_received_MCTS_RCH), SUM(eligible_for_subscriptions), SUM(total_subscriptions_accepted), SUM(total_subscriptions_completed), SUM(total_subscriptions) from agg_kilkari_subscriber_day_wise_temp group by subcentre_id;


INSERT INTO agg_kilkari_subscriber (location_type, location_id, date, total_records_received_MCTS_RCH, eligible_for_subscriptions, total_subscriptions_accepted, total_subscriptions_completed, total_subscriptions)

select a.location_type, a.location_id, dt, SUM(total_records_received_MCTS_RCH), SUM(eligible_for_subscriptions), SUM(total_subscriptions_accepted), SUM(total_subscriptions_completed), SUM(total_subscriptions) from

(select location_type, location_id, date, total_records_received_MCTS_RCH, eligible_for_subscriptions, total_subscriptions_accepted, total_subscriptions_completed, total_subscriptions from agg_kilkari_subscriber_day_wise

UNION ALL
select location_type, location_id, date, total_records_received_MCTS_RCH, eligible_for_subscriptions, total_subscriptions_accepted, total_subscriptions_completed, total_subscriptions from agg_kilkari_subscriber where date = DATE_ADD(dt, INTERVAL -1 DAY)) as a group by a.location_type, a.location_id;


SET dt = DATE_ADD(dt, INTERVAL 1 DAY);

update ETL_info_table set last_etl_time = dt where table_id = 36;

END WHILE;

END
