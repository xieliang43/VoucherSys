package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.Publish;
import com.voucher.pojo.ExtPager;

public interface PublishDao extends BaseDao<Publish> {
	void save(Publish publish);

	List<Publish> findPublishes(ExtPager pager);

	List<Publish> findPublishesByPhoneNo(ExtPager pager, String phoneNo);

}
