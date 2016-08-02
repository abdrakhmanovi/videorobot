package com.globaltec.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.service.VideoRecordingManager;

@Controller
@RequestMapping("/videoPlayback")
public class VideoPlaybackController {

	String fileStoragePath;
	String recordId;
	
	VideoRecordingManager videoRecordingManager;
	CommentManager commentManager;
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
    	
    	//Initialize all the variables
    	fileStoragePath = ConstantsVideoRobot.FILE_STORAGE;
    	
    	String videoId = request.getParameter("videoId");
    	
    	if(videoId!= null){
    		Record record = getVideoById(videoId);
    		return new ModelAndView().addObject("record", record);
    	} else {
    		List<Record> records = getRecordsList(fileStoragePath);
            return new ModelAndView().addObject("recordsList", records);
    	}
    }
    
    private List<Record> getRecordsList(String path){
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles();
    	List<Record> records = new ArrayList<Record>();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			Record record = new Record();
    			record.setName(listOfFiles[i].getName());
    			records.add(record);
    		}
    	}
    	return records;
    }
    
    private Record getVideoById(String id){
    	File folder = new File(fileStoragePath);
    	File[] listOfFiles = folder.listFiles();
    	List<Record> records = new ArrayList<Record>();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile() && listOfFiles[i].getName().equals(id)) {
    			Record record = new Record();
    			record.setName(listOfFiles[i].getName());
    			return record;
    		}
    	}
    	return null;
    }
    
    
    @RequestMapping(value = "/playVideo", method = RequestMethod.GET)
    @ResponseBody public void playVideo(HttpServletResponse response) {
        try {
            String path = "D://temp//temp//test_vr.mp4";
            File file = new File(path);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
            InputStream iStream = new FileInputStream(file);
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
