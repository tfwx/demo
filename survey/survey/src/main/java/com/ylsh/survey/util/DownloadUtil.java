package com.ylsh.survey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 服务器下载工具类
 * @author ylsh
 * @date 2018年4月6日 上午10:46:35
 */
public class DownloadUtil {
	
	private static final int  BUFFER_SIZE = 4 * 1024;
	
	/**
	 * @description: 将文件写入输出流，返回给客户端
	 * @author ylsh
	 * @date 2018年4月6日 上午10:46:54 
	 * @param file
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	public static void download(File file, String fileName, HttpServletResponse response) 
			throws Exception {
		// 设置response返回信息
		// 名称中有中文时，必须重新编码,否则会乱码
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		
		InputStream inputStream = null;
		OutputStream outputStream = response.getOutputStream();
		try {
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
		} catch (Exception e) {
			outputStream.write("下载失败！".getBytes("UTF-8"));
			e.printStackTrace();
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			
			// 下载完成后，删除源文件
			if (file!= null && file.exists()) {
				file.delete();
			}
		}
	}

}
