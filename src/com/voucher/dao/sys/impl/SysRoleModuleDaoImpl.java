package com.voucher.dao.sys.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysRoleModuleDao;
import com.voucher.entity.sys.SysRoleModule;
import com.voucher.exception.PersistenceException;

public class SysRoleModuleDaoImpl extends BaseDaoImpl implements
		SysRoleModuleDao {

	@Override
	public List<SysRoleModule> getSysRoleModuleByRole(int roleId) {
		String hql = "from SysRoleModule srm where srm.role.id = :roleId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("roleId", roleId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteSysRoleModuleByRole(int roleId) {
		List<SysRoleModule> list = this.getSysRoleModuleByRole(roleId);
		for(SysRoleModule rm : list) {
			this.delete(this.getJpaTemplate().merge(rm));
		}
	}

	@Override
	public void save(SysRoleModule roleModule) {
		try {
			this.getJpaTemplate().persist(roleModule);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(SysRoleModule roleModule) {
		try {
			this.getJpaTemplate().remove(roleModule);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getSysRoleModuleCountByModule(int moduleId) {
		String hql = "from SysRoleModule srm where srm.module.id = :moduleId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("moduleId", moduleId);
			return query.getResultList().size();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException("query exception");
		}
		
	}

}
