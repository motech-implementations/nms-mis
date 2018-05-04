package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ListeningMatrixWeekDao;
import com.beehyv.nmsreporting.model.ListeningMatrix;
import com.beehyv.nmsreporting.model.ListeningMatrixWeek;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beehyv on 10/10/17.
 */
@Qualifier
@Repository("listeningMatrixWeekReportDao")
public class ListeningMatrixWeekDaoImpl extends AbstractDao<Integer,ListeningMatrixWeek> implements ListeningMatrixWeekDao{

    @Override
    public HashMap<String,ListeningMatrix> getListeningMatrix(Integer locationId, String locationType, Date date){

        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date", date)
        ));

        List<ListeningMatrixWeek> result = criteria.list();
        if(result.isEmpty()){
            return null;
        }

        HashMap<String,ListeningMatrix> listeningMatrix= new HashMap<>();
        for(int count =0; count<4; count++){
            Long a = (long)0;
            ListeningMatrix listeningMatrixempty = new ListeningMatrix();
            String callPercent="";
            switch (count){
                case 0: {callPercent = "callsListened_morethan75";
                    listeningMatrixempty = new ListeningMatrix(0,"",a,date,callPercent,a,a,a,a);break;}
                case 1: {callPercent = "callsListened_50_75";
                    listeningMatrixempty = new ListeningMatrix(0,"",a,date,callPercent,a,a,a,a);break;}
                case 2: {callPercent = "callsListened_25_50";
                    listeningMatrixempty = new ListeningMatrix(0,"",a,date,callPercent,a,a,a,a);break;}
                case 3: {callPercent = "callsListened_lessthan25";
                    listeningMatrixempty = new ListeningMatrix(0,"",a,date,callPercent,a,a,a,a);break;}

            }
            listeningMatrix.put(callPercent,listeningMatrixempty);

        }

        for(int i=0; i<result.size();i++) {

            ListeningMatrixWeek listeningMatrixWeektemp = result.get(i);
            ListeningMatrix listeningMatrixtemp =new ListeningMatrix();

            listeningMatrixtemp.setContentListened_morethan75(listeningMatrixWeektemp.getContentListened_morethan75() == null ? 0 : listeningMatrixWeektemp.getContentListened_morethan75());
            listeningMatrixtemp.setContentListened_50_75(listeningMatrixWeektemp.getContentListened_50_75() == null ? 0 : listeningMatrixWeektemp.getContentListened_50_75());
            listeningMatrixtemp.setContentListened_25_50(listeningMatrixWeektemp.getContentListened_25_50() == null ? 0 : listeningMatrixWeektemp.getContentListened_25_50());
            listeningMatrixtemp.setContentListened_lessthan25(listeningMatrixWeektemp.getContentListened_lessthan25() == null ? 0 : listeningMatrixWeektemp.getContentListened_lessthan25());
            listeningMatrix.put(listeningMatrixWeektemp.getCalls_listened_percentage(),listeningMatrixtemp);
        }



        return listeningMatrix;
    }
}
