package com.globaltec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.dao.GenericDao;
import com.globaltec.model.Record;
import com.globaltec.model.RecordCamera;
import com.globaltec.service.VideoRecordingManager;
import com.globaltec.xuggler.XugglerWriter;

@Service("videoRecordingManager")
public class XugglerVideoRecordingManagerImpl extends GenericManagerImpl implements VideoRecordingManager{

	@Autowired
	private GenericDao<Record, Long> recordDao;

	@Autowired
	private GenericDao<RecordCamera, Long> recordCameraDao;
	
	private List<XugglerWriter> cameraWriters = new ArrayList<>();
	
	private Record record = null;
	
	public Record startRecording(List<String> cameraIdList, Long recordId) throws Exception {
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
		
		for(final String cameraID : cameraIdList){
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					XugglerWriter xugglerWriter = new XugglerWriter(recordFinalId, cameraID);
					try {
						RecordCamera recordCamera = xugglerWriter.startRecording();
						recordCameraDao.save(recordCamera);
						recordDao.save(record);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cameraWriters.add(xugglerWriter);
					System.out.println(System.nanoTime() + ": Camera " + cameraID + " is initialized");
				}
			});
			t1.start();
		}
		return record;
	}


	public List<Record> getAll() {
		List<Record> records = recordDao.getAll();
		for(Record record : records){
			Collection<File> checkFile = listFilesByWildcard(ConstantsVideoRobot.FILE_STORAGE + "/", record.getId() + "_*.mp4");
			if(checkFile.size()>0) { 
			    record.setVideoFound(true);
			} else {
				record.setVideoFound(false);
			}
		}
		return records;
	}
	
	private Collection<File> listFilesByWildcard(String folderName, String wildcard){
		File directory = new File(folderName);
		Collection<File> files = FileUtils.listFiles(directory, new WildcardFileFilter(wildcard), null);
		return files;
		
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
