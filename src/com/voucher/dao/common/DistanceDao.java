package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.Distance;
import com.voucher.pojo.ExtPager;

public interface DistanceDao extends BaseDao<Distance> {

	List<Distance> getEnabledDistances();

	List<Distance> getAllDistances(ExtPager pager);

	List<Distance> getAllDistancesByName(ExtPager pager, String name);

}
