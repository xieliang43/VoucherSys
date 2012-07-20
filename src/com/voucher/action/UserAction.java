/**
 * 
 */
package com.voucher.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.entity.User;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.JsonVO;
import com.voucher.pojo.UserVO;
import com.voucher.service.RegionService;
import com.voucher.service.UserService;
import com.voucher.util.Base64Encoder;
import com.voucher.util.JackJson;
import com.voucher.util.MD5;

public class UserAction extends BaseAction implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4387769796487595587L;
	
	private int start;
	private int limit;
	private String dir;
	private String sort;
	
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
	
	public void userRegister() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User existUser = userService.findUserByPhoneNo(phoneNo);
		if(existUser != null) {
			JsonVO jErrorVO = new JsonVO("0", "此用户已存在！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String newPwd = MD5.getInstance().encrypt(password);
		User user = new User();
		user.setPhoneNo(phoneNo);
		user.setPassword(newPwd);
		user.setCreateDate(new Date());
		
		userService.save(user);
		
		String userToken = Base64Encoder.encode(user.getId() + user.getPhoneNo() + user.getPassword());
		UserVO userVo = new UserVO(user.getId(), user.getPhoneNo(), userToken);
		JsonVO jVO = new JsonVO("1", "注册成功！", userVo);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void userLogin() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String encPassword = MD5.getInstance().encrypt(password);
		User user = userService.findUserByPhoneAndPassword(phoneNo, encPassword);
		if(user == null) {
			JsonVO jErrorVO = new JsonVO("0", "用户不存在或密码错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String userInfo = user.getId() + user.getPhoneNo() + user.getPassword();
		String userToken = Base64Encoder.encode(userInfo);

		UserVO userVo = new UserVO(user.getId(), user.getPhoneNo(), userToken);
		JsonVO jVO = new JsonVO("1", "登录成功", userVo);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void getVerifyCode() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User user = userService.findUserByPhoneNo(phoneNo);
		if(user != null) {
			JsonVO jErrorVO = new JsonVO("0", "此用户已注册过了！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Integer code = (int) (Math.random()*10000);
		String verifyCode = String.valueOf(code);
		JsonVO vo = new JsonVO("1", "获取验证码成功！", verifyCode);
		String json = this.convertToJson(vo);
		sendJSonReturn(json);
	}
	
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
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		userService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
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
