package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysModule;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.RoleModuleVO;
import com.voucher.pojo.Tree;

public interface SysModuleService {
	public Tree getModulesByUser(SysUser user);
	
	public Tree getModuleTree();
	
	public List<SysModule> getAllSysModules();
	
	public List<SysModule> getSysModulesByModuleName(ExtPager pager, String moduleName);

	public int getTotalCount();
	
	public Map<String, Object> getRootSysModules();
	
	public void saveModule(SysModule module);
	
	public void updateModule(SysModule module);
	
	public void deleteById(int id);
	
	public List<RoleModuleVO> getSysRoleModulesByRoleId(int roleId);
	
	public void saveRoleModules(int roleId, List<Integer> moduleIds);
	
	public int getSysRoleModuleCount(int moduleId);
}
