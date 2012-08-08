package com.voucher.action;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.User;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceConcurrentException;
import com.voucher.pojo.JsonVO;
import com.voucher.service.UserService;
import com.voucher.service.VoucherService;
import com.voucher.util.Base64Encoder;

public class PurchaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7386334997262307905L;
	
	private String viId;
	private String userId;
	private String token;
	
	private VoucherService voucherService;
	private UserService userService;

	/**
	 * @return the viId
	 */
	public String getViId() {
		return viId;
	}

	/**
	 * @param viId the viId to set
	 */
	public void setViId(String viId) {
		this.viId = viId;
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
	 * @return the voucherService
	 */
	public VoucherService getVoucherService() {
		return voucherService;
	}

	/**
	 * @param voucherService the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public void purchase() {
		if (StringUtils.isBlank(viId)) {
			JsonVO jErrorVO = new JsonVO("0", "代金券不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if (StringUtils.isBlank(token)) {
			JsonVO jErrorVO = new JsonVO("0", "用户标志不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if (StringUtils.isBlank(userId)) {
			JsonVO jErrorVO = new JsonVO("0", "用户不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User user = userService.findUserById(Integer.valueOf(userId));
		if(user == null) {
			JsonVO jErrorVO = new JsonVO("0", "不能找到用户！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String userInfo = user.getId() + user.getPhoneNo() + user.getPassword();
		String userToken = Base64Encoder.encode(userInfo);
		if(!token.equals(userToken)) {
			JsonVO jErrorVO = new JsonVO("0", "用户信息参数有误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		try {
			voucherService.purchaseVoucher(Integer.valueOf(userId), Integer.valueOf(viId));
			JsonVO jVO = new JsonVO("1", "领取成功！", new Integer(1));
			String json = this.convertToJson(jVO);
			this.sendJSonReturn(json);
		} catch (ServiceConcurrentException e) {
			JsonVO jErrorVO = new JsonVO("0", "此代金券已被抢购！", new Integer(0));
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (NumberFormatException e) {
			JsonVO jErrorVO = new JsonVO("0", "数据错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (DataExistException e) {
			JsonVO jErrorVO = new JsonVO("0", "你已经拥有此类代金券了！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (DataNotFoundException e) {
			JsonVO jErrorVO = new JsonVO("0", "无此类代金券！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		}
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
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
