package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Feedback;

public interface FeedbackService {
    Feedback findByFeedbackId(Integer feedbackId);
    void saveFeedback(Feedback feedback);
}
