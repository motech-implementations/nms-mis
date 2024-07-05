package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Village;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 23/5/17.
 */
public interface VillageDao {

    public Village findByVillageId(Integer villageId);

    public List<Village> findByVillageIds(Set<Integer> villageIds);

}
