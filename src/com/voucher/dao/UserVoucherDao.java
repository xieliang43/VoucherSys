package com.voucher.dao;

import java.util.List;

import com.voucher.entity.UserVoucher;


public interface UserVoucherDao {

	public UserVoucher findInstance(int userId, int viId);

	public void save(UserVoucher userVoucher);

	public List<UserVoucher> findUserInstancese(int userId);

}
