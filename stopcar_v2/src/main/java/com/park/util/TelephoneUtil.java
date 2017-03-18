package com.park.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneUtil {

/**
 * 判断是否是手机号码
 * @param mobiles
 * @return
 */
public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
}  

public static boolean  checkIsUnion(String mobiles){
	Pattern p = Pattern.compile("^((13[0,1,2])|(15[5,6])|(18[6]))\\d{8}$");
	Matcher m = p.matcher(mobiles);
	System.out.println("是否是联通号" +m.matches() ); 
	return m.matches();
}

}
