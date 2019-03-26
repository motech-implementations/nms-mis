CREATE DEFINER=`etluser`@`%` PROCEDURE `MA_Aggregate_Reports`()
BEGIN

DECLARE dt DATETIME;
DECLARE curdate DATETIME;

DECLARE i INT;

SET dt = (select DATE_ADD(last_etl_time, INTERVAL 1 DAY) from ETL_info_table where table_id = 55);

SET curdate = (select DATE_ADD(curdate(), INTERVAL 1 DAY));

WHILE dt < curdate DO

insert into agg_MobileAcademy_Reports(location_type, location_id, date, ashas_registered, ashas_started_course, ashas_not_started_course, ashas_completed_successfully, ashas_failed_course, ashas_rejected)

select a.location_type, a.location_id, a.date, a.ashas_registered, a.ashas_started_course, a.ashas_not_started_course, b.successes, b.failures, c.rejected from

(select 
'STATE' as location_type,
f.state_id as location_id,
dt as date,
sum(case when f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_registered,
sum(case when f.flw_status = 'ACTIVE' and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' and f.course_start_date < dt then 1 else 0 end) as ashas_started_course,
sum(case when (f.flw_status = 'INACTIVE' or (f.course_start_date >= dt and f.flw_status = 'ACTIVE')) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_not_started_course
from front_line_worker f 
where f.creationdate < dt 
group by f.state_id) as a


LEFT JOIN
(select 
'STATE' as location_type,
f.state_id as location_id,
dt as date,
count(distinct(case when (m.has_passed = 1 and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as successes,
count(distinct(case when (m.has_passed = 0 and m.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.creationDate < dt) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as failures
from front_line_worker f
JOIN ma_course_completion m on m.flw_id = f.flw_id 
where m.creationDate < dt
group by f.state_id) as b on b.location_id = a.location_id

LEFT JOIN
(select 
'STATE' as location_type,
r.state_id as location_id,
dt as date,
count(*) as rejected
from flw_import_rejection r 
where r.creation_date < dt and r.accepted = 0 and r.type = 'ASHA' and r.rejection_reason not in ('UPDATED_RECORD_ALREADY_EXISTS','FLW_TYPE_NOT_ASHA','FLW_IMPORT_ERROR','GF_STATUS_INACTIVE')
group by r.state_id) as c on c.location_id = a.location_id


UNION ALL
select a.location_type,a.location_id,a.date, a.ashas_registered, a.ashas_started_course, a.ashas_not_started_course, b.successes, b.failures, c.rejected from

(select 
'DISTRICT' as location_type,
f.district_id as location_id,
dt as date,
sum(case when f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_registered,
sum(case when f.flw_status = 'ACTIVE' and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' and f.course_start_date < dt then 1 else 0 end) as ashas_started_course,
sum(case when (f.flw_status = 'INACTIVE' or (f.course_start_date >= dt and f.flw_status = 'ACTIVE')) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_not_started_course
from front_line_worker f 
where f.creationdate < dt 
group by f.district_id) as a


LEFT JOIN
(select 
'DISTRICT' as location_type,
f.district_id as location_id,
dt as date,
count(distinct(case when (m.has_passed = 1 and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as successes,
count(distinct(case when (m.has_passed = 0 and m.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.creationDate < dt) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as failures
from front_line_worker f
JOIN ma_course_completion m on m.flw_id = f.flw_id 
where m.creationDate < dt
group by f.district_id) as b on b.location_id = a.location_id

LEFT JOIN
(select 
'DISTRICT' as location_type,
r.district_id as location_id,
dt as date,
count(*) as rejected
from flw_import_rejection r 
where r.creation_date < dt and r.accepted = 0 and r.type = 'ASHA' and r.rejection_reason not in ('UPDATED_RECORD_ALREADY_EXISTS','FLW_TYPE_NOT_ASHA','FLW_IMPORT_ERROR','GF_STATUS_INACTIVE')
group by r.district_id) as c on c.location_id = a.location_id



UNION ALL
select a.location_type,a.location_id,a.date, a.ashas_registered, a.ashas_started_course, a.ashas_not_started_course, b.successes, b.failures, c.rejected from

(select 
'BLOCK' as location_type,
f.block_id as location_id,
dt as date,
sum(case when f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_registered,
sum(case when f.flw_status = 'ACTIVE' and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' and f.course_start_date < dt then 1 else 0 end) as ashas_started_course,
sum(case when (f.flw_status = 'INACTIVE' or (f.course_start_date >= dt and f.flw_status = 'ACTIVE')) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_not_started_course
from front_line_worker f 
where f.creationdate < dt 
group by f.block_id) as a


LEFT JOIN
(select 
'BLOCK' as location_type,
f.block_id as location_id,
dt as date,
count(distinct(case when (m.has_passed = 1 and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as successes,
count(distinct(case when (m.has_passed = 0 and m.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.creationDate < dt) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as failures
from front_line_worker f
JOIN ma_course_completion m on m.flw_id = f.flw_id 
where m.creationDate < dt
group by f.block_id) as b on b.location_id = a.location_id

LEFT JOIN
(select 
'BLOCK' as location_type,
r.health_block_id as location_id,
dt as date,
count(*) as rejected
from flw_import_rejection r 
where r.creation_date < dt and r.accepted = 0 and r.type = 'ASHA' and r.rejection_reason not in ('UPDATED_RECORD_ALREADY_EXISTS','FLW_TYPE_NOT_ASHA','FLW_IMPORT_ERROR','GF_STATUS_INACTIVE')
group by r.health_block_id) as c on c.location_id = a.location_id



UNION ALL
select a.location_type,a.location_id,a.date, a.ashas_registered, a.ashas_started_course, a.ashas_not_started_course, b.successes, b.failures, c.rejected from

(select 
'SUBCENTRE' as location_type,
f.healthsubfacility_id as location_id,
dt as date,
sum(case when f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_registered,
sum(case when f.flw_status = 'ACTIVE' and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' and f.course_start_date < dt then 1 else 0 end) as ashas_started_course,
sum(case when (f.flw_status = 'INACTIVE' or (f.course_start_date >= dt and f.flw_status = 'ACTIVE')) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE' then 1 else 0 end) as ashas_not_started_course
from front_line_worker f 
where f.creationdate < dt 
group by f.healthsubfacility_id) as a


LEFT JOIN
(select 
'SUBCENTRE' as location_type,
f.healthsubfacility_id as location_id,
dt as date,
count(distinct(case when (m.has_passed = 1 and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as successes,
count(distinct(case when (m.has_passed = 0 and m.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.creationDate < dt) and f.flw_designation = 'ASHA' and f.job_status='ACTIVE') then m.flw_id end)) as failures
from front_line_worker f
JOIN ma_course_completion m on m.flw_id = f.flw_id 
where m.creationDate < dt
group by f.healthsubfacility_id) as b on b.location_id = a.location_id

LEFT JOIN
(select 
'SUBCENTRE' as location_type,
r.subcentre_id as location_id,
dt as date,
count(*) as rejected
from flw_import_rejection r 
where r.creation_date < dt and r.accepted = 0 and r.type = 'ASHA' and r.rejection_reason not in ('UPDATED_RECORD_ALREADY_EXISTS','FLW_TYPE_NOT_ASHA','FLW_IMPORT_ERROR','GF_STATUS_INACTIVE')
group by r.subcentre_id) as c on c.location_id = a.location_id;


update ETL_info_table set last_etl_time = dt where table_id = 55;

set dt = DATE_ADD(dt, INTERVAL 1 DAY);

END WHILE;

END
