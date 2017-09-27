package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MASubscriberDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.AggregateDailyMA;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 20/9/17.
 */
@Repository("maSubscriberDao")
public class MASubscriberDaoImpl extends AbstractDao<Integer, User>  implements MASubscriberDao {
    @Override
    public List<Object> getMASubscriberCountDaily(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(AggregateDailyMA.class);

        return null;
    }

    @Override
    public List<AggregateCumulativeMA> getMASubscriberCountSummary(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(AggregateCumulativeMA.class);

        if(locationId == 0){
            criteria.add(Restrictions.and(
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate)),
                    Restrictions.eq("locationType",locationType)
            ));
            return (List<AggregateCumulativeMA>) criteria.list();
        }
        else{
            criteria.add(Restrictions.and(
                    Restrictions.eq("locationId",locationId.longValue()),
                    Restrictions.eq("locationType",locationType),
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate))
            ));
            return (List<AggregateCumulativeMA>) criteria.list();
        }
    }

//    @Override
//    public List<AggregateCumulativeMA> getMASubscriberCountSummaryEnd(String locationType, Integer locationId, Date fromDate, Date toDate){
//
//        Criteria criteria = getSession().createCriteria(AggregateCumulativeMA.class);
//
//        if(locationId == 0){
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("date",toDate),
//                    Restrictions.eq("locationType",locationType)
//            ));
//            return (List<AggregateCumulativeMA>) criteria.list();
//        }
//        else{
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("locationId",locationId.longValue()),
//                    Restrictions.eq("locationType",locationType),
//                    Restrictions.eq("date",toDate)
//            ));
//            return (List<AggregateCumulativeMA>) criteria.list();
//        }
//
//    }
}
