package com.beehyv.nmsreporting.business.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.business.AggregateKilkariReportsService;
import com.beehyv.nmsreporting.business.BreadCrumbService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("aggregateKilkariReportsService")
@Transactional

public class AggregateKilkariReportsServiceImpl implements AggregateKilkariReportsService {

    @Autowired
    private UserDao userDao;

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

    @Autowired
    private AggregateKilkariReportsDao aggregateKilkariReportsDao;

    @Autowired
    private BreadCrumbService breadCrumbService;

    @Override
    public AggregateKilkariReportsDto getKilkariSubscriberCountReport(ReportRequest reportRequest){

        AggregateKilkariReportsDto aggregateKilkariReportsDto = new AggregateKilkariReportsDto();

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date toDate = new Date();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


        aCalendar.add(Calendar.DATE, -1);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        List<KilkariSubscriberDto> kilkariSubscriberDtoList = new ArrayList<>();
        List<KilkariSubscriber> kilkariSubscriberReportStart = new ArrayList<>();
        List<KilkariSubscriber> kilkariSubscriberReportEnd = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(0,"State",fromDate));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
                kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getStateId(),"District",fromDate));
                kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
                kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getDistrictId(),"Block",fromDate));
                kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getDistrictId(),"Block",toDate));
        } else {
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getBlockId(),"Subcenter",fromDate));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getBlockId(),"Subcenter",toDate));
        }

        for(int i = 0; i < kilkariSubscriberReportEnd.size(); i++){
            for(int j = 0; j < kilkariSubscriberReportStart.size(); j++)  {
                if(kilkariSubscriberReportEnd.get(i).getLocationId().equals(kilkariSubscriberReportStart.get(j).getLocationId())){
                    KilkariSubscriber end = kilkariSubscriberReportEnd.get(i);
                    KilkariSubscriber start = kilkariSubscriberReportStart.get(j);
                    KilkariSubscriberDto kilkariSubscriberDto = new KilkariSubscriberDto();
                    kilkariSubscriberDto.setId(end.getId());
                    kilkariSubscriberDto.setLocationId(end.getLocationId());
                    kilkariSubscriberDto.setTotalSubscriptions(end.getTotalSubscriptions() - start.getTotalSubscriptions());
                    kilkariSubscriberDto.setTotalBeneficiaryRecordsReceived(end.getTotalBeneficiaryRecordsReceived() - start.getTotalBeneficiaryRecordsReceived());
                    kilkariSubscriberDto.setTotalBeneficiaryRecordsEligible(end.getTotalBeneficiaryRecordsEligible()- start.getTotalBeneficiaryRecordsEligible());
                    kilkariSubscriberDto.setTotalBeneficiaryRecordsAccepted(end.getTotalBeneficiaryRecordsAccepted()- start.getTotalBeneficiaryRecordsAccepted());
                    kilkariSubscriberDto.setTotalBeneficiaryRecordsRejected(end.getTotalBeneficiaryRecordsRejected()- start.getTotalBeneficiaryRecordsRejected());
                    kilkariSubscriberDto.setTotalSubscriptionsCompleted(end.getTotalSubscriptionsCompleted()- start.getTotalSubscriptionsCompleted());
                    kilkariSubscriberDto.setLocationType(end.getLocationType());
                    String locationType = end.getLocationType();
                    if(locationType.equalsIgnoreCase("State")){
                        kilkariSubscriberDto.setLocationName(stateDao.findByStateId(end.getLocationId().intValue()).getStateName());
                    }
                    if(locationType.equalsIgnoreCase("District")){
                        kilkariSubscriberDto.setLocationName(districtDao.findByDistrictId(end.getLocationId().intValue()).getDistrictName());
                    }
                    if(locationType.equalsIgnoreCase("Block")){
                        kilkariSubscriberDto.setLocationName(blockDao.findByblockId(end.getLocationId().intValue()).getBlockName());
                    }
                    if(locationType.equalsIgnoreCase("Subcenter")){
                        kilkariSubscriberDto.setLocationName(subcenterDao.findBySubcenterId(end.getLocationId().intValue()).getSubcenterName());
                    }
                    if (locationType.equalsIgnoreCase("DifferenceState")) {
                        kilkariSubscriberDto.setLocationName("No District Count");
                        kilkariSubscriberDto.setLocationId((long)-1);
                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        kilkariSubscriberDto.setLocationName("No Block Count");
                        kilkariSubscriberDto.setLocationId((long)-1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        kilkariSubscriberDto.setLocationName("No Subcenter Count");
                        kilkariSubscriberDto.setLocationId((long)-1);

                    }

                    if((kilkariSubscriberDto.getTotalSubscriptions() + kilkariSubscriberDto.getTotalBeneficiaryRecordsReceived()
                            + kilkariSubscriberDto.getTotalBeneficiaryRecordsEligible() + kilkariSubscriberDto.getTotalBeneficiaryRecordsAccepted()
                            + kilkariSubscriberDto.getTotalBeneficiaryRecordsRejected() + kilkariSubscriberDto.getTotalSubscriptionsCompleted()) != 0){
                        kilkariSubscriberDtoList.add(kilkariSubscriberDto);
                    }
                }
            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariSubscriberDtoList);
        return aggregateKilkariReportsDto;
    }

    private List<KilkariSubscriber> getKilkariSubscriberCount(Integer locationId, String locationType, Date date){

        List<KilkariSubscriber> kilkariSubscribersCountList = new ArrayList<>();

        if(locationType.equalsIgnoreCase("State")) {
            List<State> states = stateDao.getStatesByServiceType("K");
            for(State state:states){
                kilkariSubscribersCountList.add(aggregateKilkariReportsDao.getKilkariSubscriberCounts(state.getStateId(),locationType, date));
            }

        } else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                KilkariSubscriber kilkariStateCounts = aggregateKilkariReportsDao.getKilkariSubscriberCounts(locationId,locationType, date);
                Integer totalSubscriptions = 0;
                Integer totalBeneficiaryRecordsReceived = 0;
                Integer totalBeneficiaryRecordsEligible = 0;
                Integer totalBeneficiaryRecordsAccepted = 0;
                Integer totalBeneficiaryRecordsRejected = 0;
                Integer totalSubscriptionsCompleted = 0;
                for(District district:districts){
                    KilkariSubscriber kilkariDistrictCount = aggregateKilkariReportsDao.getKilkariSubscriberCounts(district.getDistrictId(),locationType, date);
                    kilkariSubscribersCountList.add(kilkariDistrictCount);
                    totalSubscriptions += kilkariDistrictCount.getTotalSubscriptions();
                    totalBeneficiaryRecordsReceived += kilkariDistrictCount.getTotalBeneficiaryRecordsReceived();
                    totalBeneficiaryRecordsEligible += kilkariDistrictCount.getTotalBeneficiaryRecordsEligible();
                    totalBeneficiaryRecordsAccepted += kilkariDistrictCount.getTotalBeneficiaryRecordsAccepted();
                    totalBeneficiaryRecordsRejected += kilkariDistrictCount.getTotalBeneficiaryRecordsRejected();
                    totalSubscriptionsCompleted += kilkariDistrictCount.getTotalSubscriptionsCompleted();
                }
                KilkariSubscriber kilkariNoDistrictCount = new KilkariSubscriber();
                kilkariNoDistrictCount.setTotalSubscriptions(kilkariStateCounts.getTotalSubscriptions()- totalSubscriptions);
                kilkariNoDistrictCount.setTotalBeneficiaryRecordsReceived(kilkariStateCounts.getTotalBeneficiaryRecordsReceived()- totalBeneficiaryRecordsReceived);
                kilkariNoDistrictCount.setTotalBeneficiaryRecordsEligible(kilkariStateCounts.getTotalBeneficiaryRecordsEligible()- totalBeneficiaryRecordsEligible);
                kilkariNoDistrictCount.setTotalBeneficiaryRecordsAccepted(kilkariStateCounts.getTotalBeneficiaryRecordsAccepted()- totalBeneficiaryRecordsAccepted);
                kilkariNoDistrictCount.setTotalBeneficiaryRecordsRejected(kilkariStateCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                kilkariNoDistrictCount.setTotalSubscriptionsCompleted(kilkariStateCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                kilkariNoDistrictCount.setLocationType("DifferenceState");
                kilkariNoDistrictCount.setLocationId((long)-locationId);
                kilkariSubscribersCountList.add(kilkariNoDistrictCount);
            } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    KilkariSubscriber kilkariDistrictCounts = aggregateKilkariReportsDao.getKilkariSubscriberCounts(locationId,locationType, date);
                    Integer totalSubscriptions = 0;
                    Integer totalBeneficiaryRecordsReceived = 0;
                    Integer totalBeneficiaryRecordsEligible = 0;
                    Integer totalBeneficiaryRecordsAccepted = 0;
                    Integer totalBeneficiaryRecordsRejected = 0;
                    Integer totalSubscriptionsCompleted = 0;
                    for (Block block : blocks) {
                        KilkariSubscriber kilkariBlockCount = aggregateKilkariReportsDao.getKilkariSubscriberCounts(block.getBlockId(),locationType, date);
                        kilkariSubscribersCountList.add(kilkariBlockCount);
                        totalSubscriptions += kilkariBlockCount.getTotalSubscriptions();
                        totalBeneficiaryRecordsReceived += kilkariBlockCount.getTotalBeneficiaryRecordsReceived();
                        totalBeneficiaryRecordsEligible += kilkariBlockCount.getTotalBeneficiaryRecordsEligible();
                        totalBeneficiaryRecordsAccepted += kilkariBlockCount.getTotalBeneficiaryRecordsAccepted();
                        totalBeneficiaryRecordsRejected += kilkariBlockCount.getTotalBeneficiaryRecordsRejected();
                        totalSubscriptionsCompleted += kilkariBlockCount.getTotalSubscriptionsCompleted();
                    }
                    KilkariSubscriber kilkariNoBlockCount = new KilkariSubscriber();
                    kilkariNoBlockCount.setTotalSubscriptions(kilkariDistrictCounts.getTotalSubscriptions()- totalSubscriptions);
                    kilkariNoBlockCount.setTotalBeneficiaryRecordsReceived(kilkariDistrictCounts.getTotalBeneficiaryRecordsReceived()- totalBeneficiaryRecordsReceived);
                    kilkariNoBlockCount.setTotalBeneficiaryRecordsEligible(kilkariDistrictCounts.getTotalBeneficiaryRecordsEligible()- totalBeneficiaryRecordsEligible);
                    kilkariNoBlockCount.setTotalBeneficiaryRecordsAccepted(kilkariDistrictCounts.getTotalBeneficiaryRecordsAccepted()- totalBeneficiaryRecordsAccepted);
                    kilkariNoBlockCount.setTotalBeneficiaryRecordsRejected(kilkariDistrictCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                    kilkariNoBlockCount.setTotalSubscriptionsCompleted(kilkariDistrictCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                    kilkariNoBlockCount.setLocationType("DifferenceDistrict");
                    kilkariNoBlockCount.setLocationId((long)-locationId);
                    kilkariSubscribersCountList.add(kilkariNoBlockCount);
                } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    KilkariSubscriber blockCounts = aggregateKilkariReportsDao.getKilkariSubscriberCounts(locationId, "block", date);
                    Integer totalSubscriptions = 0;
                    Integer totalBeneficiaryRecordsReceived = 0;
                    Integer totalBeneficiaryRecordsEligible = 0;
                    Integer totalBeneficiaryRecordsAccepted = 0;
                    Integer totalBeneficiaryRecordsRejected = 0;
                    Integer totalSubscriptionsCompleted = 0;
                    for(Subcenter subcenter: subcenters){
                        KilkariSubscriber kilkariSubcenterCount = aggregateKilkariReportsDao.getKilkariSubscriberCounts(subcenter.getSubcenterId(),locationType, date);
                        kilkariSubscribersCountList.add(kilkariSubcenterCount);
                        totalSubscriptions += kilkariSubcenterCount.getTotalSubscriptions();
                        totalBeneficiaryRecordsReceived += kilkariSubcenterCount.getTotalBeneficiaryRecordsReceived();
                        totalBeneficiaryRecordsEligible += kilkariSubcenterCount.getTotalBeneficiaryRecordsEligible();
                        totalBeneficiaryRecordsAccepted += kilkariSubcenterCount.getTotalBeneficiaryRecordsAccepted();
                        totalBeneficiaryRecordsRejected += kilkariSubcenterCount.getTotalBeneficiaryRecordsRejected();
                        totalSubscriptionsCompleted += kilkariSubcenterCount.getTotalSubscriptionsCompleted();
                    }
                    KilkariSubscriber kilkariNoSubcenterCount = new KilkariSubscriber();
                    kilkariNoSubcenterCount.setTotalSubscriptions(blockCounts.getTotalSubscriptions()- totalSubscriptions);
                    kilkariNoSubcenterCount.setTotalBeneficiaryRecordsReceived(blockCounts.getTotalBeneficiaryRecordsReceived()- totalBeneficiaryRecordsReceived);
                    kilkariNoSubcenterCount.setTotalBeneficiaryRecordsEligible(blockCounts.getTotalBeneficiaryRecordsEligible()- totalBeneficiaryRecordsEligible);
                    kilkariNoSubcenterCount.setTotalBeneficiaryRecordsAccepted(blockCounts.getTotalBeneficiaryRecordsAccepted()- totalBeneficiaryRecordsAccepted);
                    kilkariNoSubcenterCount.setTotalBeneficiaryRecordsRejected(blockCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                    kilkariNoSubcenterCount.setTotalSubscriptionsCompleted(blockCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                    kilkariNoSubcenterCount.setLocationType("DifferenceBlock");
                    kilkariNoSubcenterCount.setLocationId((long)-locationId);
                    kilkariSubscribersCountList.add(kilkariNoSubcenterCount);
                }
        return kilkariSubscribersCountList;
    }

}
