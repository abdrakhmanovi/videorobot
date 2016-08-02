package com.globaltec.webapp.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.dao.GenericDao;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.service.VideoRecordingManager;
import com.globaltec.service.impl.SubtitleCommentManagerImpl;
import com.globaltec.service.impl.XugglerVideoRecordingManagerImpl;

@Controller
@RequestMapping("/videoRecording")
public class VideoRecordingController {

	@Autowired
	private GenericDao recordDao;
	
	@Autowired
	private VideoRecordingManager videoRecordingManager;
	
	String cameraURL;
	String fileStoragePath;
	CommentManager commentManager;
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
    	System.out.println(recordDao + "!!!!!!!!!!!!!!!!!!!!");
    	//Initialize all the variables
    	cameraURL = "rtsp://localhost:8554/";
    	fileStoragePath = ConstantsVideoRobot.FILE_STORAGE;
    	System.out.println(videoRecordingManager);
    	//videoRecordingManager = new XugglerVideoRecordingManagerImpl(fileStoragePath);
    	commentManager = new SubtitleCommentManagerImpl(fileStoragePath);
    	
        return new ModelAndView().addObject("defaultCameraAddress", cameraURL);
    }
    
    @RequestMapping("/startRecording")
    @ResponseBody
    @Async
    public Map<String,Object> startRecording() {
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	boolean isRecordingStarted = false;
    	try {
			isRecordingStarted = videoRecordingManager.startRecording(cameraURL, null);
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.put("errorMessage", e.getMessage());
			returnObject.put("isSuccessful", false);
			return returnObject;
		}
    	returnObject.put("isSuccessful", true);
    	return returnObject;
   	}
    
    @RequestMapping("/stopRecording")
    @ResponseBody
    @Async
    public Map<String,Object> stopRecording() {
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	boolean isRecordingStarted = false;
    	try {
			isRecordingStarted = videoRecordingManager.stopRecording(cameraURL);
			//commentManager.finalizeComments();
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.put("errorMessage", e.getMessage());
			returnObject.put("isSuccessful", false);
			return returnObject;
		}
    	returnObject.put("isSuccessful", true);
    	return returnObject;
   	}
    
    @RequestMapping("/saveComment")
    @ResponseBody
    @Async
    public Map<String, Object> saveComment(@RequestParam("commentText") String commentText, Long recordId) {
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	commentManager.saveComment(commentText, recordId);
    	returnObject.put("isSuccessful", false);
    	return returnObject;
   	}
}
