package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;

public interface SysRoleDao {
	int getTotalCount();
	List<SysRole> getSysRoles(ExtPager pager);
	List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName);
	List<SysRole> getSysRoles();
	void save(SysRole role);
	void update(SysRole role);
	void deleteById(int id);
	SysRole findById(int id);
	SysRole getSysRoleByRoleName(String roleName);
}
