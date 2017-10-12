package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.BreadCrumbService;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.entity.BreadCrumbDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beehyv on 29/9/17.
 */
@Service("breadCrumbService")
@Transactional
public class BraedCrumbServiceImpl implements BreadCrumbService {

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;


    public List<BreadCrumbDto> getBreadCrumbs(User currentUser, ReportRequest reportRequest){
        List<BreadCrumbDto> breadCrumbDtos = new ArrayList<>();
        if(currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())){

            BreadCrumbDto bread1=new BreadCrumbDto("NATIONAL","NATIONAL",0);
            breadCrumbDtos.add(bread1);
            if(reportRequest.getStateId() != 0){
                BreadCrumbDto bread2 = new BreadCrumbDto("State",stateDao.findByStateId(reportRequest.getStateId()).getStateName(),reportRequest.getStateId());
                breadCrumbDtos.add(bread2);
            }
            if(reportRequest.getDistrictId() != 0){
                BreadCrumbDto bread3 = new BreadCrumbDto("District",districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName(),reportRequest.getDistrictId());
                breadCrumbDtos.add(bread3) ;
            }
            if(reportRequest.getBlockId()!=0){
                BreadCrumbDto bread4 = new BreadCrumbDto("Block",blockDao.findByblockId(reportRequest.getBlockId()).getBlockName(),reportRequest.getBlockId());
                breadCrumbDtos.add(bread4);
            }
        }
        if(currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())){
            if(reportRequest.getStateId() != 0){
                BreadCrumbDto bread2 = new BreadCrumbDto("State",stateDao.findByStateId(reportRequest.getStateId()).getStateName(),reportRequest.getStateId());
                breadCrumbDtos.add(bread2);
            }
            if(reportRequest.getDistrictId() != 0){
                BreadCrumbDto bread3 = new BreadCrumbDto("District",districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName(),reportRequest.getDistrictId());
                breadCrumbDtos.add(bread3) ;
            }
            if(reportRequest.getBlockId()!=0){
                BreadCrumbDto bread4 = new BreadCrumbDto("Block",blockDao.findByblockId(reportRequest.getBlockId()).getBlockName(),reportRequest.getBlockId());
                breadCrumbDtos.add(bread4);
            }
        }
        if(currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel())){

            if(reportRequest.getDistrictId() != 0){
                BreadCrumbDto bread3 = new BreadCrumbDto("District",districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName(),reportRequest.getDistrictId());
                breadCrumbDtos.add(bread3) ;
            }
            if(reportRequest.getBlockId()!=0){
                BreadCrumbDto bread4 = new BreadCrumbDto("Block",blockDao.findByblockId(reportRequest.getBlockId()).getBlockName(),reportRequest.getBlockId());
                breadCrumbDtos.add(bread4);
            }
        }
        if(currentUser.getAccessLevel().equals(AccessLevel.BLOCK.getAccessLevel())){
            if(reportRequest.getBlockId()!=0){
                BreadCrumbDto bread4 = new BreadCrumbDto("Block",blockDao.findByblockId(reportRequest.getBlockId()).getBlockName(),reportRequest.getBlockId());
                breadCrumbDtos.add(bread4);
            }
        }

        return breadCrumbDtos;
    }
}
