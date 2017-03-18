/**  
* @Title: TT.java
* @Package com.intimes.biz
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月20日 下午1:32:43
* @version V1.0  
*/ 
package com.park.Transaction;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.bean.Car_in_out;
import com.park.bean.Park_info;
import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.Rental_charging_rule;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_info;
import com.park.core.Constants;
import com.park.exception.QzException;
import com.park.jpush.bean.JPushMessageBean;
import com.park.util.RequestUtil;
import com.park.v1.biz.BaseBiz;
import com.park.v1.biz.common.ParkInfoPB;
import com.park.v1.biz.common.PayParkPB;
import com.park.v1.biz.common.UserPB;

/**
 * 车辆相关事务类
 * @author jingxiaohu
 *
 */
@Transactional(rollbackFor=QzException.class)
@Service
public class CarTransaction extends  BaseBiz{
	@Autowired
	private PayParkPB payParkPB;
	@Autowired
	private UserPB userPB;
	@Autowired
	private ParkInfoPB parkInfoPB;
	/**
	 * 车辆出入的时候 计费 状态设定 等细则进行事物处理
	 */
	public void doCar_In_Out(Car_in_out car_in_out,Park_info park_info ,ReturnDataNew returnData) throws QzException{
		
		try{
			String area_code = park_info.getArea_code();
			int is_enter = car_in_out.getIs_enter();
			Date date = car_in_out.getCtime();
			long ui_id = car_in_out.getUi_id();
			long pi_id = car_in_out.getPi_id();
			String car_code = car_in_out.getCar_code();
			//入库或者出库 ：0：   入库   1：出库
			if(is_enter == 0){
	        	//入库之前先检查该用户对该车辆进行该停车场预约下单没有   如果已经进行了预约下单则直接更新对应得数据
	        	if(ui_id > 0){
	        		//有用户注册了  检查是否已经进行了预约下单
	        		Pay_park pay_park   = 	payParkPB.QueryPayPark( ui_id,pi_id, car_code, 1,area_code);
		        	if(pay_park == null){
		        		//没有预约下单  摄像头扫描到后进行普通下单
		        		payParkPB.cameraCarOrder(car_in_out,park_info);
		        	}else{
		        		//预约下单  判断当前为止   当前时间减去预约时间 是否已经超过了60分钟  如果超过则预约扣款  如果没有超过则不进行扣款
		        		if(date.getTime() - pay_park.getCtime().getTime() <= pay_park.getExpect_time()*60*1000){
		        			//获取该停车场的计费规则 rcr_type ：停车类型 0：普通车位停车 1：时间段包月停车
		        			Rental_charging_rule  charging_rule =  payParkPB.queryChargeRule( car_in_out.getPi_id(),0,car_in_out.getCar_type(),area_code,car_in_out.getCar_code_color());
		        			if(charging_rule == null){
		        				//下单失败
		        				return ;
		        			}
		        			//预约到场时间在规定时间内 则不扣预约费 
			        		pay_park.setArrive_time(date);//预约到场时间
			    			pay_park.setStart_price(charging_rule.getStart_price());//起步价格（单位 分）
			    			pay_park.setStart_time(charging_rule.getStart_time());//起步时长（分钟）
			    			pay_park.setCharging(charging_rule.getCharging());//计费价格(单位 分)
			    			pay_park.setCharging_time(charging_rule.getCharging_time());//计费时长(分钟)
			    			
			    			pay_park.setFree_minute(charging_rule.getFree_minute());//多少分钟之内进出免费
			    			pay_park.setIs_free_minute(charging_rule.getIs_free_minute());//多少分钟之内进出免费是否开启  0:不开启  1：开启
			    			
			             	//这里更新预约车辆出入库的数量变化
			            	parkInfoPB.upCarNum(park_info, is_enter,3);
		        		}else{
		        			if(pay_park.getPp_state() == 0){
		        				//是否已经扣除预约超时钱 0：未扣款 1：已经扣款成功
		        				
			        			//预约超时到场 先扣预约费用
			        			pay_park.setIs_expect_outtime(1);//是否预约已经超时 0：未超时 1：已经超时
			        			
			        			//变更用户钱包金额
			        			User_info userinfo  = userPB.updateUserMoney(ui_id, "", 1, pay_park.getExpect_money());
			        			
			        			if(userinfo == null){
			        				//更新用户扣款数据失败
			        				//这里需要抛出异常
			        				throw new QzException("预约超时到场 先扣预约费用 失败");
			        			}else{
			        				//扣款成功
			        				pay_park.setPp_state(1);
			        				pay_park.setIs_expect_deduct(1);//是否已经扣除预约超时钱 0：未扣款 1：已经扣款成功
			        				//扣款记录到用户金额明细中去  *** 后续是否进行异常捕获
			        				userPB.recordVC( 0, pay_park.getExpect_money(), pay_park.getMy_order(), pay_park.getOrder_type(), ui_id, returnData);
			        				/**
			        				 * 更新商户账户金额
			        				 */
			        				userPB.upManchentMoney(pay_park.getPu_id(),pay_park.getPu_nd(),pay_park.getExpect_money(), 1);
			        				try {
			        					//4.	预约扣款完成：由于您未在规定时间内到达停车场，扣除XX元电子劵。
			        					/**
			        					 * 这里进行推送
			        					 */
			        					JPushMessageBean jPushMessageBean = new JPushMessageBean();
			        					jPushMessageBean.setType(0);
			        					jPushMessageBean.setMessage("由于您未在规定时间内到达停车场，扣除"+pay_park.getExpect_money()+"吾泊币。");
			        					jPushMessageBean.setImgurl(Constants.JPUSH_LOGO);
			        					jPushMessageBean.setTitle("预约超时扣款");
			        					jPushMessageBean.setDate(date);
			        					asyncJpushTask.doPdaJpush(jPushMessageBean,userinfo.getUuid());
			        				} catch (Exception e) {
			        					// TODO Auto-generated catch block
			        					log.error("预约超时扣款 推送出现错误", e);
			        				}
			        			}
		        			}
		        			
			        		//预约超时且扣款后  进行入库  新生成一个临停订单
			        		payParkPB.cameraCarOrder(car_in_out,park_info);
		        		}
		        		
		    			//设置车辆进入时候的 订单 
		        		if(RequestUtil.checkObjectBlank(car_in_out.getOrder_id())){
		        			car_in_out.setOrder_id(pay_park.getMy_order());
		        		}
		    			
		    			
			        	int count = pay_parkDao.updateByKey(pay_park);
						if(count < 1){
							//扣款失败
							throw new QzException("扣款失败");
						}
		        	}
		        	

	        		
	        	}else{
	        		//该用户还没有注册   也要记录该车辆的下单情况
	        		/**
		        	 * 摄像头扫描到下单
		        	 */
	        		payParkPB.cameraCarOrder(car_in_out,park_info);
	        	}

 
			}else{
				//出库 
				//获取用户还没有支付的最后一条某停车场的订单
				Pay_park pay_park = payParkPB.QueryPayPark( ui_id, pi_id, car_code,area_code);
				if(pay_park != null){
					pay_park.setLeave_time(new Date());
					pay_park.setIs_over(1);//订单是否完成(0:没有完成1：完成)
					//更新离开时间
					int count = pay_parkDao.updateByKey(pay_park);
        			if(count < 1){
        				//更新离开时间失败
        				throw new QzException("更新离开时间失败");
        			}
				}
			}
		}catch(Exception e){
			throw new QzException("事物异常 doCar_In_Out",e);
		}
	}
	
	
	/**
	 * 处理调度轮询  预约超时的订单进行扣费处理
	 * @param pay_park
	 * @throws QzException
	 */
	public void ExpectOrderCheckTask(Pay_park pay_park) throws QzException{
		// TODO Auto-generated method stub
        try {
			//获取用户信息
			//虚拟币比例1元比100 分 
			User_info userinfo = user_infoDao.selectByKey(pay_park.getUi_id());
			if(userinfo == null){
				return;
			}
			if(userinfo.getUi_vc()-pay_park.getExpect_money() > 0){
				//虚拟币足够 且 是自动扣费
				userinfo.setUi_vc(userinfo.getUi_vc()-pay_park.getExpect_money()); //分
    			int count = user_infoDao.updateByKey(userinfo);
    			if(count < 1){
    				//更新用户扣款数据失败
    				//这里需要抛出异常
    				//扣款失败
    				throw new QzException("扣款失败");
    			}else{
    				//扣款成功
    				pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付 
    				pay_park.setIs_expect_outtime(1);//是否预约已经超时 0：未超时 1：已经超时
    				pay_park.setIs_expect_deduct(1);//是否已经扣除预约超时钱 0：未扣款 1：已经扣款成功
    				pay_park.setMoney(pay_park.getExpect_money());//单位 分
    				count = pay_parkDao.updateByKey(pay_park);
    				if(count < 1){
    					//扣款失败
    					throw new QzException("扣款失败");
    				}
    				//记录用户账户变更
    				ReturnDataNew returnData = new ReturnDataNew();
    				userPB.recordVC( 0, pay_park.getExpect_money(), pay_park.getMy_order(), 1, pay_park.getUi_id(), returnData);
    				try {
    					//4.	预约扣款完成：由于您未在规定时间内到达停车场，扣除XX元电子劵。
    					/**
    					 * 这里进行推送
    					 */
    					JPushMessageBean jPushMessageBean = new JPushMessageBean();
    					jPushMessageBean.setType(0);
    					jPushMessageBean.setMessage("由于您未在规定时间内到达停车场，扣除"+pay_park.getExpect_money()+"吾泊币。");
    					jPushMessageBean.setImgurl(Constants.JPUSH_LOGO);
    					jPushMessageBean.setTitle("预约超时扣款");
    					jPushMessageBean.setDate(new Date());
    					asyncJpushTask.doPdaJpush(jPushMessageBean,userinfo.getUuid());
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					log.error("预约超时扣款 推送出现错误", e);
    				}
    			}
			}
			// 车位数变动
    		Park_info park_info = park_infoDao.selectByKey(pay_park.getPi_id(),ReturnParkTableName(pay_park.getArea_code()));
			if(park_info == null){
				//该停车场不存在
				throw new QzException("该停车场不存在");
			}
         	//这里更新车辆出入库的数量变化
        	parkInfoPB.upCarNum(park_info, 0,3);
		} catch (Exception e) {
			log.error(" UserTransaction.ExpectOrderCheckTask is error", e);
			throw new QzException("事物异常 ExpectOrderCheckTask",e);
		}
	}
	
	/**
	 * PDA用户自动扣款   
	 * @param pay_park
	 */
	public void pda_sure(Pay_park pay_park) throws QzException{
		// TODO Auto-generated method stub
        try {
			//获取用户信息
			//虚拟币比例1元比100 分 
			User_info userinfo = user_infoDao.selectByKey(pay_park.getUi_id());
			if(userinfo == null){
				return;
			}
			//检查是否开启了自动扣款
			if(userinfo.getUi_autopay() == 0 ){
				//没有开启自动扣款
				throw new QzException("没有开启自动扣款");
			}
			
			
			if(userinfo.getUi_vc()-pay_park.getMoney() > 0){
				//虚拟币足够 且 是自动扣费
				userinfo.setUi_vc(userinfo.getUi_vc()-pay_park.getMoney()); //分
    			int count = user_infoDao.updateByKey(userinfo);
    			if(count < 1){
    				//更新用户扣款数据失败
    				//这里需要抛出异常
    				//扣款失败
    				throw new QzException("扣款失败");
    			}else{
    				//扣款成功
    				pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付 
    				pay_park.setMoney(pay_park.getMoney());//单位 分
    				count = pay_parkDao.updateByKey(pay_park);
    				if(count < 1){
    					//扣款失败
    					throw new QzException("扣款失败");
    				}
    				//记录用户账户变更
    				ReturnDataNew returnData = new ReturnDataNew();
    				userPB.recordVC( 0, pay_park.getMoney(), pay_park.getMy_order(), 0, pay_park.getUi_id(), returnData);
    			}
			}
		} catch (Exception e) {
			log.error(" UserTransaction.pda_sure is error", e);
			throw new QzException("事物异常 pda_sure",e);
		}
	}
	
	
	/**
	 * 扫描到车辆出库扣费
	 * @param is_free :0 收费  1：免费
	 */
	public void payMent(Pay_park pay_park,User_info userinfo,int total_money ,ReturnDataNew returnData,Date date,int is_free) throws QzException{
		
		try{
			
        	
        	
        	
        	
        	if(is_free == 1){
        		//免费
        		pay_park.setScan_time(date);//道闸停车  摄像头扫描到车辆  需要记录扫描时间点
        		pay_park.setIs_over(1);//订单是否完成(0:没有完成1：完成)
        		int count  = pay_parkDao.updateByKey(pay_park);
            	if(count != 1){
            		//更新失败
            		returnData.setReturnData(errorcode_data, "请求失败", ""); 
    				return;
            	}
        	}
        	
        	/**
			 * 道闸停车  摄像头扫描到车辆  需要记录扫描时间点
			 */
        	pay_park.setScan_time(date);
        	int count  = pay_parkDao.updateByKey(pay_park);
        	if(count != 1){
        		//更新失败
        		returnData.setReturnData(errorcode_data, "请求失败", ""); 
				return;
        	}
        	
        	
			/**
			 * 注意事项  占道停车  暂时不参与自动扣款处理   park_info.getPark_type() != 1
			 */
			if(userinfo.getUi_vc()-total_money > 0 && userinfo.getUi_autopay() == 1  && total_money > 0){
				//虚拟币足够 且 是自动扣费
				userinfo.setUi_vc(userinfo.getUi_vc()-total_money); //分
				count = user_infoDao.updateByKey(userinfo);
				if(count < 1){
					//更新用户扣款数据失败
					//这里需要抛出异常
					throw new QzException("扣款失败");
				}else{
					//扣款成功
					pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付
					pay_park.setMoney(total_money);//单位 分
					
					count = pay_parkDao.updateByKey(pay_park);
					if(count < 1){
						//扣款失败
						throw new QzException("扣款失败");
					}
					//扣款记录到用户金额明细中去  *** 后续是否进行异常捕获
    				userPB.recordVC( 0, pay_park.getMoney(), pay_park.getMy_order(), pay_park.getOrder_type(), pay_park.getUi_id(), returnData);
					/**
    				 * 更新商户账户金额
    				 */
    				userPB.upManchentMoney(pay_park.getPu_id(),pay_park.getPu_nd(),pay_park.getMoney(), 1);
				}
			}
		}catch(Exception e){
			throw new QzException("事物异常 payMent",e);
		}
		
		
	}
	
	
	/**
	 * 扫描到车辆出库扣费
	 */
	public void payMentRent(Pay_month_park pay_month_park,User_info userinfo,int total_money ,ReturnDataNew returnData,long pi_id, String area_code,String car_code,Date date,long difftime) throws QzException{
		
		try{
			/**
			 * 这里租赁分时间端包月产生了临停费用 则要动态创建 临停订单
			 */
			Pay_park pay_park  = payParkPB.MakeAutoOrder(pi_id, area_code, car_code, userinfo.getUi_id(),difftime);
			if(pay_park == null){
				returnData.setReturnData(errorcode_data, "请求失败", ""); 
				return;
			}
        	/**
			 * 道闸停车  摄像头扫描到车辆  需要记录扫描时间点
			 */
        	pay_month_park.setScan_time(date);
        	int count = pay_month_parkDao.updateByKey(pay_month_park);
        	if(count != 1){
        		//更新失败
        		returnData.setReturnData(errorcode_data, "请求失败", ""); 
				return;
        	}
			/**
			 * 注意事项  占道停车  暂时不参与自动扣款处理   park_info.getPark_type() != 1
			 */
			if(userinfo.getUi_vc()-total_money > 0 && userinfo.getUi_autopay() == 1){
				//虚拟币足够 且 是自动扣费
				userinfo.setUi_vc(userinfo.getUi_vc()-total_money); //分
				count = user_infoDao.updateByKey(userinfo);
				if(count < 1){
					//更新用户扣款数据失败
					//这里需要抛出异常
					throw new QzException("扣款失败");
				}else{
					//扣款成功
					pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付
					pay_park.setMoney(total_money);//单位 分
					
					count = pay_parkDao.updateByKey(pay_park);
					if(count < 1){
						//扣款失败
						throw new QzException("扣款失败");
					}
					//扣款记录到用户金额明细中去  *** 后续是否进行异常捕获
    				userPB.recordVC( 0, pay_park.getMoney(), pay_park.getMy_order(), pay_park.getOrder_type(), pay_park.getUi_id(), returnData);
					/**
    				 * 更新商户账户金额
    				 */
    				userPB.upManchentMoney(pay_park.getPu_id(),pay_park.getPu_nd(),pay_park.getMoney(), 1);
				}
			}
		}catch(Exception e){
			throw new QzException("事物异常 payMent",e);
		}
		
		
	}
	
	/*******************************下面是分出的方法*************************************/

	   



}
