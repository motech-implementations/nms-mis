package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Feedback;

public interface FeedbackDao {

    void saveFeedback(Feedback feedback);
    Feedback findByFeedbackId(Integer feedbackId);
}
