package com.park.core.action;
/*package com.qz.core.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.qz.core.BaseAction;
import com.qz.core.Constants;
import com.qz.util.InitTableInfo;
import com.qz.util.RequestUtil;
import com.qz.util.InitTableInfo.PathUtil;

public class CommonDataCUDAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CommonDataCUDAction.class);

	public String execute() throws Exception {
		// 表名必填
		if (RequestUtil.checkObjectBlank(tablename)) {
			return null;
		}
		PathUtil pathUtil = new InitTableInfo().new PathUtil();
		String filePath = pathUtil.getWebInfPath()+Constants.tableTemplate;
		Properties prop  = new Properties();
		InputStream fis = new FileInputStream(filePath);
		// 从输入流中读取属性列表（键和元素对）
		prop.load(fis);
		String fieldArray = prop.getProperty(tablename,"");
		//检测tableTemplate是否有该表
		if("".equals(fieldArray)){
			return null;
		}
		if(opt==1 || opt==2){
			if("".equals(RequestUtil.valifyStr(id, ""))){
				return null;
			}
		}
		HttpServletRequest request = getRequest();
		String sql=null;
		if(opt==0){
			sql = CommonTableHelper.inertSql(tablename, fieldArray, request);
		}else if(opt==1){
			sql = CommonTableHelper.delSql(tablename,  request);
		}else if(opt==2){
			//修改
			sql = CommonTableHelper.updateSql(tablename, fieldArray, request);
		}
		if(!RequestUtil.checkObjectBlank(sql)){
			System.out.println("sql:============"+sql);
			getBean().getCoreService().executeNoRsSQL(sql);
			return null;
		}
		String result_str = "no result";
		getResponse().getWriter().write(result_str);
		return null;
	}

	private String tablename;// 表名,必填
	private int opt = 0; //操作类型 ,增、删、改(0,1,2)
	private String id; //删、改必填的主键
	private String condition;// 查询条件，json数组

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
*/