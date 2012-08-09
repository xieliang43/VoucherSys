package com.voucher.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.voucher.service.MessageService;
import com.voucher.util.PropertiesLoader;

public class MessageServiceImpl implements MessageService {
	private static Logger logger = Logger.getLogger(MessageServiceImpl.class);

	private final static String comId = PropertiesLoader.getInstance()
			.getCompanyId();
	private final static String userName = PropertiesLoader.getInstance()
			.getUserName();
	private final static String userPwd = PropertiesLoader.getInstance()
			.getUserPwd();
	private final static String smsNumber = PropertiesLoader.getInstance()
			.getSmsNumber();
	private final static String PREFIX = PropertiesLoader.getInstance().getSmsPrefix();

	private final static String MSG_URL = PropertiesLoader.getInstance().getSmsUrl();

	@Override
	public void sendMessage(String phoneNo, String code) {
		String[] arr = new String[7];
		arr[0] = comId;
		arr[1] = userName;
		arr[2] = userPwd;
		arr[3] = phoneNo;
		try {
			arr[4] = URLEncoder.encode(PREFIX + code, "GB2312");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		arr[5] = "";
		arr[6] = smsNumber;
		String url = MessageFormat.format(MSG_URL, arr);
		
		logger.info("url: " + url);
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
			String responseBody = httpClient.execute(httpget, responseHandler);
			logger.info(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			httpClient.getConnectionManager().shutdown();
		}
	}

}
