package com.voucher.service.sys.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.sys.SysRoleDao;
import com.voucher.dao.sys.SysUserDao;
import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.entity.sys.SysUser;
import com.voucher.entity.sys.SysUserRole;
import com.voucher.pojo.ExtPager;
import com.voucher.service.sys.SysUserService;
import com.voucher.util.DateUtil;
import com.voucher.util.MD5;

public class SysUserServiceImpl implements SysUserService {
	private static final String RESET_PASSWORD = "123456";
	private SysUserDao sysUserDao;
	private SysRoleDao sysRoleDao;
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public void save(SysUser user) {
		sysUserDao.save(user);
	}

	@Override
	public void update(SysUser user) {
		sysUserDao.update(user);
	}

	@Override
	public void delete(SysUser user) {
		sysUserRoleDao.deleteByUserId(user.getId());
		sysUserDao.delete(user);
	}

	@Override
	public SysUser findUserById(int userId) {
		return sysUserDao.findUserById(userId);
	}

	@Override
	public SysUser findUserByAccountAndPassword(String account, String password) {
		SysUser user = sysUserDao.findUserByAccountAndPassword(account, password);
		return user;
	}

	/**
	 * @return the userDao
	 */
	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
	public List<SysUser> findUsersByRealName(ExtPager pager, String realName) {
		List<SysUser> list = null;
		if(StringUtils.isBlank(realName)) {
			list = sysUserDao.findUsers(pager);
		} else {
			list = sysUserDao.findUsersByRealName(pager, realName);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return sysUserDao.getTotalCount();
	}

	@Override
	public void deleteById(int userId) {
		this.delete(this.findUserById(userId));
	}

	@Override
	public void resetPassword(SysUser user) {
		String pwd = MD5.getInstance().encrypt(RESET_PASSWORD);
		user.setPassword(pwd);
		user.setErrorCount((short)0);
		
		this.update(user);
	}

	@Override
	public void save(SysUser user, List<String> roleIds) {
		this.save(user);
		for(String roleId : roleIds) {
			SysRole role = sysRoleDao.findById(Integer.valueOf(roleId));
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			
			sysUserRoleDao.save(userRole);
		}
	}

	@Override
	public void update(SysUser user, List<String> roleIds) {
		this.update(user);
		
		sysUserRoleDao.deleteByUserId(user.getId());
		
		for(String roleId : roleIds) {
			SysRole role = sysRoleDao.findById(Integer.valueOf(roleId));
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			
			sysUserRoleDao.save(userRole);
		}
	}

	/**
	 * @return the sysRoleDao
	 */
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	/**
	 * @param sysRoleDao the sysRoleDao to set
	 */
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	/**
	 * @return the sysUserRoleDao
	 */
	public SysUserRoleDao getSysUserRoleDao() {
		return sysUserRoleDao;
	}

	/**
	 * @param sysUserRoleDao the sysUserRoleDao to set
	 */
	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

}
