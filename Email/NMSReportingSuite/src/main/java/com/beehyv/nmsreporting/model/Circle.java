package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Entity
@Table(name = "dim_circle")
public class Circle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT(4)")
    private Integer circleId;

    @Column(name="circle_name", columnDefinition = "VARCHAR(255)")
    private String circleName;

    @Column(name = "modificationdate",columnDefinition = "TIMESTAMP")
    private Date lastModifiedDate;

    @Column(name = "circle_full_name", columnDefinition = "VARCHAR(255)")
    private String circleFullName;

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
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

    public String getCircleFullName() {
        return circleFullName;
    }

    public void setCircleFullName(String circleFullName) {
        this.circleFullName = circleFullName;
    }
}
