package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggCumulativeBeneficiaryComplDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiaryCompletion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 6/10/17.
 */
@Repository("aggCumulativeBeneficiaryComplDao")
public class AggCumulativeBeneficiaryComplDaoImpl extends AbstractDao<Integer,AggregateCumulativeBeneficiaryCompletion> implements AggCumulativeBeneficiaryComplDao
    {
        public AggregateCumulativeBeneficiaryCompletion getBeneficiaryCompletion(Integer locationId,String locationType,Date date){
            Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
            criteria.add(Restrictions.and(
                    Restrictions.eq("locationId",locationId.longValue()),
                    Restrictions.eq("locationType",locationType),
                    Restrictions.eq("date",date)
            ));
            if(criteria.list().isEmpty()){
                Long a = (long)0;
                AggregateCumulativeBeneficiaryCompletion aggregateCumulativeBeneficiaryCompletion = new AggregateCumulativeBeneficiaryCompletion(0,locationType,locationId.longValue(),date,a,a,a,a,a);
                return aggregateCumulativeBeneficiaryCompletion;
            }
            return (AggregateCumulativeBeneficiaryCompletion) criteria.list().get(0);

        };





    }
