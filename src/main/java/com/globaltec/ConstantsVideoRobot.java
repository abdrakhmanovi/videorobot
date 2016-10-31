package com.globaltec;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConstantsVideoRobot {
	
	private static HashMap<String, String> camerasFromXML= new HashMap<String, String>();
	
	private ConstantsVideoRobot() {}

	/**
     * File storage on a server
     */
    public static final String FILE_STORAGE = "C://mtech//output/";
    
    public static final int COLUMNS_COUNT = 2;
    
    public static HashMap<String, String> getCameraList() throws SAXException, IOException, ParserConfigurationException {
    	if(camerasFromXML.size()==0){
    		Resource resource = new ClassPathResource("cameras.xml");
    		File f = resource.getFile();
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(f);
	        NodeList cameras = document.getElementsByTagName("camera"); 
	        for(int i = 0; i<cameras.getLength(); i++){
	        	Node camera = cameras.item(i);
	        	if (camera.getNodeType() == Node.ELEMENT_NODE) {
	        		Element eElement = (Element) camera;
	        		String cameraId = eElement.getAttribute("id");
	        		String cameraURL = eElement.getAttribute("url");
	        		camerasFromXML.put(cameraId, cameraURL);
	        	}    	
	        }
    	} 
    	return camerasFromXML;
    }
}
