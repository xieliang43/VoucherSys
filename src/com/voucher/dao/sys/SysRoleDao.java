package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtPager;

public interface SysRoleDao extends BaseDao<SysRole> {
	List<SysRole> getSysRoles(ExtPager pager);
	List<SysRole> getSysRolesByRoleName(ExtPager pager, String roleName);
	SysRole getSysRoleByRoleName(String roleName);
}
