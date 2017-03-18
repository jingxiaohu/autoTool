package com.park.action.util;

import java.util.Random;

/**
 * 
 * Title:玩游戏工具类
 * Description:描述 
 * Copyright: Copyright (c) 2013
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-2-26
 */
public class GameUtil {
	/**
	 * 调用不同的游戏获取不同的数据
	 * @param type
	 * @return
	 */
	public static long returnGame(int type){
		if(type == 0){
			//幸运大转盘
			 return LuckyTurning();
		}else if(type == 1){
			//幸运摇一摇
			return LuckyShark();
		}else if(type == 2){
			//幸运刮刮乐
			return LuckyScratch();
		}
		return 0;
	}
	
	/**
	 * 幸运大转盘
	 */
	public static long LuckyTurning(){
		Random random = new Random();
		int count = random.nextInt(100);
		if(count < 98){
			return 1000L;
		}else if(count == 98){
				return 3000L;
		}else if(count == 99){
				return 5000L;
		}else{
				return 1000L;
		}
	}
	
	/**
	 * 幸运摇一摇
	 */
	public  static long LuckyShark(){
		Random random = new Random();
		int count = random.nextInt(100);
		if(count < 98){
			return 1000L;
		}else if(count == 98){
				return 3000L;
		}else if(count == 99){
				return 5000L;
		}else{
				return 1000L;
		}
	}
	
	/**
	 * 幸运刮刮乐
	 */
	public static long LuckyScratch(){
		Random random = new Random();
		int count = random.nextInt(100);
		if(count < 30){
			return 3000L;
		}else if(count > 30){
				return 1000L;
		}else if(count == 30){
				return 5000L;
		}else{
				return 1000L;
		}
	}
	
	/*******************************中奖概率方法***********************************/
	/**
	 * 判断 某数 是否在某区间
	 * @param start 
	 * @param end
	 * @param srcdata
	 * @return
	 */
	public static  boolean isBetween(double start,double end , double srcdata){
		if(srcdata >= start && srcdata < end){
			return true;
		}
		return false;
	}
}
