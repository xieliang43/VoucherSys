package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.AdviceDao;
import com.voucher.entity.Advice;
import com.voucher.pojo.ExtPager;

public class AdviceDaoImpl extends BaseDaoImpl implements AdviceDao {

	@Override
	public void save(Advice advice) {
		try {
			this.getJpaTemplate().persist(advice);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getTotalCount() {
		String hql = "from Advice ad";
		Query query = this.createQuery(hql);
		List<Advice> list = query.getResultList();
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<Advice> findAdvicesByMsg(ExtPager pager, String msg) {
		String hql = "from Advice ad where ad.msg like :msg";
		hql = this.createQueryString(Advice.class, hql, pager);
		
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("msg", "%" + msg + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Advice> findAdvices(ExtPager pager) {
		String hql = "from Advice ad";
		hql = this.createQueryString(Advice.class, hql, pager);
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(this.findById(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Advice findById(int id) {
		try {
			return this.getJpaTemplate().find(Advice.class, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
