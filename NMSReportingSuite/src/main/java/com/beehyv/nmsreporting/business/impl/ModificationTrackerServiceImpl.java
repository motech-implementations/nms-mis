package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.dao.ModificationTrackerDao;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.model.ModificationTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("modificationTrackerService")
@Transactional
public class ModificationTrackerServiceImpl implements ModificationTrackerService {
    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private UserDao userDao;

    public ModificationTracker findModificationById(Integer modificationId) {
        return modificationTrackerDao.findModificationById(modificationId);
    }

    public void saveModification(ModificationTracker modification) {
        modificationTrackerDao.saveModification(modification);
    }

    public void deleteModification(ModificationTracker modification) {
        modificationTrackerDao.deleteModification(modification);
    }

    public List<ModificationTracker> getAllModifications() {
        return modificationTrackerDao.getAllModifications();
    }

    public List<ModificationTracker> getAllModificationsMadeByUser(Integer userId) {
        return modificationTrackerDao.getAllModificationsByUser(userDao.findByUserId(userId));
    }

    public List<ModificationTracker> getAllModificationsByDate(Date date) {
        return modificationTrackerDao.getAllModificationsByDate(date);
    }

    public List<ModificationTracker> getAllModifiersForUser(Integer userId) {
        return modificationTrackerDao.getAllModifiersForUser(userDao.findByUserId(userId));
    }
}
