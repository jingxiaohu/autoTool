package com.park.v1.biz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.Car_in_outDao;
import com.park.dao.Fault_recordDao;
import com.park.dao.Intimes_payDao;
import com.park.dao.Park_deviceDao;
import com.park.dao.Park_heartbeatDao;
import com.park.dao.Park_infoDao;
import com.park.dao.Park_userinfoDao;
import com.park.dao.Pay_month_parkDao;
import com.park.dao.Pay_parkDao;
import com.park.dao.Rental_charging_ruleDao;
import com.park.dao.Sms_runningDao;
import com.park.dao.Sms_validateDao;
import com.park.dao.User_carcodeDao;
import com.park.dao.User_feedbackDao;
import com.park.dao.User_infoDao;
import com.park.dao.User_login_logDao;
import com.park.dao.User_moneybackDao;
import com.park.dao.User_park_couponDao;
import com.park.dao.User_payDao;
import com.park.dao.User_vc_actDao;
import com.park.service.MailService;
import com.park.service.MySelfService;
import com.park.service.UserRedisService;
import com.park.task.AsyncJpushTask;

/**
 * 
 * Title:action
 * Description:处理memcached中的一些数据的读取 
 * Copyright: Copyright (c) 2014
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-4-14
 * @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly=true) :只读事务
 * @Transactional(propagation = Propagation.NOT_SUPPORTED) : 不进行事务执行
 */
@Service
public  class BaseBiz{
	//@Resource(name="CacheManager")
	//private CacheManager cacheManager;
	@Resource(name="MySelfService")
	private MySelfService mySelfService;
	@Resource(name="userRedisService")
	private UserRedisService userRedisService;
	@Resource(name="mailService")
	private MailService mailService;
	
    //激光推送
	@Autowired
	protected AsyncJpushTask asyncJpushTask;
	
	public  Map<String,String> map_source = null;
   
	

	public Map<String, String> getMap_source() {
		if(map_source == null){
			map_source = new HashMap<String,String>(); 
			map_source.put("qingting", "1");
			map_source.put("kuke", "2");
			map_source.put("ifeng", "3");
		}
		return map_source;
	}

	protected Logger log = Logger.getLogger(BaseBiz.class);
	//系统错误代码
	public String errorcode_success = "0";
	public String errorcode_systerm = "1000";
	public String errorcode_param = "1001";
	public String errorcode_data = "1002";
	
	protected SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected SimpleDateFormat sf_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	protected SimpleDateFormat sf_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	/******************DAO定义************************/
	
	
	
	
	
	
	
	@Autowired
	protected User_infoDao user_infoDao;
	@Autowired
	protected User_login_logDao user_login_logDao;
	@Autowired
	protected Sms_validateDao sms_validateDao;
	@Autowired
	protected Sms_runningDao sms_runningDao;
	@Autowired
	protected Park_infoDao park_infoDao;
	@Autowired
	protected Rental_charging_ruleDao rental_charging_ruleDao;
	@Autowired
	protected Park_deviceDao park_deviceDao;
	@Autowired
	protected Car_in_outDao car_in_outDao;
	@Autowired
	protected Fault_recordDao fault_recordDao;
	@Autowired
	protected User_carcodeDao user_carcodeDao;
	@Autowired
	protected User_feedbackDao user_feedbackDao;
	@Autowired
	protected User_moneybackDao user_moneybackDao;
	@Autowired
	protected Pay_parkDao pay_parkDao;
	@Autowired
	protected Pay_month_parkDao pay_month_parkDao;
	@Autowired
	protected Park_heartbeatDao park_heartbeatDao;
	@Autowired
	protected Intimes_payDao intimes_payDao;
	@Autowired
	protected User_vc_actDao user_vc_actDao;
	@Autowired
	protected User_park_couponDao user_park_couponDao;
	@Autowired
	protected User_payDao user_payDao;
	@Autowired
	protected Park_userinfoDao park_userinfoDao;
	/*****************************************/
	public  String ERROR_RESP = "HTTP_GET_ERROR";
	public MySelfService getMySelfService() {
		return mySelfService;
	}
	/*public CacheManager getCacheManager() {
		return cacheManager;
	}*/
	
	
	/**
	 * @return the userRedisService
	 */
	public UserRedisService getUserRedisService() {
		return userRedisService;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}





	/***
	 * 处理获取单个类
	 * @param <T>
	 * @param <T>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T  returnListOne(String sql,Class classaa) throws Exception{
		List<T> list = getMySelfService().executeQuery(sql, classaa);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/*************************处理公用的业务方法***********************************/
	/**
	 * 记录手机设备信息
	* @Title: insertClientDevice
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param client_device
	* @param @return    设定文件
	* @return long    返回类型
	* @throws
	 */
/*	protected long insertClientDevice(Client_device client_device){
		if(client_device == null){
			return 0;
		}
		long xx = 0;
		try {
			String sql = "select * from client_device where imei=? limit 1";
			Client_device client_device2 = getMySelfService().queryUniqueT(sql, Client_device.class, client_device.getImei());
			if(client_device2 == null){
				xx = clientDeviceDao.insert(client_device);
			}else{
				xx = client_device2.getCd_id();
			}
			
		} catch (Exception e) {
			log.error("xx = clientDeviceDao.insert(client_device) is error", e); 
		}
		
		return xx < 1 ?0:xx;
	}*/
	
	
	
	
	
	
	/******************定义 添、删、查、改***公用方法*********************/
	



	/**
	 * 生成32位UUID
	 */
	public  String returnUUID(){
//		return RequestUtil.getUUID().substring(13)+System.currentTimeMillis();
		return new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+RandomStringUtils.random(4,false,true);  
	}
	
	/**
	 * 请求头集合
	 * @param url
	 * @param header
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public  String doGet(String url,Map<String, String> header,NameValuePair[] params){
		HttpClient  hc = new HttpClient();
		GetMethod get =  null;
		try{
			hc.setConnectionTimeout(20*1000);
			hc.setTimeout(20*1000);
			get =  new GetMethod(url);
			if(params!= null){
				get.setQueryString(params); 
			}
			get.setRequestHeader("Connection", "close");  
			get.addRequestHeader("Content-Type", "application/json;charset=utf-8");
			get.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			get.addRequestHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			if(header!=null){
				for(String h:header.keySet()){
					get.addRequestHeader(h, header.get(h));
				}
			}
			hc.executeMethod(get);
			if(get.getStatusCode() == 200){
				InputStream resStream = get.getResponseBodyAsStream();  
		        BufferedReader br = new BufferedReader(new InputStreamReader(resStream));  
		        StringBuffer resBuffer = new StringBuffer();  
		        String resTemp = "";  
		        while((resTemp = br.readLine()) != null){  
		            resBuffer.append(resTemp);  
		        }  
		        String response = resBuffer.toString();  
				//return get.getResponseBodyAsString();
		        return response;
			}else{
				log.error(url+" req error StatusCode:"+get.getStatusCode());
			}
		}catch(Exception e){
			log.error("doGet error", e);
			return ERROR_RESP;
		}finally{
			if(get!=null){
				get.releaseConnection();
				//释放链接
				hc.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
		return ERROR_RESP;
	}
	
	/**
	 * 区域代码转化成 省级市  area_code "510112";//省市区区域代码  四川省 成都市 龙泉驿区
	 */
	public String ReturnParkTableName(String area_code){
		if(area_code == null)
			return null;
		return  park_infoDao.TABLENAME+area_code.substring(0, 2)+"0000";
	}
	
	/*****************上生活需要的工具方法**********************/
	/**
	 * 分页处理
	 */
	public int returnPage(int totalsize,int pagesize){
		if(totalsize%pagesize == 0){
			return totalsize/pagesize;
		}else{
			return totalsize/pagesize+1;
		}
	}

	
/**
 * 分时间段包月计算法则	
 * @param permit_time 17:00-08:30 或者  08:00-17:00  准入时间段
 * @param starttime : 起始时间
 * @param endtime ： 到期时间
 * @param entertime : 入库时间
 * @param outtime ： 结算时间
 */
public  long  calculateMonthPay(String permit_time,Date starttime, Date endtime,Date entertime,Date outtime){ 
	//临停时间
	long temp_time = 0;
	//一天中 除开允许的 时间段之外的 临停时间 单位分钟
	long diff_minute = onedayPay(permit_time);
	System.out.println("diff_minute=="+diff_minute); 
	// 17:00-08:30 或者  08:00-17:00  准入时间段
	long diff2 = outtime.getTime()-entertime.getTime();
	temp_time = (diff2/(24*3600*1000))*diff_minute + one_day( permit_time, entertime, outtime);
	 
	//分以下几种情况  
	//第一种：用户入库后  再开始的包月
	if(starttime.getTime() > entertime.getTime()){
		temp_time +=  (starttime.getTime() - entertime.getTime())/(60*1000);
	}
	//第二种：用户出库的时候 包月已经逾期
	if(endtime.getTime() < outtime.getTime()){
		temp_time +=  (outtime.getTime() - endtime.getTime())/(60*1000);
	}
	System.out.println("temp_time====="+temp_time);
	return temp_time*60*1000;
}	


/**
 * 分时间段包月计算  一天的临停时间	
 * @param permit_time 17:00-08:30 或者  08:00-17:00  准入时间段
 */
public  long  onedayPay(String permit_time){
	//一天中 除开允许的 时间段之外的 临停时间 单位分钟
	int diff_minute = 0;
	// 17:00-08:30 或者  08:00-17:00  准入时间段
	String[] time_array = permit_time.split("-");
	if(time_array != null && time_array.length == 2){
		String[] start_array = 	time_array[0].split(":");
		if(start_array == null || start_array.length < 2){
			return 0;
		}
		String[] end_array = 	time_array[1].split(":");
		if(end_array == null || end_array.length < 2){
			return 0;
		}
		int start_hour = Integer.parseInt(start_array[0]);
		int start_minute = Integer.parseInt(start_array[1]);
		
		int end_hour = Integer.parseInt(end_array[0]);
		int end_minute = Integer.parseInt(end_array[1]);
		
		
		if(start_hour <= 24 &&  start_hour > end_hour){
			int diff_hour = start_hour - end_hour;
			diff_minute = diff_hour*60+(start_minute-end_minute);
		}else{
			int diff_hour = (end_hour - start_hour);
			diff_minute =24*60 - ( diff_hour*60+(end_minute-start_minute));
		}
	 }
		return diff_minute;
	}

/**
 * 判断时间是否在包月区间里面
 * @param args
 * @return ture:在扣款区间  false 不在扣款区间
 */
public  boolean is_between(String permit_time,Date time){ 
	// 17:00-08:30 或者  08:00-17:00  准入时间段
	String[] time_array = permit_time.split("-");
	if(time_array != null && time_array.length == 2){
		String[] start_array = 	time_array[0].split(":");
		if(start_array == null || start_array.length < 2){
			return false;
		}
		String[] end_array = 	time_array[1].split(":");
		if(end_array == null || end_array.length < 2){
			return false;
		}
		int start_hour = Integer.parseInt(start_array[0]);
		int start_minute = Integer.parseInt(start_array[1]);
		
		int end_hour = Integer.parseInt(end_array[0]);
		int end_minute = Integer.parseInt(end_array[1]);
		
		Calendar cl = Calendar.getInstance(Locale.CHINA);
		cl.setTime(time);
		int time_hour = cl.get(Calendar.HOUR_OF_DAY);
		int time_minute = cl.get(Calendar.MINUTE); 
		
		if(start_hour > end_hour){
			//17:00-08:30 
			if(time_hour*60+time_minute >= start_hour*60+start_minute || time_hour*60+time_minute <= end_hour*60+end_minute){
				return false;
			}
		}else{
			//08:00-17:00
			if(time_hour*60+time_minute >= start_hour*60+start_minute && time_hour*60+time_minute <= end_hour*60+end_minute){
				return false;
			}
		}
	
	}
	
	return true;
}

/**
 * 计算一天里面的时间
 * @param args
 */
public  long one_day(String permit_time,Date entertime,Date outtime){
	//临停时间
	long temp_time = 0;
	//一天中 除开允许的 时间段之外的 临停时间 单位分钟
	int diff_minute_enter = 0;
	int diff_minute_out = 0; 
	// 17:00-08:30 或者  08:00-17:00  准入时间段
	String[] time_array = permit_time.split("-");
	if(time_array != null && time_array.length == 2){
		String[] start_array = 	time_array[0].split(":");
		if(start_array == null || start_array.length < 2){
			return 0;
		}
		String[] end_array = 	time_array[1].split(":");
		if(end_array == null || end_array.length < 2){
			return 0;
		}
		int start_hour = Integer.parseInt(start_array[0]);
		int start_minute = Integer.parseInt(start_array[1]);
		
		int end_hour = Integer.parseInt(end_array[0]);
		int end_minute = Integer.parseInt(end_array[1]);
		
		Calendar cl = Calendar.getInstance(Locale.CHINA);
		cl.setTime(entertime);
		int enter_hour = cl.get(Calendar.HOUR_OF_DAY);
		int enter_minute = cl.get(Calendar.MINUTE);
		
		Calendar cl2 = Calendar.getInstance(Locale.CHINA);
		cl2.setTime(outtime);
		int out_hour = cl2.get(Calendar.HOUR_OF_DAY);
		int out_minute = cl2.get(Calendar.MINUTE); 
		
		
		
		if(start_hour > end_hour){
			//时间段 18:30-08:00
			int T = (start_hour  - end_hour)*60+(start_minute - end_minute);
			if(is_between(permit_time,entertime)){
				//入库时间落在 扣费时间段内部
				if(is_between(permit_time,outtime)){
					if(enter_hour*60+enter_minute >= out_hour*60+out_minute){
						int diff_hour = start_hour - enter_hour;
						diff_minute_enter = diff_hour*60+(start_minute-enter_minute);
						diff_hour =  out_hour-end_hour;
						diff_minute_out = diff_hour*60+(out_minute - end_minute);
						temp_time = diff_minute_enter+diff_minute_out;
					}else{
						int diff_hour = out_hour - enter_hour;
						temp_time = diff_hour*60+(out_minute-enter_minute);
					}
				}else{
					int diff_hour = start_hour - enter_hour;
					temp_time = diff_hour*60+(start_minute-enter_minute);
				}
			}else{
				//入库时间落在 免费时间段内部
				
				if(is_between(permit_time,outtime)){
					int diff_hour = out_hour - end_hour;
					temp_time = diff_hour*60+(out_minute-end_minute);
				}else{
					if(enter_hour*60+enter_minute >= out_hour*60+out_minute){
						temp_time = T;
					}else{
							temp_time = 0;
					}
				}
			}
		}else{
			//时间段 8:00-18:30
			int T = (24*60-(end_hour - start_hour)*60-(end_minute-start_minute));
			if(is_between(permit_time,entertime)){
				//入库时间落在 扣费时间段内部
				
				if(is_between(permit_time,outtime)){
					if(enter_hour*60+enter_minute >= out_hour*60+out_minute){
						int diff_hour = enter_hour - out_hour;
						temp_time = diff_hour*60+(enter_minute-out_minute);
						temp_time = T - temp_time;
					}else{
						int diff_hour = out_hour - enter_hour;
						temp_time = diff_hour*60+(out_minute-enter_minute);
					}
				}else{
					if(enter_hour > 12){
						int diff_hour = (24 - enter_hour)+start_hour;
						temp_time = diff_hour*60+(start_minute-enter_minute);
					}else{
						int diff_hour = start_hour - enter_hour;
						temp_time = diff_hour*60+(start_minute-enter_minute);
					}
				}
			}else{
				//入库时间落在 免费时间段内部
				if(is_between(permit_time,outtime)){
					if(out_hour >= end_hour){
						int diff_hour = out_hour - end_hour;
						temp_time = diff_hour*60+(out_minute-end_minute);	
					}else{
						int diff_hour = out_hour - end_hour;
						temp_time = diff_hour*60+(out_minute-end_minute);
					}
				}else{
					if(enter_hour*60+enter_minute >= out_hour*60+out_minute){
						//都落免费区间 且  入库时间小时 大于 出库时间小时
						temp_time = T;
					}else{
							temp_time = 0;
					}
				}
			}
		}
	}
	return temp_time;
}



public static void main(String[] args) {
	/*Calendar cl = Calendar.getInstance(Locale.CHINA);
	cl.set(2016, 8, 3, 19, 30);
	
	Calendar cl2 = Calendar.getInstance(Locale.CHINA);
	cl2.set(2016, 8, 4, 16, 30);
	
	Calendar cl3 = Calendar.getInstance(Locale.CHINA);
	cl3.set(2016, 8, 3, 17, 30);
	
	Calendar cl4 = Calendar.getInstance(Locale.CHINA);
	cl4.set(2016, 8, 6, 15, 0);
	long tt = calculateMonthPay("18:30-08:00",cl.getTime(),cl2.getTime(),cl3.getTime(),cl4.getTime());
	System.out.println(tt);*/
	Calendar cl = Calendar.getInstance(Locale.CHINA);
	cl.set(2016, 8, 5, 12, 30);
	
	Calendar cl2 = Calendar.getInstance(Locale.CHINA);
	cl2.set(2016, 8, 5, 16, 30);
	
	System.out.println(new BaseBiz().one_day("24:00-23:00",cl.getTime(),cl2.getTime())); 
}

}
