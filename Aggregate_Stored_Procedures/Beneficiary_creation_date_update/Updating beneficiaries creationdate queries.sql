If date Diff is more than 1 day :
================================
select a.beneficiary_id, b.creationdate as Beneficiary_creationDate, b.modificationDate as Beneficiary_modificationdate, a.subscription_id, a.creation_time as Subscription_CreationDate, a.last_modified as Subscription_modificationDate from (select  beneficiary_id, subscription_id, creation_time, last_modified from subscriptions group by beneficiary_id order by creation_time )a LEFT JOIN Beneficiary b on b.id=a.beneficiary_id where b.creationdate like '2015-10%' and a.beneficiary_id is not null and (b.creationdate < (a.creation_time - INTERVAL 1 DAY) or b.creationdate > (a.creation_time + INTERVAL 1 DAY)); ---- 8109

If date diff is 1 day :
======================
select a.beneficiary_id, b.creationdate as Beneficiary_creationDate, b.modificationDate as Beneficiary_modificationdate, a.subscription_id, a.creation_time as Subscription_CreationDate, a.last_modified as Subscription_modificationDate from (select  beneficiary_id, subscription_id, creation_time, last_modified from subscriptions group by beneficiary_id order by creation_time )a LEFT JOIN Beneficiary b on b.id=a.beneficiary_id where b.creationdate like '2015-10%' and a.beneficiary_id is not null and b.creationdate > (a.creation_time - INTERVAL 1 DAY) and b.creationdate < (a.creation_time + INTERVAL 1 DAY); --- 5661



We are updating in Reporting database but not in Motech database because on Reporting database, because we seperated the subscriptions into 2 categories (completed/deactivated before 1st Dec2016 and Active on 1st Dec2016) but we did not seperated on Motech database because of this we may disturb the data like there is a Mother she had 2 subscriptions in Motech, 1st deactivated 20th Nov16 and 2nd created 5th May2017 then if we updated 5th May2017 as Beneficiary create date then other will confuse on this case.

mysql> update Beneficiary a JOIN (select a.beneficiary_id, a.creation_time as creationDate, a.last_modified modificationDate from (select  beneficiary_id, subscription_id, creation_time, last_modified from subscriptions group by beneficiary_id order by creation_time )a LEFT JOIN Beneficiary b on b.id=a.beneficiary_id where b.creationdate like '2015-10%' and a.beneficiary_id is not null and (b.creationdate < (a.creation_time - INTERVAL 1 DAY) or b.creationdate > (a.creation_time + INTERVAL 1 DAY))) b set a.creationdate=b.creationdate, a.modificationdate =b.modificationdate where a.id=b.beneficiary_id;
Query OK, 8109 rows affected, 1 warning (18.52 sec)
Rows matched: 8109  Changed: 8109  Warnings: 0


=====

Updating Beneficiary Tracker creationdate form Beneficiary table :

mysql> update beneficiary_tracker a JOIN Beneficiary b set a.creationdate =b.creationdate where b.id=a.mother_beneficiary_id and b.id in (select a.id from (select a.id from Beneficiary a JOIn beneficiary_tracker b on a.id=mother_beneficiary_id where a.creationdate != b.creationdate) as a);
Query OK, 8100 rows affected (16.73 sec)
Rows matched: 8100  Changed: 8100  Warnings: 0

mysql> update beneficiary_tracker a JOIN Beneficiary b set a.creationdate =b.creationdate where b.id=a.child_beneficiary_id and b.id in (select a.id from (select a.id from Beneficiary a JOIn beneficiary_tracker b on a.id=child_beneficiary_id where a.creationdate != b.creationdate) as a);
Query OK, 6 rows affected (2.82 sec)
Rows matched: 6  Changed: 6  Warnings: 0

