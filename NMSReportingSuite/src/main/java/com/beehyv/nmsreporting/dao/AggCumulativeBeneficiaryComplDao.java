package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiaryCompletion;

import java.util.Date;

/**
 * Created by beehyv on 6/10/17.
 */
public interface AggCumulativeBeneficiaryComplDao {

    AggregateCumulativeBeneficiaryCompletion getBeneficiaryCompletion(Integer locationId,String locationType,Date date);
}
