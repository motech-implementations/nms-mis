package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeBeneficiaryDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeBeneficiaryWeekDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiaryWeek;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("aggregateCumulativeBeneficiaryWeekDao")
public class AggregateCumulativeBeneficiaryWeekDaoImpl extends AbstractDao<Integer,AggregateCumulativeBeneficiaryWeek> implements AggregateCumulativeBeneficiaryWeekDao {

    @Override
    public AggregateCumulativeBeneficiary getCumulativeBeneficiary(Long locationId, String locationType, Date toDate){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));
        List<AggregateCumulativeBeneficiaryWeek> result = criteria.list();
        if(result.size() < 1){
            Long a = (long)0;
            AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = new AggregateCumulativeBeneficiary(0,locationType,locationId,toDate,a,a,a,a,a,a,a);
            return aggregateCumulativeBeneficiary;
        }
        AggregateCumulativeBeneficiaryWeek aggregateCumulativeBeneficiaryWeek = result.get(0);
        AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = new AggregateCumulativeBeneficiary();

        aggregateCumulativeBeneficiary.setId(aggregateCumulativeBeneficiaryWeek.getId());
        aggregateCumulativeBeneficiary.setLocationId(aggregateCumulativeBeneficiaryWeek.getLocationId());
        aggregateCumulativeBeneficiary.setLocationType(aggregateCumulativeBeneficiaryWeek.getLocationType());
        aggregateCumulativeBeneficiary.setDate(aggregateCumulativeBeneficiaryWeek.getDate());
        aggregateCumulativeBeneficiary.setJoinedSubscription(aggregateCumulativeBeneficiaryWeek.getJoinedSubscription() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getJoinedSubscription());
        aggregateCumulativeBeneficiary.setChildCompletion(aggregateCumulativeBeneficiaryWeek.getChildCompletion() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getChildCompletion());
        aggregateCumulativeBeneficiary.setMotherCompletion(aggregateCumulativeBeneficiaryWeek.getMotherCompletion() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getMotherCompletion());
        aggregateCumulativeBeneficiary.setLowListenership(aggregateCumulativeBeneficiaryWeek.getLowListenership() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getLowListenership());
        aggregateCumulativeBeneficiary.setSelfDeactivated(aggregateCumulativeBeneficiaryWeek.getSelfDeactivated() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getSelfDeactivated());
        aggregateCumulativeBeneficiary.setNotAnswering(aggregateCumulativeBeneficiaryWeek.getNotAnswering() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getNotAnswering());
        aggregateCumulativeBeneficiary.setSystemDeactivation(aggregateCumulativeBeneficiaryWeek.getSystemDeactivation() == null ? 0 : aggregateCumulativeBeneficiaryWeek.getSystemDeactivation());
        return aggregateCumulativeBeneficiary;
    }


}
