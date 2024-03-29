CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariThematicContentWeek`()
BEGIN
DECLARE dt DATETIME;
DECLARE curdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 125);

SET curdate = (SELECT DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY));

WHILE dt < curdate DO

TRUNCATE TABLE agg_kilkari_thematic_content_temp_week;
insert into agg_kilkari_thematic_content_temp_week(state_id, district_id, healthBlock_id, date,message_week_number,unique_beneficiaries_called, calls_answered, minutes_consumed)

select 
bt.state_id, bt.district_id, bt.healthBlock_id,
dt as date,
substring_index(bm.contentFile,'_',1) as weekId,
COUNT(DISTINCT bm.beneficiaryTracking_id) as unique_beneficiaries_called,
COUNT(case when bm.call_status = 'SUCCESS' then bm.id end) as calls_answered,
ROUND(SUM(case when bm.call_status = 'SUCCESS' then bm.msg_duration end)/60,2) as minutes_consumed FROM beneficiary_call_measure bm LEFT JOIN beneficiary_tracker bt on bt.id = bm.beneficiaryTracking_id LEFT JOIN dim_theme th on th.message_id = substring_index(bm.contentFile, '.',1)
where bm.modificationDate between dt and DATE_ADD(dt, INTERVAL 1 WEEK) and bm.call_source = 'OBD' group by substring_index(bm.contentFile, '_',1), bt.state_id, bt.district_id, bt.healthBlock_id;

insert into agg_kilkari_thematic_content(location_type, location_id, date, message_week_number,unique_beneficiaries_called, calls_answered, minutes_consumed,period_type)

select 'NATIONAL' as location_type, 0, date,message_week_number, sum(unique_beneficiaries_called), sum(calls_answered), sum(minutes_consumed), 'WEEK' as period_type from agg_kilkari_thematic_content_temp_week group by message_week_number

UNION ALL
select 'STATE' as location_type, state_id, date,message_week_number, sum(unique_beneficiaries_called), sum(calls_answered), sum(minutes_consumed), 'WEEK' as period_type from agg_kilkari_thematic_content_temp_week group by state_id, message_week_number

UNION ALL
select 'DISTRICT' as location_type, district_id, date,message_week_number, sum(unique_beneficiaries_called), sum(calls_answered), sum(minutes_consumed), 'WEEK' as period_type from agg_kilkari_thematic_content_temp_week group by district_id, message_week_number

UNION ALL
select 'BLOCK' as location_type, healthBlock_id, date,message_week_number, sum(unique_beneficiaries_called), sum(calls_answered), sum(minutes_consumed), 'WEEK' as period_type from agg_kilkari_thematic_content_temp_week group by healthBlock_id, message_week_number;

SET dt = DATE_ADD(dt, INTERVAL 1 WEEK);

update ETL_info_table set last_etl_time = dt where table_id = 125;

END WHILE;

END
