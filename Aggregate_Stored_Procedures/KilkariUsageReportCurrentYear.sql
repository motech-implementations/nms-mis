CREATE DEFINER=`etluser`@`%` PROCEDURE `KilkariUsageReportCurrentYear`()
BEGIN
DECLARE dt DATETIME;
DECLARE i INT;
DECLARE currdate DATETIME;

SET dt = (select cast(last_etl_time as datetime) from ETL_info_table where table_id = 120);

set currdate = (select concat(year(CURDATE()), '-', month(CURDATE()), '-', '01 00:00:00'));

SET i = 1;
WHILE i<2 DO
SET i=i+1;

DELETE from agg_kilkari_usage where period_type = 'CURRENT YEAR';

truncate table agg_kilkari_usage_year_temp;

insert into agg_kilkari_usage_year_temp (state_id, district_id, block_id, subcentre_id, date,  content_listened_more_than_75_per, content_listened_50_to_75_per, content_listened_25_to_50_per, content_listened_1_to_25_per , called_inbox) 

select b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id, dt as date, 
COUNT(case when a.contentPercentage >=75 THEN a.beneficiaryTracking_id END) as 'morethan75', 
COUNT(case when a.contentPercentage >=50 and a.contentPercentage <75 THEN a.beneficiaryTracking_id END) as '50and75', 
COUNT(case when a.contentPercentage >=25 and a.contentPercentage < 50 THEN a.beneficiaryTracking_id END) as '25and50', 
COUNT(case when a.contentPercentage < 25 THEN a.beneficiaryTracking_id END) as 'lessthan25',
COUNT(DISTINCT case when a.calledInbox >0 THEN a.beneficiaryTracking_id END) as 'calledInbox'
from 
(select distinct bm.beneficiaryTracking_id, 
SUM(case when bm.call_source='INBOX' THEN 1 else 0 END) as 'calledInbox', 
(SUM(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN bm.msg_duration END)/SUM(case when bm.call_source='OBD' and bm.call_status='SUCCESS' THEN md.message_duration END))*100 as 'contentPercentage' 
from beneficiary_call_measure as bm  LEFT JOIN message_durations md on bm.campaign_id = md.id  where bm.modificationdate >= dt and bm.modificationdate < currdate and bm.beneficiaryTracking_id is not null group by bm.beneficiaryTracking_id) as a LEFT JOIN beneficiary_tracker b on a.beneficiaryTracking_id=b.id group by b.state_id, b.district_id, b.healthBlock_id, b.healthSubFacility_id;

insert into agg_kilkari_usage(location_type, location_id, date, content_listened_more_than_75_per, content_listened_50_to_75_per, content_listened_25_to_50_per, content_listened_1_to_25_per , called_inbox, period_type)

select 'STATE' as location_type, state_id as location_id, date as date, SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , SUM(called_inbox),'CURRENT YEAR' as period_type from agg_kilkari_usage_year_temp group by state_id

UNION ALL
select 'DISTRICT' as location_type, district_id as location_id, date as date, SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , SUM(called_inbox),'CURRENT YEAR' as period_type from agg_kilkari_usage_year_temp group by district_id

UNION ALL
select 'BLOCK' as location_type, block_id as location_id, date as date, SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , SUM(called_inbox),'CURRENT YEAR' as period_type from agg_kilkari_usage_year_temp group by block_id

UNION ALL
select 'SUBCENTRE' as location_type, subcentre_id as location_id, date as date, SUM(content_listened_more_than_75_per), SUM(content_listened_50_to_75_per), SUM(content_listened_25_to_50_per), SUM(content_listened_1_to_25_per) , SUM(called_inbox),'CURRENT YEAR' as period_type from agg_kilkari_usage_year_temp group by subcentre_id;

update ETL_info_table set last_etl_time = currdate where table_id = 117;

END WHILE;

END
