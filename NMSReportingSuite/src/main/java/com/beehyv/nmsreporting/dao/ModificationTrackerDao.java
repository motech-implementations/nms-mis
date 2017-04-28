package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ModificationTracker;
import com.beehyv.nmsreporting.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface ModificationTrackerDao {
    public ModificationTracker findModificationById(Integer modificationId);

    public void saveModification(ModificationTracker modification);

    public void deleteModification(ModificationTracker modification);

    public List<ModificationTracker> getAllModifications();

    public List<ModificationTracker> getAllModificationsByUser(User userId);

    public List<ModificationTracker> getAllModifiersForUser(User userId);

    public List<ModificationTracker> getAllModificationsByDate(Date date);
}
