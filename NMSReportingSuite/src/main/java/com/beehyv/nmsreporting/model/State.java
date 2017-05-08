package com.beehyv.nmsreporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="dim_state")
public class State {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name="state_name")
    private String stateName;


    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="stateOfDistrict")
    private Set<District> districts = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="stateOfBlock")
    private Set<Block> blocks = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="stateOfTaluka")
    private Set<Taluka> talukas = new HashSet<>();

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public Set<Taluka> getTalukas() {
        return talukas;
    }

    public void setTalukas(Set<Taluka> talukas) {
        this.talukas = talukas;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
}
