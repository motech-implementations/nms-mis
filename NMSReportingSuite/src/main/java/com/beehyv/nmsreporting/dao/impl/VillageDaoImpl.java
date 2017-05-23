package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.VillageDao;
import com.beehyv.nmsreporting.model.Village;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("villageDao")
public class VillageDaoImpl extends AbstractDao<Integer, Village> implements VillageDao {
    @Override
    public Village findByVillageId(Integer villageId) {
        return getByKey(villageId);
    }
}
