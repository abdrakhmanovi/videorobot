package com.globaltec.service;

import java.util.List;

import com.globaltec.model.Record;

public interface VideoRecordingManager extends GenericManager<Record, Long> {
	
	public Record startRecording(List<String> cameraIdList, Long recordId) throws Exception;	
	
	public boolean stopRecording();

}
