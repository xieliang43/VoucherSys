package com.voucher.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.AdviceDao;
import com.voucher.entity.Advice;
import com.voucher.pojo.ExtPager;
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

	@Override
	public List<Advice> findAdviceByMsg(ExtPager pager, String msg) {
		List<Advice> list = null;
		if(StringUtils.isBlank(msg)) {
			list = adviceDao.findAdvices(pager);
		} else {
			list = adviceDao.findAdvicesByMsg(pager, msg);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return adviceDao.getTotalCount();
	}

	@Override
	public void deleteById(int id) {
		adviceDao.deleteById(id);
	}

}
