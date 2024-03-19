package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.WhatsAppReportsDao;
import com.beehyv.nmsreporting.model.WhatsAppMessages;
import com.beehyv.nmsreporting.model.WhatsAppReports;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("whatsAppReportDao")
public class WhatsAppReportDaoImpl extends AbstractDao<Integer, WhatsAppReports> implements WhatsAppReportsDao {
    @Override
    public WhatsAppReports getWhatsAppReportCounts(Integer locationId, String locationType, Date toDate, String periodType){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));

        WhatsAppReports report = (WhatsAppReports) criteria.uniqueResult();

        if(report == null){
            return new WhatsAppReports(0,locationType,locationId.longValue(),toDate,0,0,0,0,0,0,0,0,0,0,"");
        }

        handleNull(report);

        return report;
    }

    void handleNull(WhatsAppReports report) {
        if (report.getWelcomeCallSuccessfulCalls() == null) {
            report.setWelcomeCallSuccessfulCalls(0);
        }
        if (report.getWelcomeCallCallsAnswered() == null) {
            report.setWelcomeCallCallsAnswered(0);
        }
        if (report.getWelcomeCallEnteredAnOption() == null) {
            report.setWelcomeCallEnteredAnOption(0);
        }
        if (report.getWelcomeCallProvidedOptIn() == null) {
            report.setWelcomeCallProvidedOptIn(0);
        }
        if (report.getWelcomeCallProvidedOptOut() == null) {
            report.setWelcomeCallProvidedOptOut(0);
        }
        if (report.getOptInSuccessfulCalls() == null) {
            report.setOptInSuccessfulCalls(0);
        }
        if (report.getOptInCallsAnswered() == null) {
            report.setOptInCallsAnswered(0);
        }
        if (report.getOptInEnteredAnOption() == null) {
            report.setOptInEnteredAnOption(0);
        }
        if (report.getOptInProvidedOptIn() == null) {
            report.setOptInProvidedOptIn(0);
        }
        if (report.getOptInProvidedOptOut() == null) {
            report.setOptInProvidedOptOut(0);
        }

    }
}
