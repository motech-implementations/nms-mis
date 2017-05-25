package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private TalukaDao talukaDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private CircleDao circleDao;

    public Location findLocationById(Integer locationId) {
        return locationDao.findByLocationId(locationId);
    }

    public Location findLocationByName(String locationName){
        return locationDao.findByLocation(locationName).get(0);
    }

    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public List<Location> getAllSubLocations(Integer locationId) {
        Location location = locationDao.findByLocationId(locationId);

        return locationDao.getAllSubLocations(location);
    }

    public List<Location> getChildLocations(int locationId){
        return locationDao.getChildLocations(locationId);
    }

//    public void createNewLocation(Location location) {
//        locationDao.saveLocation(location);
//    }
//
//    public void updateExistingLocation(Location location) {
//        Location entity = locationDao.findByLocationId(location.getLocationId());
//        if(entity != null) {
//            entity.setLocation(location.getLocation());
//            entity.setReferenceId(location.getReferenceId());
//        }
//    }
//
//    public void deleteExistingLocation(Location location) {
//        locationDao.deleteLocation(location);
//    }
    /*----------------------General----------------------*/

    @Override
    public List<State> getAllStates() {
        return stateDao.getAllStates();
    }

    /*----------------------State-------------------------*/

    @Override
    public State findStateById(Integer stateId) {
        return stateDao.findByStateId(stateId);
    }

    @Override
    public State findStateByName(String stateName) {
        return stateDao.findByName(stateName).get(0);
    }

    @Override
    public List<District> getChildDistricts(Integer stateId) {
        return districtDao.getDistrictsOfState(stateDao.findByStateId(stateId));
    }

    /*----------------------District-------------------------*/

    @Override
    public District findDistrictById(Integer districtId) {
        return districtDao.findByDistrictId(districtId);
    }

    @Override
    public District findDistrictByName(String districtName) {
        return districtDao.findByName(districtName).get(0);
    }

    @Override
    public List<Block> getChildBlocks(Integer districtId) {
        return blockDao.getBlocksOfDistrict(districtDao.findByDistrictId(districtId));
    }

    @Override
    public State getStateOfDistrict(Integer districtId) {
        return districtDao.getStateOfDistrict(districtDao.findByDistrictId(districtId));
    }

    /*----------------------Taluka-------------------------*/

    @Override
    public Taluka findTalukaById(Integer talukaId) {
        return talukaDao.findByTalukaId(talukaId);
    }

    /*----------------------Block-------------------------*/

    @Override
    public Block findBlockById(Integer blockId) {
        return blockDao.findByblockId(blockId);
    }

    @Override
    public Block findBlockByName(String blockName) {
        return blockDao.findByName(blockName).get(0);
    }

    @Override
    public District getDistrictOfBlock(Integer blockId) {
        return blockDao.getDistrictOfBlock(blockDao.findByblockId(blockId));
    }

    @Override
    public Circle findCircleById(Integer circleId) {
        return circleDao.getByCircleId(circleId);
    }
}
