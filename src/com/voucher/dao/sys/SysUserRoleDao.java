package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysUserRole;

public interface SysUserRoleDao {
	List<SysUserRole> getSysUserRolesByUser(int userId);
	List<SysUserRole> getSysUserRolesByRole(int roleId);
	void save(SysUserRole userRole);
	void update(SysUserRole userRole);
	void delete(SysUserRole userRole);
	void deleteByUserId(int userId);
}
