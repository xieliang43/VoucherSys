package com.voucher.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.voucher.constants.WebConstants;

public class GoogleMapUtil {
	private static Logger logger = Logger.getLogger(GoogleMapUtil.class);
	private static final String SENSOR = "true";
	private static final String OUTPUT = "csv";
	private static final String KEY = "ABQIAAAAqaGKijD7euSpqDeVsNA85xTT3OL8VXjPlPTFW7n7OgOFwXoSnxT7IP1pHznaiGwWMvsEq_SkxvESLw";
	private static final String MAPS_URL = "http://maps.google.com/maps/geo?q={0}&output={1}&sensor={2}&key={3}";

	private static GoogleMapUtil instance = new GoogleMapUtil();
	
	private GoogleMapUtil() {
		
	}
	
	public static GoogleMapUtil getInstance() {
		return instance;
	}
	/**
	 * 利用googlemap api 通过 HTTP 进行地址解析
	 * 
	 * @param address
	 *            地址
	 * @return HTTP状态代码,精确度（请参见精确度常数）,纬度,经度
	 */
	public String getLatlngByAddress(String address) {
		String ret = "";
		if (address != null && !address.equals("")) {
			try {
				address = URLEncoder.encode(address, "UTF-8");// 进行这一步是为了避免乱码
			} catch (UnsupportedEncodingException e1) {
				logger.error("转码失败", e1);
			}
			String[] arr = new String[4];
			arr[0] = address;
			arr[1] = OUTPUT;
			arr[2] = SENSOR;
			arr[3] = KEY;
			String url = MessageFormat.format(MAPS_URL, arr);
			URL urlmy = null;
			try {
				urlmy = new URL(url);
				HttpURLConnection con = (HttpURLConnection) urlmy
						.openConnection();
				HttpURLConnection.setFollowRedirects(true);
				con.setInstanceFollowRedirects(false);
				con.connect();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String s = "";
				StringBuffer sb = new StringBuffer("");
				while ((s = br.readLine()) != null) {
					sb.append(s + "\r\n");
				}
				ret = "" + sb;
			} catch (MalformedURLException e) {
				logger.error("通过http方式获取地址信息失败", e);
			} catch (IOException e) {
				logger.error("文件读取失败", e);
			}
		}
		return ret;
	}
	
	public Map<String, Double> getLatLng(String addr) {
		Map<String, Double> map = new HashMap<String, Double>();
		String ret = this.getLatlngByAddress(addr);
		if(!StringUtils.isBlank(ret)) {
			String []res = ret.split(",");
			map.put(WebConstants.LAT, Double.valueOf(res[2]));
			map.put(WebConstants.LNG, Double.valueOf(res[3]));
		}
		return map;
	}
}
