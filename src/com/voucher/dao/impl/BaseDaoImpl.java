/**
 * 
 */
package com.voucher.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.voucher.dao.BaseDao;
import com.voucher.pojo.ExtPager;
import com.voucher.util.FieldChecker;

/**
 * 
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	@PersistenceContext
	protected EntityManager em;
	
	protected Query createQuery(String query) {
		return em.createQuery(query);
	}

	protected String createQueryString(Class<T> clazz, String hql, ExtPager pager) {
		String tmpHql = hql;
		if (pager != null) {
			if (!StringUtils.isBlank(pager.getDir())
					&& !StringUtils.isBlank(pager.getOriginalSort())) {
				if(FieldChecker.getInstance().checkField(clazz, pager.getOriginalSort())) {
					tmpHql = hql + " order by " + pager.getSort() + " "
							+ pager.getDir();
				}
			}
		}
		return tmpHql;
	}
	
	protected Query createPagerQuery(Class<T> clazz, String hql, ExtPager pager) {
		String tmpHql = this.createQueryString(clazz, hql, pager);
		Query query = this.createQuery(tmpHql);
		if(pager != null) {
			query = query.setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
		}
		return query;
	}
	
	public void save(T entity) {
		try {
			em.persist(entity);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public void delete(T entity) {
		try {
			em.remove(em.merge(entity));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public void update(T entity) {
		try {
			em.merge(entity);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public T findById(Class<T> clazz, int id) {
		try {
			return em.find(clazz, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteById(Class<T> clazz, int id) {
		try {
			em.remove(this.findById(clazz, id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<T> findAll(Class<T> clazz) {
		String hql = "from " + clazz.getSimpleName();
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public int getTotalCount(Class<T> clazz) {
		return this.findAll(clazz).size();
	}
}
