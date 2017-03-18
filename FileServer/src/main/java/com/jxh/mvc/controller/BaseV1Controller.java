package com.jxh.mvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.jxh.bean.ReturnData;
import com.jxh.bean.ReturnDataBase;
import com.jxh.bean.ReturnDataNew;
import com.jxh.constant.MyConstant;
import com.jxh.util.RequestUtil;
@Controller
@RequestMapping("/v1")
public class BaseV1Controller {
	@Autowired 
	private ServletContext servletContext;
	
	protected Logger log =  Logger.getLogger(BaseV1Controller.class); 
	
	protected String SUCCESS="success";
	protected String ERROR="error";
	//成功
	public String errorcode_success = "0";
	//系统级错误
	public String errorcode_systerm = "1000";
	//参数级错误
	public String errorcode_param = "1001";
    //逻辑运行错误
	public String errorcode_data = "1002";



	
	
	/** 基于@ExceptionHandler异常处理 */  
    @ExceptionHandler  
    public String resolveException(
    		HttpServletRequest request,
    		HttpServletResponse response, 
            Object handler, Exception e) {  
    		ReturnDataNew returnData = new ReturnDataNew();
    		if(handler != null){
    			log.error("handler="+handler.toString()+"  resolveException="+e.getMessage());
    		}
			returnData.setReturnData(errorcode_systerm, "systerm exception", "");
			sendResp(returnData,response);
			return null;
     }  
    /******************************业务逻辑注解区域*****************************************/

   /*********************************下面是公共方法*************************************************/
	/**
	 * true：合法请求  false ：非法请求
	 * @return
	 */
	public String getSignature(String dev_server_secret,Object ... params){
		if(params.length == 0 ){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (Object param : params) {
			if(param != null){
				list.add(param.toString());
			}
		}
		return getSignature(list,dev_server_secret);
	}
	/**
     * 签名生成算法
     *
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(Map<String, String> params, String dev_server_secret,String separator){
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
 
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append(param.getValue()).append(separator);
        }
        basestring.append(dev_server_secret);
        System.out.println(basestring.toString());
        //对待签名串求签
        return DigestUtils.md5Hex(basestring.toString());
    }
    /**
     * 签名生成算法
     *
     * @return 签名
     * @throws IOException
     */
    public  String getSignature(List<String> params, String dev_server_secret){
        // 先将参数以其参数名的字典序升序进行排序
    	Collections.sort(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Object param : params) {
            basestring.append(param);
        }
        basestring.append(dev_server_secret);
        System.out.println(basestring.toString());
        //对待签名串求签
        return DigestUtils.md5Hex(basestring.toString());
    }
	

	/**
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendResp(ReturnDataBase returnData,HttpServletResponse response){
		if(returnData == null){
			returnData = new ReturnData();
		}
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String data = JSONObject.toJSONString(returnData);
			log.info("返回数据:"+data);
			response.getWriter().write(data);
		} catch (Exception e) {
			log.error("BaseAction.sendResp is error", e);
		}
	}
	/**
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendResp(JSONObject returnData,HttpServletResponse response){
		if(returnData == null){
			returnData = new JSONObject();
		}
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String data = JSONObject.toJSONString(returnData);
			log.info("返回数据:"+data);
			response.getWriter().write(data);
		} catch (Exception e) {
			log.error("BaseAction.sendResp is error", e);
		}
	}
	/**
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendResp(String returnData,HttpServletResponse response){
		returnData = returnData == null?"":returnData;
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(returnData);
		} catch (Exception e) {
			log.error("BaseAction.sendResp(String returnData) is error", e);
		}
	}
	/**
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendRespHtml(String returnData,HttpServletResponse response){
		returnData = returnData == null?"":returnData;
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(returnData);
		} catch (Exception e) {
			log.error("BaseAction.sendResp(String returnData) is error", e);
		}
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	 public static String getIpAddr(HttpServletRequest request) { 
		 System.out.println(request.getRemoteHost()+"  "+request.getRemoteAddr());
	     String ip = request.getHeader("x-forwarded-for"); 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("Proxy-Client-IP"); 
	     } 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("WL-Proxy-Client-IP"); 
	     } 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getRemoteAddr(); 
	     } 
	     return ip; 
	 } 
	 
	 /**
	  * 
	 * @Title: getQueryString
	 * @Description: TODO(request中提供的getQueryString方法只对Get方法才能生效，在我们不知道方法的情况下最好重写getQueryString)
	 * @param @param request
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	  */
	 @SuppressWarnings("rawtypes")
	public  String getQueryString(HttpServletRequest request) {
	        boolean first = true;
	        StringBuffer strbuf = new StringBuffer("");
	        Enumeration emParams = request.getParameterNames();
	        //do-while
	        do {
	            if (!emParams.hasMoreElements()) {
	                break;
	            }
	            
	            String sParam = (String) emParams.nextElement();
	            String[] sValues = request.getParameterValues(sParam);    
	            
	            String sValue = "";            
	            for (int i = 0; i < sValues.length; i++) {
	                sValue = sValues[i];
	                if (sValue != null && sValue.trim().length() != 0 && first == true) {
	                    //第一个参数
	                    first = false;                    
	                    strbuf.append(sParam).append("=").append(sValue);
	                } else if (sValue != null && sValue.trim().length() != 0 && first == false) {
	                    strbuf.append("&").append(sParam).append("=").append(sValue);
	                }
	            }
	        } while (true);

	        return strbuf.toString();
	    }
	 /**
	  * URL解码
	  * @Title: returnUrlDecode
	  * @Description: TODO(这里用一句话描述这个方法的作用)
	  * @param @param value
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	  */
	 public String returnUrlDecode(String value){
		 
		 if(RequestUtil.checkObjectBlank(value)){
			return value; 
		 }
		 
		 try {
			 return URLDecoder.decode(value, MyConstant.SYSTEM_CHARACTER);
		 } catch (UnsupportedEncodingException e) {
			log.error("returnUrlDecode is error", e);
		 }
		 
		 return value;
	 }
	 
		//判断，返回布尔值  
	 public static boolean isMobileNO(String mobiles){  
//			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,3,5-9])|(17[0,1,3,5-9]))\\d{8}$");
		 Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);  
		    return m.matches();  
	 }   
	//判断，返回布尔值  
	 public static boolean isEmail(String email){  
		 //String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
		Matcher m = p.matcher(email);  
		return m.matches();  
	}  
	 
	 

		
		
		
}
