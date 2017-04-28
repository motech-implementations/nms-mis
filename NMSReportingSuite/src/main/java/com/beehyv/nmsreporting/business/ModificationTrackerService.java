package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.ModificationTracker;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface ModificationTrackerService {
    public ModificationTracker findModificationById(Integer modificationId);

    public void saveModification(ModificationTracker modification);

    public void deleteModification(ModificationTracker modification);

    public List<ModificationTracker> getAllModifications();

    public List<ModificationTracker> getAllModificationsMadeByUser(Integer userId);

    public List<ModificationTracker> getAllModificationsByDate(Date date);

    public List<ModificationTracker> getAllModifiersForUser(Integer userId);
}

