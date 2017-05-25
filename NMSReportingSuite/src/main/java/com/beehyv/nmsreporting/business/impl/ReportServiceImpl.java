package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.dao.CircleDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.ReportType;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
}
