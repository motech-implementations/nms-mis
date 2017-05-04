package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="USER_TALUKA")
public class Taluka {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="taluka_id", columnDefinition = "TINYINT")
    private Integer talukaId;

    @Column(name="taluka_name")
    private String talukaName;

    @Column(name="last_modified_date", columnDefinition = "TIMESTAMP")
    private Date lastmodifiesDate;


    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER, mappedBy="talukaOfBlock")
    private Set<Block> blocks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "SMALLINT")
    private State stateOfTaluka;

    @ManyToOne
    @JoinColumn(name="district_id", columnDefinition = "SMALLINT")
    private District districtOfTaluka;

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

    public Date getLastmodifiesDate() {
        return lastmodifiesDate;
    }

    public void setLastmodifiesDate(Date lastmodifiesDate) {
        this.lastmodifiesDate = lastmodifiesDate;
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
