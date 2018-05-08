package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MessageMatrixDao;
import com.beehyv.nmsreporting.model.MessageMatrix;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beehyv on 12/10/17.
 */
@Repository("messageMatrixDao")
public class MessageMatrixDaoImpl extends AbstractDao<Integer,MessageMatrix> implements MessageMatrixDao {

    @Override
    public List<MessageMatrix> getMessageMatrixData(Integer locationId,String locationType,Date date, String periodType){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date", date)
        ));

        List<MessageMatrix> result = criteria.list();
        if(result.isEmpty()){
//            Long a = (long)0;
//            MessageMatrix messageMatrix = new MessageMatrix(0,date,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a);
//            return messageMatrix;
            return null;
        }
        HashMap<String,MessageMatrix> EmptyMatrix= new HashMap<>();
        for(int count =0; count<12; count++){
            Long a = (long)0;
            MessageMatrix messageMatrixemp = new MessageMatrix();
            String mW="";
            switch (count){
                case 0: {mW = "mother_week_1_6";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 1: {mW = "mother_week_7_12";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 2: {mW = "mother_week_13_18";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 3: {mW = "mother_week_19_24";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 4: {mW = "child_week_1_6";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 5: {mW = "child_week_7_12";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 6: {mW = "child_week_13_18";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 7: {mW = "child_week_19_24";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 8: {mW = "child_week_25_30";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 9: {mW = "child_week_31_36";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 10: {mW = "child_week_37_42";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
                case 11: {mW = "child_week_43_48";
                     messageMatrixemp = new MessageMatrix(0,"",a,date,mW,a,a,a,a,"");break;}
            }
            EmptyMatrix.put(mW,messageMatrixemp);

        }

        for(int i=0; i<result.size();i++) {

            MessageMatrix messageMatrix = result.get(i);

            messageMatrix.setListened_25_50(messageMatrix.getListened_25_50() == null ? 0 : messageMatrix.getListened_25_50());
            messageMatrix.setListened_50_75(messageMatrix.getListened_50_75() == null ? 0 : messageMatrix.getListened_50_75());
            messageMatrix.setListened_lessthan25(messageMatrix.getListened_lessthan25() == null ? 0 : messageMatrix.getListened_lessthan25());
            messageMatrix.setListened_morethan75(messageMatrix.getListened_morethan75() == null ? 0 : messageMatrix.getListened_morethan75());
            EmptyMatrix.put(messageMatrix.getMessageWeek(),messageMatrix);
        }
        List<MessageMatrix> finalMatrix = new ArrayList<>();
        finalMatrix.addAll(EmptyMatrix.values());
        return finalMatrix;

    }

}
