package com.ylsh.survey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @description: 文件压缩工具类
 * @author ylsh
 * @date 2018年4月6日 下午3:17:14
 */
public class ZipUtils {
	
	private static final int  BUFFER_SIZE = 4 * 1024;
	
	
	/**
	 * @description: 压缩文件
	 * @author ylsh
	 * @date 2018年4月6日 下午3:11:41 
	 * @param deleteOriginal
	 * @param files
	 * @return
	 * @throws RuntimeException
	 */
	public static File toZip(boolean deleteOriginal, File... files) throws RuntimeException {
		long start = System.currentTimeMillis();
		
		String fileName = POIUtil.TEMP_FILE_PATH + System.currentTimeMillis() + ".zip";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		ZipOutputStream zos = null ;
		
		try {
			zos = new ZipOutputStream(new FileOutputStream(file));
			int count = 0;
			for (File srcFile : files) {
				String zFileName = "All_Data_" + count++ + srcFile.getName().substring(srcFile.getName().lastIndexOf('.'));
				zos.putNextEntry(new ZipEntry(zFileName));
				
				int len = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				InputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buffer)) != -1){
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				in.close();
				
				if (deleteOriginal && srcFile.exists()) {
					// 下载完成后，删除源文件（删除失败，则重试5次）
					srcFile.delete();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) +" ms");
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}