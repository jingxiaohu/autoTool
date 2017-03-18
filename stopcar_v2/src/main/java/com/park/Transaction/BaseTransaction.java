/**  
* @Title: BaseTransaction.java
* @Package com.intimes.biz
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月20日 下午1:41:50
* @version V1.0  
*/ 
package com.park.Transaction;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.v1.biz.DaoFactory;

/**
 * 管理事务类的调用
 * @ClassName: BaseTransaction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年3月20日 下午1:41:50
 *
 */

@Service
public class BaseTransaction {
	//baseTransaction.getGameSdkTransaction()
	//baseTransaction.getAppSdkTransaction()
	protected Logger log = Logger.getLogger(DaoFactory.class);
	@Autowired
	protected UserTransaction userTransaction;
	@Autowired
	protected PayTransaction payTransaction;
	@Autowired
	protected CarTransaction carTransaction;
	/******************事务工具类***************************/
	/**
	 * @return the userTransaction
	 */
	public UserTransaction getUserTransaction() {
		return userTransaction;
	}
	public PayTransaction getPayTransaction() {
		return payTransaction;
	}
	public CarTransaction getCarTransaction() {
		return carTransaction;
	}
	
	

}
