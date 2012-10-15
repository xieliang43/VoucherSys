package com.voucher.service.impl.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.common.AdviceDao;
import com.voucher.entity.common.Advice;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.AdviceService;
import com.voucher.service.impl.BaseServiceImpl;

public class AdviceServiceImpl extends BaseServiceImpl<Advice> implements AdviceService {
	private AdviceDao adviceDao;

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
		return adviceDao.getTotalCount(Advice.class);
	}

	@Override
	public void deleteAdviceById(int id) {
		this.deleteById(Advice.class, id);
	}

}
