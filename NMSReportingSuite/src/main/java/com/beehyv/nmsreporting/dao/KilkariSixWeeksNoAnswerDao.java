package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariManualDeactivations;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSixWeeksNoAnswerDao {
    List<KilkariManualDeactivations> getKilkariUsers(Date fromDate, Date toDate);

    List<KilkariManualDeactivations> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<KilkariManualDeactivations> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<KilkariManualDeactivations> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOfDeactivatedForDistrict(Date fromDate, Date toDate, Integer districtId);

    List<KilkariManualDeactivations> getLowListenershipUsers(Date fromDate, Date toDate);

    List<KilkariManualDeactivations> getLowListenershipUsersWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<KilkariManualDeactivations> getLowListenershipUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<KilkariManualDeactivations> getLowListenershipUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOfLowListenershipUsersForDistrict(Date fromDate, Date toDate, Integer districtId);

}
