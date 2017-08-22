package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Taluka;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface TalukaDao {

    Taluka findByTalukaId(Integer talukaId);

    List<Taluka> findByName(String talukaName);

}
