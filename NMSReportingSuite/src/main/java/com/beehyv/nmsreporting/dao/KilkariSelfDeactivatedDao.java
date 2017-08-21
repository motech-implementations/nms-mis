package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariSelfDeactivated;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSelfDeactivatedDao {

    public List<KilkariSelfDeactivated> getSelfDeactivatedUsers(Date fromDate, Date toDate);

    List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOfSelfDeactivatedUsers(Date fromDate, Date toDate, Integer districtId);

}
