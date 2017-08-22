package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Entity
@Table(name="dim_village")
public class Village {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private Integer villageId;

    @Column(name="village_name")
    private String villageName;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="hblock_id", columnDefinition = "INT")
    private Integer blockOfVillage;

    @Column(name="taluka_id", columnDefinition = "SMALLINT")
    private Integer talukaOfVillage;

    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtOfVillage;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateOfVillage;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getBlockOfVillage() {
        return blockOfVillage;
    }

    public void setBlockOfVillage(Integer blockOfVillage) {
        this.blockOfVillage = blockOfVillage;
    }

    public Integer getTalukaOfVillage() {
        return talukaOfVillage;
    }

    public void setTalukaOfVillage(Integer talukaOfVillage) {
        this.talukaOfVillage = talukaOfVillage;
    }

    public Integer getDistrictOfVillage() {
        return districtOfVillage;
    }

    public void setDistrictOfVillage(Integer districtOfVillage) {
        this.districtOfVillage = districtOfVillage;
    }

    public Integer getStateOfVillage() {
        return stateOfVillage;
    }

    public void setStateOfVillage(Integer stateOfVillage) {
        this.stateOfVillage = stateOfVillage;
    }
}
