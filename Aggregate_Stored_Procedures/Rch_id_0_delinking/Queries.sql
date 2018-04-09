Motech Database :

mysql> select * from nms_mcts_mothers where rchid='0';
+----------+-------------+--------------------+-----------------+--------------------+-----------------------+--------------------------+------+----------------------------+--------------+---------------+----------------+---------------------+---------+---------------------+------------+-------+----------------+---------------------+-------+-----------+
| id       | dateOfBirth | beneficiaryId      | district_id_OID | healthBlock_id_OID | healthFacility_id_OID | healthSubFacility_id_OID | name | primaryHealthCenter_id_OID | state_id_OID | taluka_id_OID | village_id_OID | creationDate        | creator | modificationDate    | modifiedBy | owner | updatedDateNic | lastMenstrualPeriod | rchId | maxCaseNo |
+----------+-------------+--------------------+-----------------+--------------------+-----------------------+--------------------------+------+----------------------------+--------------+---------------+----------------+---------------------+---------+---------------------+------------+-------+----------------+---------------------+-------+-----------+
| 62778467 | NULL        | 020203051411500195 |              42 |           52281613 |                  NULL |                 52287939 | NULL |                   52284723 |           12 |      52281612 |           NULL | 2017-07-19 20:05:10 |         | 2017-07-19 20:05:10 |            |       | NULL           | NULL                | 0     |      NULL |
+----------+-------------+--------------------+-----------------+--------------------+-----------------------+--------------------------+------+----------------------------+--------------+---------------+----------------+---------------------+---------+---------------------+------------+-------+----------------+---------------------+-------+-----------+
1 row in set (0.00 sec)

mysql> select count(*) from nms_mcts_children where mother_id_OID = 62778467;
+----------+
| count(*) |
+----------+
|     3629 |
+----------+
1 row in set (0.01 sec)

mysql> select count(*) from nms_subscribers where mother_id_OID = 62778467;
+----------+
| count(*) |
+----------+
|      702 |
+----------+
1 row in set (0.01 sec)


mysql> select a.* from nms_subscriptions a JOIN nms_subscribers b on a.subscriber_id_oid=b.id where a.subscriptionPack_id_oid= 1 and b.mother_id_oid=62778467;
+----------+---------------------+---------------------------+---------------------+-----------------------+---------------------------+--------+------------------------+---------------------+-------------+-------------------+--------------------------------------+-------------------------+---------------------+---------+---------------------+------------+-------+
| id       | activationDate      | deactivationReason        | endDate             | firstMessageDayOfWeek | needsWelcomeMessageViaObd | origin | secondMessageDayOfWeek | startDate           | status      | subscriber_id_OID | subscriptionId                       | subscriptionPack_id_OID | creationDate        | creator | modificationDate    | modifiedBy | owner |
+----------+---------------------+---------------------------+---------------------+-----------------------+---------------------------+--------+------------------------+---------------------+-------------+-------------------+--------------------------------------+-------------------------+---------------------+---------+---------------------+------------+-------+
| 12645610 | 2016-03-14 06:00:00 | NULL                      | 2017-07-24 22:45:56 | MONDAY                |                           | IVR    | FRIDAY                 | 2016-03-14 00:00:00 | COMPLETED   |          12645609 | 695f7e6e-1b89-4e0f-a5a9-2df991f0e664 |                       1 | 2016-03-13 13:05:40 |         | 2017-07-24 22:45:56 |            |       |
| 12722091 | 2016-03-16 00:02:58 | WEEKLY_CALLS_NOT_ANSWERED | 2016-12-20 12:15:37 | TUESDAY               |                           | IVR    | SATURDAY               | 2016-03-15 00:00:00 | DEACTIVATED |          12722080 | dc4a8c8c-b0fd-4c9b-a2e3-0ffd782c4a45 |                       1 | 2016-03-14 10:27:30 |         | 2016-12-20 12:15:37 |            |       |
+----------+---------------------+---------------------------+---------------------+-----------------------+---------------------------+--------+------------------------+---------------------+-------------+-------------------+--------------------------------------+-------------------------+---------------------+---------+---------------------+------------+-------+
2 rows in set (0.01 sec)


mysql> update nms_subscribers set mother_id_OID = null where mother_id_OID = 62778467;
Query OK, 702 rows affected (0.02 sec)
Rows matched: 702  Changed: 702  Warnings: 0

mysql> select count(*) from nms_subscribers where mother_id_OID = 62778467;
+----------+
| count(*) |
+----------+
|        0 |
+----------+
1 row in set (0.00 sec)



mysql> update nms_mcts_children set mother_id_OID = null where mother_id_OID = 62778467;
Query OK, 3629 rows affected (0.11 sec)
Rows matched: 3629  Changed: 3629  Warnings: 0


mysql> select * from nms_mcts_children where mother_id_OID = 62778467;
Empty set (0.00 sec)

mysql> select * from nms_subscribers where mother_id_OID = 62778467;
Empty set (0.00 sec)

mysql> delete from nms_mcts_mothers where id = 62778467;
Query OK, 1 row affected (0.01 sec)

mysql> select * from nms_mcts_mothers where rchid ='0';
Empty set (0.00 sec)




Reporting Database :
===================

mysql> select * from Beneficiary where rch_id ='0';
+----------+--------------------+--------+---------+------+---------------+--------------------+---------------------+-------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+---------------------+-----------+-----------+---------------------+
| id       | mcts_id            | rch_id | case_no | name | language_code | mother_beneficiary | lastMenstrualPeriod | dateOfBirth | state_id | district_id | taluka_id | healthBlock_id | village_id | healthFacility_id | healthSubFacility_id | modificationDate    | mother_id | circle_id | creationDate        |
+----------+--------------------+--------+---------+------+---------------+--------------------+---------------------+-------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+---------------------+-----------+-----------+---------------------+
| 62778467 | 233421811400400048 | 0      |    NULL | NULL |             1 |                   | NULL                | NULL        |       27 |         507 |      3164 |       24071446 |     288390 |              NULL |             38881127 | 2017-07-19 20:05:10 |      NULL |         5 | 2017-07-19 20:05:10 |
+----------+--------------------+--------+---------+------+---------------+--------------------+---------------------+-------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+---------------------+-----------+-----------+---------------------+
1 row in set (0.00 sec)

mysql> select count(*) from Beneficiary where mother_id = 62778467;
+----------+
| count(*) |
+----------+
|     3544 |
+----------+
1 row in set (0.00 sec)

mysql> update Beneficiary set mother_id = null where mother_id = 62778467;
Query OK, 3544 rows affected (0.93 sec)
Rows matched: 3544  Changed: 3544  Warnings: 0

mysql> select * from beneficiary_tracker where mother_beneficiary_id =62778467;
+---------+-----------------------+----------------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+-----------+---------------------+
| id      | mother_beneficiary_id | child_beneficiary_id | state_id | district_id | taluka_id | healthBlock_id | village_id | healthFacility_id | healthSubFacility_id | circle_id | creationDate        |
+---------+-----------------------+----------------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+-----------+---------------------+
| 4632376 |              62778467 |                 NULL |       27 |         507 |      3164 |       24071446 |     288390 |              NULL |             38881127 |         5 | 2017-07-19 20:05:10 |
+---------+-----------------------+----------------------+----------+-------------+-----------+----------------+------------+-------------------+----------------------+-----------+---------------------+
1 row in set (0.00 sec)

mysql> select count(*) from subscriptions where beneficiaryTracking_id = 4632376;
+----------+
| count(*) |
+----------+
|     2829 |
+----------+
1 row in set (1.28 sec)

mysql> update subscriptions set beneficiaryTracking_id = null where beneficiaryTracking_id = 4632376;
Query OK, 2829 rows affected (5.66 sec)
Rows matched: 2829  Changed: 2829  Warnings: 0

mysql> select count(*) from beneficiary_call_measure where beneficiaryTracking_id =4632376;
+----------+
| count(*) |
+----------+
|   119281 |
+----------+
1 row in set (0.07 sec)


mysql> update beneficiary_call_measure set beneficiaryTracking_id = null where beneficiaryTracking_id = 4632376;
Query OK, 119281 rows affected (2.39 sec)
Rows matched: 119281  Changed: 119281  Warnings: 0


mysql> delete from Beneficiary where id = 62778467;
Query OK, 1 row affected (0.01 sec)


mysql> update subscriptions set beneficiary_id = null where subscription_id in (12645610, 12722091);
Query OK, 2 rows affected (0.00 sec)
Rows matched: 2  Changed: 2  Warnings: 0



mysql> delete from beneficiary_tracker where id = 4632376;
Query OK, 1 row affected (0.01 sec)


mysql> select count(*) from Beneficiary where mother_id is null and mother_beneficiary =0 and id not in (select child_beneficiary_id from beneficiary_tracker where child_beneficiary_id is not null) and id not in (select child_beneficiary_id from beneficiary_tracker_till_nov_30_2016 where child_beneficiary_id is not null);
+----------+
| count(*) |
+----------+
|     3544 |
+----------+
1 row in set (6.69 sec)


mysql> insert into beneficiary_tracker (child_beneficiary_id, state_id, district_id, taluka_id, healthBlock_id, village_id, healthFacility_id, healthSubFacility_id, circle_id, creationDate) select id, state_id, district_id, taluka_id, healthBlock_id, village_id, healthFacility_id, healthSubFacility_id, circle_id, creationDate from Beneficiary where mother_id is null and mother_beneficiary =0 and id not in (select child_beneficiary_id from beneficiary_tracker where child_beneficiary_id is not null) and id not in (select child_beneficiary_id from beneficiary_tracker_till_nov_30_2016 where child_beneficiary_id is not null);
Query OK, 3544 rows affected (9.13 sec)
Records: 3544  Duplicates: 0  Warnings: 0


mysql> select count(*) from beneficiary_call_measure a JOIN subscriptions b on a.subscription_id=b.subscription_id where b.beneficiary_id is not null and a.beneficiaryTracking_id is null;
+----------+
| count(*) |
+----------+
|   119168 |
+----------+
1 row in set (1.17 sec)


mysql> 
mysql> select count(*) from beneficiary_call_measure where subscription_id in (12645610, 12722091);
+----------+
| count(*) |
+----------+
|      113 |
+----------+
1 row in set (0.00 sec)

mysql> update beneficiary_call_measure a JOIN (select a.subscription_id, b.beneficiaryTracking_id from beneficiary_call_measure a JOIN subscriptions b on a.subscription_id=b.subscription_id where b.beneficiary_id is not null and a.beneficiaryTracking_id is null) b set a.beneficiaryTracking_id=b.beneficiaryTracking_id where a.subscription_id=b.subscription_id;
Query OK, 119168 rows affected (19.84 sec)

mysql> select count(*) from beneficiary_call_measure a JOIN subscriptions b on a.subscription_id=b.subscription_id where b.beneficiary_id is not null and a.beneficiaryTracking_id is null;
+----------+
| count(*) |
+----------+
|        0 |
+----------+
1 row in set (0.63 sec)




