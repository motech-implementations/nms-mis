package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.StateServiceDao;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.StateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 6/6/17.
 */
@Repository("stateServiceDao")
public class StateServiceDaoImpl extends AbstractDao<Integer, StateService> implements StateServiceDao {

    @Override
    public List<State> getStatesByServiceType(String type) {
        Criteria criteria = getSession().createCriteria(StateService.class);


        criteria.add(
                Restrictions.eq("serviceType", type).ignoreCase());


        List<StateService> states=(List<StateService>) criteria.list();
        List<State> statesForService=new ArrayList<>();



        for (StateService stateService:states){
            Criteria criteria1=getSession().createCriteria(State.class);
            criteria1.add(Restrictions.eq("stateId", stateService.getStateId()));
            State temporary=(State) criteria1.list().get(0);
            statesForService.add(temporary);
        }

        return  statesForService;

    }

    @Override
    public Date getServiceStartDateForState(Integer stateId, String type) {
        Criteria criteria=getSession().createCriteria(StateService.class);
        criteria.add(Restrictions.and(Restrictions.eq("stateId",stateId),Restrictions.eq("serviceType",type)));
        StateService stateService= (StateService) criteria.list().get(0);
        return stateService.getServiceStartDate();
    }
}
