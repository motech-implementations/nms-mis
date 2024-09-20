package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;

import java.util.Date;

/**
 * Created by beehyv on 9/10/17.
 */
public interface AggregateCumulativeBeneficiaryDao {

    AggregateCumulativeBeneficiary getCumulativeBeneficiary(Long locationId, String locationType, Date toDate,String periodType);

    Long getTotalBeneficiariesCalled(Long locationId, String locationType, Date date);

    Long getTotalBeneficiariesAnsweredAtleastOnce(Long locationId, String locationType, Date date);

    Long getCalledKilkariInboxCount(Long locationId, String locationType, Date date);

    Long getJoinedSubscriptionSum(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType);

    Long getTotalDeactivationSum(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType);

    Long getJoinedSubscriptionSumTillDate(Integer locationId, String locationType, Date toDate);

    Long getCumulativeJoinedSubscription(Long locationId, String locationType, Date toDate);
}
