CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariRepeatListenerMonthWise`()
BEGIN
DECLARE dt DATETIME;
DECLARE currentMonth DATETIME;

SET dt = (select cast(tb.last_etl_time as datetime) from ETL_info_table tb where tb.table_id = 61);

SET currentMonth = (select cast(CONCAT(YEAR(CURDATE()), '-',  MONTH(CURDATE()), '-01 00:00:00') as datetime));

WHILE dt < currentMonth DO

TRUNCATE table agg_kilkari_repeat_listener_month_wise_temp;

insert into agg_kilkari_repeat_listener_month_wise_temp(state_id, district_id, healthBlock_id, date, 5_calls_answered, 4_calls_answered, 3_calls_answered, 2_calls_answered, 1_calls_answered, 0_calls_answered, greater_than_5)

select 
a.state_id, a.district_id, a.healthBlock_id, dt as date, 
SUM(CASE when a.cnt=5 then 1 else 0 end) as '5_calls',
SUM(CASE when a.cnt=4 then 1 else 0 end) as '4_calls',
SUM(CASE when a.cnt=3 then 1 else 0 end) as '3_calls',
SUM(CASE when a.cnt=2 then 1 else 0 end) as '2_calls',
SUM(CASE when a.cnt=1 then 1 else 0 end) as '1_call',
SUM(CASE when a.cnt=0 then 1 else 0 end) as '0_call',
SUM(CASE when a.cnt > 5 then 1 else 0 end) as 'morethan_5_calls' from 
(select distinct a.beneficiaryTracking_id, count(case when a.call_status = 'SUCCESS' THEN a.id END) as cnt, b.state_id, b.district_id, b.healthBlock_id from beneficiary_call_measure a LEFT JOIN beneficiary_tracker b on b.id=a.beneficiaryTracking_id where a.call_source = 'OBD' and a.modificationdate >= dt and a.modificationdate < DATE_ADD(dt, INTERVAL 1 MONTH) and a.beneficiaryTracking_id is not null group by a.beneficiaryTracking_id) as a 
group by a.state_id, a.district_id, a.healthBlock_id;


INSERT INTO agg_kilkari_repeat_listener_month_wise ( `location_type`, `location_id`, `date`, `5_calls_answered`, `4_calls_answered`, `3_calls_answered`, `2_calls_answered`, `1_calls_answered`, `0_calls_answered`, `greater_than_5`)

select 'NATIONAL' as 'location_type', 0 as location_id, date, SUM(5_calls_answered), SUM(4_calls_answered), SUM(3_calls_answered), SUM(2_calls_answered), SUM(1_calls_answered), SUM(0_calls_answered),SUM(greater_than_5) from agg_kilkari_repeat_listener_month_wise_temp

UNION ALL
select 'STATE' as 'location_type', state_id, date, SUM(5_calls_answered), SUM(4_calls_answered), SUM(3_calls_answered), SUM(2_calls_answered), SUM(1_calls_answered), SUM(0_calls_answered),SUM(greater_than_5) from agg_kilkari_repeat_listener_month_wise_temp group by state_id

UNION ALL
select 'DISTRICT' as 'location_type', district_id, date, SUM(5_calls_answered), SUM(4_calls_answered), SUM(3_calls_answered), SUM(2_calls_answered), SUM(1_calls_answered), SUM(0_calls_answered),SUM(greater_than_5) from agg_kilkari_repeat_listener_month_wise_temp group by district_id

UNION ALL
select 'BLOCK' as 'location_type', healthBlock_id, date, SUM(5_calls_answered), SUM(4_calls_answered), SUM(3_calls_answered), SUM(2_calls_answered), SUM(1_calls_answered), SUM(0_calls_answered),SUM(greater_than_5) from agg_kilkari_repeat_listener_month_wise_temp group by healthBlock_id;

SET dt = DATE_ADD(dt, INTERVAL 1 MONTH);

update ETL_info_table set last_etl_time = dt where table_id = 61;
 
END WHILE;
END
