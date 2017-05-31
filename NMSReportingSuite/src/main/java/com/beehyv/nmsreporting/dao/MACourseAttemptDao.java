package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.MACourseCompletion;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {
   List<FrontLineWorkers> getSuccessFulFirstCompletion(Date fromDate, Date toDate);

    Date getFirstCompletionDate(Long flwId);

    List<FrontLineWorkers> getSuccessFulCompletion(Date toDate);
}
