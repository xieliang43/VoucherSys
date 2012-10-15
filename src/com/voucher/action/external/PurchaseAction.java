package com.voucher.action.external;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.common.User;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceConcurrentException;
import com.voucher.service.common.UserService;
import com.voucher.service.common.VoucherService;
import com.voucher.util.Base64Encoder;
import com.voucher.vo.JsonVO;

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
			JsonVO jErrorVO = new JsonVO(ZERO, "代金券不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if (StringUtils.isBlank(token)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "用户标志不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if (StringUtils.isBlank(userId)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "用户不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		User user = userService.findUserById(Integer.valueOf(userId));
		if(user == null) {
			JsonVO jErrorVO = new JsonVO(ZERO, "不能找到用户！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String userInfo = user.getId() + user.getPhoneNo() + user.getPassword();
		String userToken = Base64Encoder.encode(userInfo);
		if(!token.equals(userToken)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "用户信息参数有误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		try {
			voucherService.purchaseVoucher(Integer.valueOf(userId), Integer.valueOf(viId));
			JsonVO jVO = new JsonVO(ONE, "领取成功！", Integer.valueOf(1));
			String json = this.convertToJson(jVO);
			this.sendJSonReturn(json);
		} catch (ServiceConcurrentException e) {
			JsonVO jErrorVO = new JsonVO(ZERO, "此代金券已被抢购！", Integer.valueOf(0));
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (NumberFormatException e) {
			JsonVO jErrorVO = new JsonVO(ZERO, "数据错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (DataExistException e) {
			JsonVO jErrorVO = new JsonVO(ZERO, "你已经拥有此类代金券了！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} catch (DataNotFoundException e) {
			JsonVO jErrorVO = new JsonVO(ZERO, "无此类代金券！", null);
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
