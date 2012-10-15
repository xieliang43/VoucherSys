package com.voucher.action.sys;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.action.BasePagerAction;
import com.voucher.constants.WebConstants;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.sys.SysRoleService;
import com.voucher.service.sys.SysUserRoleService;
import com.voucher.service.sys.SysUserService;
import com.voucher.util.JackJson;
import com.voucher.util.MD5;
import com.voucher.vo.MerchantVO;
import com.voucher.vo.UserRoleVO;

public class SysMerchantAction extends BasePagerAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7149867750521120516L;

	private String userId;
	private String oldPassword;
	private String newPassword;
	private String comparePassword;
	
	private SysUserService sysUserService;
	private SysUserRoleService sysUserRoleService;
	private SysRoleService sysRoleService;

	private Map<String, Object> session;
	
	private String id;
	private String account;
	private String password;
	private String expensePassword;
	private String realName;
	private String sex;
	private String email;
	private String mobile;
	private String officePhone;
	private String qqNo;
	private String remark;
	
	private List<String> roleIds;
	
	public void changePassword() {
		if (StringUtils.isBlank(userId)) {
			this.sendExtReturn(new ExtReturn(FALSE, "用户ID不能为空！"));
			return;
		}
		if (StringUtils.isBlank(oldPassword)) {
			this.sendExtReturn(new ExtReturn(FALSE, "原密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(newPassword)) {
			this.sendExtReturn(new ExtReturn(FALSE, "新密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(comparePassword)) {
			this.sendExtReturn(new ExtReturn(FALSE, "确认密码不能为空！"));
			return;
		}
		if (!comparePassword.equals(newPassword)) {
			this.sendExtReturn(new ExtReturn(FALSE, "两次输入的密码不一致！"));
			return;
		}
		
		SysUser user = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(user == null) {
			this.sendExtReturn(new ExtReturn(FALSE, "用户未找到!"));
			return;
		}
		String encOldPassword = MD5.getInstance().encrypt(oldPassword);
		if(!encOldPassword.equals(user.getPassword())) {
			this.sendExtReturn(new ExtReturn(FALSE, "原密码不正确！请重新输入！"));
			return;
		}
		String encNewPassword = MD5.getInstance().encrypt(newPassword);
		user.setPassword(encNewPassword);
		sysUserService.update(user);
		
		session.remove(WebConstants.CURRENT_USER);
		this.sendExtReturn(new ExtReturn(TRUE, "修改密码成功！请重新登录！"));
	}
	
	public String sysUserInit() {
		Map<String, Object> map = getSysRoleService().getSysRoles();
		session.put("roleMap", JackJson.fromObjectToJson(map));
		return SUCCESS;
	}
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<MerchantVO> list = sysUserService.findUsersByRealName(pager, realName);
		int total = sysUserService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (getRoleIds() == null || getRoleIds().isEmpty()) {
			sendExtReturn(new ExtReturn(FALSE, "请至少选择一个角色！"));
			return;
		}
		if (StringUtils.isBlank(account)) {
			this.sendExtReturn(new ExtReturn(FALSE, "账号不能为空！"));
			return;
		}
		if (StringUtils.isBlank(realName)) {
			this.sendExtReturn(new ExtReturn(FALSE, "用户名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(expensePassword)) {
			this.sendExtReturn(new ExtReturn(FALSE, "消费密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(email)) {
			this.sendExtReturn(new ExtReturn(FALSE, "邮箱不能为空！"));
			return;
		}
		if (StringUtils.isBlank(mobile)) {
			this.sendExtReturn(new ExtReturn(FALSE, "手机不能为空！"));
			return;
		}
		if (StringUtils.isBlank(officePhone)) {
			this.sendExtReturn(new ExtReturn(FALSE, "电话不能为空！"));
			return;
		}
		
		String encExpensePassword = MD5.getInstance().encrypt(expensePassword);
		if(StringUtils.isBlank(id)) {
			if (StringUtils.isBlank(password)) {
				this.sendExtReturn(new ExtReturn(FALSE, "密码不能为空！"));
				return;
			}
			String encPassword = MD5.getInstance().encrypt(password);
			SysUser user = new SysUser(account, encPassword, encExpensePassword, realName, Short.valueOf(sex), email, mobile, officePhone, qqNo, remark);
			sysUserService.save(user, roleIds);
		} else {
			SysUser oldUser = sysUserService.findMerchantById(Integer.valueOf(id));
			oldUser.setAccount(account);
			oldUser.setRealName(realName);
			oldUser.setSex(Short.valueOf(sex));
			oldUser.setEmail(email);
			oldUser.setMobile(mobile);
			oldUser.setOfficePhone(officePhone);
			oldUser.setRemark(remark);
			oldUser.setQqNo(qqNo);
			
			sysUserService.update(oldUser, roleIds);
		}
		sendExtReturn(new ExtReturn(TRUE, "保存成功！"));
	}
	
	public void loadUserRole() {
		if (StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(FALSE, "用户ID不能为空！"));
			return;
		}
		List<UserRoleVO> list = sysUserRoleService.getUserRoleByUserId(Integer.valueOf(id));
		String json = this.convertToJson(list);
		this.sendJSonReturn(json);
	}
	
	public void delete() {
		if(StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(FALSE, "id不能为空！"));
			return;
		}
		sysUserService.deleteMerchantById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
	}
	
	public void resetPassword() {
		if(StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(FALSE, "id不能为空！"));
			return;
		}
		SysUser user = sysUserService.findMerchantById(Integer.valueOf(id));
		if(user == null) {
			this.sendExtReturn(new ExtReturn(FALSE, "没有找到该用户！"));
			return;
		}
		sysUserService.resetPassword(user);
		sendExtReturn(new ExtReturn(TRUE, "重置密码成功！"));
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the comparePassword
	 */
	public String getComparePassword() {
		return comparePassword;
	}

	/**
	 * @param comparePassword the comparePassword to set
	 */
	public void setComparePassword(String comparePassword) {
		this.comparePassword = comparePassword;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @return the sysUserService
	 */
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	/**
	 * @param sysUserService the sysUserService to set
	 */
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the officePhone
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * @param officePhone the officePhone to set
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the roleIds
	 */
	public List<String> getRoleIds() {
		return roleIds;
	}

	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * @return the qqNo
	 */
	public String getQqNo() {
		return qqNo;
	}

	/**
	 * @param qqNo the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	/**
	 * @return the sysUserRoleService
	 */
	public SysUserRoleService getSysUserRoleService() {
		return sysUserRoleService;
	}

	/**
	 * @param sysUserRoleService the sysUserRoleService to set
	 */
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	/**
	 * @return the expensePassword
	 */
	public String getExpensePassword() {
		return expensePassword;
	}

	/**
	 * @param expensePassword the expensePassword to set
	 */
	public void setExpensePassword(String expensePassword) {
		this.expensePassword = expensePassword;
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
}
