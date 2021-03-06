package com.globaltec.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class RecordCamera {
	
	private Long recordCameraId;
	
	private Record record;
	
	private String cameraId;
	
	private String cameraURL;
	
	private String filePath;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "RECORD_ID", nullable = false)
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraURL() {
		return cameraURL;
	}

	public void setCameraURL(String cameraURL) {
		this.cameraURL = cameraURL;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.TABLE) 
	public Long getRecordCameraId() {
		return recordCameraId;
	}

	public void setRecordCameraId(Long recordCameraId) {
		this.recordCameraId = recordCameraId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
