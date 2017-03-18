package com.park.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.park.v1.biz.common.PayMonthParkPB;
import com.park.v1.biz.common.PayParkPB;

@Component
public class ExpectOrderCheckTask {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected PayParkPB payParkPB;
	@Autowired
	protected PayMonthParkPB payMonthParkPB;
//	@Autowired
//	AsyncTask asyncTask;
	/**
	 * 调度处理预约订单超时的扣款
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void doGo(){
		try{
			log.info("------------ExpectOrderCheckTask----------------is start---");
			//处理预约订单超时的扣款
			payParkPB.upExpectOrderOutTime();
			
		}catch (Throwable e) {
			log.error("ExpectOrderCheckTask.doGo is error", e);
		} 
		
	}
	
	
	/**
	 * 调度处理租赁订单超过了结束时间更新
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void doRent(){
		try{
			log.info("------------RentOrderCheckTask----------------is start---");
			//处理租赁超时的扣款
			payMonthParkPB.upRentOrderOutTime();
			
		}catch (Throwable e) {
			log.error("ExpectOrderCheckTask.doRent is error", e);
		} 
		
	}
}
