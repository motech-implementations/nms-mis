package com.beehyv.nmsreporting.entity;


/**
 * Created by himanshu on 06/10/17.
 */


public class KilkariThematicContentReportDto {

    private Integer id;
    private String theme;
    private String messageWeekNumber;
    private Integer uniqueBeneficiariesCalled;
    private Integer callsAnswered;
    private Integer minutesConsumed;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUniqueBeneficiariesCalled() {
        return uniqueBeneficiariesCalled;
    }

    public void setUniqueBeneficiariesCalled(Integer uniqueBeneficiariesCalled) {
        this.uniqueBeneficiariesCalled = uniqueBeneficiariesCalled;
    }

    public Integer getCallsAnswered() {
        return callsAnswered;
    }

    public void setCallsAnswered(Integer callsAnswered) {
        this.callsAnswered = callsAnswered;
    }

    public Integer getMinutesConsumed() {
        return minutesConsumed;
    }

    public void setMinutesConsumed(Integer minutesConsumed) {
        this.minutesConsumed = minutesConsumed;
    }
}
