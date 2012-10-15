/**
 * 
 */
package com.voucher.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.voucher.entity.common.UserVoucher;
import com.voucher.service.common.UserVoucherService;

/**
 *
 */
public class UserVoucherQuartzJob extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(UserVoucherQuartzJob.class);
	private UserVoucherService userVoucherService;

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		List<UserVoucher> userVouchers = getUserVoucherService().getActiveUserVouchersLessThanCurrentDate();
		logger.info("Get user voucher's size: " + userVouchers.size());
		if(userVouchers != null && !userVouchers.isEmpty()) {
			for(UserVoucher userVoucher : userVouchers) {
				userVoucher.setIsActive((short)0);
				getUserVoucherService().updateUserVoucher(userVoucher);
			}
		}
		logger.info("Disable outdated user vouchers has been completed!");
	}

	/**
	 * @return the userVoucherService
	 */
	public UserVoucherService getUserVoucherService() {
		return userVoucherService;
	}

	/**
	 * @param userVoucherService the userVoucherService to set
	 */
	public void setUserVoucherService(UserVoucherService userVoucherService) {
		this.userVoucherService = userVoucherService;
	}
}
