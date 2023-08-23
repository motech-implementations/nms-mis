package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BULK_CERTIFICATE_AUDIT")
public class BulkCertificateAudit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String fileDirectory;

    @OneToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    private Date generatedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public User getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    public Date getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Date generatedOn) {
        this.generatedOn = generatedOn;
    }
}
