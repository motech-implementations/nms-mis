package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 11/10/17.
 */
@Entity
@Table(name = "message_matrix_report")
public class MessageMatrix {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;


    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="mother_1_6_Content_75", columnDefinition = "BIGINT(20)")
    private Long mother_1_6_Content_75;
    
    @Column(name="mother_7_12_Content_75", columnDefinition = "BIGINT(20)")
    private Long mother_7_12_Content_75;

    @Column(name="mother_13_18_Content_75", columnDefinition = "BIGINT(20)")
    private Long mother_13_18_Content_75;

    @Column(name="mother_19_24_Content_75", columnDefinition = "BIGINT(20)")
    private Long mother_19_24_Content_75;

    @Column(name="mother_1_6_Content_50", columnDefinition = "BIGINT(20)")
    private Long mother_1_6_Content_50;

    @Column(name="mother_7_12_Content_50", columnDefinition = "BIGINT(20)")
    private Long mother_7_12_Content_50;

    @Column(name="mother_13_18_Content_50", columnDefinition = "BIGINT(20)")
    private Long mother_13_18_Content_50;

    @Column(name="mother_19_24_Content_50", columnDefinition = "BIGINT(20)")
    private Long mother_19_24_Content_50;

    @Column(name="mother_1_6_Content_25", columnDefinition = "BIGINT(20)")
    private Long mother_1_6_Content_25;

    @Column(name="mother_7_12_Content_25", columnDefinition = "BIGINT(20)")
    private Long mother_7_12_Content_25;

    @Column(name="mother_13_18_Content_25", columnDefinition = "BIGINT(20)")
    private Long mother_13_18_Content_25;

    @Column(name="mother_19_24_Content_25", columnDefinition = "BIGINT(20)")
    private Long mother_19_24_Content_25;

    @Column(name="mother_1_6_Content_1", columnDefinition = "BIGINT(20)")
    private Long mother_1_6_Content_1;

    @Column(name="mother_7_12_Content_1", columnDefinition = "BIGINT(20)")
    private Long mother_7_12_Content_1;

    @Column(name="mother_13_18_Content_1", columnDefinition = "BIGINT(20)")
    private Long mother_13_18_Content_1;

    @Column(name="mother_19_24_Content_1", columnDefinition = "BIGINT(20)")
    private Long mother_19_24_Content_1;

    @Column(name="child_1_6_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_1_6_Content_75;

    @Column(name="child_7_12_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_7_12_Content_75;

    @Column(name="child_13_18_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_13_18_Content_75;

    @Column(name="child_19_24_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_19_24_Content_75;

    @Column(name="child_25_30_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_25_30_Content_75;

    @Column(name="child_31_36_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_31_36_Content_75;

    @Column(name="child_37_42_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_37_42_Content_75;

    @Column(name="child_43_48_Content_75", columnDefinition = "BIGINT(20)")
    private Long child_43_48_Content_75;

    @Column(name="child_1_6_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_1_6_Content_50;

    @Column(name="child_7_12_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_7_12_Content_50;

    @Column(name="child_13_18_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_13_18_Content_50;

    @Column(name="child_19_24_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_19_24_Content_50;

    @Column(name="child_25_30_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_25_30_Content_50;

    @Column(name="child_31_36_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_31_36_Content_50;

    @Column(name="child_37_42_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_37_42_Content_50;

    @Column(name="child_43_48_Content_50", columnDefinition = "BIGINT(20)")
    private Long child_43_48_Content_50;

    @Column(name="child_1_6_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_1_6_Content_25;

    @Column(name="child_7_12_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_7_12_Content_25;

    @Column(name="child_13_18_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_13_18_Content_25;

    @Column(name="child_19_24_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_19_24_Content_25;

    @Column(name="child_25_30_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_25_30_Content_25;

    @Column(name="child_31_36_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_31_36_Content_25;

    @Column(name="child_37_42_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_37_42_Content_25;

    @Column(name="child_43_48_Content_25", columnDefinition = "BIGINT(20)")
    private Long child_43_48_Content_25;

    @Column(name="child_1_6_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_1_6_Content_1;

    @Column(name="child_7_12_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_7_12_Content_1;

    @Column(name="child_13_18_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_13_18_Content_1;

    @Column(name="child_19_24_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_19_24_Content_1;

    @Column(name="child_25_30_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_25_30_Content_1;

    @Column(name="child_31_36_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_31_36_Content_1;

    @Column(name="child_37_42_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_37_42_Content_1;

    @Column(name="child_43_48_Content_1", columnDefinition = "BIGINT(20)")
    private Long child_43_48_Content_1;

    public MessageMatrix(Integer id, Date date, Long mother_1_6_Content_75, Long mother_7_12_Content_75, Long mother_13_18_Content_75, Long mother_19_24_Content_75, Long mother_1_6_Content_50, Long mother_7_12_Content_50, Long mother_13_18_Content_50, Long mother_19_24_Content_50, Long mother_1_6_Content_25, Long mother_7_12_Content_25, Long mother_13_18_Content_25, Long mother_19_24_Content_25, Long mother_1_6_Content_1, Long mother_7_12_Content_1, Long mother_13_18_Content_1, Long mother_19_24_Content_1, Long child_1_6_Content_75, Long child_7_12_Content_75, Long child_13_18_Content_75, Long child_19_24_Content_75, Long child_25_30_Content_75, Long child_31_36_Content_75, Long child_37_42_Content_75, Long child_43_48_Content_75, Long child_1_6_Content_50, Long child_7_12_Content_50, Long child_13_18_Content_50, Long child_19_24_Content_50, Long child_25_30_Content_50, Long child_31_36_Content_50, Long child_37_42_Content_50, Long child_43_48_Content_50, Long child_1_6_Content_25, Long child_7_12_Content_25, Long child_13_18_Content_25, Long child_19_24_Content_25, Long child_25_30_Content_25, Long child_31_36_Content_25, Long child_37_42_Content_25, Long child_43_48_Content_25, Long child_1_6_Content_1, Long child_7_12_Content_1, Long child_13_18_Content_1, Long child_19_24_Content_1, Long child_25_30_Content_1, Long child_31_36_Content_1, Long child_37_42_Content_1, Long child_43_48_Content_1) {
        this.id = id;
        this.date = date;
        this.mother_1_6_Content_75 = mother_1_6_Content_75;
        this.mother_7_12_Content_75 = mother_7_12_Content_75;
        this.mother_13_18_Content_75 = mother_13_18_Content_75;
        this.mother_19_24_Content_75 = mother_19_24_Content_75;
        this.mother_1_6_Content_50 = mother_1_6_Content_50;
        this.mother_7_12_Content_50 = mother_7_12_Content_50;
        this.mother_13_18_Content_50 = mother_13_18_Content_50;
        this.mother_19_24_Content_50 = mother_19_24_Content_50;
        this.mother_1_6_Content_25 = mother_1_6_Content_25;
        this.mother_7_12_Content_25 = mother_7_12_Content_25;
        this.mother_13_18_Content_25 = mother_13_18_Content_25;
        this.mother_19_24_Content_25 = mother_19_24_Content_25;
        this.mother_1_6_Content_1 = mother_1_6_Content_1;
        this.mother_7_12_Content_1 = mother_7_12_Content_1;
        this.mother_13_18_Content_1 = mother_13_18_Content_1;
        this.mother_19_24_Content_1 = mother_19_24_Content_1;
        this.child_1_6_Content_75 = child_1_6_Content_75;
        this.child_7_12_Content_75 = child_7_12_Content_75;
        this.child_13_18_Content_75 = child_13_18_Content_75;
        this.child_19_24_Content_75 = child_19_24_Content_75;
        this.child_25_30_Content_75 = child_25_30_Content_75;
        this.child_31_36_Content_75 = child_31_36_Content_75;
        this.child_37_42_Content_75 = child_37_42_Content_75;
        this.child_43_48_Content_75 = child_43_48_Content_75;
        this.child_1_6_Content_50 = child_1_6_Content_50;
        this.child_7_12_Content_50 = child_7_12_Content_50;
        this.child_13_18_Content_50 = child_13_18_Content_50;
        this.child_19_24_Content_50 = child_19_24_Content_50;
        this.child_25_30_Content_50 = child_25_30_Content_50;
        this.child_31_36_Content_50 = child_31_36_Content_50;
        this.child_37_42_Content_50 = child_37_42_Content_50;
        this.child_43_48_Content_50 = child_43_48_Content_50;
        this.child_1_6_Content_25 = child_1_6_Content_25;
        this.child_7_12_Content_25 = child_7_12_Content_25;
        this.child_13_18_Content_25 = child_13_18_Content_25;
        this.child_19_24_Content_25 = child_19_24_Content_25;
        this.child_25_30_Content_25 = child_25_30_Content_25;
        this.child_31_36_Content_25 = child_31_36_Content_25;
        this.child_37_42_Content_25 = child_37_42_Content_25;
        this.child_43_48_Content_25 = child_43_48_Content_25;
        this.child_1_6_Content_1 = child_1_6_Content_1;
        this.child_7_12_Content_1 = child_7_12_Content_1;
        this.child_13_18_Content_1 = child_13_18_Content_1;
        this.child_19_24_Content_1 = child_19_24_Content_1;
        this.child_25_30_Content_1 = child_25_30_Content_1;
        this.child_31_36_Content_1 = child_31_36_Content_1;
        this.child_37_42_Content_1 = child_37_42_Content_1;
        this.child_43_48_Content_1 = child_43_48_Content_1;
    }

    public MessageMatrix(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMother_1_6_Content_75() {
        return mother_1_6_Content_75;
    }

    public void setMother_1_6_Content_75(Long mother_1_6_Content_75) {
        this.mother_1_6_Content_75 = mother_1_6_Content_75;
    }

    public Long getMother_7_12_Content_75() {
        return mother_7_12_Content_75;
    }

    public void setMother_7_12_Content_75(Long mother_7_12_Content_75) {
        this.mother_7_12_Content_75 = mother_7_12_Content_75;
    }

    public Long getMother_13_18_Content_75() {
        return mother_13_18_Content_75;
    }

    public void setMother_13_18_Content_75(Long mother_13_18_Content_75) {
        this.mother_13_18_Content_75 = mother_13_18_Content_75;
    }

    public Long getMother_19_24_Content_75() {
        return mother_19_24_Content_75;
    }

    public void setMother_19_24_Content_75(Long mother_19_24_Content_75) {
        this.mother_19_24_Content_75 = mother_19_24_Content_75;
    }

    public Long getMother_1_6_Content_50() {
        return mother_1_6_Content_50;
    }

    public void setMother_1_6_Content_50(Long mother_1_6_Content_50) {
        this.mother_1_6_Content_50 = mother_1_6_Content_50;
    }

    public Long getMother_7_12_Content_50() {
        return mother_7_12_Content_50;
    }

    public void setMother_7_12_Content_50(Long mother_7_12_Content_50) {
        this.mother_7_12_Content_50 = mother_7_12_Content_50;
    }

    public Long getMother_13_18_Content_50() {
        return mother_13_18_Content_50;
    }

    public void setMother_13_18_Content_50(Long mother_13_18_Content_50) {
        this.mother_13_18_Content_50 = mother_13_18_Content_50;
    }

    public Long getMother_19_24_Content_50() {
        return mother_19_24_Content_50;
    }

    public void setMother_19_24_Content_50(Long mother_19_24_Content_50) {
        this.mother_19_24_Content_50 = mother_19_24_Content_50;
    }

    public Long getMother_1_6_Content_25() {
        return mother_1_6_Content_25;
    }

    public void setMother_1_6_Content_25(Long mother_1_6_Content_25) {
        this.mother_1_6_Content_25 = mother_1_6_Content_25;
    }

    public Long getMother_7_12_Content_25() {
        return mother_7_12_Content_25;
    }

    public void setMother_7_12_Content_25(Long mother_7_12_Content_25) {
        this.mother_7_12_Content_25 = mother_7_12_Content_25;
    }

    public Long getMother_13_18_Content_25() {
        return mother_13_18_Content_25;
    }

    public void setMother_13_18_Content_25(Long mother_13_18_Content_25) {
        this.mother_13_18_Content_25 = mother_13_18_Content_25;
    }

    public Long getMother_19_24_Content_25() {
        return mother_19_24_Content_25;
    }

    public void setMother_19_24_Content_25(Long mother_19_24_Content_25) {
        this.mother_19_24_Content_25 = mother_19_24_Content_25;
    }

    public Long getMother_1_6_Content_1() {
        return mother_1_6_Content_1;
    }

    public void setMother_1_6_Content_1(Long mother_1_6_Content_1) {
        this.mother_1_6_Content_1 = mother_1_6_Content_1;
    }

    public Long getMother_7_12_Content_1() {
        return mother_7_12_Content_1;
    }

    public void setMother_7_12_Content_1(Long mother_7_12_Content_1) {
        this.mother_7_12_Content_1 = mother_7_12_Content_1;
    }

    public Long getMother_13_18_Content_1() {
        return mother_13_18_Content_1;
    }

    public void setMother_13_18_Content_1(Long mother_13_18_Content_1) {
        this.mother_13_18_Content_1 = mother_13_18_Content_1;
    }

    public Long getMother_19_24_Content_1() {
        return mother_19_24_Content_1;
    }

    public void setMother_19_24_Content_1(Long mother_19_24_Content_1) {
        this.mother_19_24_Content_1 = mother_19_24_Content_1;
    }

    public Long getChild_1_6_Content_75() {
        return child_1_6_Content_75;
    }

    public void setChild_1_6_Content_75(Long child_1_6_Content_75) {
        this.child_1_6_Content_75 = child_1_6_Content_75;
    }

    public Long getChild_7_12_Content_75() {
        return child_7_12_Content_75;
    }

    public void setChild_7_12_Content_75(Long child_7_12_Content_75) {
        this.child_7_12_Content_75 = child_7_12_Content_75;
    }

    public Long getChild_13_18_Content_75() {
        return child_13_18_Content_75;
    }

    public void setChild_13_18_Content_75(Long child_13_18_Content_75) {
        this.child_13_18_Content_75 = child_13_18_Content_75;
    }

    public Long getChild_19_24_Content_75() {
        return child_19_24_Content_75;
    }

    public void setChild_19_24_Content_75(Long child_19_24_Content_75) {
        this.child_19_24_Content_75 = child_19_24_Content_75;
    }

    public Long getChild_25_30_Content_75() {
        return child_25_30_Content_75;
    }

    public void setChild_25_30_Content_75(Long child_25_30_Content_75) {
        this.child_25_30_Content_75 = child_25_30_Content_75;
    }

    public Long getChild_31_36_Content_75() {
        return child_31_36_Content_75;
    }

    public void setChild_31_36_Content_75(Long child_31_36_Content_75) {
        this.child_31_36_Content_75 = child_31_36_Content_75;
    }

    public Long getChild_37_42_Content_75() {
        return child_37_42_Content_75;
    }

    public void setChild_37_42_Content_75(Long child_37_42_Content_75) {
        this.child_37_42_Content_75 = child_37_42_Content_75;
    }

    public Long getChild_43_48_Content_75() {
        return child_43_48_Content_75;
    }

    public void setChild_43_48_Content_75(Long child_43_48_Content_75) {
        this.child_43_48_Content_75 = child_43_48_Content_75;
    }

    public Long getChild_1_6_Content_50() {
        return child_1_6_Content_50;
    }

    public void setChild_1_6_Content_50(Long child_1_6_Content_50) {
        this.child_1_6_Content_50 = child_1_6_Content_50;
    }

    public Long getChild_7_12_Content_50() {
        return child_7_12_Content_50;
    }

    public void setChild_7_12_Content_50(Long child_7_12_Content_50) {
        this.child_7_12_Content_50 = child_7_12_Content_50;
    }

    public Long getChild_13_18_Content_50() {
        return child_13_18_Content_50;
    }

    public void setChild_13_18_Content_50(Long child_13_18_Content_50) {
        this.child_13_18_Content_50 = child_13_18_Content_50;
    }

    public Long getChild_19_24_Content_50() {
        return child_19_24_Content_50;
    }

    public void setChild_19_24_Content_50(Long child_19_24_Content_50) {
        this.child_19_24_Content_50 = child_19_24_Content_50;
    }

    public Long getChild_25_30_Content_50() {
        return child_25_30_Content_50;
    }

    public void setChild_25_30_Content_50(Long child_25_30_Content_50) {
        this.child_25_30_Content_50 = child_25_30_Content_50;
    }

    public Long getChild_31_36_Content_50() {
        return child_31_36_Content_50;
    }

    public void setChild_31_36_Content_50(Long child_31_36_Content_50) {
        this.child_31_36_Content_50 = child_31_36_Content_50;
    }

    public Long getChild_37_42_Content_50() {
        return child_37_42_Content_50;
    }

    public void setChild_37_42_Content_50(Long child_37_42_Content_50) {
        this.child_37_42_Content_50 = child_37_42_Content_50;
    }

    public Long getChild_43_48_Content_50() {
        return child_43_48_Content_50;
    }

    public void setChild_43_48_Content_50(Long child_43_48_Content_50) {
        this.child_43_48_Content_50 = child_43_48_Content_50;
    }

    public Long getChild_1_6_Content_25() {
        return child_1_6_Content_25;
    }

    public void setChild_1_6_Content_25(Long child_1_6_Content_25) {
        this.child_1_6_Content_25 = child_1_6_Content_25;
    }

    public Long getChild_7_12_Content_25() {
        return child_7_12_Content_25;
    }

    public void setChild_7_12_Content_25(Long child_7_12_Content_25) {
        this.child_7_12_Content_25 = child_7_12_Content_25;
    }

    public Long getChild_13_18_Content_25() {
        return child_13_18_Content_25;
    }

    public void setChild_13_18_Content_25(Long child_13_18_Content_25) {
        this.child_13_18_Content_25 = child_13_18_Content_25;
    }

    public Long getChild_19_24_Content_25() {
        return child_19_24_Content_25;
    }

    public void setChild_19_24_Content_25(Long child_19_24_Content_25) {
        this.child_19_24_Content_25 = child_19_24_Content_25;
    }

    public Long getChild_25_30_Content_25() {
        return child_25_30_Content_25;
    }

    public void setChild_25_30_Content_25(Long child_25_30_Content_25) {
        this.child_25_30_Content_25 = child_25_30_Content_25;
    }

    public Long getChild_31_36_Content_25() {
        return child_31_36_Content_25;
    }

    public void setChild_31_36_Content_25(Long child_31_36_Content_25) {
        this.child_31_36_Content_25 = child_31_36_Content_25;
    }

    public Long getChild_37_42_Content_25() {
        return child_37_42_Content_25;
    }

    public void setChild_37_42_Content_25(Long child_37_42_Content_25) {
        this.child_37_42_Content_25 = child_37_42_Content_25;
    }

    public Long getChild_43_48_Content_25() {
        return child_43_48_Content_25;
    }

    public void setChild_43_48_Content_25(Long child_43_48_Content_25) {
        this.child_43_48_Content_25 = child_43_48_Content_25;
    }

    public Long getChild_1_6_Content_1() {
        return child_1_6_Content_1;
    }

    public void setChild_1_6_Content_1(Long child_1_6_Content_1) {
        this.child_1_6_Content_1 = child_1_6_Content_1;
    }

    public Long getChild_7_12_Content_1() {
        return child_7_12_Content_1;
    }

    public void setChild_7_12_Content_1(Long child_7_12_Content_1) {
        this.child_7_12_Content_1 = child_7_12_Content_1;
    }

    public Long getChild_13_18_Content_1() {
        return child_13_18_Content_1;
    }

    public void setChild_13_18_Content_1(Long child_13_18_Content_1) {
        this.child_13_18_Content_1 = child_13_18_Content_1;
    }

    public Long getChild_19_24_Content_1() {
        return child_19_24_Content_1;
    }

    public void setChild_19_24_Content_1(Long child_19_24_Content_1) {
        this.child_19_24_Content_1 = child_19_24_Content_1;
    }

    public Long getChild_25_30_Content_1() {
        return child_25_30_Content_1;
    }

    public void setChild_25_30_Content_1(Long child_25_30_Content_1) {
        this.child_25_30_Content_1 = child_25_30_Content_1;
    }

    public Long getChild_31_36_Content_1() {
        return child_31_36_Content_1;
    }

    public void setChild_31_36_Content_1(Long child_31_36_Content_1) {
        this.child_31_36_Content_1 = child_31_36_Content_1;
    }

    public Long getChild_37_42_Content_1() {
        return child_37_42_Content_1;
    }

    public void setChild_37_42_Content_1(Long child_37_42_Content_1) {
        this.child_37_42_Content_1 = child_37_42_Content_1;
    }

    public Long getChild_43_48_Content_1() {
        return child_43_48_Content_1;
    }

    public void setChild_43_48_Content_1(Long child_43_48_Content_1) {
        this.child_43_48_Content_1 = child_43_48_Content_1;
    }
}
