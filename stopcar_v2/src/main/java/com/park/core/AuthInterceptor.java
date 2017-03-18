package com.park.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
public class AuthInterceptor implements Interceptor {
	public Logger log = Logger.getLogger(AuthInterceptor.class);
	
	public final static String ROLE_KEY = "$_ROLE_KEY";
	// 排除的URL
	private static Map<String,String> actionMap = new HashMap<String,String>();
	static {
		actionMap.put("user_login.LoginIn", "");
		actionMap.put("user_login.LoginOut", "");
		actionMap.put("user_login.modifyPassword","");//修改密码
		actionMap.put("user_login.CheckUserPassword","");//检测密码
		//用户id 获取用户昵称
		actionMap.put("ktvcoverinfo.selectUserNickName", "");
		//趣赚官网获取最新兑换记录和提现记录
		actionMap.put("lastest_exchange", "");
		//趣赚官网获取最新公告
		actionMap.put("lastest_notice", "");
		//IOS www.quzhuan.net 上面的几个动态数据 
		actionMap.put("ios_record", "");//任务记录
		actionMap.put("ios_exchange", "");//兑换记录
		actionMap.put("ios_rangking", "");//积分排行
		
		
		
		
		
		
		//聚享游导出他们的会员完成任务情况数据
		actionMap.put("jxy.exportqd", "");
		//2478会员的兑换记录
		//actionMap.put("jxy.exportdata", "");
	} 
	private static final long serialVersionUID = 1L;	
	public void destroy() {
	}

	public void init() {
		System.out.println("--------------------------AuthInterceptor--------------------------");
	}

	/**
	 * 进行拦截器处理
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("--------------------------AuthInterceptor#intercept#actionname--------------------------"+invocation.getInvocationContext().getName());
		System.out.println("--------------------------AuthInterceptor#intercept#actionParam--------------------------"+invocation.getInvocationContext().getParameters().toString());
		//return invocation.invoke();
		HttpServletRequest request = ServletActionContext.getRequest();
		//判断SESSION 是否有效
		/*if(request.getSession() == null){
			return "relogin";
		}	*/	
		// 请求访问角色资源
		String role_resource = parseURI(request.getRequestURI());
		String action = invocation.getInvocationContext().getName()+"."+role_resource;
		log.info(" - - - - - -action- - - - -:"+action);	
		
		return  invocation.invoke();		
		}
/*	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("--------------------------AuthInterceptor#intercept#actionname--------------------------"+invocation.getInvocationContext().getName());
		System.out.println("--------------------------AuthInterceptor#intercept#actionParam--------------------------"+invocation.getInvocationContext().getParameters().toString());
		//return invocation.invoke();
		HttpServletRequest request = ServletActionContext.getRequest();
		//判断SESSION 是否有效
		if(request.getSession() == null){
			return "relogin";
		}		
		//admin账户 直接过去		
		Intimes_info intimesinfo=new Intimes_info();
		Object obj=request.getSession().getAttribute(Constants.USER_INFO);
		if(obj!=null) intimesinfo=(Intimes_info)obj;	
		if("admin".equals(intimesinfo.getAu_loginname()))
		{					
			return invocation.invoke();	
		}						
		// 请求访问角色资源
		String role_resource = parseURI(request.getRequestURI());
		String action = invocation.getInvocationContext().getName()+"."+role_resource;
		// 拥有访问资源集合
			Map<String, String> role = (Map<String, String>) request.getSession().getAttribute(ROLE_KEY);
			request.getSession().setAttribute("ROLE", JSONObject.fromObject(role));
		//System.out.println(" - - - - - -action- - - - -:"+action);				
			if(actionMap.containsKey(action))
		{
			//System.out.println(" - - - - -授权成功- - - - -");		
			return invocation.invoke();
		}else if(role==null){
			System.out.println(" - - - - - -授权失败- - - - -"+action);
			return "noauth";
			}
		else if(role.containsKey(action))  {
			//System.out.println(" - - - - - -授权成功- - - - -");			
			return  invocation.invoke();		
		}
		System.out.println(" - - - - - -授权失败- - - - -"+action);
		return "noauth";		
		
		
		
		}*/	
	
	/**
	 * 得到访问资源名称
	 * @param uri
	 * @return
	 */
	public String parseURI(String uri){
		String temp[] = uri.split("!");
		if(temp.length==2){
			String str = temp[1].replace(".", "#");
			temp = str.split("#");
			return temp[0];
		}
		return null;
	}

}
