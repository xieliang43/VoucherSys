package com.voucher.service.common;

import java.util.List;

import com.voucher.entity.common.Publish;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface PublishService extends BaseService<Publish> {
	List<Publish> findPublishByMsg(ExtPager pager, String phoneNo);

	int getTotalCount();

	void deletePublishById(int id);
}
