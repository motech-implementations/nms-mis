package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ModificationTrackerDao;
import com.beehyv.nmsreporting.model.ModificationTracker;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("modificationTrackerDao")
public class ModificationTrackerDaoImpl extends AbstractDao<Integer, ModificationTracker> implements ModificationTrackerDao {
    public ModificationTracker findModificationById(Integer modificationId) {
        return getByKey(modificationId);
    }

    public void saveModification(ModificationTracker modification) {
        persist(modification);
    }

    public void deleteModification(ModificationTracker modification) {
        delete(modification);
    }

    public List<ModificationTracker> getAllModifications() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("modificationId"));
        return (List<ModificationTracker>) criteria.list();
    }

    public List<ModificationTracker> getAllModificationsByUser(User userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modifiedByUserId", userId));
        return (List<ModificationTracker>) criteria.list();
    }

    public List<ModificationTracker> getAllModifiersForUser(User userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modifiedUserId", userId));
        return (List<ModificationTracker>) criteria.list();
    }

    public List<ModificationTracker> getAllModificationsByDate(Date date) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modificationDate", date));
        return (List<ModificationTracker>) criteria.list();
    }
}
