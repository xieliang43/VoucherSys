package com.voucher.service.impl.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.common.DistanceDao;
import com.voucher.entity.common.Distance;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.DistanceService;
import com.voucher.service.impl.BaseServiceImpl;

public class DistanceServiceImpl extends BaseServiceImpl<Distance> implements DistanceService {
	private DistanceDao distanceDao;

	@Override
	public List<Distance> getEnabledDistances() {
		List<Distance> list = getDistanceDao().getEnabledDistances();
		Collections.sort(list, new Comparator<Distance>() {

			@Override
			public int compare(Distance o1, Distance o2) {
				return Integer.valueOf(o1.getName()) - Integer.valueOf(o2.getName());
			}});
		Distance dist = new Distance();
		dist.setId(0);
		dist.setName("不限");
		list.add(0, dist);
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
	public void update(Distance distance) {
		Distance oldDistance = distanceDao.findById(Distance.class, distance.getId());
		distance.setCreateDate(oldDistance.getCreateDate());
		distanceDao.update(distance);
	}

	@Override
	public void deleteDistanceById(int id) {
		this.deleteById(Distance.class, id);
	}

}
