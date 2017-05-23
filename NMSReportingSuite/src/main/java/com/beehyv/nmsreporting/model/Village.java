package com.beehyv.nmsreporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="hblock_id", columnDefinition = "INT")
    private Block blockOfVillage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="taluka_id", columnDefinition = "SMALLINT")
    private Taluka talukaOfVillage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="district_id", columnDefinition = "SMALLINT")
    private District districtOfVillage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "TINYINT")
    private State stateOfVillage;

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

    public Block getBlockOfVillage() {
        return blockOfVillage;
    }

    public void setBlockOfVillage(Block blockOfVillage) {
        this.blockOfVillage = blockOfVillage;
    }

    public Taluka getTalukaOfVillage() {
        return talukaOfVillage;
    }

    public void setTalukaOfVillage(Taluka talukaOfVillage) {
        this.talukaOfVillage = talukaOfVillage;
    }

    public District getDistrictOfVillage() {
        return districtOfVillage;
    }

    public void setDistrictOfVillage(District districtOfVillage) {
        this.districtOfVillage = districtOfVillage;
    }

    public State getStateOfVillage() {
        return stateOfVillage;
    }

    public void setStateOfVillage(State stateOfVillage) {
        this.stateOfVillage = stateOfVillage;
    }
}
