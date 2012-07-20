package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Advice;
import com.voucher.pojo.ExtPager;

public interface AdviceDao {
	void save(Advice advice);

	int getTotalCount();

	List<Advice> findAdvicesByMsg(ExtPager pager, String msg);

	List<Advice> findAdvices(ExtPager pager);

	void deleteById(int id);
}
