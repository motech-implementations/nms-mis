package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MessageMatrixDao;
import com.beehyv.nmsreporting.model.MessageMatrix;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 12/10/17.
 */
@Repository("messageMatrixDao")
public class MessageMatrixDaoImpl extends AbstractDao<Integer,MessageMatrix> implements MessageMatrixDao {

    @Override
    public MessageMatrix getMessageMatrixData(Date date){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("date",date));
        return (MessageMatrix) criteria.list().get(0);

    }

}
