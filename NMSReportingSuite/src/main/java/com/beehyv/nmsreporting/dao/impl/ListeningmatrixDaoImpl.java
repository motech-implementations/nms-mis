package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ListeningMatrixDao;
import com.beehyv.nmsreporting.model.ListeningMatrix;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 10/10/17.
 */
@Repository("listeningMatrixDao")
public class ListeningmatrixDaoImpl extends AbstractDao<Integer,ListeningMatrix> implements ListeningMatrixDao{

    @Override
    public ListeningMatrix getListeningMatrix(Date date){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("date",date));
        return (ListeningMatrix) criteria.list().get(0);

    }
}
