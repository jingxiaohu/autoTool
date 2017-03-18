package com.park.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.park.jpush.PushUtil;
import com.park.jpush.PushUtilPDA;
import com.park.jpush.bean.JPushMessageBean;
@Service
public class AsyncJpushTask {
	static Logger log = Logger.getLogger(AsyncJpushTask.class);
	/**
	 * 进行PDA推送
	 * @param list
	 * @param pagesize
	 */
	@Async
	public   void doPdaJpush(JPushMessageBean jPushMessageBean,String uuid){
		try {
			if(jPushMessageBean != null){
				PushUtil.SendPush(JSON.toJSONString(jPushMessageBean), jPushMessageBean.getMessage(),uuid);
			}
			System.out.println("@@"+jPushMessageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("doPdaJpush(JPushMessageBean jPushMessageBean,String uuid) is error uuid="+uuid, e); 
		}
		
	}
	
	/**
	 * 进行PDA推送
	 * @param list
	 * @param pagesize
	 */
	@Async
	public   void doPdaJpushPDA(JPushMessageBean jPushMessageBean){
		try {
			if(jPushMessageBean != null){
				PushUtilPDA.SendPush(jPushMessageBean.getMessageJson().toJSONString(), jPushMessageBean.getMessage());
			}
			System.out.println("@@"+jPushMessageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("点播执行doInsertIntoDataBase(List<By_app_categories> list,int pagesize) is error", e); 
		}
		
	}
}
