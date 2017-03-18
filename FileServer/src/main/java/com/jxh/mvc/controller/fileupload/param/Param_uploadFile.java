package com.jxh.mvc.controller.fileupload.param;

import org.springframework.web.multipart.MultipartFile;

import com.jxh.mvc.controller.param.BaseParam;
/**
 * 文件上传
 * @author jingxiaohu
 *
 */
public class Param_uploadFile extends BaseParam{
	//文件
	public  MultipartFile file;
    //提交过来的file的名字
	public String fileName;
    //提交过来的file的MIME类型
	public String fileType;
	
 
	
	
	
	
	
	
	
	
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}	
	
	
	
	
	
	
}
