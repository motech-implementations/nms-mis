package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.*;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface LocationService {
    public Location findLocationById(Integer locationId);

    public Location findLocationByName(String locationName);

    public List<Location> getAllLocations();

    public List<Location> getAllSubLocations(Integer locationId);

    public List<Location> getChildLocations(int locationId);

//    public void createNewLocation(Location location);
//
//    public void updateExistingLocation(Location location);
//
//    public void deleteExistingLocation(Location location);

    /*----------------------State-------------------------*/

    public State findStateById(Integer stateId);

    public State findStateByName(String stateName);

    public List<District> getChildDistricts(State state);

//    public void createNewState(State state);
//
//    public void updateExistingState(State state);
//
//    public void deleteExistingState(State state);

    /*----------------------District-------------------------*/

    public District findDistrictById(Integer districtId);

    public District findDistrictByName(String districtName);

    public List<Block> getChildBlocks(District district);

//    public void createNewDistrict(District district);
//
//    public void updateExistingDistrict(District district);
//
//    public void deleteExistingDistrict(District district);

    /*----------------------Taluka-------------------------*/

    public Taluka findTalukaById(Integer talukaId);

//    public void createNewTaluka(Taluka taluka);
//
//    public void updateExistingTaluka(Taluka taluka);
//
//    public void deleteExistingTaluka(Taluka taluka);

    /*----------------------Block-------------------------*/

    public Block findBlockById(Integer blockId);

    public Block findBlockByName(String blockName);

//    public void createNewBlock(Block block);
//
//    public void updateExistingBlock(Block block);
//
//    public void deleteExistingBlock(Block block);
}
