package com.globaltec.service.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import com.globaltec.service.VideoRecordingManager;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IError;

public class XugglerVideoRecordingManagerImpl implements VideoRecordingManager{

	//Provide a Singletone implementation for XugglerVideoRecordingManagerImpl
	private static XugglerVideoRecordingManagerImpl instance;
	
	public static synchronized XugglerVideoRecordingManagerImpl getInstance() {
		if (instance == null) {
			instance = new XugglerVideoRecordingManagerImpl();
		}
		return instance;
	}
	
	private static IMediaReader reader = null;
	private static IMediaWriter writer = null;
	private static boolean continueProcessing;
	
	@Override
	public boolean startRecording(String cameraURL) throws Exception {
		
		reader = ToolFactory.makeReader(cameraURL);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		writer = ToolFactory.makeWriter("d:/output.mp4", reader);
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
