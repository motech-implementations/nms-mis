package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 6/10/17.
 */
@Entity
@Table(name="aggregate_cumulative_beneficiary_completion_counts")
public class AggregateCumulativeBeneficiaryCompletion {

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

    @Column(name="course_completed_beneficiaries", columnDefinition = "BIGINT(20)")
    private Long completedBeneficiaries;

    @Column(name="calls_75_100", columnDefinition = "BIGINT(20)")
    private Long calls_75_100;

    @Column(name="calls_50_75", columnDefinition = "BIGINT(20)")
    private Long calls_50_75;

    @Column(name="calls_25_50", columnDefinition = "BIGINT(20)")
    private Long calls_25_50;

    @Column(name="calls_1_25", columnDefinition = "BIGINT(20)")
    private Long calls_1_25;


    public AggregateCumulativeBeneficiaryCompletion(Integer id, String locationType, Long locationId, Date date, Long completedBeneficiaries, Long calls_75_100, Long calls_50_75, Long calls_25_50, Long calls_1_25) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.completedBeneficiaries = completedBeneficiaries;
        this.calls_75_100 = calls_75_100;
        this.calls_50_75 = calls_50_75;
        this.calls_25_50 = calls_25_50;
        this.calls_1_25 = calls_1_25;
    }

    public AggregateCumulativeBeneficiaryCompletion(){

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

    public Long getCompletedBeneficiaries() {
        return completedBeneficiaries;
    }

    public void setCompletedBeneficiaries(Long completedBeneficiaries) {
        this.completedBeneficiaries = completedBeneficiaries;
    }

    public Long getCalls_75_100() {
        return calls_75_100;
    }

    public void setCalls_75_100(Long calls_75_100) {
        this.calls_75_100 = calls_75_100;
    }

    public Long getCalls_50_75() {
        return calls_50_75;
    }

    public void setCalls_50_75(Long calls_50_75) {
        this.calls_50_75 = calls_50_75;
    }

    public Long getCalls_25_50() {
        return calls_25_50;
    }

    public void setCalls_25_50(Long calls_25_50) {
        this.calls_25_50 = calls_25_50;
    }

    public Long getCalls_1_25() {
        return calls_1_25;
    }

    public void setCalls_1_25(Long calls_1_25) {
        this.calls_1_25 = calls_1_25;
    }
}
