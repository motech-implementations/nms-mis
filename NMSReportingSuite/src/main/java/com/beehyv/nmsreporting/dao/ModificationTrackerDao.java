package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ModificationTracker;
import com.beehyv.nmsreporting.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface ModificationTrackerDao {
    ModificationTracker findModificationById(Integer modificationId);

    void saveModification(ModificationTracker modification);

    void deleteModification(ModificationTracker modification);

    List<ModificationTracker> getAllModifications();

    List<ModificationTracker> getAllModificationsByUser(User userId);

    List<ModificationTracker> getAllModifiersForUser(User userId);

    List<ModificationTracker> getAllModificationsByDate(Date date);
}
