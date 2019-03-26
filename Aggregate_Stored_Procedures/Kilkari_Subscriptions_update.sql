CREATE DEFINER=`etluser`@`%` PROCEDURE `Kilkari_Subscriptions_update`()
BEGIN

DECLARE dt DATETIME;
DECLARE curdate DATETIME;

SET dt = (select last_etl_time from ETL_info_table where table_id = 28);

SET curdate = (select curdate());

WHILE dt < curdate DO

update subscriptions a JOIN (select subscription_id , ROUND(datediff(enddate, activation_date )/7, 2) as age from subscriptions where enddate >= dt and enddate < DATE_ADD(dt, INTERVAL 1 DAY)) b on b.subscription_id = a.subscription_id set a.ageOnService=b.age;


Update subscriptions a JOIN (SELECT 
    a.subscription_id,
    SUM(COALESCE(listenedContentDuration, 0)) AS listenedContentDuration,
    SUM(COALESCE(totalContentDuration, 0)) AS totalContentDuration,
    SUM(COALESCE(answeredCalls, 0)) AS answeredCalls,
    SUM(COALESCE(attemptedCalls, 0)) AS attemptedCalls
FROM
    (SELECT 
        a.subscription_id,
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN coalesce(a.msg_duration, 0)
                ELSE 0
            END) AS 'listenedContentDuration',
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN coalesce(b.message_duration, 0)
                ELSE 0
            END) AS 'totalContentDuration',
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN 1
                ELSE 0
            END) AS 'answeredCalls',
            SUM(CASE
                WHEN a.call_source = 'OBD' THEN 1
                ELSE 0
            END) AS 'attemptedCalls'
    FROM
        beneficiary_call_measure a
    LEFT JOIN message_durations b ON b.id = a.campaign_id
    WHERE
        a.subscription_id IN (SELECT 
                CAST(subscription_id AS CHAR)
            FROM
                subscriptions
            WHERE
                subscription_status IN ('DEACTIVATED' , 'COMPLETED')
                    AND enddate >= dt
                    AND enddate < DATE_ADD(dt, INTERVAL 1 DAY))
            AND a.call_source = 'OBD'
            AND a.subscription_id IS NOT NULL
    GROUP BY a.subscription_id UNION ALL SELECT 
        a.subscription_id,
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN coalesce(a.msg_duration, 0)
                ELSE 0
            END) AS 'listenedContentDuration',
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN coalesce(b.message_duration, 0)
                ELSE 0
            END) AS 'totalContentDuration',
            SUM(CASE
                WHEN a.call_status = 'SUCCESS' THEN 1
                ELSE 0
            END) AS 'answeredCalls',
            SUM(CASE
                WHEN a.call_source = 'OBD' THEN 1
                ELSE 0
            END) AS 'attemptedCalls'
    FROM
        beneficiary_call_measure_till_Nov_30_2016 a
    LEFT JOIN message_durations b ON b.id = a.campaign_id
    WHERE
        a.subscription_id IN (SELECT 
                CAST(subscription_id AS CHAR)
            FROM
                subscriptions
            WHERE
                subscription_status IN ('DEACTIVATED' , 'COMPLETED')
                    AND enddate >= dt
                    AND enddate < DATE_ADD(dt, INTERVAL 1 DAY))
            AND a.call_source = 'OBD'
            AND a.subscription_id IS NOT NULL
    GROUP BY a.subscription_id) AS a
GROUP BY a.subscription_id) b on a.subscription_id=b.subscription_id set a.listenedContentDuration = coalesce(b.listenedContentDuration, 0), a.totalContentDuration=coalesce(b.totalContentDuration, 0), a.answeredCalls=coalesce(b.answeredCalls, 0), a.attemptedCalls=coalesce(b.attemptedCalls, 0);

set dt = DATE_ADD(dt, INTERVAL 1 DAY);

update ETL_info_table set last_etl_time = dt where table_id = 28;

END WHILE;

END
