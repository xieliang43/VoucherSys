package com.voucher.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import com.voucher.dao.sys.SysRoleDao;
import com.voucher.dao.sys.SysUserDao;
import com.voucher.dao.sys.SysUserRoleDao;
import com.voucher.entity.sys.SysRole;
import com.voucher.entity.sys.SysUser;
import com.voucher.entity.sys.SysUserRole;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.MerchantVO;
import com.voucher.service.sys.SysUserService;
import com.voucher.util.MD5;
import com.voucher.util.PropertiesLoader;

public class SysUserServiceImpl implements SysUserService {
	private static final String SMTP_AUTH_VALUE = PropertiesLoader.getInstance().getSmtpAuth();

	private static final String SMTP_HOST_VALUE = PropertiesLoader.getInstance().getSmtpHost();

	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	private static final String MAIL_SMTP_HOST = "mail.smtp.host";

	private static final Logger logger = Logger
			.getLogger(SysUserServiceImpl.class);

	private static final String RESET_PASSWORD = PropertiesLoader.getInstance()
			.getResetPassword();

	private static final String EMAIL_ACCOUNT = PropertiesLoader.getInstance().getMailAccount();
	private static final String EMAIL_PASSWORD = PropertiesLoader.getInstance().getMailPassword();
	
	private SysUserDao sysUserDao;
	private SysRoleDao sysRoleDao;
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public void save(SysUser user) {
		sysUserDao.save(user);
	}

	@Override
	public void update(SysUser user) {
		sysUserDao.update(user);
	}

	@Override
	public void delete(SysUser user) {
		sysUserRoleDao.deleteByUserId(user.getId());
		sysUserDao.delete(user);
	}

	@Override
	public SysUser findUserById(int userId) {
		return sysUserDao.findUserById(userId);
	}

	@Override
	public SysUser findUserByAccountAndPassword(String account, String password) {
		SysUser user = sysUserDao.findUserByAccountAndPassword(account,
				password);
		return user;
	}

	/**
	 * @return the userDao
	 */
	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
	public List<MerchantVO> findUsersByRealName(ExtPager pager, String realName) {
		List<SysUser> list = null;
		List<MerchantVO> resList = new ArrayList<MerchantVO>();
		if (StringUtils.isBlank(realName)) {
			list = sysUserDao.findUsers(pager);
		} else {
			list = sysUserDao.findUsersByRealName(pager, realName);
		}
		if (list != null && !list.isEmpty()) {
			for (SysUser user : list) {
				MerchantVO mvo = new MerchantVO(user.getId(),
						user.getAccount(), user.getPassword(),
						user.getExpensePassword(), user.getRealName(),
						user.getSex(), user.getEmail(), user.getMobile(),
						user.getOfficePhone(), user.getErrorCount(),
						user.getLastLoginTime(), user.getLastLoginIp(),
						user.getQqNo(), user.getCityId(), user.getRemark(),
						user.getCreateDate());
				resList.add(mvo);
			}
		}
		return resList;
	}

	@Override
	public int getTotalCount() {
		return sysUserDao.getTotalCount();
	}

	@Override
	public void deleteById(int userId) {
		this.delete(this.findUserById(userId));
	}

	@Override
	public void resetPassword(SysUser user) {
		String pwd = MD5.getInstance().encrypt(RESET_PASSWORD);
		user.setPassword(pwd);
		user.setErrorCount((short) 0);

		this.update(user);
	}

	@Override
	public void save(SysUser user, List<String> roleIds) {
		this.save(user);
		for (String roleId : roleIds) {
			SysRole role = sysRoleDao.findById(Integer.valueOf(roleId));
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			userRole.setRole(role);

			sysUserRoleDao.save(userRole);
		}
	}

	@Override
	public void update(SysUser user, List<String> roleIds) {
		this.update(user);

		sysUserRoleDao.deleteByUserId(user.getId());

		for (String roleId : roleIds) {
			SysRole role = sysRoleDao.findById(Integer.valueOf(roleId));
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			userRole.setRole(role);

			sysUserRoleDao.save(userRole);
		}
	}

	/**
	 * @return the sysRoleDao
	 */
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	/**
	 * @param sysRoleDao
	 *            the sysRoleDao to set
	 */
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	/**
	 * @return the sysUserRoleDao
	 */
	public SysUserRoleDao getSysUserRoleDao() {
		return sysUserRoleDao;
	}

	/**
	 * @param sysUserRoleDao
	 *            the sysUserRoleDao to set
	 */
	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

	@Override
	public SysUser findUserByAccount(String account) {
		return sysUserDao.findUserByAccount(account);
	}

	@Override
	public SysUser findUserByPhoneNo(String mobile) {
		return sysUserDao.findUserByPhoneNo(mobile);
	}

	@Override
	public void register(SysUser user) {
		this.save(user);
		SysRole role = sysRoleDao.getSysRoleByRoleName("provider");
		SysUserRole userRole = new SysUserRole();
		userRole.setRole(role);
		userRole.setUser(user);

		this.sysUserRoleDao.save(userRole);
	}

	/**
	 * 
	 * 发送邮件
	 */
	private boolean execSend(String address, String title, String body)
			throws Exception {
		Properties props = new Properties();
		// 定义邮件服务器的地址
		props.put(MAIL_SMTP_HOST, SMTP_HOST_VALUE);
		props.put(MAIL_SMTP_AUTH, SMTP_AUTH_VALUE);
		// 取得Session
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(EMAIL_ACCOUNT,
								EMAIL_PASSWORD);
					}
				});
		MimeMessage message = new MimeMessage(session);
		// 邮件标题
		message.setSubject(title);
		// 发件人的邮件地址
		message.setFrom(new InternetAddress(EMAIL_ACCOUNT));
		// 接收邮件的地址
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				address));
		// 邮件发送的时间日期
		message.setSentDate(new Date());
		// 新建一个MimeMultipart对象用来存放BodyPart对象 related意味着可以发送html格式的邮件
		Multipart mp = new MimeMultipart("related");
		// 新建一个存放信件内容的BodyPart对象
		BodyPart bodyPart = new MimeBodyPart();// 正文
		// 给BodyPart对象设置内容和格式/编码方式
		bodyPart.setContent(body, "text/html;charset=utf-8");
		// 将BodyPart加入到MimeMultipart对象中
		mp.addBodyPart(bodyPart);
		// 设置邮件内容
		message.setContent(mp);
		// 发送邮件
		Transport.send(message);
		logger.info("向邮件地址: " + address + "发送邮件成功！");
		return true;
	}

	@Override
	public String findPassword(SysUser user) {
		String newPassword = MD5.getInstance().encrypt(RESET_PASSWORD);
		user.setPassword(newPassword);

		this.update(user);
		String title = "亲爱的 " + user.getAccount() + "，请重新设置你的帐户密码";
		String body = "你的密码已被重置为：" + RESET_PASSWORD + ", 请登陆后立即修改！";
		try {
			this.execSend(user.getEmail(), title, body);
		} catch (Exception e) {
			return "00";
		}
		return "01";
	}

}
