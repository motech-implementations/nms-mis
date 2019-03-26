CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariCallReport_onetime`()
BEGIN
DECLARE dt DATETIME;
DECLARE i INT;
SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 35);
SET i = 1;
WHILE i<2 DO
SET i=i+1;

TRUNCATE table agg_kilkari_call_report_day_wise_temp;

insert ignore into agg_kilkari_call_report_day_wise_temp(state_id, district_id, healthBlock_id, healthSubFacility_id, date, total_beneficiaries)
select y.state_id, y.district_id, y.healthBlock_id, y.healthSubFacility_id, y.date, y.total_beneficiaries from 

(select 
bt1.state_id, bt1.district_id, bt1.healthBlock_id, bt1.healthSubFacility_id,
dt as date,
count(*) as total_beneficiaries from beneficiary_tracker bt1 where bt1.creationdate < dt group by bt1.state_id, bt1.district_id, bt1.healthBlock_id, bt1.healthSubFacility_id) as y;

TRUNCATE table agg_kilkari_call_report_day_wise;

insert ignore into agg_kilkari_call_report_day_wise(location_type, location_id, date, content_75_100, content_50_75, content_25_50, content_1_25, calls_attempted, successful_calls, billable_minutes, calls_to_inbox, total_beneficiaries)
select 'STATE' as location_type, state_id, date as date, SUM(content_75_100), SUM(content_50_75), SUM(content_25_50), SUM(content_1_25), SUM(calls_attempted), SUM(successful_calls), SUM(billable_minutes), SUM(calls_to_inbox) , SUM(total_beneficiaries) from agg_kilkari_call_report_day_wise_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id, date as date, SUM(content_75_100), SUM(content_50_75), SUM(content_25_50), SUM(content_1_25), SUM(calls_attempted), SUM(successful_calls), SUM(billable_minutes), SUM(calls_to_inbox), SUM(total_beneficiaries) from agg_kilkari_call_report_day_wise_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, healthBlock_id, date as date, SUM(content_75_100), SUM(content_50_75), SUM(content_25_50), SUM(content_1_25), SUM(calls_attempted), SUM(successful_calls), SUM(billable_minutes), SUM(calls_to_inbox), SUM(total_beneficiaries) from agg_kilkari_call_report_day_wise_temp group by healthBlock_id

UNION ALL
select 'SUBCENTRE' as location_type, healthSubFacility_id, date as date, SUM(content_75_100), SUM(content_50_75), SUM(content_25_50), SUM(content_1_25), SUM(calls_attempted), SUM(successful_calls), SUM(billable_minutes), SUM(calls_to_inbox), SUM(total_beneficiaries) from agg_kilkari_call_report_day_wise_temp group by healthSubFacility_id;


insert into agg_kilkari_call_report(location_type, location_id, date, content_75_100, content_50_75, content_25_50, content_1_25,calls_attempted,successful_calls,billable_minutes,calls_to_inbox, total_beneficiaries)

select gkc.location_type, gkc.location_id, dt, sum(gkc.content_75_100), sum(gkc.content_50_75), sum(gkc.content_25_50), sum(gkc.content_1_25), sum(gkc.calls_attempted), sum(gkc.successful_calls), sum(gkc.billable_minutes), sum(gkc.calls_to_inbox) , sum(gkc.total_beneficiaries) from (
select kcd.location_type, kcd.location_id, kcd.date, kcd.content_75_100, kcd.content_50_75, kcd.content_25_50, kcd.content_1_25,kcd.calls_attempted,kcd.successful_calls,kcd.billable_minutes,kcd.calls_to_inbox, kcd.total_beneficiaries
from agg_kilkari_call_report_day_wise kcd

UNION ALL
select kc.location_type, kc.location_id, kc.date, kc.content_75_100, kc.content_50_75, kc.content_25_50, kc.content_1_25,kc.calls_attempted,kc.successful_calls,kc.billable_minutes,kc.calls_to_inbox, kc.total_beneficiaries
from agg_kilkari_call_report kc where kc.date = DATE_ADD(dt, INTERVAL -1 DAY)) gkc 
group by gkc.location_type, gkc.location_id;

SET dt = DATE_ADD(dt, INTERVAL 1 DAY);

update ETL_info_table set last_etl_time = dt where table_id = 35;

END WHILE;

END
