package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Village;

/**
 * Created by beehyv on 23/5/17.
 */
public interface VillageDao {

    public Village findByVillageId(Integer villageId);
}
