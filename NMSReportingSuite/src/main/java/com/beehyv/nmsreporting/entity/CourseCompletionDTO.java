package com.beehyv.nmsreporting.entity;

import com.beehyv.nmsreporting.model.MACourseCompletion;

import java.util.Date;

public class CourseCompletionDTO extends MACourseCompletion{

    private Long mobileNumber;
    private String languageId;

    public CourseCompletionDTO() {
    }

    public CourseCompletionDTO(MACourseCompletion macc, Long mobileNumber, String languageId) {
        super();
        this.setId(macc.getId());
        this.setFlwId(macc.getFlwId());
        this.setScore(macc.getScore());
        this.setPassed(macc.getPassed());
        this.setChapterWiseScore(macc.getChapterWiseScore());
        this.setLastDeliveryStatus(macc.getLastDeliveryStatus());
        this.setSentNotification(macc.getSentNotification());
        this.setLastModifiedDate(macc.getLastModifiedDate());
        this.setMobileNumber(mobileNumber);
        this.setLanguageId(languageId);
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
