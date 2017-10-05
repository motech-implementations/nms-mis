package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="aggregate_daily_MA_counts")
public class AggregateDailyMA {

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

    @Column(name="ashas_started", columnDefinition = "INT(11)")
    private Integer ashasStarted;

    @Column(name="ashas_completed", columnDefinition = "INT(11)")
    private Integer ashasCompleted; //5.2.3:7 , 5.2.2:6

    @Column(name="ashas_failed", columnDefinition = "INT(11)")
    private Integer ashasFailed;

    @Column(name="ashas_recieved", columnDefinition = "INT(11)")
    private Integer ashasRecieved;

    @Column(name="ashas_rejected", columnDefinition = "INT(11)")
    private Integer ashasRejected;

    //ashasAdded = ashasRecieved - ashasRejected


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

    public Integer getAshasStarted() {
        return ashasStarted;
    }

    public void setAshasStarted(Integer ashasStarted) {
        this.ashasStarted = ashasStarted;
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

    public Integer getAshasRecieved() {
        return ashasRecieved;
    }

    public void setAshasRecieved(Integer ashasRecieved) {
        this.ashasRecieved = ashasRecieved;
    }

    public Integer getAshasRejected() {
        return ashasRejected;
    }

    public void setAshasRejected(Integer ashasRejected) {
        this.ashasRejected = ashasRejected;
    }
}
