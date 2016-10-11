package com.globaltec.dao;

import java.util.List;

import com.globaltec.model.Record;

public interface RecordDao extends GenericDao<Record, Long> {
    public List<Record> findActiveByUserName(String userName);
}
