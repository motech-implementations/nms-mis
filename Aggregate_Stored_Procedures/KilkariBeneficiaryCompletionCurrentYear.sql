CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariBeneficiaryCompletionCurrentYear`()
BEGIN
DECLARE dt DATETIME;
DECLARE i INT;
DECLARE currdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 80);

set currdate = (select concat(year(CURDATE()), '-', month(CURDATE()), '-', '01 00:00:00'));

SET i = 1;
WHILE i< 2 DO
SET i=i+1;

DELETE from agg_kilkari_beneficiary_completion where period_type = 'CURRENT YEAR';

truncate table agg_kilkari_beneficiary_completion_temp;

insert into agg_kilkari_beneficiary_completion_temp (state_id, district_id, block_id, subcentre_id, date, total_beneficiaries_completed, content_listened_more_than_75_per, content_listened_50_to_75_per, content_listened_25_to_50_per, content_listened_1_to_25_per , total_age) 

select b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id, dt as date, 
count(distinct b.id) as total_beneficiaries_completed,
COUNT(case when a.content_listened >=75 THEN a.beneficiaryTracking_id END) as 'morethan75', 
COUNT(case when a.content_listened >=50 and a.content_listened <75 THEN a.beneficiaryTracking_id END) as '50and75', 
COUNT(case when a.content_listened >=25 and a.content_listened < 50 THEN a.beneficiaryTracking_id END) as '25and50', 
COUNT(case when a.content_listened < 25 THEN a.beneficiaryTracking_id END) as 'lessthan25',
ROUND(SUM(a.ageOnService)/count(b.id), 2) as 'total_age'
from 
(select distinct beneficiaryTracking_id,
SUM(ageOnService ) as ageOnService,
case when SUM(answeredCalls) >=1 THEN SUM(listenedContentDuration)/SUM(totalContentDuration)*100 END as content_listened
from subscriptions 
where subscription_status='COMPLETED' 
and enddate >=  dt and enddate < currdate
group by beneficiaryTracking_id) as a
JOIN beneficiary_tracker b on b.id = a.beneficiaryTracking_id
group by b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id;


insert into agg_kilkari_beneficiary_completion (location_type, location_id, date, total_beneficiaries_completed, content_listened_more_than_75_per, content_listened_50_to_75_per, content_listened_25_to_50_per, content_listened_1_to_25_per , total_age, period_type)

select 'STATE' as location_type, state_id as location_id, date as date, SUM(total_beneficiaries_completed), SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , ROUND(SUM(total_age)/count(*), 2), 'CURRENT YEAR' as period_type from agg_kilkari_beneficiary_completion_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id as location_id, date as date, SUM(total_beneficiaries_completed), SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , ROUND(SUM(total_age)/count(*), 2), 'CURRENT YEAR' as period_type from agg_kilkari_beneficiary_completion_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, block_id as location_id, date as date, SUM(total_beneficiaries_completed), SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , ROUND(SUM(total_age)/count(*), 2), 'CURRENT YEAR' as period_type from agg_kilkari_beneficiary_completion_temp group by block_id

UNION ALL
select 'SUBCENTRE' as location_type, subcentre_id as location_id, date as date, SUM(total_beneficiaries_completed), SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , ROUND(SUM(total_age)/count(*), 2), 'CURRENT YEAR' as period_type from agg_kilkari_beneficiary_completion_temp group by subcentre_id;

update ETL_info_table set last_etl_time = currdate where table_id = 77;

END WHILE;

END
