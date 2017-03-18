///**  
//* @Title: Update360CPS.java
//* @Package jxh.autocoder
//* @Description: TODO(用一句话描述该文件做什么)
//* @author 敬小虎  
//* @date 2015年3月12日 下午1:32:54
//* @version V1.0  
//*/ 
//package jxh.autocoder;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.security.MessageDigest;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.park.bean.Game360_info;
//import com.park.dao.Game360_infoDao;
//
///**
// * 更新本地360CPS包库
// * @ClassName: Update360CPS
// * @Description: TODO(这里用一句话描述这个类的作用)
// * @author 敬小虎
// * @date 2015年3月12日 下午1:32:54
// *
// */
//@Repository(value="update360CPS")
//public class Update360CPS {
//	private  Logger log = Logger.getLogger(Update360CPS.class);
//	@Autowired
//	private Game360_infoDao game360_infoDao;
//	
//	public  String ERROR_RESP = "HTTP_GET_ERROR";
//	//每页条数size
//	public  int size = 300; 
//	//密钥
//	public  String dev_server_secret = "7255538101fffa5fb7c3bfc1d0e07ea7";
//	//360baseuri
//	private  String uri360 = "http://api.np.mobilem.360.cn";
//	///app/cpsgames
//	private  String cpsgame_uri = uri360+"/app/cpsgames?";
//	
//	
//	
//	
//	
//	/**
//	 * 请求360签名认证 并返回游戏数据
//	 * @throws Exception 
//	 */
//	private  String do360Game_CPS(int startyear,int start) throws Exception{
//		Map<String, String> params  = new HashMap<String, String>();
//		//渠道号（由360分配）
//		params.put("from", "lm_83011");
//		//分页使用的起始偏移量，默认0
//		params.put("start", String.valueOf(start));
//		//每次请求返回的记录最大数量，默认20，最大300
//		params.put("num", String.valueOf(size));
//		//设置起始时间
//		ReturnTimeDiff(params,startyear);
//		//组建请求参数
//		String str = getSignature(params,dev_server_secret);
//		String  data = doGet(cpsgame_uri+str,null);
//		if(JSONObject.fromObject(data).containsKey("errMsg")){
//			System.out.println(new String(JSONObject.fromObject(data).getString("errMsg").getBytes()));	
//		}
//		return data;
// 	}
//	
//	/**
//	 * 解析360CPS游戏
//	 * @throws Exception 
//	 */
//	public void doAnalysis(int startyear,int startpage) throws Exception{
//		Date date = new Date();
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String  data = do360Game_CPS(startyear,startpage);
//		
//		if(data == null || "".equalsIgnoreCase(data)){
//			return;
//		}
//		JSONObject obj = JSONObject.fromObject(data);
//		if(obj == null || obj.isNullObject()){
//			return;
//		}
//		//解析并入库
//		insertGame360info( date, obj, sf);
//		//总条数
//		int total = obj.getInt("total");
//		//起始页码位置
//		int start = obj.getInt("start");
//		start++;
//		//总页数
//		int pages = 0;
//		if(total%size == 0){
//			pages = total/size; 
//		}else{
//			pages = total/size+1;
//		}
//		for (int i = start; i < pages; i++) {
//			data = do360Game_CPS(startyear,i);
//			
//			if(data == null || "".equalsIgnoreCase(data)){
//				return;
//			}
//			obj = JSONObject.fromObject(data);
//			if(obj == null || obj.isNullObject()){
//				return;
//			}
//			//解析并入库
//			insertGame360info( date, obj, sf);
//		}
//	}
//	
//	public void insertGame360info(Date date,JSONObject obj,SimpleDateFormat sf) throws Exception{
//		JSONArray items = obj.getJSONArray("items");
//		if(items != null && items.size() > 0){
//			
//			//List<Game360_info> list = new ArrayList<Game360_info>();
//			for (int i = 0; i < items.size(); i++) {
//				JSONObject obj360 = items.getJSONObject(i);
//				//进行组装数据bean对象
//				Game360_info game360_info = new Game360_info();
//				game360_info.setGi_name(returnStr(obj360.getString("name")));
//				//versionName
//				game360_info.setGi_version(obj360.getString("versionName"));
//				//versionCode
//				game360_info.setGi_versioncode(obj360.getString("versionCode"));
//				//packageName
//				game360_info.setGi_package(obj360.getString("packageName"));
//				//developer
//				game360_info.setGi_developer(obj360.getString("developer"));
//				//iconUrl
//				game360_info.setGi_img_logo(obj360.getString("iconUrl"));
//				//screenshotsUrl
//				game360_info.setGi_img_cut(obj360.getString("screenshotsUrl")); 
//				//incomeShare 是否支持分成
//				game360_info.setGi_income_share(obj360.getInt("incomeShare"));
//				//rating 应用评分
//				game360_info.setGi_rating(obj360.getDouble("rating")); 
//				//minVersion
//				game360_info.setGi_minversion(obj360.getString("minVersion"));
//				//minVersionCode
//				game360_info.setGi_minversioncode(obj360.getString("minVersionCode"));
//				//categoryName "游戏:休闲益智",
//				game360_info.setGi_categoryname(returnStr(obj360.getString("categoryName"))); 
//				//description
//				game360_info.setGi_desc(returnStr(obj360.getString("description")));
//				//boxLabel应用角标（0：无，1：首发，2：最新，3：热门，4：独家，5：推广，6：活动，7：特权）
//				game360_info.setGi_boxLabel(obj360.getInt("boxLabel"));
//				//tag 标签
//				game360_info.setGi_tag(returnStr(obj360.getString("tag")));
//				//priceInfo 付费信息
//				if(returnStr(obj360.getString("priceInfo")).contains("付费")){
//					game360_info.setGi_is_pay(1);
//				}else{
//					game360_info.setGi_is_pay(0);
//				}
//				//language
//				game360_info.setGi_language(returnStr(obj360.getString("language")));
//				//signatureMd5
//				game360_info.setGi_signaturemd5(obj360.getString("signatureMd5"));
//				//isAd
//				game360_info.setGi_is_ad(obj360.getInt("isAd"));
//				//brief
//				game360_info.setGi_brief(returnStr(obj360.getString("brief")));
//				//updateTime
//				//game360_info.setGi_updatetime_str(obj360.getString("updateTime"));
//				//rDownloadUrl 当前版本下载地址（市场类产品请使用这个属性）
//				game360_info.setGi_url(obj360.getString("rDownloadUrl"));
//				//downloadUrl 	最新版下载地址（市场类产品请不要使用该属性，由于同步时差，下载到的apk版本号可能与您保存的版本号不一致。）
//				game360_info.setGi_note(obj360.getString("downloadUrl"));
//				//时间
//				game360_info.setGi_time(date.getTime()); 
//				game360_info.setGi_time_str(sf.format(date));
//				game360_info.setGi_updatetime(date.getTime());
//				game360_info.setGi_updatetime_str(sf.format(date)); 
//				game360_info.setGi_src(2);//0:联运游戏 1:其它渠道游戏 2:360
//				game360_info.setGi_size(String.valueOf(obj360.getInt("apkSize")/(1024*1024)));
//				//list.add(game360_info);
//				//System.out.println(list.size());
//				game360_infoDao.insert(game360_info);
//			}
//			//批量插入
//			//game360_infoDao.insert(list);
//			
//		}
//	}
//	
//	/************************下面是签名等算法***************************/
//	/**
//	 * 请求头集合
//	 * @param url
//	 * @param header
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	public  String doGet(String url,Map<String, String> header){
//		HttpClient  hc = new HttpClient();
//		GetMethod get =  null;
//		try{
//			hc.setConnectionTimeout(20*1000);
//			hc.setTimeout(20*1000);
//			get =  new GetMethod(url);
//			get.setRequestHeader("Connection", "close");  
//			get.addRequestHeader("Content-Type", "application/json;charset=utf-8");
//			get.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//			get.addRequestHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//			if(header!=null){
//				for(String h:header.keySet()){
//					get.addRequestHeader(h, header.get(h));
//				}
//			}
//			hc.executeMethod(get);
//			if(get.getStatusCode() == 200){
//				InputStream resStream = get.getResponseBodyAsStream();  
//		        BufferedReader br = new BufferedReader(new InputStreamReader(resStream));  
//		        StringBuffer resBuffer = new StringBuffer();  
//		        String resTemp = "";  
//		        while((resTemp = br.readLine()) != null){  
//		            resBuffer.append(resTemp);  
//		        }  
//		        String response = resBuffer.toString();  
//				//return get.getResponseBodyAsString();
//		        return response;
//			}else{
//				log.error(url+" req error StatusCode:"+get.getStatusCode());
//			}
//		}catch(Exception e){
//			log.error("doGet error", e);
//			return ERROR_RESP;
//		}finally{
//			if(get!=null){
//				get.releaseConnection();
//				//释放链接
//				hc.getHttpConnectionManager().closeIdleConnections(0);
//			}
//		}
//		return ERROR_RESP;
//	}
//	
//	/**
//     * 签名生成算法
//     *
//     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
//     * @param String                 dev_server_secret 开发者在有米后台设置的密钥
//     * @return 签名
//     * @throws IOException
//     */
//    public  String getSignature(Map<String, String> params, String dev_server_secret) throws IOException {
//        // 先将参数以其参数名的字典序升序进行排序
//        Map<String, String> sortedParams = new TreeMap<String, String>(params);
// 
//        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
//        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
//        StringBuilder basestring = new StringBuilder();
//        for (Map.Entry<String, String> param : entrys) {
//            basestring.append(param.getKey()).append("=").append(param.getValue()).append("&");
////        	  basestring.append(param.getKey()).append("=").append(param.getValue());
//        }
//        basestring.replace(basestring.lastIndexOf("&"),basestring.length(), "");
//        //请求字符串
//        String requestUrl = basestring.toString();
//        //加密钥
//        basestring.append(dev_server_secret);
//        // 使用MD5对待签名串求签
//        byte[] bytes = null;
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
//        } catch (GeneralSecurityException ex) {
//        	log.error("getSignature is error", ex);
//            throw new IOException(ex);
//            
//        }
//        // 将MD5输出的二进制结果转换为小写的十六进制
//        StringBuilder sign = new StringBuilder();
//        for (int i = 0; i < bytes.length; i++) {
//            String hex = Integer.toHexString(bytes[i] & 0xFF);
//            if (hex.length() == 1) {
//                sign.append("0");
//            }
//            sign.append(hex);
//        }
//        String sign_str =  sign.toString();
//        
//        //进行组装
//        return requestUrl+"&sign="+sign_str;
//    } 
//    
//    public   String getSign(String params) {
//		String[] paramsArray = params.split("&");
//		List<String> paramsList = Arrays.asList(paramsArray);// 数组转list
//		Collections.sort(paramsList);// 进行自然升序排列
//		params = Arrays.toString(paramsList.toArray());// 输出数组字符串
//		params = params.substring(1, params.length() - 1).replace(
//				", ", "&")
//				 + dev_server_secret;// 加入密钥
//		System.out.println(params);
//		return DigestUtils.md5Hex(params);
//	}
//    
//    /**
//     * 设置时间 
//     */
//    public  void ReturnTimeDiff(Map<String, String> params,int startyear){
//    	Calendar cl = Calendar.getInstance();
//    	cl.set(Calendar.YEAR, startyear);
//    	long starttime = cl.getTimeInMillis()/1000;
//    	Date date = new Date();
//    	cl.setTime(date);
//    	long endtime = cl.getTimeInMillis()/1000;
//		//按时间返回获取应用的起始时间（Unix时间戳），单位为秒
//		params.put("starttime", starttime+"");
//		//按时间返回获取应用的结束时间（Unix时间戳），单位为秒 	
//		params.put("endtime", endtime+"");
//    }
//    /**
//     * ASCII2UTF-8
//     */
//    public String returnStr(String str){
//    	return new 	String(str.getBytes());
//    }
//    
//    
//}
