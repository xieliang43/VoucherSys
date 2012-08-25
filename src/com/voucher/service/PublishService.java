package com.voucher.service;

import java.util.List;

import com.voucher.entity.Publish;
import com.voucher.pojo.ExtPager;

public interface PublishService {
	void savePublish(Publish publish);

	List<Publish> findPublishByMsg(ExtPager pager, String phoneNo);

	int getTotalCount();

	void deleteById(int id);
}
