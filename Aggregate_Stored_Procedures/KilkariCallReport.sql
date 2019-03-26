CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariCallReport`()
BEGIN
DECLARE dt DATETIME;
DECLARE curdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 35);

SET curdate = (select DATE_ADD(curdate(), INTERVAL 1 DAY));

WHILE dt < curdate DO

TRUNCATE table agg_kilkari_call_report_day_wise_temp;

insert into agg_kilkari_call_report_day_wise_temp(state_id, district_id, healthBlock_id, healthSubFacility_id, date, content_75_100, content_50_75, content_25_50, content_1_25, calls_attempted, successful_calls, billable_minutes, calls_to_inbox, total_beneficiaries)
select a.state_id, a.district_id, a.healthBlock_id, a.healthSubFacility_id, a.date, SUM(a.moreThan75), SUM(a.between50to75), SUM(a.between25to50), SUM(a.lessThan25), SUM(a.calls), SUM(a.successful_calls), SUM(a.billable_minutes), SUM(a.calls_to_inbox), SUM(a.total_beneficiaries) from 

(select 
bt.state_id, bt.district_id, bt.healthBlock_id, bt.healthSubFacility_id, dt as date,
sum(case when ((case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 >= 75) then 1 else 0 end) as moreThan75,
sum(case when ((case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 < 75 and (case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 >= 50) then 1 else 0 end) as between50to75,
sum(case when ((case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 < 50 and (case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 >= 25) then 1 else 0 end) as between25to50,
sum(case when ((case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END)*100 < 25) then 1 else 0 end) as lessThan25,
count(case when bm.call_source = 'OBD' THEN bm.id END) as 'calls',	
sum(case when bm.call_status = 'SUCCESS' and bm.call_source = 'OBD' THEN 1 else 0 END) as 'successful_calls',
ROUND(SUM(case when bm.call_status = 'SUCCESS' and bm.call_source = 'OBD' THEN bm.call_duration END)/60,2) as 'billable_minutes',
sum(case when bm.call_source = 'INBOX' THEN 1 else 0 END) as 'calls_to_inbox',
0 as total_beneficiaries
from beneficiary_call_measure bm left join beneficiary_tracker bt on bt.id = bm.beneficiaryTracking_id LEFT JOIN message_durations md on md.id= bm.campaign_id where bm.modificationDate >= DATE_ADD(dt, INTERVAL -1 DAY) and bm.modificationDate < dt group by bt.state_id, bt.district_id, bt.healthBlock_id, bt.healthSubFacility_id

UNION ALL
select state_id, district_id, healthBlock_id, healthSubFacility_id, dt as date, 0 as moreThan75, 0 as between50to75, 0 as between25to50, 0 as lessThan25, 0 as calls, 0 as successful_calls, 0 as billable_minutes, 0 as calls_to_inbox, count(*) as total_beneficiaries from beneficiary_tracker where creationdate >= DATE_ADD(dt, INTERVAL -1 DAY) and creationdate < dt group by state_id, district_id, healthBlock_id, healthSubFacility_id) as a group by a.state_id, a.district_id, a.healthBlock_id, a.healthSubFacility_id;


TRUNCATE table agg_kilkari_call_report_day_wise;

insert into agg_kilkari_call_report_day_wise(location_type, location_id, date, content_75_100, content_50_75, content_25_50, content_1_25, calls_attempted, successful_calls, billable_minutes, calls_to_inbox, total_beneficiaries)
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
