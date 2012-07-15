package com.voucher.action.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.sys.SysRoleService;

public class SysRoleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SysRoleService sysRoleService;

	private int start;
	private int limit;
	private String dir;
	private String sort;
	
	private String id;
	private String roleName;
	private String roleDesc;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<SysRole> list = sysRoleService.getSysRolesByRoleName(pager, roleName);
		int total = sysRoleService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(roleName)) {
			this.sendExtReturn(new ExtReturn(false, "角色名不能为空！"));
			return;
		}
		SysRole role = new SysRole(roleName, roleDesc);
		if(StringUtils.isBlank(id)) {
			sysRoleService.save(role);
		} else {
			role.setId(Integer.valueOf(id));
			sysRoleService.update(role);
		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		int userCountOfRole = sysRoleService.getRoleUsersCount(Integer.valueOf(id));
		if(userCountOfRole > 0) {
			this.sendExtReturn(new ExtReturn(false, "其他用户拥有该角色，还不能删除!"));
			return;
		}
		sysRoleService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}

	/**
	 * @return the sysRoleService
	 */
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	/**
	 * @param sysRoleService the sysRoleService to set
	 */
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
