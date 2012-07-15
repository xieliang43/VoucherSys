package com.voucher.dao;

import com.voucher.entity.Merchant;

public interface MerchantDao {
	public void create(Merchant merchant);
	public void update(Merchant merchant);
	public void delete(Merchant merchant);
	public Merchant findMerchantById(int merchantId);
	public Merchant findMerchantByPhoneNo(String phoneNo);
	public Merchant findMerchantByPhoneAndPassword(String phoneNo, String password);
}
