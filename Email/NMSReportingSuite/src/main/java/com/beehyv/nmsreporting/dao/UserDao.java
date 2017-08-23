package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserDao {
    User findByUserId(Integer userId);

    List<User> getActiveUsers();
}
