package com.voucher.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.DistanceDao;
import com.voucher.entity.Distance;
import com.voucher.pojo.ExtPager;
import com.voucher.service.DistanceService;

public class DistanceServiceImpl implements DistanceService {
	private DistanceDao distanceDao;

	@Override
	public List<Distance> getEnabledDistances() {
		List<Distance> list = getDistanceDao().getEnabledDistances();
		Collections.sort(list, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Distance d1 = (Distance)o1;
				Distance d2 = (Distance)o2;
				return Integer.valueOf(d1.getName()) - Integer.valueOf(d2.getName());
			}});
		return list;
	}

	/**
	 * @return the distanceDao
	 */
	public DistanceDao getDistanceDao() {
		return distanceDao;
	}

	/**
	 * @param distanceDao the distanceDao to set
	 */
	public void setDistanceDao(DistanceDao distanceDao) {
		this.distanceDao = distanceDao;
	}

	@Override
	public List<Distance> getAllDistancesByName(ExtPager pager, String name) {
		List<Distance> list = null;
		if(StringUtils.isBlank(name)) {
			list = distanceDao.getAllDistances(pager);
		} else {
			list = distanceDao.getAllDistancesByName(pager, name);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return distanceDao.getAllDistances(null).size();
	}

	@Override
	public void save(Distance distance) {
		distanceDao.save(distance);
	}

	@Override
	public void update(Distance distance) {
		Distance oldDistance = distanceDao.findById(distance.getId());
		distance.setCreateDate(oldDistance.getCreateDate());
		distanceDao.update(distance);
	}

	@Override
	public void deleteById(int id) {
		distanceDao.deleteById(id);
	}

}
