CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariListeningMatrixReportWeek`()
BEGIN
DECLARE dt DATETIME;
DECLARE curdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 85);

SET curdate = (SELECT DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY));

WHILE dt < curdate DO

truncate table agg_kilkari_listening_matrix_temp_week;

insert into agg_kilkari_listening_matrix_temp_week (state_id, district_id, healthBlock_id, date, calls_listened_percentage, contentListened_morethan75, contentListened_50_75, contentListened_25_50, contentListened_lessthan25)

select b.state_id, b.district_id, b.healthBlock_id, dt as date, 'callsListened_morethan75' as calls_listened_percentage, COUNT(distinct case when a.percentageListened >= 75 THEN a.beneficiaryTracking_id END) as morethan75, COUNT(distinct case when (a.percentageListened >= 50 and a.percentageListened <75) THEN a.beneficiaryTracking_id END) as 50and75, COUNT(distinct case when (a.percentageListened >=25 and a.percentageListened <50) THEN a.beneficiaryTracking_id END) as 25and50, COUNT(distinct case when a.percentageListened < 25 THEN a.beneficiaryTracking_id END) as lessthan25 from (select DISTINCT a.beneficiaryTracking_id, ROUND((SUM(case when a.call_status='SUCCESS' THEN a.msg_duration END)/SUM(case when a.call_status='SUCCESS' THEN b.message_duration END))*100, 2) as percentageListened from beneficiary_call_measure a LEFT JOIN message_durations b on a.campaign_id=b.id where a.call_source='OBD' and a.modificationdate between dt and DATE_ADD(dt, INTERVAL 1 WEEK) and a.beneficiaryTracking_id is not null group by a.beneficiaryTracking_id having ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) >=75 ) as a LEFT JOIN beneficiary_tracker b on b.id=a.beneficiaryTracking_id group by b.state_id, b.district_id, b.healthBlock_id

UNION ALL
select b.state_id, b.district_id, b.healthBlock_id, dt as date, 'callsListened_50_75' as calls_listened_percentage, COUNT(distinct case when a.percentageListened >= 75 THEN a.beneficiaryTracking_id END) as morethan75, COUNT(distinct case when (a.percentageListened >= 50 and a.percentageListened <75) THEN a.beneficiaryTracking_id END) as 50and75, COUNT(distinct case when (a.percentageListened >=25 and a.percentageListened <50) THEN a.beneficiaryTracking_id END) as 25and50, COUNT(distinct case when a.percentageListened < 25 THEN a.beneficiaryTracking_id END) as lessthan25 from (select DISTINCT a.beneficiaryTracking_id, ROUND((SUM(case when a.call_status='SUCCESS' THEN a.msg_duration END)/SUM(case when a.call_status='SUCCESS' THEN b.message_duration END))*100, 2) as percentageListened from beneficiary_call_measure a LEFT JOIN message_durations b on a.campaign_id=b.id where a.call_source='OBD' and a.modificationdate between dt and DATE_ADD(dt, INTERVAL 1 WEEK) and a.beneficiaryTracking_id is not null group by a.beneficiaryTracking_id having (ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) >=50 and ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) < 75)) as a LEFT JOIN beneficiary_tracker b on b.id=a.beneficiaryTracking_id group by b.state_id, b.district_id, b.healthBlock_id

UNION ALL
select b.state_id, b.district_id, b.healthBlock_id, dt as date, 'callsListened_25_50' as calls_listened_percentage, COUNT(distinct case when a.percentageListened >= 75 THEN a.beneficiaryTracking_id END) as morethan75, COUNT(distinct case when (a.percentageListened >= 50 and a.percentageListened <75) THEN a.beneficiaryTracking_id END) as 50and75, COUNT(distinct case when (a.percentageListened >=25 and a.percentageListened <50) THEN a.beneficiaryTracking_id END) as 25and50, COUNT(distinct case when a.percentageListened < 25 THEN a.beneficiaryTracking_id END) as lessthan25 from (select DISTINCT a.beneficiaryTracking_id, ROUND((SUM(case when a.call_status='SUCCESS' THEN a.msg_duration END)/SUM(case when a.call_status='SUCCESS' THEN b.message_duration END))*100, 2) as percentageListened from beneficiary_call_measure a LEFT JOIN message_durations b on a.campaign_id=b.id where a.call_source='OBD' and a.modificationdate between dt and DATE_ADD(dt, INTERVAL 1 WEEK) and   a.beneficiaryTracking_id is not null group by a.beneficiaryTracking_id having (ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) >=25 and ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) < 50)) as a LEFT JOIN beneficiary_tracker b on b.id=a.beneficiaryTracking_id group by b.state_id, b.district_id, b.healthBlock_id

UNION ALL
select b.state_id, b.district_id, b.healthBlock_id, dt as date, 'callsListened_lessthan25' as calls_listened_percentage, COUNT(distinct case when a.percentageListened >= 75 THEN a.beneficiaryTracking_id END) as morethan75, COUNT(distinct case when (a.percentageListened >= 50 and a.percentageListened <75) THEN a.beneficiaryTracking_id END) as 50and75, COUNT(distinct case when (a.percentageListened >=25 and a.percentageListened <50) THEN a.beneficiaryTracking_id END) as 25and50, COUNT(distinct case when a.percentageListened < 25 THEN a.beneficiaryTracking_id END) as lessthan25 from (select DISTINCT a.beneficiaryTracking_id, ROUND((SUM(case when a.call_status='SUCCESS' THEN a.msg_duration END)/SUM(case when a.call_status='SUCCESS' THEN b.message_duration END))*100, 2) as percentageListened from beneficiary_call_measure a LEFT JOIN message_durations b on a.campaign_id=b.id where a.call_source='OBD' and a.modificationdate between dt and DATE_ADD(dt, INTERVAL 1 WEEK) and a.beneficiaryTracking_id is not null group by a.beneficiaryTracking_id having (ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) > 0 and ROUND(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END)/(COUNT(case when a.call_status='SUCCESS' THEN a.campaign_id END) + COUNT(distinct a.campaign_id) - COUNT(distinct case when a.call_status='SUCCESS' THEN a.campaign_id END))*100, 2) < 25)) as a LEFT JOIN beneficiary_tracker b on b.id=a.beneficiaryTracking_id group by b.state_id, b.district_id, b.healthBlock_id;



insert into agg_kilkari_listening_matrix(location_type, location_id, date, calls_listened_percentage, contentListened_morethan75, contentListened_50_75, contentListened_25_50, contentListened_lessthan25,period_type)
select 'NATIONAL' as location_type, '0' as location_id, date as date, calls_listened_percentage, SUM(contentListened_morethan75), SUM(contentListened_50_75), SUM(contentListened_25_50), SUM(contentListened_lessthan25), 'WEEK' as period_type from agg_kilkari_listening_matrix_temp_week group by calls_listened_percentage

UNION ALL
select 'STATE' as location_type, state_id as location_id, date as date, calls_listened_percentage, SUM(contentListened_morethan75), SUM(contentListened_50_75), SUM(contentListened_25_50), SUM(contentListened_lessthan25), 'WEEK' as period_type from agg_kilkari_listening_matrix_temp_week group by state_id, calls_listened_percentage

UNION ALL
select 'DISTRICT' as location_type, district_id as location_id, date as date, calls_listened_percentage, SUM(contentListened_morethan75), SUM(contentListened_50_75), SUM(contentListened_25_50), SUM(contentListened_lessthan25), 'WEEK' as period_type from agg_kilkari_listening_matrix_temp_week group by district_id, calls_listened_percentage

UNION ALL
select 'BLOCK' as location_type, healthBlock_id as location_id, date as date, calls_listened_percentage, SUM(contentListened_morethan75), SUM(contentListened_50_75), SUM(contentListened_25_50), SUM(contentListened_lessthan25), 'WEEK' as period_type from agg_kilkari_listening_matrix_temp_week group by healthBlock_id, calls_listened_percentage;

SET dt = DATE_ADD(dt, INTERVAL 1 WEEK);

update ETL_info_table set last_etl_time = dt where table_id = 85;

END WHILE;

END
