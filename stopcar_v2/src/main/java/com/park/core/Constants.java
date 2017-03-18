package com.park.core;

import com.park.v1.biz.DaoFactory;

public class Constants {
	/********************正式地址**********************************/
	//服务器主机IP
	public static String SYSTEM_IP = "app.qc-wbo.com";	
	//APP服务器访问域名
	public static String domain = "app.qc-wbo.com";
	/**********************正式地址####结束******************************/
	
	/********************测试地址**********************************/
	
	
	/**********************测试地址####结束******************************/
	
	
	
	
	
	/**********************调用其它项目的地址**************************/
	public final static String BASE_URL_public = "http://"+SYSTEM_IP+"/";
	/**
	 * 访问根地址
	 */
	public final static String BASE_URL = "http://"+SYSTEM_IP+"/file/";
	/**
	 * 系统跟路径
	 */
	public static String SYSTEM_ROOT_PATH = "";
	/**
	 * 系统通用加密代码串
	 */
	public final static String SYSTEM_KEY = "!*#@()@dfgdfgFasdafaaJ*asddas499NHIJ)";

	/**
	 * 系统默认字符
	 */
	public final static String SYSTEM_CHARACTER = "utf-8";

	/**
	 * Spring属性BEAN
	 */
	public static DaoFactory daoFactory = null; 

	
	public static String tableTemplate ="WEB-INF/config/properties/TableTemplate.properties";
	

	/**
	 * 文件存放的根地址
	 */
	public final static String BASE_DIR = "/data/file/"+domain+"/ROOT";
	/**
	 * 用户头像存放路径
	 */
	public static String USER_AVATAR = "img/avatar";
	/**
	 * 用户行驶证存放路径
	 */
	public static String USER_LIENCE = "img/lience";
	/**
	 * 用户消息图片路径
	 */
	public static String USER_MSG = "img/msg";
	
	/**
	 * 系统默认字符
	 */
	public final static String JPUSH_LOGO = "https://t12.baidu.com/it/u=330026144,767285751&fm=76";
    /**
     * 用户消息图片高
     */
	public final static int MSG_HIGHT = 800;
    /**
     * 用户消息图片宽
     */
	public final static int MSG_WIDTH = 480;
	/**
	 * 广告图片存放路径
	 */
	public static String IMG_ADVER = "img/adver";
    /**
     * 头像高
     */
	public final static int AVATAR_HIGHT = 120;
    /**
     * 头像宽
     */
	public final static int AVATAR_WIDTH = 120;
	
    /**
     * 行驶证高
     */
	public final static int LIENCE_HIGHT = 800;
    /**
     * 行驶证宽
     */
	public final static int LIENCE_WIDTH = 800;
	
	
	/**
	 * 财付通回调地址
	 */
	public final static String PayNotify_QQ_Url=BASE_URL_public+"v1/paynotify_qq.do";  
	
	/**
	 * 财付通密钥
	 */
	public final static String CFT_SecretKey = "";
	/**
	 * 财付通通知ID验证地址
	 */
	public final static String CFT_NotifyValidateUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";
	/**
	 * intimes平台用户信息
	 */
	public static String USER_INFO;
	
    
	/**
	 * 支付宝密钥
	 */
	public final static String ZFB_SecretKey = "";
	/**
	 *  支付宝通知ID验证地址
	 */
	public final static String ZFB_NotifyValidateUrl = "https://mapi.alipay.com/gateway.do";
	/**
	 * 支付宝回调地址
	 */
	public final static String PayNotify_ZFB_Url=BASE_URL_public+"v1/paynotify_zfb.do";
	
	/*****************************下面定义跟客户端交互的全局字段***************************************/
	public final static String ClientDeviceParamKey = "c_device";
		
	

	
	
	
	/**
	 * 项目真实地址
	 */
	public static String APP_PATH = null;
	/**
	 * 二维码图片存放路径
	 */
	public static String SYSTEM_QrCode_path ="QzQrCode";
	/**
	 * 二维码图片访问地址
	 */
	public static String SYSTEM_QrCode_URL ="http://"+SYSTEM_IP;
	/**
	 * 热门软件图片保存文件夹
	 */
	public static String SYSTEM_HOTSOFTWARE_PICTURE = "hotsoftware_pic";
	/**
	 * 热门软件下载APP文件夹
	 */
	public static String SYSTEM_HOTSOFTWARE_APP = "hotsoftware_app";
	
}
