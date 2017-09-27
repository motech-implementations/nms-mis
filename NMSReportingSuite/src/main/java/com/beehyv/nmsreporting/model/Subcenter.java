package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 19/9/17.
 */
@Entity
@Table(name="dim_subcenter")
public class Subcenter {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private Integer subcenterId;

    @Column(name="subcenter_name")
    private String subcenterName;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="block_id", columnDefinition = "SMALLINT")
    private Integer blockOfSubcenter;

    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtOfSubcenter;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateOfSubcenter;

    public Integer getSubcenterId() {
        return subcenterId;
    }

    public void setSubcenterId(Integer subcenterId) {
        this.subcenterId = subcenterId;
    }

    public String getSubcenterName() {
        return subcenterName;
    }

    public void setSubcenterName(String subcenterName) {
        this.subcenterName = subcenterName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Integer getBlockOfSubcenter() {
        return blockOfSubcenter;
    }

    public void setBlockOfSubcenter(Integer blockOfSubcenter) {
        this.blockOfSubcenter = blockOfSubcenter;
    }

    public Integer getStateOfSubcenter() {
        return stateOfSubcenter;
    }

    public void setStateOfSubcenter(Integer stateOfSubcenter) {
        this.stateOfSubcenter = stateOfSubcenter;
    }

    public Integer getDistrictOfSubcenter() {
        return districtOfSubcenter;
    }

    public void setDistrictOfSubcenter(Integer districtOfSubcenter) {
        this.districtOfSubcenter = districtOfSubcenter;
    }
}
