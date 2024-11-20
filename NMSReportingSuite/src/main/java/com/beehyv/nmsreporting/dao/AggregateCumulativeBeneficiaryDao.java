package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by beehyv on 9/10/17.
 */
public interface AggregateCumulativeBeneficiaryDao {

    AggregateCumulativeBeneficiary getCumulativeBeneficiary(Long locationId, String locationType, Date toDate,String periodType);

    Long getTotalBeneficiariesCalled(Long locationId, String locationType, Date date);

    Long getTotalBeneficiariesAnsweredAtleastOnce(Long locationId, String locationType, Date date);

    Long getCalledKilkariInboxCount(Long locationId, String locationType, Date date);

    Map<Integer, Long> getJoinedSubscriptionSum(List<Integer> locationIds, String locationType, Date fromDate, Date toDate, String periodType);

    Map<Integer,Long> getTotalDeactivationSum(List<Integer> locationId, String locationType, Date fromDate, Date toDate, String periodType);

    Long getJoinedSubscriptionSumTillDate(Integer locationId, String locationType, Date toDate);
}
