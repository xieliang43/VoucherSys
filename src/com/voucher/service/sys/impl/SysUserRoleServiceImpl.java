package com.voucher.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysUserRole;
import com.voucher.pojo.UserRoleVO;
import com.voucher.service.sys.SysUserRoleService;

public class SysUserRoleServiceImpl implements SysUserRoleService {
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public List<UserRoleVO> getUserRoleByUserId(int userId) {
		List<UserRoleVO> userRoles = new ArrayList<UserRoleVO>();
		List<SysUserRole> list = sysUserRoleDao.getSysUserRolesByUser(userId);
		if (list != null && !list.isEmpty()) {
			for (SysUserRole sur : list) {
				UserRoleVO urVo = new UserRoleVO(sur.getId(), sur.getUser()
						.getId(), sur.getRole().getId());
				userRoles.add(urVo);
			}
		}
		return userRoles;
	}

	/**
	 * @return the sysUserRoleDao
	 */
	public SysUserRoleDao getSysUserRoleDao() {
		return sysUserRoleDao;
	}

	/**
	 * @param sysUserRoleDao
	 *            the sysUserRoleDao to set
	 */
	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

}
