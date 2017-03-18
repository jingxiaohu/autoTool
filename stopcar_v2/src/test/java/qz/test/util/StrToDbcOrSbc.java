package qz.test.util;

import com.alibaba.fastjson.JSONObject;


public class StrToDbcOrSbc {

	/**
	 * 转换原理
	 * 
	 * 全角字符unicode编码从65281~65374
	 * 
	 * 半角字符unicode编码从33~126
	 * 
	 * 空格比较特殊,全角为 12288,半角为 32
	 * 
	 * 而且除空格外,全角/半角按unicode编码排序在顺序上是对应的
	 * 
	 * 所以可以直接通过用+-法来处理非空格数据,对空格单独处理
	 */

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}

	// 半角转全角：
	public static String ToSBC2(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	// 转半角的函数(DBC case)
	public static String ToDBC2(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static void main(String[] args) {
		/*String QJstr = "'";
		String result = ToSBC2(QJstr);
		System.out.println(result);
		System.out.println(result.getBytes().length);*/
		/*int y = '\177';
		for (int i = 65281,j=33; i <= 65374&&j<= y; i++,j++) {
				System.out.println((char)i+"     "+i+"   |   "+(char)j+"    "+j);
		}*/
		
		//{"country":"中国", "province":"四川省", "city":"成都市", "address":"四川省成都市双流县德赛四街"}
		JSONObject po = new JSONObject();
		po.put("country", "中国");
		po.put("province", "四川省");
		po.put("city", "成都市");
		po.put("address", ToSBC2("四川省成都市双流县\"德赛四街"));
		
		System.out.println(po.toString());
	}

}
