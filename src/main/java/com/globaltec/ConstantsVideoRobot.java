package com.globaltec;

import java.util.HashMap;

public class ConstantsVideoRobot {
	
	private ConstantsVideoRobot() {}

	/**
     * File storage on a server
     */
    public static final String FILE_STORAGE = "C://mtech//output/";
    
    public static final HashMap<String, String> CAMERAS_URL_LIST = new HashMap<String, String>() {{
        put("1", "rtsp://localhost:8554/");
        put("2", "rtsp://localhost:8554/");
        put("3", "rtsp://localhost:8554/");
        put("4", "rtsp://localhost:8554/");
        put("5", "rtsp://localhost:8554/");
        put("6", "rtsp://localhost:8554/");
        put("7", "rtsp://localhost:8554/");
        put("8", "rtsp://localhost:8554/");

//        put("3", "rtsp://localhost:8554/");
//        put("4", "rtsp://localhost:554/");
    }};
    
    public static final int COLUMNS_COUNT = 2;
}
