package com.beehyv.nmsreporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {

    Long getCountForGivenDistrict(String forMonth, Integer districtId);
}
