package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysModule;
import com.voucher.pojo.ExtPager;

public interface SysModuleDao extends BaseDao<SysModule> {

	List<SysModule> getAllEnabledModules();
	
	List<SysModule> getAllSysModules(ExtPager pager);
	
	List<SysModule> getSysModulesByModuleName(ExtPager pager, String moduleName);
	
	List<SysModule> getRootSysModules();
	
}
