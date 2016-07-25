package com.globaltec.service;

public interface VideoRecordingManager {
	
	public boolean startRecording(String cameraURL) throws Exception;
	
	public boolean stopRecording(String cameraURL);

}
