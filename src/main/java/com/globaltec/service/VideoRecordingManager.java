package com.globaltec.service;

import com.globaltec.model.Record;

public interface VideoRecordingManager extends GenericManager<Record, Long> {
	
	public boolean startRecording(String cameraURL, Long recordId) throws Exception;	
	
	public boolean stopRecording(String recordId);
}
