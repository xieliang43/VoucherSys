package com.voucher.action.sys;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.google.code.kaptcha.Constants;
import com.voucher.action.BaseAction;
import com.voucher.constants.WebConstants;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.Tree;
import com.voucher.service.sys.SysModuleService;
import com.voucher.service.sys.SysUserService;
import com.voucher.util.MD5;

public class SysLoginAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4638067142523800046L;
	
	private String account;
	private String password;
	private String comparePassword;
	private String expensePassword;
	private String realName;
	private String sex;
	private String email;
	private String mobile;
	private String officePhone;
	private String qqNo;
	private String captcha;

	private SysUserService sysUserService;
	private SysModuleService sysModuleService;

	private Map<String, Object> session;
	
	public void login() {
		try {
			if (StringUtils.isBlank(account)) {
				sendExtReturn(new ExtReturn(false, "帐号不能为空！"));
				return;
			}
			if (StringUtils.isBlank(password)) {
				sendExtReturn(new ExtReturn(false, "密码不能为空！"));
				return;
			}
			SysUser user = sysUserService.findUserByAccountAndPassword(account, password);
			if(user == null) {
				sendExtReturn(new ExtReturn(false, "用户名或者密码错误!"));
				return;
			}
			user.setLastLoginTime(new Date());
			user.setLastLoginIp(getIpAddr());
			sysUserService.update(user);
			session.put(WebConstants.CURRENT_USER, user);
			
			sendExtReturn(new ExtReturn(true, "success"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void register() {
		if (StringUtils.isBlank(account)) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		if (StringUtils.isBlank(password)) {
			this.sendExtReturn(new ExtReturn(false, "密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(comparePassword)) {
			this.sendExtReturn(new ExtReturn(false, "确认密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(expensePassword)) {
			this.sendExtReturn(new ExtReturn(false, "消费密码不能为空！"));
			return;
		}
		if (StringUtils.isBlank(realName)) {
			this.sendExtReturn(new ExtReturn(false, "用户名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(sex)) {
			this.sendExtReturn(new ExtReturn(false, "性别不能为空！"));
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
		if (StringUtils.isBlank(qqNo)) {
			this.sendExtReturn(new ExtReturn(false, "QQ不能为空！"));
			return;
		}
		if(!password.equals(comparePassword)) {
			this.sendExtReturn(new ExtReturn(false, "登陆密码输入不一致！"));
			return;
		}
		SysUser existAccountUser = sysUserService.findUserByAccount(account);
		if(existAccountUser != null) {
			this.sendExtReturn(new ExtReturn(false, "此用户名已存在，请从新输入！"));
			return;
		}
		SysUser existPhoneUser = sysUserService.findUserByPhoneNo(mobile);
		if(existPhoneUser != null) {
			this.sendExtReturn(new ExtReturn(false, "此电话用户已存在，请从新输入！"));
			return;
		}
		String encPassword = MD5.getInstance().encrypt(password);
		String encExpensePassword = MD5.getInstance().encrypt(expensePassword);
		SysUser user = new SysUser(account, encPassword, encExpensePassword, realName, Short.valueOf(sex), email, mobile, officePhone, qqNo, "普通商家");
		user.setCreateDate(new Date());
		user.setLastLoginIp("0.0.0.0");
		
		sysUserService.register(user);
		sendExtReturn(new ExtReturn(true, "注册成功！"));
	}
	
	public void treeMenu() {
		SysUser user = (SysUser) session.get(WebConstants.CURRENT_USER);
		// 得到的是根菜单
		if(user == null) {
			this.sendExtReturn(new ExtReturn(false, "用户不能为空！"));
			return;
		}
		Tree tree = sysModuleService.getModulesByUser(user);
		String json = this.convertToJson(tree.getChildren());
		sendJSonReturn(json);
	}
	
	public void logout() {
		session.remove(WebConstants.CURRENT_USER);
		
		this.sendExtReturn(new ExtReturn(true, "退出系统成功！"));
	}
	
	public void resetPassword() {
		if (StringUtils.isBlank(account)) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		if (StringUtils.isBlank(email)) {
			this.sendExtReturn(new ExtReturn(false, "邮箱不能为空！"));
			return;
		}
		if (StringUtils.isBlank(captcha)) {
			this.sendExtReturn(new ExtReturn(false, "验证码不能为空！"));
			return;
		}
		
		Object sessionCaptcha =  session.get(Constants.KAPTCHA_SESSION_KEY);
		if(null == sessionCaptcha){
			this.sendExtReturn(new ExtReturn(false, "验证码已经失效!请重新输入新的验证码！"));
			return;
		}
		if (!captcha.equalsIgnoreCase((String)sessionCaptcha)) {
			this.sendExtReturn(new ExtReturn(false, "验证码输入不正确,请重新输入！"));
			return;
		}
		session.remove(Constants.KAPTCHA_SESSION_KEY);
		
		SysUser user = sysUserService.findUserByAccount(account);
		if(user == null) {
			this.sendExtReturn(new ExtReturn(false, "不能找到用户！"));
			return;
		}
		if(!email.equals(user.getEmail())) {
			this.sendExtReturn(new ExtReturn(false, "注册邮箱不正确！"));
			return;
		}
		
		String result = sysUserService.findPassword(user);
		
		if ("01".equals(result)) {
			this.sendExtReturn(new ExtReturn(true, "邮件发送成功！请登录注册邮箱查收！"));
		} else if ("00".equals(result)) {
			this.sendExtReturn(new ExtReturn(false, "邮件发送失败！"));
		} else {
			this.sendExtReturn(new ExtReturn(false, result));
		}
	}
	
	private String getIpAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		final String unkown = "unknown";
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || unkown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unkown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unkown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || unkown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || unkown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
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
	 * @return the userService
	 */
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
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

	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @param captcha the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
