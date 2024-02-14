package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;

import java.util.Date;
import java.util.List;

public interface BeneficiaryWithRegistrationDateDistrictDao {

    List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer stateId , Date fromDate , Date toDate);

    List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer stateId , Date fromDate , Date toDate);
}
