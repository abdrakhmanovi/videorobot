package com.globaltec.prototype;


import java.io.File;
import java.util.Arrays;
import java.util.Date;

import com.globaltec.util.srt.*;

public class Main {
    private static void print(SRTInfo info) {
        for (SRT s : info) {
            System.out.println("Number: " + s.number);
            System.out.println("Start time: " + SRTTimeFormat.format(s.startTime));
            System.out.println("End time: " + SRTTimeFormat.format(s.endTime));
            System.out.println("Texts:");
            for (String line : s.text) {
                System.out.println("    " + line);
            }
            System.out.println();
        }
    }
    
//    private static void testRead() {
//        SRTInfo info = SRTReader.read(new File("c:/output.srt"));
//        
//        Date currentTime = new Date();
//        for(int i = 0; i< info.size(); i++){
//            SRT srt = info.get(i);
//            if(srt.startTime.before(currentTime) && srt.endTime.after(currentTime)){
//        	
//            }
//        }
//        print(info);
//    }
    
    private static void testWrite() {
        SRTInfo info = new SRTInfo();
        Date d = new Date();
        info.add(new SRT(1, d, d, "Hello", "World"));
        info.add(new SRT(2, d, d, "Bye", "World"));
        
        File f = new File("f:/out1.srt");
        //f.deleteOnExit();
        SRTWriter.write(f, info);
    }
    
//    private static void testEdit() {
//        SRTInfo info = SRTReader.read(new File("f:/in.srt"));
//        SRTEditor.updateText(info, 1, 10);
//        SRTEditor.updateTime(info, 1, SRTTimeFormat.Type.MILLISECOND, 100);
//        SRTEditor.prependSubtitle(info, "00:00:05,000", "00:00:07,000",
//            Arrays.asList("Test"));
//        SRTEditor.appendSubtitle(info, "00:01:05,000", "00:01:07,000",
//            Arrays.asList("Test"));
//        
//        print(info);
//        
//        // Write it back
//        File f = new File("f:/out2.srt");
//        f.deleteOnExit();
//        SRTWriter.write(f, info);
//    }
    
    public static void main(String[] args) {
        //testRead();
        testWrite();
        //testEdit();
    }
}