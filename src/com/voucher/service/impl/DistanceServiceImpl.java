package com.voucher.service.impl;

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
		return getDistanceDao().getEnabledDistances();
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
