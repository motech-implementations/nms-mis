package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;

import java.util.Date;
import java.util.List;

public interface MACourseCompletionDao {
    List<CourseCompletionDTO> findBySentNotificationIsFalseAndHasPassed();
    public Date getLastEtlTimeForTableId(int tableId);
    public MACourseCompletion getAshaByFLWId(long flwId);
    public MACourseCompletion getAshaByPhoneNo(long flw_msisdn);
    public long getAshaPhoneNo(long flwId);
    public MACourseCompletion getAshaById(long id);

    public int getAshaLanguageId(long flwId);
    public void updateMACourseCompletion(MACourseCompletion maCourseCompletion);
    public void updateLastEtlTimeForTableId(int tableId);
}
