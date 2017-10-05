package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.BreadCrumbDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 29/9/17.
 */
public interface BreadCrumbService {

    List<BreadCrumbDto> getBreadCrumbs(User currentUser, ReportRequest reportRequest);

}
