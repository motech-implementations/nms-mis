package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.MAPerformanceService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.MAPerformanceCountsDto;
import com.beehyv.nmsreporting.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MAPerformanceServiceImpl.class);

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
            List<Integer> stateIds = new ArrayList<>();
            for(State state : states){
                stateIds.add(state.getStateId());
            }

            List<Object[]> results = maPerformanceDao.getPerformanceCount(stateIds,locationType, fromDateTemp,toDate);
            for (Object[] counts : results) {
                Long stateId =0L;
                Long accessedCount = 0L;
                Long notAccessedCount = 0L;
                Integer failedCount = 0;
                Long activatedInBetweenCount = 0L;
                Long deactivatedInBetweenCount = 0L;
                Long refresherCourse = 0L;
                Long completedInGivenTimeCount = 0L;
                Long ashaDeactivatedStartedCourseInBetweenCount =0L;
                Long ashaDeactivatedCompletedCourseInBetweenCount =0L;
                Long ashasStarted = 0L;
                Long ashasCompleted = 0L;

                            stateId = ((BigInteger)counts[0]).longValue();
                            accessedCount = ((BigInteger) counts[1]).longValue();
                            notAccessedCount = ((BigInteger) counts[2]).longValue();
                            failedCount = ((BigInteger) counts[3]).intValue();
                            activatedInBetweenCount = ((BigInteger) counts[4]).longValue();
                            deactivatedInBetweenCount = ((BigInteger) counts[5]).longValue();
                            refresherCourse = ((BigInteger) counts[6]).longValue();
                            completedInGivenTimeCount = ((BigInteger) counts[7]).longValue();
                            ashaDeactivatedStartedCourseInBetweenCount = ((BigInteger) counts[8]).longValue();
                            ashaDeactivatedCompletedCourseInBetweenCount = ((BigInteger) counts[9]).longValue();
                            ashasStarted = ((BigInteger) counts[10]).longValue();
                            ashasCompleted = ((BigInteger) counts[11]).longValue();


                MAPerformanceCountsDto statePerformance = new MAPerformanceCountsDto();
                statePerformance.setAccessedAtleastOnce(accessedCount!=null?accessedCount:0L);
                statePerformance.setAccessedNotOnce(notAccessedCount!=null?notAccessedCount:0L);
                statePerformance.setAshasFailed(failedCount!=null?failedCount:0);
                statePerformance.setAshasActivatedInBetween(activatedInBetweenCount!=null?activatedInBetweenCount:0L);
                statePerformance.setAshasDeactivatedInBetween(deactivatedInBetweenCount!=null?deactivatedInBetweenCount:0L);
                statePerformance.setAshasRefresherCourse(refresherCourse!=null?refresherCourse:0L);
                statePerformance.setAshasCompletedInGivenTime(completedInGivenTimeCount!=null?completedInGivenTimeCount:0L);
                statePerformance.setAshaDeactivatedStartedCourseInBetweenCount(ashaDeactivatedStartedCourseInBetweenCount!=null?ashaDeactivatedStartedCourseInBetweenCount:0L);
                statePerformance.setAshaDeactivatedCompletedCourseInBetweenCount(ashaDeactivatedCompletedCourseInBetweenCount!=null?ashaDeactivatedCompletedCourseInBetweenCount:0L);
                statePerformance.setAshasStarted(ashasStarted!=null?ashasStarted:0L);
                statePerformance.setAshasCompleted(ashasCompleted!=null?ashasCompleted:0L);
                countMap.put(stateId, statePerformance);
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
                Long stateCounts4=maPerformanceDao.getAshaActivatedInBetween(locationId, "State", fromDateTemp, toDate);
                Long stateCounts5=maPerformanceDao.getAshaDeactivatedInBetween(locationId, "State", fromDateTemp, toDate);
                Long stateCounts6= maPerformanceDao.getAshaRefresherCourseInBetween(locationId,"State",fromDateTemp,toDate);
                Long stateCounts7= maPerformanceDao.getAshasCompletedInGivenTime(locationId,"State",fromDateTemp,toDate);
                Long accessedCount = 0L;
                Long notAccessedCount = 0L;
                Integer failedCount = 0;
                Long activatedInBetweenCount=0L;
                Long deactivatedInBetweenCount=0L;
                Long refresherCourseCount=0L;
                Long completedInGivenTimeCount=0L;

                List<Integer> districtIds = new ArrayList<>();
                for(District district : districts){
                    districtIds.add(district.getDistrictId());
                }

                LOGGER.info("Executing performance count query with params: fromDate={}, toDate={}, blockIds={}", fromDateTemp, toDate, districtIds);
                List<Object[]> results = maPerformanceDao.getPerformanceCount(districtIds,locationType,fromDateTemp,toDate);
                LOGGER.info("Query result: {}", results);

                for(Object[] result:results){
                    MAPerformanceCountsDto districtPerformance = new MAPerformanceCountsDto();
                    Long districtId = ((BigInteger)result[0]).longValue();
                    Long districtCount1 = ((BigInteger)result[1]).longValue();
                    Long districtCount2 = ((BigInteger)result[2]).longValue();
                    Integer districtCount3 = ((BigInteger)result[3]).intValue();
                    Long districtCount4= ((BigInteger)result[4]).longValue();
                    Long districtCount5= ((BigInteger)result[5]).longValue();
                    Long districtCount6=  ((BigInteger)result[6]).longValue();
                    Long districtCount7 = ((BigInteger)result[7]).longValue();
                    Long districtCount8 = ((BigInteger)result[8]).longValue();
                    Long districtCount9 = ((BigInteger)result[9]).longValue();
                    Long ashasStarted = ((BigInteger) result[10]).longValue();
                    Long ashasCompleted = ((BigInteger) result[11]).longValue();
                    districtPerformance.setAccessedAtleastOnce(districtCount1!=null?districtCount1:0L);
                    districtPerformance.setAccessedNotOnce(districtCount2!=null?districtCount2:0L);
                    districtPerformance.setAshasFailed(districtCount3!=null?districtCount3:0);
                    districtPerformance.setAshasActivatedInBetween(districtCount4!=null?districtCount4:0L);
                    districtPerformance.setAshasDeactivatedInBetween(districtCount5!=null?districtCount5:0L);
                    districtPerformance.setAshasRefresherCourse(districtCount6!=null?districtCount6:0L);
                    districtPerformance.setAshasCompletedInGivenTime(districtCount7!=null?districtCount7:0L);
                    districtPerformance.setAshaDeactivatedStartedCourseInBetweenCount(districtCount8!=null?districtCount8:0L);
                    districtPerformance.setAshaDeactivatedCompletedCourseInBetweenCount(districtCount9!=null?districtCount9:0L);
                    districtPerformance.setAshasStarted(ashasStarted!=null?ashasStarted:0L);
                    districtPerformance.setAshasCompleted(ashasCompleted!=null?ashasCompleted:0L);

                     countMap.put(districtId,districtPerformance);
                    accessedCount+=districtCount1;
                    notAccessedCount+=districtCount2;
                    failedCount+=districtCount3!=null?districtCount3:0;
                    activatedInBetweenCount+=districtCount4;
                    deactivatedInBetweenCount+=districtCount5;
                    refresherCourseCount+=districtCount6;
                    completedInGivenTimeCount+=districtCount7;
                }
                Long noDistrictCount1 = 0L;
                Long noDistrictCount2 = 0L;
                Integer noDistrictCount3 = 0;
                Long noDistrictCount4 =0L;
                Long noDistrictCount5=0L;
                Long noDistrictCount6=0L;
                Long noDistrictCount7=0L;
                MAPerformanceCountsDto noDistrictPerformance = new MAPerformanceCountsDto();
                noDistrictCount1= stateCounts1 - accessedCount;
                noDistrictCount2= stateCounts2 - notAccessedCount;
                noDistrictCount3= stateCounts3 - failedCount;
                noDistrictCount4= stateCounts4 - activatedInBetweenCount;
                noDistrictCount5= stateCounts5 - deactivatedInBetweenCount;
                noDistrictCount6=stateCounts6 - refresherCourseCount;
                noDistrictCount7=stateCounts7 - completedInGivenTimeCount;
                noDistrictPerformance.setAccessedAtleastOnce(noDistrictCount1);
                noDistrictPerformance.setAccessedNotOnce(noDistrictCount2);
                noDistrictPerformance.setAshasFailed(noDistrictCount3);
                noDistrictPerformance.setAshasActivatedInBetween(noDistrictCount4);
                noDistrictPerformance.setAshasDeactivatedInBetween(noDistrictCount5);
                noDistrictPerformance.setAshasRefresherCourse(noDistrictCount6);
                noDistrictPerformance.setAshasCompletedInGivenTime(noDistrictCount7);

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
                    Long districtCounts4=maPerformanceDao.getAshaActivatedInBetween(locationId, "District", fromDateTemp, toDate);
                    Long districtCounts5=maPerformanceDao.getAshaDeactivatedInBetween(locationId, "District", fromDateTemp, toDate);
                    Long districtCounts6= maPerformanceDao.getAshaRefresherCourseInBetween(locationId,"District",fromDateTemp,toDate);
                    Long districtCounts7 = maPerformanceDao.getAshasCompletedInGivenTime(locationId,"District",fromDateTemp,toDate);
                    Long accessedCount = 0L;
                    Long notAccessedCount = 0L;
                    Integer failedCount = 0;
                    Long activatedInBetweenCount=0L;
                    Long deactivatedInBetweenCount=0L;
                    Long refresherCourseCount=0L;
                    Long completedInGivenTimeCount=0L;

                    List<Integer> blockIds = new ArrayList<>();
                    for(Block block:blocks){
                         blockIds.add(block.getBlockId());

                    }
                    LOGGER.info("these are the block ids {}",blockIds);


                    LOGGER.info("Executing performance count query with params: fromDate={}, toDate={}, blockIds={}", fromDateTemp, toDate, blockIds);
                    List<Object[]> results = maPerformanceDao.getPerformanceCount(blockIds,  locationType, fromDateTemp,toDate);
                    LOGGER.info("Query result: {}", results);


                    for (Object[] result : results) {
                        MAPerformanceCountsDto blockPerformance = new MAPerformanceCountsDto();
                        if (result == null) continue;
                        Long blockId = ((BigInteger) result[0]).longValue();
                        Long blockCount1 = ((BigInteger) result[1]).longValue();
                        Long blockCount2 = ((BigInteger) result[2]).longValue();
                        Integer blockCount3 = ((BigInteger) result[3]).intValue();
                        Long blockCount4=((BigInteger) result[4]).longValue();
                        Long blockCount5=((BigInteger) result[5]).longValue();
                        Long blockCount6= ((BigInteger) result[6]).longValue();
                        Long blockCount7 = ((BigInteger) result[7]).longValue();
                        Long blockCount8 = ((BigInteger) result[8]).longValue();
                        Long blockCount9 = ((BigInteger) result[9]).longValue();
                        Long ashasStarted = ((BigInteger) result[10]).longValue();
                        Long ashasCompleted = ((BigInteger) result[11]).longValue();

                        blockPerformance.setAccessedAtleastOnce(blockCount1!= null ? blockCount1 : 0L);
                        blockPerformance.setAccessedNotOnce(blockCount2!= null ? blockCount2 : 0L);
                        blockPerformance.setAshasFailed(blockCount3 != null ? blockCount3 : 0);
                        blockPerformance.setAshasActivatedInBetween(blockCount4!= null ? blockCount4 : 0L);
                        blockPerformance.setAshasDeactivatedInBetween(blockCount5!= null ? blockCount5 : 0L);
                        blockPerformance.setAshasRefresherCourse(blockCount6!= null ? blockCount6 : 0L);
                        blockPerformance.setAshasCompletedInGivenTime(blockCount7!= null ? blockCount7 : 0L);
                        blockPerformance.setAshaDeactivatedStartedCourseInBetweenCount(blockCount8!=null?blockCount8:0L);
                        blockPerformance.setAshaDeactivatedCompletedCourseInBetweenCount(blockCount9!=null?blockCount9:0L);
                        blockPerformance.setAshasStarted(ashasStarted!=null?ashasStarted:0L);
                        blockPerformance.setAshasCompleted(ashasCompleted!=null?ashasCompleted:0L);
                        countMap.put(blockId,blockPerformance);
                        accessedCount+=blockCount1;
                        notAccessedCount+=blockCount2;
                        failedCount+=(blockCount3 != null ? blockCount3 : 0);;
                        activatedInBetweenCount+=blockCount4;
                        deactivatedInBetweenCount+=blockCount5;
                        refresherCourseCount+=blockCount6;
                        completedInGivenTimeCount+=blockCount7;
                    }
                    Long noBlockCount1 = 0L;
                    Long noBlockCount2 = 0L;
                    Integer noBlockCount3 = 0;
                    Long noBlockCount4 = 0L;
                    Long noBlockCount5 = 0L;
                    Long noBlockCount6 = 0L;
                    Long noBlockCount7 = 0L;
                    MAPerformanceCountsDto noBlockPerformance = new MAPerformanceCountsDto();
                    noBlockCount1= districtCounts1 - accessedCount;
                    noBlockCount2= districtCounts2 - notAccessedCount;
                    noBlockCount3= districtCounts3 - failedCount;
                    noBlockCount4=districtCounts4 - activatedInBetweenCount;
                    noBlockCount5=districtCounts5 - deactivatedInBetweenCount;
                    noBlockCount6 = districtCounts6- refresherCourseCount;
                    noBlockCount7 = districtCounts7- completedInGivenTimeCount;
                    noBlockPerformance.setAccessedAtleastOnce(noBlockCount1);
                    noBlockPerformance.setAccessedNotOnce(noBlockCount2);
                    noBlockPerformance.setAshasFailed(noBlockCount3);
                    noBlockPerformance.setAshasActivatedInBetween(noBlockCount4);
                    noBlockPerformance.setAshasDeactivatedInBetween(noBlockCount5);
                    noBlockPerformance.setAshasRefresherCourse(noBlockCount6);
                    noBlockPerformance.setAshasCompletedInGivenTime(noBlockCount7);
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
                    Long blockCounts4=maPerformanceDao.getAshaActivatedInBetween(locationId, "block", fromDateTemp, toDate);
                    Long blockCounts5=maPerformanceDao.getAshaDeactivatedInBetween(locationId, "block", fromDateTemp, toDate);
                    Long blockCounts6= maPerformanceDao.getAshaRefresherCourseInBetween(locationId,"block",fromDateTemp,toDate);
                    Long blockCounts7 = maPerformanceDao.getAshasCompletedInGivenTime(locationId,"block",fromDateTemp,toDate);

                    Long accessedCount = 0L;
                    Long notAccessedCount = 0L;
                    Integer failedCount = 0;
                    Long activatedInBetweenCount=0L;
                    Long deactivatedInBetweenCount=0L;
                    Long refresherCourseCount=0L;
                    Long completedInGivenTimeCount=0L;

                    List<Integer> healthSubFacilityIds = new ArrayList<>();
                    for (HealthSubFacility healthSubFacility : subcenters) {
                        healthSubFacilityIds.add(healthSubFacility.getHealthSubFacilityId());
                    }

                    LOGGER.info("Executing performance count query with params: fromDate={}, toDate={}, blockIds={}", fromDateTemp, toDate, healthSubFacilityIds);
                    List<Object[]> results = maPerformanceDao.getPerformanceCount(healthSubFacilityIds,locationType,fromDateTemp,toDate);
                    LOGGER.info("Query result: {}", results);
                    int cnt = 0;

                    for(Object[] result : results){
                        MAPerformanceCountsDto subcentrePerformance = new MAPerformanceCountsDto();
                        Long healthSubFacilityId = ((BigInteger)result[0]).longValue();
                        Long subcentreCount1 = ((BigInteger)result[1]).longValue();
                        Long subcentreCount2 = ((BigInteger)result[2]).longValue();
                        Integer subcentreCount3 = ((BigInteger)result[3]).intValue();
                        Long subcentreCount4=((BigInteger)result[4]).longValue();
                        Long subcentreCount5=((BigInteger)result[5]).longValue();
                        Long subcentreCount6= ((BigInteger)result[6]).longValue();
                        Long subcentreCount7 = ((BigInteger)result[7]).longValue();

                        subcentrePerformance.setAccessedAtleastOnce(subcentreCount1 != null ? subcentreCount1 : 0L);
                        subcentrePerformance.setAccessedNotOnce(subcentreCount2 != null ? subcentreCount2 : 0L);
                        subcentrePerformance.setAshasFailed(subcentreCount3 != null ? subcentreCount3 : 0);
                        subcentrePerformance.setAshasActivatedInBetween(subcentreCount4 != null ? subcentreCount4 : 0L);
                        subcentrePerformance.setAshasDeactivatedInBetween(subcentreCount5 != null ? subcentreCount5 : 0L);
                        subcentrePerformance.setAshasRefresherCourse(subcentreCount6 != null ? subcentreCount6 : 0L);
                        subcentrePerformance.setAshasCompletedInGivenTime(subcentreCount7 != null ? subcentreCount7 : 0L);
                        countMap.put(healthSubFacilityId,subcentrePerformance);
                        accessedCount+=subcentreCount1;
                        notAccessedCount+=subcentreCount2;
                        failedCount+=subcentreCount3;
                        activatedInBetweenCount+=subcentreCount4;
                        deactivatedInBetweenCount+=subcentreCount5;
                        refresherCourseCount+=subcentreCount6;
                        completedInGivenTimeCount+=subcentreCount7;
                        LOGGER.info("locationId:{}, Atleastonce:{}, Notonce:{}, Failed:{}, Activatedinbetween:{}, Deactivatedinbetween:{}, refreshercource:{}, completedingiventime:{}", healthSubFacilityId,subcentrePerformance.getAccessedAtleastOnce(), subcentrePerformance.getAccessedNotOnce(), subcentrePerformance.getAshasFailed(),subcentrePerformance.getAshasActivatedInBetween(), subcentrePerformance.getAshasDeactivatedInBetween(), subcentrePerformance.getAshasRefresherCourse(),subcentrePerformance.getAshasCompletedInGivenTime());
                        cnt++;
                    }
                    Long noSubcentreCount1 = 0L;
                    Long noSubcentreCount2 = 0L;
                    Integer noSubcentreCount3 = 0;
                    Long noSubcentreCount4 = 0L;
                    Long noSubcentreCount5 = 0L;
                    Long noSubcentreCount6 = 0L;
                    Long noSubcentreCount7 = 0L;
                    MAPerformanceCountsDto noSubcentrePerformance = new MAPerformanceCountsDto();
                    noSubcentreCount1= blockCounts1 - accessedCount;
                    noSubcentreCount2= blockCounts2 - notAccessedCount;
                    noSubcentreCount3= blockCounts3 - failedCount;
                    noSubcentreCount4= blockCounts4 - activatedInBetweenCount;
                    noSubcentreCount5= blockCounts5 - deactivatedInBetweenCount;
                    noSubcentreCount6= blockCounts6 - refresherCourseCount;
                    noSubcentreCount7= blockCounts7 - completedInGivenTimeCount;
                    noSubcentrePerformance.setAccessedAtleastOnce(noSubcentreCount1);
                    noSubcentrePerformance.setAccessedNotOnce(noSubcentreCount2);
                    noSubcentrePerformance.setAshasFailed(noSubcentreCount3);
                    noSubcentrePerformance.setAshasActivatedInBetween(noSubcentreCount4);
                    noSubcentrePerformance.setAshasDeactivatedInBetween(noSubcentreCount5);
                    noSubcentrePerformance.setAshasRefresherCourse(noSubcentreCount6);
                    noSubcentrePerformance.setAshasCompletedInGivenTime(noSubcentreCount7);
                    countMap.put((long)-locationId,noSubcentrePerformance);
                    cnt++;
                    LOGGER.info("count : {}", cnt);
                }
            }
        }
            return countMap;
    }
}
