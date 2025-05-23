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
    private Long Id;

    @Column(name="flw_id")
    private Long flwId;

    @Column(name="score")
    private Integer score;

    @Column(name="has_passed")
    private Boolean passed;

    @Column(name="chapter_wise_score")
    private String chapterWiseScore;

    @Column(name="last_delivery_status")
    private String lastDeliveryStatus;

    @Column(name="sent_notification")
    private Boolean sentNotification;

    @Column(name="modificationdate")
    private Date lastModifiedDate;

    @Column(name = "schedule_message_sent", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean scheduleMessageSent;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
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

    public Boolean getSentNotification() { return sentNotification;}

    public void setSentNotification(Boolean sentNotification) { this.sentNotification = sentNotification; }

    public Boolean getScheduleMessageSent() {return scheduleMessageSent;}

    public void setScheduleMessageSent(Boolean scheduleMessageSent) {this.scheduleMessageSent = scheduleMessageSent;}

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MACourseCompletion{" +
                "Id=" + Id +
                ", flwId=" + flwId +
                ", score=" + score +
                ", passed=" + passed +
                ", chapterWiseScore='" + chapterWiseScore + '\'' +
                ", lastDeliveryStatus='" + lastDeliveryStatus + '\'' +
                ", sentNotification=" + sentNotification +
                ", lastModifiedDate=" + lastModifiedDate +
                ", scheduleMessageSent=" + scheduleMessageSent +
                '}';
    }
}
