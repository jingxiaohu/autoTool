package com.park.action.util;

/**
 * 
 * Title:返奖DEMOL
 * Description:描述 
 * Copyright: Copyright (c) 2013
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-2-26
 */
public class ReturnAwareBean {
	private int multiple;// 倍数
	private double probability;// 概率
	private long condition;// 条件
	/**
	 * 初始化bean
	 * @param multipe
	 * @param probability
	 * @param condition
	 * @return
	 */
	public  static ReturnAwareBean   InitReturnAwareBean(int multipe,double probability,long condition){
		ReturnAwareBean  bean = new ReturnAwareBean();
		bean.setMultiple(multipe);
		bean.setProbability(probability);
		bean.setCondition(condition);
		return bean;
	}
	
	@Override
	public String toString() {
		return "倍数multiple:"+multiple+" 中奖概率probability:"+probability+" 中奖条件condition:"+condition;
	}
	
	
	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public long getCondition() {
		return condition;
	}

	public void setCondition(long condition) {
		this.condition = condition;
	}
}
