package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 22/3/17.
 */
@Controller
@RequestMapping(value = {"/nms/location"})
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    /*--------------------------State-----------------------------*/

    @RequestMapping(value = {"/states"}, method = RequestMethod.GET)
    public @ResponseBody List<State> getAllStates() {
        User user = userService.getCurrentUser();
        List<State> states;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            states = locationService.getAllStates();
        }
        else{
            states = new ArrayList<>();
            states.add(locationService.findStateById(user.getStateId()));
        }
        return states;
    }

    @RequestMapping(value = {"/state/{serviceType}"}, method = RequestMethod.GET)
    public @ResponseBody List<State> getStatesByServiceType(@PathVariable("serviceType") String serviceType) {
        User user = userService.getCurrentUser();
        List<State> states;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            states = locationService.getStatesByServiceType(serviceType);
        }
        else{
            states = new ArrayList<>();
            if(locationService.findStateById(user.getStateId()).getServiceType().equals(serviceType)) {
                states.add(locationService.findStateById(user.getStateId()));
            }
        }
        return states;
    }

    /*--------------------------District-----------------------------*/

    @RequestMapping(value = {"/districts/{stateId}"}, method = RequestMethod.GET)
    public @ResponseBody List<District> getDistrictsOfState(@PathVariable("stateId") Integer stateId) {
        User user = userService.getCurrentUser();
        List<District> districts;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            districts = locationService.getChildDistricts(stateId);
        }
        else if(user.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            districts = locationService.getChildDistricts(user.getStateId());
        }
        else{
            districts = new ArrayList<>();
            districts.add(locationService.findDistrictById(user.getDistrictId()));
        }
        return districts;
    }

    @RequestMapping(value = {"/SoD/{districtId}"}, method = RequestMethod.GET)
    public @ResponseBody State getStateOfDistrict(@PathVariable("districtId") Integer districtId) {
        return locationService.getStateOfDistrict(districtId);
    }

    /*--------------------------Block-----------------------------*/

    @RequestMapping(value = {"/blocks/{districtId}"}, method = RequestMethod.GET)
    public @ResponseBody List<Block> getBlocksOfDistrict(@PathVariable("districtId") Integer districtId) {
        User user = userService.getCurrentUser();
        List<Block> blocks;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            blocks = locationService.getChildBlocks(districtId);
        }
        else if(user.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            if(locationService.findDistrictById(districtId).getStateOfDistrict() == user.getStateId()){
                blocks = locationService.getChildBlocks(districtId);
            }
            else{
                blocks = new ArrayList<>();
            }
        }
        else{
            blocks = locationService.getChildBlocks(user.getDistrictId());
        }

        return blocks;
    }

    @RequestMapping(value = {"/DoB/{blockId}"}, method = RequestMethod.GET)
    public @ResponseBody District getDistrictOfBlock(@PathVariable("blockId") Integer blockId) {
        return locationService.getDistrictOfBlock(blockId);
    }

    /*--------------------------Circle-----------------------------*/

    @RequestMapping(value = {"/circles"}, method = RequestMethod.GET)
    @ResponseBody List<Circle> getCircles() {
        User currentUser = userService.getCurrentUser();
        return reportService.getUserCircles(currentUser);
    }

    /*--------------------------Extra-----------------------------*/
}
