package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public interface VoucherDao extends BaseDao<Voucher> {
	public List<Voucher> getCurrentMerchantVouchers(ExtPager pager,
			SysUser merchant);
	public List<Voucher> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName);
	public void save(Voucher voucher);
	public List<Voucher> findAllEnabledVouchers();
	public List<Voucher> getEnabledVouchersByShop(int shopId);
	public List<Voucher> getCurrentMerchantVouchersByShopName(SysUser merchant,
			String shopName);
	public List<Voucher> getVouchersByShopId(int shopId);
}
