package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 10/10/17.
 */
@Entity
@Table(name="listening_matrix")
public class ListeningMatrix {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="calls_75_Content_75", columnDefinition = "BIGINT(20)")
    private Long calls_75_Content_75;

    @Column(name="calls_50_content_75", columnDefinition = "BIGINT(20)")
    private Long calls_50_content_75;

    @Column(name="calls_25_content_75", columnDefinition = "BIGINT(20)")
    private Long calls_25_content_75;

    @Column(name="calls_1_content_75", columnDefinition = "BIGINT(20)")
    private Long calls_1_content_75;

    @Column(name="calls_75_Content_50", columnDefinition = "BIGINT(20)")
    private Long calls_75_Content_50;

    @Column(name="calls_50_content_50", columnDefinition = "BIGINT(20)")
    private Long calls_50_content_50;

    @Column(name="calls_25_content_50", columnDefinition = "BIGINT(20)")
    private Long calls_25_content_50;

    @Column(name="calls_1_content_50", columnDefinition = "BIGINT(20)")
    private Long calls_1_content_50;

    @Column(name="calls_75_Content_25", columnDefinition = "BIGINT(20)")
    private Long calls_75_Content_25;

    @Column(name="calls_50_content_25", columnDefinition = "BIGINT(20)")
    private Long calls_50_content_25;

    @Column(name="calls_25_content_25", columnDefinition = "BIGINT(20)")
    private Long calls_25_content_25;

    @Column(name="calls_1_content_25", columnDefinition = "BIGINT(20)")
    private Long calls_1_content_25;

    @Column(name="calls_75_Content_1", columnDefinition = "BIGINT(20)")
    private Long calls_75_Content_1;

    @Column(name="calls_50_content_1", columnDefinition = "BIGINT(20)")
    private Long calls_50_content_1;

    @Column(name="calls_25_content_1", columnDefinition = "BIGINT(20)")
    private Long calls_25_content_1;

    @Column(name="calls_1_content_1", columnDefinition = "BIGINT(20)")
    private Long calls_1_content_1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCalls_75_Content_75() {
        return calls_75_Content_75;
    }

    public void setCalls_75_Content_75(Long calls_75_Content_75) {
        this.calls_75_Content_75 = calls_75_Content_75;
    }

    public Long getCalls_50_content_75() {
        return calls_50_content_75;
    }

    public void setCalls_50_content_75(Long calls_50_content_75) {
        this.calls_50_content_75 = calls_50_content_75;
    }

    public Long getCalls_25_content_75() {
        return calls_25_content_75;
    }

    public void setCalls_25_content_75(Long calls_25_content_75) {
        this.calls_25_content_75 = calls_25_content_75;
    }

    public Long getCalls_1_content_75() {
        return calls_1_content_75;
    }

    public void setCalls_1_content_75(Long calls_1_content_75) {
        this.calls_1_content_75 = calls_1_content_75;
    }

    public Long getCalls_75_Content_50() {
        return calls_75_Content_50;
    }

    public void setCalls_75_Content_50(Long calls_75_Content_50) {
        this.calls_75_Content_50 = calls_75_Content_50;
    }

    public Long getCalls_50_content_50() {
        return calls_50_content_50;
    }

    public void setCalls_50_content_50(Long calls_50_content_50) {
        this.calls_50_content_50 = calls_50_content_50;
    }

    public Long getCalls_25_content_50() {
        return calls_25_content_50;
    }

    public void setCalls_25_content_50(Long calls_25_content_50) {
        this.calls_25_content_50 = calls_25_content_50;
    }

    public Long getCalls_1_content_50() {
        return calls_1_content_50;
    }

    public void setCalls_1_content_50(Long calls_1_content_50) {
        this.calls_1_content_50 = calls_1_content_50;
    }

    public Long getCalls_75_Content_25() {
        return calls_75_Content_25;
    }

    public void setCalls_75_Content_25(Long calls_75_Content_25) {
        this.calls_75_Content_25 = calls_75_Content_25;
    }

    public Long getCalls_50_content_25() {
        return calls_50_content_25;
    }

    public void setCalls_50_content_25(Long calls_50_content_25) {
        this.calls_50_content_25 = calls_50_content_25;
    }

    public Long getCalls_25_content_25() {
        return calls_25_content_25;
    }

    public void setCalls_25_content_25(Long calls_25_content_25) {
        this.calls_25_content_25 = calls_25_content_25;
    }

    public Long getCalls_1_content_25() {
        return calls_1_content_25;
    }

    public void setCalls_1_content_25(Long calls_1_content_25) {
        this.calls_1_content_25 = calls_1_content_25;
    }

    public Long getCalls_75_Content_1() {
        return calls_75_Content_1;
    }

    public void setCalls_75_Content_1(Long calls_75_Content_1) {
        this.calls_75_Content_1 = calls_75_Content_1;
    }

    public Long getCalls_50_content_1() {
        return calls_50_content_1;
    }

    public void setCalls_50_content_1(Long calls_50_content_1) {
        this.calls_50_content_1 = calls_50_content_1;
    }

    public Long getCalls_25_content_1() {
        return calls_25_content_1;
    }

    public void setCalls_25_content_1(Long calls_25_content_1) {
        this.calls_25_content_1 = calls_25_content_1;
    }

    public Long getCalls_1_content_1() {
        return calls_1_content_1;
    }

    public void setCalls_1_content_1(Long calls_1_content_1) {
        this.calls_1_content_1 = calls_1_content_1;
    }
}
