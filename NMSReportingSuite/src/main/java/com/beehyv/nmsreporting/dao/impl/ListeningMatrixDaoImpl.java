package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ListeningMatrixDao;
import com.beehyv.nmsreporting.model.ListeningMatrix;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 10/10/17.
 */
@Qualifier
@Repository("listeningMatrixReportDao")
public class ListeningMatrixDaoImpl extends AbstractDao<Integer,ListeningMatrix> implements ListeningMatrixDao{

    @Override
    public ListeningMatrix getListeningMatrix(Date date){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("date",date));

        if(criteria.list().isEmpty()){
             return null;
        }
       ListeningMatrix listeningMatrix= (ListeningMatrix) criteria.list().get(0);

        listeningMatrix.setCalls_1_content_1(listeningMatrix.getCalls_1_content_1()==null?0:listeningMatrix.getCalls_1_content_1());
        listeningMatrix.setCalls_1_content_25(listeningMatrix.getCalls_1_content_25()==null?0:listeningMatrix.getCalls_1_content_25());
        listeningMatrix.setCalls_1_content_50(listeningMatrix.getCalls_1_content_50()==null?0:listeningMatrix.getCalls_1_content_50());
        listeningMatrix.setCalls_1_content_75(listeningMatrix.getCalls_1_content_75()==null?0:listeningMatrix.getCalls_1_content_75());
        listeningMatrix.setCalls_25_content_1(listeningMatrix.getCalls_25_content_1()==null?0:listeningMatrix.getCalls_25_content_1());
        listeningMatrix.setCalls_25_content_25(listeningMatrix.getCalls_25_content_25()==null?0:listeningMatrix.getCalls_25_content_25());
        listeningMatrix.setCalls_25_content_50(listeningMatrix.getCalls_25_content_50()==null?0:listeningMatrix.getCalls_25_content_50());
        listeningMatrix.setCalls_25_content_75(listeningMatrix.getCalls_25_content_75()==null?0:listeningMatrix.getCalls_25_content_75());
        listeningMatrix.setCalls_50_content_1(listeningMatrix.getCalls_50_content_1()==null?0:listeningMatrix.getCalls_50_content_1());
        listeningMatrix.setCalls_50_content_25(listeningMatrix.getCalls_50_content_25()==null?0:listeningMatrix.getCalls_50_content_25());
        listeningMatrix.setCalls_50_content_50(listeningMatrix.getCalls_50_content_50()==null?0:listeningMatrix.getCalls_50_content_50());
        listeningMatrix.setCalls_50_content_75(listeningMatrix.getCalls_50_content_75()==null?0:listeningMatrix.getCalls_50_content_75());
        listeningMatrix.setCalls_75_Content_1(listeningMatrix.getCalls_75_Content_1()==null?0:listeningMatrix.getCalls_75_Content_1());
        listeningMatrix.setCalls_75_Content_25(listeningMatrix.getCalls_75_Content_25()==null?0:listeningMatrix.getCalls_75_Content_25());
        listeningMatrix.setCalls_75_Content_50(listeningMatrix.getCalls_75_Content_50()==null?0:listeningMatrix.getCalls_75_Content_50());
        listeningMatrix.setCalls_75_Content_75(listeningMatrix.getCalls_75_Content_75()==null?0:listeningMatrix.getCalls_75_Content_75());
        return listeningMatrix;
    }
}
