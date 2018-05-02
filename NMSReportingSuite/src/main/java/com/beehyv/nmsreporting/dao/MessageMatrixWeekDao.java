package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MessageMatrix;
import com.beehyv.nmsreporting.model.MessageMatrixWeek;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 12/10/17.
 */
public interface MessageMatrixWeekDao {
    List<MessageMatrix> getMessageMatrixData(Integer locationId, String locationType, Date date);
}
