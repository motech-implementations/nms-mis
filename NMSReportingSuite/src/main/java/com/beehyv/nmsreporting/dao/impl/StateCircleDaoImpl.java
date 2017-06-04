package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.StateCircleDao;
import com.beehyv.nmsreporting.model.StateCircle;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
@Repository("stateCircleDao")
public class StateCircleDaoImpl extends AbstractDao<Integer,StateCircle> implements StateCircleDao{

    @Override
    public List<StateCircle> getCirclesByState(Integer stateId) {
        Criteria criteria = createEntityCriteria();
        if(stateId!=null)
        criteria.add(Restrictions.eq("stateId", stateId));
        return criteria.list();
    }
}
