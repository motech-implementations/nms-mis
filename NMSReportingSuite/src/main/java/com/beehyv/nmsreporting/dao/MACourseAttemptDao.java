package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MACourseFirstCompletion;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {

   List<MACourseFirstCompletion> getSuccessFulCompletion(String forMonth);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(String forMonth, Integer stateId);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(String forMonth, Integer districtId);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockId(String forMonth, Integer blockId);

   Long getCountForGivenDistrict(String forMonth,Integer districtId);

   public MACourseFirstCompletion getSuccessFulCompletionByExtrnalFlwId(String forMonth, Long Extr_Flw_Id, Integer state_id);

   public void updateMACourseFirstCompletion(MACourseFirstCompletion maCourseFirstCompletion);

   public List<MACourseFirstCompletion> getSuccessFulCompletion(Long msisdn);

   public List<MACourseFirstCompletion> getSuccessFulCompletionByState(Long msisdn, Integer stateId);

   public List<MACourseFirstCompletion> getSuccessFulCompletionByDistrict(Long msisdn, Integer stateId, Integer districtId);

   public List<MACourseFirstCompletion> getSuccessFulCompletionByBlock(Long msisdn, Integer stateId, Integer districtId, Integer blockId);

}
