package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Taluka;
import com.beehyv.nmsreporting.model.Taluka;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface TalukaDao {

    public Taluka findByTalukaId(Integer talukaId);

    public List<Taluka> findByName(String talukaName);


    public List<Taluka> getChildTalukas(int talukaId);

    public void saveTaluka(Taluka taluka);

    public void deleteTaluka(Taluka taluka);
}
