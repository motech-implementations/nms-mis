package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentReportDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentWeekDao;
import com.beehyv.nmsreporting.model.KilkariThematicContent;
import com.beehyv.nmsreporting.model.KilkariThematicContentWeek;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("kilkariThematicContentWeekDao")
public class KilkariThematicContentWeekDaoImpl extends AbstractDao<Integer,KilkariThematicContentWeek> implements KilkariThematicContentWeekDao {

    @Override
    public Map<String,KilkariThematicContent> getKilkariThematicContentReportData(Integer locationId, String locationType, Date date){

        KilkariThematicContent kilkariThematicContent;
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date", date)
                //  Restrictions.eq("messageWeekNumber", week_id)
        ));

        List<KilkariThematicContentWeek> result = (List<KilkariThematicContentWeek>)criteria.list();
        Double d = new Double(0.00);
        Map<String,KilkariThematicContent> resultMap =  new HashMap<>();
        if(result.isEmpty()){
            kilkariThematicContent = new KilkariThematicContent(0,date,"",(long)0,(long)0,d);
            resultMap.put("",kilkariThematicContent);
            return resultMap;
        }
        for(int i=0;i<72;i++){
            KilkariThematicContentWeek kilkariThematicContentWeek= new KilkariThematicContentWeek();
            KilkariThematicContent kilkariThematicContent1 = new KilkariThematicContent();

            if(i<result.size()) {
                kilkariThematicContentWeek = result.get(i);
                kilkariThematicContent1.setId(kilkariThematicContentWeek.getId());
                kilkariThematicContent1.setDate(kilkariThematicContentWeek.getDate());
                kilkariThematicContent1.setLocationId(kilkariThematicContentWeek.getLocationId());
                kilkariThematicContent1.setLocationType(kilkariThematicContentWeek.getLocationType());
                kilkariThematicContent1.setMessageWeekNumber(kilkariThematicContentWeek.getMessageWeekNumber());
                kilkariThematicContent1.setCallsAnswered(kilkariThematicContentWeek.getCallsAnswered() == null ? 0 : kilkariThematicContentWeek.getCallsAnswered());
                kilkariThematicContent1.setMinutesConsumed(kilkariThematicContentWeek.getMinutesConsumed() == null ? d : kilkariThematicContentWeek.getMinutesConsumed());
                kilkariThematicContent1.setUniqueBeneficiariesCalled(kilkariThematicContentWeek.getUniqueBeneficiariesCalled() == null ? 0 : kilkariThematicContentWeek.getUniqueBeneficiariesCalled());
            }
            resultMap.put(kilkariThematicContentWeek.getMessageWeekNumber(),kilkariThematicContent1);
        }
        return resultMap;
    }
}
