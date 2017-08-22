package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserService {
    User findUserByUserId(Integer userId);

    List<User> findAllActiveUsers();
}
