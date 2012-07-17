package com.voucher.dao.impl;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.PublishDao;
import com.voucher.entity.Publish;

public class PublishDaoImpl extends BaseDaoImpl implements PublishDao {

	@Override
	public void save(Publish publish) {
		try {
			this.getJpaTemplate().persist(publish);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
}
