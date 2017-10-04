package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.MAPerformanceService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Subcenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 25/9/17.
 */
@Service("maPerformanceServiceImpl")
@Transactional
public class MAPerformanceServiceImpl implements MAPerformanceService{

    @Autowired
    private MAPerformanceDao maPerformanceDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SubcenterDao subcenterDao;


    @Override
    public Long getAccessedCount(Integer locationId, String locationType, Date fromDate, Date toDate){

        Long count = (long)0;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fromDate);
        aCalendar.add(Calendar.DATE,1);
        fromDate = aCalendar.getTime();
        aCalendar.setTime(toDate);
        aCalendar.add(Calendar.DATE,1);
        toDate = aCalendar.getTime();
        if(locationId == 0){
            count =  maPerformanceDao.accessedAtLeastOnce(locationId,locationType,fromDate,toDate);
        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                for(District d:districts){
                    count = maPerformanceDao.accessedAtLeastOnce(d.getDistrictId(),locationType,fromDate,toDate);
                }
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    for (Block d : blocks) {
                        count = maPerformanceDao.accessedAtLeastOnce(d.getBlockId(),locationType,fromDate,toDate);                    }
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    for(Subcenter s: subcenters){
                        count = maPerformanceDao.accessedAtLeastOnce(s.getSubcenterId(),locationType,fromDate,toDate);                    }

                }
            }
        }
        return count;
    }

    public Long getNotAccessedcount(Integer locationId, String locationType, Date fromDate, Date toDate){

        Long count = (long)0;

        if(locationId == 0){
            count =  maPerformanceDao.accessedNotOnce(locationId,locationType,fromDate,toDate);
        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                for(District d:districts){
                    count = maPerformanceDao.accessedNotOnce(d.getDistrictId(),locationType,fromDate,toDate);
                }
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    for (Block d : blocks) {
                        count = maPerformanceDao.accessedNotOnce(d.getBlockId(),locationType,fromDate,toDate);                    }
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    for(Subcenter s: subcenters){
                        count = maPerformanceDao.accessedNotOnce(s.getSubcenterId(),locationType,fromDate,toDate);                    }

                }
            }
        }
        return count;
    }
}
