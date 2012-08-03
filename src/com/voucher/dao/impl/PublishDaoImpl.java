package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.PublishDao;
import com.voucher.entity.Advice;
import com.voucher.entity.Publish;
import com.voucher.pojo.ExtPager;

public class PublishDaoImpl extends BaseDaoImpl implements PublishDao {

	@Override
	public void save(Publish publish) {
		try {
			this.getJpaTemplate().persist(publish);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Advice> findPublishes(ExtPager pager) {
		String hql = "from Publish pub";
		hql = this.createQueryString(Publish.class, hql, pager);
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Advice> findPublishesByPhoneNo(ExtPager pager, String phoneNo) {
		String hql = "from Publish pb where pb.phoneNo like :phoneNo";
		hql = this.createQueryString(Publish.class, hql, pager);
		
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("phoneNo", "%" + phoneNo + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalCount() {
		String hql = "from Publish pb";
		Query query = this.createQuery(hql);
		List<Advice> list = query.getResultList();
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(findById(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Publish findById(int id) {
		try {
			return this.getJpaTemplate().find(Publish.class, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
