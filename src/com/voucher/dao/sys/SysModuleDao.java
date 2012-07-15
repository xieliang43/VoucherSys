package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysModule;
import com.voucher.pojo.ExtPager;

public interface SysModuleDao {
	List<SysModule> getModules();
	
	List<SysModule> getAllEnabledModules();
	
	List<SysModule> getAllSysModules(ExtPager pager);
	
	List<SysModule> getSysModulesByModuleName(ExtPager pager, String moduleName);
	
	int getTotalCount();
	
	List<SysModule> getRootSysModules();
	
	void save(SysModule module);
	
	void update(SysModule module);
	
	void deleteById(int id);
	
	SysModule findById(int id);
}
