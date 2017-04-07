package com.globaltec.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.globaltec.dao.RecordDao;
import com.globaltec.model.Record;
import com.globaltec.service.VideoRecordingManager;

public class VLCJVideoRecordingManagerImplementation extends GenericManagerImpl implements VideoRecordingManager {

	@Autowired
	private RecordDao recordDao;
	private Record record = null;
	
	public Record startRecording(List<String> cameraIdList, Long recordId) throws Exception {
		if( recordId == null ){
			record = new Record();
			record.setName("test video name");
			record.setCreationDate(new Date());
			record = recordDao.save(record);
			recordId = record.getId();
		} else {
			record = (Record) recordDao.get(recordId);
		}
		
		final Long recordFinalId = recordId;
		return record;
	}

	public boolean stopRecording() {
		// TODO Auto-generated method stub
		return false;
	}

	public Record get(Long recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Record> getActiveRecords() {
		// TODO Auto-generated method stub
		return null;
	}

}
