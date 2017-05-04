package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 3/5/17.
 */
@Entity
@Table(name="ma_course_completion")
public class MACourseCompletion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;

    @Column(name="flw_Id")
    private Integer flwId;

    @Column(name="score")
    private Integer score;

    @Column(name="hasPassed")
    private Boolean passed;

    @Column(name="chapterWiseScore")
    private String chapterWiseScore;

    @Column(name="lastDeliveryStatus")
    private String lastDeliveryStatus;

    @Column(name="senrNotification")
    private Boolean senrNotification;

    @Column(name="lastModified")
    private Date lastModifiedDate;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getFlwId() {
        return flwId;
    }

    public void setFlwId(Integer flwId) {
        this.flwId = flwId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getChapterWiseScore() {
        return chapterWiseScore;
    }

    public void setChapterWiseScore(String chapterWiseScore) {
        this.chapterWiseScore = chapterWiseScore;
    }

    public String getLastDeliveryStatus() {
        return lastDeliveryStatus;
    }

    public void setLastDeliveryStatus(String lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
    }

    public Boolean getSenrNotification() {
        return senrNotification;
    }

    public void setSenrNotification(Boolean senrNotification) {
        this.senrNotification = senrNotification;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
