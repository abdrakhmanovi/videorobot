package com.globaltec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

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
	
	public class WriterLauncher implements Callable<RecordCamera>{
		
		private Long recordId;
		
		private String cameraId;
		
		public WriterLauncher(Long recordId, String cameraId){
			this.recordId = recordId;
			this.cameraId = cameraId;
		}
		
		public RecordCamera call() throws Exception {
			System.out.println("Initizaling camera " + cameraId + "..");
			XugglerWriter xugglerWriter = new XugglerWriter(recordId, cameraId);
			RecordCamera recordCamera = null;
			recordCamera = xugglerWriter.startRecording();
			cameraWriters.add(xugglerWriter);
			System.out.println(System.nanoTime() + ": Camera " + cameraId + " is initialized");
			return recordCamera;
		}
	}
	
	
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
		
		ExecutorService executor = Executors.newFixedThreadPool(cameraIdList.size());
		List<Future<RecordCamera>> futureTaskList = new ArrayList<Future<RecordCamera>>();
		for(final String cameraId : cameraIdList){
//			Thread t1 = new Thread(new Runnable() {
//				public void run() {
//					
//				}
//			});
//			t1.start();
			
			
			//We use Callable here to be able to use Exceptions
			
			WriterLauncher writerLauncher = new WriterLauncher(recordFinalId, cameraId);
			Future<RecordCamera> asyncResult = executor.submit(writerLauncher);
			futureTaskList.add(asyncResult);
		}
		
		for(Future<RecordCamera> futureRC : futureTaskList){
			RecordCamera recordCamera = futureRC.get();
			List<RecordCamera> recCameras = record.getRecordCameras();
			if(recCameras == null){
				recCameras = new ArrayList<RecordCamera>();
			}
			recCameras.add(recordCamera);
			record.setRecordCameras(recCameras);
			recordDao.save(record);
			recordCamera.setRecord(record);
			recordCameraDao.save(recordCamera);
			System.out.println(System.nanoTime() + ": metadata saved for camera " + recordCamera.getCameraId());
		}
		
		executor.shutdown();
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
		cameraWriters.clear();
		record = null;
		return true;
	}

}
