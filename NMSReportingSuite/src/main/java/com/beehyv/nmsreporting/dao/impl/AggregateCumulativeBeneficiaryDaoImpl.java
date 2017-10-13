package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeBeneficiaryDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 9/10/17.
 */
@Repository("aggregateCumulativeBeneficiaryDao")
public class AggregateCumulativeBeneficiaryDaoImpl extends AbstractDao<Integer,AggregateCumulativeBeneficiary> implements AggregateCumulativeBeneficiaryDao {

    @Override
    public AggregateCumulativeBeneficiary getCumulativeBeneficiary(Integer locationId, String locationType, Date toDate){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));
        if(criteria.list().isEmpty()){
            Long a = (long)0;
            AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = new AggregateCumulativeBeneficiary(0,locationType,locationId.longValue(),toDate,a,a,a,a,a,a,a,a,a);
            return aggregateCumulativeBeneficiary;
        }
        AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = (AggregateCumulativeBeneficiary)criteria.list().get(0);
        aggregateCumulativeBeneficiary.setJoinedSubscription(aggregateCumulativeBeneficiary.getJoinedSubscription()==null?0:aggregateCumulativeBeneficiary.getJoinedSubscription());
        aggregateCumulativeBeneficiary.setCalledInbox(aggregateCumulativeBeneficiary.getCalledInbox()==null?0:aggregateCumulativeBeneficiary.getCalledInbox());
        aggregateCumulativeBeneficiary.setChildCompletion(aggregateCumulativeBeneficiary.getChildCompletion()==null?0:aggregateCumulativeBeneficiary.getChildCompletion());
        aggregateCumulativeBeneficiary.setMotherCompletion(aggregateCumulativeBeneficiary.getMotherCompletion()==null?0:aggregateCumulativeBeneficiary.getMotherCompletion());
        aggregateCumulativeBeneficiary.setBeneficiariesCalled(aggregateCumulativeBeneficiary.getBeneficiariesCalled()==null?0:aggregateCumulativeBeneficiary.getBeneficiariesCalled());
        aggregateCumulativeBeneficiary.setLowListenership(aggregateCumulativeBeneficiary.getLowListenership()==null?0:aggregateCumulativeBeneficiary.getLowListenership());
        aggregateCumulativeBeneficiary.setSelfDeactivated(aggregateCumulativeBeneficiary.getSelfDeactivated()==null?0:aggregateCumulativeBeneficiary.getSelfDeactivated());
        aggregateCumulativeBeneficiary.setNotAnswering(aggregateCumulativeBeneficiary.getNotAnswering()==null?0:aggregateCumulativeBeneficiary.getNotAnswering());
        aggregateCumulativeBeneficiary.setSystemDeactivation(aggregateCumulativeBeneficiary.getSystemDeactivation()==null?0:aggregateCumulativeBeneficiary.getSystemDeactivation());

        return aggregateCumulativeBeneficiary;

    };


}
