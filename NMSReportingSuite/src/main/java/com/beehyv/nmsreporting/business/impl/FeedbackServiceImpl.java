package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.FeedbackService;
import com.beehyv.nmsreporting.dao.FeedbackDao;
import com.beehyv.nmsreporting.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("FeedbackService")
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public  void saveFeedback(Feedback feedback){
        feedbackDao.saveFeedback(feedback);
    }
    @Override
    public Feedback findByFeedbackId(Integer feedbackId){
        return feedbackDao.findByFeedbackId(feedbackId);
    }

}
