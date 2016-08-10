package com.globaltec.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecordCamera {
	
	private Long recordCameraId;
	
	private Record record;
	
	private String cameraId;
	
	private String cameraURL;
	
	private String filePath;

	@ManyToOne(fetch = FetchType.LAZY)
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
	@GeneratedValue(strategy = GenerationType.AUTO) 
	public Long getRecordCameraId() {
		return recordCameraId;
	}

	public void setRecordCameraId(Long recordCameraId) {
		this.recordCameraId = recordCameraId;
	}
	
	
	
}
