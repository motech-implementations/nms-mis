package com.beehyv.nmsreporting.entity;
import java.math.BigDecimal;


/**
 * Created by himanshu on 06/10/17.
 */


public class KilkariThematicContentReportDto {

    private Integer id;
    private String theme;
    private String messageWeekNumber;
    private Long uniqueBeneficiariesCalled;
    private Long callsAnswered;
    private BigDecimal minutesConsumed;


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
