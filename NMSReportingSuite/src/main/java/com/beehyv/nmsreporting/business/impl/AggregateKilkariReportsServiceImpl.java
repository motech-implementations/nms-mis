package com.beehyv.nmsreporting.business.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.business.AggregateKilkariReportsService;
import com.beehyv.nmsreporting.business.BreadCrumbService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
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
    private AggregateCumulativekilkariDao aggregateCumulativekilkariDao;

    @Autowired
    private KilkariSubscriberReportDao kilkariSubscriberReportDao;

    @Autowired
    private AggregateCumulativeBeneficiaryDao aggregateCumulativeBeneficiaryDao;

    @Autowired
    private KilkariUsageDao kilkariUsageDao;

    @Autowired
    private KilkariMessageListenershipReportDao kilkariMessageListenershipReportDao;

    @Autowired
    private AggCumulativeBeneficiaryComplDao aggCumulativeBeneficiaryComplDao;

    @Autowired
    private ListeningMatrixDao listeningMatrixReportDao;

    @Autowired
    private KilkariThematicContentReportDao kilkariThematicContentReportDao;

    @Autowired
    private MessageMatrixDao messageMatrixDao;

    @Autowired
    private KilkariRepeatListenerMonthWiseDao kilkariRepeatListenerMonthWiseDao;

    @Autowired
    private KilkariCallReportDao kilkariCallReportDao;

    @Autowired
    private BreadCrumbService breadCrumbService;


    /*----------5.3.1. Kilkari Cumulative Summary Report -------*/

    @Override
    public List<AggregateCumulativeKilkariDto> getKilkariCumulativeSummary(ReportRequest reportRequest, User currentUser){

//            List<Map<String,String>> summaryReport = new ArrayList<>();
//            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(reportRequest.getToDate().getTime());
//            String toDateString = formatter.format(calendar.getTime());
//            Date toDate = new Date();
//            try {
//                toDate = formatter.parse(toDateString);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date toDate = new Date();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//        aCalendar.add(Calendar.MONTH, -1);

        toDate = aCalendar.getTime();
        List<AggregateCumulativeKilkariDto> summaryDto = new ArrayList<>();
        List<AggregateCumulativeKilkari> cumulativeSummaryReport = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
                cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
                cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getDistrictId(),"Block",toDate));
        } else {
                cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getBlockId(),"Subcenter",toDate));
        }


        for(AggregateCumulativeKilkari a:cumulativeSummaryReport){
            AggregateCumulativeKilkariDto summaryDto1 = new AggregateCumulativeKilkariDto();
            summaryDto1.setId(a.getId());
            summaryDto1.setLocationId(a.getLocationId());
            summaryDto1.setUniqueBeneficiaries(a.getUniqueBeneficiaries());
            summaryDto1.setSuccessfulCalls(a.getSuccessfulCalls());
            summaryDto1.setBillableMinutes(a.getBillableMinutes());
            summaryDto1.setLocationType(a.getLocationType());
            summaryDto1.setAverageDuration(a.getSuccessfulCalls() == 0 ? 0 : a.getBillableMinutes()/a.getSuccessfulCalls());
            String locationType = a.getLocationType();
            if(locationType.equalsIgnoreCase("State")){
                summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId()).getStateName());
            }
            if(locationType.equalsIgnoreCase("District")){
                summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId()).getDistrictName());
            }
            if(locationType.equalsIgnoreCase("Block")){
                summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId()).getBlockName());
            }
            if(locationType.equalsIgnoreCase("Subcenter")){
                summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId()).getSubcenterName());
            }
            if (locationType.equalsIgnoreCase("DifferenceState")) {
                summaryDto1.setLocationName("No District");
            }
            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                summaryDto1.setLocationName("No Block");
            }
            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                summaryDto1.setLocationName("No Subcenter");
            }

            if(a.getId() != 0){
                summaryDto.add(summaryDto1);
            }

        }
        return summaryDto;
    }

    private List<AggregateCumulativeKilkari> getCumulativeSummaryKilkariReport(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeKilkari> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                CumulativeSummary.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(s.getStateId(),locationType,toDate));
            }
        }
        else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeKilkari stateCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"State",toDate);
                Integer uniqueBeneficiaries = 0;
                Integer successfulCalls = 0;
                Integer billableMinutes = 0;
                for(District d:districts){
                    AggregateCumulativeKilkari distrcitCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getDistrictId(),locationType,toDate);
                    CumulativeSummary.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getDistrictId(),locationType,toDate));
                    uniqueBeneficiaries += distrcitCount.getUniqueBeneficiaries();
                    successfulCalls += distrcitCount.getSuccessfulCalls();
                    billableMinutes += distrcitCount.getBillableMinutes();
                }
                AggregateCumulativeKilkari noDistrictCount = new AggregateCumulativeKilkari();
                noDistrictCount.setUniqueBeneficiaries(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
                noDistrictCount.setSuccessfulCalls(stateCounts.getSuccessfulCalls()-successfulCalls);
                noDistrictCount.setBillableMinutes(stateCounts.getBillableMinutes()-billableMinutes);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+stateCounts.getSuccessfulCalls()-successfulCalls+stateCounts.getBillableMinutes()-billableMinutes));
                noDistrictCount.setLocationId((-1));
                CumulativeSummary.add(noDistrictCount);
        } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeKilkari districtCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"District",toDate);
                    Integer uniqueBeneficiaries = 0;
                    Integer successfulCalls = 0;
                    Integer billableMinutes = 0;
                    for (Block d : blocks) {
                        AggregateCumulativeKilkari blockCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getBlockId(),locationType,toDate);
                        CumulativeSummary.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getBlockId(), locationType,toDate));
                        uniqueBeneficiaries+=blockCount.getUniqueBeneficiaries();
                        successfulCalls+=blockCount.getSuccessfulCalls();
                        billableMinutes+=blockCount.getBillableMinutes();
                    }
                    AggregateCumulativeKilkari noBlockCount = new AggregateCumulativeKilkari();
                    noBlockCount.setUniqueBeneficiaries(districtCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
                    noBlockCount.setSuccessfulCalls(districtCounts.getSuccessfulCalls()-successfulCalls);
                    noBlockCount.setBillableMinutes(districtCounts.getBillableMinutes()-billableMinutes);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(districtCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+districtCounts.getSuccessfulCalls()-successfulCalls+districtCounts.getBillableMinutes()-billableMinutes));
                    noBlockCount.setLocationId(-1);
                    CumulativeSummary.add(noBlockCount);
        } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeKilkari blockCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"block",toDate);
                    Integer uniqueBeneficiaries = 0;
                    Integer successfulCalls = 0;
                    Integer billableMinutes = 0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeKilkari SubcenterCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(s.getSubcenterId(),locationType,toDate);
                        CumulativeSummary.add(SubcenterCount);
                        uniqueBeneficiaries+=SubcenterCount.getUniqueBeneficiaries();
                        successfulCalls+=SubcenterCount.getSuccessfulCalls();
                        billableMinutes+=SubcenterCount.getBillableMinutes();
                    }
                    AggregateCumulativeKilkari noSubcenterCount = new AggregateCumulativeKilkari();
                    noSubcenterCount.setUniqueBeneficiaries(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
                    noSubcenterCount.setSuccessfulCalls(blockCounts.getSuccessfulCalls()-successfulCalls);
                    noSubcenterCount.setBillableMinutes(blockCounts.getBillableMinutes()-billableMinutes);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId((int)(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+blockCounts.getSuccessfulCalls()-successfulCalls+blockCounts.getBillableMinutes()-billableMinutes));
                    noSubcenterCount.setLocationId((-1));
                    CumulativeSummary.add(noSubcenterCount);
                }
        return CumulativeSummary;
    }

    /*----------5.3.2. Kilkari Subscriber Count Report -------*/

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

        if(!(kilkariSubscriberReportEnd.isEmpty()) && !(kilkariSubscriberReportStart.isEmpty())){
            for(int i = 0; i < kilkariSubscriberReportEnd.size(); i++){
                for(int j = 0; j < kilkariSubscriberReportStart.size(); j++)  {
                    if(kilkariSubscriberReportEnd.get(i).getLocationId().equals(kilkariSubscriberReportStart.get(j).getLocationId())){
                        KilkariSubscriber end = kilkariSubscriberReportEnd.get(i);
                        KilkariSubscriber start = kilkariSubscriberReportStart.get(j);
                        KilkariSubscriberDto kilkariSubscriberDto = new KilkariSubscriberDto();
                        kilkariSubscriberDto.setId(end.getId());
                        kilkariSubscriberDto.setLocationId(end.getLocationId());
                        kilkariSubscriberDto.setTotalSubscriptionsStart(start.getTotalSubscriptions());
                        kilkariSubscriberDto.setTotalSubscriptionsEnd(end.getTotalSubscriptions());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsAccepted(end.getTotalSubscriptions()- start.getTotalSubscriptions());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsEligible(end.getRecordsRejectedButEligible()  - start.getRecordsRejectedButEligible() + kilkariSubscriberDto.getTotalBeneficiaryRecordsAccepted());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsReceived(kilkariSubscriberDto.getTotalBeneficiaryRecordsEligible() + end.getTotalBeneficiaryRecordsRejected() - start.getTotalBeneficiaryRecordsRejected());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsRejectedDuplicateMobileNumbers(end.getRecordsRejectedDuplicateMobileNumbers()- start.getRecordsRejectedDuplicateMobileNumbers());
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

                        if((kilkariSubscriberDto.getTotalSubscriptionsEnd() + kilkariSubscriberDto.getTotalSubscriptionsStart() + kilkariSubscriberDto.getTotalBeneficiaryRecordsReceived()
                                + kilkariSubscriberDto.getTotalBeneficiaryRecordsEligible() + kilkariSubscriberDto.getTotalBeneficiaryRecordsAccepted()
                                + kilkariSubscriberDto.getTotalBeneficiaryRecordsRejectedDuplicateMobileNumbers() + kilkariSubscriberDto.getTotalSubscriptionsCompleted()) != 0){
                            kilkariSubscriberDtoList.add(kilkariSubscriberDto);
                        }
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
                kilkariSubscribersCountList.add(kilkariSubscriberReportDao.getKilkariSubscriberCounts(state.getStateId(),locationType, date));
            }

        } else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                KilkariSubscriber kilkariStateCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId,locationType, date);
                Integer totalSubscriptions = 0;
                Integer totalBeneficiaryRecordsRejected = 0;
                Integer recordsRejectedDuplicateMobileNumbers = 0;
                Integer totalSubscriptionsCompleted = 0;
                Integer recordsRejectedButEligible = 0;
                for(District district:districts){
                    KilkariSubscriber kilkariDistrictCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(district.getDistrictId(),locationType, date);
                    kilkariSubscribersCountList.add(kilkariDistrictCount);
                    totalSubscriptions += kilkariDistrictCount.getTotalSubscriptions();
                    totalBeneficiaryRecordsRejected += kilkariDistrictCount.getTotalBeneficiaryRecordsRejected();
                    recordsRejectedDuplicateMobileNumbers += kilkariDistrictCount.getRecordsRejectedDuplicateMobileNumbers();
                    totalSubscriptionsCompleted += kilkariDistrictCount.getTotalSubscriptionsCompleted();
                    recordsRejectedButEligible += kilkariDistrictCount.getTotalBeneficiaryRecordsRejected();
                }
                KilkariSubscriber kilkariNoDistrictCount = new KilkariSubscriber();
                kilkariNoDistrictCount.setTotalSubscriptions(kilkariStateCounts.getTotalSubscriptions()- totalSubscriptions);
                kilkariNoDistrictCount.setTotalBeneficiaryRecordsRejected(kilkariStateCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                kilkariNoDistrictCount.setRecordsRejectedDuplicateMobileNumbers(kilkariStateCounts.getRecordsRejectedDuplicateMobileNumbers()- recordsRejectedDuplicateMobileNumbers);
                kilkariNoDistrictCount.setTotalSubscriptionsCompleted(kilkariStateCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                kilkariNoDistrictCount.setRecordsRejectedButEligible(kilkariStateCounts.getRecordsRejectedButEligible()- recordsRejectedButEligible);
                kilkariNoDistrictCount.setLocationType("DifferenceState");
                kilkariNoDistrictCount.setLocationId((long)-locationId);
                kilkariSubscribersCountList.add(kilkariNoDistrictCount);
            } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    KilkariSubscriber kilkariDistrictCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId,locationType, date);
                    Integer totalSubscriptions = 0;
                    Integer totalBeneficiaryRecordsRejected = 0;
                    Integer recordsRejectedDuplicateMobileNumbers = 0;
                    Integer totalSubscriptionsCompleted = 0;
                    Integer recordsRejectedButEligible = 0;
                    for (Block block : blocks) {
                        KilkariSubscriber kilkariBlockCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(block.getBlockId(),locationType, date);
                        kilkariSubscribersCountList.add(kilkariBlockCount);
                        totalSubscriptions += kilkariBlockCount.getTotalSubscriptions();
                        totalBeneficiaryRecordsRejected += kilkariBlockCount.getTotalBeneficiaryRecordsRejected();
                        recordsRejectedDuplicateMobileNumbers += kilkariBlockCount.getRecordsRejectedDuplicateMobileNumbers();
                        totalSubscriptionsCompleted += kilkariBlockCount.getTotalSubscriptionsCompleted();
                        recordsRejectedButEligible += kilkariBlockCount.getTotalBeneficiaryRecordsRejected();
                    }
                    KilkariSubscriber kilkariNoBlockCount = new KilkariSubscriber();
                    kilkariNoBlockCount.setTotalSubscriptions(kilkariDistrictCounts.getTotalSubscriptions()- totalSubscriptions);
                    kilkariNoBlockCount.setTotalBeneficiaryRecordsRejected(kilkariDistrictCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                    kilkariNoBlockCount.setRecordsRejectedDuplicateMobileNumbers(kilkariDistrictCounts.getRecordsRejectedDuplicateMobileNumbers()- recordsRejectedDuplicateMobileNumbers);
                    kilkariNoBlockCount.setTotalSubscriptionsCompleted(kilkariDistrictCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                    kilkariNoBlockCount.setRecordsRejectedButEligible(kilkariDistrictCounts.getRecordsRejectedButEligible()- recordsRejectedButEligible);
                    kilkariNoBlockCount.setLocationType("DifferenceDistrict");
                    kilkariNoBlockCount.setLocationId((long)-locationId);
                    kilkariSubscribersCountList.add(kilkariNoBlockCount);
                } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    KilkariSubscriber blockCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId, "block", date);
                    Integer totalSubscriptions = 0;
                    Integer totalBeneficiaryRecordsRejected = 0;
                    Integer recordsRejectedDuplicateMobileNumbers = 0;
                    Integer totalSubscriptionsCompleted = 0;
                    Integer recordsRejectedButEligible = 0;
                    for(Subcenter Subcenter : subcenters){
                        KilkariSubscriber kilkariSubcenterCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(Subcenter.getSubcenterId(),locationType, date);
                        kilkariSubscribersCountList.add(kilkariSubcenterCount);
                        totalSubscriptions += kilkariSubcenterCount.getTotalSubscriptions();
                        totalBeneficiaryRecordsRejected += kilkariSubcenterCount.getTotalBeneficiaryRecordsRejected();
                        recordsRejectedDuplicateMobileNumbers += kilkariSubcenterCount.getRecordsRejectedDuplicateMobileNumbers();
                        totalSubscriptionsCompleted += kilkariSubcenterCount.getTotalSubscriptionsCompleted();
                        recordsRejectedButEligible += kilkariSubcenterCount.getTotalBeneficiaryRecordsRejected();
                    }
                    KilkariSubscriber kilkariNoSubcenterCount = new KilkariSubscriber();
                    kilkariNoSubcenterCount.setTotalSubscriptions(blockCounts.getTotalSubscriptions()- totalSubscriptions);
                    kilkariNoSubcenterCount.setTotalBeneficiaryRecordsRejected(blockCounts.getTotalBeneficiaryRecordsRejected()- totalBeneficiaryRecordsRejected);
                    kilkariNoSubcenterCount.setRecordsRejectedDuplicateMobileNumbers(blockCounts.getRecordsRejectedDuplicateMobileNumbers()- recordsRejectedDuplicateMobileNumbers);
                    kilkariNoSubcenterCount.setTotalSubscriptionsCompleted(blockCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
                    kilkariNoSubcenterCount.setRecordsRejectedButEligible(blockCounts.getRecordsRejectedButEligible()- recordsRejectedButEligible);
                    kilkariNoSubcenterCount.setLocationType("DifferenceBlock");
                    kilkariNoSubcenterCount.setLocationId((long)-locationId);
                    kilkariSubscribersCountList.add(kilkariNoSubcenterCount);
                }
        return kilkariSubscribersCountList;
    }

    /*----------5.3.3. Kilkari Aggregate Beneficiaries Report -------*/

    @Override
    public  List<KilkariAggregateBeneficiariesDto> getBeneficiaryReport(ReportRequest reportRequest, User currentUser) {
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


        List<KilkariAggregateBeneficiariesDto> summaryDto = new ArrayList<>();
        List<KilkariAggregateBeneficiariesDto> cumulativeBeneficiaryReportStart = new ArrayList<>();
        List<KilkariAggregateBeneficiariesDto> cumulativeBeneficiaryReportEnd = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(0, "State", fromDate));
            cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(0, "State", toDate));
        } else if (reportRequest.getDistrictId() == 0) {
                cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getStateId(), "District", fromDate));
                cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getStateId(), "District", toDate));
        } else if (reportRequest.getBlockId() == 0) {
                cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getDistrictId(), "Block", fromDate));
                cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getDistrictId(), "Block", toDate));
        } else {
                cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getBlockId(), "Subcenter", fromDate));
                cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getBlockId(), "Subcenter", toDate));
        }


        if(!(cumulativeBeneficiaryReportEnd.isEmpty()) && !(cumulativeBeneficiaryReportStart.isEmpty())){
            for (int i = 0; i < cumulativeBeneficiaryReportEnd.size(); i++) {
                for (int j = 0; j < cumulativeBeneficiaryReportStart.size(); j++) {
                    if (cumulativeBeneficiaryReportEnd.get(i).getLocationId().equals(cumulativeBeneficiaryReportStart.get(j).getLocationId())) {
                        KilkariAggregateBeneficiariesDto a = cumulativeBeneficiaryReportEnd.get(i);
                        KilkariAggregateBeneficiariesDto b = cumulativeBeneficiaryReportStart.get(j);
                        KilkariAggregateBeneficiariesDto summaryDto1 = new KilkariAggregateBeneficiariesDto();
                        summaryDto1.setLocationId(a.getLocationId());
                        summaryDto1.setSelfDeactivated(a.getSelfDeactivated() - b.getSelfDeactivated());
                        summaryDto1.setJoinedSubscription(a.getJoinedSubscription() - b.getJoinedSubscription());
                        summaryDto1.setCalledInbox(a.getCalledInbox() - b.getCalledInbox());
                        summaryDto1.setMotherCompletion(a.getMotherCompletion() - b.getMotherCompletion());
                        summaryDto1.setChildCompletion(a.getChildCompletion() - b.getChildCompletion());
                        summaryDto1.setLowListenership(a.getLowListenership() - b.getLowListenership());
                        summaryDto1.setNotAnswering(a.getNotAnswering() - b.getNotAnswering());
                        summaryDto1.setSystemDeactivation(a.getSystemDeactivation() - b.getSystemDeactivation());
                        summaryDto1.setBeneficiariesCalled(a.getBeneficiariesCalled() - b.getBeneficiariesCalled());
                        summaryDto1.setAnsweredAtleastOneCall(a.getAnsweredAtleastOneCall() - b.getAnsweredAtleastOneCall());
                        summaryDto1.setLocationType(a.getLocationType());
                        String locationType = a.getLocationType();
                        if (locationType.equalsIgnoreCase("State")) {
                            summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                        }
                        if (locationType.equalsIgnoreCase("District")) {
                            summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                        }
                        if (locationType.equalsIgnoreCase("Block")) {
                            summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                        }
                        if (locationType.equalsIgnoreCase("Subcenter")) {
                            summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                        }
                        if (locationType.equalsIgnoreCase("DifferenceState")) {
                            summaryDto1.setLocationName("No District");
                        }
                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                            summaryDto1.setLocationName("No Block");
                        }
                        if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                            summaryDto1.setLocationName("No Subcenter");
                        }
                        if (a.getId() != 0) {
                            summaryDto.add(summaryDto1);
                        }
                    }
                }
            }
        }
        return summaryDto;
    }

    private List<KilkariAggregateBeneficiariesDto> getCumulativeBeneficiary(Integer locationId, String locationType, Date toDate){
        List<KilkariAggregateBeneficiariesDto> CumulativeBeneficiary = new ArrayList<>();
        KilkariAggregateBeneficiariesDto kilkariAggregateBeneficiariesDto = new KilkariAggregateBeneficiariesDto();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states = stateDao.getStatesByServiceType("K");
            for(State s:states){
                AggregateCumulativeBeneficiary stateCounts = (aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)s.getStateId(),locationType,toDate));
                kilkariAggregateBeneficiariesDto.setCalledInbox(stateCounts.getCalledInbox());
                kilkariAggregateBeneficiariesDto.setId(stateCounts.getId());
                kilkariAggregateBeneficiariesDto.setChildCompletion(stateCounts.getChildCompletion());
                kilkariAggregateBeneficiariesDto.setLocationId(stateCounts.getLocationId());
                kilkariAggregateBeneficiariesDto.setMotherCompletion(stateCounts.getMotherCompletion());
                kilkariAggregateBeneficiariesDto.setLocationType(stateCounts.getLocationType());
                kilkariAggregateBeneficiariesDto.setJoinedSubscription(stateCounts.getJoinedSubscription());
                kilkariAggregateBeneficiariesDto.setLowListenership(stateCounts.getLowListenership());
                kilkariAggregateBeneficiariesDto.setNotAnswering(stateCounts.getNotAnswering());
                kilkariAggregateBeneficiariesDto.setSelfDeactivated(stateCounts.getSelfDeactivated());
                kilkariAggregateBeneficiariesDto.setSystemDeactivation(stateCounts.getSystemDeactivation());
                kilkariAggregateBeneficiariesDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)s.getStateId(),locationType,toDate));
                kilkariAggregateBeneficiariesDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)s.getStateId(),locationType,toDate));
                CumulativeBeneficiary.add(kilkariAggregateBeneficiariesDto);
            }
        } else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeBeneficiary stateCounts = (aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,locationType,toDate));
                KilkariAggregateBeneficiariesDto stateDto = new KilkariAggregateBeneficiariesDto();
                stateDto.setCalledInbox(stateCounts.getCalledInbox());
                stateDto.setId(stateCounts.getId());
                stateDto.setChildCompletion(stateCounts.getChildCompletion());
                stateDto.setLocationId(stateCounts.getLocationId());
                stateDto.setMotherCompletion(stateCounts.getMotherCompletion());
                stateDto.setLocationType(stateCounts.getLocationType());
                stateDto.setJoinedSubscription(stateCounts.getJoinedSubscription());
                stateDto.setLowListenership(stateCounts.getLowListenership());
                stateDto.setNotAnswering(stateCounts.getNotAnswering());
                stateDto.setSelfDeactivated(stateCounts.getSelfDeactivated());
                stateDto.setSystemDeactivation(stateCounts.getSystemDeactivation());
                stateDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)locationId,locationType,toDate));
                stateDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)locationId,locationType,toDate));
                Long beneficiariesCalled = (long)0;
                Long beneficiariesAnsweredAtleastOnce = (long)0;
                Long selfDeactivated = (long)0;
                Long notAnswering = (long)0;
                Long lowListenership = (long)0;
                Long systemDeactivation = (long)0;
                Long motherCompletion = (long)0;
                Long childCompletion = (long)0;
                Long calledInbox = (long)0;
                Long joinedSubscription = (long)0;
                for(District d:districts){
                    AggregateCumulativeBeneficiary districtCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)d.getDistrictId(),locationType,toDate);
                    KilkariAggregateBeneficiariesDto districtDto = new KilkariAggregateBeneficiariesDto();
                    districtDto.setCalledInbox(districtCount.getCalledInbox());
                    districtDto.setId(districtCount.getId());
                    districtDto.setChildCompletion(districtCount.getChildCompletion());
                    districtDto.setLocationId(districtCount.getLocationId());
                    districtDto.setMotherCompletion(districtCount.getMotherCompletion());
                    districtDto.setLocationType(districtCount.getLocationType());
                    districtDto.setJoinedSubscription(districtCount.getJoinedSubscription());
                    districtDto.setLowListenership(districtCount.getLowListenership());
                    districtDto.setNotAnswering(districtCount.getNotAnswering());
                    districtDto.setSelfDeactivated(districtCount.getSelfDeactivated());
                    districtDto.setSystemDeactivation(districtCount.getSystemDeactivation());
                    districtDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)d.getDistrictId(),locationType,toDate));
                    districtDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)d.getDistrictId(),locationType,toDate));
                    CumulativeBeneficiary.add(districtDto);
                    beneficiariesCalled += districtDto.getBeneficiariesCalled();
                    beneficiariesAnsweredAtleastOnce += districtDto.getAnsweredAtleastOneCall();
                    selfDeactivated += districtDto.getSelfDeactivated();
                    notAnswering += districtDto.getNotAnswering();
                    lowListenership += districtDto.getLowListenership();
                    systemDeactivation += districtDto.getSystemDeactivation();
                    motherCompletion += districtDto.getMotherCompletion();
                    childCompletion += districtDto.getChildCompletion();
                    calledInbox += districtDto.getCalledInbox();
                    joinedSubscription += districtDto.getJoinedSubscription();

                }
                KilkariAggregateBeneficiariesDto noDistrictCount = new KilkariAggregateBeneficiariesDto();
                noDistrictCount.setAnsweredAtleastOneCall(stateDto.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
                noDistrictCount.setBeneficiariesCalled(stateDto.getBeneficiariesCalled() - beneficiariesCalled);
                noDistrictCount.setSelfDeactivated(stateDto.getSelfDeactivated()-selfDeactivated);
                noDistrictCount.setNotAnswering(stateDto.getNotAnswering()-notAnswering);
                noDistrictCount.setLowListenership(stateDto.getLowListenership()-lowListenership);
                noDistrictCount.setSystemDeactivation(stateDto.getSystemDeactivation()-systemDeactivation);
                noDistrictCount.setMotherCompletion(stateDto.getMotherCompletion()-motherCompletion);
                noDistrictCount.setChildCompletion(stateDto.getChildCompletion()-childCompletion);
                noDistrictCount.setCalledInbox(stateDto.getCalledInbox()-calledInbox);
                noDistrictCount.setJoinedSubscription(stateDto.getJoinedSubscription()-joinedSubscription);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(noDistrictCount.getSystemDeactivation()+noDistrictCount.getNotAnswering()+noDistrictCount.getLowListenership()+noDistrictCount.getChildCompletion()+noDistrictCount.getCalledInbox()+noDistrictCount.getJoinedSubscription()+noDistrictCount.getMotherCompletion()+noDistrictCount.getSelfDeactivated()));
                noDistrictCount.setLocationId((long)(-1));
                CumulativeBeneficiary.add(noDistrictCount);
            } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeBeneficiary districtCounts = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,"District",toDate);
                    KilkariAggregateBeneficiariesDto districtDto = new KilkariAggregateBeneficiariesDto();
                    districtDto.setCalledInbox(districtCounts.getCalledInbox());
                    districtDto.setId(districtCounts.getId());
                    districtDto.setChildCompletion(districtCounts.getChildCompletion());
                    districtDto.setLocationId(districtCounts.getLocationId());
                    districtDto.setMotherCompletion(districtCounts.getMotherCompletion());
                    districtDto.setLocationType(districtCounts.getLocationType());
                    districtDto.setJoinedSubscription(districtCounts.getJoinedSubscription());
                    districtDto.setLowListenership(districtCounts.getLowListenership());
                    districtDto.setNotAnswering(districtCounts.getNotAnswering());
                    districtDto.setSelfDeactivated(districtCounts.getSelfDeactivated());
                    districtDto.setSystemDeactivation(districtCounts.getSystemDeactivation());
                    districtDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)locationId,locationType,toDate));
                    districtDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)locationId,locationType,toDate));
                    Long beneficiariesCalled = (long)0;
                    Long beneficiariesAnsweredAtleastOnce = (long)0;
                    Long selfDeactivated = (long)0;
                    Long notAnswering = (long)0;
                    Long lowListenership = (long)0;
                    Long systemDeactivation = (long)0;
                    Long motherCompletion = (long)0;
                    Long childCompletion = (long)0;
                    Long calledInbox = (long)0;
                    Long joinedSubscription = (long)0;
                    for (Block d : blocks) {
                        AggregateCumulativeBeneficiary blockCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)d.getBlockId(),locationType,toDate);
                        KilkariAggregateBeneficiariesDto blockDto = new KilkariAggregateBeneficiariesDto();
                        blockDto.setCalledInbox(blockCount.getCalledInbox());
                        blockDto.setId(blockCount.getId());
                        blockDto.setChildCompletion(blockCount.getChildCompletion());
                        blockDto.setLocationId(blockCount.getLocationId());
                        blockDto.setMotherCompletion(blockCount.getMotherCompletion());
                        blockDto.setLocationType(blockCount.getLocationType());
                        blockDto.setJoinedSubscription(blockCount.getJoinedSubscription());
                        blockDto.setLowListenership(blockCount.getLowListenership());
                        blockDto.setNotAnswering(blockCount.getNotAnswering());
                        blockDto.setSelfDeactivated(blockCount.getSelfDeactivated());
                        blockDto.setSystemDeactivation(blockCount.getSystemDeactivation());
                        blockDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)d.getBlockId(),locationType,toDate));
                        blockDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)d.getBlockId(),locationType,toDate));
                        CumulativeBeneficiary.add(blockDto);
                        beneficiariesCalled += blockDto.getBeneficiariesCalled();
                        beneficiariesAnsweredAtleastOnce += blockDto.getAnsweredAtleastOneCall();
                        selfDeactivated += blockDto.getSelfDeactivated();
                        notAnswering += blockDto.getNotAnswering();
                        lowListenership += blockDto.getLowListenership();
                        systemDeactivation += blockDto.getSystemDeactivation();
                        motherCompletion += blockDto.getMotherCompletion();
                        childCompletion += blockDto.getChildCompletion();
                        calledInbox += blockDto.getCalledInbox();
                        joinedSubscription += blockDto.getJoinedSubscription();
                    }
                    KilkariAggregateBeneficiariesDto noBlockCount = new KilkariAggregateBeneficiariesDto();
                    noBlockCount.setSelfDeactivated(districtDto.getSelfDeactivated()-selfDeactivated);
                    noBlockCount.setBeneficiariesCalled(districtDto.getBeneficiariesCalled() - beneficiariesCalled);
                    noBlockCount.setAnsweredAtleastOneCall(districtDto.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
                    noBlockCount.setNotAnswering(districtDto.getNotAnswering()-notAnswering);
                    noBlockCount.setLowListenership(districtDto.getLowListenership()-lowListenership);
                    noBlockCount.setSystemDeactivation(districtDto.getSystemDeactivation()-systemDeactivation);
                    noBlockCount.setMotherCompletion(districtDto.getMotherCompletion()-motherCompletion);
                    noBlockCount.setChildCompletion(districtDto.getChildCompletion()-childCompletion);
                    noBlockCount.setCalledInbox(districtDto.getCalledInbox()-calledInbox);
                    noBlockCount.setJoinedSubscription(districtDto.getJoinedSubscription()-joinedSubscription);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(noBlockCount.getSystemDeactivation()+noBlockCount.getNotAnswering()+noBlockCount.getLowListenership()+noBlockCount.getChildCompletion()+noBlockCount.getCalledInbox()+noBlockCount.getJoinedSubscription()+noBlockCount.getMotherCompletion()+noBlockCount.getSelfDeactivated()));
                    noBlockCount.setLocationId((long)-1);
                    CumulativeBeneficiary.add(noBlockCount);
                } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeBeneficiary blockCounts = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,"block",toDate);
                    KilkariAggregateBeneficiariesDto blockDto = new KilkariAggregateBeneficiariesDto();
                    blockDto.setCalledInbox(blockCounts.getCalledInbox());
                    blockDto.setId(blockCounts.getId());
                    blockDto.setChildCompletion(blockCounts.getChildCompletion());
                    blockDto.setLocationId(blockCounts.getLocationId());
                    blockDto.setMotherCompletion(blockCounts.getMotherCompletion());
                    blockDto.setLocationType(blockCounts.getLocationType());
                    blockDto.setJoinedSubscription(blockCounts.getJoinedSubscription());
                    blockDto.setLowListenership(blockCounts.getLowListenership());
                    blockDto.setNotAnswering(blockCounts.getNotAnswering());
                    blockDto.setSelfDeactivated(blockCounts.getSelfDeactivated());
                    blockDto.setSystemDeactivation(blockCounts.getSystemDeactivation());
                    blockDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)locationId,locationType,toDate));
                    blockDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)locationId,locationType,toDate));
                    Long beneficiariesCalled = (long)0;
                    Long beneficiariesAnsweredAtleastOnce = (long)0;
                    Long selfDeactivated = (long)0;
                    Long notAnswering = (long)0;
                    Long lowListenership = (long)0;
                    Long systemDeactivation = (long)0;
                    Long motherCompletion = (long)0;
                    Long childCompletion = (long)0;
                    Long calledInbox = (long)0;
                    Long joinedSubscription = (long)0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeBeneficiary subcenterCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)s.getSubcenterId(),locationType,toDate);
                        KilkariAggregateBeneficiariesDto subcenterDto = new KilkariAggregateBeneficiariesDto();
                        subcenterDto.setCalledInbox(subcenterCount.getCalledInbox());
                        subcenterDto.setId(subcenterCount.getId());
                        subcenterDto.setChildCompletion(subcenterCount.getChildCompletion());
                        subcenterDto.setLocationId(subcenterCount.getLocationId());
                        subcenterDto.setMotherCompletion(subcenterCount.getMotherCompletion());
                        subcenterDto.setLocationType(subcenterCount.getLocationType());
                        subcenterDto.setJoinedSubscription(subcenterCount.getJoinedSubscription());
                        subcenterDto.setLowListenership(subcenterCount.getLowListenership());
                        subcenterDto.setNotAnswering(subcenterCount.getNotAnswering());
                        subcenterDto.setSelfDeactivated(subcenterCount.getSelfDeactivated());
                        subcenterDto.setSystemDeactivation(subcenterCount.getSystemDeactivation());
                        subcenterDto.setBeneficiariesCalled(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesCalled((long)s.getSubcenterId(),locationType,toDate));
                        subcenterDto.setAnsweredAtleastOneCall(aggregateCumulativeBeneficiaryDao.getTotalBeneficiariesAnsweredAtleastOnce((long)s.getSubcenterId(),locationType,toDate));
                        CumulativeBeneficiary.add(subcenterDto);
                        beneficiariesCalled += subcenterDto.getBeneficiariesCalled();
                        beneficiariesAnsweredAtleastOnce += subcenterDto.getAnsweredAtleastOneCall();
                        selfDeactivated += subcenterDto.getSelfDeactivated();
                        notAnswering += subcenterDto.getNotAnswering();
                        lowListenership += subcenterDto.getLowListenership();
                        systemDeactivation += subcenterDto.getSystemDeactivation();
                        motherCompletion += subcenterDto.getMotherCompletion();
                        childCompletion += subcenterDto.getChildCompletion();
                        calledInbox += subcenterDto.getCalledInbox();
                        joinedSubscription += subcenterDto.getJoinedSubscription();
                    }
                    KilkariAggregateBeneficiariesDto noSubcenterCount = new KilkariAggregateBeneficiariesDto();
                    noSubcenterCount.setAnsweredAtleastOneCall(blockDto.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
                    noSubcenterCount.setBeneficiariesCalled(blockDto.getBeneficiariesCalled() - beneficiariesCalled);
                    noSubcenterCount.setSelfDeactivated(blockDto.getSelfDeactivated()-selfDeactivated);
                    noSubcenterCount.setNotAnswering(blockDto.getNotAnswering()-notAnswering);
                    noSubcenterCount.setLowListenership(blockDto.getLowListenership()-lowListenership);
                    noSubcenterCount.setSystemDeactivation(blockDto.getSystemDeactivation()-systemDeactivation);
                    noSubcenterCount.setMotherCompletion(blockDto.getMotherCompletion()-motherCompletion);
                    noSubcenterCount.setChildCompletion(blockDto.getChildCompletion()-childCompletion);
                    noSubcenterCount.setCalledInbox(blockDto.getCalledInbox()-calledInbox);
                    noSubcenterCount.setJoinedSubscription(blockDto.getJoinedSubscription()-joinedSubscription);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId((int)(noSubcenterCount.getSystemDeactivation()+noSubcenterCount.getNotAnswering()+noSubcenterCount.getLowListenership()+noSubcenterCount.getChildCompletion()+noSubcenterCount.getCalledInbox()+noSubcenterCount.getJoinedSubscription()+noSubcenterCount.getMotherCompletion()+noSubcenterCount.getSelfDeactivated()));
                    noSubcenterCount.setLocationId((long)(-1));
                    CumulativeBeneficiary.add(noSubcenterCount);
                }
        return CumulativeBeneficiary;
    }

    /*----------5.3.4. Kilkari Usage Report -------*/

    @Override
    public List<UsageDto> getUsageReport(ReportRequest reportRequest, User currentUser){

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

        List<UsageDto> summaryDto = new ArrayList<>();
        List<KilkariUsage> kilkariUsageList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariUsageList.addAll(this.getKilkariUsage(0,"State",toDate));
        }
        else if (reportRequest.getDistrictId() == 0) {
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getDistrictId(),"Block",toDate));
        }
        else {
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getBlockId(),"Subcenter",toDate));
        }

        if(!(kilkariUsageList.isEmpty())){
            for(KilkariUsage a:kilkariUsageList){
                UsageDto summaryDto1 = new UsageDto();
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setCalls_75_100(a.getCalls_75_100());
                summaryDto1.setCalls_50_75(a.getCalls_50_75());
                summaryDto1.setCalls_25_50(a.getCalls_25_50());
                summaryDto1.setCalls_1_25(a.getCalls_1_25());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setCalledInbox(a.getCalledInbox());
                summaryDto1.setBeneficiariesCalled(a.getBeneficiariesCalled());
                summaryDto1.setAnsweredCall(a.getAtLeastOneCall());
                String locationType = a.getLocationType();
                if(locationType.equalsIgnoreCase("State")){
                    summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if(locationType.equalsIgnoreCase("District")){
                    summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if(locationType.equalsIgnoreCase("Block")){
                    summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if(locationType.equalsIgnoreCase("Subcenter")){
                    summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcenter");
                }

                if(a.getId()!=0){
                    summaryDto.add(summaryDto1);
                }
            }
        }
        return summaryDto;
    }

    private List<KilkariUsage> getKilkariUsage(Integer locationId,String locationType,Date toDate){
        List<KilkariUsage> KilkariUsage = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                KilkariUsage.add(kilkariUsageDao.getUsage(s.getStateId(),locationType,toDate));
            }
        }
        else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                KilkariUsage stateCounts = kilkariUsageDao.getUsage(locationId,"State",toDate);
                Long beneficiariesCalled = (long)0;
                Long calls_75_100 = (long)0;
                Long calls_50_75 = (long)0;
                Long calls_25_50 = (long)0;
                Long calls_1_25 = (long)0;
                Long calledInbox = (long)0;
                Long atLeastOneCall = (long)0;
                for(District d:districts){
                    KilkariUsage distrcitCount = kilkariUsageDao.getUsage(d.getDistrictId(),locationType,toDate);
                    KilkariUsage.add(distrcitCount);
                    beneficiariesCalled+=distrcitCount.getBeneficiariesCalled();
                    calls_75_100+=distrcitCount.getCalls_75_100();
                    calls_50_75+=distrcitCount.getCalls_50_75();
                    calls_25_50+=distrcitCount.getCalls_25_50();
                    calls_1_25+=distrcitCount.getCalls_1_25();
                    calledInbox+=distrcitCount.getCalledInbox();
                    atLeastOneCall+=distrcitCount.getAtLeastOneCall();

                }
                KilkariUsage noDistrictCount = new KilkariUsage();
                noDistrictCount.setBeneficiariesCalled(stateCounts.getBeneficiariesCalled() - beneficiariesCalled);
                noDistrictCount.setCalls_75_100(stateCounts.getCalls_75_100() - calls_75_100);
                noDistrictCount.setCalls_50_75(stateCounts.getCalls_50_75() - calls_50_75);
                noDistrictCount.setCalls_25_50(stateCounts.getCalls_25_50() - calls_25_50);
                noDistrictCount.setCalls_1_25(stateCounts.getCalls_1_25() - calls_1_25);
                noDistrictCount.setCalledInbox(stateCounts.getCalledInbox() - calledInbox);
                noDistrictCount.setAtLeastOneCall(stateCounts.getAtLeastOneCall() - atLeastOneCall);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(noDistrictCount.getBeneficiariesCalled()+noDistrictCount.getCalledInbox()+noDistrictCount.getAtLeastOneCall()+noDistrictCount.getCalls_1_25()+noDistrictCount.getCalls_25_50()+noDistrictCount.getCalls_50_75()+noDistrictCount.getCalls_75_100()+noDistrictCount.getAtLeastOneCall()));
                noDistrictCount.setLocationId((long)(-1));
                KilkariUsage.add(noDistrictCount);
            }
            else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    KilkariUsage districtCounts = kilkariUsageDao.getUsage(locationId,"District",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Long calledInbox = (long)0;
                    Long atLeastOneCall = (long)0;

                    for (Block d : blocks) {
                        KilkariUsage blockCount = kilkariUsageDao.getUsage(d.getBlockId(),locationType,toDate);
                        KilkariUsage.add(blockCount);
                        beneficiariesCalled+=blockCount.getBeneficiariesCalled();
                        calls_75_100+=blockCount.getCalls_75_100();
                        calls_50_75+=blockCount.getCalls_50_75();
                        calls_25_50+=blockCount.getCalls_25_50();
                        calls_1_25+=blockCount.getCalls_1_25();
                        calledInbox+=blockCount.getCalledInbox();
                        atLeastOneCall+=blockCount.getAtLeastOneCall();


                    }
                    KilkariUsage noBlockCount = new KilkariUsage();
                    noBlockCount.setBeneficiariesCalled(districtCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    noBlockCount.setCalls_75_100(districtCounts.getCalls_75_100()-calls_75_100);
                    noBlockCount.setCalls_50_75(districtCounts.getCalls_50_75()-calls_50_75);
                    noBlockCount.setCalls_25_50(districtCounts.getCalls_25_50()-calls_25_50);
                    noBlockCount.setCalls_1_25(districtCounts.getCalls_1_25()-calls_1_25);
                    noBlockCount.setCalledInbox(districtCounts.getCalledInbox()-calledInbox);
                    noBlockCount.setAtLeastOneCall(districtCounts.getAtLeastOneCall()-atLeastOneCall);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(noBlockCount.getCalledInbox()+noBlockCount.getBeneficiariesCalled()+noBlockCount.getAtLeastOneCall()+noBlockCount.getCalls_1_25()+noBlockCount.getCalls_25_50()+noBlockCount.getCalls_50_75()+noBlockCount.getCalls_75_100()+noBlockCount.getAtLeastOneCall()));
                    noBlockCount.setLocationId((long)(-1));
                    KilkariUsage.add(noBlockCount);
            } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    KilkariUsage blockCounts = kilkariUsageDao.getUsage(locationId,"block",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Long calledInbox = (long)0;
                    Long atLeastOneCall = (long)0;

                    for(Subcenter s: subcenters){
                        KilkariUsage SubcenterCount = kilkariUsageDao.getUsage(s.getSubcenterId(),locationType,toDate);
                        KilkariUsage.add(SubcenterCount);
                        beneficiariesCalled+=SubcenterCount.getBeneficiariesCalled();
                        calls_75_100+=SubcenterCount.getCalls_75_100();
                        calls_50_75+=SubcenterCount.getCalls_50_75();
                        calls_25_50+=SubcenterCount.getCalls_25_50();
                        calls_1_25+=SubcenterCount.getCalls_1_25();
                        calledInbox+=SubcenterCount.getCalledInbox();
                        atLeastOneCall+=SubcenterCount.getAtLeastOneCall();

                    }
                    KilkariUsage noSubcenterCount = new KilkariUsage();
                    noSubcenterCount.setBeneficiariesCalled(blockCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    noSubcenterCount.setCalls_75_100(blockCounts.getCalls_75_100()-calls_75_100);
                    noSubcenterCount.setCalls_50_75(blockCounts.getCalls_50_75()-calls_50_75);
                    noSubcenterCount.setCalls_25_50(blockCounts.getCalls_25_50()-calls_25_50);
                    noSubcenterCount.setCalls_1_25(blockCounts.getCalls_1_25()-calls_1_25);
                    noSubcenterCount.setCalledInbox(blockCounts.getCalledInbox()-calledInbox);
                    noSubcenterCount.setAtLeastOneCall(blockCounts.getAtLeastOneCall()-atLeastOneCall);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId((int)(noSubcenterCount.getBeneficiariesCalled()+noSubcenterCount.getCalledInbox()+noSubcenterCount.getCalls_1_25()+noSubcenterCount.getCalls_25_50()+noSubcenterCount.getCalls_50_75()+noSubcenterCount.getCalls_75_100()+noSubcenterCount.getAtLeastOneCall()));
                    noSubcenterCount.setLocationId((long)(-1));
                    KilkariUsage.add(noSubcenterCount);
                }
        return KilkariUsage;
    }

      /*----------5.3.5. Kilkari Message Listenership Report -------*/

    @Override
    public AggregateKilkariReportsDto getKilkariMessageListenershipReport(ReportRequest reportRequest){
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


        List<KilkariMessageListenershipReportDto> kilkariMessageListenershipReportDtoList = new ArrayList<>();
        List<KilkariMessageListenership> kilkariMessageListenershipList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getDistrictId(),"Block",toDate));
        } else {
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getBlockId(),"Subcenter",toDate));
        }

        if(!(kilkariMessageListenershipList.isEmpty())){
            for(KilkariMessageListenership a : kilkariMessageListenershipList){
                KilkariMessageListenershipReportDto kilkariMessageListenershipReportDto = new KilkariMessageListenershipReportDto();
                kilkariMessageListenershipReportDto.setLocationId(a.getLocationId());
                kilkariMessageListenershipReportDto.setTotalBeneficiariesCalled(a.getTotalBeneficiariesCalled());
                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredMoreThan75(a.getAnsweredMoreThan75Per());
                kilkariMessageListenershipReportDto.setBeneficiariesAnswered50To75(a.getAnswered50To75Per());
                kilkariMessageListenershipReportDto.setBeneficiariesAnswered25To50(a.getAnswered25To50Per());
                kilkariMessageListenershipReportDto.setLocationType(a.getLocationType());
                kilkariMessageListenershipReportDto.setBeneficiariesAnswered1To25(a.getAnswered1To25Per());
                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredAtleastOnce(a.getAnsweredAtleastOneCall());
                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredNoCalls(a.getAnsweredNoCalls());
                String locationType = a.getLocationType();
                if(locationType.equalsIgnoreCase("State")){
                    kilkariMessageListenershipReportDto.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if(locationType.equalsIgnoreCase("District")){
                    kilkariMessageListenershipReportDto.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if(locationType.equalsIgnoreCase("Block")){
                    kilkariMessageListenershipReportDto.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if(locationType.equalsIgnoreCase("Subcenter")){
                    kilkariMessageListenershipReportDto.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    kilkariMessageListenershipReportDto.setLocationName("No District");
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    kilkariMessageListenershipReportDto.setLocationName("No Block");
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    kilkariMessageListenershipReportDto.setLocationName("No Subcenter");
                }
                if(a.getId()!=0){
                    kilkariMessageListenershipReportDtoList.add(kilkariMessageListenershipReportDto);
                }
            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariMessageListenershipReportDtoList);
        return aggregateKilkariReportsDto;
    }


    private List<KilkariMessageListenership> getKilkariMessageListenershipData(Integer locationId, String locationType, Date date){
        List<KilkariMessageListenership> kilkariMessageListenershipList = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                kilkariMessageListenershipList.add(kilkariMessageListenershipReportDao.getListenerData(s.getStateId(),locationType,date));
            }
        }
        else if(locationType.equalsIgnoreCase("District")){
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            KilkariMessageListenership stateCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,"State",date);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;
            for(District d:districts){
                KilkariMessageListenership districtCount = kilkariMessageListenershipReportDao.getListenerData(d.getDistrictId(),locationType,date);
                kilkariMessageListenershipList.add(districtCount);
                answeredAtleastOneCall += districtCount.getAnsweredAtleastOneCall();
                answeredMoreThan75Per += districtCount.getAnsweredMoreThan75Per();
                answered50To75Per += districtCount.getAnswered50To75Per();
                answered25To50Per += districtCount.getAnswered25To50Per();
                answered1To25Per += districtCount.getAnswered1To25Per();
                answeredNoCalls += districtCount.getAnsweredNoCalls();
                totalBeneficiariesCalled += districtCount.getTotalBeneficiariesCalled();
            }
            KilkariMessageListenership noDistrictCount = new KilkariMessageListenership();
            noDistrictCount.setAnsweredAtleastOneCall(stateCounts.getAnsweredAtleastOneCall() - answeredAtleastOneCall);
            noDistrictCount.setAnswered1To25Per(stateCounts.getAnswered1To25Per() - answered1To25Per);
            noDistrictCount.setAnswered25To50Per(stateCounts.getAnswered25To50Per() - answered25To50Per);
            noDistrictCount.setAnswered50To75Per(stateCounts.getAnswered50To75Per() - answered50To75Per);
            noDistrictCount.setAnsweredMoreThan75Per(stateCounts.getAnsweredMoreThan75Per() - answeredMoreThan75Per);
            noDistrictCount.setAnsweredNoCalls(stateCounts.getAnsweredNoCalls() - answeredNoCalls);
            noDistrictCount.setTotalBeneficiariesCalled(stateCounts.getTotalBeneficiariesCalled() - totalBeneficiariesCalled);
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setId((int)(noDistrictCount.getAnswered1To25Per()+noDistrictCount.getAnswered25To50Per()+noDistrictCount.getAnswered50To75Per()+noDistrictCount.getAnsweredAtleastOneCall()+noDistrictCount.getAnsweredMoreThan75Per()+noDistrictCount.getAnsweredNoCalls()+noDistrictCount.getTotalBeneficiariesCalled()));
            noDistrictCount.setLocationId((long)(-1));
            kilkariMessageListenershipList.add(noDistrictCount);
        }
        else if(locationType.equalsIgnoreCase("Block")) {
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            KilkariMessageListenership districtCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,locationType,date);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;

            for (Block d : blocks) {
                KilkariMessageListenership blockCount = kilkariMessageListenershipReportDao.getListenerData(d.getBlockId(),locationType,date);
                kilkariMessageListenershipList.add(blockCount);
                answeredAtleastOneCall += blockCount.getAnsweredAtleastOneCall();
                answeredMoreThan75Per += blockCount.getAnsweredMoreThan75Per();
                answered50To75Per += blockCount.getAnswered50To75Per();
                answered25To50Per += blockCount.getAnswered25To50Per();
                answered1To25Per += blockCount.getAnswered1To25Per();
                answeredNoCalls += blockCount.getAnsweredNoCalls();
                totalBeneficiariesCalled += blockCount.getTotalBeneficiariesCalled();
            }
            KilkariMessageListenership noBlockCount = new KilkariMessageListenership();
            noBlockCount.setAnsweredAtleastOneCall(districtCounts.getAnsweredAtleastOneCall() - answeredAtleastOneCall);
            noBlockCount.setAnswered1To25Per(districtCounts.getAnswered1To25Per() - answered1To25Per);
            noBlockCount.setAnswered25To50Per(districtCounts.getAnswered25To50Per() - answered25To50Per);
            noBlockCount.setAnswered50To75Per(districtCounts.getAnswered50To75Per() - answered50To75Per);
            noBlockCount.setAnsweredMoreThan75Per(districtCounts.getAnsweredMoreThan75Per() - answeredMoreThan75Per);
            noBlockCount.setAnsweredNoCalls(districtCounts.getAnsweredNoCalls() - answeredNoCalls);
            noBlockCount.setTotalBeneficiariesCalled(districtCounts.getTotalBeneficiariesCalled() - totalBeneficiariesCalled);
            noBlockCount.setLocationType("DifferenceState");
            noBlockCount.setId((int)(noBlockCount.getAnswered1To25Per()+noBlockCount.getAnswered25To50Per()+noBlockCount.getAnswered50To75Per()+noBlockCount.getAnsweredAtleastOneCall()+noBlockCount.getAnsweredMoreThan75Per()+noBlockCount.getAnsweredNoCalls()+noBlockCount.getTotalBeneficiariesCalled()));
            noBlockCount.setLocationId((long)(-1));
            kilkariMessageListenershipList.add(noBlockCount);
        }
        else {
            List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
            KilkariMessageListenership blockCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,"block",date);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;

            for(Subcenter s: subcenters){
                KilkariMessageListenership subcenterCount = kilkariMessageListenershipReportDao.getListenerData(s.getSubcenterId(),locationType,date);
                kilkariMessageListenershipList.add(subcenterCount);
                answeredAtleastOneCall += subcenterCount.getAnsweredAtleastOneCall();
                answeredMoreThan75Per += subcenterCount.getAnsweredMoreThan75Per();
                answered50To75Per += subcenterCount.getAnswered50To75Per();
                answered25To50Per += subcenterCount.getAnswered25To50Per();
                answered1To25Per += subcenterCount.getAnswered1To25Per();
                answeredNoCalls += subcenterCount.getAnsweredNoCalls();
                totalBeneficiariesCalled += subcenterCount.getTotalBeneficiariesCalled();

            }
            KilkariMessageListenership noSubcenterCount = new KilkariMessageListenership();
            noSubcenterCount.setAnsweredAtleastOneCall(blockCounts.getAnsweredAtleastOneCall() - answeredAtleastOneCall);
            noSubcenterCount.setAnswered1To25Per(blockCounts.getAnswered1To25Per() - answered1To25Per);
            noSubcenterCount.setAnswered25To50Per(blockCounts.getAnswered25To50Per() - answered25To50Per);
            noSubcenterCount.setAnswered50To75Per(blockCounts.getAnswered50To75Per() - answered50To75Per);
            noSubcenterCount.setAnsweredMoreThan75Per(blockCounts.getAnsweredMoreThan75Per() - answeredMoreThan75Per);
            noSubcenterCount.setAnsweredNoCalls(blockCounts.getAnsweredNoCalls() - answeredNoCalls);
            noSubcenterCount.setTotalBeneficiariesCalled(blockCounts.getTotalBeneficiariesCalled() - totalBeneficiariesCalled);
            noSubcenterCount.setLocationType("DifferenceState");
            noSubcenterCount.setId((int)(noSubcenterCount.getAnswered1To25Per()+noSubcenterCount.getAnswered25To50Per()+noSubcenterCount.getAnswered50To75Per()+noSubcenterCount.getAnsweredAtleastOneCall()+noSubcenterCount.getAnsweredMoreThan75Per()+noSubcenterCount.getAnsweredNoCalls()+noSubcenterCount.getTotalBeneficiariesCalled()));
            noSubcenterCount.setLocationId((long)(-1));
            kilkariMessageListenershipList.add(noSubcenterCount);
        }
        return kilkariMessageListenershipList;
    }


    /*----------5.3.6. Kilkari Beneficiary Completion Report -------*/

    @Override
    public List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletion(ReportRequest reportRequest, User currentUser){

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


        List<AggCumulativeBeneficiaryComplDto> summaryDto = new ArrayList<>();
        List<AggregateCumulativeBeneficiaryCompletion> cumulativeCompletionReportEnd = new ArrayList<>();


        if (reportRequest.getStateId() == 0) {
            cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletionData(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
            cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
            cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getDistrictId(),"Block",toDate));
        } else {
            cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getBlockId(),"Subcenter",toDate));
        }


        if(!(cumulativeCompletionReportEnd.isEmpty())){
            for(int i=0;i<cumulativeCompletionReportEnd.size();i++){
                AggregateCumulativeBeneficiaryCompletion a = cumulativeCompletionReportEnd.get(i);
                AggCumulativeBeneficiaryComplDto summaryDto1 = new AggCumulativeBeneficiaryComplDto();
                summaryDto1.setId(a.getId());
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setCompletedBeneficiaries(a.getCompletedBeneficiaries());
                summaryDto1.setCalls_75_100(a.getCalls_75_100());
                summaryDto1.setCalls_50_75(a.getCalls_50_75());
                summaryDto1.setCalls_25_50(a.getCalls_25_50());
                summaryDto1.setCalls_1_25(a.getCalls_1_25());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setAvgWeeks(a.getTotalAge());
                String locationType = a.getLocationType();
                if(locationType.equalsIgnoreCase("State")){
                    summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if(locationType.equalsIgnoreCase("District")){
                    summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if(locationType.equalsIgnoreCase("Block")){
                    summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if(locationType.equalsIgnoreCase("Subcenter")){
                    summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcenter");
                }

                if(a.getId()!=0){
                    summaryDto.add(summaryDto1);
                }
            }
        }
        return summaryDto;
    }

    private List<AggregateCumulativeBeneficiaryCompletion> getCumulativeBeneficiaryCompletionData(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeBeneficiaryCompletion> CumulativeCompletion = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                CumulativeCompletion.add(aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getStateId(),locationType,toDate));
            }
        }
        else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeBeneficiaryCompletion stateCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"State",toDate);
                Long completedBeneficiaries = (long)0;
                Long calls_75_100 = (long)0;
                Long calls_50_75 = (long)0;
                Long calls_25_50 = (long)0;
                Long calls_1_25 = (long)0;
                Integer totalAge = 0;
                for(District d:districts){
                    AggregateCumulativeBeneficiaryCompletion districtCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getDistrictId(),locationType,toDate);
                    CumulativeCompletion.add(districtCount);
                    completedBeneficiaries+=districtCount.getCompletedBeneficiaries();
                    calls_75_100 += districtCount.getCalls_75_100();
                    calls_50_75 += districtCount.getCalls_50_75();
                    calls_25_50 += districtCount.getCalls_25_50();
                    calls_1_25 += districtCount.getCalls_1_25();
                    totalAge += districtCount.getTotalAge();
                }
                AggregateCumulativeBeneficiaryCompletion noDistrictCount = new AggregateCumulativeBeneficiaryCompletion();
                noDistrictCount.setCompletedBeneficiaries(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                noDistrictCount.setCalls_75_100(stateCounts.getCalls_75_100()-calls_75_100);
                noDistrictCount.setCalls_50_75(stateCounts.getCalls_50_75()-calls_50_75);
                noDistrictCount.setCalls_25_50(stateCounts.getCalls_25_50()-calls_25_50);
                noDistrictCount.setCalls_1_25(stateCounts.getCalls_1_25()-calls_1_25);
                noDistrictCount.setTotalAge(stateCounts.getTotalAge()-totalAge);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries+stateCounts.getCalls_75_100()-calls_75_100+stateCounts.getCalls_50_75()-calls_50_75+stateCounts.getCalls_25_50()-calls_25_50+stateCounts.getCalls_1_25()-calls_1_25+stateCounts.getTotalAge()-totalAge));
                noDistrictCount.setLocationId((long)(-1));
                CumulativeCompletion.add(noDistrictCount);
        } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeBeneficiaryCompletion districtCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"District",toDate);
                    Long completedBeneficiaries = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Integer totalAge =  0;
                    for (Block d : blocks) {
                        AggregateCumulativeBeneficiaryCompletion blockCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getBlockId(),locationType,toDate);
                        CumulativeCompletion.add(blockCount);
                        completedBeneficiaries += blockCount.getCompletedBeneficiaries();
                        calls_75_100 += blockCount.getCalls_75_100();
                        calls_50_75 += blockCount.getCalls_50_75();
                        calls_25_50 += blockCount.getCalls_25_50();
                        calls_1_25 += blockCount.getCalls_1_25();
                        totalAge += blockCount.getTotalAge();
                    }
                    AggregateCumulativeBeneficiaryCompletion noBlockCount = new AggregateCumulativeBeneficiaryCompletion();
                    noBlockCount.setCompletedBeneficiaries(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                    noBlockCount.setCalls_75_100(districtCounts.getCalls_75_100()-calls_75_100);
                    noBlockCount.setCalls_50_75(districtCounts.getCalls_50_75()-calls_50_75);
                    noBlockCount.setCalls_25_50(districtCounts.getCalls_25_50()-calls_25_50);
                    noBlockCount.setCalls_1_25(districtCounts.getCalls_1_25()-calls_1_25);
                    noBlockCount.setTotalAge(districtCounts.getTotalAge()-totalAge);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries+districtCounts.getCalls_75_100()-calls_75_100+districtCounts.getCalls_50_75()-calls_50_75+districtCounts.getCalls_25_50()-calls_25_50+districtCounts.getCalls_1_25()-calls_1_25+districtCounts.getTotalAge()-totalAge));
                    noBlockCount.setLocationId((long)(-1));
                    CumulativeCompletion.add(noBlockCount);
        } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeBeneficiaryCompletion blockCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"block",toDate);
                    Long completedBeneficiaries = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Integer totalAge = 0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeBeneficiaryCompletion SubcenterCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getSubcenterId(),locationType,toDate);
                        CumulativeCompletion.add(SubcenterCount);
                        completedBeneficiaries+=SubcenterCount.getCompletedBeneficiaries();
                        calls_75_100 += SubcenterCount.getCalls_75_100();
                        calls_50_75 += SubcenterCount.getCalls_50_75();
                        calls_25_50 += SubcenterCount.getCalls_25_50();
                        calls_1_25 += SubcenterCount.getCalls_1_25();
                        totalAge += SubcenterCount.getTotalAge();
                    }
                    AggregateCumulativeBeneficiaryCompletion noSubcenterCount = new AggregateCumulativeBeneficiaryCompletion();
                    noSubcenterCount.setCompletedBeneficiaries(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                    noSubcenterCount.setCalls_75_100(blockCounts.getCalls_75_100()-calls_75_100);
                    noSubcenterCount.setCalls_50_75(blockCounts.getCalls_50_75()-calls_50_75);
                    noSubcenterCount.setCalls_25_50(blockCounts.getCalls_25_50()-calls_25_50);
                    noSubcenterCount.setCalls_1_25(blockCounts.getCalls_1_25()-calls_1_25);
                    noSubcenterCount.setTotalAge(blockCounts.getTotalAge()-totalAge);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId((int)(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries+blockCounts.getCalls_75_100()-calls_75_100+blockCounts.getCalls_50_75()-calls_50_75+blockCounts.getCalls_25_50()-calls_25_50+blockCounts.getCalls_1_25()-calls_1_25+blockCounts.getTotalAge()-totalAge));
                    noSubcenterCount.setLocationId((long)(-1));
                    CumulativeCompletion.add(noSubcenterCount);
                }
        return CumulativeCompletion;
    }

    /*----------5.3.7. Kilkari Listening Matrix Report -------*/

    @Override
    public List<ListeningMatrixDto> getListeningMatrixReport(ReportRequest reportRequest, User currentUser){

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


        List<ListeningMatrixDto> matrixDto = new ArrayList<>();
        ListeningMatrix listeningMatrix = listeningMatrixReportDao.getListeningMatrix(toDate);

        if(listeningMatrix==null){
            return matrixDto;
        }
        ListeningMatrixDto matrixDto1 = new ListeningMatrixDto();
        matrixDto1.setPercentageCalls("Beneficiaries Listening > 75%calls");
        matrixDto1.setContent_1_25(listeningMatrix.getCalls_75_Content_1());
        matrixDto1.setContent_25_50(listeningMatrix.getCalls_75_Content_25());
        matrixDto1.setContent_50_75(listeningMatrix.getCalls_75_Content_50());
        matrixDto1.setContent_75_100(listeningMatrix.getCalls_75_Content_75());
        matrixDto1.setTotal(listeningMatrix.getCalls_75_Content_1()+listeningMatrix.getCalls_75_Content_25()+listeningMatrix.getCalls_75_Content_50()+listeningMatrix.getCalls_75_Content_75());

        ListeningMatrixDto matrixDto2 = new ListeningMatrixDto();
        matrixDto2.setPercentageCalls("Beneficiaries Listening 50 to 75 % calls");
        matrixDto2.setContent_1_25(listeningMatrix.getCalls_50_content_1());
        matrixDto2.setContent_25_50(listeningMatrix.getCalls_50_content_25());
        matrixDto2.setContent_50_75(listeningMatrix.getCalls_50_content_50());
        matrixDto2.setContent_75_100(listeningMatrix.getCalls_50_content_75());
        matrixDto2.setTotal(listeningMatrix.getCalls_50_content_1()+listeningMatrix.getCalls_50_content_25()+listeningMatrix.getCalls_50_content_50()+listeningMatrix.getCalls_50_content_75());

        ListeningMatrixDto matrixDto3 = new ListeningMatrixDto();
        matrixDto3.setPercentageCalls("Beneficiaries Listening 25 to 50 % calls");
        matrixDto3.setContent_1_25(listeningMatrix.getCalls_25_content_1());
        matrixDto3.setContent_25_50(listeningMatrix.getCalls_25_content_25());
        matrixDto3.setContent_50_75(listeningMatrix.getCalls_25_content_50());
        matrixDto3.setContent_75_100(listeningMatrix.getCalls_25_content_75());
        matrixDto3.setTotal(listeningMatrix.getCalls_25_content_1()+listeningMatrix.getCalls_25_content_25()+listeningMatrix.getCalls_25_content_50()+listeningMatrix.getCalls_25_content_75());

        ListeningMatrixDto matrixDto4 = new ListeningMatrixDto();
        matrixDto4.setPercentageCalls("Beneficiaries Listening < 25% calls");
        matrixDto4.setContent_1_25(listeningMatrix.getCalls_1_content_1());
        matrixDto4.setContent_25_50(listeningMatrix.getCalls_1_content_25());
        matrixDto4.setContent_50_75(listeningMatrix.getCalls_1_content_50());
        matrixDto4.setContent_75_100(listeningMatrix.getCalls_1_content_75());
        matrixDto4.setTotal(listeningMatrix.getCalls_1_content_1()+listeningMatrix.getCalls_1_content_25()+listeningMatrix.getCalls_1_content_50()+listeningMatrix.getCalls_1_content_75());

        ListeningMatrixDto matrixDto5 = new ListeningMatrixDto();
        matrixDto5.setPercentageCalls("Total");
        matrixDto5.setContent_1_25(listeningMatrix.getCalls_75_Content_1()+listeningMatrix.getCalls_25_content_1()+listeningMatrix.getCalls_50_content_1()+listeningMatrix.getCalls_1_content_1());
        matrixDto5.setContent_25_50(listeningMatrix.getCalls_1_content_25()+listeningMatrix.getCalls_25_content_25()+listeningMatrix.getCalls_50_content_25()+listeningMatrix.getCalls_75_Content_25());
        matrixDto5.setContent_50_75(listeningMatrix.getCalls_1_content_50()+listeningMatrix.getCalls_25_content_50()+listeningMatrix.getCalls_50_content_50()+listeningMatrix.getCalls_75_Content_50());
        matrixDto5.setContent_75_100(listeningMatrix.getCalls_1_content_75()+listeningMatrix.getCalls_75_Content_75()+listeningMatrix.getCalls_25_content_75()+listeningMatrix.getCalls_50_content_75());
        matrixDto5.setTotal(matrixDto1.getTotal()+matrixDto2.getTotal()+matrixDto3.getTotal()+matrixDto4.getTotal());

        matrixDto.add(matrixDto1);
        matrixDto.add(matrixDto2);
        matrixDto.add(matrixDto3);
        matrixDto.add(matrixDto4);
        matrixDto.add(matrixDto5);

        return matrixDto;
    }

    /*----------5.3.8. Kilkari Thematic Content Report -------*/

    @Override
    public AggregateKilkariReportsDto getKilkariThematicContentReport(ReportRequest reportRequest){

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

        List<KilkariThematicContentReportDto> kilkariThematicContentReportDtoList = new ArrayList<>();
        List<KilkariThematicContent> kilkariThematicContentDataStart = kilkariThematicContentReportDao.getKilkariThematicContentReportData(fromDate);
        List<KilkariThematicContent> kilkariThematicContentDataEnd = kilkariThematicContentReportDao.getKilkariThematicContentReportData(toDate);

        if(kilkariThematicContentDataEnd != null && kilkariThematicContentDataStart != null & !(kilkariThematicContentDataEnd.isEmpty()) && !(kilkariThematicContentDataStart.isEmpty())){
            for(int i = 0; i < kilkariThematicContentDataEnd.size(); i++) {
                for (int j = 0; j < kilkariThematicContentDataStart.size(); j++) {
                    if(kilkariThematicContentDataEnd.get(i).getMessageWeekNumber().equals(kilkariThematicContentDataStart.get(j).getMessageWeekNumber())){
                        KilkariThematicContentReportDto kilkariThematicContentReportDto = new KilkariThematicContentReportDto();
                        kilkariThematicContentReportDto.setId(kilkariThematicContentDataEnd.get(i).getId());
                        kilkariThematicContentReportDto.setTheme(kilkariThematicContentDataEnd.get(i).getTheme());
                        kilkariThematicContentReportDto.setMinutesConsumed(kilkariThematicContentDataEnd.get(i).getMinutesConsumed() - kilkariThematicContentDataStart.get(j).getMinutesConsumed());
                        kilkariThematicContentReportDto.setCallsAnswered(kilkariThematicContentDataEnd.get(i).getCallsAnswered() - kilkariThematicContentDataStart.get(j).getCallsAnswered());
                        kilkariThematicContentReportDto.setUniqueBeneficiariesCalled(kilkariThematicContentReportDao.getUniqueBeneficiariesCalled(fromDate,toDate,kilkariThematicContentDataEnd.get(i).getMessageWeekNumber()));
                        kilkariThematicContentReportDto.setMessageWeekNumber(kilkariThematicContentReportDao.getMessageWeekNumber(kilkariThematicContentDataEnd.get(i).getMessageWeekNumber()));
                        kilkariThematicContentReportDtoList.add(kilkariThematicContentReportDto);
                    }
                }
            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariThematicContentReportDtoList);
        return aggregateKilkariReportsDto;
    }

    /*----------5.3.9. Kilkari Message Matrix Report -------*/

    @Override
    public MessageMatrixResponseDto getMessageMatrixReport(ReportRequest reportRequest, User currentUser){
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

        MessageMatrixResponseDto messageMatrixResponseDto = new MessageMatrixResponseDto();
        List<MessageMatrixDto> motherMatrixDto = new ArrayList<>();
        List<MessageMatrixDto> childMatrixDto = new ArrayList<>();

        MessageMatrix messageMatrix= messageMatrixDao.getMessageMatrixData(toDate);
        if(messageMatrix == null){
            messageMatrixResponseDto.setChildData(childMatrixDto);
            messageMatrixResponseDto.setMotherData(motherMatrixDto);
            return  messageMatrixResponseDto;
        }

        MessageMatrixDto matrixDto1 = new MessageMatrixDto();
        matrixDto1.setMessageWeek("Message Week 1-6");
        matrixDto1.setContent_1_25(messageMatrix.getMother_1_6_Content_1());
        matrixDto1.setContent_25_50(messageMatrix.getMother_1_6_Content_25());
        matrixDto1.setContent_50_75(messageMatrix.getMother_1_6_Content_50());
        matrixDto1.setContent_75_100(messageMatrix.getMother_1_6_Content_75());
        matrixDto1.setTotal(messageMatrix.getMother_1_6_Content_1()+messageMatrix.getMother_1_6_Content_25()+messageMatrix.getMother_1_6_Content_50()+messageMatrix.getMother_1_6_Content_75());

        MessageMatrixDto matrixDto2 = new MessageMatrixDto();
        matrixDto2.setMessageWeek("Message Week 7-12");
        matrixDto2.setContent_1_25(messageMatrix.getMother_7_12_Content_1());
        matrixDto2.setContent_25_50(messageMatrix.getMother_7_12_Content_25());
        matrixDto2.setContent_50_75(messageMatrix.getMother_7_12_Content_50());
        matrixDto2.setContent_75_100(messageMatrix.getMother_7_12_Content_75());
        matrixDto2.setTotal(messageMatrix.getMother_7_12_Content_1()+messageMatrix.getMother_7_12_Content_25()+messageMatrix.getMother_7_12_Content_50()+messageMatrix.getMother_7_12_Content_75());

        MessageMatrixDto matrixDto3 = new MessageMatrixDto();
        matrixDto3.setMessageWeek("Message Week 13-18");
        matrixDto3.setContent_1_25(messageMatrix.getMother_13_18_Content_1());
        matrixDto3.setContent_25_50(messageMatrix.getMother_13_18_Content_25());
        matrixDto3.setContent_50_75(messageMatrix.getMother_13_18_Content_50());
        matrixDto3.setContent_75_100(messageMatrix.getMother_13_18_Content_75());
        matrixDto3.setTotal(messageMatrix.getMother_13_18_Content_1()+messageMatrix.getMother_13_18_Content_25()+messageMatrix.getMother_13_18_Content_50()+messageMatrix.getMother_13_18_Content_75());

        MessageMatrixDto matrixDto4 = new MessageMatrixDto();
        matrixDto4.setMessageWeek("Message Week 19-24");
        matrixDto4.setContent_1_25(messageMatrix.getMother_19_24_Content_1());
        matrixDto4.setContent_25_50(messageMatrix.getMother_19_24_Content_25());
        matrixDto4.setContent_50_75(messageMatrix.getMother_19_24_Content_50());
        matrixDto4.setContent_75_100(messageMatrix.getMother_19_24_Content_75());
        matrixDto4.setTotal(messageMatrix.getMother_19_24_Content_1()+messageMatrix.getMother_19_24_Content_25()+messageMatrix.getMother_19_24_Content_50()+messageMatrix.getMother_19_24_Content_75());

        MessageMatrixDto matrixDto5 = new MessageMatrixDto();
        matrixDto5.setMessageWeek("Total");
        matrixDto5.setContent_1_25(messageMatrix.getMother_1_6_Content_1()+messageMatrix.getMother_7_12_Content_1()+messageMatrix.getMother_13_18_Content_1()+messageMatrix.getMother_19_24_Content_1());
        matrixDto5.setContent_25_50(messageMatrix.getMother_1_6_Content_25()+messageMatrix.getMother_7_12_Content_25()+messageMatrix.getMother_13_18_Content_25()+messageMatrix.getMother_19_24_Content_25());
        matrixDto5.setContent_50_75(messageMatrix.getMother_19_24_Content_50()+messageMatrix.getMother_1_6_Content_50()+messageMatrix.getMother_7_12_Content_50()+messageMatrix.getMother_13_18_Content_50());
        matrixDto5.setContent_75_100(messageMatrix.getMother_19_24_Content_75()+messageMatrix.getMother_1_6_Content_75()+messageMatrix.getMother_7_12_Content_75()+messageMatrix.getMother_13_18_Content_75());
        matrixDto5.setTotal(matrixDto1.getTotal()+matrixDto2.getTotal()+matrixDto3.getTotal()+matrixDto4.getTotal());

        motherMatrixDto.add(matrixDto1);
        motherMatrixDto.add(matrixDto2);
        motherMatrixDto.add(matrixDto3);
        motherMatrixDto.add(matrixDto4);
        motherMatrixDto.add(matrixDto5);

        MessageMatrixDto matrixDto6 = new MessageMatrixDto();
        matrixDto6.setMessageWeek("Message Week 1-6");
        matrixDto6.setContent_1_25(messageMatrix.getChild_1_6_Content_1());
        matrixDto6.setContent_25_50(messageMatrix.getChild_1_6_Content_25());
        matrixDto6.setContent_50_75(messageMatrix.getChild_1_6_Content_50());
        matrixDto6.setContent_75_100(messageMatrix.getChild_1_6_Content_75());
        matrixDto6.setTotal(messageMatrix.getChild_1_6_Content_1()+messageMatrix.getChild_1_6_Content_25()+messageMatrix.getChild_1_6_Content_50()+messageMatrix.getChild_1_6_Content_75());

        MessageMatrixDto matrixDto7 = new MessageMatrixDto();
        matrixDto7.setMessageWeek("Message Week 7-12");
        matrixDto7.setContent_1_25(messageMatrix.getChild_7_12_Content_1());
        matrixDto7.setContent_25_50(messageMatrix.getChild_7_12_Content_25());
        matrixDto7.setContent_50_75(messageMatrix.getChild_7_12_Content_50());
        matrixDto7.setContent_75_100(messageMatrix.getChild_7_12_Content_75());
        matrixDto7.setTotal(messageMatrix.getChild_7_12_Content_1()+messageMatrix.getChild_7_12_Content_25()+messageMatrix.getChild_7_12_Content_50()+messageMatrix.getChild_7_12_Content_75());

        MessageMatrixDto matrixDto8 = new MessageMatrixDto();
        matrixDto8.setMessageWeek("Message Week 13-18");
        matrixDto8.setContent_1_25(messageMatrix.getChild_13_18_Content_1());
        matrixDto8.setContent_25_50(messageMatrix.getChild_13_18_Content_25());
        matrixDto8.setContent_50_75(messageMatrix.getChild_13_18_Content_50());
        matrixDto8.setContent_75_100(messageMatrix.getChild_13_18_Content_75());
        matrixDto8.setTotal(messageMatrix.getChild_13_18_Content_1()+messageMatrix.getChild_13_18_Content_25()+messageMatrix.getChild_13_18_Content_50()+messageMatrix.getChild_13_18_Content_75());

        MessageMatrixDto matrixDto9 = new MessageMatrixDto();
        matrixDto9.setMessageWeek("Message Week 19-24");
        matrixDto9.setContent_1_25(messageMatrix.getChild_19_24_Content_1());
        matrixDto9.setContent_25_50(messageMatrix.getChild_19_24_Content_25());
        matrixDto9.setContent_50_75(messageMatrix.getChild_19_24_Content_50());
        matrixDto9.setContent_75_100(messageMatrix.getChild_19_24_Content_75());
        matrixDto9.setTotal(messageMatrix.getChild_19_24_Content_1()+messageMatrix.getChild_19_24_Content_25()+messageMatrix.getChild_19_24_Content_50()+messageMatrix.getChild_19_24_Content_75());

        MessageMatrixDto matrixDto10 = new MessageMatrixDto();
        matrixDto10.setMessageWeek("Message Week 25-30");
        matrixDto10.setContent_1_25(messageMatrix.getChild_25_30_Content_1());
        matrixDto10.setContent_25_50(messageMatrix.getChild_25_30_Content_25());
        matrixDto10.setContent_50_75(messageMatrix.getChild_25_30_Content_50());
        matrixDto10.setContent_75_100(messageMatrix.getChild_25_30_Content_75());
        matrixDto10.setTotal(messageMatrix.getChild_25_30_Content_1()+messageMatrix.getChild_25_30_Content_25()+messageMatrix.getChild_25_30_Content_50()+messageMatrix.getChild_25_30_Content_75());

        MessageMatrixDto matrixDto11 = new MessageMatrixDto();
        matrixDto11.setMessageWeek("Message Week 31-36");
        matrixDto11.setContent_1_25(messageMatrix.getChild_31_36_Content_1());
        matrixDto11.setContent_25_50(messageMatrix.getChild_31_36_Content_25());
        matrixDto11.setContent_50_75(messageMatrix.getChild_31_36_Content_50());
        matrixDto11.setContent_75_100(messageMatrix.getChild_31_36_Content_75());
        matrixDto11.setTotal(messageMatrix.getChild_31_36_Content_1()+messageMatrix.getChild_31_36_Content_25()+messageMatrix.getChild_31_36_Content_50()+messageMatrix.getChild_31_36_Content_75());

        MessageMatrixDto matrixDto12 = new MessageMatrixDto();
        matrixDto12.setMessageWeek("Message Week 37-42");
        matrixDto12.setContent_1_25(messageMatrix.getChild_37_42_Content_1());
        matrixDto12.setContent_25_50(messageMatrix.getChild_37_42_Content_25());
        matrixDto12.setContent_50_75(messageMatrix.getChild_37_42_Content_50());
        matrixDto12.setContent_75_100(messageMatrix.getChild_37_42_Content_75());
        matrixDto12.setTotal(messageMatrix.getChild_37_42_Content_1()+messageMatrix.getChild_37_42_Content_25()+messageMatrix.getChild_37_42_Content_50()+messageMatrix.getChild_37_42_Content_75());

        MessageMatrixDto matrixDto13 = new MessageMatrixDto();
        matrixDto13.setMessageWeek("Message Week 43-48");
        matrixDto13.setContent_1_25(messageMatrix.getChild_43_48_Content_1());
        matrixDto13.setContent_25_50(messageMatrix.getChild_43_48_Content_25());
        matrixDto13.setContent_50_75(messageMatrix.getChild_43_48_Content_50());
        matrixDto13.setContent_75_100(messageMatrix.getChild_43_48_Content_75());
        matrixDto13.setTotal(messageMatrix.getChild_43_48_Content_1()+messageMatrix.getChild_43_48_Content_25()+messageMatrix.getChild_43_48_Content_50()+messageMatrix.getChild_43_48_Content_75());

        MessageMatrixDto matrixDto14 = new MessageMatrixDto();
        matrixDto14.setMessageWeek("Total");
        matrixDto14.setContent_1_25(messageMatrix.getChild_1_6_Content_1()+messageMatrix.getChild_7_12_Content_1()+messageMatrix.getChild_13_18_Content_1()+messageMatrix.getChild_19_24_Content_1()+messageMatrix.getChild_25_30_Content_1()+messageMatrix.getChild_31_36_Content_1()+messageMatrix.getChild_37_42_Content_1()+messageMatrix.getChild_43_48_Content_1());
        matrixDto14.setContent_25_50(messageMatrix.getChild_1_6_Content_25()+messageMatrix.getChild_7_12_Content_25()+messageMatrix.getChild_13_18_Content_25()+messageMatrix.getChild_19_24_Content_25()+messageMatrix.getChild_25_30_Content_25()+messageMatrix.getChild_31_36_Content_25()+messageMatrix.getChild_37_42_Content_25()+messageMatrix.getChild_43_48_Content_25());
        matrixDto14.setContent_50_75(messageMatrix.getChild_1_6_Content_50()+messageMatrix.getChild_7_12_Content_50()+messageMatrix.getChild_13_18_Content_50()+messageMatrix.getChild_19_24_Content_50()+messageMatrix.getChild_25_30_Content_50()+messageMatrix.getChild_31_36_Content_50()+messageMatrix.getChild_37_42_Content_50()+messageMatrix.getChild_43_48_Content_50());
        matrixDto14.setContent_75_100(messageMatrix.getChild_1_6_Content_75()+messageMatrix.getChild_7_12_Content_75()+messageMatrix.getChild_13_18_Content_75()+messageMatrix.getChild_19_24_Content_75()+messageMatrix.getChild_25_30_Content_75()+messageMatrix.getChild_31_36_Content_75()+messageMatrix.getChild_37_42_Content_75()+messageMatrix.getChild_43_48_Content_75());
        matrixDto14.setTotal(matrixDto6.getTotal()+matrixDto7.getTotal()+matrixDto8.getTotal()+matrixDto9.getTotal()+matrixDto10.getTotal()+matrixDto11.getTotal()+matrixDto12.getTotal()+matrixDto13.getTotal());

        childMatrixDto.add(matrixDto6);
        childMatrixDto.add(matrixDto7);
        childMatrixDto.add(matrixDto8);
        childMatrixDto.add(matrixDto9);
        childMatrixDto.add(matrixDto10);
        childMatrixDto.add(matrixDto11);
        childMatrixDto.add(matrixDto12);
        childMatrixDto.add(matrixDto13);
        childMatrixDto.add(matrixDto14);

        messageMatrixResponseDto.setChildData(childMatrixDto);
        messageMatrixResponseDto.setMotherData(motherMatrixDto);
        return messageMatrixResponseDto;
    }

     /*----------5.3.10. Kilkari Repeat Listener Month-Wise Report -------*/

    @Override
    public AggregateKilkariRepeatListenerMonthWiseDto getKilkariListenerMonthWiseReport(ReportRequest reportRequest){
        AggregateKilkariRepeatListenerMonthWiseDto aggregateKilkariRepeatListenerMonthWiseDto = new AggregateKilkariRepeatListenerMonthWiseDto();

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

        List<KilkariRepeatListenerMonthWiseDto> kilkariRepeatListenerMonthWiseDtoList = new ArrayList<>();
        List<KilkariRepeatListenerMonthWisePercentDto> kilkariRepeatListenerMonthWisePercentDtoList = new ArrayList<>();
        List<KilkariRepeatListenerMonthWise> kilkariRepeatListenerMonthWiseList = kilkariRepeatListenerMonthWiseDao.getListenerData(fromDate,toDate);

        if(kilkariRepeatListenerMonthWiseList != null && !(kilkariRepeatListenerMonthWiseList.isEmpty())){
            for(int i = 0; i < kilkariRepeatListenerMonthWiseList.size(); i++){
                KilkariRepeatListenerMonthWiseDto kilkariRepeatListenerMonthWiseDto = new KilkariRepeatListenerMonthWiseDto();
                KilkariRepeatListenerMonthWisePercentDto kilkariRepeatListenerMonthWisePercentDto = new KilkariRepeatListenerMonthWisePercentDto();
                KilkariRepeatListenerMonthWise kilkariRepeatListenerMonthWise1 = kilkariRepeatListenerMonthWiseList.get(i);
                kilkariRepeatListenerMonthWiseDto.setId(kilkariRepeatListenerMonthWise1.getId());
                kilkariRepeatListenerMonthWiseDto.setMonth("Month "+(i+1));
                kilkariRepeatListenerMonthWiseDto.setDate(kilkariRepeatListenerMonthWise1.getDate());
                kilkariRepeatListenerMonthWiseDto.setFiveCallsAnswered(kilkariRepeatListenerMonthWise1.getFiveCallsAnswered());
                kilkariRepeatListenerMonthWiseDto.setFourCallsAnswered(kilkariRepeatListenerMonthWise1.getFourCallsAnswered());
                kilkariRepeatListenerMonthWiseDto.setThreeCallsAnswered(kilkariRepeatListenerMonthWise1.getThreeCallsAnswered());
                kilkariRepeatListenerMonthWiseDto.setTwoCallsAnswered(kilkariRepeatListenerMonthWise1.getTwoCallsAnswered());
                kilkariRepeatListenerMonthWiseDto.setOneCallAnswered(kilkariRepeatListenerMonthWise1.getOneCallAnswered());
                kilkariRepeatListenerMonthWiseDto.setNoCallsAnswered(kilkariRepeatListenerMonthWise1.getNoCallsAnswered());
                kilkariRepeatListenerMonthWiseDto.setTotal(kilkariRepeatListenerMonthWise1.getFiveCallsAnswered()
                        +kilkariRepeatListenerMonthWise1.getFourCallsAnswered()
                        +kilkariRepeatListenerMonthWise1.getThreeCallsAnswered()
                        +kilkariRepeatListenerMonthWise1.getTwoCallsAnswered()
                        +kilkariRepeatListenerMonthWise1.getOneCallAnswered()
                        +kilkariRepeatListenerMonthWise1.getNoCallsAnswered());
                kilkariRepeatListenerMonthWisePercentDto.setFiveCallsAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getFiveCallsAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setFourCallsAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getFourCallsAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setThreeCallsAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getThreeCallsAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setTwoCallsAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getTwoCallsAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setOneCallAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getOneCallAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setNoCallsAnsweredPercent(((float)kilkariRepeatListenerMonthWiseDto.getNoCallsAnswered()/(float)kilkariRepeatListenerMonthWiseDto.getTotal())*100);
                kilkariRepeatListenerMonthWisePercentDto.setId(kilkariRepeatListenerMonthWise1.getId());
                kilkariRepeatListenerMonthWisePercentDto.setMonth("Month "+(i+1));
                kilkariRepeatListenerMonthWisePercentDto.setDate(kilkariRepeatListenerMonthWise1.getDate());
                kilkariRepeatListenerMonthWiseDtoList.add(kilkariRepeatListenerMonthWiseDto);
                kilkariRepeatListenerMonthWisePercentDtoList.add(kilkariRepeatListenerMonthWisePercentDto);
            }
        }
        aggregateKilkariRepeatListenerMonthWiseDto.setNumberData(kilkariRepeatListenerMonthWiseDtoList);
        aggregateKilkariRepeatListenerMonthWiseDto.setPercentData(kilkariRepeatListenerMonthWisePercentDtoList);

        return aggregateKilkariRepeatListenerMonthWiseDto;
    }


    /*----------5.3.11. Kilkari Call Report -------*/

    @Override
    public List<KilkariCallReportDto> getKilkariCallReport(ReportRequest reportRequest, User currentUser){

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

        List<KilkariCallReportDto> callReportDtos = new ArrayList<>();
        List<KilkariCalls> kilkariCallList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariCallList.addAll(this.getKilkariCallReport(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
            kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(),"Block",toDate));
        } else {
            kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getBlockId(),"Subcenter",toDate));
        }

        if(!(kilkariCallList.isEmpty())){
            for(KilkariCalls a:kilkariCallList){
                KilkariCallReportDto summaryDto1 = new KilkariCallReportDto();
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setBillableMinutes(a.getBillableMinutes());
                summaryDto1.setContent_1_25(a.getContent_1_25());
                summaryDto1.setContent_75_100(a.getContent_75_100());
                summaryDto1.setContent_50_75(a.getContent_50_75());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setContent_25_50(a.getContent_25_50());
                summaryDto1.setAvgDuration(a.getAvgDuration());
                summaryDto1.setCallsAttempted(a.getCallsAttempted());
                summaryDto1.setCallsToInbox(a.getCallsToInbox());
                summaryDto1.setSuccessfulCalls(a.getSuccessfulCalls());
                String locationType = a.getLocationType();
                if(locationType.equalsIgnoreCase("State")){
                    summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if(locationType.equalsIgnoreCase("District")){
                    summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if(locationType.equalsIgnoreCase("Block")){
                    summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if(locationType.equalsIgnoreCase("Subcenter")){
                    summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcenter");
                }
                if(a.getId()!=0){
                    callReportDtos.add(summaryDto1);
                }
            }
        }
        return callReportDtos;
    }


    private List<KilkariCalls> getKilkariCallReport(Integer locationId,String locationType,Date toDate){
        List<KilkariCalls> kilkariCall = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                kilkariCall.add(kilkariCallReportDao.getKilkariCallreport(s.getStateId(),locationType,toDate));
            }

        }  else if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                KilkariCalls stateCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"State",toDate);
                Long callsAttempted = (long)0;
                Long successfulCalls = (long)0;
                Long billableMinutes = (long)0;
                Long callsToInbox = (long)0;
                Integer avgDuration = 0;
                Long content_75_100 = (long)0;
                Long content_50_75 = (long)0;
                Long content_25_50 = (long)0;
                Long content_1_25 = (long)0;
                for(District d:districts){
                    KilkariCalls districtCount = kilkariCallReportDao.getKilkariCallreport(d.getDistrictId(),locationType,toDate);
                    kilkariCall.add(districtCount);
                    callsAttempted+=districtCount.getCallsAttempted();
                    successfulCalls+=districtCount.getSuccessfulCalls();
                    billableMinutes+=districtCount.getBillableMinutes();
                    callsToInbox+=districtCount.getCallsToInbox();
                    avgDuration+=districtCount.getAvgDuration();
                    content_75_100+=districtCount.getContent_75_100();
                    content_50_75+=districtCount.getContent_50_75();
                    content_25_50+=districtCount.getContent_25_50();
                    content_1_25+=districtCount.getContent_1_25();
                }
                KilkariCalls noDistrictCount = new KilkariCalls();
                noDistrictCount.setCallsAttempted(stateCounts.getCallsAttempted()-callsAttempted);
                noDistrictCount.setSuccessfulCalls(stateCounts.getSuccessfulCalls()-successfulCalls);
                noDistrictCount.setBillableMinutes(stateCounts.getBillableMinutes()-billableMinutes);
                noDistrictCount.setCallsToInbox(stateCounts.getCallsToInbox()-callsToInbox);
                noDistrictCount.setAvgDuration(stateCounts.getAvgDuration()-avgDuration);
                noDistrictCount.setContent_1_25(stateCounts.getContent_1_25()-content_1_25);
                noDistrictCount.setContent_25_50(stateCounts.getContent_25_50()-content_25_50);
                noDistrictCount.setContent_50_75(stateCounts.getContent_50_75()-content_50_75);
                noDistrictCount.setContent_75_100(stateCounts.getContent_75_100()-content_75_100);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(noDistrictCount.getAvgDuration()+noDistrictCount.getBillableMinutes()+noDistrictCount.getCallsAttempted()+noDistrictCount.getCallsToInbox()+noDistrictCount.getContent_1_25()+noDistrictCount.getContent_25_50()+noDistrictCount.getContent_50_75()+noDistrictCount.getContent_75_100()+noDistrictCount.getSuccessfulCalls()));
                noDistrictCount.setLocationId((long)(-1));
                kilkariCall.add(noDistrictCount);
            } else if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    KilkariCalls districtCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"District",toDate);
                    Long callsAttempted = (long)0;
                    Long successfulCalls = (long)0;
                    Long billableMinutes = (long)0;
                    Long callsToInbox = (long)0;
                    Integer avgDuration = 0;
                    Long content_75_100 = (long)0;
                    Long content_50_75 = (long)0;
                    Long content_25_50 = (long)0;
                    Long content_1_25 = (long)0;
                    for (Block d : blocks) {
                        KilkariCalls blockCount = kilkariCallReportDao.getKilkariCallreport(d.getBlockId(),locationType,toDate);
                        kilkariCall.add(blockCount);
                        callsAttempted+=blockCount.getCallsAttempted();
                        successfulCalls+=blockCount.getSuccessfulCalls();
                        billableMinutes+=blockCount.getBillableMinutes();
                        callsToInbox+=blockCount.getCallsToInbox();
                        avgDuration+=blockCount.getAvgDuration();
                        content_75_100+=blockCount.getContent_75_100();
                        content_50_75+=blockCount.getContent_50_75();
                        content_25_50+=blockCount.getContent_25_50();
                        content_1_25+=blockCount.getContent_1_25();
                    }
                    KilkariCalls noBlockCount = new KilkariCalls();
                    noBlockCount.setCallsAttempted(districtCounts.getCallsAttempted()-callsAttempted);
                    noBlockCount.setSuccessfulCalls(districtCounts.getSuccessfulCalls()-successfulCalls);
                    noBlockCount.setBillableMinutes(districtCounts.getBillableMinutes()-billableMinutes);
                    noBlockCount.setCallsToInbox(districtCounts.getCallsToInbox()-callsToInbox);
                    noBlockCount.setAvgDuration(districtCounts.getAvgDuration()-avgDuration);
                    noBlockCount.setContent_1_25(districtCounts.getContent_1_25()-content_1_25);
                    noBlockCount.setContent_25_50(districtCounts.getContent_25_50()-content_25_50);
                    noBlockCount.setContent_50_75(districtCounts.getContent_50_75()-content_50_75);
                    noBlockCount.setContent_75_100(districtCounts.getContent_75_100()-content_75_100);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(noBlockCount.getAvgDuration()+noBlockCount.getBillableMinutes()+noBlockCount.getCallsAttempted()+noBlockCount.getCallsToInbox()+noBlockCount.getContent_1_25()+noBlockCount.getContent_25_50()+noBlockCount.getContent_50_75()+noBlockCount.getContent_75_100()+noBlockCount.getSuccessfulCalls()));
                    noBlockCount.setLocationId((long)(-1));
                    kilkariCall.add(noBlockCount);
            } else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    KilkariCalls blockCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"block",toDate);
                    Long callsAttempted = (long)0;
                    Long successfulCalls = (long)0;
                    Long billableMinutes = (long)0;
                    Long callsToInbox = (long)0;
                    Integer avgDuration = 0;
                    Long content_75_100 = (long)0;
                    Long content_50_75 = (long)0;
                    Long content_25_50 = (long)0;
                    Long content_1_25 = (long)0;
                    for(Subcenter s: subcenters){
                        KilkariCalls SubcenterCount = kilkariCallReportDao.getKilkariCallreport(s.getSubcenterId(),locationType,toDate);
                        kilkariCall.add(SubcenterCount);
                        callsAttempted+=SubcenterCount.getCallsAttempted();
                        successfulCalls+=SubcenterCount.getSuccessfulCalls();
                        billableMinutes+=SubcenterCount.getBillableMinutes();
                        callsToInbox+=SubcenterCount.getCallsToInbox();
                        avgDuration+=SubcenterCount.getAvgDuration();
                        content_75_100+=SubcenterCount.getContent_75_100();
                        content_50_75+=SubcenterCount.getContent_50_75();
                        content_25_50+=SubcenterCount.getContent_25_50();
                        content_1_25+=SubcenterCount.getContent_1_25();
                    }
                    KilkariCalls noSubcenterCount = new KilkariCalls();
                    noSubcenterCount.setCallsAttempted(blockCounts.getCallsAttempted()-callsAttempted);
                    noSubcenterCount.setSuccessfulCalls(blockCounts.getSuccessfulCalls()-successfulCalls);
                    noSubcenterCount.setBillableMinutes(blockCounts.getBillableMinutes()-billableMinutes);
                    noSubcenterCount.setCallsToInbox(blockCounts.getCallsToInbox()-callsToInbox);
                    noSubcenterCount.setAvgDuration(blockCounts.getAvgDuration()-avgDuration);
                    noSubcenterCount.setContent_1_25(blockCounts.getContent_1_25()-content_1_25);
                    noSubcenterCount.setContent_25_50(blockCounts.getContent_25_50()-content_25_50);
                    noSubcenterCount.setContent_50_75(blockCounts.getContent_50_75()-content_50_75);
                    noSubcenterCount.setContent_75_100(blockCounts.getContent_75_100()-content_75_100);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId((int)(noSubcenterCount.getAvgDuration()+noSubcenterCount.getBillableMinutes()+noSubcenterCount.getCallsAttempted()+noSubcenterCount.getCallsToInbox()+noSubcenterCount.getContent_1_25()+noSubcenterCount.getContent_25_50()+noSubcenterCount.getContent_50_75()+noSubcenterCount.getContent_75_100()+noSubcenterCount.getSuccessfulCalls()));
                    noSubcenterCount.setLocationId((long)(-1));
                    kilkariCall.add(noSubcenterCount);
                }
        return kilkariCall;
    }
}
