package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.ListeningMatrix;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beehyv on 10/10/17.
 */
public interface ListeningMatrixWeekDao {

    HashMap<String,ListeningMatrix> getListeningMatrix(Integer locationDate, String locationType, Date date);
}
