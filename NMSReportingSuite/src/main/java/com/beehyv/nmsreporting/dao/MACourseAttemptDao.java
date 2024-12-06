package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MACourseFirstCompletion;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {

   List<MACourseFirstCompletion> getSuccessFulCompletion(String forMonth);

   void bulkUpdateMACourseFirstCompletion(List<Long> flwIds);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(String forMonth, Integer stateId);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(String forMonth, Integer districtId);

   List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockId(String forMonth, Integer blockId);

   Long getCountForGivenDistrict(String forMonth,Integer districtId);

    MACourseFirstCompletion getSuccessFulCompletionByExtrnalFlwId(String forMonth, Long Extr_Flw_Id, Integer state_id);

    void updateMACourseFirstCompletion(MACourseFirstCompletion maCourseFirstCompletion);

    List<MACourseFirstCompletion> getSuccessFulCompletion(Long msisdn);

    MACourseFirstCompletion getMACourseFirstCompletionByMobileNo(Long msisdn);

    List<MACourseFirstCompletion> getSuccessFulCompletionByState(Long msisdn, Integer stateId);

    List<MACourseFirstCompletion> getSuccessFulCompletionByDistrict(Long msisdn, Integer stateId, Integer districtId);

    List<MACourseFirstCompletion> getSuccessFulCompletionByBlock(Long msisdn, Integer stateId, Integer districtId, Integer blockId);

    List<MACourseFirstCompletion> getSuccessFulCompletionByStateIdAndMonth(String forMonth, Integer stateId);

    List<MACourseFirstCompletion> getSuccessFulCompletionByStateId(Integer stateId);

    List<MACourseFirstCompletion> getSuccessFullCompletionByStateAndCompletionDate(Integer stateId, Date firstCompletionDate);

    List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictIdAndMonth(String forMonth, Integer stateId, Integer districtId);

    List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockIdAndMont(String forMonth, Integer stateId, Integer districtId, Integer blockId);

}
