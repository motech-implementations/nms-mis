package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AshaEachBlockServiceDao;
import com.beehyv.nmsreporting.model.AshaInEachBlock;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository("ashaEachBlockServiceDao")
@Transactional
public class AshaEachBlockServiceDaoImpl extends AbstractDao<Integer, AshaInEachBlock> implements AshaEachBlockServiceDao {

    private final String queryString1 = "select cd.flw_id, cc.call_detail_id, cc.contentName, cc.correct_answer_entered, cd.duration, cc.start_time, f.course_first_completion_date, cp.noOfAttempts, cdm.total\n" +
            "                from Reporting.ma_call_content_measure cc \n" +
            "                inner join (select * from Reporting.front_line_worker \n" +
            "                where state_id = 28 and district_id = :districtId and block_id = :blockId \n" +
            "                and flw_designation='ASHA' and course_first_completion_date is not null order by course_first_completion_date asc limit 50) f\n" +
            "                inner join (select count(cmp.flw_id) as noOfAttempts, cmp.flw_id from Reporting.ma_course_completion cmp group by cmp.flw_id) cp on cp.flw_id = f.flw_id \n" +
            "                left join Reporting.ma_call_detail_measure cd\n" +
            "                inner join (SELECT flw_id, SUM(duration) as total\n" +
            "                FROM Reporting.ma_call_detail_measure\n" +
            "                Group By flw_id) cdm on cdm.flw_id = cd.flw_id\n" +
            "                on cc.call_detail_id = cd.id\n" +
            "                where cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 day) and cd.flw_id = f.flw_id order by cd.flw_id asc, cc.start_time desc";

    private final String queryString2 =  "select cd.flw_id, cc.call_detail_id, cc.contentName, cc.correct_answer_entered, cd.duration, cc.start_time, f.course_first_completion_date, cp.noOfAttempts, cdm.total\n" +
            "                from Reporting.ma_call_content_measure cc \n" +
            "                inner join (select * from Reporting.front_line_worker \n" +
            "                where state_id = 28 and district_id = :districtId and block_id = :blockId \n" +
            "                and flw_designation='ASHA' and course_first_completion_date is not null order by course_first_completion_date desc limit 50) f\n" +
            "                inner join (select count(cmp.flw_id) as noOfAttempts, cmp.flw_id from Reporting.ma_course_completion cmp group by cmp.flw_id) cp on cp.flw_id = f.flw_id \n" +
            "                left join Reporting.ma_call_detail_measure cd\n" +
            "                inner join (SELECT flw_id, SUM(duration) as total\n" +
            "                FROM Reporting.ma_call_detail_measure\n" +
            "                Group By flw_id) cdm on cdm.flw_id = cd.flw_id\n" +
            "                on cc.call_detail_id = cd.id\n" +
            "                where cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 day) and cd.flw_id = f.flw_id order by cd.flw_id asc, cc.start_time desc";


    private String queryString3 =  "select cd.flw_id, f.state_id, f.district_id,\n" +
            "    coalesce(f.block_id, 0), cc.call_detail_id, cc.contentName, cc.correct_answer_entered, cd.duration, cc.start_time, f.course_first_completion_date, cp.noOfAttempts, cdm.total\n" +
            "                from Reporting.ma_call_content_measure cc \n" +
            "                inner join (select * from Reporting.front_line_worker a \n" +
            "                where state_id = 28 and district_id = :districtId \n" +
            "                and flw_designation='ASHA' and a.course_first_completion_date >= '2018-01-01 00:00:00') f\n" +
            "                inner join (select count(cmp.flw_id) as noOfAttempts, cmp.flw_id from Reporting.ma_course_completion cmp group by cmp.flw_id) cp on cp.flw_id = f.flw_id \n" +
            "                left join Reporting.ma_call_detail_measure cd\n" +
            "                inner join (SELECT flw_id, SUM(duration) as total\n" +
            "                FROM Reporting.ma_call_detail_measure\n" +
            "                Group By flw_id) cdm on cdm.flw_id = cd.flw_id\n" +
            "                on cc.call_detail_id = cd.id\n" +
            "                where cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 day) and cd.flw_id = f.flw_id order by cd.flw_id asc, cc.start_time desc";


    private String queryString2018 =  "SELECT \n" +
            "        cd.flw_id,\n" +
            "           f.flw_name,f.flw_msisdn, b.state_name, c.district_name, d.taluka_name, e.village_name, i.healthblock_name, j.healthfacility_name, k.healthsubfacility_name, \n" +
            "            cc.call_detail_id,\n" +
            "            cc.contentName,\n" +
            "            cc.correct_answer_entered,\n" +
            "            cd.duration,\n" +
            "            cc.start_time,\n" +
            "            f.course_first_completion_date,\n" +
            "            cp.noOfAttempts,\n" +
            "            cdm.total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_content_measure cc\n" +
            "    INNER JOIN (SELECT \n" +
            "        *\n" +
            "    FROM\n" +
            "        Reporting.front_line_worker a\n" +
            "    WHERE\n" +
            "        state_id = 28 AND district_id = :districtId\n" +
            "            AND flw_designation = 'ASHA'\n" +
            "            AND a.course_first_completion_date >= '2018-01-01 00:00:00'\n" +
            "            AND a.course_first_completion_date < '2019-01-01 00:00:00') f\n" +
            "    INNER JOIN (SELECT \n" +
            "        COUNT(cmp.flw_id) AS noOfAttempts, cmp.flw_id\n" +
            "    FROM\n" +
            "        Reporting.ma_course_completion cmp\n" +
            "    GROUP BY cmp.flw_id) cp ON cp.flw_id = f.flw_id\n" +
            "    LEFT JOIN Reporting.ma_call_detail_measure cd\n" +
            "    INNER JOIN (SELECT \n" +
            "        flw_id, SUM(duration) AS total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_detail_measure\n" +
            "    GROUP BY flw_id) cdm ON cdm.flw_id = cd.flw_id ON cc.call_detail_id = cd.id\n" +
            "    LEFT JOIN\n" +
            "    dim_state b ON f.state_id = b.id\n" +
            "        LEFT JOIN\n" +
            "    dim_district c ON f.district_id = c.id\n" +
            "        LEFT JOIN\n" +
            "    dim_taluka d ON f.taluka_id = d.id\n" +
            "        LEFT JOIN\n" +
            "    dim_village e ON f.village_id = e.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthblock i ON f.block_id = i.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthfacility j ON f.healthfacility_id = j.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthsubfacility k ON f.healthsubFacility_id = k.id\n" +
            "    WHERE\n" +
            "        cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 DAY)\n" +
            "            AND cd.flw_id = f.flw_id\n" +
            "    ORDER BY cd.flw_id ASC , cc.start_time DESC\n" +
            "        \n ";

    private String queryString2016 =  "SELECT \n" +
            "        cd.flw_id,\n" +
            "           f.flw_name,f.flw_msisdn, b.state_name, c.district_name, d.taluka_name, e.village_name, i.healthblock_name, j.healthfacility_name, k.healthsubfacility_name, \n" +
            "            cc.call_detail_id,\n" +
            "            cc.contentName,\n" +
            "            cc.correct_answer_entered,\n" +
            "            cd.duration,\n" +
            "            cc.start_time,\n" +
            "            f.course_first_completion_date,\n" +
            "            cp.noOfAttempts,\n" +
            "            cdm.total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_content_measure cc\n" +
            "    INNER JOIN (SELECT \n" +
            "        *\n" +
            "    FROM\n" +
            "        Reporting.front_line_worker a\n" +
            "    WHERE\n" +
            "        state_id = 28 AND district_id = :districtId\n" +
            "            AND flw_designation = 'ASHA'\n" +
            "            AND a.course_first_completion_date >= '2016-01-01 00:00:00'\n" +
            "            AND a.course_first_completion_date < '2017-01-01 00:00:00') f\n" +
            "    INNER JOIN (SELECT \n" +
            "        COUNT(cmp.flw_id) AS noOfAttempts, cmp.flw_id\n" +
            "    FROM\n" +
            "        Reporting.ma_course_completion cmp\n" +
            "    GROUP BY cmp.flw_id) cp ON cp.flw_id = f.flw_id\n" +
            "    LEFT JOIN Reporting.ma_call_detail_measure cd\n" +
            "    INNER JOIN (SELECT \n" +
            "        flw_id, SUM(duration) AS total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_detail_measure\n" +
            "    GROUP BY flw_id) cdm ON cdm.flw_id = cd.flw_id ON cc.call_detail_id = cd.id\n" +
            "    LEFT JOIN\n" +
            "    dim_state b ON f.state_id = b.id\n" +
            "        LEFT JOIN\n" +
            "    dim_district c ON f.district_id = c.id\n" +
            "        LEFT JOIN\n" +
            "    dim_taluka d ON f.taluka_id = d.id\n" +
            "        LEFT JOIN\n" +
            "    dim_village e ON f.village_id = e.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthblock i ON f.block_id = i.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthfacility j ON f.healthfacility_id = j.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthsubfacility k ON f.healthsubFacility_id = k.id\n" +
            "    WHERE\n" +
            "        cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 DAY)\n" +
            "            AND cd.flw_id = f.flw_id\n" +
            "    ORDER BY cd.flw_id ASC , cc.start_time DESC\n" +
            "        \n ";

    private String queryString2017 =  "SELECT \n" +
            "        cd.flw_id,\n" +
            "           f.flw_name,f.flw_msisdn, b.state_name, c.district_name, d.taluka_name, e.village_name, i.healthblock_name, j.healthfacility_name, k.healthsubfacility_name, \n" +
            "            cc.call_detail_id,\n" +
            "            cc.contentName,\n" +
            "            cc.correct_answer_entered,\n" +
            "            cd.duration,\n" +
            "            cc.start_time,\n" +
            "            f.course_first_completion_date,\n" +
            "            cp.noOfAttempts,\n" +
            "            cdm.total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_content_measure cc\n" +
            "    INNER JOIN (SELECT \n" +
            "        *\n" +
            "    FROM\n" +
            "        Reporting.front_line_worker a\n" +
            "    WHERE\n" +
            "        state_id = 28 AND district_id = :districtId\n" +
            "            AND flw_designation = 'ASHA'\n" +
            "            AND a.course_first_completion_date >= '2017-01-01 00:00:00'\n" +
            "            AND a.course_first_completion_date < '2018-01-01 00:00:00') f\n" +
            "    INNER JOIN (SELECT \n" +
            "        COUNT(cmp.flw_id) AS noOfAttempts, cmp.flw_id\n" +
            "    FROM\n" +
            "        Reporting.ma_course_completion cmp\n" +
            "    GROUP BY cmp.flw_id) cp ON cp.flw_id = f.flw_id\n" +
            "    LEFT JOIN Reporting.ma_call_detail_measure cd\n" +
            "    INNER JOIN (SELECT \n" +
            "        flw_id, SUM(duration) AS total\n" +
            "    FROM\n" +
            "        Reporting.ma_call_detail_measure\n" +
            "    GROUP BY flw_id) cdm ON cdm.flw_id = cd.flw_id ON cc.call_detail_id = cd.id\n" +
            "    LEFT JOIN\n" +
            "    dim_state b ON f.state_id = b.id\n" +
            "        LEFT JOIN\n" +
            "    dim_district c ON f.district_id = c.id\n" +
            "        LEFT JOIN\n" +
            "    dim_taluka d ON f.taluka_id = d.id\n" +
            "        LEFT JOIN\n" +
            "    dim_village e ON f.village_id = e.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthblock i ON f.block_id = i.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthfacility j ON f.healthfacility_id = j.id\n" +
            "        LEFT JOIN\n" +
            "    dim_healthsubfacility k ON f.healthsubFacility_id = k.id\n" +
            "    WHERE\n" +
            "        cd.creationDate <= DATE_ADD(f.course_first_completion_date, INTERVAL 1 DAY)\n" +
            "            AND cd.flw_id = f.flw_id\n" +
            "    ORDER BY cd.flw_id ASC , cc.start_time DESC\n" +
            "        \n ";

    public List<AshaInEachBlock> setQuery(Integer districtId, Integer year) {
        Query query = null;
        if ( year == 2016) {
            query = getSession().createSQLQuery(queryString2016);
        } else if ( year == 2017) {
            query = getSession().createSQLQuery(queryString2017);
        } else if ( year == 2018) {
            query = getSession().createSQLQuery(queryString2018);
        }
        query.setParameter("districtId", districtId);
        /*query.setParameter("startDate", Timestamp.valueOf("2017-01-01 00:00:00"));
        query.setParameter("endDate", Timestamp.valueOf("2018-01-01 00:00:00"));*/
//        query.setParameter("blockId", blockId);

        List<Object[]> ashas = query.list();
//        System.out.println(ashas.size());
//        query = getSession().createSQLQuery(queryString2);
//        query.setParameter("districtId", districtId);
//        query.setParameter("blockId", blockId);

//        ashas.addAll(query.list());
        System.out.println(ashas.size());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //DecimalFormat df2 = new DecimalFormat(".##");
        //Long previousFlwId = 0L;
        //int chapterScore = 0 ;
        //Double duration  = 0.00;
        //StringBuilder chapterwiseCounts = new StringBuilder("");
        Map<Long, AshaInEachBlock> ashasMap= new HashMap<>();
        //String chapterName = "Chapter11";
        //String chapterNumber = null;
        //boolean latestScore =  true;
        //boolean startCounting = true;
        DecimalFormat df = new DecimalFormat("#.##");
        List<Long> callIds = new LinkedList<>();
        for (Object[] ashaInEachBlock : ashas) {
            Long flwId =  Long.valueOf(ashaInEachBlock[0].toString());
            String contentName =  ashaInEachBlock[11].toString();
            /*if (previousFlwId.equals(flwId)) {
                continue;
            } else if (!startCounting && !contentName.contains(chapterName)) {
                continue;
            } else {
                startCounting = true;
            }*/

            if (ashasMap.get(flwId) == null) {
                ashasMap.put(flwId, new AshaInEachBlock());
                ashasMap.get(flwId).setFlwId(flwId);
                ashasMap.get(flwId).setAshaName(ashaInEachBlock[1].toString());
                ashasMap.get(flwId).setMobileNumber(ashaInEachBlock[2].toString());
                ashasMap.get(flwId).setState(ashaInEachBlock[3] == null ? null : ashaInEachBlock[3].toString());
                ashasMap.get(flwId).setDistrict(ashaInEachBlock[4] == null ? null : ashaInEachBlock[4].toString());
                ashasMap.get(flwId).setTaluka(ashaInEachBlock[5] == null ? null :  ashaInEachBlock[5].toString());
                ashasMap.get(flwId).setVillage(ashaInEachBlock[6] == null ? null :  ashaInEachBlock[6].toString());
                ashasMap.get(flwId).setBlock(ashaInEachBlock[7] == null ? null :  ashaInEachBlock[7].toString());
                ashasMap.get(flwId).setHealthfacility(ashaInEachBlock[8] == null ? null :  ashaInEachBlock[8].toString());
                ashasMap.get(flwId).setHealthsubfacility(ashaInEachBlock[9] == null ? null : ashaInEachBlock[9].toString());
                ashasMap.get(flwId).setStartDate(ft.format(new Date(( (Timestamp) ashaInEachBlock[14]).getTime())));
                ashasMap.get(flwId).setCompletionDate(ashaInEachBlock[15].toString());
                ashasMap.get(flwId).setNoOfAttempts(Long.valueOf(ashaInEachBlock[16].toString()));
                ashasMap.get(flwId).setTotalDurationInMinutes(Double.valueOf(df.format(Double.valueOf(ashaInEachBlock[17].toString())/60)));

            }

    //        for (Long.valueOf(ashaInEachBlock[1].toString()) : callIds) {
            if (!callIds.contains(Long.valueOf(ashaInEachBlock[10].toString()))) {
               // duration= duration;
          //  } else {
                ((LinkedList<Long>) callIds).push(Long.valueOf(ashaInEachBlock[10].toString()));
                ashasMap.get(flwId).setDurationInMinutes(ashasMap.get(flwId).getDurationInMinutes() + Integer.valueOf(ashaInEachBlock[13].toString()));
                //duration += Integer.valueOf(ashaInEachBlock[13].toString());
            }

            if ( !contentName.contains("Lesson") && Boolean.valueOf(ashaInEachBlock[12].toString())) {
                ashasMap.get(flwId).setTotalScore(ashasMap.get(flwId).getTotalScore() + 1);//chapterScore = 1;
            }

            /*if (contentName.contains(chapterName)) {

                chapterNumber = contentName.substring(contentName.indexOf("Chapter") + 7, contentName.indexOf("_"));
                if ( !contentName.contains("Lesson") && latestScore) {
                    Object completed = ashaInEachBlock[12];
                    if (completed != null && Boolean.valueOf(completed.toString()) ) {
                        chapterScore++;
                    }
                } else {
                    latestScore = false;
                }
            } else  {
                if (!chapterwiseCounts.toString().contains(chapterNumber + ":")) {
                    chapterwiseCounts.append(chapterNumber);
                    chapterwiseCounts.append(":" + chapterScore + ",");
                }
                chapterName = contentName.substring(0, contentName.indexOf("_"));
                if ( !contentName.contains("Lesson") && Boolean.valueOf(ashaInEachBlock[12].toString())) {
                    ashasMap.get(flwId).setTotalScore(ashasMap.get(flwId).getTotalScore() + 1);//chapterScore = 1;
                }*//* else {
                    chapterScore = 0;
                }*//*
                latestScore = true;

            }

            if (contentName.equalsIgnoreCase("Chapter01_Lesson01")) {
                if (!chapterwiseCounts.toString().contains(chapterNumber + ":")) {
                    chapterwiseCounts.append(contentName.substring(contentName.indexOf("Chapter") + 7, contentName.indexOf("_")) + ":" + chapterScore);
                }

                //ashasMap.get(flwId).setChapterWiseScore(chapterwiseCounts.toString());
                //ashasMap.get(flwId).setTotalScore(getTotalScoreFromChapterWiseScores(chapterwiseCounts.toString()));
                ashasMap.get(flwId).setDurationInMinutes(Double.valueOf(df.format(duration/60)));
                previousFlwId = flwId;
                duration = 0.00;
                chapterwiseCounts = new StringBuilder("");
                chapterName = "Chapter11";
                startCounting = false;
            }*/
        }
        for (AshaInEachBlock asha : ashasMap.values()) {
            asha.setDurationInMinutes(Double.valueOf(df.format(asha.getDurationInMinutes()/60)));
        }
        return new ArrayList<>(ashasMap.values());
    }

    /*private Integer getTotalScoreFromChapterWiseScores(String chapterWiseScores) {

        Integer scores = 0;
        String[] chapterScores = chapterWiseScores.split(",");
        if (chapterScores.length == 0) {
            return scores;
        }
        for (String chapterScore : chapterScores) {
            if (chapterScore == null || chapterScore.isEmpty()) {
                continue;
            }
            String[] result = chapterScore.split(":");
            if (result.length < 2) {
                continue;
            }
            scores += Integer.parseInt(result[1]);
        }
        return scores;
    }*/
}
