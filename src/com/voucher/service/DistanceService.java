package com.voucher.service;

import java.util.List;

import com.voucher.entity.Distance;
import com.voucher.pojo.ExtPager;

public interface DistanceService {

	List<Distance> getEnabledDistances();

	List<Distance> getAllDistancesByName(ExtPager pager, String name);

	int getTotalCount();

	void save(Distance distance);

	void update(Distance distance);

	void deleteById(int id);

}
