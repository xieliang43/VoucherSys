package com.voucher.service.common;

import java.util.List;

import com.voucher.entity.common.Advice;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface AdviceService extends BaseService<Advice> {
	List<Advice> findAdviceByMsg(ExtPager pager, String msg);

	int getTotalCount();
	
	public void deleteAdviceById(int id);
}
