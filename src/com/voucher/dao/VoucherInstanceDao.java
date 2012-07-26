package com.voucher.dao;

import java.util.List;

import com.voucher.entity.VoucherInstance;
import com.voucher.exception.PersistenceConcurrentException;

public interface VoucherInstanceDao {

	int getActiveCountByVoucher(int vchId);
	
	List<VoucherInstance> getActiveVoucherInstancesByVoucher(int vchId);

	void save(VoucherInstance vi);

	int getVoucherInstanceCountByVoucher(int vchId);
	
	List<VoucherInstance> getVoucherInstancesByVoucher(int vchId);

	VoucherInstance getVoucherInstancesById(int viId);

	void update(VoucherInstance vchInst) throws PersistenceConcurrentException;

}
