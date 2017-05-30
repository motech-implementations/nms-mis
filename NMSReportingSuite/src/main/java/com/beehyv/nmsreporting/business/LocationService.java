package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.*;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface LocationService {
    Location findLocationById(Integer locationId);

    Location findLocationByName(String locationName);

    List<Location> getAllLocations();

    List<Location> getAllSubLocations(Integer locationId);

    List<Location> getChildLocations(int locationId);

//    public void createNewLocation(Location location);
//
//    public void updateExistingLocation(Location location);
//
//    public void deleteExistingLocation(Location location);

    /*----------------------General-----------------------*/

    List<State> getAllStates();

    /*----------------------State-------------------------*/

    State findStateById(Integer stateId);

    State findStateByName(String stateName);

    List<District> getChildDistricts(Integer stateId);

    /*----------------------District-------------------------*/

    District findDistrictById(Integer districtId);

    District findDistrictByName(String districtName);

    List<Block> getChildBlocks(Integer districtId);

    State getStateOfDistrict(Integer districtId);

    /*----------------------Taluka-------------------------*/

    Taluka findTalukaById(Integer talukaId);

    /*----------------------Block-------------------------*/

    Block findBlockById(Integer blockId);

    Block findBlockByName(String blockName);

    District getDistrictOfBlock(Integer blockId);

     /*----------------------Circle-------------------------*/
    Circle findCircleById(Integer circleId);

    List<Circle> getAllCirles();
}
