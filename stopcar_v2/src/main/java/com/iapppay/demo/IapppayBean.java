/**  
* @Title: iapppayBean.java
* @Package com.iapppay.demo
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月23日 下午1:13:00
* @version V1.0  
*/ 
package com.iapppay.demo;

import java.io.Serializable;

/**
 * @ClassName: iapppayBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年3月23日 下午1:13:00
 *
 */
public class IapppayBean implements Serializable{

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 8533532857330388080L;
	public int transtype;//交易类型： 0–支付交易； 1–支付冲正（暂未启用）； 2–契约退订 3–自动续费  必填
	public String cporderid;//商户订单号 必填
	public String transid;//交易流水号  必填
	public String appuserid;//用户在商户应用的唯一标识 必填
	public String appid;//平台为商户应用分配的唯一代码 必填
	public int waresid;//平台为应用内需计费商品分配的编码 必填
	/**
	 * feetype表示商品采用的计费方式，目前定义如下：
		0–消费型_应用传入价格
		2 –消费型_平台设置价格
		3 –订阅型_按时长
		4 –订阅型_自动续费
		5 –非消费型
		6 –订阅型_按次数
		因为业务拓展，计费方式定义可能会扩展，建议商户不要做严格校验
	 */
	public int feetype;//计费方式，具体定义见附录  必填 
	public double money;//本次交易的金额  必填
	public String currency;//货币类型以及单位： RMB – 人民币（单位：元）  必填
	public int result;//交易结果： 0–交易成功 1–交易失败  必填
	public String transtime;//交易时间格式： yyyy-mm-dd hh24:mi:ss  必填
	public String cpprivate;//商户私有信息  可选 
	/**
	 * paytype表示用户采用的支付方式，目前定义如下：
		1   - 充值卡
		2   - 游戏点卡
		4   - 银行卡
		5   - 爱贝币
		6   - 爱贝快付
		16  - 百度钱包
		30  - 移动话费
		31  - 联通话费
		32  - 电信话费
		401 - 支付宝
		402 - 财付通
		因为业务拓展，支付方式定义可能会扩展，建议商户不要做严格校验

	 */
	public int paytype;//支付方式，具体定义见附录  可选



	/****************************下面是get set********************************/
	
	/**
	 * @return the transtype
	 */
	public int getTranstype() {
		return transtype;
	}
	



	/**
	 * @param transtype the transtype to set
	 */
	public void setTranstype(int transtype) {
		this.transtype = transtype;
	}
	/**
	 * @return the cporderid
	 */
	public String getCporderid() {
		return cporderid;
	}
	/**
	 * @param cporderid the cporderid to set
	 */
	public void setCporderid(String cporderid) {
		this.cporderid = cporderid;
	}
	/**
	 * @return the transid
	 */
	public String getTransid() {
		return transid;
	}
	/**
	 * @param transid the transid to set
	 */
	public void setTransid(String transid) {
		this.transid = transid;
	}
	/**
	 * @return the appuserid
	 */
	public String getAppuserid() {
		return appuserid;
	}
	/**
	 * @param appuserid the appuserid to set
	 */
	public void setAppuserid(String appuserid) {
		this.appuserid = appuserid;
	}
	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * @return the waresid
	 */
	public int getWaresid() {
		return waresid;
	}
	/**
	 * @param waresid the waresid to set
	 */
	public void setWaresid(int waresid) {
		this.waresid = waresid;
	}
	/**
	 * @return the feetype
	 */
	public int getFeetype() {
		return feetype;
	}
	/**
	 * @param feetype the feetype to set
	 */
	public void setFeetype(int feetype) {
		this.feetype = feetype;
	}
	
	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}




	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}




	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the transtime
	 */
	public String getTranstime() {
		return transtime;
	}
	/**
	 * @param transtime the transtime to set
	 */
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	/**
	 * @return the cpprivate
	 */
	public String getCpprivate() {
		return cpprivate;
	}
	/**
	 * @param cpprivate the cpprivate to set
	 */
	public void setCpprivate(String cpprivate) {
		this.cpprivate = cpprivate;
	}
	/**
	 * @return the paytype
	 */
	public int getPaytype() {
		return paytype;
	}
	/**
	 * @param paytype the paytype to set
	 */
	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

 
	
}
