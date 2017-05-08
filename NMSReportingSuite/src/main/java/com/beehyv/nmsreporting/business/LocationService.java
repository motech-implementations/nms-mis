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

    List<District> getChildDistricts(State state);

//    public void createNewState(State state);
//
//    public void updateExistingState(State state);
//
//    public void deleteExistingState(State state);

    /*----------------------District-------------------------*/

    District findDistrictById(Integer districtId);

    District findDistrictByName(String districtName);

    List<Block> getChildBlocks(District district);

//    public void createNewDistrict(District district);
//
//    public void updateExistingDistrict(District district);
//
//    public void deleteExistingDistrict(District district);

    /*----------------------Taluka-------------------------*/

    Taluka findTalukaById(Integer talukaId);

//    public void createNewTaluka(Taluka taluka);
//
//    public void updateExistingTaluka(Taluka taluka);
//
//    public void deleteExistingTaluka(Taluka taluka);

    /*----------------------Block-------------------------*/

    Block findBlockById(Integer blockId);

    Block findBlockByName(String blockName);

//    public void createNewBlock(Block block);
//
//    public void updateExistingBlock(Block block);
//
//    public void deleteExistingBlock(Block block);
}
