package com.park.action.util;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * 
 * Title:用户领取签到等级奖励初始化数据类
 * Description:描述 
 * Copyright: Copyright (c) 2013
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-2-24
 */
public class UserSignInit {
 public static 	Map<Integer,Long> map =  null;
 
 public static Map<Integer, Long> getMap() {
	return map;
}

public static void setMap(Map<Integer, Long> map) {
	UserSignInit.map = map;
}
@PostConstruct
public void init(){
	 map = new Hashtable<Integer, Long>();
	 map.put(2, 500L);
	 map.put(5, 5000L);
	 map.put(10, 5000L);
	 map.put(15, 7500L);
	 map.put(20, 10000L);
	 map.put(25, 12500L);
	 map.put(30, 15000L);
	 map.put(35, 17500L);
	 map.put(40, 20000L);
	 
	 map.put(45, 22500L);
	 map.put(50, 25000L);
	 map.put(60, 30000L);
	 map.put(70, 35000L);
	 map.put(80, 40000L);
	 map.put(90, 45000L);
	 map.put(100, 50000L);
 }
 
	
}
