package com.beehyv.nmsreporting.entity;

import com.beehyv.nmsreporting.model.MACourseCompletion;

public class CourseCompletionDTO extends MACourseCompletion{

    private Long mobileNumber;
    private Long languageId;

    public CourseCompletionDTO() {
    }

    public CourseCompletionDTO(MACourseCompletion macc, Long mobileNumber, Long languageId) {
        super();
        this.setId(macc.getId());
        this.setFlwId(macc.getFlwId());
        this.setScore(macc.getScore());
        this.setPassed(macc.getPassed());
        this.setChapterWiseScore(macc.getChapterWiseScore());
        this.setLastDeliveryStatus(macc.getLastDeliveryStatus());
        this.setSentNotification(macc.getSentNotification());
        this.setLastModifiedDate(macc.getLastModifiedDate());
        this.setScheduleMessageSent(macc.getScheduleMessageSent());
        this.setMobileNumber(mobileNumber);
        this.setLanguageId(languageId);
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }
}
