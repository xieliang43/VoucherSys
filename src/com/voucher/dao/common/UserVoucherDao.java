package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.UserVoucher;
import com.voucher.entity.sys.SysUser;


public interface UserVoucherDao extends BaseDao<UserVoucher> {

	public UserVoucher findInstance(int userId, int viId);

	public List<UserVoucher> findUserInstancese(int userId);

	public List<UserVoucher> findActiveUserVouchers();

	public SysUser findMerchantByUserVoucher(int uvId);

	public UserVoucher findUserVoucherByVoucherInstanceId(int vchId);

}
