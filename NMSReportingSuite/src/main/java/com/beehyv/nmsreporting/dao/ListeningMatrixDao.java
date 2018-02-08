package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ListeningMatrix;

import java.util.Date;

/**
 * Created by beehyv on 10/10/17.
 */
public interface ListeningMatrixDao {

    ListeningMatrix getListeningMatrix(Integer locationDate, String locationType,Date date);
}
