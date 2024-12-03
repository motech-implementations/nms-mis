package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AshaBeneficiaryMessageDao;
import com.beehyv.nmsreporting.model.AshaBeneficiaryMessage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("ashaBeneficiaryMessageQueueDao")
public class AshaBeneficiaryMessageDaoImpl extends AbstractDao<Long, AshaBeneficiaryMessage> implements AshaBeneficiaryMessageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AshaBeneficiaryMessageDaoImpl.class);

    @Override
    public List<AshaBeneficiaryMessage> fetchMessagesByDate(Date date) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("date", date));
        return criteria.list();
    }

    @Override
    public List<AshaBeneficiaryMessage> fetchMessagesByDateRange(List<Date> dates) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("date", dates));
        return criteria.list();
    }

}
