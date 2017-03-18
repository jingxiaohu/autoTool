//package com.park.spider;
//
//import java.util.Calendar;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.junit.Test;
//
///**
// * 蜻蜓电台抓取类
// * @author jingxiaohu
// *
// */
//public class QingTingRadioSpider extends BaseSpider{
//	/**
//	 * {
//
//    "errorno": ​20002,
//    "errormsg": "token_expired"
//
//}
//	 * 整套接口通信采用UTF-8编码   返回数据格式 JSON
//	 */
//	//全局变量
//	private static String client_id =  "YTZlMzczYjQtZThhNi0xMWU0LTkyM2YtMDAxNjNlMDAyMGFk";
//	//针对合作伙伴 XXXX：注意下面接口中提到的soure参数请使用固定字符串’XXXX’
//	private static String client_secret  = "MGFiODIwYjctYzU0Zi0zZDFlLWI3ZmUtY2IwOTM1ZTI1ZGE1";
//	//设备名称
//	private static String accesstoken = null;
//	
//	
//	static{
//		getAccesstoken();
//	}
//	
//	/**
//	 * 蜻蜓accesstoken 授权
//	 * {"access_token":"YTUzNWI3ZTgtZDk5OS00MTA2LWI5MjItZjFiMTliYTY3NDdl","token_type":"bearer","expires_in":7200}
//	 */
//	public static void getAccesstoken(){
//		try {
//			String url="http://api.open.qingting.fm/access?&grant_type=client_credentials";
//			NameValuePair[] params = new NameValuePair[]{
//					 new 	NameValuePair("client_id",client_id),
//					 new    NameValuePair("client_secret",client_secret)
//				};
//			String result  = dopost(url, null, params);
//			if(result != null && !"HTTP_GET_ERROR".equals(result)){
//				JSONObject accesstoken_obj = JSONObject.fromObject(result);
//				if(accesstoken_obj != null && !accesstoken_obj.isNullObject()){
//					accesstoken =  accesstoken_obj.getString("access_token");
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getAccesstoken is error", e);
//		}
//	}
//	
//	/**
//	 * 获取点播所有分类
//	 * http://api.open.qingting.fm/v6/media/categories?access_token=#{access_token}
//	 * {
//    "errorno": 0,
//    "errormsg": "",
//    "data": [
//        {
//            "id": 521,
//            "name": "小说",
//            "sequence": 1,
//            "section_id": 208,
//            "type": "category"
//        },
//        {
//            "id": 523,
//            "name": "音乐",
//            "sequence": 2,
//            "section_id": 139,
//            "type": "category"
//        },
//	 */
//	public static JSONArray  getCategoryList_dianbo(){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/categories?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_dianbo点播分类列表失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_dianbo:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_dianbo is error", e);
//		}
//		return null;
//	} 
//	
//	/**
//	 * 直播分类及获取直播属性
//	 * http://api.open.qingting.fm/v6/media/categories?access_token=#{access_token}&category_id=5
//	{
//    "errorno": 0,
//    "errormsg": "",
//    "data": [
//        {
//            "id": 521,
//            "name": "小说",
//            "sequence": 1,
//            "section_id": 208,
//            "type": "category"
//        },
//        {
//            "id": 523,
//            "name": "音乐",
//            "sequence": 2,
//            "section_id": 139,
//            "type": "category"
//        },
//	 */
//	public static JSONArray  getCategoryList_onlive(){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/categories?access_token="+accesstoken;
//			NameValuePair[] params = new NameValuePair[]{
//					 new 	NameValuePair("category_id","5")
//				};
//			String result = dopost(url, null,params);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_onlive直播分类列表失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_onlive:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_onlive is error", e);
//		}
//		return null;
//	}
//	
//	
//	
//	/**
//	 * 获取某个分类下的属性
//	 * {
//    "errorno": 0,
//    "errormsg": "",
//    "data": [
//        {
//            "id": 32,
//            "name": "分类",
//            "values": [
//                {
//                    "id": 2079,
//                    "name": "蜻蜓出品",
//                    "sequence": 1
//                },
//                {
//                    "id": 517,
//                    "name": "悬疑探险",
//                    "sequence": 2
//                },
//	 */
//	public static JSONArray getCategoryList_single(String category_id){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/categories/"+category_id+"?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_single获取某个分类下的属性列表失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_single:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_single is error", e);
//		}
//		return null;
//		
//	} 
//	
//	
//	/**
//	 * 获取分类下的所有电台或直播电台
//	 * 作用
//	获取指定分类下的所有电台。
//	URL
//	http://api.open.qingting.fm/v6/media/categories/#{category_id}/channels/order/0/curpage/#{curpage}/pagesize/30?access_token=#{access_token}
//	  点播电台示例
//	  {
//        errorno: 0,
//        errormsg: "",
//        data: [
//            {
//                id: 35242,
//                title: "老梁观世界",
//                description: "老梁将以最犀利的目光解读最热辣的新闻话题，带你探索中国，看懂世界。",
//                update_time: "2015-02-11 09:32:59",
//                chatgroup_id: 0,
//                thumbs: {
//                    small_thumb: "http://qingting-pic.b0.upaiyun.com/2014/0507/20140507115903658.jpg!small",
//                    medium_thumb: "http://qingting-pic.b0.upaiyun.com/2014/0507/20140507115903658.jpg!medium",
//                    large_thumb: "http://qingting-pic.b0.upaiyun.com/2014/0507/20140507115903658.jpg!large"
//                },
//                category_id: 545,
//                type: "channel_ondemand",
//                auto_play: 0,
//                record_enabled: 0,
//                latest_program: "《老梁观世界》 20150210 倒卖出生证背后的真问题",
//                detail: null
//            },
//            ...
//        ],
//        total: 216
//        
//        直播电台示例
//
//   {
//        errorno: 0,
//        errormsg: "",
//        data: [
//            {
//                id: 386,
//                title: "CNR中国之声",
//                description: "中国之声――中央人民广播电台最悠久的第一套节目，中国国家电台最具权威的新闻综合广播；全天24小时不间断播音，遍布海内外数百家电台、与上千名记者全面合作",
//                update_time: "2014-12-24 18:43:17",
//                chatgroup_id: 0,
//                thumbs: -{
//                    small_thumb: "http://pic.qingting.fm/2014/0330/20140330111349821.jpg!small",
//                    medium_thumb: "http://pic.qingting.fm/2014/0330/20140330111349821.jpg!medium",
//                    large_thumb: "http://pic.qingting.fm/2014/0330/20140330111349821.jpg!large"
//                },
//                category_id: 5,
//                type: "channel_live",
//                freq: "",
//                audience_count: 32638765,
//                mediainfo: -{
//                    id: 386
//                }
//            },
//            ...
//        ],
//        total: 1743
//    }
//    }
//	 */
//	public static JSONArray getCategoryList_allchannel(String  category_id,int curpage,int pagesize){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/categories/"+category_id+"/channels/order/0/curpage/"+curpage+"/pagesize/"+pagesize+"?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_allchannel获取分类下的所有电台或直播电台失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_single:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}else{
//				//重置ACCESSTOKEN
//				resetAccessToken(resultjson);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_allchannel is error", e);
//		}
//		return null; 
//	}
//	
//	/**
//	 * 获取专辑列表总条数
//	 * @param pagesize
//	 * @return
//	 */
//	public static int getCategoryList_allchannelCount(String  category_id,int pagesize){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/categories/"+category_id+"/channels/order/0/curpage/1/pagesize/"+pagesize+"?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "HTTP_GET_ERROR".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_allchannel获取分类下的所有电台或直播电台失败"); 
//				return 0;
//			}
//			////result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_single:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getInt("total");
//			}else{
//				//重置ACCESSTOKEN
//				resetAccessToken(resultjson);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_allchannelCount is error", e); 
//		}
//		return 0; 
//	}
//	
//	/**
//	 * 获取音频列表
//	 * 获取点播电台下的点播节目
//	作用
//	获取指定电台的节目列表
//	URL
//	http://api.open.qingting.fm/v6/media/channelondemands/#{channel_id}/programs/curpage/#{curpage}/pagesize/30?access_token=#{access_token}
//	 * @param resultjson
//	 */
//	public static JSONArray getCategoryList_radiolist(String  program_id,int curpage,int pagesize){
//		try {
//			String url = "http://api.open.qingting.fm/v6/media/channelondemands/"+program_id+"/programs/curpage/"+curpage+"/pagesize/"+pagesize+"?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getCategoryList_radiolist获取点播电台下的点播节目失败"); 
//				return null;
//			}
//			result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getCategoryList_radiolist:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}else{
//				//重置ACCESSTOKEN
//				resetAccessToken(resultjson);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getCategoryList_radiolist is error", e);
//		}
//		return null; 
//	}
//	
//    /***************直播电台******************/
//	/**
//	 * 获取直播 分类属性下面的专辑
//	 * @param attrid
//	 * @param curpage
//	 * @param size
//	 * @return
//	 */
//	public static JSONArray getLiveCategoryAttrProgramList(String attrid,int curpage,int size){
//		try {
//			//获取直播电台的属性
//			String url = "http://api.open.qingting.fm/v6/media/categories/5/channels/order/0/attr/"
//			+attrid+"/curpage/"+curpage+"/pagesize/"+size+"?access_token="+accesstoken;
//			
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getLiveCategoryAttrProgramList获取直播 分类属性下面的专辑失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getLiveCategoryAttrProgramList:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONArray("data");
//			}else{
//				//重置ACCESSTOKEN
//				resetAccessToken(resultjson);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getLiveCategoryAttrProgramList is error", e);
//		}
//		return null; 
//		
//	}
//	
//	/**
//	 * 获取直播专辑对应的音频   获取直播电台节目单
//	 * @param resultjson
//	 */
//	public static JSONArray getLive_radiolist(String  channel_id,int pagesize){
//		try {
//			Calendar cl = Calendar.getInstance();
//			String url = "http://api.open.qingting.fm/v6/media/channellives/"+channel_id+"/programs/day/"+cl.get(Calendar.DAY_OF_WEEK)+"?access_token="+accesstoken;
//			String result = dopost(url, null,null);
//			if(result == null || "".equals(result)){
//				log.error("抓取蜻蜓电台的getLive_radiolist 获取直播电台节目单失败"); 
//				return null;
//			}
//			//result = StringEscapeUtils.unescapeJava(result);
//			JSONObject resultjson = JSONObject.fromObject(result);
//			log.info("QingTingRadioSpider-getLive_radiolist:"+resultjson); 
//			if(resultjson != null && !resultjson.isNullObject() && "0".equals(resultjson.getString("errorno"))){
//				return resultjson.getJSONObject("data").getJSONArray(cl.get(Calendar.DAY_OF_WEEK)+""); 
//			}else{
//				//重置ACCESSTOKEN
//				resetAccessToken(resultjson);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("getLive_radiolist is error", e);
//		}
//		return null; 
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	//重置ACCESSTOKEN
//	public static void resetAccessToken(JSONObject resultjson){
//		if(resultjson != null && !resultjson.isNullObject() && resultjson.getInt("errorno") > 20000){
//			getAccesstoken();
//		}
//	}
//	
//	@Test
//	public void test(){
//		getAccesstoken();
//		//点播
////		getCategoryList_dianbo();
//		//直播
//		//getCategoryList_onlive();
//		// 获取某个分类下的属性
////		getCategoryList_single("97960");
//		getCategoryList_radiolist("134478",1,100);
//	}
//	
//	@Test
//	public void testI(){
//		Calendar cl = Calendar.getInstance();
//		System.out.println(cl.get(Calendar.DAY_OF_WEEK));
//		System.out.println(Calendar.DAY_OF_WEEK);
//	}
//	
//	
//}
