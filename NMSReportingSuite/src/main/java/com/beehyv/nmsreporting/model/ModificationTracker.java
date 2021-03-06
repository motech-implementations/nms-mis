package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.enums.ModificationType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="modifications_table")
public class ModificationTracker {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer modificationId;
	
	@Column(name="modification_type")
	private String modificationType = ModificationType.CREATE.getModificationType();
	
	@Column(name="modified_field")
	private String modifiedField;

	@Column(name="previous_value")
	private String previousValue;

	@Column(name="new_value")
	private String newValue;
	
	@Column(name="modification_date")
	private Date modificationDate;
	
	@Column(name="modified_user_id")
	private Integer modifiedUserId;

	@Column(name="modified_by_user_id")
	private Integer modifiedByUserId;

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

	public String getModifiedField() {
		return modifiedField;
	}

	public void setModifiedField(String modifiedField) {
		this.modifiedField = modifiedField;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Integer getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(Integer modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Integer getModifiedByUserId() {
		return modifiedByUserId;
	}

	public void setModifiedByUserId(Integer modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}
}
