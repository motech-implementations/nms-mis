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

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="theme", columnDefinition = "VARCHAR(90)")
    private String theme;

    @Column(name="message_week_number", columnDefinition = "VARCHAR(45)")
    private String messageWeekNumber;

    @Column(name="calls_answered", columnDefinition = "BIGINT(20)")
    private Long callsAnswered;

    @Column(name="minutes_consumed", columnDefinition = "BIGINT(20)")
    private Long minutesConsumed;

    public KilkariThematicContent(){

    }

    public KilkariThematicContent(Integer id, Date date, String theme, String messageWeekNumber, Long callsAnswered, Long minutesConsumed){
        this.id = id;
        this.date = date;
        this.theme = theme;
        this.messageWeekNumber = messageWeekNumber;
        this.callsAnswered = callsAnswered;
        this.minutesConsumed = minutesConsumed;
    }

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

    public String getMessageWeekNumber() {
        return messageWeekNumber;
    }

    public void setMessageWeekNumber(String messageWeekNumber) {
        this.messageWeekNumber = messageWeekNumber;
    }

    public Long getCallsAnswered() {
        return callsAnswered;
    }

    public void setCallsAnswered(Long callsAnswered) {
        this.callsAnswered = callsAnswered;
    }

    public Long getMinutesConsumed() {
        return minutesConsumed;
    }

    public void setMinutesConsumed(Long minutesConsumed) {
        this.minutesConsumed = minutesConsumed;
    }
}
