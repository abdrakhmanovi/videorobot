package com.globaltec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.globaltec.ConstantsVideoRobot;
import com.globaltec.model.Record;
import com.globaltec.service.CommentManager;
import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;
import com.globaltec.util.srt.SRTReader;
import com.globaltec.util.srt.SRTWriter;
import com.mysql.fabric.xmlrpc.base.Array;

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
	
	@Override
	public List<SRT> getCommentsByRecordId(Long recordId) {
		List<SRT> comments = new ArrayList<SRT>();
		if(recordId != null){
			File subtitlesFile = new File(path + "/" + recordId + ".srt");
			if(subtitlesFile.exists()){
				SRTInfo subtitles = SRTReader.read(subtitlesFile);
				for (int i = 1; i <= subtitles.size(); i++) {
					SRT srt = subtitles.get(i);
					comments.add(srt);
			    }
				return comments;
			} else{
				System.out.println("Error: subtitles file " + recordId + " is null");
				return null;
			}
	    }
		return null;
	}
}
