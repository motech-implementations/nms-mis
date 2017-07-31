package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.CircleDto;
import com.beehyv.nmsreporting.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface LocationService {

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

    List<State> getStatesByServiceType(String serviceType);

    Date getServiceStartdateForState(Integer stateId,String serviceType);

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

    List<State> getStatesOfCircle(Circle circle);

    List<CircleDto> getCircleObjectList(User user, String serviceType);


    public User SetLocations(User user);
}
