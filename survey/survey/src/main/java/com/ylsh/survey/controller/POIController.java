package com.ylsh.survey.controller;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.util.POIUtil;



@Controller
@RequestMapping("/word")
public class POIController {
	
	
	@RequestMapping(value="/parse", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult read(@RequestParam("file")MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new FileNotFoundException();
			} else {
				File tempFile = new File(POIUtil.TEMP_FILE_PATH + System.currentTimeMillis() + file.getOriginalFilename());
				file.transferTo(tempFile);
				System.err.println("upload tempFile：" + tempFile.getAbsolutePath());
				String fileContent = POIUtil.readWord(tempFile);
				tempFile.delete();
				return ResponseResult.ok(fileContent);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
		
	}
	
	

}
