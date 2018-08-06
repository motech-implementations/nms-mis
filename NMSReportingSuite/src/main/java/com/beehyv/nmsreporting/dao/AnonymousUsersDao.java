package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AnonymousUsers;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface AnonymousUsersDao {

    List<AnonymousUsers> getAnonymousUsers(String forMonth);

    List<AnonymousUsers> getAnonymousUsersCircle(String forMonth,String circleName);

}
