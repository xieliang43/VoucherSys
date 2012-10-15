package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysRoleModule;

public interface SysRoleModuleDao extends BaseDao<SysRoleModule> {
	List<SysRoleModule> getSysRoleModuleByRole(int roleId);
	void deleteSysRoleModuleByRole(int roleId);
	int getSysRoleModuleCountByModule(int moduleId);
}
