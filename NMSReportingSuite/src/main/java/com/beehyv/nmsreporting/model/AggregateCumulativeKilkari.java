package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="aggregate_cumulative_kilkari_counts")
public class AggregateCumulativeKilkari {

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

    @Column(name="total_unique_beneficiaries", columnDefinition = "BIGINT(20)")
    private Long uniqueBeneficiaries;

    @Column(name="total_successful_calls", columnDefinition = "BIGINT(20)")
    private Long successfulCalls;

    @Column(name="total_billable_minutes", columnDefinition = "BIGINT(20)")
    private Long billableMinutes;

    //Average minutes = billableMinutes/successfulCalls


    public AggregateCumulativeKilkari(Integer id, String locationType, Long locationId, Date date, Long uniqueBeneficiaries, Long successfulCalls, Long billableMinutes) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.uniqueBeneficiaries = uniqueBeneficiaries;
        this.successfulCalls = successfulCalls;
        this.billableMinutes = billableMinutes;
    }

    public AggregateCumulativeKilkari(){

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

    public Long getUniqueBeneficiaries() {
        return uniqueBeneficiaries;
    }

    public void setUniqueBeneficiaries(Long uniqueBeneficiaries) {
        this.uniqueBeneficiaries = uniqueBeneficiaries;
    }

    public Long getSuccessfulCalls() {
        return successfulCalls;
    }

    public void setSuccessfulCalls(Long successfulCalls) {
        this.successfulCalls = successfulCalls;
    }

    public Long getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Long billableMinutes) {
        this.billableMinutes = billableMinutes;
    }
}
