package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.NoticeDao;
import com.beehyv.nmsreporting.model.Notice;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository("noticeDaoImpl")
public class NoticeDaoImpl extends AbstractDao<Integer, Notice> implements NoticeDao {

    @Override
    @Transactional
    public List<Notice> findNoticeForLast28Days() {

        Session session = getSession();
        String sql = "SELECT * FROM notices " +
                "WHERE date BETWEEN NOW() - INTERVAL 28 DAY AND NOW() order by date desc";

        // Create and configure the query
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Notice.class);

        return query.list();
    }

    @Override
    @Transactional
    public List<Notice> findByNoticeTypeAndDate(String noticeType, Date yesterdayDate, String status) {
        Session session = getSession();
        String sql = "SELECT * FROM notices WHERE noticeType = :noticeType AND status = :status AND DATE(date) = :yesterdayDate";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Notice.class);
        query.setParameter("noticeType", noticeType);
        query.setParameter("status", status);
        query.setParameter("yesterdayDate", new java.sql.Date(yesterdayDate.getTime()));

        return query.list();
    }

    @Override
    @Transactional
    public void saveAll(List<Notice> notices) {
        Session session = getSession();
        for (Notice notice : notices){
            session.persist(notice);
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Notice> findByNoticeTypeAndMessageAndYear(String noticeType, int year, String message) {

        Session session = getSession();
        String sql = "SELECT * FROM notices WHERE noticeType = :noticeType AND message = :message AND YEAR(date) = :year";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Notice.class);
        query.setParameter("noticeType", noticeType);
        query.setParameter("message", message);
        query.setParameter("year", year);

        return query.list();
    }
}
