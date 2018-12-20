package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FeedbackDao;
import com.beehyv.nmsreporting.model.Feedback;
import org.springframework.stereotype.Repository;

@Repository("FeedbackDao")
public class FeedbackDaoImpl extends AbstractDao<Integer, Feedback> implements FeedbackDao {

    @Override
    public void saveFeedback(Feedback feedback) {
        persist(feedback);
    }

    @Override
    public Feedback findByFeedbackId(Integer feedbackId) {
        return getByKey(feedbackId);
    }
}
