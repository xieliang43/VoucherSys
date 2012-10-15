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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.voucher.constants.WebConstants;
import com.voucher.map.BaiduMap;
import com.voucher.map.Location;
import com.voucher.map.Result;

public class BaiduMapUtil {
	private static Logger logger = Logger.getLogger(BaiduMapUtil.class);
	
	private static final String OUTPUT = PropertiesLoader.getInstance()
			.getBaiduMapsOutput();
	private static final String KEY = PropertiesLoader.getInstance()
			.getBaiduMapsKey();
	private static final String MAPS_URL = PropertiesLoader.getInstance()
			.getBaiduMapsUrl();

	private static BaiduMapUtil instance = new BaiduMapUtil();

	private BaiduMapUtil() {

	}

	public static BaiduMapUtil getInstance() {
		return instance;
	}

	/**
	 * @param region
	 * 			  地区（市）
	 * @param queryAddress
	 *           地址
	 * @return HTTP状态代码,精确度（请参见精确度常数）,纬度,经度
	 */
	public String getLatlngByAddress(String region, String queryAddress) {
		String ret = "";
		if (region != null && !region.equals("")) {
			try {
				region = URLEncoder.encode(region, "UTF-8");// 进行这一步是为了避免乱码
			} catch (UnsupportedEncodingException e1) {
				logger.error("转码失败", e1);
			}
		}
		if (queryAddress != null && !queryAddress.equals("")) {
			try {
				queryAddress = URLEncoder.encode(queryAddress, "UTF-8");// 进行这一步是为了避免乱码
			} catch (UnsupportedEncodingException e1) {
				logger.error("转码失败", e1);
			}
		}
		Object[] arr = new String[4];
		arr[0] = queryAddress;
		arr[1] = region;
		arr[2] = OUTPUT;
		arr[3] = KEY;
		String url = MessageFormat.format(MAPS_URL, arr);
		URL myUrl = null;
		try {
			myUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
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
		return ret;
	}

	public Map<String, Double> getLatLng(String region, String queryAddress) {
		Map<String, Double> map = new HashMap<String, Double>();
		String res = this.getLatlngByAddress(region, queryAddress);
		Gson gson = new Gson();
		BaiduMap baiduMap = gson.fromJson(res, BaiduMap.class);
		Result []results = baiduMap.getResults();
		if(results != null && results.length > 0) {
			Location location = results[0].getLocation();
			map.put(WebConstants.LAT, location.getLat());
			map.put(WebConstants.LNG, location.getLng());
		} else {
			map.put(WebConstants.LAT, .0);
			map.put(WebConstants.LNG, .0);
		}
		return map;
	}
}
