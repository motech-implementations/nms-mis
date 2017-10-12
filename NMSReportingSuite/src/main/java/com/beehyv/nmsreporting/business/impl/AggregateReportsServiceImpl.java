package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AggregateReportsService;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.enums.*;
import com.beehyv.nmsreporting.model.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("aggregateReportsService")
@Transactional
public class AggregateReportsServiceImpl implements AggregateReportsService {

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
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeMADao aggregateCumulativeMADao;

    @Autowired
    private AggregateCumulativekilkariDao aggregateCumulativekilkariDao;

    @Autowired
    private AggCumulativeBeneficiaryComplDao aggCumulativeBeneficiaryComplDao;

    @Autowired
    private AggregateCumulativeBeneficiaryDao aggregateCumulativeBeneficiaryDao;

    @Autowired
    private KilkariUsageDao kilkariUsageDao;

    @Autowired
    private ListeningMatrixDao listeningMatrixDao;

    @Autowired
    private KilkariCallReportDao kilkariCallReportDao;

    @Override
    public List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeMA> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("M");
            for(State s:states){
                CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeMA stateCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"State",toDate);
                Integer ashasRegistered = 0;
                Integer ashasStarted = 0;
                Integer ashasNotStarted = 0;
                Integer ashasCompleted = 0;
                Integer ashasFailed = 0;
                Integer ashasRejected = 0;
                for(District d:districts){
                    AggregateCumulativeMA distrcitCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,toDate);
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,toDate));
                    ashasStarted+=distrcitCount.getAshasStarted();
                    ashasCompleted+=distrcitCount.getAshasCompleted();
                    ashasFailed+=distrcitCount.getAshasFailed();
                    ashasNotStarted+=distrcitCount.getAshasNotStarted();
                    ashasRejected+=distrcitCount.getAshasRejected();
                    ashasRegistered+=distrcitCount.getAshasRegistered();
                }
                AggregateCumulativeMA noDistrictCount = new AggregateCumulativeMA();
                noDistrictCount.setAshasRejected(stateCounts.getAshasRejected()-ashasRejected);
                noDistrictCount.setAshasNotStarted(stateCounts.getAshasNotStarted()-ashasNotStarted);
                noDistrictCount.setAshasRegistered(stateCounts.getAshasRegistered()-ashasRegistered);
                noDistrictCount.setAshasFailed(stateCounts.getAshasFailed()-ashasFailed);
                noDistrictCount.setAshasCompleted(stateCounts.getAshasCompleted()-ashasCompleted);
                noDistrictCount.setAshasStarted(stateCounts.getAshasStarted()-ashasStarted);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId(stateCounts.getAshasRejected()-ashasRejected+stateCounts.getAshasNotStarted()-ashasNotStarted+stateCounts.getAshasRegistered()-ashasRegistered+stateCounts.getAshasFailed()-ashasFailed+stateCounts.getAshasCompleted()-ashasCompleted+stateCounts.getAshasStarted()-ashasStarted);
                noDistrictCount.setLocationId((long)-locationId);
                CumulativeSummery.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeMA districtCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"District",toDate);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for (Block d : blocks) {
                        AggregateCumulativeMA blockCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType,toDate));
                        ashasStarted+=blockCount.getAshasStarted();
                        ashasCompleted+=blockCount.getAshasCompleted();
                        ashasFailed+=blockCount.getAshasFailed();
                        ashasNotStarted+=blockCount.getAshasNotStarted();
                        ashasRejected+=blockCount.getAshasRejected();
                        ashasRegistered+=blockCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA noBlockCount = new AggregateCumulativeMA();
                    noBlockCount.setAshasRejected(districtCounts.getAshasRejected()-ashasRejected);
                    noBlockCount.setAshasNotStarted(districtCounts.getAshasNotStarted()-ashasNotStarted);
                    noBlockCount.setAshasRegistered(districtCounts.getAshasRegistered()-ashasRegistered);
                    noBlockCount.setAshasFailed(districtCounts.getAshasFailed()-ashasFailed);
                    noBlockCount.setAshasCompleted(districtCounts.getAshasCompleted()-ashasCompleted);
                    noBlockCount.setAshasStarted(districtCounts.getAshasStarted()-ashasStarted);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId(districtCounts.getAshasRejected()-ashasRejected+districtCounts.getAshasNotStarted()-ashasNotStarted+districtCounts.getAshasRegistered()-ashasRegistered+districtCounts.getAshasFailed()-ashasFailed+districtCounts.getAshasCompleted()-ashasCompleted+districtCounts.getAshasStarted()-ashasStarted);
                    noBlockCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeMA blockCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"block",toDate);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeMA subcenterCount = aggregateCumulativeMADao.getMACumulativeSummery(s.getSubcenterId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getSubcenterId(), locationType,toDate));
                        ashasStarted+=subcenterCount.getAshasStarted();
                        ashasCompleted+=subcenterCount.getAshasCompleted();
                        ashasFailed+=subcenterCount.getAshasFailed();
                        ashasNotStarted+=subcenterCount.getAshasNotStarted();
                        ashasRejected+=subcenterCount.getAshasRejected();
                        ashasRegistered+=subcenterCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA nosubcenterCount = new AggregateCumulativeMA();
                    nosubcenterCount.setAshasRejected(blockCounts.getAshasRejected()-ashasRejected);
                    nosubcenterCount.setAshasNotStarted(blockCounts.getAshasNotStarted()-ashasNotStarted);
                    nosubcenterCount.setAshasRegistered(blockCounts.getAshasRegistered()-ashasRegistered);
                    nosubcenterCount.setAshasFailed(blockCounts.getAshasFailed()-ashasFailed);
                    nosubcenterCount.setAshasCompleted(blockCounts.getAshasCompleted()-ashasCompleted);
                    nosubcenterCount.setAshasStarted(blockCounts.getAshasStarted()-ashasStarted);
                    nosubcenterCount.setLocationType("DifferenceBlock");
                    nosubcenterCount.setId(blockCounts.getAshasRejected()-ashasRejected+blockCounts.getAshasNotStarted()-ashasNotStarted+blockCounts.getAshasRegistered()-ashasRegistered+blockCounts.getAshasFailed()-ashasFailed+blockCounts.getAshasCompleted()-ashasCompleted+blockCounts.getAshasStarted()-ashasStarted);
                    nosubcenterCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(nosubcenterCount);
                }
            }
        }

        return CumulativeSummery;
    };

    public List<AggregateCumulativeKilkari> getCumulativeSummaryKilkariReport(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeKilkari> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                CumulativeSummery.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeKilkari stateCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"State",toDate);
                Integer uniqueBeneficiaries = 0;
                Long successfulCalls = (long)0;
                Long billableMinutes = (long)0;
                for(District d:districts){
                    AggregateCumulativeKilkari distrcitCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getDistrictId(),locationType,toDate);
                    CumulativeSummery.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getDistrictId(),locationType,toDate));
                    uniqueBeneficiaries+=distrcitCount.getUniqueBeneficiaries();
                    successfulCalls+=distrcitCount.getSuccessfulCalls();
                    billableMinutes+=distrcitCount.getBillableMinutes();
                }
                AggregateCumulativeKilkari noDistrictCount = new AggregateCumulativeKilkari();
                noDistrictCount.setUniqueBeneficiaries(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
                noDistrictCount.setSuccessfulCalls(stateCounts.getSuccessfulCalls()-successfulCalls);
                noDistrictCount.setBillableMinutes(stateCounts.getBillableMinutes()-billableMinutes);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(stateCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+stateCounts.getSuccessfulCalls()-successfulCalls+stateCounts.getBillableMinutes()-billableMinutes));
                noDistrictCount.setLocationId((long)(-1));
                CumulativeSummery.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeKilkari districtCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"District",toDate);
                    Integer uniqueBeneficiaries = 0;
                    Long successfulCalls = (long)0;
                    Long billableMinutes = (long)0;
                    for (Block d : blocks) {
                        AggregateCumulativeKilkari blockCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getBlockId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativekilkariDao.getKilkariCumulativeSummary(d.getBlockId(), locationType,toDate));
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
                    noBlockCount.setLocationId((long)(-1));
                    CumulativeSummery.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeKilkari blockCounts = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(locationId,"block",toDate);
                    Integer uniqueBeneficiaries = 0;
                    Long successfulCalls = (long)0;
                    Long billableMinutes = (long)0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeKilkari subcenterCount = aggregateCumulativekilkariDao.getKilkariCumulativeSummary(s.getSubcenterId(),locationType,toDate);
                        CumulativeSummery.add(subcenterCount);
                        uniqueBeneficiaries+=subcenterCount.getUniqueBeneficiaries();
                        successfulCalls+=subcenterCount.getSuccessfulCalls();
                        billableMinutes+=subcenterCount.getBillableMinutes();
                    }
                    AggregateCumulativeKilkari nosubcenterCount = new AggregateCumulativeKilkari();
                    nosubcenterCount.setUniqueBeneficiaries(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries);
                    nosubcenterCount.setSuccessfulCalls(blockCounts.getSuccessfulCalls()-successfulCalls);
                    nosubcenterCount.setBillableMinutes(blockCounts.getBillableMinutes()-billableMinutes);
                    nosubcenterCount.setLocationType("DifferenceBlock");
                    nosubcenterCount.setId((int)(blockCounts.getUniqueBeneficiaries()-uniqueBeneficiaries+blockCounts.getSuccessfulCalls()-successfulCalls+blockCounts.getBillableMinutes()-billableMinutes));
                    nosubcenterCount.setLocationId((long)(-1));
                    CumulativeSummery.add(nosubcenterCount);
                }
            }
        }

        return CumulativeSummery;
    };

    @Override
    public List<AggregateCumulativekilkariDto> getKilkariCumulativeSummary(ReportRequest reportRequest,User currentUser){

//            List<Map<String,String>> summaryReport = new ArrayList<>();
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(reportRequest.getToDate().getTime());
            String toDateString = formatter.format(calendar.getTime());
            Date toDate = new Date();
            try {
                toDate = formatter.parse(toDateString);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<AggregateCumulativekilkariDto> summaryDto = new ArrayList<>();
            List<AggregateCumulativeKilkari> cumulativesummaryReport = new ArrayList<>();

            if (reportRequest.getStateId() == 0) {
                cumulativesummaryReport.addAll(this.getCumulativeSummaryKilkariReport(0,"State",toDate));
            }
            else{
                if (reportRequest.getDistrictId() == 0) {
                    cumulativesummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getStateId(),"District",toDate));
                }
                else{
                    if(reportRequest.getBlockId() == 0){
                        cumulativesummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getDistrictId(),"Block",toDate));
                    }
                    else {
                        cumulativesummaryReport.addAll(this.getCumulativeSummaryKilkariReport(reportRequest.getBlockId(),"Subcenter",toDate));
                    }
                }


            }

            for(AggregateCumulativeKilkari a:cumulativesummaryReport){
                AggregateCumulativekilkariDto summaryDto1 = new AggregateCumulativekilkariDto();
                summaryDto1.setId(a.getId());
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setUniqueBeneficiaries(a.getUniqueBeneficiaries());
                summaryDto1.setSuccessfulCalls(a.getSuccessfulCalls());
                summaryDto1.setBillableMinutes(a.getBillableMinutes());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setAverageDuration(a.getSuccessfulCalls() ==0?0:a.getBillableMinutes()/a.getSuccessfulCalls());
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
                    summaryDto1.setLocationName("No District Count");
                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block Count");
                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcenter Count");
                }

                if(a.getId()!=0){
                    summaryDto.add(summaryDto1);
                }

            }
            return summaryDto;




    }

/*------- cumulative beneficiary completion----*/
    private List<AggregateCumulativeBeneficiaryCompletion> getCumulativeBeneficiaryCompletion(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeBeneficiaryCompletion> CumulativeCompletion = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                CumulativeCompletion.add(aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeBeneficiaryCompletion stateCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"State",toDate);
                Long completedBeneficiaries = (long)0;
                Long calls_75_100 = (long)0;
                Long calls_50_75 = (long)0;
                Long calls_25_50 = (long)0;
                Long calls_1_25 = (long)0;
                for(District d:districts){
                    AggregateCumulativeBeneficiaryCompletion distrcitCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getDistrictId(),locationType,toDate);
                    CumulativeCompletion.add(distrcitCount);
                    completedBeneficiaries+=distrcitCount.getCompletedBeneficiaries();
                    calls_75_100+=distrcitCount.getCalls_75_100();
                    calls_50_75+=distrcitCount.getCalls_50_75();
                    calls_25_50+=distrcitCount.getCalls_25_50();
                    calls_1_25+=distrcitCount.getCalls_1_25();
                }
                AggregateCumulativeBeneficiaryCompletion noDistrictCount = new AggregateCumulativeBeneficiaryCompletion();
                noDistrictCount.setCompletedBeneficiaries(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                noDistrictCount.setCalls_75_100(stateCounts.getCalls_75_100()-calls_75_100);
                noDistrictCount.setCalls_50_75(stateCounts.getCalls_50_75()-calls_50_75);
                noDistrictCount.setCalls_25_50(stateCounts.getCalls_25_50()-calls_25_50);
                noDistrictCount.setCalls_1_25(stateCounts.getCalls_1_25()-calls_1_25);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries+stateCounts.getCalls_75_100()-calls_75_100+stateCounts.getCalls_50_75()-calls_50_75+stateCounts.getCalls_25_50()-calls_25_50+stateCounts.getCalls_1_25()-calls_1_25));
                noDistrictCount.setLocationId((long)(-1));
                CumulativeCompletion.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeBeneficiaryCompletion districtCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"District",toDate);
                    Long completedBeneficiaries = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    for (Block d : blocks) {
                        AggregateCumulativeBeneficiaryCompletion blockCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(d.getBlockId(),locationType,toDate);
                        CumulativeCompletion.add(blockCount);
                        completedBeneficiaries+=blockCount.getCompletedBeneficiaries();
                        calls_75_100+=blockCount.getCalls_75_100();
                        calls_50_75+=blockCount.getCalls_50_75();
                        calls_25_50+=blockCount.getCalls_25_50();
                        calls_1_25+=blockCount.getCalls_1_25();
                    }
                    AggregateCumulativeBeneficiaryCompletion noBlockCount = new AggregateCumulativeBeneficiaryCompletion();
                    noBlockCount.setCompletedBeneficiaries(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                    noBlockCount.setCalls_75_100(districtCounts.getCalls_75_100()-calls_75_100);
                    noBlockCount.setCalls_50_75(districtCounts.getCalls_50_75()-calls_50_75);
                    noBlockCount.setCalls_25_50(districtCounts.getCalls_25_50()-calls_25_50);
                    noBlockCount.setCalls_1_25(districtCounts.getCalls_1_25()-calls_1_25);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries+districtCounts.getCalls_75_100()-calls_75_100+districtCounts.getCalls_50_75()-calls_50_75+districtCounts.getCalls_25_50()-calls_25_50+districtCounts.getCalls_1_25()-calls_1_25));
                    noBlockCount.setLocationId((long)(-1));
                    CumulativeCompletion.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeBeneficiaryCompletion blockCounts = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(locationId,"block",toDate);
                    Long completedBeneficiaries = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeBeneficiaryCompletion subcenterCount = aggCumulativeBeneficiaryComplDao.getBeneficiaryCompletion(s.getSubcenterId(),locationType,toDate);
                        CumulativeCompletion.add(subcenterCount);
                        completedBeneficiaries+=subcenterCount.getCompletedBeneficiaries();
                        calls_75_100+=subcenterCount.getCalls_75_100();
                        calls_50_75+=subcenterCount.getCalls_50_75();
                        calls_25_50+=subcenterCount.getCalls_25_50();
                        calls_1_25+=subcenterCount.getCalls_1_25();
                    }
                    AggregateCumulativeBeneficiaryCompletion nosubcenterCount = new AggregateCumulativeBeneficiaryCompletion();
                    nosubcenterCount.setCompletedBeneficiaries(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries);
                    nosubcenterCount.setCalls_75_100(blockCounts.getCalls_75_100()-calls_75_100);
                    nosubcenterCount.setCalls_50_75(blockCounts.getCalls_50_75()-calls_50_75);
                    nosubcenterCount.setCalls_25_50(blockCounts.getCalls_25_50()-calls_25_50);
                    nosubcenterCount.setCalls_1_25(blockCounts.getCalls_1_25()-calls_1_25);
                    nosubcenterCount.setLocationType("DifferenceBlock");
                    nosubcenterCount.setId((int)(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries+blockCounts.getCalls_75_100()-calls_75_100+blockCounts.getCalls_50_75()-calls_50_75+blockCounts.getCalls_25_50()-calls_25_50+blockCounts.getCalls_1_25()-calls_1_25));
                    nosubcenterCount.setLocationId((long)(-1));
                    CumulativeCompletion.add(nosubcenterCount);
                }
            }
        }

        return CumulativeCompletion;
    };

    @Override
    public List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletion(ReportRequest reportRequest, User currentUser){

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reportRequest.getToDate().getTime());
        String toDateString = formatter.format(calendar.getTime());
        Date toDate = new Date();
        try {
            toDate = formatter.parse(toDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(reportRequest.getFromDate().getTime());
        String fromDateString = formatter.format(calendar.getTime());
        Date fromDate = new Date();
        try {
            fromDate = formatter.parse(fromDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, -1);
        fromDate = calendar.getTime();


        List<AggCumulativeBeneficiaryComplDto> summaryDto = new ArrayList<>();
//        List<AggregateCumulativeBeneficiaryCompletion> cumulativeCompletionReportStart = new ArrayList<>();
        List<AggregateCumulativeBeneficiaryCompletion> cumulativeCompletionReportEnd = new ArrayList<>();


        if (reportRequest.getStateId() == 0) {
//            cumulativeCompletionReportStart.addAll(this.getCumulativeBeneficiaryCompletion(0,"State",fromDate));
            cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletion(0,"State",toDate));
        }
        else{
            if (reportRequest.getDistrictId() == 0) {
//                cumulativeCompletionReportStart.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getStateId(),"District",fromDate));
                cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getStateId(),"District",toDate));
            }
            else{
                if(reportRequest.getBlockId() == 0){
//                    cumulativeCompletionReportStart.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getDistrictId(),"Block",fromDate));
                    cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getDistrictId(),"Block",toDate));
                }
                else {
//                    cumulativeCompletionReportStart.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getBlockId(),"Subcenter",fromDate));
                    cumulativeCompletionReportEnd.addAll(this.getCumulativeBeneficiaryCompletion(reportRequest.getBlockId(),"Subcenter",toDate));
                }
            }


        }

        for(int i=0;i<cumulativeCompletionReportEnd.size();i++){
//            for(int j=0;j<cumulativeCompletionReportStart.size();j++)  {
//                if(cumulativeCompletionReportEnd.get(i).getLocationId().equals(cumulativeCompletionReportStart.get(j).getLocationId())){
                    AggregateCumulativeBeneficiaryCompletion a = cumulativeCompletionReportEnd.get(i);
//                    AggregateCumulativeBeneficiaryCompletion b = cumulativeCompletionReportStart.get(j);
                    AggCumulativeBeneficiaryComplDto summaryDto1 = new AggCumulativeBeneficiaryComplDto();
                    summaryDto1.setId(a.getId());
                    summaryDto1.setLocationId(a.getLocationId());
                    summaryDto1.setCompletedBeneficiaries(a.getCompletedBeneficiaries());
                    summaryDto1.setCalls_75_100(a.getCalls_75_100());
                    summaryDto1.setCalls_50_75(a.getCalls_50_75());
                    summaryDto1.setCalls_25_50(a.getCalls_25_50());
                    summaryDto1.setCalls_1_25(a.getCalls_1_25());
                    summaryDto1.setLocationType(a.getLocationType());
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
                        summaryDto1.setLocationName("No District Count");
                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        summaryDto1.setLocationName("No Block Count");
                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        summaryDto1.setLocationName("No Subcenter Count");
                    }

                    if(a.getId()!=0){
                        summaryDto.add(summaryDto1);
                    }
                }





        return summaryDto;
    }


/*----------5.3.3. Kilkari Aggregate Beneficiaries Report -------*/

    private List<AggregateCumulativeBeneficiary> getCumulativeBeneficiary(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeBeneficiary> CumulativeBeneficiary = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                CumulativeBeneficiary.add(aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeBeneficiary stateCounts = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(locationId,"State",toDate);
                Long beneficiariesCalled = (long)0;
                Long selfDeactivated = (long)0;
                Long notAnswering = (long)0;
                Long lowListenership = (long)0;
                Long systemDeactivation = (long)0;
                Long motherCompletion = (long)0;
                Long childCompletion = (long)0;
                Long calledInbox = (long)0;
                Long joinedSubscription = (long)0;
                for(District d:districts){
                    AggregateCumulativeBeneficiary distrcitCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(d.getDistrictId(),locationType,toDate);
                    CumulativeBeneficiary.add(distrcitCount);
                    beneficiariesCalled+=distrcitCount.getBeneficiariesCalled();
                    selfDeactivated+=distrcitCount.getSelfDeactivated();
                    notAnswering+=distrcitCount.getNotAnswering();
                    lowListenership+=distrcitCount.getLowListenership();
                    systemDeactivation+=distrcitCount.getSystemDeactivation();
                    motherCompletion+=distrcitCount.getMotherCompletion();
                    childCompletion+=distrcitCount.getChildCompletion();
                    calledInbox+=distrcitCount.getCalledInbox();
                    joinedSubscription+=distrcitCount.getJoinedSubscription();

                }
                AggregateCumulativeBeneficiary noDistrictCount = new AggregateCumulativeBeneficiary();
                noDistrictCount.setBeneficiariesCalled(stateCounts.getBeneficiariesCalled()-beneficiariesCalled);
                noDistrictCount.setSelfDeactivated(stateCounts.getSelfDeactivated()-selfDeactivated);
                noDistrictCount.setNotAnswering(stateCounts.getNotAnswering()-notAnswering);
                noDistrictCount.setLowListenership(stateCounts.getLowListenership()-lowListenership);
                noDistrictCount.setSystemDeactivation(stateCounts.getSystemDeactivation()-systemDeactivation);
                noDistrictCount.setMotherCompletion(stateCounts.getMotherCompletion()-motherCompletion);
                noDistrictCount.setChildCompletion(stateCounts.getChildCompletion()-childCompletion);
                noDistrictCount.setCalledInbox(stateCounts.getCalledInbox()-calledInbox);
                noDistrictCount.setJoinedSubscription(stateCounts.getJoinedSubscription()-joinedSubscription);
                noDistrictCount.setLocationType("DifferenceState");
//                noDistrictCount.setId((int)(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries+stateCounts.getCalls_75_100()-calls_75_100+stateCounts.getCalls_50_75()-calls_50_75+stateCounts.getCalls_25_50()-calls_25_50+stateCounts.getCalls_1_25()-calls_1_25));
                noDistrictCount.setLocationId((long)(-1));
                CumulativeBeneficiary.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeBeneficiary districtCounts = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(locationId,"District",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long selfDeactivated = (long)0;
                    Long notAnswering = (long)0;
                    Long lowListenership = (long)0;
                    Long systemDeactivation = (long)0;
                    Long motherCompletion = (long)0;
                    Long childCompletion = (long)0;
                    Long calledInbox = (long)0;
                    Long joinedSubscription = (long)0;
                    for (Block d : blocks) {
                        AggregateCumulativeBeneficiary blockCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(d.getBlockId(),locationType,toDate);
                        CumulativeBeneficiary.add(blockCount);
                        beneficiariesCalled+=blockCount.getBeneficiariesCalled();
                        selfDeactivated+=blockCount.getSelfDeactivated();
                        notAnswering+=blockCount.getNotAnswering();
                        lowListenership+=blockCount.getLowListenership();
                        systemDeactivation+=blockCount.getSystemDeactivation();
                        motherCompletion+=blockCount.getMotherCompletion();
                        childCompletion+=blockCount.getChildCompletion();
                        calledInbox+=blockCount.getCalledInbox();
                        joinedSubscription+=blockCount.getJoinedSubscription();

                    }
                    AggregateCumulativeBeneficiary noBlockCount = new AggregateCumulativeBeneficiary();
                    noBlockCount.setBeneficiariesCalled(districtCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    noBlockCount.setSelfDeactivated(districtCounts.getSelfDeactivated()-selfDeactivated);
                    noBlockCount.setNotAnswering(districtCounts.getNotAnswering()-notAnswering);
                    noBlockCount.setLowListenership(districtCounts.getLowListenership()-lowListenership);
                    noBlockCount.setSystemDeactivation(districtCounts.getSystemDeactivation()-systemDeactivation);
                    noBlockCount.setMotherCompletion(districtCounts.getMotherCompletion()-motherCompletion);
                    noBlockCount.setChildCompletion(districtCounts.getChildCompletion()-childCompletion);
                    noBlockCount.setCalledInbox(districtCounts.getCalledInbox()-calledInbox);
                    noBlockCount.setJoinedSubscription(districtCounts.getJoinedSubscription()-joinedSubscription);
                    noBlockCount.setLocationType("DifferenceDistrict");
//                    noBlockCount.setId((int)(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries+districtCounts.getCalls_75_100()-calls_75_100+districtCounts.getCalls_50_75()-calls_50_75+districtCounts.getCalls_25_50()-calls_25_50+districtCounts.getCalls_1_25()-calls_1_25));
                    noBlockCount.setLocationId((long)(-1));
                    CumulativeBeneficiary.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeBeneficiary blockCounts = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(locationId,"block",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long selfDeactivated = (long)0;
                    Long notAnswering = (long)0;
                    Long lowListenership = (long)0;
                    Long systemDeactivation = (long)0;
                    Long motherCompletion = (long)0;
                    Long childCompletion = (long)0;
                    Long calledInbox = (long)0;
                    Long joinedSubscription = (long)0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeBeneficiary subcenterCount = aggregateCumulativeBeneficiaryDao.getCumulativeBeneficiary(s.getSubcenterId(),locationType,toDate);
                        CumulativeBeneficiary.add(subcenterCount);
                        beneficiariesCalled+=subcenterCount.getBeneficiariesCalled();
                        selfDeactivated+=subcenterCount.getSelfDeactivated();
                        notAnswering+=subcenterCount.getNotAnswering();
                        lowListenership+=subcenterCount.getLowListenership();
                        systemDeactivation+=subcenterCount.getSystemDeactivation();
                        motherCompletion+=subcenterCount.getMotherCompletion();
                        childCompletion+=subcenterCount.getChildCompletion();
                        calledInbox+=subcenterCount.getCalledInbox();
                        joinedSubscription+=subcenterCount.getJoinedSubscription();
                    }
                    AggregateCumulativeBeneficiary nosubcenterCount = new AggregateCumulativeBeneficiary();
                    nosubcenterCount.setBeneficiariesCalled(blockCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    nosubcenterCount.setSelfDeactivated(blockCounts.getSelfDeactivated()-selfDeactivated);
                    nosubcenterCount.setNotAnswering(blockCounts.getNotAnswering()-notAnswering);
                    nosubcenterCount.setLowListenership(blockCounts.getLowListenership()-lowListenership);
                    nosubcenterCount.setSystemDeactivation(blockCounts.getSystemDeactivation()-systemDeactivation);
                    nosubcenterCount.setMotherCompletion(blockCounts.getMotherCompletion()-motherCompletion);
                    nosubcenterCount.setChildCompletion(blockCounts.getChildCompletion()-childCompletion);
                    nosubcenterCount.setCalledInbox(blockCounts.getCalledInbox()-calledInbox);
                    nosubcenterCount.setJoinedSubscription(blockCounts.getJoinedSubscription()-joinedSubscription);
                    nosubcenterCount.setLocationType("DifferenceBlock");
//                    nosubcenterCount.setId((int)(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries+blockCounts.getCalls_75_100()-calls_75_100+blockCounts.getCalls_50_75()-calls_50_75+blockCounts.getCalls_25_50()-calls_25_50+blockCounts.getCalls_1_25()-calls_1_25));
                    nosubcenterCount.setLocationId((long)(-1));
                    CumulativeBeneficiary.add(nosubcenterCount);
                }
            }
        }

        return CumulativeBeneficiary;
    };


    @Override
    public  List<AggregateBeneficiaryDto> getBeneficiaryReport(ReportRequest reportRequest, User currentUser) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reportRequest.getToDate().getTime());
        String toDateString = formatter.format(calendar.getTime());
        Date toDate = new Date();
        try {
            toDate = formatter.parse(toDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(reportRequest.getFromDate().getTime());
        String fromDateString = formatter.format(calendar.getTime());
        Date fromDate = new Date();
        try {
            fromDate = formatter.parse(fromDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, -1);
        fromDate = calendar.getTime();


        List<AggregateBeneficiaryDto> summaryDto = new ArrayList<>();
        List<AggregateCumulativeBeneficiary> cumulativeBeneficiaryReportStart = new ArrayList<>();
        List<AggregateCumulativeBeneficiary> cumulativeBeneficiaryReportEnd = new ArrayList<>();


        if (reportRequest.getStateId() == 0) {
            cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(0, "State", fromDate));
            cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(0, "State", toDate));
        } else {
            if (reportRequest.getDistrictId() == 0) {
                cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getStateId(), "District", fromDate));
                cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getStateId(), "District", toDate));
            } else {
                if (reportRequest.getBlockId() == 0) {
                    cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getDistrictId(), "Block", fromDate));
                    cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getDistrictId(), "Block", toDate));
                } else {
                    cumulativeBeneficiaryReportStart.addAll(this.getCumulativeBeneficiary(reportRequest.getBlockId(), "Subcenter", fromDate));
                    cumulativeBeneficiaryReportEnd.addAll(this.getCumulativeBeneficiary(reportRequest.getBlockId(), "Subcenter", toDate));
                }
            }


        }

        for (int i = 0; i < cumulativeBeneficiaryReportEnd.size(); i++) {
            for (int j = 0; j < cumulativeBeneficiaryReportStart.size(); j++) {
                if (cumulativeBeneficiaryReportEnd.get(i).getLocationId().equals(cumulativeBeneficiaryReportStart.get(j).getLocationId())) {
                    AggregateCumulativeBeneficiary a = cumulativeBeneficiaryReportEnd.get(i);
                    AggregateCumulativeBeneficiary b = cumulativeBeneficiaryReportStart.get(j);
                    AggregateBeneficiaryDto summaryDto1 = new AggregateBeneficiaryDto();
                    summaryDto1.setLocationId(a.getLocationId());
                    summaryDto1.setBeneficiariesCalled(a.getBeneficiariesCalled() - b.getBeneficiariesCalled());
                    summaryDto1.setSelfDeactivated(a.getSelfDeactivated() - b.getSelfDeactivated());
                    summaryDto1.setJoinedSubscription(a.getJoinedSubscription() - b.getJoinedSubscription());
                    summaryDto1.setCalledInbox(a.getCalledInbox() - b.getCalledInbox());
                    summaryDto1.setMotherCompletion(a.getMotherCompletion() - b.getMotherCompletion());
                    summaryDto1.setChildCompletion(a.getChildCompletion() - b.getChildCompletion());
                    summaryDto1.setLowListenership(a.getLowListenership() - b.getLowListenership());
                    summaryDto1.setNotAnswering(a.getNotAnswering() - b.getNotAnswering());
                    summaryDto1.setSystemDeactivation(a.getSystemDeactivation() - b.getSystemDeactivation());
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
                        summaryDto1.setLocationName("No District Count");
                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        summaryDto1.setLocationName("No Block Count");
                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        summaryDto1.setLocationName("No Subcenter Count");
                    }

                    if (a.getId() != 0) {
                        summaryDto.add(summaryDto1);
                    }
                }


            }


        }
        return summaryDto;
    }

    /*----------5.3.4. Kilkari Usage Report -------*/
    private List<KilkariUsage> getKilkariUsage(Integer locationId,String locationType,Date toDate){
        List<KilkariUsage> KilkariUsage = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                KilkariUsage.add(kilkariUsageDao.getUsage(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                KilkariUsage stateCounts = kilkariUsageDao.getUsage(locationId,"State",toDate);
                Long beneficiariesCalled = (long)0;
                Long calls_75_100 = (long)0;
                Long calls_50_75 = (long)0;
                Long calls_25_50 = (long)0;
                Long calls_1_25 = (long)0;
                Long calledInbox = (long)0;
                for(District d:districts){
                    KilkariUsage distrcitCount = kilkariUsageDao.getUsage(d.getDistrictId(),locationType,toDate);
                    KilkariUsage.add(distrcitCount);
                    beneficiariesCalled+=distrcitCount.getBeneficiariesCalled();
                    calls_75_100+=distrcitCount.getCalls_75_100();
                    calls_50_75+=distrcitCount.getCalls_50_75();
                    calls_25_50+=distrcitCount.getCalls_25_50();
                    calls_1_25+=distrcitCount.getCalls_1_25();
                    calledInbox+=distrcitCount.getCalledInbox();

                }
                KilkariUsage noDistrictCount = new KilkariUsage();
                noDistrictCount.setBeneficiariesCalled(stateCounts.getBeneficiariesCalled()-beneficiariesCalled);
                noDistrictCount.setCalls_75_100(stateCounts.getCalls_75_100()-calls_75_100);
                noDistrictCount.setCalls_50_75(stateCounts.getCalls_50_75()-calls_50_75);
                noDistrictCount.setCalls_25_50(stateCounts.getCalls_25_50()-calls_25_50);
                noDistrictCount.setCalls_1_25(stateCounts.getCalls_1_25()-calls_1_25);
                noDistrictCount.setCalledInbox(stateCounts.getCalledInbox()-calledInbox);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId((int)(noDistrictCount.getBeneficiariesCalled()+noDistrictCount.getCalledInbox()));
                noDistrictCount.setLocationId((long)(-1));
                KilkariUsage.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    KilkariUsage districtCounts = kilkariUsageDao.getUsage(locationId,"District",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Long calledInbox = (long)0;
                    for (Block d : blocks) {
                        KilkariUsage blockCount = kilkariUsageDao.getUsage(d.getBlockId(),locationType,toDate);
                        KilkariUsage.add(blockCount);
                        beneficiariesCalled+=blockCount.getBeneficiariesCalled();
                        calls_75_100+=blockCount.getCalls_75_100();
                        calls_50_75+=blockCount.getCalls_50_75();
                        calls_25_50+=blockCount.getCalls_25_50();
                        calls_1_25+=blockCount.getCalls_1_25();
                        calledInbox+=blockCount.getCalledInbox();


                    }
                    KilkariUsage noBlockCount = new KilkariUsage();
                    noBlockCount.setBeneficiariesCalled(districtCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    noBlockCount.setCalls_75_100(districtCounts.getCalls_75_100()-calls_75_100);
                    noBlockCount.setCalls_50_75(districtCounts.getCalls_50_75()-calls_50_75);
                    noBlockCount.setCalls_25_50(districtCounts.getCalls_25_50()-calls_25_50);
                    noBlockCount.setCalls_1_25(districtCounts.getCalls_1_25()-calls_1_25);
                    noBlockCount.setCalledInbox(districtCounts.getCalledInbox()-calledInbox);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId((int)(noBlockCount.getCalledInbox()+noBlockCount.getBeneficiariesCalled()));
                    noBlockCount.setLocationId((long)(-1));
                    KilkariUsage.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    KilkariUsage blockCounts = kilkariUsageDao.getUsage(locationId,"block",toDate);
                    Long beneficiariesCalled = (long)0;
                    Long calls_75_100 = (long)0;
                    Long calls_50_75 = (long)0;
                    Long calls_25_50 = (long)0;
                    Long calls_1_25 = (long)0;
                    Long calledInbox = (long)0;
                    for(Subcenter s: subcenters){
                        KilkariUsage subcenterCount = kilkariUsageDao.getUsage(s.getSubcenterId(),locationType,toDate);
                        KilkariUsage.add(subcenterCount);
                        beneficiariesCalled+=subcenterCount.getBeneficiariesCalled();
                        calls_75_100+=subcenterCount.getCalls_75_100();
                        calls_50_75+=subcenterCount.getCalls_50_75();
                        calls_25_50+=subcenterCount.getCalls_25_50();
                        calls_1_25+=subcenterCount.getCalls_1_25();
                        calledInbox+=subcenterCount.getCalledInbox();

                    }
                    KilkariUsage nosubcenterCount = new KilkariUsage();
                    nosubcenterCount.setBeneficiariesCalled(blockCounts.getBeneficiariesCalled()-beneficiariesCalled);
                    nosubcenterCount.setCalls_75_100(blockCounts.getCalls_75_100()-calls_75_100);
                    nosubcenterCount.setCalls_50_75(blockCounts.getCalls_50_75()-calls_50_75);
                    nosubcenterCount.setCalls_25_50(blockCounts.getCalls_25_50()-calls_25_50);
                    nosubcenterCount.setCalls_1_25(blockCounts.getCalls_1_25()-calls_1_25);
                    nosubcenterCount.setCalledInbox(blockCounts.getCalledInbox()-calledInbox);
                    nosubcenterCount.setLocationType("DifferenceBlock");
                    nosubcenterCount.setId((int)(nosubcenterCount.getBeneficiariesCalled()+nosubcenterCount.getCalledInbox()));
                    nosubcenterCount.setLocationId((long)(-1));
                    KilkariUsage.add(nosubcenterCount);
                }
            }
        }

        return KilkariUsage;
    };

    @Override
    public List<UsageDto> getUsageReport(ReportRequest reportRequest, User currentUser){

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reportRequest.getToDate().getTime());
        String toDateString = formatter.format(calendar.getTime());
        Date toDate = new Date();
        try {
            toDate = formatter.parse(toDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(reportRequest.getFromDate().getTime());
        String fromDateString = formatter.format(calendar.getTime());
        Date fromDate = new Date();
        try {
            fromDate = formatter.parse(fromDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, -1);
        fromDate = calendar.getTime();


        List<UsageDto> summaryDto = new ArrayList<>();
        List<KilkariUsage> kilkariUsageList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariUsageList.addAll(this.getKilkariUsage(0,"State",toDate));
        }
        else{
            if (reportRequest.getDistrictId() == 0) {
                kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getStateId(),"District",toDate));
            }
            else{
                if(reportRequest.getBlockId() == 0){
                    kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getDistrictId(),"Block",toDate));
                }
                else {
                    kilkariUsageList.addAll(this.getKilkariUsage(reportRequest.getBlockId(),"Subcenter",toDate));
                }
            }


        }

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
                summaryDto1.setLocationName("No District Count");
            }
            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                summaryDto1.setLocationName("No Block Count");
            }
            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                summaryDto1.setLocationName("No Subcenter Count");
            }

            if(a.getId()!=0){
                summaryDto.add(summaryDto1);
            }

        }

        return summaryDto;
    }

    /*----------5.3.7. Kilkari Listening Matrix Report -------*/
    @Override
    public List<ListeningMatrixDto> getListeningMatrixReport(ReportRequest reportRequest, User currentUser){

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reportRequest.getToDate().getTime());
        String toDateString = formatter.format(calendar.getTime());
        Date toDate = new Date();
        try {
            toDate = formatter.parse(toDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(reportRequest.getFromDate().getTime());
        String fromDateString = formatter.format(calendar.getTime());
        Date fromDate = new Date();
        try {
            fromDate = formatter.parse(fromDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, -1);
        fromDate = calendar.getTime();


        List<ListeningMatrixDto> matrixDto = new ArrayList<>();
        ListeningMatrix listeningMatrix = new ListeningMatrix();
        listeningMatrix = listeningMatrixDao.getListeningMatrix(toDate);

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

     /*----------5.3.11. Kilkari Call Report -------*/

    private List<KilkariCalls> getKilkariCallReport(Integer locationId,String locationType,Date toDate){
        List<KilkariCalls> kilkariCall = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("K");
            for(State s:states){
                kilkariCall.add(kilkariCallReportDao.getKilkariCallreport(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
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
                    KilkariCalls distrcitCount = kilkariCallReportDao.getKilkariCallreport(d.getDistrictId(),locationType,toDate);
                    kilkariCall.add(distrcitCount);
                    callsAttempted+=distrcitCount.getCallsAttempted();
                    successfulCalls+=distrcitCount.getSuccessfulCalls();
                    billableMinutes+=distrcitCount.getBillableMinutes();
                    callsToInbox+=distrcitCount.getCallsToInbox();
                    avgDuration+=distrcitCount.getAvgDuration();
                    content_75_100+=distrcitCount.getContent_75_100();
                    content_50_75+=distrcitCount.getContent_50_75();
                    content_25_50+=distrcitCount.getContent_25_50();
                    content_1_25+=distrcitCount.getContent_1_25();
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
               // noDistrictCount.setId((int)(stateCounts.getCompletedBeneficiaries()-completedBeneficiaries+stateCounts.getCalls_75_100()-calls_75_100+stateCounts.getCalls_50_75()-calls_50_75+stateCounts.getCalls_25_50()-calls_25_50+stateCounts.getCalls_1_25()-calls_1_25));
                noDistrictCount.setLocationId((long)(-1));
                kilkariCall.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
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
                    //noBlockCount.setId((int)(districtCounts.getCompletedBeneficiaries()-completedBeneficiaries+districtCounts.getCalls_75_100()-calls_75_100+districtCounts.getCalls_50_75()-calls_50_75+districtCounts.getCalls_25_50()-calls_25_50+districtCounts.getCalls_1_25()-calls_1_25));
                    noBlockCount.setLocationId((long)(-1));
                    kilkariCall.add(noBlockCount);
                }
                else {
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
                        KilkariCalls subcenterCount = kilkariCallReportDao.getKilkariCallreport(s.getSubcenterId(),locationType,toDate);
                        kilkariCall.add(subcenterCount);
                        callsAttempted+=subcenterCount.getCallsAttempted();
                        successfulCalls+=subcenterCount.getSuccessfulCalls();
                        billableMinutes+=subcenterCount.getBillableMinutes();
                        callsToInbox+=subcenterCount.getCallsToInbox();
                        avgDuration+=subcenterCount.getAvgDuration();
                        content_75_100+=subcenterCount.getContent_75_100();
                        content_50_75+=subcenterCount.getContent_50_75();
                        content_25_50+=subcenterCount.getContent_25_50();
                        content_1_25+=subcenterCount.getContent_1_25();
                    }
                    KilkariCalls nosubcenterCount = new KilkariCalls();
                    nosubcenterCount.setCallsAttempted(blockCounts.getCallsAttempted()-callsAttempted);
                    nosubcenterCount.setSuccessfulCalls(blockCounts.getSuccessfulCalls()-successfulCalls);
                    nosubcenterCount.setBillableMinutes(blockCounts.getBillableMinutes()-billableMinutes);
                    nosubcenterCount.setCallsToInbox(blockCounts.getCallsToInbox()-callsToInbox);
                    nosubcenterCount.setAvgDuration(blockCounts.getAvgDuration()-avgDuration);
                    nosubcenterCount.setContent_1_25(blockCounts.getContent_1_25()-content_1_25);
                    nosubcenterCount.setContent_25_50(blockCounts.getContent_25_50()-content_25_50);
                    nosubcenterCount.setContent_50_75(blockCounts.getContent_50_75()-content_50_75);
                    nosubcenterCount.setContent_75_100(blockCounts.getContent_75_100()-content_75_100);
                    nosubcenterCount.setLocationType("DifferenceBlock");
                    //nosubcenterCount.setId((int)(blockCounts.getCompletedBeneficiaries()-completedBeneficiaries+blockCounts.getCalls_75_100()-calls_75_100+blockCounts.getCalls_50_75()-calls_50_75+blockCounts.getCalls_25_50()-calls_25_50+blockCounts.getCalls_1_25()-calls_1_25));
                    nosubcenterCount.setLocationId((long)(-1));
                    kilkariCall.add(nosubcenterCount);
                }
            }
        }

        return kilkariCall;
    };


    @Override
    public List<KilkariCallReportDto> getKilkariCallReport(ReportRequest reportRequest, User currentUser){

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reportRequest.getToDate().getTime());
        String toDateString = formatter.format(calendar.getTime());
        Date toDate = new Date();
        try {
            toDate = formatter.parse(toDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(reportRequest.getFromDate().getTime());
        String fromDateString = formatter.format(calendar.getTime());
        Date fromDate = new Date();
        try {
            fromDate = formatter.parse(fromDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, -1);
        fromDate = calendar.getTime();


        List<KilkariCallReportDto> callReportDtos = new ArrayList<>();
        List<KilkariCalls> kilkariCallList = new ArrayList<>();

        if (reportRequest.getStateId() == 0) {
            kilkariCallList.addAll(this.getKilkariCallReport(0,"State",toDate));
        }
        else{
            if (reportRequest.getDistrictId() == 0) {
                kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getStateId(),"District",toDate));
            }
            else{
                if(reportRequest.getBlockId() == 0){
                    kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getDistrictId(),"Block",toDate));
                }
                else {
                    kilkariCallList.addAll(this.getKilkariCallReport(reportRequest.getBlockId(),"Subcenter",toDate));
                }
            }


        }

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
                summaryDto1.setLocationName("No District Count");
            }
            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                summaryDto1.setLocationName("No Block Count");
            }
            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                summaryDto1.setLocationName("No Subcenter Count");
            }

            if(a.getId()!=0){
                callReportDtos.add(summaryDto1);
            }

        }
        return callReportDtos;
    }



}

