package com.jxh.mvc.controller.fileupload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxh.bean.ReturnDataNew;
import com.jxh.constant.MyConstant;
import com.jxh.file.ImgUtil;
import com.jxh.mvc.controller.BaseV1Controller;
import com.jxh.mvc.controller.fileupload.param.Param_uploadFile;
import com.jxh.util.RequestUtil;

@Controller
public class FileUploadAction extends BaseV1Controller{

    @RequestMapping(value="/fileupload",method=RequestMethod.POST)
    @ResponseBody
    String fileupload(HttpServletRequest request,HttpServletResponse response,Param_uploadFile param) {
    	       ReturnDataNew returnData = null;
    	       try {
				   returnData = new ReturnDataNew();
				   //检查是否是合法请求
					String ip = getIpAddr(request);
					if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
						ip  =  null;
					}
					 //参数检查
					 if(param == null){ 
						 //参数传递错误 
						 returnData.setReturnData(errorcode_param, "参数传递错误", "");
						 sendResp(returnData,response);
						 return null; 
					 }
					 //检查是否进行了参数签名认证
					 if(!param.checkRequest()){
						 returnData.setReturnData(errorcode_param, "没有进行参数签名认证", "");
						 sendResp(returnData,response);
						 return null; 
					 }
					 //对封装的参数对象中的属性进行 非空等规则验证
					
					if(RequestUtil.checkObjectBlank(param.getFile())){
						returnData.setReturnData(errorcode_param, " file is null", null);
						sendResp(returnData,response);
						return null;
					}
					
					/**
					 * 这里处理文件相关属性
					 */
					MultipartFile file = param.getFile(); 
					String fileName = null;
					if(file != null){
						fileName = file.getOriginalFilename();
						param.setFileName(fileName);
						param.setFileType(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()));
					}
					String sign_str = getSignature(MyConstant.SYSTEM_KEY, param.dtype);
					if(!param.sign.equalsIgnoreCase(sign_str)){
						log.error("sign="+param.sign+"  sign_str="+sign_str);
						returnData.setReturnData(errorcode_param, " sign is not right", null);
						sendResp(returnData,response);
						return null;
					}
					/**
					 * 这里处理文件的读取存取等
					 */
					if(param.getWidth() != null && param.getHeight() != null){
						//进行按指定宽和高进行设置
						String filename = ImgUtil.ImgSize(MyConstant.toSavePath, file.getInputStream(), param.getWidth(), param.getHeight(), param.getFileType());
						String fileurl = MyConstant.BASE_URL+filename;
						returnData.setReturnData(errorcode_success, "file is upload ok", fileurl);
						sendResp(returnData,response);
						return null;
					}
					
					
					
					returnData.setReturnData(errorcode_param, "file is upload fail", "");
					sendResp(returnData,response);
					return null;
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				log.error("FileUploadAction->fileupload is error ", e);
				returnData.setReturnData(errorcode_param, "file is upload fail", "");
			}
    	    sendResp(returnData,response);
    		return null; 
    }
    
}
