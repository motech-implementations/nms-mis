package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;

import java.util.Date;
import java.util.List;

public interface BeneficiaryWithRegistrationDateSubCentreDao {

    List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer blockId , Date fromDate , Date toDate);

    List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer blockId , Date fromDate , Date toDate);
}
