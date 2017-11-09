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

    @Column(name="location_id", columnDefinition = "INT(11)")
    private Integer locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="total_unique_beneficiaries", columnDefinition = "INT(11)")
    private Integer uniqueBeneficiaries;

    @Column(name="total_successful_calls", columnDefinition = "INT(11)")
    private Integer successfulCalls;

    @Column(name="total_billable_minutes", columnDefinition = "INT(11)")
    private Integer billableMinutes;

    //Average minutes = billableMinutes/successfulCalls


    public AggregateCumulativeKilkari(Integer id, String locationType, Integer locationId, Date date, Integer uniqueBeneficiaries, Integer successfulCalls, Integer billableMinutes) {
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUniqueBeneficiaries() {
        return uniqueBeneficiaries;
    }

    public void setUniqueBeneficiaries(Integer uniqueBeneficiaries) {
        this.uniqueBeneficiaries = uniqueBeneficiaries;
    }

    public Integer getSuccessfulCalls() {
        return successfulCalls;
    }

    public void setSuccessfulCalls(Integer successfulCalls) {
        this.successfulCalls = successfulCalls;
    }

    public Integer getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Integer billableMinutes) {
        this.billableMinutes = billableMinutes;
    }
}
