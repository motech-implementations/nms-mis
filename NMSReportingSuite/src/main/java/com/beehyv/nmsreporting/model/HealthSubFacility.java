package com.beehyv.nmsreporting.model;

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

    @Column(name="healthFacility_id", columnDefinition = "INT")
    private Integer healthFacilityOfHealthSubFacility;

    @Column(name="modificationdate", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="block_id", columnDefinition = "INT")
    private Integer blockOfhealthSubFacility;

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

    public Integer getHealthFacilityOfHealthSubFacility() {
        return healthFacilityOfHealthSubFacility;
    }

    public void setHealthFacilityOfHealthSubFacility(Integer healthFacilityOfHealthSubFacility) {
        this.healthFacilityOfHealthSubFacility = healthFacilityOfHealthSubFacility;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getBlockOfhealthSubFacility() {
        return blockOfhealthSubFacility;
    }

    public void setBlockOfhealthSubFacility(Integer blockOfhealthSubFacility) {
        this.blockOfhealthSubFacility = blockOfhealthSubFacility;
    }
}
