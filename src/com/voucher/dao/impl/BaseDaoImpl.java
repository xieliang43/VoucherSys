/**
 * 
 */
package com.voucher.dao.impl;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.voucher.pojo.ExtPager;
import com.voucher.util.FieldChecker;

/**
 * 
 */
public abstract class BaseDaoImpl extends JpaDaoSupport {
	protected Query createQuery(String query) {
		return this.getJpaTemplate().getEntityManagerFactory()
				.createEntityManager().createQuery(query);
	}

	protected String createQueryString(Class<?> clazz, String hql, ExtPager pager) {
		String tmpHql = hql;
		if (pager != null) {
			if (!StringUtils.isBlank(pager.getDir())
					&& !StringUtils.isBlank(pager.getSort())) {
				if(FieldChecker.getInstance().checkField(clazz, pager.getOriginalSort())) {
					tmpHql = hql + " order by " + pager.getSort() + " "
							+ pager.getDir();
				}
			}
		}
		return tmpHql;
	}
	
	protected Query createPagerQuery(Class clazz, String hql, ExtPager pager) {
		String tmpHql = this.createQueryString(clazz, hql, pager);
		Query query = this.createQuery(tmpHql);
		if(pager != null) {
			query = query.setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
		}
		return query;
	}
}
