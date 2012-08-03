package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;

public interface SysRoleService {
	int getTotalCount();
	List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName);
	void save(SysRole role);
	void update(SysRole role);
	void deleteById(int roleId);
	int getRoleUsersCount(int roleId);
	Map<String, Object> getSysRoles();
}
