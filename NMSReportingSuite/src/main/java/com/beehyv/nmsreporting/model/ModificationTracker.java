package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.enums.ModificationType;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="MODIFICATIONS_TABLE")
public class ModificationTracker {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="modification_id")
	private Integer modificationId;
	
	@Column(name="modification_type")
	private String modificationType = ModificationType.CREATE.getModificationType();
	
	@Column(name="modification_desc")
	private String modificationDescription;
	
	@Column(name="modification_date")
	private Date modificationDate;
	
	@ManyToOne
	@JoinColumn(name="modified_user_id")
	private User modifiedUserId;
	
	@ManyToOne
	@JoinColumn(name="modified_by_user_id")
	private User modifiedByUserId;

	public Integer getModificationId() {
		return modificationId;
	}

	public void setModificationId(Integer modificationId) {
		this.modificationId = modificationId;
	}

	public String getModificationType() {
		return modificationType;
	}

	public void setModificationType(String modificationType) {
		this.modificationType = modificationType;
	}

	public String getModificationDescription() {
		return modificationDescription;
	}

	public void setModificationDescription(String modificationDescription) {
		this.modificationDescription = modificationDescription;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(User modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public User getModifiedByUserId() {
		return modifiedByUserId;
	}

	public void setModifiedByUserId(User modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}	
}
