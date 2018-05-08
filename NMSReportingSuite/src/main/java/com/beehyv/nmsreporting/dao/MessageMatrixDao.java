package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MessageMatrix;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 12/10/17.
 */
public interface MessageMatrixDao {
    List<MessageMatrix> getMessageMatrixData(Integer locationId, String locationType, Date date, String periodType);
}
