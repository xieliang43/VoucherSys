package com.voucher.dao.impl.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.DistanceDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Distance;
import com.voucher.pojo.ExtPager;

public class DistanceDaoImpl extends BaseDaoImpl<Distance> implements DistanceDao {

	@Override
	public List<Distance> getEnabledDistances() {
		String hql = "from Distance d where d.enabled = :enabled order by d.name asc";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("enabled", (short)1);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Distance>();
	}

	@Override
	public List<Distance> getAllDistances(ExtPager pager) {
		String hql = "from Distance d";
		try {
			Query query = this.createPagerQuery(Distance.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Distance> getAllDistancesByName(ExtPager pager, String name) {
		String hql = "from Distance d where d.name = :name";
		try {
			Query query = this.createPagerQuery(Distance.class, hql, pager);
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
