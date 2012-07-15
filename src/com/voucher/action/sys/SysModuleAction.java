package com.voucher.action.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.action.BaseAction;
import com.voucher.entity.sys.SysModule;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.RoleModuleVO;
import com.voucher.pojo.Tree;
import com.voucher.service.sys.SysModuleService;
import com.voucher.util.JackJson;

public class SysModuleAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256016275291809404L;
	
	private SysModuleService sysModuleService;
	
	private int start;
	private int limit;
	/**
	 * 大写的ASC or DESC
	 */
	private String dir;
	/**
	 * 排序的字段
	 */
	private String sort;
	
	private String id;
	private String moduleName;
	private String moduleUrl;
	private String parentId;
	private String leaf;
	private String expanded;
	private String displayIndex;
	private String isDisplay;
	private String iconCss;
	private String information;
	
	private String roleId;
	private String moduleIds;

	private Map<String, Object> session;
	
	public String module() {
		Map<String, Object> map = sysModuleService.getRootSysModules();
		map.put("0", "主菜单");
		session.put("moduleMap", JackJson.fromObjectToJson(map));
		return SUCCESS;
	}
	
	public void loadTreeMenu() {
		Tree tree = sysModuleService.getModuleTree();
		String json = JackJson.fromObjectToJson(tree.getChildren());
		json = json.replaceAll("\"leaf", "\"checked\":false,\"leaf");
		this.sendJSonReturn(json);
	}
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		
		List<SysModule> list = getSysModuleService().getSysModulesByModuleName(pager, moduleName);
		int total = getSysModuleService().getTotalCount();
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(moduleName)) {
			sendExtReturn(new ExtReturn(false, "字段不能为空！"));
			return;
		}
		if (StringUtils.isBlank(iconCss)) {
			sendExtReturn(new ExtReturn(false, "样式不能为空！"));
			return;
		}
		SysModule module = new SysModule(moduleName, moduleUrl, Integer.valueOf(parentId), Short.valueOf(leaf), Short.valueOf(expanded), Short.valueOf(displayIndex), Short.valueOf(isDisplay), null, iconCss, information);
		if(StringUtils.isBlank(id)) {
			sysModuleService.saveModule(module);
		} else {
			module.setId(Integer.valueOf(id));
			sysModuleService.updateModule(module);
		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		int roleModuleCount = sysModuleService.getSysRoleModuleCount(Integer.valueOf(id));
		if(roleModuleCount > 0) {
			sendExtReturn(new ExtReturn(false, "其他用户拥有该模块，还不能删除!"));
			return;
		}
		sysModuleService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}
	
	public void getSysModulesByRole() {
		if (StringUtils.isBlank(roleId)) {
			sendExtReturn(new ExtReturn(false, "角色主键不能为空！"));
			return;
		}
		List<RoleModuleVO> list = sysModuleService.getSysRoleModulesByRoleId(Integer.valueOf(roleId));
		this.sendJSonReturn(JackJson.fromObjectToJson(list));
	}
	
	public void saveRoleModules() {
		List<Integer> modulesIdList = new ArrayList<Integer>();
		if (StringUtils.isBlank(roleId)) {
			sendExtReturn(new ExtReturn(false, "角色主键不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getModuleIds())) {
			sendExtReturn(new ExtReturn(false, "选择的资源不能为空！"));
			return;
		} else {
			String[] modules = StringUtils.split(getModuleIds(), ",");
			if (null == modules || modules.length == 0) {
				sendExtReturn(new ExtReturn(false, "选择的资源不能为空！"));
				return;
			}
			for (int i = 0; i < modules.length; i++) {
				modulesIdList.add(Integer.valueOf((modules[i])));
			}
		}
		
		sysModuleService.saveRoleModules(Integer.valueOf(roleId), modulesIdList);
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}

	/**
	 * @return the sysModuleService
	 */
	public SysModuleService getSysModuleService() {
		return sysModuleService;
	}

	/**
	 * @param sysModuleService the sysModuleService to set
	 */
	public void setSysModuleService(SysModuleService sysModuleService) {
		this.sysModuleService = sysModuleService;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the moduleUrl
	 */
	public String getModuleUrl() {
		return moduleUrl;
	}

	/**
	 * @param moduleUrl the moduleUrl to set
	 */
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the leaf
	 */
	public String getLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return the expanded
	 */
	public String getExpanded() {
		return expanded;
	}

	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	/**
	 * @return the displayIndex
	 */
	public String getDisplayIndex() {
		return displayIndex;
	}

	/**
	 * @param displayIndex the displayIndex to set
	 */
	public void setDisplayIndex(String displayIndex) {
		this.displayIndex = displayIndex;
	}

	/**
	 * @return the isDisplay
	 */
	public String getIsDisplay() {
		return isDisplay;
	}

	/**
	 * @param isDisplay the isDisplay to set
	 */
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * @return the iconCss
	 */
	public String getIconCss() {
		return iconCss;
	}

	/**
	 * @param iconCss the iconCss to set
	 */
	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
	}

	/**
	 * @return the information
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * @param information the information to set
	 */
	public void setInformation(String information) {
		this.information = information;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the moduleIds
	 */
	public String getModuleIds() {
		return moduleIds;
	}

	/**
	 * @param moduleIds the moduleIds to set
	 */
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	
}
