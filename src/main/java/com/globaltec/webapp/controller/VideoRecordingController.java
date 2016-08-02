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

	private CommentManager commentManager;	
	private String cameraURL;
	private String fileStoragePath;
	private Record currentRecord;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
    	setConstants();
        return new ModelAndView().addObject("defaultCameraAddress", cameraURL);
    }
    
    @RequestMapping("/startRecording")
    @ResponseBody
    @Async
    public Map<String,Object> startRecording() {
    	setConstants();
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	try {
    		currentRecord = videoRecordingManager.startRecording(cameraURL, null);
			commentManager = new SubtitleCommentManagerImpl(currentRecord);
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
    	setConstants();
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	boolean isRecordingStoped = false;
    	try {
    		isRecordingStoped = videoRecordingManager.stopRecording(cameraURL);
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
    	commentManager.saveComment(commentText);
    	returnObject.put("isSuccessful", false);
    	return returnObject;
   	}
    
    //TODO: Deal with constants
    private void setConstants(){
    	cameraURL = ConstantsVideoRobot.TEST_CAMERA_URL;
    	fileStoragePath = ConstantsVideoRobot.FILE_STORAGE;
    }
}
