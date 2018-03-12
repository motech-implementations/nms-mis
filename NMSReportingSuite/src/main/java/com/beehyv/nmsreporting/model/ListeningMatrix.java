package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 10/10/17.
 */
@Entity
@Table(name="agg_kilkari_listening_matrix")
public class ListeningMatrix {

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

    @Column(name="calls_listened_percentage", columnDefinition = "BIGINT(20)")
    private String calls_listened_percentage;

    @Column(name="contentListened_morethan75", columnDefinition = "BIGINT(20)")
    private Long contentListened_morethan75;

    @Column(name="contentListened_50_75", columnDefinition = "BIGINT(20)")
    private Long contentListened_50_75;

    @Column(name="contentListened_25_50", columnDefinition = "BIGINT(20)")
    private Long contentListened_25_50;

    @Column(name="contentListened_lessthan25", columnDefinition = "BIGINT(20)")
    private Long contentListened_lessthan25;

    public ListeningMatrix() {
    }

    public ListeningMatrix(Integer id, String locationType, Long locationId, Date date, String calls_listened_percentage, Long contentListened_morethan75, Long contentListened_50_75, Long contentListened_25_50, Long contentListened_lessthan25) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.calls_listened_percentage = calls_listened_percentage;
        this.contentListened_morethan75 = contentListened_morethan75;
        this.contentListened_50_75 = contentListened_50_75;
        this.contentListened_25_50 = contentListened_25_50;
        this.contentListened_lessthan25 = contentListened_lessthan25;
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

    public String getCalls_listened_percentage() {
        return calls_listened_percentage;
    }

    public void setCalls_listened_percentage(String calls_listened_percentage) {
        this.calls_listened_percentage = calls_listened_percentage;
    }

    public Long getContentListened_morethan75() {
        return contentListened_morethan75;
    }

    public void setContentListened_morethan75(Long contentListened_morethan75) {
        this.contentListened_morethan75 = contentListened_morethan75;
    }

    public Long getContentListened_50_75() {
        return contentListened_50_75;
    }

    public void setContentListened_50_75(Long contentListened_50_75) {
        this.contentListened_50_75 = contentListened_50_75;
    }

    public Long getContentListened_25_50() {
        return contentListened_25_50;
    }

    public void setContentListened_25_50(Long contentListened_25_50) {
        this.contentListened_25_50 = contentListened_25_50;
    }

    public Long getContentListened_lessthan25() {
        return contentListened_lessthan25;
    }

    public void setContentListened_lessthan25(Long contentListened_lessthan25) {
        this.contentListened_lessthan25 = contentListened_lessthan25;
    }
}
