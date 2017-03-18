
package com.park.v1.biz;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iapppay.demo.IapppayBean;
import com.park.Transaction.BaseTransaction;
import com.park.bean.Intimes_pay;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_info;

/**
 * 管理GAMESDK里面产生的事件和数据
* @ClassName: GameSdkBiz
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年3月10日 下午2:40:33
*
 */
@Service
public class GameSdkBiz extends BaseBiz{
	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	
	
	
	/**
	 * 2.6	用户充值（GAMESDK-JAVA）- P
	* @Title: ReChargeAction_ReCharge
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param returnData
	* @param @param pay_type
	* @param @param ii_id
	* @param @param pay_price
	* @param @param version
	* @param @param terminal_type
	* @param @param item_type
	* @param @param prop_name
	* @param @param appname
	* @param @param imei
	* @param @param apk_str
	* @param @param t
	* @param @param app_id
	* @param @param ssid
	* @param @param ip
	* @param @param m
	* @param @return    设定文件
	* @return ReturnData    返回类型
	* @throws
	*/ 
	public ReturnDataNew ReChargeAction_ReCharge(ReturnDataNew returnData,
			int pay_type, long uid, int pay_price, String version,
			int terminal_type, String prop_name, 
			String imei, long t,  String ssid,
			String ip,String token) throws Exception{
		// TODO Auto-generated method stub
		try {
			String callbackurl = "";//回调地址
	        User_info userinfo = user_infoDao.selectByKey(uid);
	        if(userinfo == null || !userinfo.getUi_token().equalsIgnoreCase(token)){
	        	//用户不存在
	        	returnData.setReturnData(errorcode_param, "用户不存在", "");
				return returnData;
	        }
			//处理返回数据和业务逻辑
/*			JSONObject obj = baseTransaction.getPayTransaction()
					.MakeUserReCharge(uid,userinfo.getUi_tel(),pay_type,pay_price,version,
					 terminal_type,  prop_name,
					 imei,  t,  ssid,
					 ip,callbackurl);
			if(obj == null){
				returnData.setReturnData("1", "-1", "");
				return returnData;
			}
			*/
//			returnData.setReturnData("0", "", obj); 
			return returnData;
		} catch (Exception e) {
			log.error("ReChargeAction_ReCharge is error", e);
			returnData.setReturnData("1", "-1", ""); 
			return returnData;
		}
	}
	
	
	

/**
 * 支付宝通知监听接口（GAMESDK-JAVA）- P
* @Title: PayNotify_ZFBAction_PayNotify_ZFB
* @Description: TODO(这里用一句话描述这个方法的作用)
* @param @param returnData
* @param @param notify_time
* @param @param notify_id
* @param @param sign_type
* @param @param trade_status
* @param @param out_trade_no
* @param @param discount
* @param @param refund_status
* @param @param subject
* @param @param buyer_id
* @param @param buyer_email
* @param @param total_fee
* @param @param ip
* @param @return    设定文件
* @return String    返回类型
* @throws
*/ 
public String PayNotify_ZFBAction_PayNotify_ZFB(String returnData,
		String notify_time, String notify_id, String sign_type,
		String trade_status, String out_trade_no,String trade_no, String discount,
		String refund_status, String subject, String buyer_id,
		String buyer_email, double total_fee, String ip) throws Exception{
	// TODO Auto-generated method stub
	
	//验证该通知的有效期是否合法 time_end=20100511115436
	Date date  = new Date();
	//查询数据库中是否存在该订单 如果存在进行通知财付通已经处理 并去查询该通知ID是否合法
	String sql  = "select * from intimes_pay where ip_order_no=? and ip_price=? limit 1";
	Intimes_pay intimes_pay = getMySelfService().queryUniqueT(sql, Intimes_pay.class, out_trade_no,total_fee);
	if(intimes_pay == null){
		//不存在该条订单
		return "fail";
	}
	//更新支付宝的交易流水号
	intimes_pay.setIp_alipay_no(trade_no);
	//成功
	intimes_pay.setIp_updatetime(date.getTime());
	intimes_pay.setIp_updatetime_str(sf.format(date));
	intimes_pay.setIp_state(1);//0等待付款 1交易成功 2已经退款
	int xx = intimes_payDao.updateByKey(intimes_pay);
	if(xx > 0){
		log.info("update xx=="+xx+" start PayNotify_IApppayAction_PayNotify_IPAY .....doCallBackUrl"); 
		//充值成功
		doCallBackUrl(intimes_pay,1);
		return "success";
	}else{
		//充值失败
		return "fail";
	}
}
	/************************下面是子方法*********************************/

	
	
	






/**
* @Title: PayNotify_ACPAction_PayNotify_ACP
* @Description: TODO(这里用一句话描述这个方法的作用)
* @param @param returnData
* @param @param mapParam
* @param @param ip
* @param @return    设定文件
* @return String    返回类型
* @throws
*/ 
public String PayNotify_ACPAction_PayNotify_ACP(String returnData,
		Map<String, String> valideData, String ip) throws Exception{
	// TODO Auto-generated method stub
	if(valideData == null){
		return "fail";
	}
	String orderId = valideData.get("orderId");
	log.info("orderId===="+orderId); 
	
	return null;
}



/**
 * 爱贝通知监听接口（GAMESDK-JAVA）- P
* @Title: PayNotify_IApppayAction_PayNotify_IPAY
* @Description: TODO(这里用一句话描述这个方法的作用)
* @param @param returnData
* @param @param iapppayBean
* @param @param ip
* @param @return    设定文件
* @return String    返回类型
* @throws
*/ 
public String PayNotify_IApppayAction_PayNotify_IPAY(String returnData,
		IapppayBean iapppayBean, String ip) throws Exception{
	// TODO Auto-generated method stub
	log.info("start PayNotify_IApppayAction_PayNotify_IPAY .....body"); 
	//验证该通知的有效期是否合法 time_end=20100511115436
	Date date  = new Date();
	/*if(sf.parse(iapppayBean.getTranstime()).getTime() < date.getTime() - 10*60*1000){
		//时间过期  超过了10分钟
		return "fail";		
	}*/
	//查询数据库中是否存在该订单 如果存在进行通知财付通已经处理 并去查询该通知ID是否合法
	String sql  = "select * from intimes_pay_sdk where ip_order_no=? and ip_price=? limit 1";
	Intimes_pay intimes_pay = getMySelfService().queryUniqueT(sql, Intimes_pay.class, iapppayBean.getCporderid(),iapppayBean.getMoney());
	if(intimes_pay == null){
		//不存在该条订单
		return "fail";
	}
	if(intimes_pay.getIp_state() == 1){
		//已经充值成功则返回
		return "success";
	}
	//更新爱贝的事务订单号
	intimes_pay.setIp_alipay_no(iapppayBean.getTransid());
	//成功
	intimes_pay.setIp_updatetime(date.getTime());
	intimes_pay.setIp_updatetime_str(sf.format(date));
	intimes_pay.setIp_state(1);//0等待付款 1交易成功 2已经退款
	int xx = intimes_payDao.updateByKey(intimes_pay);
	if(xx > 0){
		log.info("update xx=="+xx+" start PayNotify_IApppayAction_PayNotify_IPAY .....doCallBackUrl"); 
		//充值成功
		doCallBackUrl(intimes_pay,1);
		return "success";
	}else{
		//充值失败
		doCallBackUrl(intimes_pay,0);
		return "fail";
	}
	
}



/*******************这里进行异步处理回调************************/
/**
 * 处理GameSdk回调
 */

/**
 * 
* @Title: doCallBackUrl
* @Description: TODO(这里用一句话描述这个方法的作用)
* @param @param url
* @param @param ii_id
* @param @param price
* @param @param orderid
* @param @param time
* @param @param state (1:success或者0:false)    设定文件
* @return void    返回类型
* @throws
 */
@Async
public void doCallBackUrl(Intimes_pay intimes_pay,int state){
	try {
		//修改用户的虚拟币
		if(intimes_pay == null){
			return;
		}
		User_info userinfo = user_infoDao.selectByKey(intimes_pay.getIi_id());
		if(userinfo == null){
			return;
		}
		userinfo.setUi_rmb(userinfo.getUi_rmb()+(long)intimes_pay.getIp_price());
		userinfo.setUi_vc(userinfo.getUi_vc()+(long)intimes_pay.getIp_price());
		//积分
		userinfo.setUi_score(userinfo.getUi_score()+(long)intimes_pay.getIp_price());
		int count = user_infoDao.updateByKey(userinfo);
		if(count == 1){
			//成功
			log.info("success  orderid="+intimes_pay.getIp_order_no()); 
			//更新通知状态
			intimes_pay.setIp_state(1);
			count = intimes_payDao.updateByKey(intimes_pay);
			if(count == 0){
				log.error("更新通知状态 is fail  orderid="+intimes_pay.getIp_order_no()); 
			}
		}
	} catch (Exception e) {
		log.error("doCallBackUrl(Intimes_pay_sdk intimes_pay,int state) is error", e);
	}

}
 






}
