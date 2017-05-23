package com.beehyv.nmsreporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Entity
@Table(name="dim_healthsubfacility")
public class HealthSubFacility {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private Integer healthSubFacilityId;

    @Column(name="healthsubFacility_name")
    private String healthSubFacilityName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="healthFacility_id", columnDefinition = "INT")
    private HealthFacility healthFacilityOfHealthSubFacility;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    public Integer getHealthSubFacilityId() {
        return healthSubFacilityId;
    }

    public void setHealthSubFacilityId(Integer healthSubFacilityId) {
        this.healthSubFacilityId = healthSubFacilityId;
    }

    public String getHealthSubFacilityName() {
        return healthSubFacilityName;
    }

    public void setHealthSubFacilityName(String healthSubFacilityName) {
        this.healthSubFacilityName = healthSubFacilityName;
    }

    public HealthFacility getHealthFacilityOfHealthSubFacility() {
        return healthFacilityOfHealthSubFacility;
    }

    public void setHealthFacilityOfHealthSubFacility(HealthFacility healthFacilityOfHealthSubFacility) {
        this.healthFacilityOfHealthSubFacility = healthFacilityOfHealthSubFacility;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
