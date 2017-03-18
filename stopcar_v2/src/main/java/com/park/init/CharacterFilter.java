package com.park.init;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.park.core.Constants;
import com.park.v1.biz.DaoFactory;

public class CharacterFilter implements Filter {
	public static FilterConfig config = null;
	public void destroy() {
	}

	/**
	 * 内容过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(Constants.SYSTEM_CHARACTER);
		response.setCharacterEncoding(Constants.SYSTEM_CHARACTER);
		chain.doFilter(request, response);
	}

	/**
	 * 初始化参数
	 */
	public void init(FilterConfig config) throws ServletException {
		CharacterFilter.config = config;
		initSpring(config);
		//初始化ACP的配置
		//SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
	}
	
	public void initSpring(FilterConfig config){
		if (Constants.daoFactory == null) {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
			Constants.daoFactory = (DaoFactory) wac.getBean("daoFactory");
			Constants.SYSTEM_ROOT_PATH = config.getServletContext().getRealPath("/");
		}
	}

}
