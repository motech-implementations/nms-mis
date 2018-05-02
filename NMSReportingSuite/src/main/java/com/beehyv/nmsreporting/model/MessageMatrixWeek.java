package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 11/10/17.
 */
@Entity
@Table(name = "agg_kilkari_message_matrix_week")
public class MessageMatrixWeek {

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

    @Column(name="message_week_group", columnDefinition = "VARCHAR(45)")
    private String messageWeek;

    @Column(name="listened_morethan75", columnDefinition = "BIGINT(20)")
    private Long listened_morethan75;

    @Column(name="listened_50_75", columnDefinition = "BIGINT(20)")
    private Long listened_50_75;

    @Column(name="listened_25_50", columnDefinition = "BIGINT(20)")
    private Long listened_25_50;

    @Column(name="listened_lessthan25", columnDefinition = "BIGINT(20)")
    private Long listened_lessthan25;

    public MessageMatrixWeek() {
    }

    public MessageMatrixWeek(Integer id, String locationType, Long locationId, Date date, String messageWeek,
                         Long listened_morethan75, Long listened_50_75, Long listened_25_50, Long listened_lessthan25) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.messageWeek = messageWeek;
        this.listened_morethan75 = listened_morethan75;
        this.listened_50_75 = listened_50_75;
        this.listened_25_50 = listened_25_50;
        this.listened_lessthan25 = listened_lessthan25;
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

    public String getMessageWeek() {
        return messageWeek;
    }

    public void setMessageWeek(String messageWeek) {
        this.messageWeek = messageWeek;
    }

    public Long getListened_morethan75() {
        return listened_morethan75;
    }

    public void setListened_morethan75(Long listened_morethan75) {
        this.listened_morethan75 = listened_morethan75;
    }

    public Long getListened_50_75() {
        return listened_50_75;
    }

    public void setListened_50_75(Long listened_50_75) {
        this.listened_50_75 = listened_50_75;
    }

    public Long getListened_25_50() {
        return listened_25_50;
    }

    public void setListened_25_50(Long listened_25_50) {
        this.listened_25_50 = listened_25_50;
    }

    public Long getListened_lessthan25() {
        return listened_lessthan25;
    }

    public void setListened_lessthan25(Long listened_lessthan25) {
        this.listened_lessthan25 = listened_lessthan25;
    }
}
