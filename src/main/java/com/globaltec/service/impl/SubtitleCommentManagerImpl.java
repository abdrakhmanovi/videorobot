package com.globaltec.service.impl;

import java.io.File;
import java.util.Date;

import com.globaltec.service.CommentManager;
import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;
import com.globaltec.util.srt.SRTWriter;

public class SubtitleCommentManagerImpl implements CommentManager {
	
	Long recordingStart;
	String path;
	File f;
	
	public SubtitleCommentManagerImpl(String path){
		recordingStart = System.currentTimeMillis();
		this.path = path;
	}
	
	int commentId=0;
	SRTInfo info = new SRTInfo();

	@Override
	public void saveComment(String commentText, Long recordId) {
		if(f == null){
			f = new File(path + "/" + recordId + ".srt");
		}
		if(commentText.length()>0){
	        commentId++;
	        Long timePastFromStart = System.currentTimeMillis() - recordingStart;
	        Date subtitleDate = new Date (timePastFromStart);
	        Date endSubtitle = new Date (timePastFromStart + 3000);
	        SRT comment = new SRT(commentId, subtitleDate, endSubtitle, commentText);
	        //info.add(comment);
	        SRTWriter.writeSingleComment(f, comment);
	        
	    }
	}
//	
//	public void finalizeComments(){
//		File f = new File(path + "/" + recordId + ".srt");
//        SRTWriter.write(f, info);
//	}

}
