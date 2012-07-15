package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysRoleModule;

public interface SysRoleModuleDao {
	List<SysRoleModule> getSysRoleModuleByRole(int roleId);
	void deleteSysRoleModuleByRole(int roleId);
	
	void save(SysRoleModule roleModule);
	
	void delete(SysRoleModule roleModule);
	
	int getSysRoleModuleCountByModule(int moduleId);
}
