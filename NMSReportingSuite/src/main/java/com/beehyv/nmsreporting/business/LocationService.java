package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Location;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface LocationService {
    public Location findLocationById(Integer locationId);

    public List<Location> getAllLocations();

    public List<Location> getAllSubLocations(Integer locationId);

    public List<Location> getChildLocations(int locationId);

    public void createNewLocation(Location location);

    public void updateExistingLocation(Location location);

    public void deleteExistingLocation(Location location);
}
