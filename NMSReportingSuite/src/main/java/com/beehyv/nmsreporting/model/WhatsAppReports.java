package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agg_whatsapp_reports")
public class WhatsAppReports {
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

    @Column(name="welcome_call_successful_calls", columnDefinition = "INT(11)")
    private Integer welcomeCallSuccessfulCalls;

    @Column(name = "welcome_call_calls_answered", columnDefinition = "INT(11)")
    private Integer welcomeCallCallsAnswered;

    @Column(name = "welcome_call_entered_an_option", columnDefinition = "INT(11)")
    private Integer welcomeCallEnteredAnOption;

    @Column(name = "welcome_call_provided_opt_in", columnDefinition = "INT(11)")
    private Integer welcomeCallProvidedOptIn;

    @Column(name = "welcome_call_provided_opt_out", columnDefinition = "INT(11)")
    private Integer welcomeCallProvidedOptOut;

    @Column(name="opt_in_successful_calls", columnDefinition = "INT(11)")
    private Integer optInSuccessfulCalls;

    @Column(name = "opt_in_calls_answered", columnDefinition = "INT(11)")
    private Integer optInCallsAnswered;

    @Column(name = "opt_in_entered_an_option", columnDefinition = "INT(11)")
    private Integer optInEnteredAnOption;

    @Column(name = "opt_in_provided_opt_in", columnDefinition = "INT(11)")
    private Integer optInProvidedOptIn;

    @Column(name = "opt_in_provided_opt_out", columnDefinition = "INT(11)")
    private Integer optInProvidedOptOut;
    @Column(name="period_type", columnDefinition = "VARCHAR(45)")
    private String periodType;

    public WhatsAppReports() {
    }

    public WhatsAppReports(Integer id, String locationType, Long locationId, Date date, Integer welcomeCallSuccessfulCalls, Integer welcomeCallCallsAnswered, Integer welcomeCallEnteredAnOption, Integer welcomeCallProvidedOptIn, Integer welcomeCallProvidedOptOut, Integer optInSuccessfulCalls, Integer optInCallsAnswered, Integer optInEnteredAnOption, Integer optInProvidedOptIn, Integer optInProvidedOptOut, String periodType) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.welcomeCallSuccessfulCalls = welcomeCallSuccessfulCalls;
        this.welcomeCallCallsAnswered = welcomeCallCallsAnswered;
        this.welcomeCallEnteredAnOption = welcomeCallEnteredAnOption;
        this.welcomeCallProvidedOptIn = welcomeCallProvidedOptIn;
        this.welcomeCallProvidedOptOut = welcomeCallProvidedOptOut;
        this.optInSuccessfulCalls = optInSuccessfulCalls;
        this.optInCallsAnswered = optInCallsAnswered;
        this.optInEnteredAnOption = optInEnteredAnOption;
        this.optInProvidedOptIn = optInProvidedOptIn;
        this.optInProvidedOptOut = optInProvidedOptOut;
        this.periodType = periodType;
    }

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

    public Integer getWelcomeCallSuccessfulCalls() {
        return welcomeCallSuccessfulCalls;
    }

    public void setWelcomeCallSuccessfulCalls(Integer welcomeCallSuccessfulCalls) {
        this.welcomeCallSuccessfulCalls = welcomeCallSuccessfulCalls;
    }

    public Integer getWelcomeCallCallsAnswered() {
        return welcomeCallCallsAnswered;
    }

    public void setWelcomeCallCallsAnswered(Integer welcomeCallCallsAnswered) {
        this.welcomeCallCallsAnswered = welcomeCallCallsAnswered;
    }

    public Integer getWelcomeCallEnteredAnOption() {
        return welcomeCallEnteredAnOption;
    }

    public void setWelcomeCallEnteredAnOption(Integer welcomeCallEnteredAnOption) {
        this.welcomeCallEnteredAnOption = welcomeCallEnteredAnOption;
    }

    public Integer getWelcomeCallProvidedOptIn() {
        return welcomeCallProvidedOptIn;
    }

    public void setWelcomeCallProvidedOptIn(Integer welcomeCallProvidedOptIn) {
        this.welcomeCallProvidedOptIn = welcomeCallProvidedOptIn;
    }

    public Integer getWelcomeCallProvidedOptOut() {
        return welcomeCallProvidedOptOut;
    }

    public void setWelcomeCallProvidedOptOut(Integer welcomeCallProvidedOptOut) {
        this.welcomeCallProvidedOptOut = welcomeCallProvidedOptOut;
    }

    public Integer getOptInSuccessfulCalls() {
        return optInSuccessfulCalls;
    }

    public void setOptInSuccessfulCalls(Integer optInSuccessfulCalls) {
        this.optInSuccessfulCalls = optInSuccessfulCalls;
    }

    public Integer getOptInCallsAnswered() {
        return optInCallsAnswered;
    }

    public void setOptInCallsAnswered(Integer optInCallsAnswered) {
        this.optInCallsAnswered = optInCallsAnswered;
    }

    public Integer getOptInEnteredAnOption() {
        return optInEnteredAnOption;
    }

    public void setOptInEnteredAnOption(Integer optInEnteredAnOption) {
        this.optInEnteredAnOption = optInEnteredAnOption;
    }

    public Integer getOptInProvidedOptIn() {
        return optInProvidedOptIn;
    }

    public void setOptInProvidedOptIn(Integer optInProvidedOptIn) {
        this.optInProvidedOptIn = optInProvidedOptIn;
    }

    public Integer getOptInProvidedOptOut() {
        return optInProvidedOptOut;
    }

    public void setOptInProvidedOptOut(Integer optInProvidedOptOut) {
        this.optInProvidedOptOut = optInProvidedOptOut;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
