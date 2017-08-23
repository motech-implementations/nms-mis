package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AnonymousUsersDao;
import com.beehyv.nmsreporting.model.AnonymousUsers;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("anonymousUsersDao")
public class AnonymousUsersDaoImpl extends AbstractDao<Integer, AnonymousUsers> implements AnonymousUsersDao {


}

