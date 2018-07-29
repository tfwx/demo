package com.ylsh.survey.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ylsh.survey.dto.ResponseResult;


/**
 * @description: 
 * @author ylsh
 * @date 2018年5月3日 下午6:03:04
 */
@Controller
@RequestMapping("naire/upload")
public class UploadController {
	
	@Value("${poster_upload_path}")
	private String POSTER_UPLOAD_PATH;				// 图片上传路径
	
	
	/**
	 * @description: 保存问卷二维码海报图片
	 * @author ylsh
	 * @date 2018年5月3日 下午6:02:38 
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/poster", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult savePosterFile(@RequestParam("file")MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new Exception("文件异常");
			}
			File directory = new File(POSTER_UPLOAD_PATH);
			if (!directory.exists()) {
				boolean success = directory.mkdirs();
				if (!success) {
					throw new Exception("文件上传目录建立异常：" + POSTER_UPLOAD_PATH + "，请检查目录所在磁盘是否存在");
				}
			}
			File posterFile = new File(directory, file.getOriginalFilename());
			if (posterFile.exists()) {
				posterFile.delete();
			}
			file.transferTo(posterFile);
			return ResponseResult.ok("文件保存成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

}
