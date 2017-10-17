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

        if(criteria.list().isEmpty()){
//            Long a = (long)0;
//            MessageMatrix messageMatrix = new MessageMatrix(0,date,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a);
//            return messageMatrix;
            return null;
        }
        MessageMatrix messageMatrix = (MessageMatrix) criteria.list().get(0);

        messageMatrix.setChild_1_6_Content_1(messageMatrix.getChild_1_6_Content_1()==null?0:messageMatrix.getChild_1_6_Content_1());
        messageMatrix.setChild_1_6_Content_25(messageMatrix.getChild_1_6_Content_25()==null?0:messageMatrix.getChild_1_6_Content_25());
        messageMatrix.setChild_1_6_Content_50(messageMatrix.getChild_1_6_Content_50()==null?0:messageMatrix.getChild_1_6_Content_50());
        messageMatrix.setChild_1_6_Content_75(messageMatrix.getChild_1_6_Content_75()==null?0:messageMatrix.getChild_1_6_Content_75());
        messageMatrix.setChild_7_12_Content_1(messageMatrix.getChild_7_12_Content_1()==null?0:messageMatrix.getChild_7_12_Content_1());
        messageMatrix.setChild_7_12_Content_25(messageMatrix.getChild_7_12_Content_25()==null?0:messageMatrix.getChild_7_12_Content_25());
        messageMatrix.setChild_7_12_Content_50(messageMatrix.getChild_7_12_Content_50()==null?0:messageMatrix.getChild_7_12_Content_50());
        messageMatrix.setChild_7_12_Content_75(messageMatrix.getChild_7_12_Content_75()==null?0:messageMatrix.getChild_7_12_Content_75());
        messageMatrix.setChild_13_18_Content_1(messageMatrix.getChild_13_18_Content_1()==null?0:messageMatrix.getChild_13_18_Content_1());
        messageMatrix.setChild_13_18_Content_25(messageMatrix.getChild_13_18_Content_25()==null?0:messageMatrix.getChild_13_18_Content_25());
        messageMatrix.setChild_13_18_Content_50(messageMatrix.getChild_13_18_Content_50()==null?0:messageMatrix.getChild_13_18_Content_50());
        messageMatrix.setChild_13_18_Content_75(messageMatrix.getChild_13_18_Content_75()==null?0:messageMatrix.getChild_13_18_Content_75());
        messageMatrix.setChild_19_24_Content_1(messageMatrix.getChild_19_24_Content_1()==null?0:messageMatrix.getChild_19_24_Content_1());
        messageMatrix.setChild_19_24_Content_25(messageMatrix.getChild_19_24_Content_25()==null?0:messageMatrix.getChild_19_24_Content_25());
        messageMatrix.setChild_19_24_Content_50(messageMatrix.getChild_19_24_Content_50()==null?0:messageMatrix.getChild_19_24_Content_50());
        messageMatrix.setChild_19_24_Content_75(messageMatrix.getChild_19_24_Content_75()==null?0:messageMatrix.getChild_19_24_Content_75());
        messageMatrix.setChild_25_30_Content_1(messageMatrix.getChild_25_30_Content_1()==null?0:messageMatrix.getChild_25_30_Content_1());
        messageMatrix.setChild_25_30_Content_25(messageMatrix.getChild_25_30_Content_25()==null?0:messageMatrix.getChild_25_30_Content_25());
        messageMatrix.setChild_25_30_Content_50(messageMatrix.getChild_25_30_Content_50()==null?0:messageMatrix.getChild_25_30_Content_50());
        messageMatrix.setChild_25_30_Content_75(messageMatrix.getChild_25_30_Content_75()==null?0:messageMatrix.getChild_25_30_Content_75());
        messageMatrix.setChild_31_36_Content_1(messageMatrix.getChild_31_36_Content_1()==null?0:messageMatrix.getChild_31_36_Content_1());
        messageMatrix.setChild_31_36_Content_25(messageMatrix.getChild_31_36_Content_25()==null?0:messageMatrix.getChild_31_36_Content_25());
        messageMatrix.setChild_31_36_Content_50(messageMatrix.getChild_31_36_Content_50()==null?0:messageMatrix.getChild_31_36_Content_50());
        messageMatrix.setChild_31_36_Content_75(messageMatrix.getChild_31_36_Content_75()==null?0:messageMatrix.getChild_31_36_Content_75());
        messageMatrix.setChild_37_42_Content_1(messageMatrix.getChild_37_42_Content_1()==null?0:messageMatrix.getChild_37_42_Content_1());
        messageMatrix.setChild_37_42_Content_25(messageMatrix.getChild_37_42_Content_25()==null?0:messageMatrix.getChild_37_42_Content_25());
        messageMatrix.setChild_37_42_Content_50(messageMatrix.getChild_37_42_Content_50()==null?0:messageMatrix.getChild_37_42_Content_50());
        messageMatrix.setChild_37_42_Content_75(messageMatrix.getChild_37_42_Content_75()==null?0:messageMatrix.getChild_37_42_Content_75());
        messageMatrix.setChild_43_48_Content_1(messageMatrix.getChild_43_48_Content_1()==null?0:messageMatrix.getChild_43_48_Content_1());
        messageMatrix.setChild_43_48_Content_25(messageMatrix.getChild_43_48_Content_25()==null?0:messageMatrix.getChild_43_48_Content_25());
        messageMatrix.setChild_43_48_Content_50(messageMatrix.getChild_43_48_Content_50()==null?0:messageMatrix.getChild_43_48_Content_50());
        messageMatrix.setChild_43_48_Content_75(messageMatrix.getChild_43_48_Content_75()==null?0:messageMatrix.getChild_43_48_Content_75());
        messageMatrix.setMother_1_6_Content_1(messageMatrix.getMother_1_6_Content_1()==null?0:messageMatrix.getMother_1_6_Content_1());
        messageMatrix.setMother_1_6_Content_25(messageMatrix.getMother_1_6_Content_25()==null?0:messageMatrix.getMother_1_6_Content_25());
        messageMatrix.setMother_1_6_Content_50(messageMatrix.getMother_1_6_Content_50()==null?0:messageMatrix.getMother_1_6_Content_50());
        messageMatrix.setMother_1_6_Content_75(messageMatrix.getMother_1_6_Content_75()==null?0:messageMatrix.getMother_1_6_Content_75());
        messageMatrix.setMother_7_12_Content_1(messageMatrix.getMother_7_12_Content_1()==null?0:messageMatrix.getMother_7_12_Content_1());
        messageMatrix.setMother_7_12_Content_25(messageMatrix.getMother_7_12_Content_25()==null?0:messageMatrix.getMother_7_12_Content_25());
        messageMatrix.setMother_7_12_Content_50(messageMatrix.getMother_7_12_Content_50()==null?0:messageMatrix.getMother_7_12_Content_50());
        messageMatrix.setMother_7_12_Content_75(messageMatrix.getMother_7_12_Content_75()==null?0:messageMatrix.getMother_7_12_Content_75());
        messageMatrix.setMother_13_18_Content_1(messageMatrix.getMother_13_18_Content_1()==null?0:messageMatrix.getMother_13_18_Content_1());
        messageMatrix.setMother_13_18_Content_25(messageMatrix.getMother_13_18_Content_25()==null?0:messageMatrix.getMother_13_18_Content_25());
        messageMatrix.setMother_13_18_Content_50(messageMatrix.getMother_13_18_Content_50()==null?0:messageMatrix.getMother_13_18_Content_50());
        messageMatrix.setMother_13_18_Content_75(messageMatrix.getMother_13_18_Content_75()==null?0:messageMatrix.getMother_13_18_Content_75());
        messageMatrix.setMother_19_24_Content_1(messageMatrix.getMother_19_24_Content_1()==null?0:messageMatrix.getMother_19_24_Content_1());
        messageMatrix.setMother_19_24_Content_25(messageMatrix.getMother_19_24_Content_25()==null?0:messageMatrix.getMother_19_24_Content_25());
        messageMatrix.setMother_19_24_Content_50(messageMatrix.getMother_19_24_Content_50()==null?0:messageMatrix.getMother_19_24_Content_50());
        messageMatrix.setMother_19_24_Content_75(messageMatrix.getMother_19_24_Content_75()==null?0:messageMatrix.getMother_19_24_Content_75());
        return messageMatrix;

    }

}
