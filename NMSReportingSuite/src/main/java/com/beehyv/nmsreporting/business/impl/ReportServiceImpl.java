package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.dao.ReportTypeDao;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.StateCircle;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

/**
 * Created by beehyv on 25/5/17.
 */
@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService{

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private StateCircleDao stateCircleDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    private final String documents = System.getProperty("user.home") + File.separator+ "Documents/";
    private final String reports = documents+"Reports/";

    @Override
    public List<String> getReportPathName(ReportRequest reportRequest) {
        String rootPath = "";
        String place = "NATIONAL";

        if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            if(reportRequest.getCircleId()!=0){
                place=circleDao.getByCircleId(reportRequest.getCircleId()).getCircleName();
                rootPath+=place+"/";
            }
        }
        else {
            if (reportRequest.getStateId() != 0) {
                place = stateDao.findByStateId(reportRequest.getStateId()).getStateName();
                rootPath += place + "/";
            }

            if (reportRequest.getDistrictId() != 0) {
                place = districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName();
                rootPath += place + "/";
            }

            if (reportRequest.getBlockId() != 0) {
                place = blockDao.findByblockId(reportRequest.getBlockId()).getBlockName();
                rootPath += place + "/";
            }
        }
        String filename= reportRequest.getReportType()+"_"+place+"_"+reportRequest.getToDate()+".xlsx";
        rootPath = reports+reportRequest.getReportType()+"/"+rootPath+filename;
        List<String> extras = new ArrayList<>();
        extras.add(filename);
        extras.add(rootPath);
        return extras;
    }

//    @Override
//    public List<StateCircle> getCirclesByState(Integer stateId) {
//        return stateCircleDao.getCirclesByState(stateId);
//    }


    @Override
    public List<Circle> getUserCircles(User user){
        List<StateCircle> list;
        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            list = stateCircleDao.getCirclesByState(null);
        }
        else{
            list = stateCircleDao.getCirclesByState(user.getStateId());
        }
        List<Circle> circleList = new ArrayList<>();
        for(StateCircle item : list){
            circleList.add(circleDao.getByCircleId(item.getCircleId()));
        }
        return circleList;
    }

    @Override
    public ReportType getReportTypeByName(String reportName) {
        return reportTypeDao.getReportTypeByName(reportName);
    }

    @Override
    public String getMonthYear(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
//        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }
}
