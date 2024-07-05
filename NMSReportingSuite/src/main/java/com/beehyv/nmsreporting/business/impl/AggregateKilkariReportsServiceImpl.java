package com.beehyv.nmsreporting.business.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.business.AggregateKilkariReportsService;
import com.beehyv.nmsreporting.business.BreadCrumbService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service("aggregateKilkariReportsService")
@Transactional

public class AggregateKilkariReportsServiceImpl implements AggregateKilkariReportsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ThemeDao themeDao;

    @Autowired
    private HealthFacilityDao healthFacilitydao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    private AggregateCumulativekilkariDao aggregateCumulativekilkariDao;

    @Autowired
    private KilkariSubscriberReportDao kilkariSubscriberReportDao;

    @Autowired
    private AggregateCumulativeBeneficiaryDao aggregateCumulativeBeneficiaryDao;

    @Autowired
    private BeneficiaryWithRegistrationDateBlockDao beneficiaryWithRegistrationDateBlockDao;

    @Autowired
    private BeneficiaryWithRegistrationDateDistrictDao beneficiaryWithRegistrationDateDistrictDao;

    @Autowired
    private BeneficiaryWithRegistrationDateStateDao beneficiaryWithRegistrationDateStateDao;

    @Autowired
    private BeneficiaryWithRegistrationDateSubCentreDao beneficiaryWithRegistrationDateSubCentreDao;


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

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateKilkariReportsServiceImpl.class);

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

        aCalendar.add(Calendar.DATE, 1);

        toDate = aCalendar.getTime();
        List<AggregateCumulativeKilkariDto> summaryDto = new ArrayList<>();
        List<KilkariCalls> cumulativeSummaryReport = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(0,"State",toDate));
        } else if (reportRequest.getDistrictId() == 0) {
            cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getStateId(),"District",toDate));
        } else if(reportRequest.getBlockId() == 0){
            cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getDistrictId(),"Block",toDate));
        } else {
            cumulativeSummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getBlockId(),"Subcentre",toDate));
        }


        for(KilkariCalls a:cumulativeSummaryReport){
            AggregateCumulativeKilkariDto summaryDto1 = new AggregateCumulativeKilkariDto();
            summaryDto1.setId(a.getId());
            summaryDto1.setLocationId(a.getLocationId());
            summaryDto1.setUniqueBeneficiaries(a.getUniqueBeneficiaries());
            summaryDto1.setSuccessfulCalls(a.getSuccessfulCalls());
            summaryDto1.setBillableMinutes(a.getBillableMinutes());
            summaryDto1.setLocationType(a.getLocationType());
            summaryDto1.setAverageDuration(a.getSuccessfulCalls() == 0 ? 0 : (float)Math.round((a.getBillableMinutes()/(float) a.getSuccessfulCalls()) * 100)/100);
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
            if(locationType.equalsIgnoreCase("Subcentre")){
                summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                summaryDto1.setLink(true);
            }
            if (locationType.equalsIgnoreCase("DifferenceState")) {
                summaryDto1.setLocationName("No District");
                summaryDto1.setLink(true);
            }
            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                summaryDto1.setLocationName("No Block");
                summaryDto1.setLink(true);
            }
            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                summaryDto1.setLocationName("No Subcentre");
                summaryDto1.setLink(true);
            }
            if((summaryDto1.getAverageDuration()+Math.round(summaryDto1.getBillableMinutes()*100)+summaryDto1.getSuccessfulCalls()+summaryDto1.getUniqueBeneficiaries())!=0 && !locationType.equalsIgnoreCase("DifferenceState")){
                summaryDto.add(summaryDto1);
            }
        }
        return summaryDto;
    }

    private List<KilkariCalls> getCumulativeSummaryKilkariReport(Integer locationId,String locationType,Date toDate){
        List<KilkariCalls> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    CumulativeSummary.add(kilkariCallReportDao.getKilkariCallreport(s.getStateId(),locationType,toDate));}
            }
        }
        else if(locationType.equalsIgnoreCase("District")){
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            KilkariCalls stateCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"State",toDate);
            Long uniqueBeneficiaries = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            for(District d:districts){
                KilkariCalls distrcitCount = kilkariCallReportDao.getKilkariCallreport(d.getDistrictId(),locationType,toDate);
                CumulativeSummary.add(kilkariCallReportDao.getKilkariCallreport(d.getDistrictId(),locationType,toDate));
                uniqueBeneficiaries += distrcitCount.getUniqueBeneficiaries();
                successfulCalls += distrcitCount.getSuccessfulCalls();
                billableMinutes += distrcitCount.getBillableMinutes();
            }
            KilkariCalls noDistrictCount = new KilkariCalls();
            noDistrictCount.setUniqueBeneficiaries(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
            noDistrictCount.setSuccessfulCalls(stateCounts.getSuccessfulCalls()-successfulCalls);
            noDistrictCount.setBillableMinutes(stateCounts.getBillableMinutes()-billableMinutes);
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setId((int)(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+stateCounts.getSuccessfulCalls()-successfulCalls+stateCounts.getBillableMinutes()-billableMinutes));
            noDistrictCount.setLocationId((long)(-1));
            CumulativeSummary.add(noDistrictCount);
        } else if(locationType.equalsIgnoreCase("Block")) {
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            KilkariCalls districtCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"District",toDate);
            Long uniqueBeneficiaries = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            for (Block d : blocks) {
                KilkariCalls blockCount = kilkariCallReportDao.getKilkariCallreport(d.getBlockId(),locationType,toDate);
                CumulativeSummary.add(kilkariCallReportDao.getKilkariCallreport(d.getBlockId(), locationType,toDate));
                uniqueBeneficiaries+=blockCount.getUniqueBeneficiaries();
                successfulCalls+=blockCount.getSuccessfulCalls();
                billableMinutes+=blockCount.getBillableMinutes();
            }
            KilkariCalls noBlockCount = new KilkariCalls();
            noBlockCount.setUniqueBeneficiaries(districtCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
            noBlockCount.setSuccessfulCalls(districtCounts.getSuccessfulCalls()-successfulCalls);
            noBlockCount.setBillableMinutes(districtCounts.getBillableMinutes()-billableMinutes);
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setId((int)(districtCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+districtCounts.getSuccessfulCalls()-successfulCalls+districtCounts.getBillableMinutes()-billableMinutes));
            noBlockCount.setLocationId((long)-1);
            CumulativeSummary.add(noBlockCount);
        } else {
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }

            KilkariCalls blockCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"block",toDate);
            Long uniqueBeneficiaries = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            for(HealthSubFacility s: subcenters){
                KilkariCalls SubcenterCount = kilkariCallReportDao.getKilkariCallreport(s.getHealthSubFacilityId(),locationType,toDate);
                CumulativeSummary.add(SubcenterCount);
                uniqueBeneficiaries+=SubcenterCount.getUniqueBeneficiaries();
                successfulCalls+=SubcenterCount.getSuccessfulCalls();
                billableMinutes+=SubcenterCount.getBillableMinutes();
            }
            KilkariCalls noSubcenterCount = new KilkariCalls();
            noSubcenterCount.setUniqueBeneficiaries(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
            noSubcenterCount.setSuccessfulCalls(blockCounts.getSuccessfulCalls()-successfulCalls);
            noSubcenterCount.setBillableMinutes(blockCounts.getBillableMinutes()-billableMinutes);
            noSubcenterCount.setLocationType("DifferenceBlock");
            noSubcenterCount.setId((int)(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+blockCounts.getSuccessfulCalls()-successfulCalls+blockCounts.getBillableMinutes()-billableMinutes));
            noSubcenterCount.setLocationId((long)(-1));
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
        Date fromDate = aCalendar.getTime();

        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 1);
        toDate = aCalendar.getTime();

        List<KilkariSubscriberDto> kilkariSubscriberDtoList = new ArrayList<>();
        List<KilkariSubscriber> kilkariSubscriberReportStart = new ArrayList<>();
        List<KilkariSubscriber> kilkariSubscriberReportEnd = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(0,"State",fromDate, reportRequest.getPeriodType()));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(0,"State",toDate, reportRequest.getPeriodType()));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getStateId(),"District",fromDate, reportRequest.getPeriodType()));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getStateId(),"District",toDate, reportRequest.getPeriodType()));
        } else if(reportRequest.getBlockId() == 0){
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getDistrictId(),"Block",fromDate, reportRequest.getPeriodType()));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getDistrictId(),"Block",toDate, reportRequest.getPeriodType()));
        } else {
            kilkariSubscriberReportStart.addAll(getKilkariSubscriberCount(reportRequest.getBlockId(),"Subcentre",fromDate, reportRequest.getPeriodType()));
            kilkariSubscriberReportEnd.addAll(getKilkariSubscriberCount(reportRequest.getBlockId(),"Subcentre",toDate, reportRequest.getPeriodType()));
        }

        if(!(kilkariSubscriberReportEnd.isEmpty()) && !(kilkariSubscriberReportStart.isEmpty())){
            for(int i = 0; i < kilkariSubscriberReportEnd.size(); i++){
                for(int j = 0; j < kilkariSubscriberReportStart.size(); j++)  {
                    boolean showRow = true;
                    if(kilkariSubscriberReportEnd.get(i).getLocationId().equals(kilkariSubscriberReportStart.get(j).getLocationId())){
                        KilkariSubscriber end = kilkariSubscriberReportEnd.get(i);
                        KilkariSubscriber start = kilkariSubscriberReportStart.get(j);
                        KilkariSubscriberDto kilkariSubscriberDto = new KilkariSubscriberDto();
                        kilkariSubscriberDto.setLocationId(end.getLocationId());
                        kilkariSubscriberDto.setTotalSubscriptionsStart(start.getTotalSubscriptions());
                        kilkariSubscriberDto.setTotalSubscriptionsEnd(end.getTotalSubscriptions());

//                        kilkariSubscriberDto.setTotalRecordsRejected(end.getTotalSubscriptionsRejected()- start.getTotalSubscriptionsRejected());
                        kilkariSubscriberDto.setTotalRecordsRejected(start.getTotalSubscriptionsRejected());

                        kilkariSubscriberDto.setTotalSubscriptionsCompleted(end.getTotalSubscriptionsCompleted()- start.getTotalSubscriptionsCompleted());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsAccepted(end.getTotalSubscriptionsAccepted()-start.getTotalSubscriptionsAccepted());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsEligible(end.getEligibleForSubscriptions()+end.getTotalSubscriptionsAccepted()  - start.getEligibleForSubscriptions()- start.getTotalSubscriptionsAccepted());
                        kilkariSubscriberDto.setTotalBeneficiaryRecordsReceived(end.getTotalRecordsReceived_MCTS_RCH()-start.getTotalRecordsReceived_MCTS_RCH());
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
                        if(locationType.equalsIgnoreCase("Subcentre")){
                            kilkariSubscriberDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(end.getLocationId().intValue()).getHealthSubFacilityName());
                            kilkariSubscriberDto.setLink(true);
                        }
                        if (locationType.equalsIgnoreCase("DifferenceState")) {
                            kilkariSubscriberDto.setLocationName("No District Count");
                            kilkariSubscriberDto.setLink(true);
                            kilkariSubscriberDto.setLocationId((long)-1);
                        }
                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                            kilkariSubscriberDto.setLocationName("No Block Count");
                            kilkariSubscriberDto.setLink(true);
                            kilkariSubscriberDto.setLocationId((long)-1);

                        }
                        if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                            kilkariSubscriberDto.setLocationName("No Subcentre Count");
                            kilkariSubscriberDto.setLink(true);
                            kilkariSubscriberDto.setLocationId((long)-1);

                        }
                        if(end.getLocationType().equalsIgnoreCase("State")&& !serviceStarted(end.getLocationId().intValue(),"State",toDate,fromDate,"KILKARI"))
                        { showRow = false;}
                        if((kilkariSubscriberDto.getTotalSubscriptionsEnd() + kilkariSubscriberDto.getTotalSubscriptionsStart() + kilkariSubscriberDto.getTotalBeneficiaryRecordsReceived()
                                + kilkariSubscriberDto.getTotalBeneficiaryRecordsEligible() + kilkariSubscriberDto.getTotalBeneficiaryRecordsAccepted()
                                + kilkariSubscriberDto.getTotalRecordsRejected() + kilkariSubscriberDto.getTotalSubscriptionsCompleted()) != 0 && !locationType.equalsIgnoreCase("DifferenceState") && showRow){
                            kilkariSubscriberDtoList.add(kilkariSubscriberDto);
                        }
                    }
                }
            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariSubscriberDtoList);
        return aggregateKilkariReportsDto;
    }

    private List<KilkariSubscriber> getKilkariSubscriberCount(Integer locationId, String locationType, Date toDate, String periodType){
        Date date = toDate;
        Date date1 = toDate;

        List<KilkariSubscriber> kilkariSubscribersCountList = new ArrayList<>();

        if(locationType.equalsIgnoreCase("State")) {
            List<State> states = stateDao.getStatesByServiceType("KILKARI");
            for(State state:states){
                if(date.before(stateServiceDao.getServiceStartDateForState(state.getStateId(),"KILKARI"))){
                    date = stateServiceDao.getServiceStartDateForState(state.getStateId(),"KILKARI");
                }

                KilkariSubscriber kilkariSubscriber = kilkariSubscriberReportDao.getKilkariSubscriberCounts(state.getStateId(),locationType, date);
                AggregateCumulativeBeneficiary subscriptionsStateRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(state.getStateId()),"State", date1, periodType);
                kilkariSubscriber.setTotalSubscriptionsRejected(subscriptionsStateRejected.getSubscriptionsRejected().intValue());
                kilkariSubscribersCountList.add(kilkariSubscriber);

                date = toDate;
            }
        } else if(locationType.equalsIgnoreCase("District")){
            if(date.before(stateServiceDao.getServiceStartDateForState(locationId,"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(locationId,"KILKARI");
            }
            List<District> districts = districtDao.getDistrictsOfState(locationId);

            KilkariSubscriber kilkariStateCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId,"State", date);

            AggregateCumulativeBeneficiary subscriptionsStateRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(locationId),"State", date, periodType);
            kilkariStateCounts.setTotalSubscriptionsRejected(subscriptionsStateRejected.getSubscriptionsRejected().intValue());

            Integer totalRecordsReceived_MCTS_RCH = 0;
            Integer eligibleForSubscriptions = 0;
            Integer totalSubscriptionsCompleted = 0;
            Integer totalSubscriptionsAccepted = 0;
            Integer totalSubscriptionsRejected = 0;
            Integer totalSubscriptions = 0;
            for(District district:districts){

                KilkariSubscriber kilkariDistrictCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(district.getDistrictId(),locationType, date);

                AggregateCumulativeBeneficiary subscriptionsDistrictRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(district.getDistrictId()),"District", date, periodType);
                kilkariDistrictCount.setTotalSubscriptionsRejected(subscriptionsDistrictRejected.getSubscriptionsRejected().intValue());


                kilkariSubscribersCountList.add(kilkariDistrictCount);
                totalSubscriptions += kilkariDistrictCount.getTotalSubscriptions();
                totalRecordsReceived_MCTS_RCH += kilkariDistrictCount.getTotalRecordsReceived_MCTS_RCH();
                eligibleForSubscriptions += kilkariDistrictCount.getEligibleForSubscriptions();
                totalSubscriptionsCompleted += kilkariDistrictCount.getTotalSubscriptionsCompleted();
                totalSubscriptionsAccepted += kilkariDistrictCount.getTotalSubscriptionsAccepted();
                totalSubscriptionsRejected += kilkariDistrictCount.getTotalSubscriptionsRejected();
            }
            KilkariSubscriber kilkariNoDistrictCount = new KilkariSubscriber();
            kilkariNoDistrictCount.setTotalSubscriptions(kilkariStateCounts.getTotalSubscriptions()- totalSubscriptions);
            kilkariNoDistrictCount.setTotalRecordsReceived_MCTS_RCH(kilkariStateCounts.getTotalRecordsReceived_MCTS_RCH()- totalRecordsReceived_MCTS_RCH);
            kilkariNoDistrictCount.setEligibleForSubscriptions(kilkariStateCounts.getEligibleForSubscriptions()- eligibleForSubscriptions);
            kilkariNoDistrictCount.setTotalSubscriptionsCompleted(kilkariStateCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
            kilkariNoDistrictCount.setTotalSubscriptionsAccepted(kilkariStateCounts.getTotalSubscriptionsAccepted()- totalSubscriptionsAccepted);
            kilkariNoDistrictCount.setTotalSubscriptionsRejected(kilkariStateCounts.getTotalSubscriptionsRejected()- totalSubscriptionsRejected);

            kilkariNoDistrictCount.setLocationType("DifferenceState");
            kilkariNoDistrictCount.setLocationId((long)-locationId);
            kilkariSubscribersCountList.add(kilkariNoDistrictCount);
        }
        else if(locationType.equalsIgnoreCase("Block")) {
            if(date.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"KILKARI");
            }
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);

            KilkariSubscriber kilkariDistrictCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId,"District", date);

            AggregateCumulativeBeneficiary subscriptionsDistrictRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(locationId),"District", date, periodType);
            kilkariDistrictCounts.setTotalSubscriptionsRejected(subscriptionsDistrictRejected.getSubscriptionsRejected().intValue());

            Integer totalRecordsReceived_MCTS_RCH = 0;
            Integer eligibleForSubscriptions = 0;
            Integer totalSubscriptionsCompleted = 0;
            Integer totalSubscriptionsAccepted = 0;
            Integer totalSubscriptionsRejected = 0;
            Integer totalSubscriptions = 0;
            for (Block block : blocks) {
                KilkariSubscriber kilkariBlockCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(block.getBlockId(),locationType, date);

                AggregateCumulativeBeneficiary subscriptionsBlockRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(block.getBlockId()),"Block", date, periodType);
                kilkariBlockCount.setTotalSubscriptionsRejected(subscriptionsBlockRejected.getSubscriptionsRejected().intValue());

                kilkariSubscribersCountList.add(kilkariBlockCount);
                totalSubscriptions += kilkariBlockCount.getTotalSubscriptions();
                totalRecordsReceived_MCTS_RCH += kilkariBlockCount.getTotalRecordsReceived_MCTS_RCH();
                eligibleForSubscriptions += kilkariBlockCount.getEligibleForSubscriptions();
                totalSubscriptionsCompleted += kilkariBlockCount.getTotalSubscriptionsCompleted();
                totalSubscriptionsAccepted += kilkariBlockCount.getTotalSubscriptionsAccepted();
                totalSubscriptionsRejected += kilkariBlockCount.getTotalSubscriptionsRejected();
            }
            KilkariSubscriber kilkariNoBlockCount = new KilkariSubscriber();
            kilkariNoBlockCount.setTotalSubscriptions(kilkariDistrictCounts.getTotalSubscriptions()- totalSubscriptions);
            kilkariNoBlockCount.setTotalRecordsReceived_MCTS_RCH(kilkariDistrictCounts.getTotalRecordsReceived_MCTS_RCH()- totalRecordsReceived_MCTS_RCH);
            kilkariNoBlockCount.setEligibleForSubscriptions(kilkariDistrictCounts.getEligibleForSubscriptions()- eligibleForSubscriptions);
            kilkariNoBlockCount.setTotalSubscriptionsCompleted(kilkariDistrictCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
            kilkariNoBlockCount.setTotalSubscriptionsAccepted(kilkariDistrictCounts.getTotalSubscriptionsAccepted()- totalSubscriptionsAccepted);
            kilkariNoBlockCount.setTotalSubscriptionsRejected(kilkariDistrictCounts.getTotalSubscriptionsRejected()- totalSubscriptionsRejected);

            kilkariNoBlockCount.setLocationType("DifferenceDistrict");
            kilkariNoBlockCount.setLocationId((long)-locationId);
            kilkariSubscribersCountList.add(kilkariNoBlockCount);
        } else {
            if(date.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"KILKARI");
            }
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            KilkariSubscriber blockCounts = kilkariSubscriberReportDao.getKilkariSubscriberCounts(locationId, "block", date);

            AggregateCumulativeBeneficiary subscriptionsBlockRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(locationId),"Block", date, periodType);
            blockCounts.setTotalSubscriptionsRejected(subscriptionsBlockRejected.getSubscriptionsRejected().intValue());

            Integer totalRecordsReceived_MCTS_RCH = 0;
            Integer eligibleForSubscriptions = 0;
            Integer totalSubscriptionsCompleted = 0;
            Integer totalSubscriptionsAccepted = 0;
            Integer totalSubscriptionsRejected = 0;
            Integer totalSubscriptions = 0;
            for(HealthSubFacility Subcenter : subcenters){
                KilkariSubscriber kilkariSubcenterCount = kilkariSubscriberReportDao.getKilkariSubscriberCounts(Subcenter.getHealthSubFacilityId(),locationType, date);

                AggregateCumulativeBeneficiary subscriptionsSubcentreRejected = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(Long.valueOf(Subcenter.getHealthSubFacilityId()),"Subcentre", date, periodType);
                kilkariSubcenterCount.setTotalSubscriptionsRejected(subscriptionsSubcentreRejected.getSubscriptionsRejected().intValue());

                kilkariSubscribersCountList.add(kilkariSubcenterCount);
                totalSubscriptions += kilkariSubcenterCount.getTotalSubscriptions();
                totalRecordsReceived_MCTS_RCH += kilkariSubcenterCount.getTotalRecordsReceived_MCTS_RCH();
                eligibleForSubscriptions += kilkariSubcenterCount.getEligibleForSubscriptions();
                totalSubscriptionsCompleted += kilkariSubcenterCount.getTotalSubscriptionsCompleted();
                totalSubscriptionsAccepted += kilkariSubcenterCount.getTotalSubscriptionsAccepted();
                totalSubscriptionsRejected += kilkariSubcenterCount.getTotalSubscriptionsRejected();
            }
            KilkariSubscriber kilkariNoSubcenterCount = new KilkariSubscriber();
            kilkariNoSubcenterCount.setTotalSubscriptions(blockCounts.getTotalSubscriptions()- totalSubscriptions);
            kilkariNoSubcenterCount.setTotalRecordsReceived_MCTS_RCH(blockCounts.getTotalRecordsReceived_MCTS_RCH()- totalRecordsReceived_MCTS_RCH);
            kilkariNoSubcenterCount.setEligibleForSubscriptions(blockCounts.getEligibleForSubscriptions()- eligibleForSubscriptions);
            kilkariNoSubcenterCount.setTotalSubscriptionsCompleted(blockCounts.getTotalSubscriptionsCompleted()- totalSubscriptionsCompleted);
            kilkariNoSubcenterCount.setTotalSubscriptionsAccepted(blockCounts.getTotalSubscriptionsAccepted()- totalSubscriptionsAccepted);
            kilkariNoSubcenterCount.setTotalSubscriptionsRejected(blockCounts.getTotalSubscriptionsRejected()- totalSubscriptionsRejected);
            kilkariNoSubcenterCount.setLocationType("DifferenceBlock");
            kilkariNoSubcenterCount.setLocationId((long)-locationId);
            kilkariSubscribersCountList.add(kilkariNoSubcenterCount);
        }
        return kilkariSubscribersCountList;
    }

    /*---------- Kilkari Subscriber Count Report Based on Registration Date -------*/

    @Override
    public AggregateKilkariReportsDto getKilkariSubscriberCountReportBasedOnRegistrationDate(ReportRequest reportRequest){

        LOGGER.info("Operation getKilkariSubscriberCountReportBasedOnRegistrationDate , result = IN_PROGRESS, " +
                        "reportStartDate = {} , reportEndDate = {} , PeriodType = {} " ,
                reportRequest.getFromDate() , reportRequest.getToDate() , reportRequest.getPeriodType());

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
        Date fromDate = aCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-04-01";
        try {
            Date comparisonDate = sdf.parse(dateString);
            if(fromDate.before(comparisonDate)){
                return aggregateKilkariReportsDto;
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 1);
        toDate = aCalendar.getTime();
        List<KilkariSubscriberRegistrationDateDto> kilkariSubscriberList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<KilkariSubscriberRegistrationDateRejectedCountDto> kilkariSubscriberRegistrationDateRejectedCountDtoList = new ArrayList<>();
        Integer locationId ;
        String locationType ;
        if(reportRequest.getStateId() == 0){
            locationId = 0;
            locationType = "State";
        }
        else if(reportRequest.getDistrictId() == 0){
            locationId = reportRequest.getStateId();
            locationType = "District";
        }
        else if(reportRequest.getBlockId() == 0){
            locationId = reportRequest.getDistrictId();
            locationType = "Block";
        }
        else {
            locationId = reportRequest.getBlockId();
            locationType = "SubCenter";
        }

            FetchRegistrationDateReportData registrationDateAllCountData = new RegistrationDateAllCountDataImpl(locationId , locationType , fromDate , toDate);
            FetchRegistrationDateReportData registrationDateDuplicateCountData = new RegistrationDateDuplicateDataImpl(locationId , locationType , fromDate , toDate);

            List<Callable<Object>> tasks = new ArrayList<>();

            tasks.add(registrationDateAllCountData);
            tasks.add(registrationDateDuplicateCountData);
            try {
                List<Future<Object>> futures = executor.invokeAll(tasks);
                kilkariSubscriberList = (List<KilkariSubscriberRegistrationDateDto>) futures.get(0).get();
                kilkariSubscriberRegistrationDateRejectedCountDtoList = (List<KilkariSubscriberRegistrationDateRejectedCountDto>) futures.get(1).get();
                executor.shutdown();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        List<KilkariSubscriberRegistrationDateListDto> kilkariSubscriberRegistrationDateListDtos = new ArrayList<>();
        for(int i=0;i<kilkariSubscriberList.size();i++){
            for(int j=0;j<kilkariSubscriberRegistrationDateRejectedCountDtoList.size();j++) {
                if(kilkariSubscriberList.get(i).getLocationId() == kilkariSubscriberRegistrationDateRejectedCountDtoList.get(j).getLocationId()) {

                    KilkariSubscriberRegistrationDateDto kilkariSubscriberRegistrationDateDto = kilkariSubscriberList.get(i);
                    KilkariSubscriberRegistrationDateRejectedCountDto kilkariSubscriberRegistrationDateRejectedCountDto = kilkariSubscriberRegistrationDateRejectedCountDtoList.get(j);

                    KilkariSubscriberRegistrationDateListDto kilkariSubscriberRegistrationDateListDto = new KilkariSubscriberRegistrationDateListDto();

                    kilkariSubscriberRegistrationDateListDto.setLocationId(kilkariSubscriberRegistrationDateDto.getLocationId());
                    kilkariSubscriberRegistrationDateListDto.setTotalBeneficiaryWithActiveStatus(kilkariSubscriberRegistrationDateDto.getTotalBeneficiaryWithActiveStatus());
                    kilkariSubscriberRegistrationDateListDto.setTotalBeneficiaryWithPendingStatus(kilkariSubscriberRegistrationDateDto.getTotalBeneficiaryWithPendingStatus());
                    kilkariSubscriberRegistrationDateListDto.setTotalBeneficiaryWithOnHoldStatus(kilkariSubscriberRegistrationDateDto.getTotalBeneficiaryWithHoldSubscriptionStatus());
                    kilkariSubscriberRegistrationDateListDto.setTotalBeneficiaryWithDeactivatedStatus(kilkariSubscriberRegistrationDateDto.getTotalBeneficiaryWithDeactivatedStatus());
                    kilkariSubscriberRegistrationDateListDto.setTotalRejectedSubscriberCount(kilkariSubscriberRegistrationDateDto.getTotalRecordsRejected() - kilkariSubscriberRegistrationDateRejectedCountDto.getSubscriberCount());
                    kilkariSubscriberRegistrationDateListDto.setTotalSubscriberCount(kilkariSubscriberRegistrationDateDto.getTotalSubscriptions() - kilkariSubscriberRegistrationDateRejectedCountDto.getSubscriberCount());
                    kilkariSubscriberRegistrationDateListDto.setTotalBeneficiaryWithCompletedStatus(kilkariSubscriberRegistrationDateDto.getTotalSubscriptionsCompletedStatus());
                    locationType = kilkariSubscriberRegistrationDateDto.getLocationType();
                    kilkariSubscriberRegistrationDateListDto.setLocationType(locationType);
                    try {
                        LOGGER.debug("locationType is " + locationType);
                        if (locationType.equalsIgnoreCase("State")) {
                            kilkariSubscriberRegistrationDateListDto.setLocationName(stateDao.findByStateId(kilkariSubscriberRegistrationDateDto.getLocationId().intValue()).getStateName());
                        }
                        if (locationType.equalsIgnoreCase("District")) {
                            kilkariSubscriberRegistrationDateListDto.setLocationName(districtDao.findByDistrictId(kilkariSubscriberRegistrationDateDto.getLocationId().intValue()).getDistrictName());
                        }
                        if (locationType.equalsIgnoreCase("Block")) {
                            kilkariSubscriberRegistrationDateListDto.setLocationName(blockDao.findByblockId(kilkariSubscriberRegistrationDateDto.getLocationId().intValue()).getBlockName());
                        }
                        if (locationType.equalsIgnoreCase("SubCenter")) {
                            kilkariSubscriberRegistrationDateListDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(kilkariSubscriberRegistrationDateDto.getLocationId().intValue()).getHealthSubFacilityName());
                        }
                        if(locationType.equalsIgnoreCase("No State Count")){
                            kilkariSubscriberRegistrationDateListDto.setLocationName("No State Count");
                        }
                        if(locationType.equalsIgnoreCase("No District Count")){
                            kilkariSubscriberRegistrationDateListDto.setLocationName("No District Count");
                        }
                        if(locationType.equalsIgnoreCase("No Block Count")){
                            kilkariSubscriberRegistrationDateListDto.setLocationName("No Block Count");
                        }
                        if(locationType.equalsIgnoreCase("No SubCenter Count")){
                            kilkariSubscriberRegistrationDateListDto.setLocationName("No SubCenter Count");
                        }
                    } catch (Exception e) {
                        LOGGER.info("Location not found with id " + kilkariSubscriberRegistrationDateDto.getLocationId().intValue());
                        e.printStackTrace();
                    }

                    if ((kilkariSubscriberRegistrationDateListDto.getTotalBeneficiaryWithActiveStatus()
                            + kilkariSubscriberRegistrationDateListDto.getTotalBeneficiaryWithOnHoldStatus() + kilkariSubscriberRegistrationDateListDto.getTotalBeneficiaryWithDeactivatedStatus()
                            + kilkariSubscriberRegistrationDateListDto.getTotalBeneficiaryWithPendingStatus() + kilkariSubscriberRegistrationDateListDto.getTotalSubscriberCount()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
                        LOGGER.debug("Adding record for " + kilkariSubscriberRegistrationDateListDto.getLocationName());
                        kilkariSubscriberRegistrationDateListDtos.add(kilkariSubscriberRegistrationDateListDto);
                    }
                }

            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariSubscriberRegistrationDateListDtos);
        LOGGER.info("Operation = getKilkariSubscriberCountReportBasedOnRegistrationDate , status = COMPLETED Report Data generated " );
        return  aggregateKilkariReportsDto;
    }
    interface FetchRegistrationDateReportData<R> extends Callable<R> { }
    class RegistrationDateAllCountDataImpl implements FetchRegistrationDateReportData {
        Integer locationId ;
        String locationType ;
        Date fromDate;
        Date toDate;

        public RegistrationDateAllCountDataImpl(Integer locationId, String locationType, Date fromDate, Date toDate) {
            this.locationId = locationId;
            this.locationType = locationType;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        public List<KilkariSubscriberRegistrationDateDto> call(){
            return getKilkariSubscriberCountRegistrationDateWise(this.locationId , this.locationType , this.fromDate , this.toDate);
        }

        private List<KilkariSubscriberRegistrationDateDto> getKilkariSubscriberCountRegistrationDateWise (Integer locationId , String locationType, Date fromDate, Date toDate ){
            List<KilkariSubscriberRegistrationDateDto> kilkariSubscriberRegistrationDateDtoList = new ArrayList<KilkariSubscriberRegistrationDateDto>();
            if(locationType.equalsIgnoreCase("State")){
                kilkariSubscriberRegistrationDateDtoList = beneficiaryWithRegistrationDateStateDao.allCountOffReports( fromDate, toDate);
            }
            else if(locationType.equalsIgnoreCase("District")){
                kilkariSubscriberRegistrationDateDtoList = beneficiaryWithRegistrationDateDistrictDao.allCountOffReports(locationId, fromDate, toDate);
            }
            else if(locationType.equalsIgnoreCase("Block")) {
                kilkariSubscriberRegistrationDateDtoList = beneficiaryWithRegistrationDateBlockDao.allCountOffReports(locationId, fromDate, toDate);
            }
            else if (locationType.equalsIgnoreCase("SubCenter")){
                kilkariSubscriberRegistrationDateDtoList = beneficiaryWithRegistrationDateSubCentreDao.allCountOffReports(locationId , fromDate , toDate);
            }
            return kilkariSubscriberRegistrationDateDtoList;
        }
    }

    class RegistrationDateDuplicateDataImpl implements FetchRegistrationDateReportData {
        Integer locationId ;
        String locationType ;
        Date fromDate;
        Date toDate;

        public RegistrationDateDuplicateDataImpl(Integer locationId, String locationType, Date fromDate, Date toDate) {
            this.locationId = locationId;
            this.locationType = locationType;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }
        @Override
        public List<KilkariSubscriberRegistrationDateRejectedCountDto> call(){
            return getKilkariSubscriberCountRegistrationDateWise(this.locationId , this.locationType , this.fromDate , this.toDate);
        }

        private List<KilkariSubscriberRegistrationDateRejectedCountDto> getKilkariSubscriberCountRegistrationDateWise (Integer locationId , String locationType, Date fromDate, Date toDate ){
            List<KilkariSubscriberRegistrationDateRejectedCountDto> kilkariSubscriberRegistrationDateRejectedCountDtoList = new ArrayList<>();
            if(locationType.equalsIgnoreCase("State")){
                kilkariSubscriberRegistrationDateRejectedCountDtoList = beneficiaryWithRegistrationDateStateDao.duplicateRejectedSubscriberCount( fromDate, toDate);
            }
            else if(locationType.equalsIgnoreCase("District")){
                kilkariSubscriberRegistrationDateRejectedCountDtoList = beneficiaryWithRegistrationDateDistrictDao.duplicateRejectedSubscriberCount(locationId, fromDate, toDate);
            }
            else if(locationType.equalsIgnoreCase("Block")) {
                kilkariSubscriberRegistrationDateRejectedCountDtoList = beneficiaryWithRegistrationDateBlockDao.duplicateRejectedSubscriberCount(locationId, fromDate, toDate);
            }
            else if (locationType.equalsIgnoreCase("SubCenter")){
                kilkariSubscriberRegistrationDateRejectedCountDtoList = beneficiaryWithRegistrationDateSubCentreDao.duplicateRejectedSubscriberCount(locationId , fromDate , toDate);
            }
            return kilkariSubscriberRegistrationDateRejectedCountDtoList;
        }
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


        aCalendar.add(Calendar.DATE, 0);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();


        List<KilkariAggregateBeneficiariesDto> summaryDto = new ArrayList<>();
        List<KilkariAggregateBeneficiariesDto> aggregateCumulativeBeneficiaryList = new ArrayList<>();


        if (reportRequest.getStateId() == 0) {
            aggregateCumulativeBeneficiaryList.addAll(this.getCumulativeBeneficiary(0, "State", fromDate,toDate,reportRequest.getPeriodType()));
        } else if (reportRequest.getDistrictId() == 0) {
            aggregateCumulativeBeneficiaryList.addAll(this.getCumulativeBeneficiary(reportRequest.getStateId(), "District", fromDate,toDate,reportRequest.getPeriodType()));
        } else if (reportRequest.getBlockId() == 0) {
            aggregateCumulativeBeneficiaryList.addAll(this.getCumulativeBeneficiary(reportRequest.getDistrictId(), "Block", fromDate,toDate,reportRequest.getPeriodType()));
        } else {
            aggregateCumulativeBeneficiaryList.addAll(this.getCumulativeBeneficiary(reportRequest.getBlockId(), "Subcentre", fromDate,toDate,reportRequest.getPeriodType()));
        }


        if(!(aggregateCumulativeBeneficiaryList.isEmpty())){
            for (int i = 0; i < aggregateCumulativeBeneficiaryList.size(); i++) {
                KilkariAggregateBeneficiariesDto a = aggregateCumulativeBeneficiaryList.get(i);
                KilkariAggregateBeneficiariesDto summaryDto1 = new KilkariAggregateBeneficiariesDto();
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setSelfDeactivated(a.getSelfDeactivated());
                summaryDto1.setJoinedSubscription(a.getJoinedSubscription());
                summaryDto1.setCalledInbox(a.getCalledInbox());
                summaryDto1.setMotherCompletion(a.getMotherCompletion());
                summaryDto1.setChildCompletion(a.getChildCompletion());
                summaryDto1.setLowListenership(a.getLowListenership());
                summaryDto1.setNotAnswering(a.getNotAnswering());
                summaryDto1.setSystemDeactivation(a.getSystemDeactivation());
                summaryDto1.setBeneficiariesCalled(a.getBeneficiariesCalled());
                summaryDto1.setAnsweredAtleastOneCall(a.getAnsweredAtleastOneCall());
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
                if (locationType.equalsIgnoreCase("Subcentre")) {
                    summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcentre");
                    summaryDto1.setLink(true);
                }
                if ((a.getBeneficiariesCalled()+a.getAnsweredAtleastOneCall()+a.getSelfDeactivated()+
                        a.getNotAnswering()+a.getLowListenership()+a.getSystemDeactivation()+a.getMotherCompletion()+
                        a.getChildCompletion()+a.getCalledInbox()+a.getJoinedSubscription() != 0)
                        && !locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto.add(summaryDto1);
                }
            }
        }
        return summaryDto;
    }

    private List<KilkariAggregateBeneficiariesDto> getCumulativeBeneficiary(Integer locationId, String locationType, Date fromDate,Date toDate, String periodType){
        List<KilkariAggregateBeneficiariesDto> CumulativeBeneficiary = new ArrayList<>();
        KilkariAggregateBeneficiariesDto kilkariAggregateBeneficiariesDto = new KilkariAggregateBeneficiariesDto();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states = stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    AggregateCumulativeBeneficiary stateCount1=(aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)s.getStateId(),locationType,fromDate,periodType));
                    KilkariUsage stateCount2=(kilkariUsageDao.getUsage(s.getStateId(),locationType,fromDate,periodType));
                    KilkariMessageListenership stateCount3 = kilkariMessageListenershipReportDao.getListenerData(s.getStateId(),locationType,fromDate,periodType);
                    KilkariAggregateBeneficiariesDto stateCount = new KilkariAggregateBeneficiariesDto();
                    stateCount.setJoinedSubscription(stateCount1.getJoinedSubscription());
                    stateCount.setChildCompletion(stateCount1.getChildCompletion());
                    stateCount.setMotherCompletion(stateCount1.getMotherCompletion());
                    stateCount.setLowListenership(stateCount1.getLowListenership());
                    stateCount.setSelfDeactivated(stateCount1.getSelfDeactivated());
                    stateCount.setNotAnswering(stateCount1.getNotAnswering());
                    stateCount.setSystemDeactivation(stateCount1.getSystemDeactivation());
                    stateCount.setCalledInbox(stateCount2.getCalledInbox());
                    stateCount.setBeneficiariesCalled(stateCount3.getTotalBeneficiariesCalled());
                    stateCount.setAnsweredAtleastOneCall(stateCount3.getAnsweredAtleastOneCall());
                    stateCount.setLocationType(locationType);
                    stateCount.setLocationId((long)s.getStateId());
                    stateCount.setId((int)(stateCount.getSystemDeactivation()+stateCount.getNotAnswering()+stateCount.getLowListenership()+stateCount.getChildCompletion()+stateCount.getCalledInbox()+stateCount.getJoinedSubscription()+stateCount.getMotherCompletion()+stateCount.getSelfDeactivated()));
                    CumulativeBeneficiary.add(stateCount);

                }}
        } else if(locationType.equalsIgnoreCase("District")){
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            AggregateCumulativeBeneficiary stateCounts1 = (aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,"State",fromDate,periodType));
            KilkariUsage stateCounts2=(kilkariUsageDao.getUsage(locationId,"State",fromDate,periodType));
            KilkariMessageListenership stateCounts3 = kilkariMessageListenershipReportDao.getListenerData(locationId,"State",fromDate,periodType);

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
                AggregateCumulativeBeneficiary districtCount1 = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)d.getDistrictId(),locationType,fromDate,periodType);
                KilkariUsage districtCount2=(kilkariUsageDao.getUsage(d.getDistrictId(),locationType,fromDate,periodType));
                KilkariMessageListenership districtCount3 = kilkariMessageListenershipReportDao.getListenerData(d.getDistrictId(),locationType,fromDate,periodType);
                KilkariAggregateBeneficiariesDto districtCount = new KilkariAggregateBeneficiariesDto();
                districtCount.setJoinedSubscription(districtCount1.getJoinedSubscription());
                districtCount.setChildCompletion(districtCount1.getChildCompletion());
                districtCount.setMotherCompletion(districtCount1.getMotherCompletion());
                districtCount.setLowListenership(districtCount1.getLowListenership());
                districtCount.setSelfDeactivated(districtCount1.getSelfDeactivated());
                districtCount.setNotAnswering(districtCount1.getNotAnswering());
                districtCount.setSystemDeactivation(districtCount1.getSystemDeactivation());
                districtCount.setCalledInbox(districtCount2.getCalledInbox());
                districtCount.setBeneficiariesCalled(districtCount3.getTotalBeneficiariesCalled());
                districtCount.setAnsweredAtleastOneCall(districtCount3.getAnsweredAtleastOneCall());
                districtCount.setLocationType(locationType);
                districtCount.setLocationId((long)d.getDistrictId());
                districtCount.setId((int)(districtCount.getSystemDeactivation()+districtCount.getNotAnswering()+districtCount.getLowListenership()+districtCount.getChildCompletion()+districtCount.getCalledInbox()+districtCount.getJoinedSubscription()+districtCount.getMotherCompletion()+districtCount.getSelfDeactivated()));
                CumulativeBeneficiary.add(districtCount);


                beneficiariesCalled += districtCount.getBeneficiariesCalled();
                beneficiariesAnsweredAtleastOnce += districtCount.getAnsweredAtleastOneCall();
                selfDeactivated += districtCount.getSelfDeactivated();
                notAnswering += districtCount.getNotAnswering();
                lowListenership += districtCount.getLowListenership();
                systemDeactivation += districtCount.getSystemDeactivation();
                motherCompletion += districtCount.getMotherCompletion();
                childCompletion += districtCount.getChildCompletion();
                calledInbox += districtCount.getCalledInbox();
                joinedSubscription += districtCount.getJoinedSubscription();
            }
            KilkariAggregateBeneficiariesDto noDistrictCount = new KilkariAggregateBeneficiariesDto();
            noDistrictCount.setAnsweredAtleastOneCall(stateCounts3.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
            noDistrictCount.setBeneficiariesCalled(stateCounts3.getTotalBeneficiariesCalled() - beneficiariesCalled);
            noDistrictCount.setSelfDeactivated(stateCounts1.getSelfDeactivated()-selfDeactivated);
            noDistrictCount.setNotAnswering(stateCounts1.getNotAnswering()-notAnswering);
            noDistrictCount.setLowListenership(stateCounts1.getLowListenership()-lowListenership);
            noDistrictCount.setSystemDeactivation(stateCounts1.getSystemDeactivation()-systemDeactivation);
            noDistrictCount.setMotherCompletion(stateCounts1.getMotherCompletion()-motherCompletion);
            noDistrictCount.setChildCompletion(stateCounts1.getChildCompletion()-childCompletion);
            noDistrictCount.setCalledInbox(stateCounts2.getCalledInbox()-calledInbox);
            noDistrictCount.setJoinedSubscription(stateCounts1.getJoinedSubscription()-joinedSubscription);
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setId((int)(noDistrictCount.getSystemDeactivation()+noDistrictCount.getNotAnswering()+noDistrictCount.getLowListenership()+noDistrictCount.getChildCompletion()+noDistrictCount.getCalledInbox()+noDistrictCount.getJoinedSubscription()+noDistrictCount.getMotherCompletion()+noDistrictCount.getSelfDeactivated()));
            noDistrictCount.setLocationId((long)(-1));
            CumulativeBeneficiary.add(noDistrictCount);

        } else if(locationType.equalsIgnoreCase("Block")) {
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            AggregateCumulativeBeneficiary districtCounts1 = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,"District",fromDate,periodType);
            KilkariUsage districtCounts2=(kilkariUsageDao.getUsage(locationId,"District",fromDate,periodType));
            KilkariMessageListenership districtCounts3 = kilkariMessageListenershipReportDao.getListenerData(locationId,"District",fromDate,periodType);


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
                AggregateCumulativeBeneficiary blockCount1 = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)d.getBlockId(),locationType,fromDate,periodType);
                KilkariUsage blockCount2=(kilkariUsageDao.getUsage(d.getBlockId(),locationType,fromDate,periodType));
                KilkariMessageListenership blockCount3 = kilkariMessageListenershipReportDao.getListenerData(d.getBlockId(),locationType,fromDate,periodType);
                KilkariAggregateBeneficiariesDto blockCount = new KilkariAggregateBeneficiariesDto();
                blockCount.setJoinedSubscription(blockCount1.getJoinedSubscription());
                blockCount.setChildCompletion(blockCount1.getChildCompletion());
                blockCount.setMotherCompletion(blockCount1.getMotherCompletion());
                blockCount.setLowListenership(blockCount1.getLowListenership());
                blockCount.setSelfDeactivated(blockCount1.getSelfDeactivated());
                blockCount.setNotAnswering(blockCount1.getNotAnswering());
                blockCount.setSystemDeactivation(blockCount1.getSystemDeactivation());
                blockCount.setCalledInbox(blockCount2.getCalledInbox());
                blockCount.setBeneficiariesCalled(blockCount3.getTotalBeneficiariesCalled());
                blockCount.setAnsweredAtleastOneCall(blockCount3.getAnsweredAtleastOneCall());
                blockCount.setLocationType(locationType);
                blockCount.setLocationId((long)d.getBlockId());
                blockCount.setId((int)(blockCount.getSystemDeactivation()+blockCount.getNotAnswering()+blockCount.getLowListenership()+blockCount.getChildCompletion()+blockCount.getCalledInbox()+blockCount.getJoinedSubscription()+blockCount.getMotherCompletion()+blockCount.getSelfDeactivated()));
                CumulativeBeneficiary.add(blockCount);

                beneficiariesCalled += blockCount.getBeneficiariesCalled();
                beneficiariesAnsweredAtleastOnce += blockCount.getAnsweredAtleastOneCall();
                selfDeactivated += blockCount.getSelfDeactivated();
                notAnswering += blockCount.getNotAnswering();
                lowListenership += blockCount.getLowListenership();
                systemDeactivation += blockCount.getSystemDeactivation();
                motherCompletion += blockCount.getMotherCompletion();
                childCompletion += blockCount.getChildCompletion();
                calledInbox += blockCount.getCalledInbox();
                joinedSubscription += blockCount.getJoinedSubscription();
            }
            KilkariAggregateBeneficiariesDto noBlockCount = new KilkariAggregateBeneficiariesDto();
            noBlockCount.setSelfDeactivated(districtCounts1.getSelfDeactivated()-selfDeactivated);
            noBlockCount.setBeneficiariesCalled(districtCounts3.getTotalBeneficiariesCalled() - beneficiariesCalled);
            noBlockCount.setAnsweredAtleastOneCall(districtCounts3.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
            noBlockCount.setNotAnswering(districtCounts1.getNotAnswering()-notAnswering);
            noBlockCount.setLowListenership(districtCounts1.getLowListenership()-lowListenership);
            noBlockCount.setSystemDeactivation(districtCounts1.getSystemDeactivation()-systemDeactivation);
            noBlockCount.setMotherCompletion(districtCounts1.getMotherCompletion()-motherCompletion);
            noBlockCount.setChildCompletion(districtCounts1.getChildCompletion()-childCompletion);
            noBlockCount.setCalledInbox(districtCounts2.getCalledInbox()-calledInbox);
            noBlockCount.setJoinedSubscription(districtCounts1.getJoinedSubscription()-joinedSubscription);
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setId((int)(noBlockCount.getSystemDeactivation()+noBlockCount.getNotAnswering()+noBlockCount.getLowListenership()+noBlockCount.getChildCompletion()+noBlockCount.getCalledInbox()+noBlockCount.getJoinedSubscription()+noBlockCount.getMotherCompletion()+noBlockCount.getSelfDeactivated()));
            noBlockCount.setLocationId((long)-1);
            CumulativeBeneficiary.add(noBlockCount);
        } else {
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            AggregateCumulativeBeneficiary blockCounts1 = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)locationId,"block",fromDate,periodType);
            KilkariUsage blockCounts2=(kilkariUsageDao.getUsage(locationId,"block",fromDate,periodType));
            KilkariMessageListenership blockCounts3 = kilkariMessageListenershipReportDao.getListenerData(locationId,"block",fromDate,periodType);

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
            for(HealthSubFacility s: subcenters){
                AggregateCumulativeBeneficiary subcenterCount1 = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary((long)s.getHealthSubFacilityId(),locationType,fromDate,periodType);
                KilkariUsage subcenterCount2=(kilkariUsageDao.getUsage(s.getHealthSubFacilityId(),locationType,fromDate,periodType));
                KilkariMessageListenership subcenterCount3 = kilkariMessageListenershipReportDao.getListenerData(s.getHealthSubFacilityId(),locationType,fromDate,periodType);
                KilkariAggregateBeneficiariesDto subCenterCount = new KilkariAggregateBeneficiariesDto();
                subCenterCount.setJoinedSubscription(subcenterCount1.getJoinedSubscription());
                subCenterCount.setChildCompletion(subcenterCount1.getChildCompletion());
                subCenterCount.setMotherCompletion(subcenterCount1.getMotherCompletion());
                subCenterCount.setLowListenership(subcenterCount1.getLowListenership());
                subCenterCount.setSelfDeactivated(subcenterCount1.getSelfDeactivated());
                subCenterCount.setNotAnswering(subcenterCount1.getNotAnswering());
                subCenterCount.setSystemDeactivation(subcenterCount1.getSystemDeactivation());
                subCenterCount.setCalledInbox(subcenterCount2.getCalledInbox());
                subCenterCount.setBeneficiariesCalled(subcenterCount3.getTotalBeneficiariesCalled());
                subCenterCount.setAnsweredAtleastOneCall(subcenterCount3.getAnsweredAtleastOneCall());
                subCenterCount.setLocationType(locationType);
                subCenterCount.setLocationId((long)s.getHealthSubFacilityId());
                subCenterCount.setId((int)(subCenterCount.getSystemDeactivation()+subCenterCount.getNotAnswering()+subCenterCount.getLowListenership()+subCenterCount.getChildCompletion()+subCenterCount.getCalledInbox()+subCenterCount.getJoinedSubscription()+subCenterCount.getMotherCompletion()+subCenterCount.getSelfDeactivated()));
                CumulativeBeneficiary.add(subCenterCount);

                beneficiariesCalled += subCenterCount.getBeneficiariesCalled();
                beneficiariesAnsweredAtleastOnce += subCenterCount.getAnsweredAtleastOneCall();
                selfDeactivated += subCenterCount.getSelfDeactivated();
                notAnswering += subCenterCount.getNotAnswering();
                lowListenership += subCenterCount.getLowListenership();
                systemDeactivation += subCenterCount.getSystemDeactivation();
                motherCompletion += subCenterCount.getMotherCompletion();
                childCompletion += subCenterCount.getChildCompletion();
                calledInbox += subCenterCount.getCalledInbox();
                joinedSubscription += subCenterCount.getJoinedSubscription();
            }
            KilkariAggregateBeneficiariesDto noSubcenterCount = new KilkariAggregateBeneficiariesDto();
            noSubcenterCount.setAnsweredAtleastOneCall(blockCounts3.getAnsweredAtleastOneCall() - beneficiariesAnsweredAtleastOnce);
            noSubcenterCount.setBeneficiariesCalled(blockCounts3.getTotalBeneficiariesCalled() - beneficiariesCalled);
            noSubcenterCount.setSelfDeactivated(blockCounts1.getSelfDeactivated()-selfDeactivated);
            noSubcenterCount.setNotAnswering(blockCounts1.getNotAnswering()-notAnswering);
            noSubcenterCount.setLowListenership(blockCounts1.getLowListenership()-lowListenership);
            noSubcenterCount.setSystemDeactivation(blockCounts1.getSystemDeactivation()-systemDeactivation);
            noSubcenterCount.setMotherCompletion(blockCounts1.getMotherCompletion()-motherCompletion);
            noSubcenterCount.setChildCompletion(blockCounts1.getChildCompletion()-childCompletion);
            noSubcenterCount.setCalledInbox(blockCounts2.getCalledInbox()-calledInbox);
            noSubcenterCount.setJoinedSubscription(blockCounts1.getJoinedSubscription()-joinedSubscription);
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


        aCalendar.add(Calendar.DATE, 0);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        List<UsageDto> summaryDto = new ArrayList<>();
        List<UsageDto> kilkariUsageList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariUsageList.addAll(this.getKilkariUsage(0,"State",fromDate,toDate,reportRequest.getPeriodType()));
        }
        else if (reportRequest.getDistrictId() == 0) {
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getStateId(),"District",fromDate,toDate,reportRequest.getPeriodType()));
        } else if(reportRequest.getBlockId() == 0){
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getDistrictId(),"Block",fromDate,toDate,reportRequest.getPeriodType()));
        }
        else {
            kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getBlockId(),"Subcentre",fromDate,toDate,reportRequest.getPeriodType()));
        }

        if(!(kilkariUsageList.isEmpty())){
            for(UsageDto a:kilkariUsageList){
                UsageDto summaryDto1 = new UsageDto();
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setCalls_75_100(a.getCalls_75_100());
                summaryDto1.setCalls_50_75(a.getCalls_50_75());
                summaryDto1.setCalls_25_50(a.getCalls_25_50());

                summaryDto1.setCalls_1_25(a.getCalls_1_25());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setCalledInbox(a.getCalledInbox());
                summaryDto1.setBeneficiariesCalled(a.getBeneficiariesCalled());
                summaryDto1.setAnsweredCall(a.getAnsweredCall());
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
                if(locationType.equalsIgnoreCase("Subcentre")){
                    summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcentre");
                    summaryDto1.setLink(true);
                }

                if(summaryDto1.getAnsweredCall()+summaryDto1.getBeneficiariesCalled()+summaryDto1.getCalledInbox()+
                        summaryDto1.getCalls_1_25()+summaryDto1.getCalls_25_50()+
                        summaryDto1.getCalls_50_75()+summaryDto1.getCalls_75_100()!=0&&
                        !locationType.equalsIgnoreCase("DifferenceState")){
                    summaryDto.add(summaryDto1);
                }
            }
        }
        return summaryDto;
    }

    private List<UsageDto> getKilkariUsage(Integer locationId,String locationType,Date fromDate,Date toDate,String periodType){
        List<UsageDto> KilkariUsageDto = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    KilkariUsage statecount1=(kilkariUsageDao.getUsage(s.getStateId(),locationType,fromDate,periodType));
                    KilkariMessageListenership statecount2 = kilkariMessageListenershipReportDao.getListenerData(s.getStateId(),locationType,fromDate,periodType);
                    UsageDto stateCount = new UsageDto();
                    stateCount.setLocationId(statecount1.getLocationId());
                    stateCount.setLocationType(statecount1.getLocationType());
                    stateCount.setCalledInbox(statecount1.getCalledInbox());
                    stateCount.setCalls_1_25(statecount1.getCalls_1_25());
                    stateCount.setCalls_25_50(statecount1.getCalls_25_50());
                    stateCount.setCalls_50_75(statecount1.getCalls_50_75());
                    stateCount.setCalls_75_100(statecount1.getCalls_75_100());
                    stateCount.setBeneficiariesCalled(statecount2.getTotalBeneficiariesCalled());
                    stateCount.setAnsweredCall(statecount2.getAnsweredAtleastOneCall());
                    KilkariUsageDto.add(stateCount);

                }}
        }
        else if(locationType.equalsIgnoreCase("District")){
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            KilkariUsage statecount1 = kilkariUsageDao.getUsage(locationId,"State",fromDate,periodType);
            KilkariMessageListenership statecount2 = kilkariMessageListenershipReportDao.getListenerData(locationId,"State",fromDate,periodType);

            Long beneficiariesCalled = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Long calledInbox = (long)0;
            Long atLeastOneCall = (long)0;
            for(District d:districts){
                KilkariUsage distrcitCount1 = kilkariUsageDao.getUsage(d.getDistrictId(),locationType,fromDate,periodType);
                KilkariMessageListenership districtcount2 = kilkariMessageListenershipReportDao.getListenerData(d.getDistrictId(),locationType,fromDate,periodType);
                UsageDto distrcitCount = new UsageDto();
                distrcitCount.setLocationId(distrcitCount1.getLocationId());
                distrcitCount.setLocationType(distrcitCount1.getLocationType());
                distrcitCount.setCalledInbox(distrcitCount1.getCalledInbox());
                distrcitCount.setCalls_1_25(distrcitCount1.getCalls_1_25());
                distrcitCount.setCalls_25_50(distrcitCount1.getCalls_25_50());
                distrcitCount.setCalls_50_75(distrcitCount1.getCalls_50_75());
                distrcitCount.setCalls_75_100(distrcitCount1.getCalls_75_100());
                distrcitCount.setBeneficiariesCalled(districtcount2.getTotalBeneficiariesCalled());
                distrcitCount.setAnsweredCall(districtcount2.getAnsweredAtleastOneCall());
                KilkariUsageDto.add(distrcitCount);

                beneficiariesCalled+=distrcitCount.getBeneficiariesCalled();
                calls_75_100+=distrcitCount.getCalls_75_100();
                calls_50_75+=distrcitCount.getCalls_50_75();
                calls_25_50+=distrcitCount.getCalls_25_50();
                calls_1_25+=distrcitCount.getCalls_1_25();
                calledInbox+=distrcitCount.getCalledInbox();
                atLeastOneCall+=distrcitCount.getAnsweredCall();

            }
            UsageDto noDistrictCount = new UsageDto();
            noDistrictCount.setBeneficiariesCalled(statecount2.getTotalBeneficiariesCalled() - beneficiariesCalled);
            noDistrictCount.setCalls_75_100(statecount1.getCalls_75_100() - calls_75_100);
            noDistrictCount.setCalls_50_75(statecount1.getCalls_50_75() - calls_50_75);
            noDistrictCount.setCalls_25_50(statecount1.getCalls_25_50() - calls_25_50);
            noDistrictCount.setCalls_1_25(statecount1.getCalls_1_25() - calls_1_25);
            noDistrictCount.setCalledInbox(statecount1.getCalledInbox() - calledInbox);
            noDistrictCount.setAnsweredCall(statecount2.getAnsweredAtleastOneCall() - atLeastOneCall);
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setLocationId((long)(-1));
            KilkariUsageDto.add(noDistrictCount);
        }
        else if(locationType.equalsIgnoreCase("Block")) {
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            KilkariUsage districtCount1 = kilkariUsageDao.getUsage(locationId,"District",fromDate,periodType);
            KilkariMessageListenership districtCount2 = kilkariMessageListenershipReportDao.getListenerData(locationId,"District",fromDate,periodType);

            Long beneficiariesCalled = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Long calledInbox = (long)0;
            Long atLeastOneCall = (long)0;

            for (Block d : blocks) {
                KilkariUsage blockCount1 = kilkariUsageDao.getUsage(d.getBlockId(),locationType,fromDate,periodType);
                KilkariMessageListenership blockCount2 = kilkariMessageListenershipReportDao.getListenerData(d.getBlockId(),locationType,fromDate,periodType);
                UsageDto blockCount = new UsageDto();
                blockCount.setLocationId(blockCount1.getLocationId());
                blockCount.setLocationType(blockCount1.getLocationType());
                blockCount.setCalledInbox(blockCount1.getCalledInbox());
                blockCount.setCalls_1_25(blockCount1.getCalls_1_25());
                blockCount.setCalls_25_50(blockCount1.getCalls_25_50());
                blockCount.setCalls_50_75(blockCount1.getCalls_50_75());
                blockCount.setCalls_75_100(blockCount1.getCalls_75_100());
                blockCount.setBeneficiariesCalled(blockCount2.getTotalBeneficiariesCalled());
                blockCount.setAnsweredCall(blockCount2.getAnsweredAtleastOneCall());
                KilkariUsageDto.add(blockCount);

                beneficiariesCalled+=blockCount.getBeneficiariesCalled();
                calls_75_100+=blockCount.getCalls_75_100();
                calls_50_75+=blockCount.getCalls_50_75();
                calls_25_50+=blockCount.getCalls_25_50();
                calls_1_25+=blockCount.getCalls_1_25();
                calledInbox+=blockCount.getCalledInbox();
                atLeastOneCall+=blockCount.getAnsweredCall();


            }
            UsageDto noBlockCount = new UsageDto();
            noBlockCount.setBeneficiariesCalled(districtCount2.getTotalBeneficiariesCalled()-beneficiariesCalled);
            noBlockCount.setCalls_75_100(districtCount1.getCalls_75_100()-calls_75_100);
            noBlockCount.setCalls_50_75(districtCount1.getCalls_50_75()-calls_50_75);
            noBlockCount.setCalls_25_50(districtCount1.getCalls_25_50()-calls_25_50);
            noBlockCount.setCalls_1_25(districtCount1.getCalls_1_25()-calls_1_25);
            noBlockCount.setCalledInbox(districtCount1.getCalledInbox()-calledInbox);
            noBlockCount.setAnsweredCall(districtCount2.getAnsweredAtleastOneCall()-atLeastOneCall);
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setLocationId((long)(-1));
            KilkariUsageDto.add(noBlockCount);
        } else {
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            KilkariUsage blockCount1 = kilkariUsageDao.getUsage(locationId,"block",fromDate,periodType);
            KilkariMessageListenership blockCount2 = kilkariMessageListenershipReportDao.getListenerData(locationId,"block",fromDate,periodType);

            Long beneficiariesCalled = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Long calledInbox = (long)0;
            Long atLeastOneCall = (long)0;

            for(HealthSubFacility s: subcenters){
                KilkariUsage SubcenterCount1 = kilkariUsageDao.getUsage(s.getHealthSubFacilityId(),locationType,fromDate,periodType);
                KilkariMessageListenership SubcenterCount2 = kilkariMessageListenershipReportDao.getListenerData(s.getHealthSubFacilityId(),locationType,fromDate,periodType);
                UsageDto SubcenterCounts = new UsageDto();
                SubcenterCounts.setLocationId(SubcenterCount1.getLocationId());
                SubcenterCounts.setLocationType(SubcenterCount1.getLocationType());
                SubcenterCounts.setCalledInbox(SubcenterCount1.getCalledInbox());
                SubcenterCounts.setCalls_1_25(SubcenterCount1.getCalls_1_25());
                SubcenterCounts.setCalls_25_50(SubcenterCount1.getCalls_25_50());
                SubcenterCounts.setCalls_50_75(SubcenterCount1.getCalls_50_75());
                SubcenterCounts.setCalls_75_100(SubcenterCount1.getCalls_75_100());
                SubcenterCounts.setBeneficiariesCalled(SubcenterCount2.getTotalBeneficiariesCalled());
                SubcenterCounts.setAnsweredCall(SubcenterCount2.getAnsweredAtleastOneCall());
                KilkariUsageDto.add(SubcenterCounts);

                beneficiariesCalled+=SubcenterCounts.getBeneficiariesCalled();
                calls_75_100+=SubcenterCounts.getCalls_75_100();
                calls_50_75+=SubcenterCounts.getCalls_50_75();
                calls_25_50+=SubcenterCounts.getCalls_25_50();
                calls_1_25+=SubcenterCounts.getCalls_1_25();
                calledInbox+=SubcenterCounts.getCalledInbox();
                atLeastOneCall+=SubcenterCounts.getAnsweredCall();

            }
            UsageDto noSubcenterCount = new UsageDto();
            noSubcenterCount.setBeneficiariesCalled(blockCount2.getTotalBeneficiariesCalled()-beneficiariesCalled);
            noSubcenterCount.setCalls_75_100(blockCount1.getCalls_75_100()-calls_75_100);
            noSubcenterCount.setCalls_50_75(blockCount1.getCalls_50_75()-calls_50_75);
            noSubcenterCount.setCalls_25_50(blockCount1.getCalls_25_50()-calls_25_50);
            noSubcenterCount.setCalls_1_25(blockCount1.getCalls_1_25()-calls_1_25);
            noSubcenterCount.setCalledInbox(blockCount1.getCalledInbox()-calledInbox);
            noSubcenterCount.setAnsweredCall(blockCount2.getAnsweredAtleastOneCall()-atLeastOneCall);
            noSubcenterCount.setLocationType("DifferenceBlock");
            noSubcenterCount.setLocationId((long)(-1));
            KilkariUsageDto.add(noSubcenterCount);
        }
        return KilkariUsageDto;
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


        aCalendar.add(Calendar.DATE, 0);
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
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(0,"State",fromDate,toDate,reportRequest.getPeriodType()));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getStateId(),"District",fromDate,toDate,reportRequest.getPeriodType()));
        } else if(reportRequest.getBlockId() == 0){
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getDistrictId(),"Block",fromDate,toDate,reportRequest.getPeriodType()));
        } else {
            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getBlockId(),"Subcentre",fromDate,toDate,reportRequest.getPeriodType()));
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
                if(locationType.equalsIgnoreCase("Subcentre")){
                    kilkariMessageListenershipReportDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                    kilkariMessageListenershipReportDto.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    kilkariMessageListenershipReportDto.setLocationName("No District");
                    kilkariMessageListenershipReportDto.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    kilkariMessageListenershipReportDto.setLocationName("No Block");
                    kilkariMessageListenershipReportDto.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    kilkariMessageListenershipReportDto.setLocationName("No Subcentre");
                    kilkariMessageListenershipReportDto.setLink(true);
                }
                if(a.getId()!=0 && !locationType.equalsIgnoreCase("DifferenceState")){
                    kilkariMessageListenershipReportDtoList.add(kilkariMessageListenershipReportDto);
                }
            }
        }
        aggregateKilkariReportsDto.setTableData(kilkariMessageListenershipReportDtoList);
        return aggregateKilkariReportsDto;
    }


    private List<KilkariMessageListenership> getKilkariMessageListenershipData(Integer locationId, String locationType, Date date,Date toDate,String periodType){
        List<KilkariMessageListenership> kilkariMessageListenershipList = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    kilkariMessageListenershipList.add(kilkariMessageListenershipReportDao.getListenerData(s.getStateId(),locationType,date,periodType));
                }}
        }
        else if(locationType.equalsIgnoreCase("District")){
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            KilkariMessageListenership stateCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,"State",date,periodType);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;
            for(District d:districts){
                KilkariMessageListenership districtCount = kilkariMessageListenershipReportDao.getListenerData(d.getDistrictId(),locationType,date,periodType);
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
            KilkariMessageListenership districtCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,"district",date,periodType);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;

            for (Block d : blocks) {
                KilkariMessageListenership blockCount = kilkariMessageListenershipReportDao.getListenerData(d.getBlockId(),locationType,date,periodType);
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
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setId((int)(noBlockCount.getAnswered1To25Per()+noBlockCount.getAnswered25To50Per()+noBlockCount.getAnswered50To75Per()+noBlockCount.getAnsweredAtleastOneCall()+noBlockCount.getAnsweredMoreThan75Per()+noBlockCount.getAnsweredNoCalls()+noBlockCount.getTotalBeneficiariesCalled()));
            noBlockCount.setLocationId((long)(-1));
            kilkariMessageListenershipList.add(noBlockCount);
        }
        else {
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            KilkariMessageListenership blockCounts = kilkariMessageListenershipReportDao.getListenerData(locationId,"block",date,periodType);
            Long answeredAtleastOneCall = (long)0;
            Long answeredMoreThan75Per = (long)0;
            Long answered50To75Per = (long)0;
            Long answered25To50Per = (long)0;
            Long answered1To25Per = (long)0;
            Long answeredNoCalls = (long)0;
            Long totalBeneficiariesCalled = (long)0;

            for(HealthSubFacility s: subcenters){
                KilkariMessageListenership subcenterCount = kilkariMessageListenershipReportDao.getListenerData(s.getHealthSubFacilityId(),locationType,date,periodType);
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
            noSubcenterCount.setLocationType("DifferenceBlock");
            noSubcenterCount.setId((int)(noSubcenterCount.getAnswered1To25Per()+noSubcenterCount.getAnswered25To50Per()+noSubcenterCount.getAnswered50To75Per()+noSubcenterCount.getAnsweredAtleastOneCall()+noSubcenterCount.getAnsweredMoreThan75Per()+noSubcenterCount.getAnsweredNoCalls()+noSubcenterCount.getTotalBeneficiariesCalled()));
            noSubcenterCount.setLocationId((long)(-1));
            kilkariMessageListenershipList.add(noSubcenterCount);
        }
        return kilkariMessageListenershipList;
    }



//    @Override
//    public List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletionReport(ReportRequest reportRequest){
//
////
////        List<KilkariMessageListenershipReportDto> kilkariMessageListenershipReportDtoList = new ArrayList<>();
////        List<KilkariMessageListenership> kilkariMessageListenershipList = new ArrayList<>();
////
////        if (reportRequest.getStateId() == 0) {
////            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(0,"State",fromDate,toDate,reportRequest.getPeriodType()));
////        } else if (reportRequest.getDistrictId() == 0) {
////            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getStateId(),"District",fromDate,toDate,reportRequest.getPeriodType()));
////        } else if(reportRequest.getBlockId() == 0){
////            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getDistrictId(),"Block",fromDate,toDate,reportRequest.getPeriodType()));
////        } else {
////            kilkariMessageListenershipList.addAll(getKilkariMessageListenershipData(reportRequest.getBlockId(),"Subcentre",fromDate,toDate,reportRequest.getPeriodType()));
////        }
////
////        if(!(kilkariMessageListenershipList.isEmpty())){
////            for(KilkariMessageListenership a : kilkariMessageListenershipList){
////                KilkariMessageListenershipReportDto kilkariMessageListenershipReportDto = new KilkariMessageListenershipReportDto();
////                kilkariMessageListenershipReportDto.setLocationId(a.getLocationId());
////                kilkariMessageListenershipReportDto.setTotalBeneficiariesCalled(a.getTotalBeneficiariesCalled());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredMoreThan75(a.getAnsweredMoreThan75Per());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnswered50To75(a.getAnswered50To75Per());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnswered25To50(a.getAnswered25To50Per());
////                kilkariMessageListenershipReportDto.setLocationType(a.getLocationType());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnswered1To25(a.getAnswered1To25Per());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredAtleastOnce(a.getAnsweredAtleastOneCall());
////                kilkariMessageListenershipReportDto.setBeneficiariesAnsweredNoCalls(a.getAnsweredNoCalls());
////                String locationType = a.getLocationType();
////                if(locationType.equalsIgnoreCase("State")){
////                    kilkariMessageListenershipReportDto.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
////                }
////                if(locationType.equalsIgnoreCase("District")){
////                    kilkariMessageListenershipReportDto.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
////                }
////                if(locationType.equalsIgnoreCase("Block")){
////                    kilkariMessageListenershipReportDto.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
////                }
////                if(locationType.equalsIgnoreCase("Subcentre")){
////                    kilkariMessageListenershipReportDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
////                    kilkariMessageListenershipReportDto.setLink(true);
////                }
////                if (locationType.equalsIgnoreCase("DifferenceState")) {
////                    kilkariMessageListenershipReportDto.setLocationName("No District");
////                    kilkariMessageListenershipReportDto.setLink(true);
////                }
////                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
////                    kilkariMessageListenershipReportDto.setLocationName("No Block");
////                    kilkariMessageListenershipReportDto.setLink(true);
////                }
////                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
////                    kilkariMessageListenershipReportDto.setLocationName("No Subcentre");
////                    kilkariMessageListenershipReportDto.setLink(true);
////                }
////                if(a.getId()!=0 && !locationType.equalsIgnoreCase("DifferenceState")){
////                    kilkariMessageListenershipReportDtoList.add(kilkariMessageListenershipReportDto);
////                }
////            }
////        }
////        aggregateKilkariReportsDto.setTableData(kilkariMessageListenershipReportDtoList);
////        return aggregateKilkariReportsDto;
////
////
////
////
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        AggregateKilkariReportsDto aggregateKilkariReportsDto = new AggregateKilkariReportsDto();
//
//
//        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Calendar calendar = Calendar.getInstance();
//        Date toDate = new Date();
//        Date startDate=new Date(0);
//        Calendar aCalendar = Calendar.getInstance();
//        aCalendar.setTime(reportRequest.getFromDate());
//        aCalendar.set(Calendar.MILLISECOND, 0);
//        aCalendar.set(Calendar.SECOND, 0);
//        aCalendar.set(Calendar.MINUTE, 0);
//        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
//
//
//
//        Date fromDate = aCalendar.getTime();
//        aCalendar.setTime(reportRequest.getToDate());
//        aCalendar.set(Calendar.MILLISECOND, 0);
//        aCalendar.set(Calendar.SECOND, 0);
//        aCalendar.set(Calendar.MINUTE, 0);
//        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        aCalendar.add(Calendar.DATE, 1);
//        toDate = aCalendar.getTime();
//
//
//        List<AggCumulativeBeneficiaryComplDto> aggBeneficiaryCompletionDtoList = new ArrayList<>();
//        List<AggregateCumulativeBeneficiaryCompletion> aggBeneficiaryCompletionList = new ArrayList<>();
//
//
//        if (reportRequest.getStateId() == 0) {
//            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(0, "State", fromDate, reportRequest.getPeriodType()));
//        } else if (reportRequest.getDistrictId() == 0) {
//            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getStateId(), "District", fromDate, reportRequest.getPeriodType()));
//        } else if (reportRequest.getBlockId() == 0) {
//            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getDistrictId(), "Block", fromDate, reportRequest.getPeriodType()));
//        } else {
//            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getBlockId(), "Subcentre", fromDate, reportRequest.getPeriodType()));
//        }
//
//        if (!(aggBeneficiaryCompletionList.isEmpty())) {
//            for (AggregateCumulativeBeneficiaryCompletion a : aggBeneficiaryCompletionList) {
//
//                        AggCumulativeBeneficiaryComplDto aggCumulativeBeneficiaryComplDto = new AggCumulativeBeneficiaryComplDto();
//                        aggCumulativeBeneficiaryComplDto.setLocationId(a.getLocationId());
//                        aggCumulativeBeneficiaryComplDto.setCalls_1_25(a.getCalls_1_25());
//                        aggCumulativeBeneficiaryComplDto.setCalls_25_50(a.getCalls_25_50());
//                        aggCumulativeBeneficiaryComplDto.setCalls_50_75(a.getCalls_50_75());
//                        aggCumulativeBeneficiaryComplDto.setCalls_75_100(a.getCalls_75_100());
//                        aggCumulativeBeneficiaryComplDto.setCompletedBeneficiaries(a.getCompletedBeneficiaries());
//                        aggCumulativeBeneficiaryComplDto.setAvgWeeks((float)Math.round((float)(a.getTotalAge())/(float)aggCumulativeBeneficiaryComplDto.getCompletedBeneficiaries()* 100)/100);
//                        aggCumulativeBeneficiaryComplDto.setLocationType(a.getLocationType());
//                        String locationType = a.getLocationType();
//                        if (locationType.equalsIgnoreCase("State")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
//                        }
//                        if (locationType.equalsIgnoreCase("District")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
//                        }
//                        if (locationType.equalsIgnoreCase("Block")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
//                        }
//                        if (locationType.equalsIgnoreCase("Subcentre")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceState")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No District Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No Block Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceBlock")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No Subcentre Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//
//                        }
//
//                        if ((aggCumulativeBeneficiaryComplDto.getCompletedBeneficiaries() + aggCumulativeBeneficiaryComplDto.getCalls_1_25() + aggCumulativeBeneficiaryComplDto.getCalls_25_50()
//                                + aggCumulativeBeneficiaryComplDto.getCalls_50_75() + aggCumulativeBeneficiaryComplDto.getCalls_75_100()
//                                + aggCumulativeBeneficiaryComplDto.getAvgWeeks()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
//                            aggBeneficiaryCompletionDtoList.add(aggCumulativeBeneficiaryComplDto);
//                        }
//                    }
//            }
//        return aggBeneficiaryCompletionDtoList;
//        }
//
//
//
















    /*----------5.3.6. Kilkari Beneficiary Completion Report -------*/

    @Override
    public List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletion(ReportRequest reportRequest, User currentUser){


//        AggregateKilkariReportsDto aggregateKilkariReportsDto = new AggregateKilkariReportsDto();


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



        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 1);
        toDate = aCalendar.getTime();


        List<AggCumulativeBeneficiaryComplDto> aggBeneficiaryCompletionDtoList = new ArrayList<>();
        List<AggregateCumulativeBeneficiaryCompletion> aggBeneficiaryCompletionList = new ArrayList<>();


        if (reportRequest.getStateId() == 0) {
            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(0, "State", fromDate, toDate,  reportRequest.getPeriodType()));
        } else if (reportRequest.getDistrictId() == 0) {
            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getStateId(), "District",  fromDate, toDate, reportRequest.getPeriodType()));
        } else if (reportRequest.getBlockId() == 0) {
            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getDistrictId(), "Block",  fromDate, toDate, reportRequest.getPeriodType()));
        } else {
            aggBeneficiaryCompletionList.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getBlockId(), "Subcentre", fromDate, toDate , reportRequest.getPeriodType()));
        }

        if (!(aggBeneficiaryCompletionList.isEmpty())) {
            for (AggregateCumulativeBeneficiaryCompletion a : aggBeneficiaryCompletionList) {

                AggCumulativeBeneficiaryComplDto aggCumulativeBeneficiaryComplDto = new AggCumulativeBeneficiaryComplDto();
                aggCumulativeBeneficiaryComplDto.setLocationId(a.getLocationId());
                aggCumulativeBeneficiaryComplDto.setCalls_1_25(a.getCalls_1_25());
                aggCumulativeBeneficiaryComplDto.setCalls_25_50(a.getCalls_25_50());
                aggCumulativeBeneficiaryComplDto.setCalls_50_75(a.getCalls_50_75());
                aggCumulativeBeneficiaryComplDto.setCalls_75_100(a.getCalls_75_100());
                aggCumulativeBeneficiaryComplDto.setCompletedBeneficiaries(a.getCompletedBeneficiaries());
                aggCumulativeBeneficiaryComplDto.setAvgWeeks(a.getTotalAge());
                aggCumulativeBeneficiaryComplDto.setLocationType(a.getLocationType());
                String locationType = a.getLocationType();
                if (locationType.equalsIgnoreCase("State")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if (locationType.equalsIgnoreCase("District")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if (locationType.equalsIgnoreCase("Block")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if (locationType.equalsIgnoreCase("Subcentre")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                    aggCumulativeBeneficiaryComplDto.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName("No District Count");
                    aggCumulativeBeneficiaryComplDto.setLink(true);
                    aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName("No Block Count");
                    aggCumulativeBeneficiaryComplDto.setLink(true);
                    aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);

                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    aggCumulativeBeneficiaryComplDto.setLocationName("No Subcentre Count");
                    aggCumulativeBeneficiaryComplDto.setLink(true);
                    aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);

                }

                if ((aggCumulativeBeneficiaryComplDto.getCompletedBeneficiaries() + aggCumulativeBeneficiaryComplDto.getCalls_1_25() + aggCumulativeBeneficiaryComplDto.getCalls_25_50()
                        + aggCumulativeBeneficiaryComplDto.getCalls_50_75() + aggCumulativeBeneficiaryComplDto.getCalls_75_100()
                        + aggCumulativeBeneficiaryComplDto.getAvgWeeks()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
                    aggBeneficiaryCompletionDtoList.add(aggCumulativeBeneficiaryComplDto);
                }
            }
        }
        return aggBeneficiaryCompletionDtoList;


















//        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Calendar calendar = Calendar.getInstance();
//        Date toDate = new Date();
//        Date startDate=new Date(0);
//        Calendar aCalendar = Calendar.getInstance();
//        aCalendar.setTime(reportRequest.getFromDate());
//        aCalendar.set(Calendar.MILLISECOND, 0);
//        aCalendar.set(Calendar.SECOND, 0);
//        aCalendar.set(Calendar.MINUTE, 0);
//        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
//
//
//
//        Date fromDate = aCalendar.getTime();
//        aCalendar.setTime(reportRequest.getToDate());
//        aCalendar.set(Calendar.MILLISECOND, 0);
//        aCalendar.set(Calendar.SECOND, 0);
//        aCalendar.set(Calendar.MINUTE, 0);
//        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        aCalendar.add(Calendar.DATE, 1);
//        toDate = aCalendar.getTime();
//
//
//        List<AggCumulativeBeneficiaryComplDto> summaryDto = new ArrayList<>();
//        List<AggregateCumulativeBeneficiaryCompletion> aggBeneficiaryCompletionStart = new ArrayList<>();
//        List<AggregateCumulativeBeneficiaryCompletion> aggBeneficiaryCompletionEnd = new ArrayList<>();
//
//        if (reportRequest.getStateId() == 0) {
//            aggBeneficiaryCompletionStart.addAll(this.getCumulativeBeneficiaryCompletionData(0, "State", fromDate));
//            aggBeneficiaryCompletionEnd.addAll(this.getCumulativeBeneficiaryCompletionData(0, "State", toDate));
//        } else if (reportRequest.getDistrictId() == 0) {
//            aggBeneficiaryCompletionStart.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getStateId(), "District", fromDate));
//            aggBeneficiaryCompletionEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getStateId(), "District", toDate));
//        } else if (reportRequest.getBlockId() == 0) {
//            aggBeneficiaryCompletionStart.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getDistrictId(), "Block", fromDate));
//            aggBeneficiaryCompletionEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getDistrictId(), "Block", toDate));
//        } else {
//            aggBeneficiaryCompletionStart.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getBlockId(), "Subcentre", fromDate));
//            aggBeneficiaryCompletionEnd.addAll(this.getCumulativeBeneficiaryCompletionData(reportRequest.getBlockId(), "Subcentre", toDate));
//        }
//
//        if (!(aggBeneficiaryCompletionEnd.isEmpty()) && !(aggBeneficiaryCompletionStart.isEmpty())) {
//            for (int i = 0; i < aggBeneficiaryCompletionEnd.size(); i++) {
//                for (int j = 0; j < aggBeneficiaryCompletionStart.size(); j++) {
//                    if (aggBeneficiaryCompletionEnd.get(i).getLocationId().equals(aggBeneficiaryCompletionStart.get(j).getLocationId())) {
//                        AggregateCumulativeBeneficiaryCompletion end = aggBeneficiaryCompletionEnd.get(i);
//                        AggregateCumulativeBeneficiaryCompletion start = aggBeneficiaryCompletionStart.get(j);
//                        AggCumulativeBeneficiaryComplDto aggCumulativeBeneficiaryComplDto = new AggCumulativeBeneficiaryComplDto();
//                        aggCumulativeBeneficiaryComplDto.setLocationId(end.getLocationId());
//                        aggCumulativeBeneficiaryComplDto.setCalls_1_25(end.getCalls_1_25() - start.getCalls_1_25());
//                        aggCumulativeBeneficiaryComplDto.setCalls_25_50(end.getCalls_25_50() - start.getCalls_25_50());
//                        aggCumulativeBeneficiaryComplDto.setCalls_50_75(end.getCalls_50_75() - start.getCalls_50_75());
//                        aggCumulativeBeneficiaryComplDto.setCalls_75_100(end.getCalls_75_100() - start.getCalls_75_100());
//                        aggCumulativeBeneficiaryComplDto.setCompletedBeneficiaries(end.getCompletedBeneficiaries() - start.getCompletedBeneficiaries());
//                        aggCumulativeBeneficiaryComplDto.setAvgWeeks((float)Math.round((float)(end.getTotalAge() - start.getTotalAge())/(float)aggCumulativeBeneficiaryComplDto.getCompletedBeneficiaries()* 100)/100);
//                        aggCumulativeBeneficiaryComplDto.setLocationType(end.getLocationType());
//                        String locationType = end.getLocationType();
//                        if (locationType.equalsIgnoreCase("State")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(stateDao.findByStateId(end.getLocationId().intValue()).getStateName());
//                        }
//                        if (locationType.equalsIgnoreCase("District")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(districtDao.findByDistrictId(end.getLocationId().intValue()).getDistrictName());
//                        }
//                        if (locationType.equalsIgnoreCase("Block")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(blockDao.findByblockId(end.getLocationId().intValue()).getBlockName());
//                        }
//                        if (locationType.equalsIgnoreCase("Subcentre")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(end.getLocationId().intValue()).getHealthSubFacilityName());
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceState")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No District Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No Block Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//
//                        }
//                        if (locationType.equalsIgnoreCase("DifferenceBlock")) {
//                            aggCumulativeBeneficiaryComplDto.setLocationName("No Subcentre Count");
//                            aggCumulativeBeneficiaryComplDto.setLink(true);
//                            aggCumulativeBeneficiaryComplDto.setLocationId((long) -1);
//
//                        }
//
//                        if ((aggCumulativeBeneficiaryComplDto.getCompletedBeneficiaries() + aggCumulativeBeneficiaryComplDto.getCalls_1_25() + aggCumulativeBeneficiaryComplDto.getCalls_25_50()
//                                + aggCumulativeBeneficiaryComplDto.getCalls_50_75() + aggCumulativeBeneficiaryComplDto.getCalls_75_100()
//                                + aggCumulativeBeneficiaryComplDto.getAvgWeeks()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
//                            summaryDto.add(aggCumulativeBeneficiaryComplDto);
//                        }
//                    }
//                }
//            }
//        }
//        return summaryDto;
    }

    private List<AggregateCumulativeBeneficiaryCompletion> getCumulativeBeneficiaryCompletionData(Integer locationId,String locationType,Date fromDate, Date toDate, String periodtype){
        List<AggregateCumulativeBeneficiaryCompletion> cumulativeCompletion = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    cumulativeCompletion.add(aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getStateId(),locationType, fromDate, periodtype));
                }

            }
        }
        else if(locationType.equalsIgnoreCase("District")){

            List<District> districts = districtDao.getDistrictsOfState(locationId);
            AggregateCumulativeBeneficiaryCompletion stateCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"State",fromDate, periodtype);
            Long completedBeneficiaries = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Double totalAge = 0.00;
            for(District d:districts){
                AggregateCumulativeBeneficiaryCompletion districtCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getDistrictId(),locationType,fromDate, periodtype);
                cumulativeCompletion.add(districtCount);
                completedBeneficiaries+=districtCount.getCompletedBeneficiaries();
                calls_75_100 += districtCount.getCalls_75_100();
                calls_50_75 += districtCount.getCalls_50_75();
                calls_25_50 += districtCount.getCalls_25_50();
                calls_1_25 += districtCount.getCalls_1_25();
                totalAge += districtCount.getTotalAge()*districtCount.getCompletedBeneficiaries();
            }
            AggregateCumulativeBeneficiaryCompletion noDistrictCount = new AggregateCumulativeBeneficiaryCompletion();
            noDistrictCount.setCompletedBeneficiaries(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries);
            noDistrictCount.setCalls_75_100(stateCounts.getCalls_75_100()-calls_75_100);
            noDistrictCount.setCalls_50_75(stateCounts.getCalls_50_75()-calls_50_75);
            noDistrictCount.setCalls_25_50(stateCounts.getCalls_25_50()-calls_25_50);
            noDistrictCount.setCalls_1_25(stateCounts.getCalls_1_25()-calls_1_25);
            noDistrictCount.setTotalAge(noDistrictCount.getCompletedBeneficiaries() == 0 ? 0.00 : (double) (stateCounts.getTotalAge()*stateCounts.getCompletedBeneficiaries()-totalAge)/noDistrictCount.getCompletedBeneficiaries());
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setId((int)(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries+stateCounts.getCalls_75_100()-calls_75_100+stateCounts.getCalls_50_75()-calls_50_75+stateCounts.getCalls_25_50()-calls_25_50+stateCounts.getCalls_1_25()-calls_1_25+stateCounts.getTotalAge()-totalAge));
            noDistrictCount.setLocationId((long)(-1));
            cumulativeCompletion.add(noDistrictCount);
        } else if(locationType.equalsIgnoreCase("Block")) {

            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            AggregateCumulativeBeneficiaryCompletion districtCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"District",fromDate, periodtype);
            Long completedBeneficiaries = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Double totalAge =  0.00;
            for (Block d : blocks) {
                AggregateCumulativeBeneficiaryCompletion blockCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getBlockId(),locationType,fromDate, periodtype);
                cumulativeCompletion.add(blockCount);
                completedBeneficiaries += blockCount.getCompletedBeneficiaries();
                calls_75_100 += blockCount.getCalls_75_100();
                calls_50_75 += blockCount.getCalls_50_75();
                calls_25_50 += blockCount.getCalls_25_50();
                calls_1_25 += blockCount.getCalls_1_25();
                totalAge += blockCount.getTotalAge()*blockCount.getCompletedBeneficiaries();
            }
            AggregateCumulativeBeneficiaryCompletion noBlockCount = new AggregateCumulativeBeneficiaryCompletion();
            noBlockCount.setCompletedBeneficiaries(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries);
            noBlockCount.setCalls_75_100(districtCounts.getCalls_75_100()-calls_75_100);
            noBlockCount.setCalls_50_75(districtCounts.getCalls_50_75()-calls_50_75);
            noBlockCount.setCalls_25_50(districtCounts.getCalls_25_50()-calls_25_50);
            noBlockCount.setCalls_1_25(districtCounts.getCalls_1_25()-calls_1_25);
            noBlockCount.setTotalAge(noBlockCount.getCompletedBeneficiaries() == 0 ? 0.00 : (districtCounts.getTotalAge()*districtCounts.getCompletedBeneficiaries()-totalAge)/noBlockCount.getCompletedBeneficiaries());
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setId((int)(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries+districtCounts.getCalls_75_100()-calls_75_100+districtCounts.getCalls_50_75()-calls_50_75+districtCounts.getCalls_25_50()-calls_25_50+districtCounts.getCalls_1_25()-calls_1_25+districtCounts.getTotalAge()-totalAge));
            noBlockCount.setLocationId((long)(-1));
            cumulativeCompletion.add(noBlockCount);
        } else {
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            AggregateCumulativeBeneficiaryCompletion blockCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"block",fromDate, periodtype);
            Long completedBeneficiaries = (long)0;
            Long calls_75_100 = (long)0;
            Long calls_50_75 = (long)0;
            Long calls_25_50 = (long)0;
            Long calls_1_25 = (long)0;
            Double totalAge = 0.00;
            for(HealthSubFacility s: subcenters){
                AggregateCumulativeBeneficiaryCompletion SubcenterCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getHealthSubFacilityId(),locationType,fromDate, periodtype);
                cumulativeCompletion.add(SubcenterCount);
                completedBeneficiaries+=SubcenterCount.getCompletedBeneficiaries();
                calls_75_100 += SubcenterCount.getCalls_75_100();
                calls_50_75 += SubcenterCount.getCalls_50_75();
                calls_25_50 += SubcenterCount.getCalls_25_50();
                calls_1_25 += SubcenterCount.getCalls_1_25();
                totalAge += SubcenterCount.getTotalAge()*SubcenterCount.getCompletedBeneficiaries();
            }
            AggregateCumulativeBeneficiaryCompletion noSubcenterCount = new AggregateCumulativeBeneficiaryCompletion();
            noSubcenterCount.setCompletedBeneficiaries(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries);
            noSubcenterCount.setCalls_75_100(blockCounts.getCalls_75_100()-calls_75_100);
            noSubcenterCount.setCalls_50_75(blockCounts.getCalls_50_75()-calls_50_75);
            noSubcenterCount.setCalls_25_50(blockCounts.getCalls_25_50()-calls_25_50);
            noSubcenterCount.setCalls_1_25(blockCounts.getCalls_1_25()-calls_1_25);
            noSubcenterCount.setTotalAge(noSubcenterCount.getCompletedBeneficiaries() == 0 ? 0.00 : (blockCounts.getTotalAge()*blockCounts.getCompletedBeneficiaries()-totalAge)/noSubcenterCount.getCompletedBeneficiaries());
            noSubcenterCount.setLocationType("DifferenceBlock");
            noSubcenterCount.setId((int)(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries+blockCounts.getCalls_75_100()-calls_75_100+blockCounts.getCalls_50_75()-calls_50_75+blockCounts.getCalls_25_50()-calls_25_50+blockCounts.getCalls_1_25()-calls_1_25+blockCounts.getTotalAge()-totalAge));
            noSubcenterCount.setLocationId((long)(-1));
            cumulativeCompletion.add(noSubcenterCount);
        }
        return cumulativeCompletion;
    }

    /*----------5.3.7. Kilkari Listening Matrix Report -------*/

    @Override
    public List<ListeningMatrixDto> getListeningMatrixReport(ReportRequest reportRequest, User currentUser){

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date toDate = new Date();
        Date startDate=new Date(0);
        Integer locationId;
        String locationType;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


//        aCalendar.add(Calendar.DATE, -1);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        if (reportRequest.getStateId() == 0) {
            locationId = 0;
            locationType = "National";
        } else if (reportRequest.getDistrictId() == 0) {
            locationId = reportRequest.getStateId();
            locationType = "State";
        } else if(reportRequest.getBlockId() == 0){
            locationId = reportRequest.getDistrictId();
            locationType = "District";
        } else {
            locationId = reportRequest.getBlockId();
            locationType = "Block";
        }


        List<ListeningMatrixDto> matrixDto = new ArrayList<>();
        HashMap<String,ListeningMatrix> listeningMatrix = listeningMatrixReportDao.getListeningMatrix(locationId,locationType,fromDate,reportRequest.getPeriodType());

        if(listeningMatrix==null){
            return matrixDto;
        }
        ListeningMatrixDto matrixDto1 = new ListeningMatrixDto();
        matrixDto1.setPercentageCalls("Beneficiaries Answered >= 75% calls");
        matrixDto1.setContent_1_25(listeningMatrix.get("callsListened_morethan75").getContentListened_lessthan25());
        matrixDto1.setContent_25_50(listeningMatrix.get("callsListened_morethan75").getContentListened_25_50());
        matrixDto1.setContent_50_75(listeningMatrix.get("callsListened_morethan75").getContentListened_50_75());
        matrixDto1.setContent_75_100(listeningMatrix.get("callsListened_morethan75").getContentListened_morethan75());
        matrixDto1.setTotal(listeningMatrix.get("callsListened_morethan75").getContentListened_lessthan25()+listeningMatrix.get("callsListened_morethan75").getContentListened_25_50()+listeningMatrix.get("callsListened_morethan75").getContentListened_50_75()+listeningMatrix.get("callsListened_morethan75").getContentListened_morethan75());

        ListeningMatrixDto matrixDto2 = new ListeningMatrixDto();
        matrixDto2.setPercentageCalls("Beneficiaries Answered >= 50 and < 75% calls");
        matrixDto2.setContent_1_25(listeningMatrix.get("callsListened_50_75").getContentListened_lessthan25());
        matrixDto2.setContent_25_50(listeningMatrix.get("callsListened_50_75").getContentListened_25_50());
        matrixDto2.setContent_50_75(listeningMatrix.get("callsListened_50_75").getContentListened_50_75());
        matrixDto2.setContent_75_100(listeningMatrix.get("callsListened_50_75").getContentListened_morethan75());
        matrixDto2.setTotal(listeningMatrix.get("callsListened_50_75").getContentListened_lessthan25()+listeningMatrix.get("callsListened_50_75").getContentListened_25_50()+listeningMatrix.get("callsListened_50_75").getContentListened_50_75()+listeningMatrix.get("callsListened_50_75").getContentListened_morethan75());

        ListeningMatrixDto matrixDto3 = new ListeningMatrixDto();
        matrixDto3.setPercentageCalls("Beneficiaries Answered >= 25% and < 50% calls");
        matrixDto3.setContent_1_25(listeningMatrix.get("callsListened_25_50").getContentListened_lessthan25());
        matrixDto3.setContent_25_50(listeningMatrix.get("callsListened_25_50").getContentListened_25_50());
        matrixDto3.setContent_50_75(listeningMatrix.get("callsListened_25_50").getContentListened_50_75());
        matrixDto3.setContent_75_100(listeningMatrix.get("callsListened_25_50").getContentListened_morethan75());
        matrixDto3.setTotal(listeningMatrix.get("callsListened_25_50").getContentListened_lessthan25()+listeningMatrix.get("callsListened_25_50").getContentListened_25_50()+listeningMatrix.get("callsListened_25_50").getContentListened_50_75()+listeningMatrix.get("callsListened_25_50").getContentListened_morethan75());

        ListeningMatrixDto matrixDto4 = new ListeningMatrixDto();
        matrixDto4.setPercentageCalls("Beneficiaries Answered < 25% calls");
        matrixDto4.setContent_1_25(listeningMatrix.get("callsListened_lessthan25").getContentListened_lessthan25());
        matrixDto4.setContent_25_50(listeningMatrix.get("callsListened_lessthan25").getContentListened_25_50());
        matrixDto4.setContent_50_75(listeningMatrix.get("callsListened_lessthan25").getContentListened_50_75());
        matrixDto4.setContent_75_100(listeningMatrix.get("callsListened_lessthan25").getContentListened_morethan75());
        matrixDto4.setTotal(listeningMatrix.get("callsListened_lessthan25").getContentListened_lessthan25()+listeningMatrix.get("callsListened_lessthan25").getContentListened_25_50()+listeningMatrix.get("callsListened_lessthan25").getContentListened_50_75()+listeningMatrix.get("callsListened_lessthan25").getContentListened_morethan75());

        ListeningMatrixDto matrixDto5 = new ListeningMatrixDto();
        matrixDto5.setPercentageCalls("Total");
        matrixDto5.setContent_1_25(listeningMatrix.get("callsListened_lessthan25").getContentListened_lessthan25()+
                listeningMatrix.get("callsListened_25_50").getContentListened_lessthan25()+
                listeningMatrix.get("callsListened_50_75").getContentListened_lessthan25()+
                listeningMatrix.get("callsListened_morethan75").getContentListened_lessthan25());

        matrixDto5.setContent_25_50(listeningMatrix.get("callsListened_lessthan25").getContentListened_25_50()+
                listeningMatrix.get("callsListened_25_50").getContentListened_25_50()+
                listeningMatrix.get("callsListened_50_75").getContentListened_25_50()+
                listeningMatrix.get("callsListened_morethan75").getContentListened_25_50());

        matrixDto5.setContent_50_75(listeningMatrix.get("callsListened_lessthan25").getContentListened_50_75()+
                listeningMatrix.get("callsListened_25_50").getContentListened_50_75()+
                listeningMatrix.get("callsListened_50_75").getContentListened_50_75()+
                listeningMatrix.get("callsListened_morethan75").getContentListened_50_75());

        matrixDto5.setContent_75_100(listeningMatrix.get("callsListened_lessthan25").getContentListened_morethan75()+
                listeningMatrix.get("callsListened_25_50").getContentListened_morethan75()+
                listeningMatrix.get("callsListened_50_75").getContentListened_morethan75()+
                listeningMatrix.get("callsListened_morethan75").getContentListened_morethan75());

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
        Integer locationId;
        String locationType;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


        aCalendar.add(Calendar.DATE, 0);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        List<KilkariThematicContentReportDto> kilkariThematicContentReportDtoList = new ArrayList<>();
        KilkariThematicContent kilkariThematicContentData;

        if (reportRequest.getStateId() == 0) {
            locationId = 0;
            locationType = "National";
        } else if (reportRequest.getDistrictId() == 0) {
            locationId = reportRequest.getStateId();
            locationType = "State";
        } else if(reportRequest.getBlockId() == 0){
            locationId = reportRequest.getDistrictId();
            locationType = "District";
        } else {
            locationId = reportRequest.getBlockId();
            locationType = "Block";
        }

        Double minutesConsumed = new Double(0.00);
        Long callsAnswered = (long)0;
        Long totalBenefeciariesCalled = (long)0;

        Map<Integer,Theme> themes = themeDao.getAllThemes();
        Map<String,KilkariThematicContent> kilkariThematicContentData1 = kilkariThematicContentReportDao.getKilkariThematicContentReportData(locationId,locationType,fromDate,reportRequest.getPeriodType());
        for(int i = 1; i <= 72; i++){
            kilkariThematicContentData = kilkariThematicContentData1.get("w"+i);
            Theme theme = themes.get(i);
            KilkariThematicContentReportDto kilkariThematicContentReportDto = new KilkariThematicContentReportDto();
            kilkariThematicContentReportDto.setId(kilkariThematicContentData==null?0:kilkariThematicContentData.getId());
            kilkariThematicContentReportDto.setTheme(theme.getTheme());
            kilkariThematicContentReportDto.setMinutesConsumed(kilkariThematicContentData==null?0:kilkariThematicContentData.getMinutesConsumed());
            minutesConsumed += kilkariThematicContentData==null?0:kilkariThematicContentData.getMinutesConsumed();
            kilkariThematicContentReportDto.setCallsAnswered(kilkariThematicContentData==null?0:kilkariThematicContentData.getCallsAnswered());
            callsAnswered += kilkariThematicContentData==null?0:kilkariThematicContentData.getCallsAnswered();
            kilkariThematicContentReportDto.setUniqueBeneficiariesCalled(kilkariThematicContentData==null?0:kilkariThematicContentData.getUniqueBeneficiariesCalled());
            kilkariThematicContentReportDto.setMessageWeekNumber("Message "+i+" - "+theme.getTitle());
            if(kilkariThematicContentReportDto.getCallsAnswered()+Math.round(kilkariThematicContentReportDto.getMinutesConsumed().floatValue()*100)+kilkariThematicContentReportDto.getUniqueBeneficiariesCalled() > 0){
                kilkariThematicContentReportDtoList.add(kilkariThematicContentReportDto);
            }
        }

        if(locationType.equalsIgnoreCase("National")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s :states){
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))) {
                    KilkariMessageListenership kilkariMessageListenership = kilkariMessageListenershipReportDao.getListenerData(s.getStateId(), "State", fromDate,reportRequest.getPeriodType());
                    totalBenefeciariesCalled += kilkariMessageListenership.getTotalBeneficiariesCalled();
                }
            }

        }else{
            KilkariMessageListenership kilkariMessageListenership = kilkariMessageListenershipReportDao.getListenerData(locationId,locationType,fromDate,reportRequest.getPeriodType());
            totalBenefeciariesCalled = kilkariMessageListenership.getTotalBeneficiariesCalled();
        }

        KilkariThematicContentReportDto kilkariThematicContentReportDto = new KilkariThematicContentReportDto();
        kilkariThematicContentReportDto.setId(0);
        kilkariThematicContentReportDto.setTheme("");
        kilkariThematicContentReportDto.setMinutesConsumed(minutesConsumed);
        kilkariThematicContentReportDto.setCallsAnswered(callsAnswered);
        kilkariThematicContentReportDto.setUniqueBeneficiariesCalled(totalBenefeciariesCalled);
        kilkariThematicContentReportDto.setMessageWeekNumber("Total");
        kilkariThematicContentReportDtoList.add(kilkariThematicContentReportDto);


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
        Integer locationId;
        String locationType;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


//        aCalendar.add(Calendar.DATE, -1);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        if (reportRequest.getStateId() == 0) {
            locationId = 0;
            locationType = "National";
        } else if (reportRequest.getDistrictId() == 0) {
            locationId = reportRequest.getStateId();
            locationType = "State";
        } else if(reportRequest.getBlockId() == 0){
            locationId = reportRequest.getDistrictId();
            locationType = "District";
        } else {
            locationId = reportRequest.getBlockId();
            locationType = "Block";
        }

        MessageMatrixResponseDto messageMatrixResponseDto = new MessageMatrixResponseDto();
        List<MessageMatrixDto> motherMatrixDto = new ArrayList<>();
        List<MessageMatrixDto> childMatrixDto = new ArrayList<>();

        List<MessageMatrix> messagelist= messageMatrixDao.getMessageMatrixData(locationId,locationType,fromDate,reportRequest.getPeriodType());
        Long Mother_1_25=0L,Mother_25_50=0L,Mother_50_75=0L,Mother_75_100=0L,Child_1_25=0L,Child_25_50=0L,Child_50_75=0L,Child_75_100=0L;
        if(messagelist == null){
            messageMatrixResponseDto.setChildData(childMatrixDto);
            messageMatrixResponseDto.setMotherData(motherMatrixDto);
            return  messageMatrixResponseDto;
        }

        while(motherMatrixDto.size() < 4) motherMatrixDto.add(new MessageMatrixDto());
        while(childMatrixDto.size() < 8) childMatrixDto.add(new MessageMatrixDto());
        for(int count =0; count< messagelist.size();count++) {
            MessageMatrixDto matrixDto1 = new MessageMatrixDto();

            matrixDto1.setContent_1_25(messagelist.get(count).getListened_lessthan25());

            matrixDto1.setContent_25_50(messagelist.get(count).getListened_25_50());

            matrixDto1.setContent_50_75(messagelist.get(count).getListened_50_75());

            matrixDto1.setContent_75_100(messagelist.get(count).getListened_morethan75());

            matrixDto1.setTotal(messagelist.get(count).getListened_lessthan25() + messagelist.get(count).getListened_25_50() + messagelist.get(count).getListened_50_75() + messagelist.get(count).getListened_morethan75());

            switch (messagelist.get(count).getMessageWeek()){
                case "mother_week_1_6":
                {
                    matrixDto1.setMessageWeek("Message Week 2-6");
                    Mother_1_25 += messagelist.get(count).getListened_lessthan25();
                    Mother_25_50 += messagelist.get(count).getListened_25_50();
                    Mother_50_75 += messagelist.get(count).getListened_50_75();
                    Mother_75_100 += messagelist.get(count).getListened_morethan75();
                    motherMatrixDto.set(0,matrixDto1);
                    break;
                }
                case "mother_week_7_12":
                {
                    matrixDto1.setMessageWeek("Message Week 7-12");
                    Mother_1_25 += messagelist.get(count).getListened_lessthan25();
                    Mother_25_50 += messagelist.get(count).getListened_25_50();
                    Mother_50_75 += messagelist.get(count).getListened_50_75();
                    Mother_75_100 += messagelist.get(count).getListened_morethan75();
                    motherMatrixDto.set(1,matrixDto1);
                    break;
                }
                case "mother_week_13_18":
                {
                    matrixDto1.setMessageWeek("Message Week 13-18");
                    Mother_1_25 += messagelist.get(count).getListened_lessthan25();
                    Mother_25_50 += messagelist.get(count).getListened_25_50();
                    Mother_50_75 += messagelist.get(count).getListened_50_75();
                    Mother_75_100 += messagelist.get(count).getListened_morethan75();
                    motherMatrixDto.set(2,matrixDto1);
                    break;
                }
                case "mother_week_19_24":
                {
                    matrixDto1.setMessageWeek("Message Week 19-24");
                    Mother_1_25 += messagelist.get(count).getListened_lessthan25();
                    Mother_25_50 += messagelist.get(count).getListened_25_50();
                    Mother_50_75 += messagelist.get(count).getListened_50_75();
                    Mother_75_100 += messagelist.get(count).getListened_morethan75();
                    motherMatrixDto.set(3,matrixDto1);
                    break;
                }




                case "child_week_1_6": {
                    matrixDto1.setMessageWeek("Message Week 2-6");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(0,matrixDto1);
                    break;
                }

                case "child_week_7_12": {
                    matrixDto1.setMessageWeek("Message Week 7-12");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(1,matrixDto1);
                    break;
                }

                case "child_week_13_18": {
                    matrixDto1.setMessageWeek("Message Week 13-18");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(2,matrixDto1);
                    break;
                }

                case "child_week_19_24": {
                    matrixDto1.setMessageWeek("Message Week 19-24");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(3,matrixDto1);
                    break;
                }

                case "child_week_25_30": {
                    matrixDto1.setMessageWeek("Message Week 25-30");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(4,matrixDto1);
                    break;
                }

                case "child_week_31_36": {
                    matrixDto1.setMessageWeek("Message Week 31-36");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(5,matrixDto1);
                    break;
                }

                case "child_week_37_42":{
                    matrixDto1.setMessageWeek("Message Week 37-42");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(6,matrixDto1);
                    break;
                }


                case "child_week_43_48": {
                    matrixDto1.setMessageWeek("Message Week 43-48");
                    Child_1_25 += messagelist.get(count).getListened_lessthan25();
                    Child_25_50 += messagelist.get(count).getListened_25_50();
                    Child_50_75 += messagelist.get(count).getListened_50_75();
                    Child_75_100 += messagelist.get(count).getListened_morethan75();
                    childMatrixDto.set(7,matrixDto1);
                    break;
                }


            }


        }

//        MessageMatrixDto matrixDto5 = new MessageMatrixDto();
//        matrixDto5.setMessageWeek("Total");
//        matrixDto5.setContent_1_25(Mother_1_25);
//        matrixDto5.setContent_25_50(Mother_25_50);
//        matrixDto5.setContent_50_75(Mother_50_75);
//        matrixDto5.setContent_75_100(Mother_75_100);
//        matrixDto5.setTotal(Mother_1_25 + Mother_25_50 + Mother_50_75 + Mother_75_100);
//        motherMatrixDto.set(4,matrixDto5);
//
//        MessageMatrixDto matrixDto14 = new MessageMatrixDto();
//        matrixDto14.setMessageWeek("Total");
//        matrixDto14.setContent_1_25(Child_1_25);
//        matrixDto14.setContent_25_50(Child_25_50);
//        matrixDto14.setContent_50_75(Child_50_75);
//        matrixDto14.setContent_75_100(Child_75_100);
//        matrixDto14.setTotal(Child_1_25 + Child_25_50 +Child_50_75 + Child_75_100);
//
//        childMatrixDto.set(8,matrixDto14);



        messageMatrixResponseDto.setChildData(childMatrixDto);
        messageMatrixResponseDto.setMotherData(motherMatrixDto);
        return messageMatrixResponseDto;
    }

     /*----------5.3.10. Kilkari Repeat Listener Month-Wise Report -------*/

    @Override
    public AggregateKilkariRepeatListenerMonthWiseDto getKilkariRepeatListenerMonthWiseReport(ReportRequest reportRequest){
        AggregateKilkariRepeatListenerMonthWiseDto aggregateKilkariRepeatListenerMonthWiseDto = new AggregateKilkariRepeatListenerMonthWiseDto();

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Integer locationId;
        String locationType;
        Date toDate = new Date();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 0);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toDate = aCalendar.getTime();

        Calendar aCalendar1 = new GregorianCalendar(2016, 11, 01);
        aCalendar1.set(Calendar.MILLISECOND, 0);
        aCalendar1.set(Calendar.SECOND, 0);
        aCalendar1.set(Calendar.MINUTE, 0);
        aCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        Date refDate = aCalendar1.getTime();

        if(fromDate.compareTo(refDate) < 0){
            fromDate = refDate;
        }
        Date date = fromDate;

        List<KilkariRepeatListenerMonthWiseDto> kilkariRepeatListenerMonthWiseDtoList = new ArrayList<>();
        List<KilkariRepeatListenerMonthWisePercentDto> kilkariRepeatListenerMonthWisePercentDtoList = new ArrayList<>();
        List<KilkariRepeatListenerMonthWise> kilkariRepeatListenerMonthWiseList = new ArrayList<>();
        if (reportRequest.getStateId() == 0) {
            locationId = 0;
            locationType = "National";
        } else if (reportRequest.getDistrictId() == 0) {
            locationId = reportRequest.getStateId();
            locationType = "State";
        } else if(reportRequest.getBlockId() == 0){
            locationId = reportRequest.getDistrictId();
            locationType = "District";
        } else {
            locationId = reportRequest.getBlockId();
            locationType = "Block";
        }
        while(date.compareTo(toDate) < 0){
            kilkariRepeatListenerMonthWiseList.add(kilkariRepeatListenerMonthWiseDao.getListenerData(locationId,locationType,date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            date = cal.getTime();
        }

        if(!(kilkariRepeatListenerMonthWiseList.isEmpty())){
            for(int i = 0; i < kilkariRepeatListenerMonthWiseList.size(); i++){
                KilkariRepeatListenerMonthWiseDto kilkariRepeatListenerMonthWiseDto = new KilkariRepeatListenerMonthWiseDto();
                KilkariRepeatListenerMonthWisePercentDto kilkariRepeatListenerMonthWisePercentDto = new KilkariRepeatListenerMonthWisePercentDto();
                KilkariRepeatListenerMonthWise kilkariRepeatListenerMonthWise1 = kilkariRepeatListenerMonthWiseList.get(i);
                kilkariRepeatListenerMonthWiseDto.setId(kilkariRepeatListenerMonthWise1.getId());
                kilkariRepeatListenerMonthWiseDto.setMonth(new DateFormatSymbols().getMonths()[(fromDate.getMonth() + i)%12]);
                kilkariRepeatListenerMonthWiseDto.setDate(kilkariRepeatListenerMonthWise1.getDate());
                kilkariRepeatListenerMonthWiseDto.setMoreThanFiveCallsAnswered(kilkariRepeatListenerMonthWise1.getMoreThanFiveCallsAnswered());
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
                        +kilkariRepeatListenerMonthWise1.getNoCallsAnswered()
                        +kilkariRepeatListenerMonthWise1.getMoreThanFiveCallsAnswered()
                );
                kilkariRepeatListenerMonthWisePercentDto.setMoreThanFiveCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getMoreThanFiveCallsAnswered() == 0 ? 0.00 : (Math.round((kilkariRepeatListenerMonthWiseDto.getMoreThanFiveCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setFiveCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getFiveCallsAnswered() == 0 ? 0.00 : (Math.round((kilkariRepeatListenerMonthWiseDto.getFiveCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setFourCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getFourCallsAnswered() == 0 ? 0.00 :  (Math.round((kilkariRepeatListenerMonthWiseDto.getFourCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setThreeCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getThreeCallsAnswered() == 0 ? 0.00 :  (Math.round((kilkariRepeatListenerMonthWiseDto.getThreeCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setTwoCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getTwoCallsAnswered() == 0 ? 0.00 :  (Math.round((kilkariRepeatListenerMonthWiseDto.getTwoCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setOneCallAnsweredPercent( (double)  (kilkariRepeatListenerMonthWiseDto.getOneCallAnswered() == 0 ? 0.00 :  (Math.round((kilkariRepeatListenerMonthWiseDto.getOneCallAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setNoCallsAnsweredPercent((double) (kilkariRepeatListenerMonthWiseDto.getNoCallsAnswered() == 0 ? 0.00 :  (Math.round((kilkariRepeatListenerMonthWiseDto.getNoCallsAnswered() * 10000.0d) / kilkariRepeatListenerMonthWiseDto.getTotal()))) / 100d);
                kilkariRepeatListenerMonthWisePercentDto.setId(kilkariRepeatListenerMonthWise1.getId());
                kilkariRepeatListenerMonthWisePercentDto.setMonth(new DateFormatSymbols().getMonths()[(fromDate.getMonth() + i)%12]);
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
    public List<KilkariCallReportDto> getKilkariCallReport(ReportRequest reportRequest, User currentUser) {

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date toDate = new Date();
        Date startDate = new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


        //aCalendar.add(Calendar.DATE, -1);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 1);
        toDate = aCalendar.getTime();

        List<KilkariCallReportDto> callReportDtos = new ArrayList<>();
        List<KilkariCalls> kilkariCallStart = new ArrayList<>();
        List<KilkariCalls> kilkariCallEnd = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(0, "State", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(0, "State", toDate));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getStateId(), "District", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getStateId(), "District", toDate));
        } else if (reportRequest.getBlockId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(), "Block", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(), "Block", toDate));
        } else {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getBlockId(), "Subcentre", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getBlockId(), "Subcentre", toDate));
        }

        if (!(kilkariCallEnd.isEmpty()) && !(kilkariCallStart.isEmpty())) {
            for (int i = 0; i < kilkariCallEnd.size(); i++) {
                for (int j = 0; j < kilkariCallStart.size(); j++) {
                    if (kilkariCallEnd.get(i).getLocationId().equals(kilkariCallStart.get(j).getLocationId())) {
                        KilkariCalls end = kilkariCallEnd.get(i);
                        KilkariCalls start = kilkariCallStart.get(j);
                        KilkariCallReportDto kilkariCallReportDto = new KilkariCallReportDto();
                        kilkariCallReportDto.setLocationId(end.getLocationId());
                        kilkariCallReportDto.setContent_1_25(end.getContent_1_25() - start.getContent_1_25());
                        kilkariCallReportDto.setContent_25_50(end.getContent_25_50() - start.getContent_25_50());
                        kilkariCallReportDto.setContent_50_75(end.getContent_50_75() - start.getContent_50_75());
                        kilkariCallReportDto.setContent_75_100(end.getContent_75_100() - start.getContent_75_100());
                        kilkariCallReportDto.setBillableMinutes(end.getBillableMinutes() - start.getBillableMinutes());
                        kilkariCallReportDto.setCallsAttempted(end.getCallsAttempted() - start.getCallsAttempted());
                        kilkariCallReportDto.setCallsToInbox(end.getCallsToInbox() - start.getCallsToInbox());
                        kilkariCallReportDto.setSuccessfulCalls(end.getSuccessfulCalls() - start.getSuccessfulCalls());
                        kilkariCallReportDto.setAvgDuration((float)((kilkariCallReportDto.getSuccessfulCalls()==0)?0.00 : (float) Math.round( kilkariCallReportDto.getBillableMinutes() / (float) kilkariCallReportDto.getSuccessfulCalls() * 100) / 100));
                        kilkariCallReportDto.setLocationType(end.getLocationType());
                        String locationType = end.getLocationType();
                        if (locationType.equalsIgnoreCase("State")) {
                            kilkariCallReportDto.setLocationName(stateDao.findByStateId(end.getLocationId().intValue()).getStateName());
                        }
                        if (locationType.equalsIgnoreCase("District")) {
                            kilkariCallReportDto.setLocationName(districtDao.findByDistrictId(end.getLocationId().intValue()).getDistrictName());
                        }
                        if (locationType.equalsIgnoreCase("Block")) {
                            kilkariCallReportDto.setLocationName(blockDao.findByblockId(end.getLocationId().intValue()).getBlockName());
                        }
                        if (locationType.equalsIgnoreCase("Subcentre")) {
                            kilkariCallReportDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(end.getLocationId().intValue()).getHealthSubFacilityName());
                            kilkariCallReportDto.setLink(true);
                        }
                        if (locationType.equalsIgnoreCase("DifferenceState")) {
                            kilkariCallReportDto.setLocationName("No District Count");
                            kilkariCallReportDto.setLink(true);
                            kilkariCallReportDto.setLocationId((long) -1);
                        }
                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                            kilkariCallReportDto.setLocationName("No Block Count");
                            kilkariCallReportDto.setLink(true);
                            kilkariCallReportDto.setLocationId((long) -1);

                        }
                        if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                            kilkariCallReportDto.setLocationName("No Subcentre Count");
                            kilkariCallReportDto.setLink(true);
                            kilkariCallReportDto.setLocationId((long) -1);

                        }

                        if ((kilkariCallReportDto.getSuccessfulCalls() + Math.round(kilkariCallReportDto.getBillableMinutes()*100) + kilkariCallReportDto.getCallsAttempted()
                                + kilkariCallReportDto.getCallsToInbox() + kilkariCallReportDto.getContent_1_25()
                                + kilkariCallReportDto.getContent_25_50() + kilkariCallReportDto.getContent_50_75()
                                + kilkariCallReportDto.getContent_75_100()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
                            callReportDtos.add(kilkariCallReportDto);
                        }
                    }
                }
            }
        }
        return callReportDtos;
    }
    @Override
    public List<KilkariCallReportWithBeneficiariesDto> getKilkariCallReportWithBeneficiaries(ReportRequest reportRequest, User currentUser) {

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date toDate = new Date();
        Date startDate = new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


        //aCalendar.add(Calendar.DATE, -1);
        Date fromDate = aCalendar.getTime();
        aCalendar.setTime(reportRequest.getToDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DATE, 1);
        toDate = aCalendar.getTime();

        List<KilkariCallReportWithBeneficiariesDto> callReportWithBeneficiariesDtos = new ArrayList<>();
        List<KilkariCalls> kilkariCallStart = new ArrayList<>();
        List<KilkariCalls> kilkariCallEnd = new ArrayList<>();
        List<KilkariMessageListenership> kilkariMessageListenerships = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(0, "State", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(0, "State", toDate));
            kilkariMessageListenerships.addAll(this.getKilkariMessageListenershipData(0, "State", fromDate, toDate, reportRequest.getPeriodType()));
        } else if (reportRequest.getDistrictId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getStateId(), "District", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getStateId(), "District", toDate));
            kilkariMessageListenerships.addAll(this.getKilkariMessageListenershipData(reportRequest.getStateId(), "District", fromDate, toDate, reportRequest.getPeriodType()));
        } else if (reportRequest.getBlockId() == 0) {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(), "Block", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(), "Block", toDate));
            kilkariMessageListenerships.addAll(this.getKilkariMessageListenershipData(reportRequest.getDistrictId(), "Block", fromDate, toDate, reportRequest.getPeriodType()));
        } else {
            kilkariCallStart.addAll(this.getKilkariCallReport(reportRequest.getBlockId(), "Subcentre", fromDate));
            kilkariCallEnd.addAll(this.getKilkariCallReport(reportRequest.getBlockId(), "Subcentre", toDate));
            kilkariMessageListenerships.addAll(this.getKilkariMessageListenershipData(reportRequest.getBlockId(), "Subcentre", fromDate, toDate, reportRequest.getPeriodType()));
        }

        if (!(kilkariCallEnd.isEmpty()) && !(kilkariCallStart.isEmpty()) && !(kilkariMessageListenerships.isEmpty())) {
            for(int k = 0; k < kilkariMessageListenerships.size(); k++) {
                for (int i = 0; i < kilkariCallEnd.size(); i++) {
                    for (int j = 0; j < kilkariCallStart.size(); j++) {
                        if (kilkariCallEnd.get(i).getLocationId().equals(kilkariCallStart.get(j).getLocationId()) &&
                                kilkariCallStart.get(j).getLocationId().equals(kilkariMessageListenerships.get(k).getLocationId())) {
                            KilkariCalls end = kilkariCallEnd.get(i);
                            KilkariCalls start = kilkariCallStart.get(j);
                            KilkariMessageListenership kml = kilkariMessageListenerships.get(k);
                            KilkariCallReportWithBeneficiariesDto kilkariCallReportWithBeneficiariesDto = new KilkariCallReportWithBeneficiariesDto();
                            kilkariCallReportWithBeneficiariesDto.setLocationId(end.getLocationId());
                            kilkariCallReportWithBeneficiariesDto.setContent_1_25(end.getContent_1_25() - start.getContent_1_25());
                            kilkariCallReportWithBeneficiariesDto.setContent_75_100(end.getContent_75_100() - start.getContent_75_100());
                            kilkariCallReportWithBeneficiariesDto.setBillableMinutes(end.getBillableMinutes() - start.getBillableMinutes());
                            kilkariCallReportWithBeneficiariesDto.setCallsAttempted(end.getCallsAttempted() - start.getCallsAttempted());
                            kilkariCallReportWithBeneficiariesDto.setCallsToInbox(end.getCallsToInbox() - start.getCallsToInbox());
                            kilkariCallReportWithBeneficiariesDto.setSuccessfulCalls(end.getSuccessfulCalls() - start.getSuccessfulCalls());
                            kilkariCallReportWithBeneficiariesDto.setAvgDuration((float)((kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls()==0)?0.00 : (float) Math.round( kilkariCallReportWithBeneficiariesDto.getBillableMinutes() / (float) kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls() * 100) / 100));
                            kilkariCallReportWithBeneficiariesDto.setLocationType(end.getLocationType());
                            kilkariCallReportWithBeneficiariesDto.setBeneficiariesCalled(kml.getTotalBeneficiariesCalled());
                            kilkariCallReportWithBeneficiariesDto.setPercentageCalls_1_25((float) (kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls() == 0 ? 0 : (Math.round((kilkariCallReportWithBeneficiariesDto.getContent_1_25() * 10000.0f/ kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls())))) / 100f);
                            kilkariCallReportWithBeneficiariesDto.setPercentageCalls_75_100((float) (kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls() == 0 ? 0 : (Math.round((kilkariCallReportWithBeneficiariesDto.getContent_75_100() * 10000.0f/ kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls())))) / 100f);
                            kilkariCallReportWithBeneficiariesDto.setPercentageCallsResponded((float) (kilkariCallReportWithBeneficiariesDto.getCallsAttempted() == 0 ? 0 : (Math.round((kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls() * 10000.0f/ kilkariCallReportWithBeneficiariesDto.getCallsAttempted())))) / 100f);
                            String locationType = end.getLocationType();
                            if (locationType.equalsIgnoreCase("State")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName(stateDao.findByStateId(end.getLocationId().intValue()).getStateName());
                            }
                            if (locationType.equalsIgnoreCase("District")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName(districtDao.findByDistrictId(end.getLocationId().intValue()).getDistrictName());
                            }
                            if (locationType.equalsIgnoreCase("Block")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName(blockDao.findByblockId(end.getLocationId().intValue()).getBlockName());
                            }
                            if (locationType.equalsIgnoreCase("Subcentre")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(end.getLocationId().intValue()).getHealthSubFacilityName());
                                kilkariCallReportWithBeneficiariesDto.setLink(true);
                            }
                            if (locationType.equalsIgnoreCase("DifferenceState")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName("No District Count");
                                kilkariCallReportWithBeneficiariesDto.setLink(true);
                                kilkariCallReportWithBeneficiariesDto.setLocationId((long) -1);
                            }
                            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName("No Block Count");
                                kilkariCallReportWithBeneficiariesDto.setLink(true);
                                kilkariCallReportWithBeneficiariesDto.setLocationId((long) -1);

                            }
                            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                                kilkariCallReportWithBeneficiariesDto.setLocationName("No Subcentre Count");
                                kilkariCallReportWithBeneficiariesDto.setLink(true);
                                kilkariCallReportWithBeneficiariesDto.setLocationId((long) -1);

                            }

                            if ((kilkariCallReportWithBeneficiariesDto.getSuccessfulCalls() + Math.round(kilkariCallReportWithBeneficiariesDto.getBillableMinutes()*100) + kilkariCallReportWithBeneficiariesDto.getCallsAttempted()
                                    + kilkariCallReportWithBeneficiariesDto.getCallsToInbox() + kilkariCallReportWithBeneficiariesDto.getContent_1_25()
                                    + kilkariCallReportWithBeneficiariesDto.getContent_75_100()) != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
                                callReportWithBeneficiariesDtos.add(kilkariCallReportWithBeneficiariesDto);
                            }
                        }
                    }
                }
            }

        }
        return callReportWithBeneficiariesDtos;
    }



    private List<KilkariCalls> getKilkariCallReport(Integer locationId,String locationType, Date toDate){
        Date date = toDate;
        List<KilkariCalls> kilkariCall = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("KILKARI");
            for(State s:states){
                if(date.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI"))){
                    date = stateServiceDao.getServiceStartDateForState(s.getStateId(),"KILKARI");
                }
                kilkariCall.add(kilkariCallReportDao.getKilkariCallreport(s.getStateId(),locationType,date));
                date = toDate;
            }

        }  else if(locationType.equalsIgnoreCase("District")){
            if(date.before(stateServiceDao.getServiceStartDateForState(locationId,"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(locationId,"KILKARI");
            }
            List<District> districts = districtDao.getDistrictsOfState(locationId);
            KilkariCalls stateCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"State",date);
            Long callsAttempted = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            Long callsToInbox = (long)0;
            Long content_75_100 = (long)0;
            Long content_50_75 = (long)0;
            Long content_25_50 = (long)0;
            Long content_1_25 = (long)0;
            for(District d:districts){
                KilkariCalls districtCount = kilkariCallReportDao.getKilkariCallreport(d.getDistrictId(),locationType,date);
                kilkariCall.add(districtCount);
                callsAttempted+=districtCount.getCallsAttempted();
                successfulCalls+=districtCount.getSuccessfulCalls();
                billableMinutes+=districtCount.getBillableMinutes();
                callsToInbox+=districtCount.getCallsToInbox();
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
            noDistrictCount.setContent_1_25(stateCounts.getContent_1_25()-content_1_25);
            noDistrictCount.setContent_25_50(stateCounts.getContent_25_50()-content_25_50);
            noDistrictCount.setContent_50_75(stateCounts.getContent_50_75()-content_50_75);
            noDistrictCount.setContent_75_100(stateCounts.getContent_75_100()-content_75_100);
            noDistrictCount.setLocationType("DifferenceState");
            noDistrictCount.setId((int)(noDistrictCount.getBillableMinutes()+noDistrictCount.getCallsAttempted()+noDistrictCount.getCallsToInbox()+noDistrictCount.getContent_1_25()+noDistrictCount.getContent_25_50()+noDistrictCount.getContent_50_75()+noDistrictCount.getContent_75_100()+noDistrictCount.getSuccessfulCalls()));
            noDistrictCount.setLocationId((long)(-1));
            kilkariCall.add(noDistrictCount);
        } else if(locationType.equalsIgnoreCase("Block")) {
            if(date.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"KILKARI");
            }
            List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
            KilkariCalls districtCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"District",date);
            Long callsAttempted = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            Long callsToInbox = (long)0;
            Long content_75_100 = (long)0;
            Long content_50_75 = (long)0;
            Long content_25_50 = (long)0;
            Long content_1_25 = (long)0;
            for (Block d : blocks) {
                KilkariCalls blockCount = kilkariCallReportDao.getKilkariCallreport(d.getBlockId(),locationType,date);
                kilkariCall.add(blockCount);
                callsAttempted+=blockCount.getCallsAttempted();
                successfulCalls+=blockCount.getSuccessfulCalls();
                billableMinutes+=blockCount.getBillableMinutes();
                callsToInbox+=blockCount.getCallsToInbox();
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
            noBlockCount.setContent_1_25(districtCounts.getContent_1_25()-content_1_25);
            noBlockCount.setContent_25_50(districtCounts.getContent_25_50()-content_25_50);
            noBlockCount.setContent_50_75(districtCounts.getContent_50_75()-content_50_75);
            noBlockCount.setContent_75_100(districtCounts.getContent_75_100()-content_75_100);
            noBlockCount.setLocationType("DifferenceDistrict");
            noBlockCount.setId((int)(noBlockCount.getBillableMinutes()+noBlockCount.getCallsAttempted()+noBlockCount.getCallsToInbox()+noBlockCount.getContent_1_25()+noBlockCount.getContent_25_50()+noBlockCount.getContent_50_75()+noBlockCount.getContent_75_100()+noBlockCount.getSuccessfulCalls()));
            noBlockCount.setLocationId((long)(-1));
            kilkariCall.add(noBlockCount);
        } else {
            if(date.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"KILKARI"))){
                date = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"KILKARI");
            }
            List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
            List<HealthSubFacility> subcenters = new ArrayList<>();
            for(HealthFacility hf :healthFacilities){
                subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
            }
            KilkariCalls blockCounts = kilkariCallReportDao.getKilkariCallreport(locationId,"block",date);
            Long callsAttempted = (long)0;
            Long successfulCalls = (long)0;
            Double billableMinutes = 0.00;
            Long callsToInbox = (long)0;
            Long content_75_100 = (long)0;
            Long content_50_75 = (long)0;
            Long content_25_50 = (long)0;
            Long content_1_25 = (long)0;
            for(HealthSubFacility s: subcenters){
                KilkariCalls SubcenterCount = kilkariCallReportDao.getKilkariCallreport(s.getHealthSubFacilityId(),locationType,date);
                kilkariCall.add(SubcenterCount);
                callsAttempted+=SubcenterCount.getCallsAttempted();
                successfulCalls+=SubcenterCount.getSuccessfulCalls();
                billableMinutes+=SubcenterCount.getBillableMinutes();
                callsToInbox+=SubcenterCount.getCallsToInbox();
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
            noSubcenterCount.setContent_1_25(blockCounts.getContent_1_25()-content_1_25);
            noSubcenterCount.setContent_25_50(blockCounts.getContent_25_50()-content_25_50);
            noSubcenterCount.setContent_50_75(blockCounts.getContent_50_75()-content_50_75);
            noSubcenterCount.setContent_75_100(blockCounts.getContent_75_100()-content_75_100);
            noSubcenterCount.setLocationType("DifferenceBlock");
            noSubcenterCount.setId((int)(noSubcenterCount.getBillableMinutes()+noSubcenterCount.getCallsAttempted()+noSubcenterCount.getCallsToInbox()+noSubcenterCount.getContent_1_25()+noSubcenterCount.getContent_25_50()+noSubcenterCount.getContent_50_75()+noSubcenterCount.getContent_75_100()+noSubcenterCount.getSuccessfulCalls()));
            noSubcenterCount.setLocationId((long)(-1));
            kilkariCall.add(noSubcenterCount);
        }
        return kilkariCall;
    }
    private boolean serviceStarted(Integer locationId,String locationType, Date toDate, Date fromDate, String sreviceType){
        if(locationType.equalsIgnoreCase("State")){
            return !toDate.before(stateServiceDao.getServiceStartDateForState( locationId,sreviceType));
        }else{
            if(locationType.equalsIgnoreCase("District")){
                return !toDate.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),sreviceType));
            }else{
                if(locationType.equalsIgnoreCase("Block")){
                    return !toDate.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),sreviceType));
                }
            }
        }
        return true;
    }
}
