package com.globaltec.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.globaltec.dao.RecordDao;
import com.globaltec.model.Record;

public class RecordDaoHibernate extends GenericDaoHibernate<Record, Long> implements RecordDao {
 
    public RecordDaoHibernate() {
        super(Record.class);
    }
 
    public List<Record> findActiveByUserName(String userName) {
        return getSession().createCriteria(Record.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("active", true)).list();
    }
}