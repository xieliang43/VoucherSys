package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysUserRole;

public interface SysUserRoleDao extends BaseDao<SysUserRole> {
	List<SysUserRole> getSysUserRolesByUser(int userId);
	List<SysUserRole> getSysUserRolesByRole(int roleId);
	void deleteByUserId(int userId);
}
