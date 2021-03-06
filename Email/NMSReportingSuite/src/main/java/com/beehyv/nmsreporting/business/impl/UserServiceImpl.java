package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserId(Integer userId) {
        return userDao.findByUserId(userId);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userDao.getActiveUsers();
    }

}
