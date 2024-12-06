package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AshaBeneficiaryMessage;

import java.util.Date;
import java.util.List;

public interface AshaBeneficiaryMessageDao  {

    List<AshaBeneficiaryMessage> fetchMessagesByDate(Date date);


    List<AshaBeneficiaryMessage> fetchMessagesByDateRange(List<Date> dates);



}
