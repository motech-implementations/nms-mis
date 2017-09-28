package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AggregateReportsService;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.ContactInfo;
import com.beehyv.nmsreporting.entity.ForgotPasswordDto;
import com.beehyv.nmsreporting.entity.PasswordDto;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.enums.ModificationType;
import com.beehyv.nmsreporting.model.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


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
}
