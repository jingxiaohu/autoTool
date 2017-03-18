package com.park.mvc.v1.action;

import java.io.IOException;
import java.io.Serializable;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.park.bean.ReturnData;
import com.park.bean.ReturnDataBase;
import com.park.core.Constants;
import com.park.util.RequestUtil;
import com.park.v1.biz.CarBiz;
import com.park.v1.biz.CouponBiz;
import com.park.v1.biz.DaoFactory;
import com.park.v1.biz.GameSdkBiz;
import com.park.v1.biz.OrderBiz;
import com.park.v1.biz.ParkinfoBiz;
import com.park.v1.biz.SMSBiz;
import com.park.v1.biz.TestBiz;
import com.park.v1.biz.UserBiz;
import com.park.v1.biz.UserPayBiz;
import com.park.v1.biz.VersionBiz;
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
@ParentPackage("struts-default")  
@Namespace("/v1")
//@Results({ @Result(name = "success", location = "/main.jsp"),
//@Result(name = "error", location = "/error.jsp") })
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class BaseV1Action extends ActionSupport implements Serializable{
	private static final long serialVersionUID = 6182244061803296061L;
	protected Logger log =  Logger.getLogger(BaseV1Action.class); 
	//成功
	public String errorcode_success = "0";
	//系统级错误
	public String errorcode_systerm = "1000";
	//参数级错误
	public String errorcode_param = "1001";
    //逻辑运行错误
	public String errorcode_data = "1002";
	/**
	 * 系统请求接口公有参数
	 */
	public long ui_id;//用户ID
	public String sign;//md5散列后的值
	public int dtype;//从什么设备发出的请求 0:android 1:ios  2:web

	/**
	 * true：合法请求  false ：非法请求
	 * @return
	 */
	public boolean checkRequest(){
		if(null == sign || "".equals(sign)){ 
			return false;
		}
		return true; 
	}
	
	
	
    /******************************业务逻辑注解区域*****************************************/

	@Autowired
	protected TestBiz testBiz;
	@Autowired
	protected VersionBiz versionBiz;
	@Autowired
	protected UserBiz userBiz;
	@Autowired
	protected SMSBiz sMSBiz;
	@Autowired
	protected ParkinfoBiz parkinfoBiz;
	@Autowired
	protected CarBiz carBiz;
	@Autowired
	protected  CouponBiz couponBiz;
	@Autowired
	protected OrderBiz orderBiz;
	@Autowired
	protected GameSdkBiz gameSdkBiz;
	@Autowired
	protected UserPayBiz userPayBiz;
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
     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 dev_server_secret 开发者在力美后台设置的密钥
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
     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 dev_server_secret 开发者在力美后台设置的密钥
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
	protected void sendResp(ReturnDataBase returnData){
		if(returnData == null){
			returnData = new ReturnData();
		}
		getResponse().setContentType("text/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			String data = JSONObject.toJSONString(returnData);
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
	protected void sendResp(JSONObject returnData){
		if(returnData == null){
			returnData = new JSONObject();
		}
		getResponse().setContentType("text/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			String data = JSONObject.toJSONString(returnData);
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
	 * 封装发送响应
	* @Title: sendResp
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData    设定文件
	* @return void    返回类型
	* @throws
	 */
	protected void sendRespHtml(String returnData){
		returnData = returnData == null?"":returnData;
		getResponse().setContentType("text/html; charset=utf-8");
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
	  * 对手机设备信息进行解析
	  */
/*	 @SuppressWarnings("static-access")
	public Client_device returnClientDeviceID(HttpServletRequest request){
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
	 /*****************get set **********************/
		public long getUi_id() {
			return ui_id;
		}
		public void setUi_id(long ui_id) {
			this.ui_id = ui_id;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		
		/*public static void main(String[] args) {
			System.out.println(isMobileNO("17608005981"));
		}*/
		 
}
