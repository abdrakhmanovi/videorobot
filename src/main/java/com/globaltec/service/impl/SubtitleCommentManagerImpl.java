package com.globaltec.service.impl;

import java.io.File;
import java.util.Date;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;
import com.globaltec.util.srt.SRTWriter;

public class SubtitleCommentManagerImpl implements CommentManager {
	
	private String path = ConstantsVideoRobot.FILE_STORAGE;
	private File subtitlesFile;
	private int commentId=0;
	private SRTInfo info = new SRTInfo();
	private Record record;
	
	public SubtitleCommentManagerImpl(Record record){
		this.record = record;
	};

	@Override
	public void saveComment(String commentText) {
		if(subtitlesFile == null){
			subtitlesFile = new File(path + "/" + record.getId() + ".srt");
		}
		if(commentText.length()>0){
	        commentId++;
	        Long timePastFromStart = System.currentTimeMillis() - record.getCreationDate().getTime();
	        Date subtitleDate = new Date (timePastFromStart);
	        Date endSubtitle = new Date (timePastFromStart + 3000);
	        SRT comment = new SRT(commentId, subtitleDate, endSubtitle, commentText);
	        //info.add(comment);
	        SRTWriter.writeSingleComment(subtitlesFile, comment);
	    }
	}
}
