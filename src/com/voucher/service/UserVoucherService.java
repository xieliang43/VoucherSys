package com.voucher.service;

import java.util.List;

import com.voucher.entity.UserVoucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.UserVoucherVO;

public interface UserVoucherService {
	public List<UserVoucherVO> getUserInstances(int userId);

	public List<UserVoucher> getActiveUserVouchersLessThanCurrentDate();

	public void updateUserVoucher(UserVoucher userVoucher);

	public SysUser findMerchantByUserVoucher(int uvId);
	
	public String useUserVoucher(int uvId);
}
