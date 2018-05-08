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
}
