package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.MAPerformanceService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.MAPerformanceCountsDto;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    private StateServiceDao stateServiceDao;

    @Autowired
    private HealthFacilityDao healthFacilitydao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

//    @Override
//    public Long getAccessedCount(Integer locationId, String locationType, Date fromDate, Date toDate){
//
//        Long count = (long)0;
//        Long addedCount = (long)0;
//        Long differenceCount = (long)0;
//
//        if(locationType.equalsIgnoreCase("DifferenceState")){
//            count =  maPerformanceDao.accessedAtLeastOnce(-locationId,"State",fromDate,toDate);
//            List<District> districts = districtDao.getDistrictsOfState(-locationId);
//            for(District d:districts){
//                addedCount += maPerformanceDao.accessedAtLeastOnce(d.getDistrictId(),"District",fromDate,toDate);
//            }
//            differenceCount = count - addedCount;
//            return differenceCount;
//        }
//        else{
//            if(locationType.equalsIgnoreCase("DifferenceDistrict")){
//
//                count =  maPerformanceDao.accessedAtLeastOnce(-locationId,"District",fromDate,toDate);
//                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
//                for (Block d : blocks) {
//                    addedCount += maPerformanceDao.accessedAtLeastOnce(d.getBlockId(),"Block",fromDate,toDate);
//                    }
//                differenceCount = count - addedCount;
//                return differenceCount;
//                }
//            else{
//                if(locationType.equalsIgnoreCase("DifferenceBlock")) {
//
//                    count =  maPerformanceDao.accessedAtLeastOnce(-locationId,"Block",fromDate,toDate);
//                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(-locationId);
//                    for(Subcenter s: subcenters){
//                        addedCount += maPerformanceDao.accessedAtLeastOnce(s.getSubcenterId(),"Subcenter",fromDate,toDate);
//                        }
//                    differenceCount = count - addedCount;
//                    return differenceCount;
//                }
//                else {
//                    count =  maPerformanceDao.accessedAtLeastOnce(locationId,locationType,fromDate,toDate);
//                    return count;
//                }
//
//            }
//        }
//
//    }
//
//    @Override
//    public Long getNotAccessedcount(Integer locationId, String locationType, Date fromDate, Date toDate){
//
//        Long count = (long)0;
//        Long addedCount = (long)0;
//        Long differenceCount = (long)0;
//
//
//        if(locationType.equalsIgnoreCase("DifferenceState")){
//            count =  maPerformanceDao.accessedNotOnce(-locationId,"State",fromDate,toDate);
//            List<District> districts = districtDao.getDistrictsOfState(-locationId);
//            for(District d:districts){
//                addedCount += maPerformanceDao.accessedNotOnce(d.getDistrictId(),"District",fromDate,toDate);
//            }
//            differenceCount = count - addedCount;
//            return differenceCount;
//        }
//        else{
//            if(locationType.equalsIgnoreCase("DifferenceDistrict")){
//
//                count =  maPerformanceDao.accessedNotOnce(-locationId,"District",fromDate,toDate);
//                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
//                for (Block d : blocks) {
//                    addedCount += maPerformanceDao.accessedNotOnce(d.getBlockId(),"Block",fromDate,toDate);
//                }
//                differenceCount = count - addedCount;
//                return differenceCount;
//            }
//            else{
//                if(locationType.equalsIgnoreCase("DifferenceBlock")) {
//
//                    count =  maPerformanceDao.accessedNotOnce(-locationId,"Block",fromDate,toDate);
//                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(-locationId);
//                    for(Subcenter s: subcenters){
//                        addedCount += maPerformanceDao.accessedNotOnce(s.getSubcenterId(),"Subcenter",fromDate,toDate);
//                    }
//                    differenceCount = count - addedCount;
//                    return differenceCount;
//                }
//                else{
//                    count =  maPerformanceDao.accessedNotOnce(locationId,locationType,fromDate,toDate);
//                    return count;
//                }
//            }
//        }
//    }
//
//    @Override
//    public Integer getAshasFailed(Integer locationId, String locationType, Date fromDate, Date toDate){
//
//        Integer count = 0;
//        Integer addedCount = 0;
//        Integer differenceCount = 0;
//
//
//        if(locationType.equalsIgnoreCase("DifferenceState")){
//            count =  maPerformanceDao.getAshasFailed(-locationId,"State",fromDate,toDate);
//            List<District> districts = districtDao.getDistrictsOfState(-locationId);
//            for(District d:districts){
//                addedCount += maPerformanceDao.getAshasFailed(d.getDistrictId(),"District",fromDate,toDate);
//            }
//            differenceCount = count - addedCount;
//            return differenceCount;
//        }
//        else{
//            if(locationType.equalsIgnoreCase("DifferenceDistrict")){
//
//                count =  maPerformanceDao.getAshasFailed(-locationId,"District",fromDate,toDate);
//                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
//                for (Block d : blocks) {
//                    addedCount += maPerformanceDao.getAshasFailed(d.getBlockId(),"Block",fromDate,toDate);
//                }
//                differenceCount = count - addedCount;
//                return differenceCount;
//            }
//            else{
//                if(locationType.equalsIgnoreCase("DifferenceBlock")) {
//
//                    count =  maPerformanceDao.getAshasFailed(-locationId,"Block",fromDate,toDate);
//                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(-locationId);
//                    for(Subcenter s: subcenters){
//                        addedCount += maPerformanceDao.getAshasFailed(s.getSubcenterId(),"Subcenter",fromDate,toDate);
//                    }
//                    differenceCount = count - addedCount;
//                    return differenceCount;
//                }
//                else{
//                    count =  maPerformanceDao.getAshasFailed(locationId,locationType,fromDate,toDate);
//                    return count;
//                }
//            }
//        }
//    }

    @Override
    public HashMap<Long,MAPerformanceCountsDto> getMAPerformanceCounts (Integer locationId, String locationType, Date fromDate, Date toDate){

        Long count = (long)0;
        Long addedCount = (long)0;
        Long differenceCount = (long)0;
        Date fromDateTemp = fromDate;
        HashMap<Long,MAPerformanceCountsDto> countMap = new HashMap<>();

        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("MOBILE_ACADEMY");
            for(State s:states){
                Long accessedCount = 0L;
                Long notAccessedCount = 0L;
                Integer failedCount = 0;
                if(fromDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY"))){
                    fromDateTemp = stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY");
                }
                MAPerformanceCountsDto statePerformance = new MAPerformanceCountsDto();
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY"))) {
                    accessedCount = maPerformanceDao.accessedAtLeastOnce(s.getStateId(), locationType, fromDateTemp, toDate);
                    notAccessedCount = maPerformanceDao.accessedNotOnce(s.getStateId(), locationType, fromDateTemp, toDate);
                    failedCount = maPerformanceDao.getAshasFailed(s.getStateId(), locationType, fromDateTemp, toDate);
                }
                statePerformance.setAccessedAtleastOnce(accessedCount);
                statePerformance.setAccessedNotOnce(notAccessedCount);
                statePerformance.setAshasFailed(failedCount);
                countMap.put((long)s.getStateId(),statePerformance);

                fromDateTemp = fromDate;
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(locationId,"MOBILE_ACADEMY"))){
                    fromDateTemp = stateServiceDao.getServiceStartDateForState(locationId,"MOBILE_ACADEMY");
                }
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                Long stateCounts1 = maPerformanceDao.accessedAtLeastOnce(locationId,"State",fromDateTemp,toDate);
                Long stateCounts2 = maPerformanceDao.accessedNotOnce(locationId,"State",fromDateTemp,toDate);
                Integer stateCounts3 = maPerformanceDao.getAshasFailed(locationId,"State",fromDateTemp,toDate);
                Long accessedCount = 0L;
                Long notAccessedCount = 0L;
                Integer failedCount = 0;
                for(District d:districts){
                    MAPerformanceCountsDto districtPerformance = new MAPerformanceCountsDto();
                    Long districtCount1 = maPerformanceDao.accessedAtLeastOnce(d.getDistrictId(),locationType,fromDateTemp,toDate);
                    Long districtCount2 = maPerformanceDao.accessedNotOnce(d.getDistrictId(),locationType,fromDateTemp,toDate);
                    Integer districtCount3 = maPerformanceDao.getAshasFailed(d.getDistrictId(),locationType,fromDateTemp,toDate);
                    districtPerformance.setAccessedAtleastOnce(districtCount1);
                    districtPerformance.setAccessedNotOnce(districtCount2);
                    districtPerformance.setAshasFailed(districtCount3);
                     countMap.put((long)d.getDistrictId(),districtPerformance);
                    accessedCount+=districtCount1;
                    notAccessedCount+=districtCount2;
                    failedCount+=districtCount3;

                }
                Long noDistrictCount1 = 0L;
                Long noDistrictCount2 = 0L;
                Integer noDistrictCount3 = 0;
                MAPerformanceCountsDto noDistrictPerformance = new MAPerformanceCountsDto();
                noDistrictCount1= stateCounts1 - accessedCount;
                noDistrictCount2= stateCounts2 - notAccessedCount;
                noDistrictCount3= stateCounts3 - failedCount;

                noDistrictPerformance.setAccessedAtleastOnce(noDistrictCount1);
                noDistrictPerformance.setAccessedNotOnce(noDistrictCount2);
                noDistrictPerformance.setAshasFailed(noDistrictCount3);
                countMap.put((long)-locationId,noDistrictPerformance);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"MOBILE_ACADEMY"))){
                        fromDateTemp = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"MOBILE_ACADEMY");
                    }
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    Long districtCounts1 = maPerformanceDao.accessedAtLeastOnce(locationId,"District",fromDateTemp,toDate);
                    Long districtCounts2 = maPerformanceDao.accessedNotOnce(locationId,"District",fromDateTemp,toDate);
                    Integer districtCounts3 = maPerformanceDao.getAshasFailed(locationId,"District",fromDateTemp,toDate);
                    Long accessedCount = 0L;
                    Long notAccessedCount = 0L;
                    Integer failedCount = 0;
                    for (Block d : blocks) {
                        MAPerformanceCountsDto blockPerformance = new MAPerformanceCountsDto();
                        Long blockCount1 = maPerformanceDao.accessedAtLeastOnce(d.getBlockId(),locationType,fromDateTemp,toDate);
                        Long blockCount2 = maPerformanceDao.accessedNotOnce(d.getBlockId(),locationType,fromDateTemp,toDate);
                        Integer blockCount3 = maPerformanceDao.getAshasFailed(d.getBlockId(),locationType,fromDateTemp,toDate);

                        blockPerformance.setAccessedAtleastOnce(blockCount1);
                        blockPerformance.setAccessedNotOnce(blockCount2);
                        blockPerformance.setAshasFailed(blockCount3);
                        countMap.put((long)d.getBlockId(),blockPerformance);
                        accessedCount+=blockCount1;
                        notAccessedCount+=blockCount2;
                        failedCount+=blockCount3;
                    }
                    Long noBlockCount1 = 0L;
                    Long noBlockCount2 = 0L;
                    Integer noBlockCount3 = 0;
                    MAPerformanceCountsDto noBlockPerformance = new MAPerformanceCountsDto();
                    noBlockCount1= districtCounts1 - accessedCount;
                    noBlockCount2= districtCounts2 - notAccessedCount;
                    noBlockCount3= districtCounts3 - failedCount;

                    noBlockPerformance.setAccessedAtleastOnce(noBlockCount1);
                    noBlockPerformance.setAccessedNotOnce(noBlockCount2);
                    noBlockPerformance.setAshasFailed(noBlockCount3);
                    countMap.put((long)-locationId,noBlockPerformance);
                }
                else {
                    if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"MOBILE_ACADEMY"))){
                        fromDateTemp = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"MOBILE_ACADEMY");
                    }
                    List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
                    List<HealthSubFacility> subcenters = new ArrayList<>();
                    for(HealthFacility hf :healthFacilities){
                        subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
                    }
                    Long blockCounts1 = maPerformanceDao.accessedAtLeastOnce(locationId,"block",fromDateTemp,toDate);
                    Long blockCounts2 = maPerformanceDao.accessedNotOnce(locationId,"block",fromDateTemp,toDate);
                    Integer blockCounts3 = maPerformanceDao.getAshasFailed(locationId,"block",fromDateTemp,toDate);
                    Long accessedCount = 0L;
                    Long notAccessedCount = 0L;
                    Integer failedCount = 0;
                    for(HealthSubFacility s: subcenters){
                        MAPerformanceCountsDto subcentrePerformance = new MAPerformanceCountsDto();
                        Long subcentreCount1 = maPerformanceDao.accessedAtLeastOnce(s.getHealthSubFacilityId(),locationType,fromDateTemp,toDate);
                        Long subcentreCount2 = maPerformanceDao.accessedNotOnce(s.getHealthSubFacilityId(),locationType,fromDateTemp,toDate);
                        Integer subcentreCount3 = maPerformanceDao.getAshasFailed(s.getHealthSubFacilityId(),locationType,fromDateTemp,toDate);

                        subcentrePerformance.setAccessedAtleastOnce(subcentreCount1);
                        subcentrePerformance.setAccessedNotOnce(subcentreCount2);
                        subcentrePerformance.setAshasFailed(subcentreCount3);
                        countMap.put((long)s.getHealthSubFacilityId(),subcentrePerformance);
                        accessedCount+=subcentreCount1;
                        notAccessedCount+=subcentreCount2;
                        failedCount+=subcentreCount3;
                    }
                    Long noSubcentreCount1 = 0L;
                    Long noSubcentreCount2 = 0L;
                    Integer noSubcentreCount3 = 0;
                    MAPerformanceCountsDto noSubcentrePerformance = new MAPerformanceCountsDto();
                    noSubcentreCount1= blockCounts1 - accessedCount;
                    noSubcentreCount2= blockCounts2 - notAccessedCount;
                    noSubcentreCount3= blockCounts3 - failedCount;

                    noSubcentrePerformance.setAccessedAtleastOnce(noSubcentreCount1);
                    noSubcentrePerformance.setAccessedNotOnce(noSubcentreCount2);
                    noSubcentrePerformance.setAshasFailed(noSubcentreCount3);
                    countMap.put((long)-locationId,noSubcentrePerformance);
                }
            }
        }
            return countMap;
    }

}
