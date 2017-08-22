package com.beehyv.nmsreporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
public interface FrontLineWorkersDao {

    Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId);
}
