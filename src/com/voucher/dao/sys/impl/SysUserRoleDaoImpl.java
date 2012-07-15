package com.voucher.dao.sys.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysUserRole;

public class SysUserRoleDaoImpl extends BaseDaoImpl implements SysUserRoleDao {

	@Override
	public List<SysUserRole> getSysUserRolesByUser(int userId) {
		String hql = "from SysUserRole sur where sur.user.id = :userId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("userId", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysUserRole> getSysUserRolesByRole(int roleId) {
		String hql = "from SysUserRole sur where sur.role.id = :roleId";
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
	public void save(SysUserRole userRole) {
		try {
			this.getJpaTemplate().persist(userRole);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysUserRole userRole) {
		try {
			this.getJpaTemplate().merge(userRole);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(SysUserRole userRole) {
		try {
			this.getJpaTemplate().remove(userRole);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByUserId(int userId) {
		List<SysUserRole> list = this.getSysUserRolesByUser(userId);
		for(SysUserRole userRole : list) {
			this.delete(this.getJpaTemplate().merge(userRole));
		}
	}

}
