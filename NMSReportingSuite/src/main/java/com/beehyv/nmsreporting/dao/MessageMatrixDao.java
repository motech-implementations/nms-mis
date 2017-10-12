package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MessageMatrix;

import java.util.Date;

/**
 * Created by beehyv on 12/10/17.
 */
public interface MessageMatrixDao {
    MessageMatrix getMessageMatrixData(Date date);
}
