package com.voucher.dao.sys.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysModuleDao;
import com.voucher.entity.sys.SysModule;
import com.voucher.pojo.ExtPager;

public class SysModuleDaoImpl extends BaseDaoImpl implements SysModuleDao {

	@Override
	public List<SysModule> getModules() {
		String hql = "from SysModule sm";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysModule> getAllSysModules(ExtPager pager) {
		String hql = "from SysModule sm";
		if (!StringUtils.isBlank(pager.getDir())
				&& !StringUtils.isBlank(pager.getSort())) {
			hql = hql + " order by " + pager.getSort() + " " + pager.getDir();
		} else {
			hql = hql + " ORDER BY PARENT_ID asc, DISPLAY_INDEX asc";
		}
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart())
					.setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysModule> getSysModulesByModuleName(ExtPager pager,
			String moduleName) {
		String hql = "from SysModule sm where sm.moduleName = :moduleName";
		if (!StringUtils.isBlank(pager.getDir())
				&& !StringUtils.isBlank(pager.getSort())) {
			hql = hql + " order by " + pager.getSort() + " " + pager.getDir();
		} else {
			hql = hql + " ORDER BY PARENT_ID asc, DISPLAY_INDEX asc";
		}
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart())
					.setMaxResults(pager.getLimit());
			query.setParameter("moduleName", moduleName);
			return query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalCount() {
		return this.getModules().size();
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
	public void save(SysModule module) {
		try {
			this.getJpaTemplate().persist(module);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysModule module) {
		try {
			this.getJpaTemplate().merge(module);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
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
	public SysModule findById(int id) {
		try {
			return this.getJpaTemplate().find(SysModule.class, id);
		} catch (DataAccessException e) {
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
