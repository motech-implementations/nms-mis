package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.model.Location;
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

    @RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
    public @ResponseBody List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @RequestMapping(value = {"/list/{locationId}"}, method = RequestMethod.GET)
    public @ResponseBody List<Location> getLocationsByLocationId(@PathVariable("locationId") Integer locationId) {
        return locationService.getAllSubLocations(locationId);
    }
}
