package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysModule;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.Tree;
import com.voucher.service.BaseService;
import com.voucher.vo.RoleModuleVO;

public interface SysModuleService extends BaseService<SysModule> {
	public Tree getModulesByUser(SysUser user);
	
	public Tree getModuleTree();
	
	public List<SysModule> getAllSysModules();
	
	public List<SysModule> getSysModulesByModuleName(ExtPager pager, String moduleName);

	public int getTotalCount();
	
	public Map<String, Object> getRootSysModules();
	
	public List<RoleModuleVO> getSysRoleModulesByRoleId(int roleId);
	
	public void saveRoleModules(int roleId, List<Integer> moduleIds);
	
	public int getSysRoleModuleCount(int moduleId);
	
	public void deleteModuleById(int id);
}
