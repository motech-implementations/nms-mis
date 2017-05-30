//package com.beehyv.nmsreporting.model;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.*;
//
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Table(name="USER_LOCATION")
//public class Location {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="location_id")
//	private Integer locationId;
//
//	@Column(name="location")
//	private String location;
//
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="reference_id")
//	private Location referenceId;
//
//	@OneToMany(mappedBy="referenceId")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@JsonIgnore
//	private Set<Location> subLocations = new HashSet<>();
//
//	public Integer getLocationId() {
//		return locationId;
//	}
//
//	public void setLocationId(Integer locationId) {
//		this.locationId = locationId;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public Location getReferenceId() {
//		return referenceId;
//	}
//
//	public void setReferenceId(Location referenceId) {
//		this.referenceId = referenceId;
//	}
//
//	public Set<Location> getSubLocations() {
//		return subLocations;
//	}
//
//	public void setSubLocations(Set<Location> subLocations) {
//		this.subLocations = subLocations;
//	}
//}
