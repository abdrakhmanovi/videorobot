package com.globaltec.xuggler;

import java.awt.image.BufferedImage;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.RecordCamera;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IError;

public class XugglerWriter {
	
	private String cameraId;
	private Long recordId;
	
	public XugglerWriter(Long recordId, String cameraId){
		this.cameraId = cameraId;
		this.recordId = recordId;
	};

	private IMediaReader reader = null;
	private IMediaWriter writer = null;
	private boolean continueProcessing;
	
	
	public RecordCamera startRecording() throws Exception {
		
		String cameraURL = ConstantsVideoRobot.CAMERAS_URL_LIST.get(cameraId);
		String filePath = ConstantsVideoRobot.FILE_STORAGE + "/" + recordId + "_" + cameraId + ".mp4";
		
		reader = ToolFactory.makeReader(cameraURL);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		writer = ToolFactory.makeWriter(filePath, reader);
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
			System.out.println("Started recording camera " + cameraId + ": " + cameraURL + "...");
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					continueProcessing = true;
					while (continueProcessing) {
						IError err = null;
						if (reader != null)
							err = reader.readPacket();
						if (err != null) {
							System.out.println("Error: " + err);
							break;
						}
					}
					closeStreams();
				}
			});
			t1.start();
			RecordCamera recordCamera = new RecordCamera();
			recordCamera.setCameraId(cameraId);
			recordCamera.setCameraURL(cameraURL);
			recordCamera.setFilePath(filePath);
			return recordCamera;
		}
	}

	private void closeStreams() {
		if (writer != null && writer.isOpen()) {
			writer.close();
		}
		if (reader != null && reader.isOpen()) {
			reader.close();
		}
	}

	public boolean stopRecording() {
		continueProcessing = false;		
		return true;
	}

	public String getCameraId() {
		return cameraId;
	}
	
}
