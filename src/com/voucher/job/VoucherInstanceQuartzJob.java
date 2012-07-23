/**
 * 
 */
package com.voucher.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.voucher.entity.Voucher;
import com.voucher.service.VoucherService;

/**
 *
 */
public class VoucherInstanceQuartzJob extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(VoucherInstanceQuartzJob.class);
	private VoucherService voucherService;

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		List<Voucher> vouchers = voucherService.getEnabledVouchersLessThanCurrentDate();
		if(vouchers != null && !vouchers.isEmpty()) {
			for(Voucher voucher : vouchers) {
				voucher.setEnabled((short)0);
				voucherService.updateVoucher(voucher);
			}
		}
		logger.info("Disable outdated vouchers has been completed!");
	}

	/**
	 * @return the voucherService
	 */
	public VoucherService getVoucherService() {
		return voucherService;
	}

	/**
	 * @param voucherService the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

}
