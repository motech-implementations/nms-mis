package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.WhatsAppMessagesReportDao;
import com.beehyv.nmsreporting.model.WhatsAppMessages;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("whatsAppMessagesReportDao")
public class WhatsAppMessagesReportDaoImpl extends AbstractDao<Integer, WhatsAppMessages> implements WhatsAppMessagesReportDao {

    @Override
    public WhatsAppMessages getWhatsAppMessageCounts(Integer locationId, String locationType, Date toDate, String periodType){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));

        WhatsAppMessages whatsAppMessages = (WhatsAppMessages)criteria.uniqueResult();

        if(whatsAppMessages == null){
            return new WhatsAppMessages(0,locationType,locationId.longValue(),toDate,0,0,0,0,"");
        }

        handleNull(whatsAppMessages);

        return whatsAppMessages;
    }

    void handleNull(WhatsAppMessages whatsAppMessages){
        if(whatsAppMessages.getTotalSubscribers() == null){
            whatsAppMessages.setTotalSubscribers(0);
        }
        if(whatsAppMessages.getCoreMessagesSent() == null){
            whatsAppMessages.setCoreMessagesSent(0);
        }
        if(whatsAppMessages.getCoreMessagesDelivered() == null){
            whatsAppMessages.setCoreMessagesDelivered(0);
        }
        if(whatsAppMessages.getCoreMessagesRead() == null){
            whatsAppMessages.setCoreMessagesRead(0);
        }

    }
}
