package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.enums.FileType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sms_audit_log")
public class SmsFileAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "error")
    private String error;

    @Column(name = "checksum")
    private String checksum;

    @Column(name = "record_count")
    private Integer recordCount;

    @Column(name = "success_status", nullable = false)
    private Boolean successStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public SmsFileAuditLog() {
    }

    public SmsFileAuditLog(FileType fileType, String fileName, String error, String checksum, Integer recordCount, Boolean successStatus) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.error = error;
        this.checksum = checksum;
        this.recordCount = recordCount;
        this.successStatus = successStatus;
    }

    public SmsFileAuditLog(FileType fileType, String fileName, String error, String checksum, Integer recordCount, Boolean successStatus, Date createdAt, Date updatedAt) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.error = error;
        this.checksum = checksum;
        this.recordCount = recordCount;
        this.successStatus = successStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType.toString();
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Boolean getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(Boolean successStatus) {
        this.successStatus = successStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FileAuditRecord{" +
                "type=" + fileType +
                ", fileName='" + fileName + '\'' +
                ", success=" + successStatus +
                ", error='" + error + '\'' +
                ", recordCount=" + recordCount +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
