package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Override
    public User findByUserId(Integer userId) {
        return getByKey(userId);
    }

    public User findByUserName(String username) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("username", username),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findByEmailId(String emailId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("emailId", emailId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("phoneNumber", phoneNumber),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> findByCreationDate(Date creationDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("creationDate", creationDate));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getActiveUsers() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getAllUsers() {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getUsersByAccountStatus(String accountStatus) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountStatus", accountStatus));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getUsersByRole(Role roleId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("roleId", roleId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    @Override
    public <E> List<User> getUsersByLocation(String propertyName, E location) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq(propertyName, location));
        return (List<User>) criteria.list();
    }

    @Override
    public void saveUser(User user) {
        persist(user);
    }

    @Override
    public boolean isAdminCreated(District districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("district", districtId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()),Restrictions.eq("access_level", AccessLevel.DISTRICT.getAccessLevel()),Restrictions.eq("access_type", AccessType.ADMIN.getAccesType())));
        List<User> Admins=(List<User>) criteria.list();
        if(Admins==null||Admins.size()==0){
            return true;
        }
        else return false;
    }

}
