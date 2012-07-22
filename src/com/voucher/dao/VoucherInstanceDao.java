package com.voucher.dao;

import java.util.List;

import com.voucher.entity.VoucherInstance;

public interface VoucherInstanceDao {

	int getActiveCountByVoucher(int id);
	
	List<VoucherInstance> getActiveVouchersById(int id);

	void save(VoucherInstance vi);

}
