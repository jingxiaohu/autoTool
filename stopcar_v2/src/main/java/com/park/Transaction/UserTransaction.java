/**  
* @Title: TT.java
* @Package com.intimes.biz
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月20日 下午1:32:43
* @version V1.0  
*/ 
package com.park.Transaction;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_info;
import com.park.bean.User_park_coupon;
import com.park.bean.User_vc_act;
import com.park.exception.QzException;
import com.park.v1.biz.BaseBiz;
import com.park.v1.biz.common.ParkCouponPB;
import com.park.v1.biz.common.ParkInfoPB;
import com.park.v1.biz.common.UserPB;

/**
 * 
* @ClassName: AppSdkTransaction
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年3月20日 下午2:11:51
*
 */
@Transactional(rollbackFor=QzException.class)
@Service
public class UserTransaction extends  BaseBiz{
	@Autowired
	protected ParkCouponPB parkCouponPB;
	@Autowired
	private ParkInfoPB parkInfoPB;
	@Autowired
	private UserPB userPB;
	
	/**
	 * 记录用户虚拟币的 变更详情
	 * @param ui_id
	 * @param money
	 * @param order_id
	 * @param order_type 订单类型 0:普通订单 1：租赁订单
	 * @param act_type
	 * @param state
	 * @param type : 0 ：自动扣款   1：第三方支付回调
	 */
	public void record_user_vc_act(ReturnDataNew returnData ,long ui_id,int money,String order_id,int order_type,int act_type,int type,User_park_coupon   user_park_coupon) throws QzException{
		// TODO Auto-generated method stub
        try {
        	Date date = new Date();
        	//首先判断用户是否存在
    		User_info user_info  =  user_infoDao.selectByKey(ui_id);
        	if(user_info == null ){
        		//更新失败
        		returnData.setReturnData(errorcode_data, "用户不存在", "");
				throw new QzException("record_user_vc_act 用户不存在");
        	}
        	//订单类型 0:普通订单 1：租赁订单
        	if(order_type == 0){
        		//普通订单
        		String sql = "select * from pay_park where my_order=? limit 1";
        		Pay_park pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, order_id);
        		if(pay_park == null){
            		//更新失败
        			returnData.setReturnData(errorcode_data, "订单不存在", "");
    				throw new QzException("record_user_vc_act 订单不存在");
        		}
        		//进行用户虚拟币和订单的缴费状态
        		//这里查看用户是自动扣费还是手动扣费
        		//如果是选择自己钱包扣款，更改用户账户的 虚拟币
    			if(type == 0){//钱包扣款---开始
    				//钱包扣款
    				//虚拟币比例1元比100 分 
    				if(user_info.getUi_vc()-money > 0){
    					//虚拟币足够 且 是自动扣费
    					user_info.setUi_vc(user_info.getUi_vc()-money); //分
            			int count = user_infoDao.updateByKey(user_info);
            			if(count < 1){
            				//更新用户扣款数据失败
            				//这里需要抛出异常
            				 //返回结果
            				//更新失败
            				returnData.setReturnData(errorcode_data, "停车缴费失败", "");
            				throw new QzException("record_user_vc_act 停车缴费失败");
            			}else{
            				//扣款成功
            				pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付
            				pay_park.setMoney(money);//设置钱
            				pay_park.setUtime(date);//更新时间
            				pay_park.setLeave_time(date);//离开时间
            				count = pay_parkDao.updateByKey(pay_park);
            				if(count < 1){
            					//更新失败
            					//该车辆未出入
                				//更新失败
            					returnData.setReturnData(errorcode_data, "停车缴费失败", "");
                				throw new QzException("record_user_vc_act 停车缴费失败");
            				}
            				
            				/**
            				 * 更新商户账户金额
            				 */
            				userPB.upManchentMoney(pay_park.getPu_id(),pay_park.getPu_nd(),pay_park.getMoney(), 1);
            			}
    				}
    			}
        		
        	}else{
        		//租赁订单
        		String sql = "select * from pay_month_park where my_order=? limit 1";
        		Pay_month_park pay_month_park = getMySelfService().queryUniqueT(sql, Pay_month_park.class, order_id);
        		if(pay_month_park == null){
    				//更新失败
        			returnData.setReturnData(errorcode_data, "订单不存在", "");
    				throw new QzException("record_user_vc_act 订单不存在");
        		}
        		//进行用户虚拟币和订单的缴费状态
        		//这里查看用户是自动扣费还是手动扣费
        		//如果是选择自己钱包扣款，更改用户账户的 虚拟币
        		if(type == 0){//钱包扣款---开始
    				//钱包扣款
    				//虚拟币比例1元比100 分 
    				if(user_info.getUi_vc()-money > 0){
    					//虚拟币足够 且 是自动扣费
    					user_info.setUi_vc(user_info.getUi_vc()-money); //分
            			int count = user_infoDao.updateByKey(user_info);
            			if(count < 1){
            				//更新用户扣款数据失败
            				//这里需要抛出异常
            				 //返回结果
            				//更新失败
            				returnData.setReturnData(errorcode_data, "停车包月缴费失败", "");
            				throw new QzException("record_user_vc_act 停车包月缴费失败");
            			}else{
            				//扣款成功
            				pay_month_park.setPp_state(1);//支付状态 0:未支付 1：已经支付
            				count = pay_month_parkDao.updateByKey(pay_month_park);
            				if(count < 1){
            					//更新失败
            					//该车辆未出入
                				//更新失败
            					returnData.setReturnData(errorcode_data, "停车包月缴费失败", "");
                				throw new QzException("record_user_vc_act 停车包月缴费失败");
            				}
            				/**
            				 * 更新商户账户金额
            				 */
            				userPB.upManchentMoney(pay_month_park.getPu_id(),pay_month_park.getPu_nd(),pay_month_park.getMoney(), 1);
            			}
    				}
    			}
        	}
        	
        	if(user_park_coupon != null){
            	//如果使用了优惠券 那么需要更改优惠券的使用状态
            	if(!parkCouponPB.upUserParkCouponState(user_park_coupon.getUpc_id())){
    				//更新失败
					returnData.setReturnData(errorcode_data, "优惠券更新使用状态失败", "");
    				throw new QzException("record_user_vc_act 优惠券更新使用状态失败");
            	}
        	}
			
        	
        	//记录该次用户虚拟币更改记录
        	recordVC( act_type, money, order_id, order_type, ui_id, returnData);
		} catch (Exception e) {
			log.error("VcBiz record_user_vc_act is error", e);
			throw new QzException("事物异常 record_user_vc_act",e);
		}
	}
	
	
	
	public void recordVC(int act_type,int money,String order_id,int order_type,long ui_id,ReturnDataNew returnData) throws QzException{
    	try {
			//记录该次用户虚拟币更改记录
			User_vc_act  va = new User_vc_act();
			Date date = new Date();
			va.setAct_type(act_type);//用户行为  0：订单支付  1：暂定
			va.setCtime(date);
			va.setMoney(money);//交易金额（单位 分）
			va.setOrder_id(order_id);//订单ID
			va.setOrder_type(order_type);//订单类型 0:普通订单 1：租赁订单
			va.setUi_id(ui_id);
			va.setState(1);//处理状态 0：未处理 1：已处理
			int count = user_vc_actDao.insert(va);
			if(count < 1){
				//更新失败
				returnData.setReturnData(errorcode_data, "缴费失败", "");
				throw new QzException("record_user_vc_act 缴费失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnData.setReturnData(errorcode_data, "缴费失败", "");
			throw new QzException("record_user_vc_act 缴费失败",e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	
	/*******************************下面是分出的方法*************************************/


}
