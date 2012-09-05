package com.voucher.constants;

import java.io.File;

/**
 * web中使用的常量
 * 
 */
public interface WebConstants {
	/** 超时提醒 */
	public static final String TIME_OUT = "{\"error\":true,\"msg\":\"登录超时,请重新登录！\"}";
	/** 保存session中的admin用户key */
	public static final String CURRENT_USER = "CURRENT_USER";
	
	public static final String FIELDS = "fields";
	public static final String CITYMAP = "cityMap";
	
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	
	public static final String SHOP = "shop";
	public static final String VOUCHER = "voucher";
	public static final String DOT_REG = "\\.";
	public static final String DOT = ".";
	public static final String FILE_SEPARATOR = File.separator;
	public static final String FORWARD_SLASH = "/";
	public static final String UPLOAD = "upload";
	public static final String MINUS = "-";
	public static final String IMAGE_DIR = "examples";
}
