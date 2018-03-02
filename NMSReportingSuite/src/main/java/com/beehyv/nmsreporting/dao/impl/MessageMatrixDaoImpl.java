package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MessageMatrixDao;
import com.beehyv.nmsreporting.model.MessageMatrix;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 12/10/17.
 */
@Repository("messageMatrixDao")
public class MessageMatrixDaoImpl extends AbstractDao<Integer,MessageMatrix> implements MessageMatrixDao {

    @Override
    public List<MessageMatrix> getMessageMatrixData(Integer locationId,String locationType,Date date){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date", date)
        ));

        List<MessageMatrix> result = criteria.list();
        if(result.isEmpty()){
//            Long a = (long)0;
//            MessageMatrix messageMatrix = new MessageMatrix(0,date,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a);
//            return messageMatrix;
            return null;
        }

        for(int i=0; i<result.size();i++) {
            MessageMatrix messageMatrix = result.get(i);

            messageMatrix.setListened_25_50(messageMatrix.getListened_25_50() == null ? 0 : messageMatrix.getListened_25_50());
            messageMatrix.setListened_50_75(messageMatrix.getListened_50_75() == null ? 0 : messageMatrix.getListened_50_75());
            messageMatrix.setListened_lessthan25(messageMatrix.getListened_lessthan25() == null ? 0 : messageMatrix.getListened_lessthan25());
            messageMatrix.setListened_morethan75(messageMatrix.getListened_morethan75() == null ? 0 : messageMatrix.getListened_morethan75());
            result.set(i,messageMatrix);
        }

        return result;

    }

}
