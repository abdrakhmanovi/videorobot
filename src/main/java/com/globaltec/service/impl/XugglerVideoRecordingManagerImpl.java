package com.globaltec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.dao.GenericDao;
import com.globaltec.model.Record;
import com.globaltec.model.RecordCamera;
import com.globaltec.service.VideoRecordingManager;
import com.globaltec.xuggler.XugglerWriter;
import com.xuggle.xuggler.IError;

@Service("videoRecordingManager")
public class XugglerVideoRecordingManagerImpl extends GenericManagerImpl<Record, Long> implements VideoRecordingManager{

	@Autowired
	private GenericDao<Record, Long> recordDao;
	
	private List<XugglerWriter> cameraWriters = new ArrayList<>();
	
	public XugglerVideoRecordingManagerImpl(GenericDao recordDao){
		this.recordDao = recordDao;	
	}
	
	public Record startRecording(List<String> cameraIdList, Long recordId) throws Exception {
		Record record;

		if( recordId == null ){
			record = new Record();
			record.setName("test video name");
			record.setCreationDate(new Date());
			record = recordDao.save(record);
			recordId = record.getId();
		} else {
			record = (Record) recordDao.get(recordId);
		}
		
		final Long recordFinalId = recordId;
		
		List<RecordCamera> recordCameras = new ArrayList<>();
		for(final String cameraID : cameraIdList){
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					XugglerWriter xugglerWriter = new XugglerWriter(recordDao, recordFinalId, cameraID);
					try {
						RecordCamera recordCamera = xugglerWriter.startRecording();
						if(recordCamera != null) {
							recordCameras.add(recordCamera);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cameraWriters.add(xugglerWriter);
					System.out.println(System.nanoTime() + ": Camera " + cameraID + " is initialized");
				}
			});
			t1.start();
		}
		record.setRecordCameras(recordCameras);
		recordDao.save(record);
		return record;
	}


	public List<Record> getAll() {
		List<Record> records = recordDao.getAll();
		for(Record record : records){
			File checkFile = new File(ConstantsVideoRobot.FILE_STORAGE + "/" + record.getId() + "_*.mp4");
			if(checkFile.exists() && !checkFile.isDirectory()) { 
			    record.setVideoFound(true);
			} else {
				record.setVideoFound(false);
			}
		}
		return records;
	}
	
	public Record get(Long recordId) {
		Record record = recordDao.get(recordId);
		return record;
	}

	@Override
	public boolean stopRecording() {
		System.out.println("Stopping all cameras..");
		for(XugglerWriter writer : cameraWriters){
			System.out.println("Camera " + writer.getCameraId() + " stopped");
			writer.stopRecording();
		}
		return true;
	}

}
