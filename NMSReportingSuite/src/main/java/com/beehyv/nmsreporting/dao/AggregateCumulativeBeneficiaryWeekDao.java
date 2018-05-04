package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;

import java.util.Date;

public interface AggregateCumulativeBeneficiaryWeekDao {
    AggregateCumulativeBeneficiary getCumulativeBeneficiary(Long locationId, String locationType, Date toDate);
}
