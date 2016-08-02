package com.globaltec.service.impl;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.dao.GenericDao;
import com.globaltec.model.Record;
import com.globaltec.service.VideoRecordingManager;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IError;

@Service("videoRecordingManager")
public class XugglerVideoRecordingManagerImpl extends GenericManagerImpl<Record, Long> implements VideoRecordingManager{

	String fileStoragePath = ConstantsVideoRobot.FILE_STORAGE;
	
	@Autowired
	private GenericDao<Record, Long> recordDao;
	
	public XugglerVideoRecordingManagerImpl(GenericDao recordDao){
		this.recordDao = recordDao;
	}
	
	private static IMediaReader reader = null;
	private static IMediaWriter writer = null;
	private static boolean continueProcessing;
	
	public boolean startRecording(String cameraURL, Long recordId) throws Exception {
		
		Record record;
		System.out.println(recordDao + "!!!");
		if(recordId == null){
			record = new Record();
			record.setName("test video name");
			recordDao.save(record);
			recordId = record.getId();
		} else {
			record = (Record) recordDao.get(recordId);
		}
		
		reader = ToolFactory.makeReader(cameraURL);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		writer = ToolFactory.makeWriter(fileStoragePath + "/" + recordId + ".mp4", reader);
		reader.addListener(writer);

		IError err = null;
		
		try {
			err = reader.readPacket();
		} catch (Exception e) {
			throw(e);
		}
		if (err != null) {
			System.out.println("Error: " + err);
			closeStreams();
			throw(new Exception(err.toString()));
		} else {
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					int i = 0;
					continueProcessing = true;
					while (continueProcessing) {
						System.out.println(i);
						IError err = null;
						if (reader != null)
							err = reader.readPacket();
						// System.out.println("end packet");
						if (err != null) {
							System.out.println("Error: " + err);
							break;
						}
						i++;
						/*
						if(i>200){
							continueProcessing = false;				
						}
						*/
					}
					closeStreams();
				}
			});
			t1.start();
			return true;
		}
	}

	private static void saveComment() {
		System.out.println(System.currentTimeMillis());
	}

	private static void closeStreams() {
		if (writer != null && writer.isOpen()) {
			writer.close();
		}
		if (reader != null && reader.isOpen()) {
			reader.close();
		}
	}

	@Override
	public boolean stopRecording(String cameraURL) {
		continueProcessing = false;		
		return true;
	}

}
