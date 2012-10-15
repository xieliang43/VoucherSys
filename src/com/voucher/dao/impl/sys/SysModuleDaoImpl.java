package com.voucher.dao.impl.sys;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysModuleDao;
import com.voucher.entity.sys.SysModule;
import com.voucher.pojo.ExtPager;

public class SysModuleDaoImpl extends BaseDaoImpl<SysModule> implements SysModuleDao {

	@Override
	public List<SysModule> getAllSysModules(ExtPager pager) {
		String hql = "from SysModule sm";
		try {
			Query query = this.createPagerQuery(SysModule.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysModule> getSysModulesByModuleName(ExtPager pager,
			String moduleName) {
		String hql = "from SysModule sm where sm.moduleName = :moduleName";
		try {
			Query query = this.createPagerQuery(SysModule.class, hql, pager);
			query.setParameter("moduleName", moduleName);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysModule> getRootSysModules() {
		String hql = "from SysModule sm where sm.leaf = :leaf order by sm.id asc";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("leaf", (short)0);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysModule> getAllEnabledModules() {
		String hql = "from SysModule sm where sm.isDisplay = :isDisplay order by displayIndex";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("isDisplay", (short)1);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
