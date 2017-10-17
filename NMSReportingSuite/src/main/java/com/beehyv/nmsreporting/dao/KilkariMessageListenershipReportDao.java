package com.beehyv.nmsreporting.dao;

/**
 * Created by himanshu on 06/10/17.
 */


import com.beehyv.nmsreporting.entity.KilkariMessageListenershipReportDto;
import com.beehyv.nmsreporting.model.State;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface KilkariMessageListenershipReportDao {

    List<State> getStateList();

    List<Object> getAllBeneficiaryIds(Date startDate, Date endDate, Integer stateId);

    Integer getTotalCallsMadeToABeneficiary(BigInteger beneficiaryId);

    Integer getTotalCallsAnsweredByBeneficiary(BigInteger beneficiaryId);
}
