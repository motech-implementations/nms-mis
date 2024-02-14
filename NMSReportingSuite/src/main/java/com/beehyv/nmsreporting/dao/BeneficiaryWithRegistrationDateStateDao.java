package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;

import java.util.Date;
import java.util.List;

public interface BeneficiaryWithRegistrationDateStateDao {

    List<KilkariSubscriberRegistrationDateDto> allCountOffReports( Date fromDate , Date toDate);

    List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Date fromDate , Date toDate);
}
