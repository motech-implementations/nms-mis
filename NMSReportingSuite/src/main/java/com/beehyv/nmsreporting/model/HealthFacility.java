package com.beehyv.nmsreporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Entity
@Table(name="dim_healthfacility")
public class HealthFacility {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private Integer healthFacilityId;

    @Column(name="healthfacility_name")
    private String healthFacilityName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="healthBlock_id", columnDefinition = "INT")
    private Block blockOfHealthFacility;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    public Integer getHealthFacilityId() {
        return healthFacilityId;
    }

    public void setHealthFacilityId(Integer healthFacilityId) {
        this.healthFacilityId = healthFacilityId;
    }

    public String getHealthFacilityName() {
        return healthFacilityName;
    }

    public void setHealthFacilityName(String healthFacilityName) {
        this.healthFacilityName = healthFacilityName;
    }

    public Block getBlockOfHealthFacility() {
        return blockOfHealthFacility;
    }

    public void setBlockOfHealthFacility(Block blockOfHealthFacility) {
        this.blockOfHealthFacility = blockOfHealthFacility;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}