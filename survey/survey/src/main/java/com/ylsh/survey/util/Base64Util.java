package com.ylsh.survey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;



/**
 * @description:Base64编解码工具
 * @author ylsh
 * @date 2017年12月1日 下午10:46:25
 */
public class Base64Util {

	public Base64Util() {
		throw new AssertionError();
	}


	/**
	 * @description:图片转化成base64字符串
	 * @author ylsh
	 * @date 2017年12月1日 下午10:08:19
	 * @return
	 */
	public static String encodeImage(String imagePath) {
		File imageFile = new File(imagePath);
		if (!imageFile.exists()) {
			return null;
		}
		
		InputStream in = null;
		byte[] buffer = null;
		String imageBase64Code = null;
		try {
			in = new FileInputStream(imageFile);
			buffer = new byte[in.available()];
			in.read(buffer);
			imageBase64Code = Base64.encodeBase64String(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return imageBase64Code;	
	}

	/**
	 * @description:将Base64编码转为图片文件
	 * @author ylsh
	 * @date 2017年12月1日 下午10:12:03
	 * @param imgStr
	 * @return
	 */
	public static boolean decodeImage(String imgFilePath, String imgBase64Code) {
		if (StringUtils.isBlank(imgFilePath) || StringUtils.isBlank(imgBase64Code)) {
			return false;
		}
		
		File imageFile = new File(imgFilePath);
		if (!imageFile.getParentFile().exists()) {
			imageFile.getParentFile().mkdirs();
		}

		OutputStream out = null;
		try {
			// Base64解码
			byte[] buffer = Base64.decodeBase64(imgBase64Code);
			// 调整异常数据
			for (int i = 0; i < buffer.length; i++) {				
				if (buffer[i] < 0) {
					buffer[i] += 256;
				}
			}
			
			out = new FileOutputStream(imageFile);
			out.write(buffer);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
