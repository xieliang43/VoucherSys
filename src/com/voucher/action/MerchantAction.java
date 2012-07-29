package com.voucher.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.constants.WebConstants;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.MerchantVO;
import com.voucher.service.RegionService;
import com.voucher.service.sys.SysUserService;
import com.voucher.util.JackJson;
import com.voucher.util.MD5;

public class MerchantAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436158914934510514L;

	private String id;
	private String account;
	private String password;
	private String expensePassword;
	private String realName;
	private String sex;
	private String email;
	private String mobile;
	private String cityId;
	private String officePhone;
	private String qqNo;
	private String remark;

	private String oldPassword;
	private String newPassword;
	private String comparePassword;

	private Map<String, Object> session;

	private RegionService regionService;
	private SysUserService sysUserService;

	public String initMerchant() {
		Map<String, Object> areaMap = getRegionService().getAllCities();
		session.put("mchAreaMap", JackJson.fromObjectToJson(areaMap));
		return SUCCESS;
	}

	public void loadMerchant() {
		SysUser user = (SysUser) session.get(WebConstants.CURRENT_USER);
		if (user == null) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		List<MerchantVO> list = new ArrayList<MerchantVO>();
		list.add(createMerchantVO(user));
		int total = 1;

		sendExtGridReturn(new ExtGridReturn(total, list));
	}

	private MerchantVO createMerchantVO(SysUser user) {
		MerchantVO mvo = new MerchantVO(user.getId(), user.getAccount(),
				user.getPassword(), user.getExpensePassword(), user.getRealName(), user.getSex(),
				user.getEmail(), user.getMobile(), user.getOfficePhone(),
				user.getErrorCount(), user.getLastLoginTime(),
				user.getLastLoginIp(), user.getQqNo(), user.getCityId(),
				user.getRemark(), user.getCreateDate());
		return mvo;
	}

	public void update() {
		if (StringUtils.isBlank(account)) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		if (StringUtils.isBlank(realName)) {
			this.sendExtReturn(new ExtReturn(false, "用户名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(expensePassword)) {
			this.sendExtReturn(new ExtReturn(false, "消费密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(email)) {
			this.sendExtReturn(new ExtReturn(false, "邮箱不能为空！"));
			return;
		}
		if (StringUtils.isBlank(mobile)) {
			this.sendExtReturn(new ExtReturn(false, "手机不能为空！"));
			return;
		}
		if (StringUtils.isBlank(officePhone)) {
			this.sendExtReturn(new ExtReturn(false, "电话不能为空！"));
			return;
		}
		if (StringUtils.isBlank(cityId)) {
			this.sendExtReturn(new ExtReturn(false, "城市不能为空！"));
			return;
		}
		if (StringUtils.isBlank(qqNo)) {
			this.sendExtReturn(new ExtReturn(false, "QQ不能为空！"));
			return;
		}
		SysUser user = (SysUser) session.get(WebConstants.CURRENT_USER);
		user.setAccount(account);
		user.setCityId(Integer.valueOf(cityId));
		user.setEmail(email);
		user.setMobile(mobile);
		user.setSex(Short.valueOf(sex));
		user.setRemark(remark);
		user.setRealName(realName);
		user.setQqNo(qqNo);
		user.setOfficePhone(officePhone);
		if(!expensePassword.equals(user.getExpensePassword())) {
			user.setExpensePassword(MD5.getInstance().encrypt(expensePassword));
		}

		sysUserService.update(user);
		sendExtReturn(new ExtReturn(true, "更新成功！"));
	}

	public void changePassword() {
		SysUser user = (SysUser) session.get(WebConstants.CURRENT_USER);
		if (user == null) {
			this.sendExtReturn(new ExtReturn(false, "用户未找到!"));
			return;
		}
		if (StringUtils.isBlank(oldPassword)) {
			this.sendExtReturn(new ExtReturn(false, "原密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(newPassword)) {
			this.sendExtReturn(new ExtReturn(false, "新密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(comparePassword)) {
			this.sendExtReturn(new ExtReturn(false, "确认密码不能为空！"));
			return;
		}
		if (!comparePassword.equals(newPassword)) {
			this.sendExtReturn(new ExtReturn(false, "两次输入的密码不一致！"));
			return;
		}
		String encPassword = MD5.getInstance().encrypt(oldPassword);
		if (!encPassword.equals(user.getPassword())) {
			this.sendExtReturn(new ExtReturn(false, "原密码不正确！请重新输入！"));
			return;
		}
		String encNewPassword = MD5.getInstance().encrypt(newPassword);
		user.setPassword(encNewPassword);
		sysUserService.update(user);

		session.remove(WebConstants.CURRENT_USER);
		this.sendExtReturn(new ExtReturn(true, "修改密码成功！请重新登录！"));
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param account
	 *            the account to set
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
	 * @param password
	 *            the password to set
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
	 * @param realName
	 *            the realName to set
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
	 * @param sex
	 *            the sex to set
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
	 * @param email
	 *            the email to set
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
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the officePhone
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * @param officePhone
	 *            the officePhone to set
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * @return the qqNo
	 */
	public String getQqNo() {
		return qqNo;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
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
	 * @param newPassword
	 *            the newPassword to set
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
	 * @param comparePassword
	 *            the comparePassword to set
	 */
	public void setComparePassword(String comparePassword) {
		this.comparePassword = comparePassword;
	}

	/**
	 * @param qqNo
	 *            the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService
	 *            the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * @return the sysUserService
	 */
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	/**
	 * @param sysUserService
	 *            the sysUserService to set
	 */
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
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

}
