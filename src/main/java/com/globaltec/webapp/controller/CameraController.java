package com.globaltec.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/camerasOverview")
public class CameraController {

    String camera = "rtsp://admin:admin@192.168.0.37:554/cam/realmonitor?channel=1&subtype=1";
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView().addObject("defaultCameraAddress", camera);
    }
    
    @RequestMapping("/startRecording")
    public String startRecording() {
    	 return "REST POST Call !!!";
    }
    
}
