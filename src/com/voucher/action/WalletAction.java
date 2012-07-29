package com.voucher.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.User;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.JsonVO;
import com.voucher.pojo.UserVoucherVO;
import com.voucher.service.UserService;
import com.voucher.service.UserVoucherService;
import com.voucher.util.Base64Encoder;
import com.voucher.util.MD5;

public class WalletAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String token;
	private String viId;
	private String uvId;
	private String expensePassword;
	
	private UserVoucherService userVoucherService;
	private UserService userService;

	public void getWallet() {
		if(validateUser()) {
			List<UserVoucherVO> userVouchers = userVoucherService.getUserInstances(Integer.valueOf(userId));
			JsonVO jVO = new JsonVO("1", "抢购成功！", userVouchers);
			String json = this.convertToJson(jVO);
			this.sendJSonReturn(json);
		}
	}

	private boolean validateUser() {
		if (StringUtils.isBlank(token)) {
			JsonVO jErrorVO = new JsonVO("0", "用户标志不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		if (StringUtils.isBlank(userId)) {
			JsonVO jErrorVO = new JsonVO("0", "用户不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		User user = getUserService().findUserById(Integer.valueOf(userId));
		if(user == null) {
			JsonVO jErrorVO = new JsonVO("0", "不能找到用户！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		String userInfo = user.getId() + user.getPhoneNo() + user.getPassword();
		String userToken = Base64Encoder.encode(userInfo);
		if(!token.equals(userToken)) {
			JsonVO jErrorVO = new JsonVO("0", "用户信息参数有误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		return true;
	}
	
	public void useWallet() {
		if(!validateUser() || !validateMerchant()){
			return;
		}
		String result = userVoucherService.useUserVoucher(Integer.valueOf(uvId));
		if("00".equals(result)) {
			JsonVO jErrorVO = new JsonVO("0", "不能找到代金券！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} else if("01".equals(result)) {
			JsonVO jErrorVO = new JsonVO("1", "此代金券已使用！", new Integer(0));
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
		} else if("02".equals(result)) {
			JsonVO jResultVO = new JsonVO("1", "代金券使用成功！", new Integer(1));
			String json = this.convertToJson(jResultVO);
			sendJSonReturn(json);
		}
	}

	private boolean validateMerchant() {
		if (StringUtils.isBlank(expensePassword)) {
			JsonVO jErrorVO = new JsonVO("0", "消费密码不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		String encExpensePassword = MD5.getInstance().encrypt(expensePassword);
		SysUser merchant = userVoucherService.findMerchantByUserVoucher(Integer.valueOf(getUvId()));
		if(merchant == null) {
			JsonVO jErrorVO = new JsonVO("0", "不能找到商家！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		if(!encExpensePassword.equals(merchant.getExpensePassword())) {
			JsonVO jErrorVO = new JsonVO("0", "商家消费密码不正确！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return false;
		}
		return true;
	}

	/**
	 * @return the userVoucherService
	 */
	public UserVoucherService getUserVoucherService() {
		return userVoucherService;
	}

	/**
	 * @param userVoucherService the userVoucherService to set
	 */
	public void setUserVoucherService(UserVoucherService userVoucherService) {
		this.userVoucherService = userVoucherService;
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

	/**
	 * @return the mchPassword
	 */
	public String getExpensePassword() {
		return expensePassword;
	}

	/**
	 * @param mchPassword the mchPassword to set
	 */
	public void setExpensePassword(String expensePassword) {
		this.expensePassword = expensePassword;
	}

	/**
	 * @return the uvId
	 */
	public String getUvId() {
		return uvId;
	}

	/**
	 * @param uvId the uvId to set
	 */
	public void setUvId(String uvId) {
		this.uvId = uvId;
	}
}
