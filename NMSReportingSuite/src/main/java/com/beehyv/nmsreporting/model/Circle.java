package com.beehyv.nmsreporting.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
public class Circle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT(4)")
    private Integer circleId;

    @Column(name="circle_name", columnDefinition = "VARCHAR(255)")
    private String circleName;

    @Column(name = "last_modified",columnDefinition = "TIMESTAMP")
    private Date lastModifiedDate;

    public Integer getCircleIdId() {
        return circleId;
    }

    public void setCircleIdId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
