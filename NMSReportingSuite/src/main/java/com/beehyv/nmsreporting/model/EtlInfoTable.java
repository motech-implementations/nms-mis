package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ETL_info_table")
public class EtlInfoTable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "table_id", columnDefinition = "INT(11)")
    private int tableId;

    @Column(name = "record_count", columnDefinition = "bigint(20)")
    private  long recordCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_etl_time", nullable = true)
    private Date lastEtlTime;

    @Column(name = "etl_job", columnDefinition = "varchar(255)")
    private String etlJob;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public String getEtlJob() {
        return etlJob;
    }

    public void setEtlJob(String etlJob) {
        this.etlJob = etlJob;
    }

    public Date getLastEtlTime() {
        return lastEtlTime;
    }

    public void setLastEtlTime(Date lastEtlTime) {
        this.lastEtlTime = lastEtlTime;
    }
}
