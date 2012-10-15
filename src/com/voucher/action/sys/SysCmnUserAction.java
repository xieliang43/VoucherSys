/**
 * 
 */
package com.voucher.action.sys;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.common.User;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.RegionService;
import com.voucher.service.common.UserService;
import com.voucher.util.JackJson;

public class SysCmnUserAction extends BasePagerAction implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4387769796487595587L;
	
	private String id;
	private String phoneNo;
	private String password;
	private String name;
	private String sex;
	private String email;
	private String cityId;
	private String token;
	
	private UserService userService;
	private RegionService regionService;

	private Map<String, Object> session;
	
	public String initUserCities() {
		Map<String, Object> areaMap = regionService.getAllCities();
		session.put("userAreaMap", JackJson.fromObjectToJson(areaMap));
		return SUCCESS;
	}
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<User> list = userService.findUsersByPhoneNo(pager, phoneNo);
		int total = userService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		userService.deleteUserById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
	}

	/**
	 * @return the phone
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
