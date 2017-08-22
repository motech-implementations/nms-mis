package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariLowUsage;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariLowUsageDao {

    public List<KilkariLowUsage> getKilkariLowUsageUsers(String forMonth);

    List<KilkariLowUsage> getKilkariLowUsageUsersWithStateId(String forMonth, Integer stateId);

    List<KilkariLowUsage> getKilkariLowUsageUsersWithDistrictId(String forMonth, Integer districtId);

    List<KilkariLowUsage> getKilkariLowUsageUsersWithBlockId(String forMonth, Integer blockId);

    Long getCountOfLowUsageUsersForGivenDistrict(String month, Integer districtId);
}
