package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.StateService;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 6/6/17.
 */
public interface StateServiceDao {
    List<StateService> getStatesByServiceType(String serviceType);

    List<String> getServiceTypeOfState(Integer stateId);

    Date getServiceStartDateForState(Integer stateId, String type);
}
