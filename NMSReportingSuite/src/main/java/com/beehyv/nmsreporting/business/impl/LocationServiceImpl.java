package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.dao.LocationDao;
import com.beehyv.nmsreporting.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationDao locationDao;

    public Location findLocationById(Integer locationId) {
        return locationDao.findByLocationId(locationId);
    }

    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public List<Location> getAllSubLocations(Integer locationId) {
        Location location = locationDao.findByLocationId(locationId);

        return locationDao.getAllSubLocations(location);
    }

    public void createNewLocation(Location location) {
        locationDao.saveLocation(location);
    }

    public void updateExistingLocation(Location location) {
        Location entity = locationDao.findByLocationId(location.getLocationId());
        if(entity != null) {
            entity.setLocation(location.getLocation());
            entity.setReferenceId(location.getReferenceId());
        }
    }

    public void deleteExistingLocation(Location location) {
        locationDao.deleteLocation(location);
    }
}
