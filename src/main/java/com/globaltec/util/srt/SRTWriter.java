package com.globaltec.util.srt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SRTWriter {
    public SRTWriter() {
    }
    
    /**
     * Writes an SRT file from an SRT object.
     * 
     * @param srtFile the SRT file
     * @param srtInfo the SRTInfo object
     * @throws SRTWriterException thrown while writing an SRT file
     */
    public static void write(File srtFile, SRTInfo srtInfo) throws SRTWriterException {
        try (PrintWriter pw = new PrintWriter(srtFile)) {
            for (SRT srt : srtInfo) {
                pw.println(srt.number);
                pw.println(
                    SRTTimeFormat.format(srt.startTime) +
                    SRTTimeFormat.TIME_DELIMITER +
                    SRTTimeFormat.format(srt.endTime));
                for (String text : srt.text) {
                    pw.println(text);
                }
                // Add an empty line at the end
                pw.println();
            }
        } catch (IOException e) {
            throw new SRTWriterException(e);
        }
    }
    
    public static void writeSingleComment(File srtFile, SRT srt) throws SRTWriterException {
    	try{
	    	BufferedWriter bw = new BufferedWriter(new FileWriter(srtFile, true));
	    	bw.write(srt.number + "");
	    	bw.newLine();
	    	bw.write(
	                SRTTimeFormat.format(srt.startTime) +
	                SRTTimeFormat.TIME_DELIMITER +
	                SRTTimeFormat.format(srt.endTime));
	    	bw.newLine();
	    	for (String text : srt.text) {
	    		bw.write(text);bw.newLine();
            }
	    	bw.newLine();
	    	bw.flush();
    	} catch (IOException e) {
            throw new SRTWriterException(e);
        }
    }
}