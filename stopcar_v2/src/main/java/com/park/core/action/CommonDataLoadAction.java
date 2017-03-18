package com.park.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.park.core.BaseAction;
import com.park.util.RequestUtil;

public class CommonDataLoadAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() throws Exception {
		// 表名必填
		if (RequestUtil.checkObjectBlank(tablename)) {
			return null;
		}
		if(condition !=null && !condition.equals("")){
			condition = new String(condition.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		//进行拦截自己写的SQL
		if(condition != null && condition.contains("jingxiaohu_myselfsql")){ 
			//condition = URLDecoder.decode(condition, "utf-8");
			JSONArray jsonConditionArrary = JSONArray.parseArray(condition);
			if(jsonConditionArrary != null){
				String mysql = "";
				String mytable = "";
				int conditionSize = jsonConditionArrary.size();
				for (int i = 0; i < conditionSize; i++) {
					JSONObject jsonObject = (JSONObject) jsonConditionArrary.get(i);
					if(jsonObject.getString("name").equalsIgnoreCase("jingxiaohu_myselfsql")){ 
						mysql = getJsonValue_MySql( jsonObject,"jingxiaohu_myselfsql");
						if(mytable != null && !"".equals(mytable)){
							break;
						}
					}
					if(jsonObject.getString("name").equalsIgnoreCase("tablename")){
						mytable = getJsonValue_MySql( jsonObject,"tablename");
						if(mysql != null && !"".equals(mysql)){
							break;
						}
					}
				}
				//进行处理SQL
				if(mysql != null && !"".equals(mysql)){
					if(mytable == null || "".equals(mytable)){
						mytable = tablename;
					}
					
					//进行数据组装
					start=	start<0?0:start;
					String sql = "";
					String result_str = "";
						//首先处理计数
						sql = mysql;
						if (!RequestUtil.checkObjectBlank(sql)) {
							Map map = new HashMap();
							List<Map<String, Object>> list = getBean().getCoreService().executeSQLForList(sql);
							if(list!=null&&list.size()>0){
								map.put("total", list.size());
							}else
								map.put("total", 0);
							mysql = mysql+" limit " + start + "," + pagesize;
							map.put("data", getBean().getCoreService().executeSQLForList(mysql));
							result_str = JSONObject.toJSONString(map);
						}
					getResponse().getWriter().write(result_str);
					return null;
				}
				
			}
			
		}
		// pageSize= pageSize==0 ?20:pageSize;
		String sql = CommonTableHelper.sizeSql(tablename, condition);
		String result_str = "";
		if (!RequestUtil.checkObjectBlank(sql)) {
			Map map = new HashMap();
			List<Map<String, Object>> list = getBean().getCoreService().executeSQLForList(sql);
			if(list!=null&&list.size()>0){
				map.put("total", RequestUtil.valifyStr(list.get(0).get("count"), "0"));
			}else
				map.put("total", 0);
			sql = CommonTableHelper.dataSql(tablename, start, pagesize, sortfield, dir, condition);
			try {
				map.put("data", getBean().getCoreService().executeSQLForList(sql));
			} catch (Exception e) {
				e.printStackTrace();
			}
			result_str = JSONObject.toJSONString(map);
		}
		getResponse().getWriter().write(result_str);
		return null;
	}

	
	/**
	 * 
	 * @param json
	 * @param name
	 * @return
	 */
	private  String getJsonValue_MySql(JSONObject json,String name){
		try{
			if(name.equalsIgnoreCase(RequestUtil.valifyStr(json.getString("name"), ""))){
				return RequestUtil.valifyStr(json.getString("value"), "");
			}
		}catch (Exception e) {
			return "";
		}
		return ""; 
	}
	
	private String tablename;// 表名,必填

	private int start = 0;
	private int pagesize;
	private String sortfield;
	private String dir;
	private String condition;// 查询条件，json数组

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getSortfield() {
		return sortfield;
	}

	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
