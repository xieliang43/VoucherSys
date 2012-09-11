package com.voucher.dao.sys.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysFieldDao;
import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;

public class SysFieldDaoImpl extends BaseDaoImpl implements SysFieldDao {

	@Override
	public List<SysField> getAllEnabledSysFields() {
		String hql = "from SysField sf where sf.enabled = 1 order by field asc, sortOrder asc";
		try {
			return this.createQuery(hql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName) {
		String hql = "from SysField sf where sf.fieldName = :fieldName";
		try {
			Query query = this.createPagerQuery(SysField.class, hql, pager);
			query.setParameter("fieldName", fieldName);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysField> getAllSysFields(ExtPager pager) {
		String hql = "from SysField sf";
		try {
			Query query = this.createPagerQuery(SysField.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalCount() {
		String hql = "from SysField sf";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void save(SysField field) {
		try {
			this.getJpaTemplate().persist(field);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysField field) {
		try {
			this.getJpaTemplate().merge(field);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(final int id) {
		SysField field = this.findById(id);
		try {
			this.getJpaTemplate().remove(field);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SysField findById(int id) {
		try {
			return this.getJpaTemplate().find(SysField.class, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
