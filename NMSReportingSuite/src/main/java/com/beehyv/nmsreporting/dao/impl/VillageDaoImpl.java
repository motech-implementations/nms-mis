package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.VillageDao;
import com.beehyv.nmsreporting.model.Village;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("villageDao")
public class VillageDaoImpl extends AbstractDao<Integer, Village> implements VillageDao {
    @Override
    public Village findByVillageId(Integer villageId) {
        return getByKey(villageId);
    }

    @Override
    public List<Village> findByVillageIds(Set<Integer> villageIds){
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("villageId",villageIds));
        return (List<Village>) criteria.list();
    }
}
