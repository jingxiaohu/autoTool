package com.highbeauty.sql.spring.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

import com.highbeauty.pinyin.PinYin;
import com.highbeauty.util.DbInfoUtil;

public class ABuilder {

	/**
	 * @param src:false:不写在SRC下 true:写在SRC下
	 * @param pkg :"test.dao."
	 * @param tablenames:{"t_radio1","t_radio2"}
	 * @param ip:127.0.0.1
	 * @param port:3306
	 * @param user:root
	 * @param password:root
	 * @param databaseName:bag_radio
	 * @param  moduleName MAVEN项目中的module
	 * @throws Throwable
	 */
	public static void  AutoCoder(boolean is_maven,boolean src,String moduleName,String pkg,String[] tablenames,String ip,int port,String user,String password,String databaseName) throws Throwable{
//		boolean immediately = true;
//		String appcontext = "";
		Connection conn = null;
		for (String tablename : tablenames) {
			conn = SqlEx.newMysqlConnection(ip,port, databaseName, user, password);
			Map<String,String>  map = DbInfoUtil.returnRemarkInfo(ip, port, databaseName, user, password, true, "UTF-8", tablename);
			BeanBuild(moduleName,is_maven,conn, tablename, pkg, src,map);
			conn = SqlEx.newMysqlConnection(ip,port, databaseName, user, password);
			DaoBuild(moduleName,is_maven,conn, tablename, pkg, src,map);
			conn = SqlEx.newMysqlConnection(ip,port, databaseName, user, password);
			map = DbInfoUtil.returnRemarkInfoDOC(ip, port, databaseName, user, password, true, "UTF-8", tablename);
			DocBuild(moduleName,is_maven,conn, tablename, pkg, src,map);
			//conn = SqlEx.newMysqlConnection(ip,port, databaseName, user, password);
			//ServiceBuild(conn, tablename, appcontext, pkg, src, immediately);
		}

		//创建DAO代理工厂
		DaoFactoryBuild(moduleName,is_maven,tablenames, pkg, src);

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	/*public static void main(String[] args) throws Exception {
		// String driver = "com.mysql.jdbc.Driver";
		String host = "127.0.0.1";
		String db = "bag_radio";
		// int port = 3306;
		// boolean reconnect = true;
//		 String encoding = "utf-8";
		// String url =
		// "jdbc:mysql://127.0.0.1:3306/accounts?autoReconnect=true&characterEncoding=utf-8";
		// String user = "root";
		// String password = "";
		boolean immediately = true;
		// String appcontext = AppContext.class.getName();
		String appcontext = "";
		String tablename = "t_radio";
		String pkg = "test.dao.";
		Connection conn = null;
		boolean src = false;
		conn = SqlEx.newMysqlConnection(host, db);
		BeanBuild(conn, tablename, pkg, src);
		conn = SqlEx.newMysqlConnection(host, db);
		DaoBuild(conn, tablename, pkg, src);
		conn = SqlEx.newMysqlConnection(host, db);
		ServiceBuild(conn, tablename, appcontext, pkg, src, immediately);
		//EntityBuild(conn, tablename, appcontext, pkg, src, immediately);
	}*/
	public static  void main(String[] args) throws Throwable {
		boolean src = true;
		String pkg = "com.wradio.";
		String[] tablenames = {"t_radio"};
		String ip = "127.0.0.1";
		int port = 3306;
		String user = "root";
		String password = "root";
		String databaseName = "bag_radio";
//		com.highbeauty.sql.spring.builder.ABuilder.AutoCoder(src, pkg, tablenames, ip, port, user, password, databaseName);
	}
	public static void BeanBuild(String moduleName,boolean is_maven,Connection conn, String tablename, String pkg,
			boolean src,Map<String,String> map) throws Exception {

		String sql = String.format("SELECT * FROM `%s` LIMIT 1", tablename);
		ResultSet rs = SqlEx.executeQuery(conn, sql);
		BeanBuilder builder = new BeanBuilder();
		String xml = builder.build(rs, pkg + "bean", true,map);
		System.out.println(xml);
		String filename = file(is_maven,pkg, src, "bean", tablename, "java");
		if(moduleName != null && !"".equalsIgnoreCase(moduleName)){
			filename = moduleName+File.separator+filename;
		}
		writeFile(filename, xml);
		conn.close();
	}

	public static void DaoBuild(String moduleName,boolean is_maven,Connection conn, String tablename, String pkg,
			boolean src,Map<String,String> map_comment) throws Exception {

		String sql = String.format("SELECT * FROM `%s` LIMIT 1", tablename);
		ResultSet rs = SqlEx.executeQuery(conn, sql);
		NewDaoBuilder builder = new NewDaoBuilder();
		String xml = builder.build(conn, rs, pkg + "dao", pkg + "bean",map_comment);
		System.out.println(xml);
		String filename = file(is_maven,pkg, src, "dao", tablename + "Dao", "java");
		if(moduleName != null && !"".equalsIgnoreCase(moduleName)){
			filename = moduleName+File.separator+filename;
		}
		writeFile(filename, xml);
		conn.close();

	}

	public static void DaoFactoryBuild(String moduleName,boolean is_maven,String[] tablenames, String pkg,
			boolean src) throws Exception {

		NewDaoFactoryBuilder builder = new NewDaoFactoryBuilder();
		String xml = builder.build(tablenames, pkg+ "dao");
		System.out.println(xml);
		String filename = file(is_maven,pkg, src, "dao", "DaoFactory", "java");
		if(moduleName != null && !"".equalsIgnoreCase(moduleName)){
			filename = moduleName+File.separator+filename;
		}
		writeFile(filename, xml);
	}


	public static void ServiceBuild(boolean is_maven,Connection conn, String tablename,
			String appcontext, String pkg, boolean src, boolean immediately)
			throws Exception {

		String sql = String.format("SELECT * FROM `%s` LIMIT 1", tablename);
		ResultSet rs = SqlEx.executeQuery(conn, sql);
		ServiceBuilder builder = new ServiceBuilder();
//		String xml = builder.build(conn, rs, pkg + "internal", pkg + "bean",pkg + "dao", pkg + "entity", appcontext, immediately);
		String xml = builder.build(conn, rs, pkg + "service", pkg + "bean",pkg + "dao", pkg + "entity", appcontext, immediately);
		System.out.println(xml);
//		String filename = file(pkg, src, "internal", tablename + "Internal","java");
		String filename = file(is_maven,pkg, src, "service", tablename + "Service","java");
		writeFile(filename, xml);
		conn.close();
	}

	public static void EntityBuild(boolean is_maven,Connection conn, String tablename,
			String appcontext, String pkg, boolean src, boolean immediately)
			throws Exception {
		String filename = file(is_maven,pkg, src, "entity", tablename + "Entity", "java");
		File f = new File(filename);
		if (f.exists())
			return;
		String sql = String.format("SELECT * FROM `%s` LIMIT 1", tablename);
		ResultSet rs = SqlEx.executeQuery(conn, sql);
		EntityBuilder builder = new EntityBuilder();
		String xml = builder.build(conn, rs, pkg + "entity", pkg + "bean", pkg
				+ "dao", pkg + "internal", appcontext, immediately);
		System.out.println(xml);
		// String filename = file(pkg, src, "entity", tablename + "Entity",
		// "java");
		// File f = new File(filename);
		// if (!f.exists())
		writeFile(filename, xml);
		conn.close();
	}

	/**
	 * 文档处理
	 * @param is_maven
	 * @param conn
	 * @param tablename
	 * @param pkg
	 * @param src
	 * #########返回字段说明

|名称|描述|类型|
|--------|----|--------|-------|
| resend_time| 验证码再次发送的时间|字符串|
| verify_list| 验证序列码|字符串|
	 * @throws Exception
	 */
	public static void DocBuild(String moduleName,boolean is_maven,Connection conn, String tablename, String pkg,
			boolean src,Map<String,String> map) throws Exception {

		String sql = String.format("SELECT * FROM `%s` LIMIT 1", tablename);
		ResultSet rs = SqlEx.executeQuery(conn, sql);
		DocBuilder builder = new DocBuilder();
		String xml = builder.build(rs,map);
		System.out.println(xml);
		String filename = file(is_maven,pkg, src, "doc", tablename, "TXT");
		if(moduleName != null && !"".equalsIgnoreCase(moduleName)){
			filename = moduleName+File.separator+filename;
		}
		writeFile(filename, xml);
		conn.close();

	}

	public static String file(boolean is_maven,String pkg, boolean src, String type,
			String tablename, String ext) {
		String path = StrEx.pkg2Path(pkg);

		if(is_maven){
			//是maven项目
			if (src)
				path = "src/main/java/" + path;
			path = path + type + "/"
					+ StrEx.upperFirst(PinYin.getShortPinYin(tablename)) + "."
					+ ext;

		}else{
			if (src)
				path = "src/" + path;
			path = path + type + "/"
					+ StrEx.upperFirst(PinYin.getShortPinYin(tablename)) + "."
					+ ext;
		}


		return path;
	}

	public static void writeFile(String f, String s) throws Exception {
		File file = new File(f);
		if(file != null && !file.exists()){
			file.mkdirs();
			file.delete();
		}
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(s.getBytes());
		fos.close();
	}
}
