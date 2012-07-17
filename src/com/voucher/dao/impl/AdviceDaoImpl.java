package com.voucher.dao.impl;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.AdviceDao;
import com.voucher.entity.Advice;

public class AdviceDaoImpl extends BaseDaoImpl implements AdviceDao {

	@Override
	public void save(Advice advice) {
		try {
			this.getJpaTemplate().persist(advice);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
}
