package com.voucher.dao.impl.sys;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysRoleDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;

public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao {

	@Override
	public List<SysRole> getSysRoles(ExtPager pager) {
		String hql = "from SysRole sr";
		try {
			Query query = this.createPagerQuery(SysRole.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName) {
		String hql = "from SysRole sr where sr.roleName = :roleName";
		try {
			Query query = this.createPagerQuery(SysRole.class, hql, pager);
			query.setParameter("roleName", roleName);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SysRole getSysRoleByRoleName(String roleName) {
		String hql = "from SysRole sr where sr.roleName = :roleName";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("roleName", roleName);
			List<SysRole> roles = query.getResultList();
			if(roles != null && !roles.isEmpty()) {
				return roles.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
