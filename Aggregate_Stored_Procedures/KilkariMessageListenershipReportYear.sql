CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariMessageListenershipReportYear`()
BEGIN
DECLARE dt DATETIME;
DECLARE currdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 100);
set currdate = (select concat(year(CURDATE()), '-01-01 00:00:00'));

WHILE dt < currdate DO

truncate table agg_kilkari_message_listenership_year_temp;

insert into agg_kilkari_message_listenership_year_temp (state_id, district_id, block_id, subcentre_id, date, answered_atleast_one_call, answered_more_than_75_per, answered_50_to_75_per, answered_25_to_50_per, answered_1_to_25_per, answered_no_calls, total_beneficiaries_called) select b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id, dt as date, COUNT(case when a.ansCalls >0 THEN a.beneficiaryTracking_id END) as 'AnsatleastOnce', COUNT(case when a.callsPercentage >=75 THEN a.beneficiaryTracking_id END) as 'morethan75', COUNT(case when a.callsPercentage >=50 and a.callsPercentage <75 THEN a.beneficiaryTracking_id END) as '50and75', COUNT(case when a.callsPercentage >=25 and a.callsPercentage < 50 THEN a.beneficiaryTracking_id END) as '25and50', COUNT(case when a.callsPercentage > 0 and a.callsPercentage < 25 THEN a.beneficiaryTracking_id END) as 'lessthan25', COUNT(case when a.ansCalls =0 THEN a.beneficiaryTracking_id END) as 'notAnsEvenOnce', COUNT(distinct a.beneficiaryTracking_id) as 'TotalBeneficiariesCalled' from (select distinct beneficiaryTracking_id, count(case when call_status='SUCCESS' THEN campaign_id END) as 'ansCalls', ROUND(COUNT(case when call_status='SUCCESS' THEN campaign_id END)/(COUNT(case when call_status='SUCCESS' THEN campaign_id END) + (COUNT(distinct campaign_id) - COUNT(distinct case when call_status='SUCCESS' THEN campaign_id END)))*100, 2) as 'callsPercentage' from beneficiary_call_measure where call_source='OBD' and modificationdate between dt and DATE_ADD(dt, INTERVAL 1 YEAR) and beneficiaryTracking_id is not null group by beneficiaryTracking_id) as a LEFT JOIN beneficiary_tracker b on a.beneficiaryTracking_id=b.id group by b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id;

insert into agg_kilkari_message_listenership(location_type, location_id, date, answered_atleast_one_call, answered_more_than_75_per, answered_50_to_75_per, answered_25_to_50_per, answered_1_to_25_per, answered_no_calls, total_beneficiaries_called,period_type) select 'STATE' as location_type, state_id as location_id, date as date, SUM(answered_atleast_one_call), SUM(answered_more_than_75_per), SUM(answered_50_to_75_per), SUM(answered_25_to_50_per), SUM(answered_1_to_25_per), SUM(answered_no_calls), SUM(total_beneficiaries_called),'YEAR' as period_type from agg_kilkari_message_listenership_year_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id as location_id, date as date, SUM(answered_atleast_one_call), SUM(answered_more_than_75_per), SUM(answered_50_to_75_per), SUM(answered_25_to_50_per), SUM(answered_1_to_25_per), SUM(answered_no_calls), SUM(total_beneficiaries_called),'YEAR' as period_type from agg_kilkari_message_listenership_year_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, block_id as location_id, date as date, SUM(answered_atleast_one_call), SUM(answered_more_than_75_per), SUM(answered_50_to_75_per), SUM(answered_25_to_50_per), SUM(answered_1_to_25_per), SUM(answered_no_calls), SUM(total_beneficiaries_called),'YEAR' as period_type from agg_kilkari_message_listenership_year_temp group by block_id

UNION ALL
select 'SUBCENTRE' as location_type, subcentre_id as location_id, date as date, SUM(answered_atleast_one_call), SUM(answered_more_than_75_per), SUM(answered_50_to_75_per), SUM(answered_25_to_50_per), SUM(answered_1_to_25_per), SUM(answered_no_calls), SUM(total_beneficiaries_called),'YEAR' as period_type from agg_kilkari_message_listenership_year_temp group by subcentre_id;


SET dt = DATE_ADD(dt, INTERVAL 1 YEAR);
update ETL_info_table set last_etl_time = dt where table_id = 100;

END WHILE;

END
