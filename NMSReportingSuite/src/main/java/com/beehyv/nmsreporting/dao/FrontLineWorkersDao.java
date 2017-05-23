package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FrontLineWorkers;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface FrontLineWorkersDao {
    List<FrontLineWorkers> getInactiveFrontLineWorkers();
}
