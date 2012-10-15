package com.voucher.dao.impl.sys;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.dao.sys.SysUserDao;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

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
		String hql = "from SysUser su where su.account like :account or su.realName like :realName";
		try {
			Query query = this.createPagerQuery(SysUser.class, hql, pager);
			query.setParameter("account", "%" + realName + "%");
			query.setParameter("realName", "%" + realName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysUser> findUsers(ExtPager pager) {
		String hql = "from SysUser su";
		try {
			Query query = this.createPagerQuery(SysUser.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
