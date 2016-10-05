package com.globaltec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantsVideoRobot {
	
	private ConstantsVideoRobot() {}

	/**
     * File storage on a server
     */
    public static final String FILE_STORAGE = "D://vr_output//";
    
    public static final HashMap<String, String> CAMERAS_URL_LIST = new HashMap<String, String>() {{
        put("1", "rtsp://localhost:8554/");
        put("2", "rtsp://localhost:554/");
        put("3", "rtsp://localhost:8554/");
        put("4", "rtsp://localhost:554/");
//        put("3", "rtsp://localhost:8554/");
//        put("4", "rtsp://localhost:554/");
    }};
    
    public static final int columns = 2;
}
