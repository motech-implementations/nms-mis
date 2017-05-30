//package com.beehyv.nmsreporting.dao.impl;
//
//import com.beehyv.nmsreporting.dao.AbstractDao;
//import com.beehyv.nmsreporting.dao.LocationDao;
//import com.beehyv.nmsreporting.model.Location;
//import org.hibernate.Criteria;
//import org.hibernate.Query;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
///**
// * Created by beehyv on 15/3/17.
// */
//@Repository("locationDao")
//public class LocationDaoImpl extends AbstractDao<Integer, Location> implements LocationDao {
//    public Location findByLocationId(Integer locationId) {
//        return getByKey(locationId);
//    }
//
//    @Override
//    public List<Location> findByLocation(String locationName) {
//        Criteria criteria = createEntityCriteria();
//        criteria.add(Restrictions.eq("location", locationName).ignoreCase());
//        return (List<Location>) criteria.list();
//    }
//
//
//    public List<Location> getAllLocations() {
//        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
//        return (List<Location>) criteria.list();
//    }
//
//    public List<Location> getAllSubLocations(Location location) {
//        List<Location> subLocations = new ArrayList<>();
//        subLocations=getLocations(location,subLocations);
//        Collections.sort(subLocations, new Comparator<Location>() {
//            @Override
//            public int compare(Location o1, Location o2) {
//                return o1.getLocationId() - o2.getLocationId();
//            }
//        });
//        return subLocations;
//    }
//
//    private List<Location> getLocations(Location location, List<Location> subLocations) {
//        subLocations.add(location);
//        for(Location l: location.getSubLocations()) {
//            getLocations(l,subLocations);
//        }
//        return subLocations;
//    }
//
//    public List<Location> getChildLocations(int locationId){
////        Criteria criteria = createEntityCriteria();
////        criteria.add(Restrictions.eq("referenceId", locationId));
////        return criteria.list();
//        Query query = getSession().createQuery("from Location where referenceId= :id");
//        query.setLong("id", locationId);
//        return query.list();
//    }
//
//    public void saveLocation(Location location) {
//        persist(location);
//    }
//
//    public void deleteLocation(Location location) {
//        delete(location);
//    }
//}
