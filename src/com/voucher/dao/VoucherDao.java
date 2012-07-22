package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public interface VoucherDao {
	public void create(Voucher voucher);
	public void delete(Voucher voucher);
	public void update(Voucher voucher);
	public Voucher findVoucherById(int voucherId);
	public List<Voucher> findAll();
	public List<Voucher> getCurrentMerchantVouchers(ExtPager pager,
			SysUser merchant);
	public List<Voucher> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName);
	public List<Voucher> getCurrentMerchantVouchers(SysUser merchant);
	public void deleteById(int id);
	public void save(Voucher voucher);
}
