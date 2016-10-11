package com.globaltec.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Record extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private boolean videoFound;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date creationDate; 
	
	private List<RecordCamera> recordCameras;
	
	private String userName;
	
	private boolean active;

	@Id 
	@GeneratedValue(strategy = GenerationType.TABLE) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isVideoFound() {
		return videoFound;
	}

	public void setVideoFound(boolean videoFound) {
		this.videoFound = videoFound;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (this.id).toString();
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "record")
	public List<RecordCamera> getRecordCameras() {
		return recordCameras;
	}

	public void setRecordCameras(List<RecordCamera> recordCameras) {
		this.recordCameras = recordCameras;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void addRecordCamera(RecordCamera recordCamera){
		recordCameras.add(recordCamera);
	}

}
