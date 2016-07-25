package com.globaltec.webapp.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.globaltec.service.VideoRecordingManager;
import com.globaltec.service.impl.XugglerVideoRecordingManagerImpl;

@Controller
@RequestMapping("/camerasOverview")
public class CameraController {

	String cameraURL = "rtsp://localhost:8554/";
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView().addObject("defaultCameraAddress", cameraURL);
    }
    
    @RequestMapping("/startRecording")
    @ResponseBody
    @Async
    public Map<String,Object> startRecording() {
    	Map<String, Object> returnObject = new HashMap<String, Object>();
    	boolean isRecordingStarted = false;
    	try {
    		VideoRecordingManager videoRecordingManager = new XugglerVideoRecordingManagerImpl();
			isRecordingStarted = videoRecordingManager.startRecording(cameraURL);
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
    		VideoRecordingManager videoRecordingManager = new XugglerVideoRecordingManagerImpl();
			isRecordingStarted = videoRecordingManager.stopRecording(cameraURL);
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.put("errorMessage", e.getMessage());
			returnObject.put("isSuccessful", false);
			return returnObject;
		}
    	returnObject.put("isSuccessful", true);
    	return returnObject;
   	}
}
