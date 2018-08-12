package com.highbeauty.sql.spring.builder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexBuilder {
	public static Map<String, List<String>> getIndex(Connection conn, ResultSetMetaData rsmd) throws SQLException{
		//List<List<String>> ret = new ArrayList<List<String>>();
		
		Map<String, List<String>> m = new HashMap<String, List<String>>();
		DatabaseMetaData dmd = conn.getMetaData();
		String tableName = rsmd.getTableName(1);
		ResultSet rs = dmd.getIndexInfo(null, null, tableName, false, true);
		while (rs.next()) {
			String indexName = rs.getString("INDEX_NAME");
			String columnName = rs.getString("COLUMN_NAME");
			
			List<String> l = m.get(indexName);
			if(l == null){
				l = new ArrayList<String>();
				l.add(columnName);
				m.put(indexName, l);
			}else{
				l.add(columnName);
			}
		}

//		Iterator<List<String>> it = m.values().iterator();
//		while (it.hasNext()) {
//			ret.add(it.next());			
//		}
		return m;
	}

	/**
	 * 获取主键名字
	 * @param connection
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public String gainPrimaryKey(Connection connection,String tableName){
		Object primaryKeyName = null;
		try {
			DatabaseMetaData dbMeta = connection.getMetaData();
			ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, tableName);
/*        System.err.println("****** Comment ******");
        System.err.println("TABLE_CAT : "+pkRSet.getObject(1));
        System.err.println("TABLE_SCHEM: "+pkRSet.getObject(2));
        System.err.println("TABLE_NAME : "+pkRSet.getObject(3));
        System.err.println("COLUMN_NAME: "+pkRSet.getObject(4));
        System.err.println("KEY_SEQ : "+pkRSet.getObject(5));
        System.err.println("PK_NAME : "+pkRSet.getObject(6));
        System.err.println("****** ******* ******");*/
			while( pkRSet.next() ) {
				primaryKeyName =   pkRSet.getObject(4);
			}
		} catch (Exception e) {
//			log.error("获取主键名字失败",e);
		}
		return  primaryKeyName == null ? null:primaryKeyName.toString();
	}
}
