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

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="total_unique_beneficiaries", columnDefinition = "INT(11)")
    private Integer uniqueBeneficiaries; //5.3.1:3 , 5.3.2:3, 5.3.2:9

    @Column(name="total_successful_calls", columnDefinition = "BIGINT(20)")
    private Long successfulCalls;

    @Column(name="total_billable_minutes", columnDefinition = "BIGINT(20)")
    private Long billableMinutes;

    //Average minutes = billableMinutes/successfulCalls


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

    public Integer getUniqueBeneficiaries() {
        return uniqueBeneficiaries;
    }

    public void setUniqueBeneficiaries(Integer uniqueBeneficiaries) {
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
