package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.Taluka;
import com.beehyv.nmsreporting.model.Taluka;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface TalukaDao {

    Taluka findByTalukaId(Integer talukaId);

    List<Taluka> findByName(String talukaName);

    List<Taluka> getTalukasOfDistrict(District district);

    List<Taluka> getTalukasOfState(State state);

    List<Taluka> getAllTalukas();

    void saveTaluka(Taluka taluka);

    void deleteTaluka(Taluka taluka);
}