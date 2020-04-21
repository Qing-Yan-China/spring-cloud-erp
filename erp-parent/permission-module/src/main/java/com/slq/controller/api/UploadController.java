package com.slq.controller.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slq.service.IUserService;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;

@RestController
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private IUserService userService;
	
	private String storageImagePath="C:\\javaEclipse作业合集\\spring-boot作业合集\\Project2\\src\\main\\resources\\static\\images";
	
	@PostMapping("headPortrai")
	public ResponseData uploadHeadPortrai(MultipartFile file,Integer userId) {
		System.out.println("fileName:"+file.getOriginalFilename());
		String newName;
		try {
			newName = userService.setHeadPortrai(file,userId);
			return DefaultResponseData.successResponseData("上传头像成功!", newName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DefaultResponseData.errorResponseData("上传失败!");
	
	}
	
	@GetMapping("/showImage/{imageName}")
	public void shoImage(@PathVariable("imageName")String imageName,HttpServletResponse response) {
		//第一种方式
		File file=new File(storageImagePath, imageName);
		System.out.println(imageName);
		System.out.println(file.exists());
		if(file.exists()) {
			try {
				response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(imageName, "utf-8"));
				FileUtils.copyFile(file, response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//第二中方式
//		File file=new File(storageImagePath, imageName);
//		FileInputStream inputStream=null;
//		OutputStream outputStream=null;
//		if(file.exists()) {
//			response.setContentType("image/jpg");
//			response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", 0);
//			try {
//				inputStream=new FileInputStream(file);
//				outputStream=response.getOutputStream();
//				//设置bytes数组的长度
//				byte[] bytes=new byte[inputStream.available()];
//				//写入到bytes中
//				inputStream.read(bytes);
//				outputStream.write(bytes);
//				outputStream.flush();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally {
//				try {
//					outputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
}
