package com.park.v1.biz;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.park.Transaction.BaseTransaction;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_info;
import com.park.bean.User_pay;
import com.park.v1.biz.common.PayMonthParkPB;
import com.park.v1.biz.common.PayParkPB;

@Service
public class UserPayBiz extends BaseBiz {
	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	@Autowired
	private PayParkPB payParkPB;
	@Autowired
	private PayMonthParkPB payMonthParkPB;
	/**
	 * 建设银行龙支付WEB
	 * @param returnData
	 * @param pay_type
	 * @param ui_id
	 * @param pay_price_fen
	 * @param version
	 * @param system_type
	 * @param subject
	 * @param ip
	 * @param token
	 * @return
	 */
	public void lzf_charge(ReturnDataNew returnData, int pay_type,
			long ui_id, long money, int version, int system_type,
			String subject, String ip, String token,String pub,int type,String orderid) {
		// TODO Auto-generated method stub
		try {
			String callbackurl = "";//回调地址
	        User_info userinfo = user_infoDao.selectByKey(ui_id);
	        if(userinfo == null){
	        	//用户不存在
	        	returnData.setReturnData(errorcode_param, "用户不存在", "");
				return ;
	        }
	        if(!userinfo.getUi_token().equalsIgnoreCase(token)){
	        	//用户不合法
	        	returnData.setReturnData(errorcode_param, "用户未授权", "");
				return ;
	        }
			//处理返回数据和业务逻辑
	        User_pay user_pay = baseTransaction.getPayTransaction()
					.MakeUserReCharge(ui_id,userinfo.getUuid(),
							pay_type,money,version,
							system_type,subject,ip,callbackurl,type,orderid);
			if(user_pay == null){
				returnData.setReturnData(errorcode_data, "下单失败", "");
				return ;
			}
			JSONObject obj = (JSONObject)JSONObject.toJSON(user_pay);
			if(obj != null){
				obj.put("pub", pub);
			}
			returnData.setReturnData("0", "下单成功", obj); 
			return ;
		} catch (Exception e) {
			log.error("UserPayBiz.lzf_charge is error", e);
			returnData.setReturnData("1", "-1", ""); 
			return ;
		}
	}
	
	
	/**
	 *建设银行龙支付通知   验证成功  更新该订单的支付状态 并把对应的金额添加给用户
	 * @param returnData
	 * @param orderid
	 */
	public void notify_lzf(ReturnDataNew returnData, String orderid,String type,long money) {
		// TODO Auto-generated method stub
		try {
			// type 是支付 还是 充值  1：充值  2：普通订单支付  3：租赁订单支付
			//首先检查该条订单是否存在
			User_pay user_pay = payParkPB.selectOneUserPay(orderid);
			if(user_pay == null){
				//订单不存在
				returnData.setReturnData(errorcode_param, "订单不存在", "");
				return;
			}
			//避免第三方多次回调
			if(user_pay.getState() == 1){
				returnData.setReturnData(errorcode_success, "通知更新成功", user_pay); 
				return;
			}
			//验证是否金额一致 如果不一致有可能是被抓包  恶意刷我们的钱包
			if(user_pay.getMoney() != money){
				returnData.setReturnData(errorcode_param, "金额不匹配", "");
				return;
			}
			
			try {
				//建行龙支付用户充值通知：修改用户钱包金额、更改订单状态
				user_pay = baseTransaction.getPayTransaction().NotifyUserPay(user_pay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("通知更新失败 baseTransaction.getPayTransaction().NotifyUserPay(user_pay)", e); 
			}
			returnData.setReturnData("0", "通知更新成功", user_pay); 
			return;
		} catch (Exception e) {
			log.error("UserPayBiz.lzf_charge is error", e);
			returnData.setReturnData(errorcode_data, "通知更新失败", ""); 
			return;
		}
	}
	
	
	/**
	 * 支付宝充值下单
	 * @param returnData
	 * @param pay_type
	 * @param ui_id
	 * @param money
	 * @param version
	 * @param system_type
	 * @param subject
	 * @param ip
	 * @param token
	 */
	public void zfb_charge(ReturnDataNew returnData, int pay_type,
			long ui_id, long money, int version, int system_type,
			String subject, String ip, String token,int type,String orderid) {
		// TODO Auto-generated method stub
		try {
			String callbackurl = "";//回调地址
	        User_info userinfo = user_infoDao.selectByKey(ui_id);
	        if(userinfo == null){
	        	//用户不存在
	        	returnData.setReturnData(errorcode_param, "用户不存在", "");
				return ;
	        }
	        if(!userinfo.getUi_token().equalsIgnoreCase(token)){
	        	//用户不合法
	        	returnData.setReturnData(errorcode_param, "用户未授权", "");
				return ;
	        }
			//处理返回数据和业务逻辑
	        User_pay user_pay = baseTransaction.getPayTransaction()
					.MakeUserReCharge(ui_id,userinfo.getUuid(),
							pay_type,money,version,
							system_type,subject,ip,callbackurl,type,orderid);
			if(user_pay == null){
				returnData.setReturnData(errorcode_data, "下单失败", "");
				return ;
			}
			returnData.setReturnData("0", "下单成功", user_pay); 
			return ;
		} catch (Exception e) {
			log.error("UserPayBiz.zfb_charge is error", e);
			returnData.setReturnData(errorcode_data, "下单失败", ""); 
			return ;
		}
	}
	
	
	/**
	 *支付宝通知   验证成功  更新该订单的支付状态 并把对应的金额添加给用户
	 * @param returnData
	 * @param orderid
	 */
	public void notify_zfb(ReturnDataNew returnData, String orderid,String trade_no,String type,long money) { 
		// TODO Auto-generated method stub
		try {
			//是支付 还是 充值  1：充值  2：普通订单支付  3：租赁订单支付
				//首先检查该条订单是否存在
				User_pay user_pay = payParkPB.selectOneUserPay(orderid);
				if(user_pay == null){
					//订单不存在
					returnData.setReturnData(errorcode_param, "订单不存在", "");
					return;
				}
				//避免第三方多次回调
				if(user_pay.getState() == 1){
					returnData.setReturnData(errorcode_success, "通知更新成功", user_pay); 
					return;
				}
				
				//支付宝用户充值通知：修改用户钱包金额、更改订单状态
				user_pay.setTransaction_id(trade_no);
				//验证是否金额一致 如果不一致有可能是被抓包  恶意刷我们的钱包
				if(user_pay.getMoney() != money){
					returnData.setReturnData(errorcode_param, "金额不匹配", "");
					return;
				}
				try {
					user_pay = baseTransaction.getPayTransaction().NotifyUserPay(user_pay);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("通知更新失败 baseTransaction.getPayTransaction().NotifyUserPay(user_pay)", e); 
				}
				
				returnData.setReturnData(errorcode_success, "通知更新成功", user_pay); 
				return;
			
		} catch (Exception e) {
			log.error("UserPayBiz.notify_zfb is error", e);
			returnData.setReturnData(errorcode_data, "通知更新失败", ""); 
			return;
		}
	}
}
