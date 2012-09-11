package com.voucher.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysRoleDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;

public class SysRoleDaoImpl extends BaseDaoImpl implements SysRoleDao {

	@Override
	public int getTotalCount() {
		return this.getSysRoles().size();
	}

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
	public List<SysRole> getSysRoles() {
		String hql = "from SysRole sr";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<SysRole>();
	}

	@Override
	public void save(SysRole role) {
		try {
			this.getJpaTemplate().persist(role);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysRole role) {
		try {
			this.getJpaTemplate().merge(role);
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
	public SysRole findById(int id) {
		try {
			return this.getJpaTemplate().find(SysRole.class, id);
		} catch (DataAccessException e) {
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
