package com.voucher.service.impl.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.sys.SysModuleDao;
import com.voucher.dao.sys.SysRoleDao;
import com.voucher.dao.sys.SysRoleModuleDao;
import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysModule;
import com.voucher.entity.sys.SysRole;
import com.voucher.entity.sys.SysRoleModule;
import com.voucher.entity.sys.SysUser;
import com.voucher.entity.sys.SysUserRole;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.Tree;
import com.voucher.pojo.TreeMenu;
import com.voucher.service.impl.BaseServiceImpl;
import com.voucher.service.sys.SysModuleService;
import com.voucher.vo.RoleModuleVO;

public class SysModuleServiceImpl extends BaseServiceImpl<SysModule> implements SysModuleService {

	private SysModuleDao sysModuleDao;
	private SysUserRoleDao sysUserRoleDao;
	private SysRoleModuleDao sysRoleModuleDao;
	private SysRoleDao sysRoleDao;
	
	@Override
	public Tree getModulesByUser(SysUser user) {
		List<SysModule> modules = sysModuleDao.findAll(SysModule.class);
		if(modules == null || modules.isEmpty()) {
			return null;
		}
		List<SysUserRole> userRoles = sysUserRoleDao.getSysUserRolesByUser(user.getId());
		if(userRoles == null || userRoles.isEmpty()) {
			return null;
		}
		List<SysRoleModule> roleModules = new ArrayList<SysRoleModule>();
		for(SysUserRole userRole : userRoles) {
			roleModules.addAll(sysRoleModuleDao.getSysRoleModuleByRole(userRole.getRole().getId()));
		}
		if(roleModules.isEmpty()) {
			return null;
		}
		Iterator<SysModule> itr = modules.iterator();
		List<SysModule> list = new ArrayList<SysModule>();
		while(itr.hasNext()) {
			SysModule sysModule = itr.next();
			for(SysRoleModule roleModule : roleModules) {
				if(sysModule.getId() == roleModule.getModule().getId()) {
					list.add(sysModule);
					break;
				}
			}
		}
		TreeMenu menu = new TreeMenu(list);
		return menu.getTreeJson();
	}

	/**
	 * @return the sysModuleDao
	 */
	public SysModuleDao getSysModuleDao() {
		return sysModuleDao;
	}

	/**
	 * @param sysModuleDao the sysModuleDao to set
	 */
	public void setSysModuleDao(SysModuleDao sysModuleDao) {
		this.sysModuleDao = sysModuleDao;
	}

	/**
	 * @return the sysUserRoleDao
	 */
	public SysUserRoleDao getSysUserRoleDao() {
		return sysUserRoleDao;
	}

	/**
	 * @param sysUserRoleDao the sysUserRoleDao to set
	 */
	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

	/**
	 * @return the sysRoleModuleDao
	 */
	public SysRoleModuleDao getSysRoleModuleDao() {
		return sysRoleModuleDao;
	}

	/**
	 * @param sysRoleModuleDao the sysRoleModuleDao to set
	 */
	public void setSysRoleModuleDao(SysRoleModuleDao sysRoleModuleDao) {
		this.sysRoleModuleDao = sysRoleModuleDao;
	}

	@Override
	public List<SysModule> getAllSysModules() {
		return sysModuleDao.findAll(SysModule.class);
	}

	@Override
	public List<SysModule> getSysModulesByModuleName(ExtPager pager, String moduleName) {
		List<SysModule> list = null;
		if(StringUtils.isBlank(moduleName)) {
			list = sysModuleDao.getAllSysModules(pager);
		} else {
			list = sysModuleDao.getSysModulesByModuleName(pager, moduleName);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return sysModuleDao.getTotalCount(SysModule.class);
	}

	@Override
	public Map<String, Object> getRootSysModules() {
		List<SysModule> roots = sysModuleDao.getRootSysModules();
		Map<String, Object> map = new HashMap<String, Object>();
		if(roots != null && !roots.isEmpty()) {
			for(SysModule module : roots) {
				map.put(String.valueOf(module.getId()), module.getModuleName());
			}
		}
		return map;
	}

	@Override
	public List<RoleModuleVO> getSysRoleModulesByRoleId(int roleId) {
		List<SysRoleModule> roleModules = sysRoleModuleDao.getSysRoleModuleByRole(roleId);
		List<RoleModuleVO> list = new ArrayList<RoleModuleVO>();
		for(SysRoleModule rm : roleModules) {
			RoleModuleVO rmvo = new RoleModuleVO(rm.getRole().getId(), rm.getModule().getId());
			list.add(rmvo);
		}
		return list;
	}

	@Override
	public Tree getModuleTree() {
		List<SysModule> list = sysModuleDao.getAllEnabledModules();
		TreeMenu menu = new TreeMenu(list);
		return menu.getTreeJson();
	}

	@Override
	public void saveRoleModules(int roleId, List<Integer> moduleIds) {
		sysRoleModuleDao.deleteSysRoleModuleByRole(roleId);
		
		SysRole role = sysRoleDao.findById(SysRole.class, roleId);
		for(Integer moduleId : moduleIds) {
			SysModule module = sysModuleDao.findById(SysModule.class, moduleId);
			
			SysRoleModule roleModule = new SysRoleModule();
			roleModule.setRole(role);
			roleModule.setModule(module);
			
			sysRoleModuleDao.save(roleModule);
		}
	}

	/**
	 * @return the sysRoleDao
	 */
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	/**
	 * @param sysRoleDao the sysRoleDao to set
	 */
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	@Override
	public int getSysRoleModuleCount(int moduleId) {
		return sysRoleModuleDao.getSysRoleModuleCountByModule(moduleId);
	}

	@Override
	public void deleteModuleById(int id) {
		this.deleteById(SysModule.class, id);
	}

}
