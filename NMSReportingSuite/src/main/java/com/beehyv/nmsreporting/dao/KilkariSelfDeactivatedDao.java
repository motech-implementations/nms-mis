package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariSelfDeactivated;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSelfDeactivatedDao {

    public List<KilkariSelfDeactivated> getSelfDeactivatedUsers(Date fromDate, Date toDate);

}
