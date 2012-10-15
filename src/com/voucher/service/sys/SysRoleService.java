package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface SysRoleService extends BaseService<SysRole> {
	int getTotalCount();
	List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName);
	void deleteRoleById(int roleId);
	int getRoleUsersCount(int roleId);
	Map<String, Object> getSysRoles();
}
