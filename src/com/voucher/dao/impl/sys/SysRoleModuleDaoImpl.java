package com.voucher.dao.impl.sys;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysRoleModuleDao;
import com.voucher.entity.sys.SysRoleModule;
import com.voucher.exception.PersistenceException;

public class SysRoleModuleDaoImpl extends BaseDaoImpl<SysRoleModule> implements
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
		for(SysRoleModule srm : list) {
			this.delete(srm);
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
