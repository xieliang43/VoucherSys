/**
 * 
 */
package com.voucher.dao.impl;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.voucher.pojo.ExtPager;

/**
 * @author LL
 * 
 */
public abstract class BaseDaoImpl extends JpaDaoSupport {
	protected Query createQuery(String query) {
		return this.getJpaTemplate().getEntityManagerFactory()
				.createEntityManager().createQuery(query);
	}

	protected String createQueryString(String hql, ExtPager pager) {
		String tmpHql = hql;
		if (pager != null) {
			if (!StringUtils.isBlank(pager.getDir())
					&& !StringUtils.isBlank(pager.getSort())) {
				tmpHql = hql + " order by " + pager.getSort() + " "
						+ pager.getDir();
			}
		}
		return tmpHql;
	}
}
