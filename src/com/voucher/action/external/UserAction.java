/**
 * 
 */
package com.voucher.action.external;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.common.User;
import com.voucher.service.common.MessageService;
import com.voucher.service.common.UserService;
import com.voucher.util.Base64Encoder;
import com.voucher.util.MD5;
import com.voucher.vo.JsonVO;
import com.voucher.vo.UserVO;

public class UserAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4387769796487595587L;
	
	private String phoneNo;
	private String password;
	private String cityId;
	private String token;
	
	private UserService userService;
	private MessageService messageService;

	public void userRegister() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User existUser = userService.findUserByPhoneNo(phoneNo);
		if(existUser != null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "此用户已存在！", null);
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
		JsonVO jVO = new JsonVO(ONE, "注册成功！", userVo);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void userLogin() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String encPassword = MD5.getInstance().encrypt(password);
		User user = userService.findUserByPhoneAndPassword(phoneNo, encPassword);
		if(user == null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "用户不存在或密码错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String userInfo = user.getId() + user.getPhoneNo() + user.getPassword();
		String userToken = Base64Encoder.encode(userInfo);

		UserVO userVo = new UserVO(user.getId(), user.getPhoneNo(), userToken);
		JsonVO jVO = new JsonVO(ONE, "登录成功", userVo);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void getVerifyCode() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User user = userService.findUserByPhoneNo(phoneNo);
		if(user != null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "此用户已注册过了！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Integer code = (int) (Math.random()*10000);
		String verifyCode = String.format("%04d", code);
		messageService.sendMessage(phoneNo, verifyCode);
		JsonVO vo = new JsonVO(ONE, "获取验证码成功！", verifyCode);
		String json = this.convertToJson(vo);
		sendJSonReturn(json);
	}
	
	public void getResetCode() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User existUser = userService.findUserByPhoneNo(phoneNo);
		if(existUser == null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "此手机号码未注册！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Integer code = (int) (Math.random()*10000);
		String verifyCode = String.format("%04d", code);
		messageService.sendMessage(phoneNo, verifyCode);
		JsonVO vo = new JsonVO(ONE, "获取验证码成功！", verifyCode);
		String json = this.convertToJson(vo);
		sendJSonReturn(json);
	}
	
	public void resetPassword() {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User existUser = userService.findUserByPhoneNo(phoneNo);
		if(existUser == null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "此手机号码未注册！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String newPwd = MD5.getInstance().encrypt(password);
		existUser.setPassword(newPwd);
		userService.update(existUser);
		JsonVO jVO = new JsonVO(ONE, "密码修改成功！", null);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
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
	 * @return the messageService
	 */
	public MessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
