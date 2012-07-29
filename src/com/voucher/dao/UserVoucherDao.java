package com.voucher.dao;

import java.util.List;

import com.voucher.entity.UserVoucher;
import com.voucher.entity.sys.SysUser;


public interface UserVoucherDao {

	public UserVoucher findInstance(int userId, int viId);

	public void save(UserVoucher userVoucher);

	public List<UserVoucher> findUserInstancese(int userId);

	public void update(UserVoucher userVoucher);

	public List<UserVoucher> findActiveUserVouchers();

	public SysUser findMerchantByUserVoucher(int uvId);

	public UserVoucher findUserVoucherById(int uvId);

}
