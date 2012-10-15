package com.voucher.dao.impl.sys;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysFieldDao;
import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;

public class SysFieldDaoImpl extends BaseDaoImpl<SysField> implements SysFieldDao {

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

}
