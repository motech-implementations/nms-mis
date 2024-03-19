package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.WhatsAppSubscribersReportDao;
import com.beehyv.nmsreporting.model.WhatsAppSubscribers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("whatsAppSubscriberReportDao")
public class WhatsAppSubscriberReportDaoImpl extends AbstractDao<Integer, WhatsAppSubscribers> implements WhatsAppSubscribersReportDao {
    @Override
    public WhatsAppSubscribers getWhatsAppSubscriberCounts(Integer locationId, String locationType, Date toDate, String periodType){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));

        WhatsAppSubscribers whatsAppSubscribers = (WhatsAppSubscribers)criteria.uniqueResult();

        if(whatsAppSubscribers == null){
            return new WhatsAppSubscribers(0,locationType,locationId.longValue(),toDate,0,0,0,0,0,0,0,"");
        }

        handleNull(whatsAppSubscribers);

        return whatsAppSubscribers;
    }

    void handleNull(WhatsAppSubscribers whatsAppSubscribers){
        if(whatsAppSubscribers.getActiveWhatsAppSubscribers() == null){
            whatsAppSubscribers.setActiveWhatsAppSubscribers(0);
        }
        if(whatsAppSubscribers.getNewWhatsAppOptIns() == null) {
            whatsAppSubscribers.setNewWhatsAppOptIns(0);
        }
        if(whatsAppSubscribers.getNewWhatsAppSubscribers() == null) {
            whatsAppSubscribers.setNewWhatsAppSubscribers(0);
        }
        if(whatsAppSubscribers.getSelfWhatsAppDeactivatedSubscriber() == null){
            whatsAppSubscribers.setSelfWhatsAppDeactivatedSubscriber(0);
        }
        if(whatsAppSubscribers.getDeliveryFailureWhatsAppDeactivatedSubscriber() == null){
            whatsAppSubscribers.setDeliveryFailureWhatsAppDeactivatedSubscriber(0);
        }
        if(whatsAppSubscribers.getMotherPackCompletedSubscribers() == null){
            whatsAppSubscribers.setMotherPackCompletedSubscribers(0);
        }
        if(whatsAppSubscribers.getChildPackCompletedSubscribers() == null){
            whatsAppSubscribers.setChildPackCompletedSubscribers(0);
        }

    }
}
