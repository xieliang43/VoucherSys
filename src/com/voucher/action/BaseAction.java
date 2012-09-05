package com.voucher.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.voucher.constants.WebConstants;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.JsonVO;

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8647134177875947159L;

	protected HttpServletResponse getHttpServletResponse() {
		return ServletActionContext.getResponse();
	}

	protected String convertToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	protected void sendErrorResoponse(String msg) {
		JsonVO jErrorVO = new JsonVO("0", msg, null);
		String json = this.convertToJson(jErrorVO);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().setContentType(
				"application/json;charset=UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendExtReturn(ExtReturn extReturn) {
		String json = this.convertToJson(extReturn);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().setContentType(
				"application/json;charset=UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendExtGridReturn(ExtGridReturn extGridReturn) {
		String json = this.convertToJson(extGridReturn);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendJSonReturn(String json) {
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().setContentType(
				"application/json;charset=UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void uploadVoucherImage(SysUser merchant, File srcFile,
			String fileName) {
		String toFileName = retrieveFileName(merchant, WebConstants.VOUCHER,
				fileName);
		File dst = new File(toFileName);
		writeFile(srcFile, dst);
	}

	protected void uploadShopImage(SysUser merchant, File srcFile,
			String fileName) {
		String toFileName = retrieveFileName(merchant, WebConstants.SHOP,
				fileName);
		File dst = new File(toFileName);
		writeFile(srcFile, dst);
	}

	protected void deleteShopImage(SysUser merchant, String fileName) {
		deleteImage(merchant, WebConstants.SHOP, fileName);
	}

	protected void deleteVoucherImage(SysUser merchant, String fileName) {
		deleteImage(merchant, WebConstants.VOUCHER, fileName);
	}

	protected void deleteImage(SysUser merchant, String type, String fileName) {
		String existFileName = retrieveFileName(merchant, type, fileName);
		File existImage = new File(existFileName);
		if (existImage.exists()) {
			existImage.delete();
		}
	}

	private String retrieveFileName(SysUser merchant, String type,
			String fileName) {
		String webBaseSrc = ServletActionContext.getServletContext()
				.getRealPath("");
		webBaseSrc = webBaseSrc.substring(0,
				webBaseSrc.lastIndexOf(WebConstants.FILE_SEPARATOR) + 1)
				+ WebConstants.IMAGE_DIR
				+ WebConstants.FILE_SEPARATOR
				+ WebConstants.UPLOAD + WebConstants.FILE_SEPARATOR;
		String uploadDir = webBaseSrc + type + WebConstants.FILE_SEPARATOR
				+ merchant.getAccount();
		checkDir(uploadDir);
		return uploadDir + WebConstants.FILE_SEPARATOR + fileName;
	}

	protected static String buildFileName(String fileName) {
		String imageName = fileName.substring(0,
				fileName.lastIndexOf(WebConstants.DOT) - 1);
		String suffix = fileName.substring(fileName
				.lastIndexOf(WebConstants.DOT) + 1);
		String uuidStr = UUID.randomUUID().toString();
		uuidStr = uuidStr.substring(uuidStr.lastIndexOf("-") + 1);
		return imageName + WebConstants.MINUS + uuidStr + WebConstants.DOT
				+ suffix;
	}

	private void checkDir(String dir) {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
	}

	private static final int BUFFER_SIZE = 1024 * 1024;

	private static void writeFile(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), // 获得要上传的文件
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), // 指定要上传到的位置
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				// 开始写入
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
