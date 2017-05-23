package com.beehyv.nmsreporting.model;

import javax.persistence.*;

/**
 * Created by beehyv on 14/5/17.
 */
@Entity
@Table(name="kilkari_low_usage")
public class KilkariLowUsage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer kilkariId;

    @Column(name="month_id", columnDefinition = "SMALLINT(6)")
    private Integer monthId;

    @Column(name="mcts_id", columnDefinition = "BIGINT(20)")
    private Long mctsId;

    @Column(name="name", columnDefinition = "VARCHAR(100)")
    private String name;

    @Column(name="msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="age_on_service", columnDefinition = "TINYINT(4)")
    private Integer ageOnService;

    @Column(name="calls_answered", columnDefinition = "INT(11)")
    private Integer callsAnswered;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name="block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name="hsubcenter_id", columnDefinition = "INT(11)")
    private Integer hsubcenterId;

    @Column(name="village_id", columnDefinition = "INT(11)")
    private Integer villageId;

    public Integer getKilkariId() {
        return kilkariId;
    }

    public void setKilkariId(Integer kilkariId) {
        this.kilkariId = kilkariId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Long getMctsId() {
        return mctsId;
    }

    public void setMctsId(Long mctsId) {
        this.mctsId = mctsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getAgeOnService() {
        return ageOnService;
    }

    public void setAgeOnService(Integer ageOnService) {
        this.ageOnService = ageOnService;
    }

    public Integer getCallsAnswered() {
        return callsAnswered;
    }

    public void setCallsAnswered(Integer callsAnswered) {
        this.callsAnswered = callsAnswered;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getHsubcenterId() {
        return hsubcenterId;
    }

    public void setHsubcenterId(Integer hsubcenterId) {
        this.hsubcenterId = hsubcenterId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }
}
