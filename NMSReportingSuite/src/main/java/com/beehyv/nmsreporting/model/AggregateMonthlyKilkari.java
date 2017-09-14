package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="aggregate_monthly_kilkari_counts")
public class AggregateMonthlyKilkari {

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

    @Column(name="answered_5", columnDefinition = "INT(11)")
    private Integer answered_5; //5.3.10

    @Column(name="answered_4", columnDefinition = "INT(11)")
    private Integer answered_4; //5.3.10

    @Column(name="answered_3", columnDefinition = "INT(11)")
    private Integer answered_3; //5.3.10

    @Column(name="answered_2", columnDefinition = "INT(11)")
    private Integer answered_2; //5.3.10

    @Column(name="answered_1", columnDefinition = "INT(11)")
    private Integer answered_1; //5.3.10

    @Column(name="answered_0", columnDefinition = "INT(11)")
    private Integer answered_0; //5.3.10

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

    public Integer getAnswered_5() {
        return answered_5;
    }

    public void setAnswered_5(Integer answered_5) {
        this.answered_5 = answered_5;
    }

    public Integer getAnswered_4() {
        return answered_4;
    }

    public void setAnswered_4(Integer answered_4) {
        this.answered_4 = answered_4;
    }

    public Integer getAnswered_3() {
        return answered_3;
    }

    public void setAnswered_3(Integer answered_3) {
        this.answered_3 = answered_3;
    }

    public Integer getAnswered_2() {
        return answered_2;
    }

    public void setAnswered_2(Integer answered_2) {
        this.answered_2 = answered_2;
    }

    public Integer getAnswered_1() {
        return answered_1;
    }

    public void setAnswered_1(Integer answered_1) {
        this.answered_1 = answered_1;
    }

    public Integer getAnswered_0() {
        return answered_0;
    }

    public void setAnswered_0(Integer answered_0) {
        this.answered_0 = answered_0;
    }
}
