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
@Table(name="USER_DISTRICT")
public class District {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtId;

    @Column(name="district_name", columnDefinition = "VARCHAR")
    private String districtName;

    @Column(name="last_modified_date", columnDefinition = "TIMESTAMP")
    private Date lastmodifiesDate;


    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER, mappedBy="districtOfBlock")
    private Set<Block> blocks = new HashSet<>();

    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER, mappedBy="districtOfTaluka")
    private Set<Taluka> talukas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "TINYINT")
    private State stateOfDistrict;

    public Set<Taluka> getTalukas() {
        return talukas;
    }

    public void setTalukas(Set<Taluka> talukas) {
        this.talukas = talukas;
    }

    public Date getLastmodifiesDate() {
        return lastmodifiesDate;
    }

    public void setLastmodifiesDate(Date lastmodifiesDate) {
        this.lastmodifiesDate = lastmodifiesDate;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public State getStateOfDistrict() {
        return stateOfDistrict;
    }

    public void setStateOfDistrict(State stateOfDistrict) {
        this.stateOfDistrict = stateOfDistrict;
    }
}
