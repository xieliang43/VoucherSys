package com.voucher.service.impl;

import com.voucher.dao.AdviceDao;
import com.voucher.entity.Advice;
import com.voucher.service.AdviceService;

public class AdviceServiceImpl implements AdviceService {
	private AdviceDao adviceDao;

	@Override
	public void saveAdvice(Advice advice) {
		adviceDao.save(advice);
	}

	/**
	 * @return the adviceDao
	 */
	public AdviceDao getAdviceDao() {
		return adviceDao;
	}

	/**
	 * @param adviceDao the adviceDao to set
	 */
	public void setAdviceDao(AdviceDao adviceDao) {
		this.adviceDao = adviceDao;
	}

}
