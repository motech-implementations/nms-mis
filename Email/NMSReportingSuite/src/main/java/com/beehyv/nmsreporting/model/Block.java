package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="dim_healthblock")
public class Block {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private Integer blockId;

    @Column(name="healthblock_name")
    private String blockName;

    @Column(name="modificationdate", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="taluka_id", columnDefinition = "SMALLINT")
    private Integer talukaOfBlock;

    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtOfBlock;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateOfBlock;

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

    public Integer getTalukaOfBlock() {
        return talukaOfBlock;
    }

    public void setTalukaOfBlock(Integer talukaOfBlock) {
        this.talukaOfBlock = talukaOfBlock;
    }

    public Integer getDistrictOfBlock() {
        return districtOfBlock;
    }

    public void setDistrictOfBlock(Integer districtOfBlock) {
        this.districtOfBlock = districtOfBlock;
    }

    public Integer getStateOfBlock() {
        return stateOfBlock;
    }

    public void setStateOfBlock(Integer stateOfBlock) {
        this.stateOfBlock = stateOfBlock;
    }
}
