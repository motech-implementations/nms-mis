package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;

import java.util.Date;
import java.util.List;

public interface MACourseCompletionDao {
     List<CourseCompletionDTO> findBySentNotificationIsFalseAndHasPassed(Integer offset, Integer limit);
     Date getLastEtlTimeForTableId(int tableId);
     MACourseCompletion getAshaByFLWId(long flwId);
     long getAshaPhoneNo(long flwId);
     MACourseCompletion getAshaById(long id);

     long getAshaLanguageId(long flwId);
     void updateMACourseCompletion(MACourseCompletion maCourseCompletion);
     void updateLastEtlTimeForTableId(int tableId);
}
