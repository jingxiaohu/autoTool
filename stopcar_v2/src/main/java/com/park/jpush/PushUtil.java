package com.park.jpush;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.park.bean.User_info;
import com.park.core.Constants;
import com.park.jpush.bean.JPushMessageBean;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 推送PDA等通知
 * @author jingxiaohu
 *
 */
public class PushUtil {
	private static Logger log = Logger.getLogger(PushUtil.class);
	private static final String appKey ="544d9db9494b2df82c88ca43";//吾泊
	private static final String masterSecret = "ca3dbeb4fdb2b5b2ed7802ed";//wubo
	
	@Test
	public void testMessageSent(){
		/**
		 * 这里进行推送
		 */
		JPushMessageBean jPushMessageBean = new JPushMessageBean();
		jPushMessageBean.setType(0);
		jPushMessageBean.setMessage("您充值"+1+"吾泊币已经到账，可以在电子钱包明细处查询。");
		jPushMessageBean.setImgurl(Constants.JPUSH_LOGO);
		jPushMessageBean.setTitle("充值到账");
		jPushMessageBean.setDate(new Date());
		String message = JSON.toJSONString(jPushMessageBean);
		String alert = "alert";
		SendPush(message, alert,"2016121217048983");
	}
	
	
	public static  void SendPush(String message,String alert,String... uuid) { 
		ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        
        PushPayload payload = buildPushObject_android_and_ios(message,alert,uuid);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);
            log.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
        	e.printStackTrace();
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
        }catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Deprecated
    public static  PushPayload buildPushObject_android_and_ios(String MSG_CONTENT,String ALERT,String... uuid) {
    	return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(uuid))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    /**
     * 推送消息
     * @param MSG_CONTENT
     * @return
     */
    public static  PushPayload buildPushMessage(String MSG_CONTENT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder().setApnsProduction(true).build())
                 .build();
    }
    /**
     * 推送通知
     * @param MSG_CONTENT
     * @return
     */
    public static  PushPayload buildPushAlert(String ALERT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                                 .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(null)       
                 .setOptions(Options.newBuilder().setApnsProduction(true).build())
                 .build();
    }
	
}
