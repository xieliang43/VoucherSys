package com.voucher.action.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.sys.SysRole;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.sys.SysRoleService;

public class SysRoleAction extends BasePagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SysRoleService sysRoleService;
	
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
			this.sendExtReturn(new ExtReturn(FALSE, "角色名不能为空！"));
			return;
		}
		SysRole role = new SysRole(roleName, roleDesc);
		if(StringUtils.isBlank(id)) {
			sysRoleService.save(role);
		} else {
			role.setId(Integer.valueOf(id));
			sysRoleService.update(role);
		}
		sendExtReturn(new ExtReturn(TRUE, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		int userCountOfRole = sysRoleService.getRoleUsersCount(Integer.valueOf(id));
		if(userCountOfRole > 0) {
			this.sendExtReturn(new ExtReturn(FALSE, "其他用户拥有该角色，还不能删除!"));
			return;
		}
		sysRoleService.deleteRoleById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
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
