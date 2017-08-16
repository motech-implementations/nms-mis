package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariSixWeeksNoAnswer;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSixWeeksNoAnswerDao {
    public List<KilkariSixWeeksNoAnswer> getKilkariUsers(Date fromDate, Date toDate);
    List<KilkariSixWeeksNoAnswer> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId);
    List<KilkariSixWeeksNoAnswer> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId);
    List<KilkariSixWeeksNoAnswer> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId);
}
