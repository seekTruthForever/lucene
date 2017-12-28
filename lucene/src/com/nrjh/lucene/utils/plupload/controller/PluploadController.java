package com.nrjh.lucene.utils.plupload.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrjh.lucene.constant.Constant;
import com.nrjh.lucene.utils.plupload.service.PluploadService;
import com.nrjh.lucene.utils.plupload.view.Plupload;
@Controller
@RequestMapping(value = "/plupload")
public class PluploadController {
	@Autowired
	private PluploadService pluploadService;
	
	/**Plupload文件上传处理方法*/
	@RequestMapping(value="/upload.do")
	public void upload(Plupload plupload,HttpServletRequest request,HttpServletResponse response) {
	 
	  String FileDir = Constant.getFileDirPath("dirPath");//文件保存的文件夹
	  plupload.setRequest(request);//手动传入Plupload对象HttpServletRequest属性
	 
	  //文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"pluploadDir"文件夹，还会以用户唯一id作划分
	  File dir = new File(FileDir);
	  if(!dir.exists()){
	    dir.mkdirs();//可创建多级目录，而mkdir()只能创建一级目录
	  }
	  //开始上传文件
	  pluploadService.upload(plupload, dir);
	}

	public PluploadService getPluploadService() {
		return pluploadService;
	}

	public void setPluploadService(PluploadService pluploadService) {
		this.pluploadService = pluploadService;
	}
}
