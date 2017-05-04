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
@Table(name="USER_BLOCK")
public class Block {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="block_id", columnDefinition = "INT")
    private Integer blockId;

    @Column(name="block_name")
    private String blockName;

    @Column(name="last_modified_date", columnDefinition = "TIMESTAMP")
    private Date lastmodifiesDate;

    @ManyToOne
    @JoinColumn(name="taluka_id", columnDefinition = "SMALLINT")
    private Taluka talukaOfBlock;


    @ManyToOne
    @JoinColumn(name="district_id", columnDefinition = "SMALLINT")
    private District districtOfBlock;

    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "TINYINT")
    private State stateOfBlock;





    public Date getLastmodifiesDate() {
        return lastmodifiesDate;
    }

    public void setLastmodifiesDate(Date lastmodifiesDate) {
        this.lastmodifiesDate = lastmodifiesDate;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Taluka getTalukaOfBlock() {
        return talukaOfBlock;
    }

    public void setTalukaOfBlock(Taluka talukaOfBlock) {
        this.talukaOfBlock = talukaOfBlock;
    }

    public District getDistrictOfBlock() {
        return districtOfBlock;
    }

    public void setDistrictOfBlock(District districtOfBlock) {
        this.districtOfBlock = districtOfBlock;
    }

    public State getStateOfBlock() {
        return stateOfBlock;
    }

    public void setStateOfBlock(State stateOfBlock) {
        this.stateOfBlock = stateOfBlock;
    }
}
