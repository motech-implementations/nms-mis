package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import com.beehyv.nmsreporting.model.State;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {

   List<MACourseFirstCompletion> getSuccessFulCompletion(Date toDate);
   List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId);
   List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(Date toDate, Integer districtId);
   List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockId(Date toDate, Integer blockId);
}
