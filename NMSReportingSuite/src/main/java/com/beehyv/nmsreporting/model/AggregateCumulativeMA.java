package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="agg_MobileAcademy_Reports")
public class AggregateCumulativeMA {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="ashas_registered", columnDefinition = "INT(11)")
    private Integer ashasRegistered;

    @Column(name="ashas_started_course", columnDefinition = "INT(11)")
    private Integer ashasStarted; //sum of ashasStarted from daily

    @Column(name="ashas_not_started_course", columnDefinition = "INT(11)")
    private Integer ashasNotStarted;

    @Column(name="ashas_completed_successfully", columnDefinition = "INT(11)")
    private Integer ashasCompleted; //sum of ashasCompleted from daily

    @Column(name="ashas_failed_course", columnDefinition = "INT(11)")
    private Integer ashasFailed; //sum of ashasFailed from daily

    @Column(name="ashas_rejected", columnDefinition = "INT(11)")
    private Integer ashasRejected;

    public AggregateCumulativeMA(Integer id, String locationType, Long locationId, Date date, Integer ashasRegistered, Integer ashasStarted, Integer ashasNotStarted, Integer ashasCompleted, Integer ashasFailed, Integer ashasRejected) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.ashasRegistered = ashasRegistered;
        this.ashasStarted = ashasStarted;
        this.ashasNotStarted = ashasNotStarted;
        this.ashasCompleted = ashasCompleted;
        this.ashasFailed = ashasFailed;
        this.ashasRejected = ashasRejected;
    }

    public AggregateCumulativeMA(){

    }

    public Integer getAshasRejected() {
        return ashasRejected;
    }

    public void setAshasRejected(Integer ashasRejected) {
        this.ashasRejected = ashasRejected;
    }
//Registered but not completed = ashasRegistered - ashasCompleted - ashasFailed ====> 5.2.3:3, 5.2.3:8


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAshasRegistered() {
        return ashasRegistered;
    }

    public void setAshasRegistered(Integer ashasRegistered) {
        this.ashasRegistered = ashasRegistered;
    }

    public Integer getAshasStarted() {
        return ashasStarted;
    }

    public void setAshasStarted(Integer ashasStarted) {
        this.ashasStarted = ashasStarted;
    }

    public Integer getAshasNotStarted() {
        return ashasNotStarted;
    }

    public void setAshasNotStarted(Integer ashasNotStarted) {
        this.ashasNotStarted = ashasNotStarted;
    }

    public Integer getAshasCompleted() {
        return ashasCompleted;
    }

    public void setAshasCompleted(Integer ashasCompleted) {
        this.ashasCompleted = ashasCompleted;
    }

    public Integer getAshasFailed() {
        return ashasFailed;
    }

    public void setAshasFailed(Integer ashasFailed) {
        this.ashasFailed = ashasFailed;
    }
}
