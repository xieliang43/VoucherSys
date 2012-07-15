/**
 * 
 */
package com.voucher.service;

import com.voucher.entity.Merchant;

/**
 * @author LL
 *
 */
public interface MerchantService {
	public void save(Merchant merchant);
	public void update(Merchant merchant);
	public void delete(Merchant merchant);
	public Merchant findMerchantById(int userId);
	public Merchant findMerchantByPhoneNo(String phoneNo);
	public Merchant findMerchantByPhoneAndPassword(String phoneNo, String password);
}
