package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
    public User findByUserId(Integer userId) {
        return getByKey(userId);
    }

    public User findByUserName(String username) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("username", username),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    public User findByEmailId(String emailId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("emailId", emailId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    public List<User> findByPhoneNumber(String phoneNumber) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("phoneNumber", phoneNumber),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    public List<User> findByLocation(Location locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("locationId", locationId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    public List<User> findByCreationDate(Date creationDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("creationDate", creationDate));
        return (List<User>) criteria.list();
    }

    public List<User> getAllActiveUsers() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()));
        return (List<User>) criteria.list();
    }

    public List<User> getUsersByAccountStatus(String accountStatus) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountStatus", accountStatus));
        return (List<User>) criteria.list();
    }

    public List<User> getUsersByRole(Role roleId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("roleId", roleId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    public void saveUser(User user) {
        persist(user);
    }

    @Override
    public boolean isAdminCreated(District districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("district", districtId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()),Restrictions.eq("access_level",AccessLevel.DISTRICT.getAccessLevel()),Restrictions.eq("access_type",AccessType.ADMIN.getAccesType())));
        List<User> Admins=(List<User>) criteria.list();
        if(Admins==null||Admins.size()==0){
            return true;
        }
        else return false;
    }





}
