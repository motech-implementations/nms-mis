package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.StateCircle;
import com.beehyv.nmsreporting.model.User;
import com.beehyv.nmsreporting.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;

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



    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";

    @Override
    public List<String> getReportPathName(ReportRequest reportRequest) {
        String rootPath = "";
        String place = "NATIONAL";

        if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            if(reportRequest.getCircleId()!=0){
                place=StReplace(circleDao.getByCircleId(reportRequest.getCircleId()).getCircleFullName());
                rootPath+=place+"/";
            }
        }
        else {
            if (reportRequest.getStateId() != 0) {
                place = StReplace(stateDao.findByStateId(reportRequest.getStateId()).getStateName());
                rootPath += place + "/";
            }

            if (reportRequest.getDistrictId() != 0) {
                place = StReplace(districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName());
                rootPath += place + "/";
            }

            if (reportRequest.getBlockId() != 0) {
                place = StReplace(blockDao.findByblockId(reportRequest.getBlockId()).getBlockName());
                rootPath += place + "/";
            }
        }
        String filename= reportRequest.getReportType()+"_"+place+"_"+getMonthYear(reportRequest.getToDate())+".xlsx";
        if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType()) ||
                reportRequest.getReportType().equals(ReportType.motherRejected.getReportType()) ||
                reportRequest.getReportType().equals(ReportType.childRejected.getReportType())) {
            filename=reportRequest.getReportType()+"_"+place+"_"+this.getDateMonthYear(reportRequest.getToDate())+".xlsx";
        }
        rootPath = reports+reportRequest.getReportType()+"/"+rootPath+filename;
        List<String> extras = new ArrayList<>();
        extras.add(filename);
        extras.add(rootPath);
        return extras;
    }

//    @Override
//    public List<StateCircle> getRelByStateId(Integer stateId) {
//        return stateCircleDao.getRelByStateId(stateId);
//    }


    @Override
    public List<Circle> getUserCircles(User user){
        List<StateCircle> list = new ArrayList<>();
        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(null);
        }
        else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(user.getStateId());
        }else{
            StateCircle stateCircle = new StateCircle();
            stateCircle.setCircleId(districtDao.findByDistrictId(user.getDistrictId()).getCircleOfDistrict());
            stateCircle.setStateId(user.getStateId());
            list.add(stateCircle);
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
    public String getMonthName(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        int month=c.get(Calendar.MONTH)+1;
//        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

    private String getMonthYear(Date toDate){
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

    @Override
    public String getDateMonthYear(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        int date=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH)+1;
        int year=(calendar.get(Calendar.YEAR))%100;
        String dateString;
        if(date<10) {
            dateString="0"+String.valueOf(date);
        }
        else dateString=String.valueOf(date);
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return dateString + "_" + monthString+"_"+yearString;

    }
}
