package com.voucher.service;

import java.util.List;

import com.voucher.entity.Advice;
import com.voucher.pojo.ExtPager;

public interface AdviceService {
	void saveAdvice(Advice advice);

	List<Advice> findAdviceByMsg(ExtPager pager, String msg);

	int getTotalCount();

	void deleteById(int id);
}
