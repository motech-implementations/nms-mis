package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;

import java.util.Date;
import java.util.List;

public interface BeneficiaryWithRegistrationDateBlockDao {

    List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer districtId , Date fromDate , Date toDate);

    List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer districtId , Date fromDate , Date toDate);
}
