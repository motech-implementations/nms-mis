package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariDeactivationOther;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSixWeeksNoAnswerDao {
    List<KilkariDeactivationOther> getKilkariUsers(Date fromDate, Date toDate);
    List<KilkariDeactivationOther> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId);
    List<KilkariDeactivationOther> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);
    List<KilkariDeactivationOther> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);

    List<KilkariDeactivationOther> getLowListenershipUsers(Date fromDate, Date toDate);
    List<KilkariDeactivationOther> getLowListenershipUsersWithStateId(Date fromDate, Date toDate, Integer stateId);
    List<KilkariDeactivationOther> getLowListenershipUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);
    List<KilkariDeactivationOther> getLowListenershipUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);
}
