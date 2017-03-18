package com.park.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.park.bean.ReturnData;
import com.park.bean.ReturnDataBase;
import com.park.util.RequestUtil;
import com.park.v1.biz.DaoFactory;
/**
 * 
 * Struts2基于注解的Action配置
 *  总结常用的注解如下：

 

Namespace：指定命名空间。

ParentPackage：指定父包。

Result：提供了Action结果的映射。（一个结果的映射）

Results：“Result”注解列表

ResultPath：指定结果页面的基路径。

Action：指定Action的访问URL。

Actions：“Action”注解列表。

ExceptionMapping：指定异常映射。（映射一个声明异常）

ExceptionMappings：一级声明异常的数组。

InterceptorRef：拦截器引用。

InterceptorRefs：拦截器引用组。
 * 
 */
@ParentPackage(value="default")
//@Namespace("")
//@Results({ @Result(name = "success", location = "/main.jsp"),
//@Result(name = "error", location = "/error.jsp") })
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class BaseAction extends ActionSupport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6182244061803296061L;
	protected Logger log =  Logger.getLogger(BaseAction.class); 
	//系统级错误
	public String errorcode_systerm = "1000";
	//参数级错误
	public String errorcode_param = "1001";
    //逻辑运行错误
	public String errorcode_logical = "1002";
	/**
	 * 系统请求接口公有参数
	 */
	
	public long ui_id;//用户ID
	public String m;//md5散列后的值
	public int source;//从什么设备发出的请求 0:android 1:ios  2:web
	
	
	




	/**
	 * true：合法请求  false ：非法请求
	 * @return
	 */
	public boolean checkRequest(){
		if(null == m || "".equals(m)){ 
			return true;
		}
		return false; 
	}
	
	
	



   /*********************************下面是公共方法*************************************************/

	/**
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendResp(ReturnDataBase returnData){
		if(returnData == null){
			returnData = new ReturnData();
		}
		getResponse().setContentType("text/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			String data = JSON.toJSONString(returnData);
			log.info("返回数据:"+data);
			getResponse().getWriter().write(data);
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
	protected void sendResp(String returnData){
		returnData = returnData == null?"":returnData;
		getResponse().setContentType("text/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			getResponse().getWriter().write(returnData);
		} catch (Exception e) {
			log.error("BaseAction.sendResp(String returnData) is error", e);
		}
	}
	/**
	 * 得到当前的SERVICE工厂
	 * 
	 * @return
	 */
	public DaoFactory getBean() {
		if (Constants.daoFactory == null) {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			Constants.daoFactory = (DaoFactory) wac.getBean("daoFactory");
		}
		return Constants.daoFactory;
	}
	/**
	 * 得到当前的HttpServletRequest对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	/**
	 * 得到当前的HttpServletResponse对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	 public static String getIpAddr(HttpServletRequest request) { 
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
	  * 对手机设备信息进行解析
	  */
	 @SuppressWarnings("static-access")
/*	public Client_device returnClientDeviceID(HttpServletRequest request){
		 try {
			String c_device =  request.getParameter(Constants.ClientDeviceParamKey);
			 if(!RequestUtil.checkObjectBlank(c_device)){
				 //URL编码
				 c_device = returnUrlDecode(c_device);
				 //非空
				 JSONObject obj =  JSONObject.fromObject(c_device);
				 if(obj != null && !obj.isNullObject()){
					 //进行解析手机端的设备信息
					 Client_device clientDevice = (Client_device) obj.toBean(obj, Client_device.class);
					 return clientDevice;
				 }
				 
			 }
		} catch (Exception e) {
			log.error("returnClientDeviceID is error", e);
		}
		return null;
	 }*/
	 
	 
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
			 return URLDecoder.decode(value, Constants.SYSTEM_CHARACTER);
		} catch (UnsupportedEncodingException e) {
			log.error("returnUrlDecode is error", e);
		}
		 
		return value;
	 }
	 
}
