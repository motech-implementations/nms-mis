package com.beehyv.nmsreporting.model;

import javax.persistence.*;
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

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="theme", columnDefinition = "VARCHAR(45)")
    private String theme;

    @Column(name="message_week_number", columnDefinition = "INT(11)")
    private Integer messageWeekNumber;

    @Column(name="calls_answered", columnDefinition = "INT(11)")
    private Integer callsAnswered;

    @Column(name="minutes_consumed", columnDefinition = "INT(11)")
    private Integer minutesConsumed;


    public Integer getId() {
        return id;
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

    public Integer getMessageWeekNumber() {
        return messageWeekNumber;
    }

    public void setMessageWeekNumber(Integer messageWeekNumber) {
        this.messageWeekNumber = messageWeekNumber;
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
