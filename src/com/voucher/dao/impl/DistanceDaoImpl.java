package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.DistanceDao;
import com.voucher.entity.Distance;
import com.voucher.pojo.ExtPager;

public class DistanceDaoImpl extends BaseDaoImpl implements DistanceDao {

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
		return null;
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

	@Override
	public void save(Distance distance) {
		try {
			this.getJpaTemplate().persist(distance);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Distance distance) {
		this.getJpaTemplate().merge(distance);
	}

	@Override
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(this.findById(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Distance findById(int id) {
		try {
			return this.getJpaTemplate().find(Distance.class, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
