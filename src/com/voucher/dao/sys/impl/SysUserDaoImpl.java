package com.voucher.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysUserDao;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public class SysUserDaoImpl extends BaseDaoImpl implements SysUserDao {

	@Override
	public void save(SysUser user) {
		try {
			this.getJpaTemplate().persist(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysUser user) {
		try {
			this.getJpaTemplate().merge(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(SysUser user) {
		try {
			this.getJpaTemplate().remove(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SysUser findUserById(int userId) {
		try {
			return this.getJpaTemplate().find(SysUser.class, userId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SysUser findUserByAccountAndPassword(String account, String password) {
		String hql = "from SysUser u where u.account=:account and u.password=:password";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("account", account);
			query.setParameter("password", password);
			List<SysUser> users = query.getResultList();
			if(users !=null && !users.isEmpty()) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysUser> findUsersByRealName(ExtPager pager, String realName) {
		String hql = "from SysUser su where su.realName = :realName";
		if(!StringUtils.isBlank(pager.getDir()) && !StringUtils.isBlank(pager.getSort())) {
			hql = hql + " order by " + pager.getSort() + " " + pager.getDir();
		}
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("realName", realName);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysUser> findUsers(ExtPager pager) {
		String hql = "from SysUser su";
		if(!StringUtils.isBlank(pager.getDir()) && !StringUtils.isBlank(pager.getSort())) {
			hql = hql + " order by " + pager.getSort() + " " + pager.getDir();
		}
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalCount() {
		return findUsers().size();
	}

	@Override
	public List<SysUser> findUsers() {
		String hql = "from SysUser su";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<SysUser>();
	}

	@Override
	public SysUser findUserByAccount(String account) {
		String hql = "from SysUser su where su.account = :account";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("account", account);
			List<SysUser> users = query.getResultList();
			if(users !=null && !users.isEmpty()) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SysUser findUserByPhoneNo(String mobile) {
		String hql = "from SysUser su where su.mobile = :mobile";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("mobile", mobile);
			List<SysUser> users = query.getResultList();
			if(users !=null && !users.isEmpty()) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
