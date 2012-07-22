package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Distance;
import com.voucher.pojo.ExtPager;

public interface DistanceDao {

	List<Distance> getEnabledDistances();

	List<Distance> getAllDistances(ExtPager pager);

	List<Distance> getAllDistancesByName(ExtPager pager, String name);

	void save(Distance distance);

	void update(Distance distance);

	void deleteById(int id);
	
	Distance findById(int id);

}
