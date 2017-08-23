package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;

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


    /*----------------------State-------------------------*/

    State findStateById(Integer stateId);

    /*----------------------District-------------------------*/

    District findDistrictById(Integer districtId);

    /*----------------------Taluka-------------------------*/


    /*----------------------Block-------------------------*/

    Block findBlockById(Integer blockId);

     /*----------------------Circle-------------------------*/

    List<State> getStatesOfCircle(Circle circle);
}
