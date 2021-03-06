package com.globaltec.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.service.VideoRecordingManager;
import com.globaltec.service.impl.SubtitleCommentManagerImpl;

@Controller
@RequestMapping("/videoRecording")
public class VideoRecordingController {

	@Autowired
	private VideoRecordingManager videoRecordingManager;
	
	private CommentManager commentManager;	
	private Record currentRecord;
	private HashMap<String, String> camerasURL;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
    	setConfiguration();
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("camerasURL", camerasURL);
    	modelAndView.addObject("columns", ConstantsVideoRobot.COLUMNS_COUNT);
        return modelAndView;
    }
    
    @RequestMapping("/startRecording")
    @ResponseBody
    @Async
    public Map<String,Object> startRecording(@RequestParam("cameraIdToRecord") String cameraIdToRecord) {
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	try {
			setConfiguration();
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
			returnObject.put("errorMessage", e1.getMessage());
			returnObject.put("isSuccessful", false);
			stopRecording();
			return returnObject;
		}
    	try {
    		String[] camerasIDArray = StringUtils.split(cameraIdToRecord, "_");
    		List<String> camerasListToSubmit = new ArrayList<>();
    		for(int i=0; i<camerasIDArray.length; i++){
    			String cameraIndex = camerasIDArray[i];
    			String singleCameraURL = camerasURL.get(cameraIndex);
    			if(singleCameraURL == null){
    				returnObject.put("errorMessage", "Camera with index " + cameraIndex + " is not found");
    				returnObject.put("isSuccessful", false);
    				return returnObject;
    			}
    			camerasListToSubmit.add(cameraIndex);
    		}
    		currentRecord = videoRecordingManager.startRecording(camerasListToSubmit, null);
    		if(currentRecord!=null){
    			commentManager = new SubtitleCommentManagerImpl(currentRecord);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.put("errorMessage", e.getMessage());
			returnObject.put("isSuccessful", false);
			stopRecording();
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
    	try {
			setConfiguration();
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
			returnObject.put("errorMessage", e1.getMessage());
			returnObject.put("isSuccessful", false);
		}
    	boolean isRecordingStoped = false;
    	try {
    		isRecordingStoped = videoRecordingManager.stopRecording();
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
    
    @RequestMapping("/checkRecordingStatus")
    @ResponseBody
    @Async
    public boolean checkRecordingStatus() {
    	List<Record> records = videoRecordingManager.getActiveRecords();
    	if(records.size()>0){
    		return true;
    	} else {
    		return false;
    	}
   	}
    
    private void setConfiguration() throws SAXException, IOException, ParserConfigurationException{
    	camerasURL = ConstantsVideoRobot.getCameraList();
    }
}
