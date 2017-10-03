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
                noDistrictCount.setLocationId((long)(-1));
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
                    noBlockCount.setLocationId((long)(-1));
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
                    nosubcenterCount.setLocationId((long)(-1));
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
            List<State> states=stateDao.getStatesByServiceType("M");
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
}
