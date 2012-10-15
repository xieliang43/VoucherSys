package com.voucher.service.impl.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.sys.SysRoleDao;
import com.voucher.dao.sys.SysRoleModuleDao;
import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;
import com.voucher.service.impl.BaseServiceImpl;
import com.voucher.service.sys.SysRoleService;

public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
	private SysRoleDao sysRoleDao;
	private SysRoleModuleDao sysRoleModuleDao;
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public int getTotalCount() {
		return sysRoleDao.getTotalCount(SysRole.class);
	}

	@Override
	public List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName) {
		List<SysRole> list = null;
		if(StringUtils.isBlank(roleName)) {
			list = sysRoleDao.getSysRoles(pager);
		} else {
			list = sysRoleDao.getSysRolesByRoleName(pager, roleName);
		}
		return list;
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

	@Override
	public void deleteRoleById(int roleId) {
		sysRoleModuleDao.deleteSysRoleModuleByRole(roleId);
		sysRoleDao.deleteById(SysRole.class, roleId);
	}

	/**
	 * @return the sysRoleModuleDao
	 */
	public SysRoleModuleDao getSysRoleModuleDao() {
		return sysRoleModuleDao;
	}

	/**
	 * @param sysRoleModuleDao the sysRoleModuleDao to set
	 */
	public void setSysRoleModuleDao(SysRoleModuleDao sysRoleModuleDao) {
		this.sysRoleModuleDao = sysRoleModuleDao;
	}

	@Override
	public int getRoleUsersCount(int roleId) {
		return sysUserRoleDao.getSysUserRolesByRole(roleId).size();
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

	@Override
	public Map<String, Object> getSysRoles() {
		List<SysRole> sysRoles = sysRoleDao.findAll(SysRole.class);
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysRoles != null && !sysRoles.isEmpty()) {
			for(SysRole role : sysRoles) {
				map.put(String.valueOf(role.getId()), role.getRoleName());
			}
		}
		return map;
	}

}
