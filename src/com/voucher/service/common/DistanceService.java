package com.voucher.service.common;

import java.util.List;

import com.voucher.entity.common.Distance;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface DistanceService extends BaseService<Distance> {

	List<Distance> getEnabledDistances();

	List<Distance> getAllDistancesByName(ExtPager pager, String name);

	int getTotalCount();
	
	public void deleteDistanceById(int id);

}
