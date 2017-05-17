package com.beehyv.nmsreporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="dim_taluka")
public class Taluka {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "SMALLINT")
    private Integer talukaId;

    @Column(name="taluka_name")
    private String talukaName;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER, mappedBy="talukaOfBlock")
    private Set<Block> blocks = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "TINYINT")
    private State stateOfTaluka;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="district_id", columnDefinition = "SMALLINT")
    private District districtOfTaluka;

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

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }


    public State getStateOfTaluka() {
        return stateOfTaluka;
    }

    public void setStateOfTaluka(State stateOfTaluka) {
        this.stateOfTaluka = stateOfTaluka;
    }

    public District getDistrictOfTaluka() {
        return districtOfTaluka;
    }

    public void setDistrictOfTaluka(District districtOfTaluka) {
        this.districtOfTaluka = districtOfTaluka;
    }
}
