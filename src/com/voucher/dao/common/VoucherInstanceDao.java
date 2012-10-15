package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.VoucherInstance;
import com.voucher.exception.PersistenceConcurrentException;

public interface VoucherInstanceDao extends BaseDao<VoucherInstance> {

	int getActiveCountByVoucher(int vchId);
	
	List<VoucherInstance> getActiveVoucherInstancesByVoucher(int vchId);

	int getVoucherInstanceCountByVoucher(int vchId);
	
	List<VoucherInstance> getVoucherInstancesByVoucher(int vchId);

	void update(VoucherInstance vchInst) throws PersistenceConcurrentException;

}
