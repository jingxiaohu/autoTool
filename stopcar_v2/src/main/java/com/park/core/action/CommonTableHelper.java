package com.park.core.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.park.util.RequestUtil;

/**
 * 通用生成sql的工具类
 * 
 * @author tt
 * 
 */
public class CommonTableHelper {

	public static String dataSql(String tablename, int start, int pageSize, String sort, String dir, String condition) throws UnsupportedEncodingException {
		StringBuffer sbSqlTemp = new StringBuffer();
		// 匹配查询字段

		sbSqlTemp.append("select ").append("*").append(" from ").append(tablename);
		if (!RequestUtil.checkObjectBlank(condition)) {
			condition = URLDecoder.decode(condition, "utf-8");
			// 条件解析失败
			String conditionSql = hanldeCondition(condition);
			if (conditionSql != null&&conditionSql.length()!=0) {
				sbSqlTemp.append(" where ").append(conditionSql);
			}
		}
		// 添加order by 条件
		if (!RequestUtil.checkObjectBlank(sort)) {
			sbSqlTemp.append(" order by  ").append(sort);
			// 添加方向条件
			if (!RequestUtil.checkObjectBlank(dir)) {
				sbSqlTemp.append(" " + dir);
			}
		}
		// start 为0或者1时，sql的start都是0
		//start=start-1;
		start=	start<0?0:start;
		sbSqlTemp.append(" limit " + start + "," + pageSize);

		return sbSqlTemp.toString();
	}
	
	public static String sizeSql(String tablename,String condition) throws UnsupportedEncodingException {
		StringBuffer sbSqlTemp = new StringBuffer();
		// 匹配查询字段

		sbSqlTemp.append("select ").append("count(*) as count").append(" from ").append(tablename);
		if (!RequestUtil.checkObjectBlank(condition)) {
			condition = URLDecoder.decode(condition, "utf-8");
			// 条件解析失败
			String conditionSql = hanldeCondition(condition);
			if (conditionSql != null&&conditionSql.length()!=0) {
				sbSqlTemp.append(" where ").append(conditionSql);
			}
		}
		String str = sbSqlTemp.toString();
		str = str.trim();
		/*if(str.contains("and") && str.lastIndexOf("and")==str.length()-3){
			str = str.substring(0,str.lastIndexOf("and"));
		}*/
		return str;
	}
	
	/**
	 * 
	 * @param json
	 * @param name
	 * @return
	 */
	private static String getJsonValue(JSONObject json,String name){
		try{
			return RequestUtil.valifyStr(json.getString(name), "");
		}catch (Exception e) {
			return "";
		}
	}

	/**
	 * 解析where后面的condtion字段
	 * 
	 * @param condition
	 *            json数组
	 * @return NULL表示参数错误,正确时返回where后面的字符串
	 */
	private static String hanldeCondition(String condition) {
		// 匹配查询条件
		StringBuffer sbSqlCondition = new StringBuffer();
		if (!RequestUtil.checkObjectBlank(condition)) {
			sbSqlCondition.append(1).append("="). append(1);
			JSONArray jsonConditionArrary = JSONArray.parseArray(condition);
			int conditionSize = jsonConditionArrary.size();
			/*if(conditionSize >0){
				sbSqlCondition.append(" and ");
			}*/
			for (int i = 0; i < conditionSize; i++) {
				JSONObject jsonObject = (JSONObject) jsonConditionArrary.get(i);
				String type = getJsonValue(jsonObject,"type");
				String name = getJsonValue(jsonObject,"name");
				String value = getJsonValue(jsonObject,"value");
				String otherValue = getJsonValue(jsonObject,"othervalue");
				if("mi_content".equals(name)){
					try {
						value = URLEncoder.encode(value, "utf-8");
					} catch (UnsupportedEncodingException e) {
					}
				}
				
				if(!RequestUtil.checkObjectBlank(value)){
					sbSqlCondition.append(" and ");
					// 精确查找
					if (type.equals("0")) {
								sbSqlCondition.append(name).append("=").append("'"). append(value).append("'");
					}
					// 模糊查找
					if (type.equals("1")) {
							sbSqlCondition.append(name).append(" like ").append("'").append("%" + value + "%").append("'");
					}
					// between查询
					if (type.equals("2")&&!RequestUtil.checkObjectBlank(otherValue)) {
						sbSqlCondition.append(" and ");
						sbSqlCondition.append(name).append(" between ").append("'").append(value).append("'").append(" and ").append("'").append(otherValue).append("'").append(" ");
					}
					// or查询
					if (type.equals("3")&&!RequestUtil.checkObjectBlank(otherValue)) {
						sbSqlCondition.append(" and ");
						sbSqlCondition.append(name).append(" = ").append("'").append(value).append("'").append(" or ").append(name).append(" = ").append("'").append(otherValue).append("'");
					}
				}
				// 记录为空
				if (type.equals("4")) {
					sbSqlCondition.append(name).append(" is null ");
				}
			}
		}
		return sbSqlCondition.toString().replace("and  and", " and ");
	}
	
	/**
	 * 新增一条记录的SQL语句
	 * @param tablename
	 * @param fields
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String inertSql(String tablename,String fields,HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		sb.append("insert into "+tablename+" ");
		JSONArray jsonArray = JSONArray.parseArray(fields);
		Map<String,String[]> map = request.getParameterMap();
		JSONObject o = null;
		String name="";
		String keys="";
		String values="";
		for(int i=0;i<jsonArray.size();i++){
			o = jsonArray.getJSONObject(i);
			if(o.containsKey("name")){
				name  = o.getString("name");
				if(map.containsKey(name)){
					if(!"".equals(keys)){
						keys+=",";
					}
					if(!"".equals(values)){
						values+=",";
					}
					keys+=name;
					values+="'"+map.get(name)[0]+"'";
				}
			}
		}
		if("".equals(keys)){
			return null;
		}
		sb.append("("+keys+")"+" values ("+values+")");
		return sb.toString();
	}
	
	/**
	 * 删除操作
	 * @param tablename
	 * @param request
	 * @return
	 */
	public static  String delSql(String tablename,HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		sb.append("delete from "+tablename+" ");
		String key  =request.getParameter("key");
		String value = request.getParameter("id");
		//参数不完整,不予处理
		if("".equals(RequestUtil.valifyStr(key, "")) || "".equals(RequestUtil.valifyStr(value, ""))){
			return null;
		}
		sb.append(" where "+key+"='"+value+"'");
		return  sb.toString();
	}
	
	/**
	 * 修改操作
	 * @param tablename
	 * @param fields
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String updateSql(String tablename,String fields,HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		sb.append("update "+tablename+" set ");
		JSONArray jsonArray = JSONArray.parseArray(fields);
		Map<String,String[]> map = request.getParameterMap();
		JSONObject o ;
		String name="";
		String kv="";
		for(int i=0;i<jsonArray.size();i++){
			o = jsonArray.getJSONObject(i);
			if(o.containsKey("name")){
				name  = o.getString("name");
				if(map.containsKey(name)){
					if(!"".equals(kv)){
						kv+=",";
					}
					kv+=name+"='"+map.get(name)[0]+"'";
				}
			}
		}
		if("".equals(kv)){
			return null;
		}
		sb.append(kv+" where "+request.getParameter("key")+"='"+request.getParameter("id")+"'");
		return sb.toString();
	}
	/**
	 * 
	 * @param tablename
	 * @param opt
	 * @param id
	 * @param condition
	 * @return
	 */
	public static String cudSql(String tablename,int opt,String id,String condition){
		StringBuffer sb = new StringBuffer();
		JSONArray jsonArray = JSONArray.parseArray(condition);
		if(opt==1){
			//删除
			if(!jsonArray.isEmpty()){
				sb.append("delete from "+tablename+" where ");
				JSONObject cd = jsonArray.getJSONObject(0);
				String name = cd.getString("name");
				String value = cd.getString("value");
				sb.append(name+"='"+value+"'");
			}
		}else if(opt==2){
			//修改,至少有一个修改条件
			if(jsonArray.size()>1){
				String keyName = jsonArray.getJSONObject(0).getString("name");
				sb.append("update "+tablename+" set ");
				String tmp = "";
				JSONObject kv ;
				for(int i=1;i<jsonArray.size();i++){
					if(!"".equals(tmp)){
						tmp+=",";
					}
					kv=  jsonArray.getJSONObject(i);
					tmp+=kv.getString("name")+"='"+kv.getString("value")+"'";
				}
				sb.append(tmp).append(" where "+keyName+"='"+id+"'");
			}
		}else if(opt==0){
			//新增,至少有一个条件
			if(!jsonArray.isEmpty()){
				sb.append("insert into "+tablename+" ");
				String keys="";
				String values="";
				JSONObject kv ;
				for(int i=0;i<jsonArray.size();i++){
					if(!"".equals(keys)){
						keys+=",";
					}
					if(!"".equals(values)){
						values+=",";
					}
					kv=  jsonArray.getJSONObject(i);
					keys+=kv.getString("name");
					values+="'"+kv.getString("value")+"'";
				}
				sb.append("("+keys+")"+" values ("+values+")");
			}
		}
		return sb.toString();
	}
}
