package com.park.util;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.park.core.Constants;
import com.park.exception.QzException;
public class Dom4jUtil {
	private static Logger loger = Logger.getLogger(Dom4jUtil.class); 
	private static JSONArray jsonroot = null;
	private static Map<String,JSONObject> jsonUserLevel = null;//用户等级
    public static JSONArray getJsonroot() {
    	if(jsonroot == null){
    	  jsonroot = 	new JSONArray();
    	  READXMLFile();
    	}
		return jsonroot;
	}
	public static Map<String,JSONObject> getJsonUserLevel() {
		if(jsonUserLevel == null){
			jsonUserLevel = 	new Hashtable<String, JSONObject>();
			READUserLevelXMLFile();
	    }
		return jsonUserLevel;
	}
	
	
	/**
	 * 读取用户等级奖励
	 */
	public static void READUserLevelXMLFile(){
	       try{
	    	   String  filepath = Constants.SYSTEM_ROOT_PATH+"WEB-INF/config/properties/userLevel.xml";
	           SAXReader saxReader = new SAXReader();
	           Document document = saxReader.read(new File(filepath));
	           Element root = document.getRootElement();
				for (Iterator<?> i = root.elementIterator(); i.hasNext();) { 
					Element el = (Element) i.next();
					String id = el.attribute("id").getValue();
					String level = el.attribute("最低领取等级").getValue();
					String gold = el.attribute("奖励金币").getValue();
					JSONObject json = new JSONObject();
					json.put("id", id);
					json.put("level", level);
					json.put("gold", gold);
					getJsonUserLevel().put(id,json);
				}
	          
	       }catch(Exception ex){
	    	   loger.error("加载用户等级奖励模板文件失败", ex);
	       }
	    }
	
	
	
	
	
	
	
	
	
	/**
     * 读取活动礼包配置XML文件 
     */
	public static void READXMLFile(){
       try{
    	   String  filepath = Constants.SYSTEM_ROOT_PATH+"WEB-INF/config/properties/ActivityGiftBag.xml";
    	  // filepath = System.getProperty("user.dir")+"/webapp/WEB-INF/config/properties/ActivityGiftBag.xml";
           SAXReader saxReader = new SAXReader();
           Document document = saxReader.read(new File(filepath));
           Element root = document.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext();) { 
				JSONObject json = new JSONObject();
				Element el = (Element) i.next();
				String id = el.attribute("id").getValue();
				json.put("id", id);
				System.out.println(id); 
				Element el1 = el.element("获取条件");
				String up = el1.attribute("充值金额上限").getValue();
				String floor = el1.attribute("充值金额下限").getValue();
				json.put("floor", floor);
				json.put("up", up);
				
				Element el2 = el.element("奖励");
				String gold = el2.attribute("金币").getValue();
				String exper = el2.attribute("经验").getValue();
				json.put("gold", gold);
				json.put("exper", exper);
				
				getJsonroot().add(json);
			}
          
       }catch(Exception ex){
    	   loger.error("加载活动奖励模板文件失败", ex);
       }
    }
    
    /**
     * 遍历进行处理
     */
public static JSONObject returnResult(long money){ 
	JSONObject obj = null;
	for (int i = 0; i < getJsonroot().size(); i++) {
		obj = getJsonroot().getJSONObject(i);
		long floor = obj.getLong("floor");
		long up = obj.getLong("up");//上限
		if(money > 0 && money >= floor && money < up ){
			break;
		}
	}
	return obj;
	
}    
 
/**
 * 通过模板ID获取对应的数据
 */
public static JSONObject returnJsonByTtemplateID(int gbid){ 
	JSONObject obj = null;
	for (int i = 0; i < getJsonroot().size(); i++) {
		obj = getJsonroot().getJSONObject(i);
		if(gbid == obj.getIntValue("id")  ){
			break;
		}
	}
	return obj;
	
} 

/**
 * 向代领取表中插入数据
 */
public static void inserAgent(long ui_id,long money) throws Exception{
	try {
		JSONObject obj =  returnResult(money);
		if(obj != null && !obj.isEmpty()){//数据有效
			int gb_id = obj.getIntValue("id"); 
			String uuid = UUID.randomUUID().toString();
			long gb_create_time = System.currentTimeMillis();
			String sql = "insert into ac_t_gift_bag(uuid,gb_user_id,gb_id,gb_create_time) values('%s',%s,%s,%s)";
			sql = String.format(sql, uuid,ui_id,gb_id,gb_create_time);
			Constants.daoFactory.getCoreService().executeNoRsSQL(sql);
		}
		
	} catch (QzException e) {
		loger.error("用户ID="+ui_id+"向代领取表中插入数据失败", e);
		throw new Exception("用户ID="+ui_id+"向代领取表中插入数据失败",e);
	}
}


/**
 * 更改用户账户信息
 */
public static void doUserGift(long money,long ui_id) throws Exception{
	JSONObject obj =  returnResult(money);
	if(obj != null && !obj.isEmpty()){//数据有效
		//进行更新用户账户信息
		long gold = obj.getLong("gold");//金币
		long exper = obj.getLong("exper");//小时
		exper = exper * 60;
		String sql = "update user_info set ui_online_time=ui_online_time+%s where ui_id=%s";
		sql = String.format(sql, exper,ui_id);
		try {
			Constants.daoFactory.getCoreService().executeNoRsSQL(sql);
		} catch (QzException e) {
			loger.error("用户ID="+ui_id+"充值更改用户在线时长失败", e);
			throw new Exception("用户ID="+ui_id+"充值更改用户在线时长失败",e);
		}
		sql = "update user_gift_info set ui_gold=ui_gold+%s where ui_id=%s";
		sql = String.format(sql, gold,ui_id);
		try {
			Constants.daoFactory.getCoreService().executeNoRsSQL(sql);
		} catch (QzException e) {
			loger.error("用户ID="+ui_id+"充值更改用户金币失败", e);
			throw new Exception("用户ID="+ui_id+"充值更改用户金币失败",e);
		}
	}
	
}
}
