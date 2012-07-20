package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Advice;
import com.voucher.entity.Publish;
import com.voucher.pojo.ExtPager;

public interface PublishDao {
	void save(Publish publish);

	List<Advice> findPublishes(ExtPager pager);

	List<Advice> findPublishesByPhoneNo(ExtPager pager, String phoneNo);

	int getTotalCount();

	void deleteById(int id);
	
	Publish findById(int id);
}
