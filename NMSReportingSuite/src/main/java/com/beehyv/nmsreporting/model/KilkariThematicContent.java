package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */

@Entity
@Table(name="kilkari_thematic_content")
public class KilkariThematicContent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;


    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;


    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="theme", columnDefinition = "VARCHAR(90)")
    private String theme;

    @Column(name="message_week_number", columnDefinition = "VARCHAR(45)")
    private String messageWeekNumber;

    @Column(name="unique_beneficiaries_called", columnDefinition = "BIGINT(20)")
    private Long uniqueBeneficiariesCalled;

    @Column(name="calls_answered", columnDefinition = "BIGINT(20)")
    private Long callsAnswered;

    @Column(name="minutes_consumed", columnDefinition = "BIGINT(20)")
    private BigDecimal minutesConsumed;

    public KilkariThematicContent(){

    }

    public KilkariThematicContent(Integer id, Date date, String theme, String messageWeekNumber, Long uniqueBeneficiariesCalled, Long callsAnswered, BigDecimal minutesConsumed){
        this.id = id;
        this.date = date;
        this.theme = theme;
        this.messageWeekNumber = messageWeekNumber;
        this.uniqueBeneficiariesCalled = uniqueBeneficiariesCalled;
        this.callsAnswered = callsAnswered;
        this.minutesConsumed = minutesConsumed;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessageWeekNumber() {
        return messageWeekNumber;
    }

    public void setMessageWeekNumber(String messageWeekNumber) {
        this.messageWeekNumber = messageWeekNumber;
    }

    public Long getUniqueBeneficiariesCalled() {
        return uniqueBeneficiariesCalled;
    }

    public void setUniqueBeneficiariesCalled(Long uniqueBeneficiariesCalled) {
        this.uniqueBeneficiariesCalled = uniqueBeneficiariesCalled;
    }

    public Long getCallsAnswered() {
        return callsAnswered;
    }

    public void setCallsAnswered(Long callsAnswered) {
        this.callsAnswered = callsAnswered;
    }

    public BigDecimal getMinutesConsumed() {
        return minutesConsumed;
    }

    public void setMinutesConsumed(BigDecimal minutesConsumed) {
        this.minutesConsumed = minutesConsumed;
    }
}
