package com.globaltec.service;

import java.util.List;

import com.globaltec.model.Record;
import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;

public interface CommentManager {
	
	public void saveComment(String commentText);
	
	public List<SRT> getCommentsByRecordId(Long recordId);

}
