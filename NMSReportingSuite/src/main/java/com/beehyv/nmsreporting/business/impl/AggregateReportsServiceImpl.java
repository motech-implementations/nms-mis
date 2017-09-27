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
            List<State> states=stateDao.getAllStates();
            for(State s:states){
                CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                for(District d:districts){
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,toDate));
                }
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    for (Block d : blocks) {
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType,toDate));
                    }
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    for(Subcenter s: subcenters){
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getSubcenterId(), locationType,toDate));
                    }

                }
            }
        }

        return CumulativeSummery;
    };
}
