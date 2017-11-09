package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Subcenter;

import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface SubcenterDao {

    public Subcenter findBySubcenterId(Integer SubcenterId);

    Subcenter findByLocationId(Integer stateId);

    public List<Subcenter> findByName(String SubcenterName);

    public List<Subcenter> getSubcentersOfBlock(Integer blockId);

    public Integer getBlockOfSubcenter(Subcenter Subcenter);
}
