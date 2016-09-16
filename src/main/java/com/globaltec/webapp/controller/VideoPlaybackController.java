package com.globaltec.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.service.VideoRecordingManager;
import com.globaltec.service.impl.SubtitleCommentManagerImpl;
import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;

@Controller
@RequestMapping("/videoPlayback")
public class VideoPlaybackController {

	String fileStoragePath;
	String recordId;
	
	@Autowired
	private VideoRecordingManager videoRecordingManager;
	
	private CommentManager commentManager;
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
    	//Initialize all the variables
    	fileStoragePath = ConstantsVideoRobot.FILE_STORAGE;
    	
    	String videoId = request.getParameter("videoId");
    	
    	if(videoId!= null){
    		ModelAndView modelAndView = new ModelAndView();
    		
    		Record record = (Record) videoRecordingManager.get(new Long(videoId));
    		modelAndView.addObject("record", record);
    		
    		//TODO: ugly, let's change later
    		commentManager = new SubtitleCommentManagerImpl(record);
    		List<SRT> subtitles = commentManager.getCommentsByRecordId(record.getId());    		
    		modelAndView.addObject("subtitles", subtitles);
    		return modelAndView;
    	} else {
    		List<Record> records = videoRecordingManager.getAll();
            return new ModelAndView().addObject("recordsList", records);
    	}
    }
    
    @RequestMapping(value = "/playVideo", method = RequestMethod.GET)
    @ResponseBody public void playVideo(@RequestParam("recordId") Long recordId, HttpServletResponse response) {
        try {
        	if(recordId != null){
        		//TODO : CHANGE TEST 1!!!!!!! ->>>
	            String path = ConstantsVideoRobot.FILE_STORAGE + recordId + "_1.mp4";
	            File file = new File(path);
	            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	            response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
	            InputStream iStream = new FileInputStream(file);
	            IOUtils.copy(iStream, response.getOutputStream());
	            response.flushBuffer();
        	}
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    
//    
//    @RequestMapping(value = "/getComments", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody 
//    private List<SRT> getComments(@RequestParam("recordId") Long recordId, HttpServletResponse response) {
//        try {
//        	List<SRT> subtitles = commentManager.getCommentsByRecordId(recordId);
//        	return subtitles;
//        } catch (Exception e) {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//		return null;
//    }

}
