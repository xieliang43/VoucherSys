package com.voucher.dao;

import java.util.List;

import com.voucher.entity.VoucherInstance;

public interface VoucherInstanceDao {

	int getActiveCountByVoucher(int vchId);
	
	List<VoucherInstance> getActiveVoucherInstancesByVoucher(int vchId);

	void save(VoucherInstance vi);

	int getVoucherInstanceCountByVoucher(int vchId);
	
	List<VoucherInstance> getVoucherInstancesByVoucher(int vchId);

}
