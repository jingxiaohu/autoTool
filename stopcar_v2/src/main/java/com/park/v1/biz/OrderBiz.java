
package com.park.v1.biz;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.park.DataSource.DynamicDataSourceHolder;
import com.park.DataSource.TargetDataSource;
import com.park.Transaction.BaseTransaction;
import com.park.bean.Park_info;
import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.Rental_charging_rule;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_carcode;
import com.park.bean.User_info;
import com.park.bean.User_park_coupon;
import com.park.bean.User_vc_act;
import com.park.jpush.PDA.bean.PDAPushMessage;
import com.park.jpush.bean.JPushMessageBean;
import com.park.util.RequestUtil;
import com.park.v1.biz.common.ParkInfoPB;
import com.park.v1.biz.common.PayMonthParkPB;
import com.park.v1.biz.common.PayParkPB;

/**
 * 处理用户订单管理
 * @author jingxiaohu
 *
 */
@Service
public class OrderBiz extends BaseBiz{

	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	@Autowired
	protected CarBiz carBiz;
	@Autowired
	protected PayParkPB payParkPB;
	@Autowired
	protected PayMonthParkPB payMonthParkPB;
	
	@Autowired
	protected ParkInfoPB parkInfoPB;
    /**
     * 用户预约下单普通车位
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param pi_id
     * @param pay_type
     * @param expect_info
     * @param expect_money
     * @param expect_time
     * @param car_code
     * @param pp_versioncode
     */
	public void expect_order(ReturnDataNew returnData, int dtype, long ui_id,
			long pi_id, int pay_type, String expect_info, int expect_money,
			int expect_time, String car_code, String pp_versioncode,String area_code) {
		// TODO Auto-generated method stub
		try {
			//用户预约车位前的检查
			Pay_park  pay_park = 	QueryPayPark( ui_id, pi_id, car_code, 1, area_code);
			if(pay_park != null){
				//你已经预约过了
				returnData.setReturnData(errorcode_data, "您已经预约过了，亲！", ""); 
				return ;
			}
			//首先检查该用户账户上是否钱足够担负的起预约超时扣款
			User_info user_info = user_infoDao.selectByKey(ui_id);
			if(user_info == null){
				//用户不存在
				returnData.setReturnData(errorcode_data, "用户不存在", ""); 
				return ;
			}
			if(user_info.getUi_autopay() == 0){//是否自动支付 ：0 ：不是 1：是
				//没有开启自动扣款
				returnData.setReturnData(errorcode_data, "没有开启自动扣款", ""); 
				return ;
			}
			Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
			if(park_info == null){
				//该停车场不存在
				returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
				return ;
			}else{
				//检查该停车场是否出现故障
				if(park_info.getIs_fault() == 1){
					//不允许进行预约和租赁
					returnData.setReturnData(errorcode_data, "该停车场目前出现预约故障，暂时不能进行预约服务", ""); 
					return ;
				}
				
				//检查该停车场是否开启了预约
				if(park_info.getIs_expect() == 0){
					//检查该停车场是否开启了预约
					returnData.setReturnData(errorcode_data, "该停车场目前还未开启预约服务", ""); 
					return ;
				}
				
			}
			
			if(user_info.getUi_vc() - park_info.getExpect_money() < 0){
				//账户余额不足
				returnData.setReturnData(errorcode_data, "账户余额不足", ""); 
				return ;
			}
			
			Date date  = new Date();
			pay_park = new Pay_park();
			pay_park.setAllege_state(0);//申述状态 0:未申述 1：已申述
			pay_park.setArrive_time(date);//到场时间
    	    pay_park.setLeave_time(date);//离场时间
			pay_park.setCar_code(car_code);//车牌号
			pay_park.setCtime(date);//创建时间
			pay_park.setMy_order(returnUUID());//我们自己生成的订单号
			pay_park.setOrder_type(1);//下单类型 0: 普通下单  1：预约下单 2：租赁包月订单
//    	    pay_park.setOther_order(value);//第三方的 支付单号
//    	    pay_park.setPay_source(pay_source);//支付类型 1:支付宝 2：微信 3：银联
    	    pay_park.setPay_type(pay_type);//支付类型 0:手动输入密码支付 1：快捷支付（服务器可以请求第三方直接扣款）
    	    pay_park.setPhone_type(dtype);//手机类型 0:android 1：IOS
			pay_park.setPi_id(pi_id);//支付停车场主键ID
			pay_park.setPp_state(0);//支付状态 0:未支付 1：已经支付
    	    pay_park.setPp_versioncode(pp_versioncode);//当前APPSDK版本号 （内部升级版本代号）
			pay_park.setUi_id(ui_id);//用户ID
			pay_park.setUtime(date);//更新时间
    	    pay_park.setExpect_info(expect_info);//预约提示信息
         	pay_park.setExpect_money(expect_money);//预约价格
    	    pay_park.setExpect_time(expect_time);//预约时间 单位分钟
//    	    pay_park.setMoney(value);//支付金额（单位 分）
			pay_park.setNote("用户预约下单"); 
			//省市县区域代码
			pay_park.setAddress_name(park_info.getAddress_name());//停车场地址
			pay_park.setPi_name(park_info.getPi_name());//停车场名称
			pay_park.setArea_code(area_code);//省市县编号
			pay_park.setPark_type(park_info.getPark_type());
				
			
			
			//设置商户唯一标识
			pay_park.setPu_id(park_info.getPu_id());
			pay_park.setPu_nd(park_info.getPu_nd());
			
			//设置经纬度
			pay_park.setLng(park_info.getLng());
			pay_park.setLat(park_info.getLat());
			
			int id  = pay_parkDao.insert(pay_park);
			if(id < 1){
				//下单失败
				log.error("ParkinfoBiz.cameraCarOrder is error 下单插入的时候失败");
				returnData.setReturnData(errorcode_data, "预约失败", ""); 
				return;
			}
         	//这里更新车辆出入库的数量变化
        	parkInfoPB.upCarNum(park_info, 0,3);
			try {
				//预约成功 进行JPUSH推送
				PDAPushMessage pDAPushMessage = new PDAPushMessage();
				pDAPushMessage.setCar_code(car_code);
				pDAPushMessage.setOrderid(pay_park.getMy_order());
				pDAPushMessage.setTime(pay_park.getCtime());
				pDAPushMessage.setUi_id(ui_id);
				pDAPushMessage.setUi_tel(user_info.getUi_tel());
				pDAPushMessage.setUiid(user_info.getUuid());
				pDAPushMessage.setId(id);
				pDAPushMessage.setType(1);
				
				JPushMessageBean jPushMessageBean = new JPushMessageBean();
				jPushMessageBean.setType(1);
				jPushMessageBean.setMessage("");
				jPushMessageBean.setMessageJson((JSON)JSON.toJSON(pDAPushMessage));
				asyncJpushTask.doPdaJpushPDA(jPushMessageBean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("预约下单推送失败", e); 
			}
			
			
			
			 //返回结果
			returnData.setReturnData(errorcode_success, null, pay_park); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.expect_order is error"+e.getMessage(), e);
		}
	}
    /**
     *  用户下单租赁包月车位
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param pi_id
     * @param pay_type
     * @param money
     * @param month_num
     * @param month_info
     * @param car_code
     * @param pp_versioncode
     */
	public void pay_rent_order(ReturnDataNew returnData, int dtype,
			long ui_id, long pi_id, int pay_type, int month_num,
			String month_info, String car_code, String pp_versioncode,String area_code,long upc_id,int pay_source,String permit_time,int is_24hours,String orderid) {
		// TODO Auto-generated method stub  pay_source;//支付类型 1:支付l宝 2：微信 3：银联  4：钱包 
		try {
			Date date  = new Date();
			
			
			Pay_month_park pay_month_park = new Pay_month_park();
			if(!RequestUtil.checkObjectBlank(orderid)){
				//获取该未支付的 租赁订单信息
				pay_month_park = payMonthParkPB.selectOnePayMonthPark(orderid);
				if(pay_month_park == null){
					returnData.setReturnData(errorcode_data, "支付失败", ""); 
					return;
				}
			}else{
				//***暂时不处理***  检查该用户是否存在租赁确没有付款的  --- 租赁订单
				try {
					String sql = "select * from pay_month_park where pi_id=? and car_code=?   and ui_id=? and cancel_state=0  order by ctime desc limit 1";
					Pay_month_park pay_month_park2 = getMySelfService().queryUniqueT(sql, Pay_month_park.class, pi_id,car_code,ui_id);
					if(pay_month_park2 != null){
						if(pay_month_park2.getPp_state() == 0){
							returnData.setReturnData(errorcode_data, "亲，你还有没有支付完成的订单", ""); 
							return;
						}else{
							if(pay_month_park2.getEnd_time().getTime() - date.getTime() > 0){
								//你的包月订单还没有到期
								returnData.setReturnData(errorcode_data, "亲，你的租赁时间还未到期!", ""); 
								return;
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("亲，你还有没有支付完成的订单 失败", e);
				}
			}
			//先检查该用户在该停车场已经出现了临停费用  则不允许没有缴费临停   之前进行租赁
			Pay_park pay_park = payParkPB.QueryPayPark(ui_id, pi_id, car_code, area_code);
			if(pay_park != null){
				returnData.setReturnData(errorcode_data, "你还没有进行临停缴费，不允许进行租赁", ""); 
    			return;
			}
			
			//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}else{
				//检查该停车场是否出现故障
				if(park_info.getIs_fault() == 1){
					//不允许进行预约和租赁
					returnData.setReturnData(errorcode_data, "该停车场目前出现租赁故障，暂时不能进行租赁服务", ""); 
					return ;
				}
				
				//占道停车不允许租赁
				if(pay_month_park.getPark_type()== 1){
					//占道停车不允许租赁
					returnData.setReturnData(errorcode_data, "占道停车场，暂时未开通租赁服务", ""); 
					return ;
				}
				
			}
        	
        	
			int money = park_info.getMonth_price()*month_num;
			//用户下单租赁车位前的检查
			//用户ID 通过车牌号获取用户ID 
			
			pay_month_park.setAllege_state(0);//申述状态 0:未申述 1：已申述
			pay_month_park.setMoney(money);//支付金额（单位 分）
			
			
			User_park_coupon   user_park_coupon = null;
			if(upc_id > 0 ){
				//如果有折扣券或者金额券
				user_park_coupon =  user_park_couponDao.selectByKey(upc_id);
				if(user_park_coupon != null && user_park_coupon.getEnd_time().getTime() < date.getTime() && user_park_coupon.getUpc_state() == 0){
					//没有过期且有效
					if(user_park_coupon.getUpc_type() == 0){
						//金额券
						if(user_park_coupon.getHigh_money() > user_park_coupon.getMoney()){
							if(money-user_park_coupon.getMoney() > 0){
								pay_month_park.setMoney(money-user_park_coupon.getMoney());
							}else{
								pay_month_park.setMoney(0);
							}
							pay_month_park.setDiscount_money(user_park_coupon.getMoney());
							pay_month_park.setDiscount_name(user_park_coupon.getMoney()/100+"元券");
						}else{
							pay_month_park.setDiscount_money(user_park_coupon.getHigh_money());
							pay_month_park.setMoney(money - user_park_coupon.getHigh_money());
						}
						
					}else{
						//折扣券
						if(user_park_coupon.getHigh_money() > (money-(int)user_park_coupon.getDiscount()*money)){
							pay_month_park.setDiscount_money(money-(int)user_park_coupon.getDiscount()*money);
							pay_month_park.setMoney((int)user_park_coupon.getDiscount()*money);
						}else{
							pay_month_park.setDiscount_money(user_park_coupon.getHigh_money());
							pay_month_park.setMoney(money - user_park_coupon.getHigh_money());
						}
						
						pay_month_park.setDiscount_name((int)user_park_coupon.getDiscount()+"折券");
					}
					pay_month_park.setDiscount_type(user_park_coupon.getUpc_type());
				}
			}
			
			
    	    pay_month_park.setMonth_num(month_num);//包月租凭月数
    	    pay_month_park.setMonth_info(month_info);//包月提示信息
    	    //包月起始日 包月截止日
    	    pay_month_park.setStart_time(date);
    		
    		Calendar cl = Calendar.getInstance(Locale.CHINESE);
    		cl.setTime(date);
    		cl.add(Calendar.DATE, month_num*30);
    		pay_month_park.setEnd_time(cl.getTime());
    	    
			pay_month_park.setCar_code(car_code);//车牌号
			pay_month_park.setCtime(date);//创建时间
			pay_month_park.setMy_order(returnUUID());//我们自己生成的订单号
			pay_month_park.setOrder_type(2);//下单类型 0: 普通下单  1：预约下单 2：租赁包月订单
//    	    pay_park.setOther_order(value);//第三方的 支付单号
			pay_month_park.setPay_source(pay_source);//支付类型 1:支付宝 2：微信 3：银联 4：钱包
    	    pay_month_park.setPay_type(pay_type);//支付类型 0:手动输入密码支付 1：快捷支付（服务器可以请求第三方直接扣款）
    	    pay_month_park.setPhone_type(dtype);//手机类型 0:android 1：IOS
			pay_month_park.setPi_id(pi_id);//支付停车场主键ID
			pay_month_park.setPp_state(0);//支付状态 0:未支付 1：已经支付
    	    pay_month_park.setPp_versioncode(pp_versioncode);//当前APPSDK版本号 （内部升级版本代号）
			pay_month_park.setUi_id(ui_id);//用户ID
			pay_month_park.setUtime(date);//更新时间
			pay_month_park.setNote("用户预约下单"); 
			//优惠券
			pay_month_park.setUpc_id(upc_id);
			pay_month_park.setAddress_name(park_info.getAddress_name());
			pay_month_park.setPi_name(park_info.getPi_name());//停车场名称
			
			//省市县区域代码
			pay_month_park.setArea_code(area_code);
			//允许的时间段
			pay_month_park.setPermit_time(permit_time);
			//是否是24小时包月
			pay_month_park.setIs_24hours(is_24hours);
			pay_month_park.setPark_type(park_info.getPark_type());
			
			//设置商户唯一标识
			pay_month_park.setPu_id(park_info.getPu_id());
			pay_month_park.setPu_nd(park_info.getPu_nd());
			
			if(!RequestUtil.checkObjectBlank(orderid)){
				//获取该未支付的 租赁订单信息
				int count = pay_month_parkDao.updateByKey(pay_month_park);
				if(count < 1){
					//更新失败
					returnData.setReturnData(errorcode_data, "支付失败", ""); 
					return;
				}
				
			}else{
				//设置经纬度
				pay_month_park.setLng(park_info.getLng());
				pay_month_park.setLat(park_info.getLat());
				
				int id  = pay_month_parkDao.insert(pay_month_park);
				if(id < 1){
					//下单失败
					log.error("ParkinfoBiz.cameraCarOrder is error 下单插入的时候失败");
					returnData.setReturnData(errorcode_data, "预约失败", ""); 
					return;
				}
				//设置主键
				pay_month_park.setId(id);
			}

			//设置返回的消息
			String message = "下单成功";
			
			if(ui_id > 0){
				//处理扣款等 --- 支付的时候才进行扣款处理   
				//支付类型 1:支付宝  2：微信  3：银联  4：钱包 5:龙支付
				if(pay_source == 4 && pay_month_park.getPark_type()== 0){
					//钱包   Park_type   INT    停车场类型0：道闸停车场1：占道车场2：露天立体车库停车场
					//处理扣款等
					try {
		        		//事物处理  
		        		baseTransaction.getUserTransaction().record_user_vc_act(returnData, ui_id, pay_month_park.getMoney(), pay_month_park.getMy_order(), 2, 0, 0,user_park_coupon);
		        		message = "支付成功";
		        		//这里更新车辆出入库的数量变化 ****** 这里需要再次思考
                    	parkInfoPB.upCarNum(park_info, 0,2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("事物处理 record_user_vc_act  付款失败",e); 
						return;
					}
				}	

			}

			
			 //返回结果
			returnData.setReturnData(errorcode_success, message, pay_month_park); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.pay_rent_order is error"+e.getMessage(), e);
		}
	}
	/**
	 * 用户取消预约下单
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param orderid
	 * @param type 0：普通车位预约  1：租赁车位预约
	 * @param area_code 省市区代码
	 * @param pi_id 停车场主键ID
	 */
	public void cancel_order(ReturnDataNew returnData, int dtype, long ui_id,
			String orderid, int type,long pi_id,String area_code) {
		// TODO Auto-generated method stub
        try {
        	if(type == 0){
        		//首先查询订单是否存在  然后比对订单时间是否超过5分钟 如果超过5分钟则不能进行取消订单
        		Pay_park pay_park = QueryByOrderId( orderid, ui_id);
        		if(pay_park == null){
        			returnData.setReturnData(errorcode_data, "该订单不存在", ""); 
        			return;
        		}
        		//已经取消的订单不允许再次取消
        		if(pay_park.getCancel_state() == 1){
        			//已经取消过了
        			returnData.setReturnData(errorcode_data, "该订单已经取消过了", ""); 
        			return;
        		}
        		//下单类型0:普通下单1：预约下单2：租赁包月订单
        		int order_type = pay_park.getOrder_type();
        		if(order_type == 1){
        			//预约订单
            		if(System.currentTimeMillis() - pay_park.getCtime().getTime() > 5*60*1000){
            			//超过5分钟
            			returnData.setReturnData(errorcode_data, "超过5分钟不允许取消订单", ""); 
            			return;
            		}
        		}else if(order_type == 0){
        			//普通订单
        			
        		}
        		
        		

        		if(pay_park.getArrive_time().getTime() > pay_park.getCtime().getTime()){
        			//到场的车不能取消订单
        			returnData.setReturnData(errorcode_data, "进入停车场后不允许取消订单", ""); 
        			return;
        		}
        		
        		// 取消普通车位预约订单
        		String sql = "update pay_park set cancel_state=1,is_over=1 where my_order=:orderid";
        		Map<String, Object> paramMap = new HashMap<String, Object>();
        		paramMap.put("orderid", orderid);
        		int count = getMySelfService().updateBySQL(sql, paramMap);
        		if(count < 1){
        			//取消普通车位订单失败
        			returnData.setReturnData(errorcode_success, "取消订单失败", ""); 
        			return;
        		}

    			
    			if(order_type == 1){
    				//预约订单
        			try {
                		// 车位数变动
                		Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
            			if(park_info == null){
            				//该停车场不存在
            				returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
            				return ;
            			}
            			/**
            			 * 2016-11-24
            			 * 车库车辆数量变更
            			 */
            			//is_enter 0：   入库   1：出库
            			parkInfoPB.upCarNum(park_info, 1,4);
                		
            			
        				//取消预约成功 进行JPUSH推送
        				PDAPushMessage pDAPushMessage = new PDAPushMessage();
        				pDAPushMessage.setCar_code(pay_park.getCar_code());
        				pDAPushMessage.setOrderid(pay_park.getMy_order());
        				pDAPushMessage.setTime(pay_park.getCtime());
        				pDAPushMessage.setUi_id(ui_id);
        				User_info user_info = user_infoDao.selectByKey(ui_id);
        				if(user_info == null){
        					//用户不存在
        					log.error("取消预约下单推送失败  用户不存在 ui_id="+ui_id); 
        					return ;
        				}
        				pDAPushMessage.setUi_tel(user_info.getUi_tel());
        				pDAPushMessage.setUiid(user_info.getUuid());
        				pDAPushMessage.setId(pay_park.getId());
        				pDAPushMessage.setType(2);
        				
        				JPushMessageBean jPushMessageBean = new JPushMessageBean();
        				jPushMessageBean.setType(1);
        				jPushMessageBean.setMessage("");
        				jPushMessageBean.setMessageJson((JSON)JSON.toJSON(pDAPushMessage));
        				asyncJpushTask.doPdaJpushPDA(jPushMessageBean);
        			} catch (Exception e) {
        				// TODO Auto-generated catch block
        				log.error("取消预约下单推送失败", e); 
        			}
    			}
        		//取消普通车位订单成功
    			returnData.setReturnData(errorcode_success, "取消订单成功", ""); 
    			return;
        		
        	}else{
        		/**
        		 * 暂时不考虑 租赁车位的订单取消问题
        		 */
        		// 取消租赁车位预约订单
        		String sql = "update pay_month_park set cancel_state=1 ,is_over=1 where my_order=:orderid";
        		Map<String, Object> paramMap = new HashMap<String, Object>();
        		paramMap.put("orderid", orderid);
        		int count = getMySelfService().updateBySQL(sql, paramMap);
        		if(count == 1){
        			//取消租赁车位订单成功
        			returnData.setReturnData(errorcode_success, "取消订单成功", ""); 
        			return;
        		}
        		//取消租赁车位订单失败
    			returnData.setReturnData(errorcode_data, "取消订单失败", ""); 
    			return;
        	}
		} catch (Exception e) {
			log.error("CarBiz cancel_order is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	/**
	 * 用户删除订单
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param orderid
	 * @param type
	 * @param pi_id
	 * @param area_code
	 */
	public void delete_order(ReturnDataNew returnData, int dtype, long ui_id,
			String orderid, int type, long pi_id, String area_code) {
		// TODO Auto-generated method stub
        try {
        	//type 0：普通车位  1：租赁车位
        	if(type == 0){
        		//获取订单
        		Pay_park pay_park = payParkPB.selectOnePayPark(orderid);// QueryByOrderId(orderid);
        		if(pay_park == null){
        			//该订单不存在
        			returnData.setReturnData(errorcode_data, "该订单不存在,亲!", ""); 
        			return;
        		}
        		if(pay_park.getOrder_type() == 1){
        			//普通订单 
        			if(pay_park.getPp_state() == 0){
            			//未支付
            			returnData.setReturnData(errorcode_data, "未支付的订单不允许删除,亲!", ""); 
            			return;
            		}
        		}else{
        			//预约订单
        			if(pay_park.getCancel_state() == 0 && pay_park.getPp_state() == 0){
        				//没有取消 且 没有支付
        				returnData.setReturnData(errorcode_data, "没有被取消或者没有超时的订单不能删除,亲!", ""); 
            			return;
        			}
        		}
        		
        		//普通车位
        		String sql = "update pay_park set is_del=1 where my_order=:orderid";
        		Map<String, Object> paramMap = new HashMap<String, Object>();
        		paramMap.put("orderid", orderid);
        		int count = getMySelfService().updateBySQL(sql, paramMap);
        		if(count == 1){
        			//删除普通车位预约订单成功
        			returnData.setReturnData(errorcode_success, "删除订单成功", ""); 
        			return;
        		}
        		//删除普通车位预约订单失败
    			returnData.setReturnData(errorcode_data, "删除订单失败", ""); 
    			return;
        		
        	}else{
        		Date date = new Date();
        		Pay_month_park pay_month_park =  payMonthParkPB.selectOnePayMonthPark(orderid);
        		if(pay_month_park == null){
        			//该订单不存在
        			returnData.setReturnData(errorcode_data, "该订单不存在,亲!", ""); 
        			return;
        		}
        		if(pay_month_park.getPp_state() == 0 && pay_month_park.getCancel_state() == 0){
        			//没有支付的租赁订单不允许删除
        			returnData.setReturnData(errorcode_data, "该订单还未支付不允许删除,亲!", ""); 
        			return;
        		}
        		if(pay_month_park.getPp_state() == 1 && (pay_month_park.getEnd_time().getTime() - date.getTime()  > 0)){
        			//没有支付的租赁订单不允许删除
        			returnData.setReturnData(errorcode_data, "已支付且未到期的租赁订单不允许删除,亲!", ""); 
        			return;
        		}
        		// 删除租赁车位订单
        		String sql = "update pay_month_park set is_del=1 where my_order=:orderid";
        		Map<String, Object> paramMap = new HashMap<String, Object>();
        		paramMap.put("orderid", orderid);
        		int count = getMySelfService().updateBySQL(sql, paramMap);
        		if(count == 1){
        			//删除租赁车位预约订单成功
        			returnData.setReturnData(errorcode_success, "删除订单成功", ""); 
        			return;
        		}
        		//删除租赁车位预约订单失败
    			returnData.setReturnData(errorcode_data, "删除订单失败", ""); 
    			return;
        	}
		} catch (Exception e) {
			log.error("CarBiz delete_order is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}

	
	
	/**
	 * 读取停车场租赁详情
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param pi_id
	 */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void read_parkrent_info(ReturnDataNew returnData, int dtype, long ui_id,
			long pi_id,String area_code) {
		// TODO Auto-generated method stub
        try {
        	JSONObject returnobj = new JSONObject();
        	
        	
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null){
        		//该停车场不存在
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
        	//停车场基本信息
        	returnobj.put("park_info", park_info);
        	//获取该停车场  蓝牌小汽车的包月规则
        	String sql = "select *  from rental_charging_rule where pi_id=? and area_code=? and  car_type=1 and car_code_color=1  and  rcr_type=1 limit 1";
        	Rental_charging_rule rule = getMySelfService().queryUniqueT(sql, Rental_charging_rule.class, pi_id,area_code);
        	if(rule == null){
        		//该停车场不存在包月租赁业务
        		returnData.setReturnData(errorcode_data, "该停车场不存在包月租赁业务", ""); 
    			return;
        	}
        	//填充 包月规则详情
        	returnobj.put("rule", rule);
        	
        	//优惠券列表数据
        	sql = "select * from user_park_coupon where ui_id =:ui_id and upc_state=0 and end_time > :time order by ctime  desc";
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ui_id", ui_id);
			paramMap.put("time", new Date());
			List<Map<String,Object>> list = getMySelfService().queryForList(sql, paramMap);
			
			//填充优惠券列表
			if(list == null || list.size() == 0){
	        	returnobj.put("coupon", "");
			}else{
				returnobj.put("coupon", list);
			}
        	
			returnData.setReturnData(errorcode_success, "读取停车场租赁详情成功", returnobj); 
			return;
		} catch (Exception e) {
			log.error("orderBiz read_parkrent_info is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	
	/**
	 * 用户停车缴费读取订单
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param pi_id
	 */
	public void read_pay_order(ReturnDataNew returnData, int dtype, long ui_id,
			long pi_id,String car_code,String area_code) {
		// TODO Auto-generated method stub
		try {
			JSONObject returnobj = new JSONObject();
			
			
			//从订单表中 获取该用户的停车缴费订单信息
			Pay_park pay_park = null;
			String sql = "select *  from  pay_park where pi_id=? and area_code=? and ui_id=? and car_code=? and pp_state=0 order by ctime desc limit 1";
			pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,ui_id,car_code);
			if(pay_park == null){
				//该车辆未出入
				returnData.setReturnData(errorcode_data, "该车辆未出入", ""); 
				return;
			}
			
			Date date = new Date();
			//时间差 
			//计算当前应付金额
			long diff_time = date.getTime() - pay_park.getCtime().getTime() - pay_park.getStart_time()*60*1000;
			int hours = 0;//超时时间 单位小时
			if(diff_time > 0){
				//超时
				//超时时间
				if(diff_time % (3600*1000) > 1*1000){
					//余数大于一分钟 按一个小时计算
					hours = (int)diff_time /(3600*1000) + 1;
				}else{
					hours = (int)diff_time /(3600*1000) ;
				}
				
			}
			if(hours < 1){
				//不到一个小时就按一个小时计算
				hours = 1;
			}
			//设置累计计费时长
			pay_park.setFinal_time((int)(date.getTime() - pay_park.getCtime().getTime())/(60*1000));
			/**
			 * 是否具有 多少分钟之内进出免费是否开启  0:不开启  1：开启
			 * 
			 * 验证是否该停车场允许 某15分钟内 进出免费
			 */
			if(pay_park.getIs_free_minute() == 1 
					&& date.getTime() - pay_park.getCtime().getTime()  <=  pay_park.getFree_minute() ){
				//开启 且 pay_park.getFree_minute() 分钟内 可以免费进出
				//结算时计费时长（单位分钟）
				int minute = (int)(date.getTime() - pay_park.getCtime().getTime())/(60*1000);
				pay_park.setFinal_time(minute);
				pay_park.setMoney(0);
			}else{
				//总计费金额
				int total_money = pay_park.getStart_price()+pay_park.getCharging()*hours;
				pay_park.setMoney(total_money);
			}
			

			
			//订单信息
			returnobj.put("pay_park", pay_park);
			
			
			//获取用户优惠券
			//获取用户抵扣券
        	//优惠券列表数据
        	sql = "select * from user_park_coupon where ui_id =:ui_id and upc_state=0 and end_time > :time order by ctime  desc";
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ui_id", ui_id);
			paramMap.put("time", new Date());
			List<Map<String,Object>> list = getMySelfService().queryForList(sql, paramMap);
			//填充优惠券列表
			if(list == null || list.size() == 0){
	        	returnobj.put("coupon", "");
			}else{
				returnobj.put("coupon", list);
			}
			//验证是否可以进行临停订单支付
			//车辆还没有出库  还没有进行金额计算  该车辆还未被道闸系统扫描到车牌
			if(date.getTime()-pay_park.getScan_time().getTime() >= 30*60*1000 || pay_park.getCtime().getTime()-pay_park.getScan_time().getTime() == 0 ){
				//车辆还没有出库  还没有进行金额计算  该车辆还未被道闸系统扫描到车牌
				//亲！请耐心等待费用计算....
				returnobj.put("is_show", 0);
			}else{
				returnobj.put("is_show", 1);
			}
			 //返回结果
			returnData.setReturnData(errorcode_success, "用户停车缴费读取订单成功", returnobj); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.Read_pay_order is error"+e.getMessage(), e);
		}
	}
	
	
	/**
	 * 用户普通停车位直接正式付款下单(e泊的停车缴费)
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param orderid
	 * @param upc_id
	 * @param pay_source
	 */
	public void pay_order(ReturnDataNew returnData, int dtype, long ui_id,
			String orderid, long upc_id, int pay_source) {
		// TODO Auto-generated method stub
		//pay_source;//支付类型 1:支付l宝 2：微信 3：银联  4：钱包 
		try {
			Date date = new Date();
			//从订单表中 获取该用户的停车缴费订单信息
			Pay_park pay_park = null;
			String sql = "select *  from  pay_park where my_order=? limit 1";
			pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, orderid);
			if(pay_park == null){
				//该车辆未出入
				returnData.setReturnData(errorcode_data, "该车辆未出入", ""); 
				return;
			} 
			//更新该订单对应得 应付金额  结算时计费时长 抵扣优惠券名称  优惠券类型   抵扣优惠金额
			pay_park.setPay_source(pay_source);
			//每次刷新订单的实时算费 都要刷新时间
			pay_park.setUtime(date);
			
			//车辆还没有出库  还没有进行金额计算  该车辆还未被道闸系统扫描到车牌
			if(date.getTime()-pay_park.getScan_time().getTime() >= 30*60*1000 || pay_park.getCtime().getTime()-pay_park.getScan_time().getTime() == 0 ){
				//车辆还没有出库  还没有进行金额计算  该车辆还未被道闸系统扫描到车牌
				returnData.setReturnData(errorcode_data, "亲！请耐心等待费用计算....", ""); 
				return;
			}
			
			
			/**
			 * 是否具有 多少分钟之内进出免费是否开启  0:不开启  1：开启
			 * 
			 * 验证是否该停车场允许 某15分钟内 进出免费
			 */
			if(pay_park.getIs_free_minute() == 1 
					&& date.getTime() - pay_park.getCtime().getTime()  <=  pay_park.getFree_minute() ){
				//开启 且 pay_park.getFree_minute() 分钟内 可以免费进出
				//结算时计费时长（单位分钟）
				int minute = (int)(date.getTime() - pay_park.getCtime().getTime())/(60*1000);
				pay_park.setFinal_time(minute);
				int count = pay_parkDao.updateByKey(pay_park);
				if(count < 1){
					//更新失败
					//该车辆未出入
					returnData.setReturnData(errorcode_data, "用户停车缴费下单信息更新失败", ""); 
					return;
				}
				returnData.setReturnData(errorcode_success, "用户停车缴费下单信息更新成功", pay_park); 
				return;
			}
			

			
			
			
			
			
			//计算当前应付金额
			int money = payParkPB.returnCarMoney(pay_park);
			User_park_coupon   user_park_coupon = null;
			if(upc_id > 0 ){
				//如果有折扣券或者金额券
				user_park_coupon =  user_park_couponDao.selectByKey(upc_id);
				if(user_park_coupon != null && user_park_coupon.getEnd_time().getTime() < date.getTime() && user_park_coupon.getUpc_state() == 0){
					//没有过期且有效
					if(user_park_coupon.getUpc_type() == 0){
						//金额券
						if(user_park_coupon.getHigh_money() > user_park_coupon.getMoney()){
							if(money-user_park_coupon.getMoney() > 0){
								pay_park.setMoney(money-user_park_coupon.getMoney());
							}else{
								pay_park.setMoney(0);
							}
							pay_park.setDiscount_money(user_park_coupon.getMoney());
						}else{
							pay_park.setDiscount_money(user_park_coupon.getHigh_money());
							pay_park.setMoney(money - user_park_coupon.getHigh_money());
						}
						
						pay_park.setDiscount_name(user_park_coupon.getMoney()+"元券");
						
					}else{
						//折扣券
						if(user_park_coupon.getHigh_money() > (money-(int)user_park_coupon.getDiscount()*money)){
								pay_park.setDiscount_money(money-(int)user_park_coupon.getDiscount()*money);
								pay_park.setMoney((int)user_park_coupon.getDiscount()*money);
						}else{
							pay_park.setDiscount_money(user_park_coupon.getHigh_money());
							pay_park.setMoney(money - user_park_coupon.getHigh_money());
						}
						
						pay_park.setDiscount_name((int)user_park_coupon.getDiscount()+"折券");
					}
					pay_park.setDiscount_type(user_park_coupon.getUpc_type());
				}
			}

			//结算时计费时长（单位分钟）
			int minute = (int)(date.getTime() - pay_park.getCtime().getTime())/1000;
			pay_park.setFinal_time(minute);
			//
			int count = pay_parkDao.updateByKey(pay_park);
			if(count < 1){
				//更新失败
				//该车辆未出入
				returnData.setReturnData(errorcode_data, "该车辆未出入", ""); 
				return;
			}
			if(ui_id > 0){
				//处理扣款等 --- 支付的时候才进行扣款处理   
				//支付类型 1:支付宝  2：微信  3：银联  4：钱包 5:龙支付
				if(pay_source == 4 && pay_park.getPark_type()== 0){
					//钱包   Park_type   INT    停车场类型0：道闸停车场1：占道车场2：露天立体车库停车场
					try {
		        		//事物处理  
		        		baseTransaction.getUserTransaction().record_user_vc_act(returnData, ui_id, pay_park.getMoney(), pay_park.getMy_order(), 0, 0, 0,  user_park_coupon);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("事物处理 record_user_vc_act  付款失败",e); 
						return;
					}
				}
			}

			 //返回结果
			returnData.setReturnData(errorcode_success, "用户停车缴费下单信息更新成功", pay_park); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.pay_order is error"+e.getMessage(), e);
		}
	}
	
	
	
	/**
	 * 获取我的订单 （1：临时停车订单  2： 租赁订单  3：全部订单）
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param type
	 * @param car_code
	 * @param area_code
	 */
	@TargetDataSource(value=DynamicDataSourceHolder.SLAVE)
	public void my_order(ReturnDataNew returnData, int dtype, long ui_id, int type,
			String car_code, String area_code,int page,int size) {
		// TODO Auto-generated method stub 获取订单类型  0:普通停车订单  1：租赁停车订单
		try {
			if(page < 1){
				page = 1;
			}
			int start = (page-1)*size;
			if(type == 0){
				//0:普通停车订单
				String sql = "select *  from  pay_park  where ui_id=? and is_del=0 and cancel_state=0 order by ctime desc limit "+start+","+size;
				List<Pay_park> list = getMySelfService().queryListT(sql, Pay_park.class, ui_id);
				//返回数据
				returnData.setReturnData(errorcode_success, "普通停车订单", list);  
				return;
			}else{
				//1：租赁停车订单
				String sql = "select *  from  pay_month_park  where ui_id=? and is_del=0 and cancel_state=0 order by ctime desc limit "+start+","+size;
				List<Pay_month_park> list = getMySelfService().queryListT(sql, Pay_month_park.class, ui_id);
				//返回数据
				returnData.setReturnData(errorcode_success, "租赁停车订单", list); 
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.my_order is error"+e.getMessage(), e);
		}
	}
	
	/**
	 * 读取停车场详情
	 * @param returnData
	 * @param dtype
	 * @param pi_id
	 * @param area_code
	 */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void read_park_info(ReturnDataNew returnData, int dtype, long pi_id,
			String area_code) {
		// TODO Auto-generated method stub
		 try {
	        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	        	if(park_info == null){
	        		//该停车场不存在
	        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
	    			return;
	        	}
        		//返回结果
        		returnData.setReturnData(errorcode_success, "获取停车场详情成功", park_info); 
    			return;
			} catch (Exception e) {
				log.error("orderBiz read_park_info is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
	    }
	}
	
	
	/**
	 * 获取我的账户出入记录
	 * @param returnData
	 * @param dtype
	 * @param ui_id
	 * @param type
	 * @param page
	 * @param size
	 */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void vc_record(ReturnDataNew returnData, int dtype, long ui_id,
			int type, int page, int size) {
		// TODO Auto-generated method stub
		try {
			if(page < 1){
				page = 1;
			}
			int start = (page-1)*size;
			if(type == 0){
				//0:全部的出入记录
				String sql = "select *  from  user_vc_act  where ui_id=? order by ctime desc limit "+start+","+size;
				List<User_vc_act> list = getMySelfService().queryListT(sql, User_vc_act.class, ui_id);
				//返回数据
				returnData.setReturnData(errorcode_success, "全部的出入记录", list);  
				return;
			}else if(type == 1){
				String sql = "select *  from  user_vc_act  where ui_id=? and is_add=? order by ctime desc limit "+start+","+size;
				List<User_vc_act> list = getMySelfService().queryListT(sql, User_vc_act.class, ui_id,0);
				//返回数据
				returnData.setReturnData(errorcode_success, "减少的虚拟币记录", list); 
				return;
			}else{
				String sql = "select *  from  user_vc_act  where ui_id=? and is_add=? order by ctime desc limit "+start+","+size;
				List<User_vc_act> list = getMySelfService().queryListT(sql, User_vc_act.class, ui_id,1);
				//返回数据
				returnData.setReturnData(errorcode_success, "增加的虚拟币记录", list); 
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.vc_record is error"+e.getMessage(), e);
		}
	}
	
	/**
	 * 露天停车场的PDA更新用户自动支付
	 * @param returnData
	 * @param dtype
	 * @param orderid
	 * @param pay_type 支付类型 0:自动扣款  1：现金支付
	 * @param pi_id
	 * @param area_code
	 */
	public void pda_sure(ReturnDataNew returnData, int dtype, String orderid,
			int pay_type, long pi_id, String area_code,int money) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	Pay_park pay_park   =  payParkPB.selectOnePayPark(orderid);// QueryByOrderId(orderid);
        	if(pay_park == null){
        		returnData.setReturnData(errorcode_data, "该订单不存在", ""); 
    			return;
        	}
        	pay_park.setMoney(money);
        	if(pay_type == 1){
        		//现金支付
        		pay_park.setPp_state(1);//设置已经支付
        		pay_park.setIs_cash(1);//是否现金支付0：在线支付1：现金支付
        	}else{
        		//钱包自动支付
        		try {
            		if(pay_park.getPp_state() == 0){
            			//未支付
                		//事物处理  该车辆出入的细节
                		baseTransaction.getCarTransaction().pda_sure(pay_park);
            		}

    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				log.error("PDA用户自动扣款变更 错误",e); 
    				returnData.setReturnData(errorcode_data, "自动支付失败", "","1"); 
    				return;
    			}
        	}
        	int count  = pay_parkDao.updateByKey(pay_park);
        	if(count == 1){
        		 //返回结果
    			returnData.setReturnData(errorcode_success, "处理成功", ""); 
    			return;
        	}else{
        		 //返回结果
    			returnData.setReturnData(errorcode_data, "处理失败", "","2"); 
    			return;
        	}
			
		} catch (Exception e) {
			log.error("ParkinfoBiz pda_sure is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	/**
	 * 道闸停车场的更新用户现金支付状态和金额
	 * @param returnData
	 * @param dtype
	 * @param orderid
	 * @param pi_id
	 * @param area_code
	 * @param money
	 */
	public void pay_sure(ReturnDataNew returnData, int dtype, String orderid,
			long pi_id, String area_code, int money) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	Pay_park pay_park   =  payParkPB.selectOnePayPark(orderid);// QueryByOrderId(orderid);
        	if(pay_park == null){
        		returnData.setReturnData(errorcode_data, "该订单不存在", ""); 
    			return;
        	}
        	pay_park.setMoney(money);
        	//现金支付
        	pay_park.setPp_state(1);//设置已经支付
        	pay_park.setIs_cash(1);//是否现金支付0：在线支付1：现金支付
        	int count  = pay_parkDao.updateByKey(pay_park);
        	if(count == 1){
        		 //返回结果
    			returnData.setReturnData(errorcode_success, "处理成功", ""); 
    			return;
        	}else{
        		 //返回结果
    			returnData.setReturnData(errorcode_data, "处理失败", "","1"); 
    			return;
        	}
			
		} catch (Exception e) {
			log.error("ParkinfoBiz pay_sure is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	/**
	 * 检查该车牌号是否是预约车
	 * @param returnData
	 * @param area_code
	 * @param pi_id
	 * @param car_code
	 * @param car_type
	 * @param car_code_color
	 */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void check_expectcar(ReturnDataNew returnData, String area_code,
			long pi_id, String car_code, int car_type, int car_code_color) {
		// TODO Auto-generated method stub
		 try {
			    JSONObject obj = new JSONObject();
	        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
	        	//获取该场地的信息
	        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	        	if(park_info == null ){
	        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
	    			return;
	        	}
				/**
				 * 检查该车牌号 该次订单是否已经支付 如果已经在线支付了 那么就只记录该次订单的车辆已经开闸出库和开闸时间
				 * 如果没有在线支付 那么就是现金支付 则更改支付状态为已经支付 
				 */
				//获取订单信息
				String sql = "select *  from pay_park where pi_id = ? and area_code=? and car_code=? and order_type=1 and pp_state=0  and is_del=0 and cancel_state=0 and  unix_timestamp()-UNIX_TIMESTAMP(ctime) <= expect_time*60 order by  ctime desc limit 1";
				Pay_park pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,car_code);
				
				if(pay_park != null){
					obj.put("type", 1);//0:临停车 1:预约车 2：租赁车
					obj.put("expect_state", 2);//1:预约超时 2：预约未超时
					obj.put("rent_state", 0);//1:租期超时 2：租期未超时  3：不在租期时间段内入场
					returnData.setReturnData(errorcode_success, "获取成功", ""); 
	    			return;
				}
				
				//获取租赁订单信息
				sql = "select *  from pay_month_park where pi_id = ? and area_code=? and car_code=?  and pp_state=1 and  UNIX_TIMESTAMP(end_time) - unix_timestamp() >= 0  order by  ctime desc limit 1";
				Pay_month_park pay_month_park = getMySelfService().queryUniqueT(sql, Pay_month_park.class, pi_id,area_code,car_code);
				if(pay_month_park != null){
					obj.put("type", 1);//0:临停车 1:预约车 2：租赁车
					obj.put("expect_state", 0);//1:预约超时 2：预约未超时
					if(pay_month_park.getPermit_time() == null){
						
					}
					obj.put("rent_state", 2);//1:租期超时 2：租期未超时  3：不在租期时间段内入场
					returnData.setReturnData(errorcode_success, "获取成功", ""); 
	    			return;
				}
				
	            //返回结果
				obj.put("type", 0);//0:临停车 1:预约车 2：租赁车
				obj.put("expect_state", 0);//1:预约超时 2：预约未超时
				obj.put("rent_state", 0);//1:租期超时 2：租期未超时  3：不在租期时间段内入场
				returnData.setReturnData(errorcode_success, "获取成功", obj); 
				return;
				
			} catch (Exception e) {
				log.error("ParkinfoBiz check_expectcar is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
	}
	
	/**
	 * 获取PDA的预约且未付款和未取消的订单
	 * @param returnData
	 * @param dtype
	 * @param area_code
	 * @param pi_id
	 * @param page
	 * @param size
	 */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void pda_expect_order(ReturnDataNew returnData, int dtype,
			String area_code, long pi_id, int page, int size) {
		// TODO Auto-generated method stub
		try {
			if(page < 1){
				page = 1;
			}
			int start = (page-1)*size;
			//0:普通停车订单
			String sql = "select *  from  pay_park  where pi_id=? and area_code=? and is_del=0 and cancel_state=0 and order_type=1 and pp_state=0 order by ctime desc limit "+start+","+size;
			List<Pay_park> list = getMySelfService().queryListT(sql, Pay_park.class, pi_id,area_code);
			//返回数据
			returnData.setReturnData(errorcode_success, "获取PDA的预约且未付款和未取消的订单", list);  
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.pda_expect_order is error"+e.getMessage(), e);
		}
	}

	
	/**
	 * 扫描到车辆出库扣费
	 * @param returnData
	 * @param dtype
	 * @param orderid
	 * @param pi_id
	 * @param car_code
	 * @param area_code
	 */
	public void payment(ReturnDataNew returnData, int dtype, String orderid,int is_rent,
			long pi_id, String car_code, String area_code) {
		// TODO Auto-generated method stub
		try {
			Date date = new Date();
			//用户ID 通过车牌号获取用户ID 
        	long ui_id = 0;
			User_carcode user_carcode = carBiz.queryUserCarBycode(car_code);
			if(user_carcode != null){
				ui_id = user_carcode.getUi_id();
			}
			//出库 扣款
			/**
			 * 计算出库的账单结算金额
			 * 两种情况 第一种 用户开启了自动扣款 那么就进行自动扣款  
			 * 第二种 用户没有开启自动扣款 那么就只需要计算出来费用然后等待 用户手动去支付
			 */
			int total_money = 0;//计费总金额
			long diff_time = 0;
			int start_price=0;//起步价
			int charging = 0;//计费价
			int hours = 0;//超时时间 单位小时
			
			if(is_rent == 0){
				//临停车辆
				//获取用户还没有支付的最后一条某停车场的订单
				Pay_park pay_park = payParkPB.selectOnePayPark(orderid);
				if(pay_park != null){
					int  is_free = 0; //:0 收费  1：免费
					//是否具有 多少分钟之内进出免费是否开启  0:不开启  1：开启
					if(pay_park.getIs_free_minute() == 1 
							&& date.getTime() - pay_park.getCtime().getTime()  <=  pay_park.getFree_minute() ){
						//开启 且 pay_park.getFree_minute() 分钟内 可以免费进出
						is_free = 1;
					}
					
					
					
					//时间差   = 当前时间 - 入库时间  - 起步时长（分钟数 * 60 *　1000）
					diff_time = date.getTime() - pay_park.getCtime().getTime() - pay_park.getStart_time()*60*1000;
					start_price = pay_park.getStart_price();
					charging = pay_park.getCharging();
					if(diff_time > 0){
						//时间差   = 当前时间 - 入库时间  - 起步时长（分钟数 * 60 *　1000）
						//超时
						//超时时间
						hours = (int)diff_time /(3600*1000) ;
						if(hours < 1){
							//不到一个小时就按一个小时计算
							hours = 1;
						}
						//总计费金额
						total_money = start_price+charging*hours;
					}else{
						//表示 只扣除起步时长
						//总计费金额
						total_money = start_price;
					}	
					//这里查看用户是自动扣费还是手动扣费
					//虚拟币比例1元比100 分 
					User_info userinfo = user_infoDao.selectByKey(ui_id);
		        	try {
		        		//事物处理  该车辆出入的细节
		        		baseTransaction.getCarTransaction().payMent(pay_park, userinfo, total_money, returnData,date,is_free);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("扫描到车辆出库扣费错误",e); 
					}
				}
				
			}else{
				//租赁车辆
				//这里要验证 该车辆是否在该停车场包月
	        	Pay_month_park pay_month_park = payParkPB.queryRentOrder(pi_id,car_code,area_code,ui_id,date);
				if(pay_month_park != null){
					//用户分时间段包月 产生了临停费用
					diff_time = calculateMonthPay(pay_month_park.getPermit_time()
							,pay_month_park.getStart_time(),pay_month_park.getEnd_time(),pay_month_park.getCtime(),date);
					
					start_price = pay_month_park.getOuttime_start_price();
					charging = pay_month_park.getOuttime_charge_money();
					if(diff_time > 0){
						//时间差   = 当前时间 - 入库时间  - 起步时长（分钟数 * 60 *　1000）
						//超时
						//超时时间
						hours = (int)diff_time /(3600*1000) ;
						if(hours < 1){
							//不到一个小时就按一个小时计算
							hours = 1;
						}
						//总计费金额
						total_money = start_price+charging*hours;
						
					//这里查看用户是自动扣费还是手动扣费
					//虚拟币比例1元比100 分 
					User_info userinfo = user_infoDao.selectByKey(ui_id);
					
		        	try {
		        		//事物处理  该车辆出入的细节
		        		baseTransaction.getCarTransaction().payMentRent(pay_month_park, userinfo, total_money , returnData, pi_id,  area_code, car_code, date,diff_time);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("扫描到车辆出库扣费错误",e); 
					}
		        	
					}
				}
			}
			returnData.setReturnData(errorcode_success, "请求成功", ""); 
			return;
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("orderBiz.payment is error"+e.getMessage(), e);
		}
	
	}
/****************************下面是分离出来的方法***************************************/
/**
 * 用户预约车普通位前的检查
 * @param returnData
 * @param ui_id
 * @param pi_id
 * @param expect_money 该次预约需要的钱 （单位分）
 * @return false 检查不通过   true 检查通过
 */
public boolean checkUserOrder(ReturnDataNew returnData,long ui_id,long pi_id,int expect_money,String area_code){
	//首先检查该用户账户上是否钱足够担负的起预约超时扣款
	User_info user_info = user_infoDao.selectByKey(ui_id);
	if(user_info == null){
		//用户不存在
		returnData.setReturnData(errorcode_data, "用户不存在", ""); 
		return false;
	}
	if(user_info.getUi_autopay() == 0){//是否自动支付 ：0 ：不是 1：是
		//没有开启自动扣款
		returnData.setReturnData(errorcode_data, "没有开启自动扣款", ""); 
		return false;
	}
	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	if(park_info == null){
		//该停车场不存在
		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
		return false;
	}
	
	if(user_info.getUi_vc() - expect_money < 0){
		//账户余额不足
		returnData.setReturnData(errorcode_data, "账户余额不足", ""); 
		return false;
	}
	
	return true;
}


/**
 * 用户预约租赁车位前的检查
 * @param returnData
 * @param ui_id
 * @param pi_id
 * @param money 租赁共花费（分）
 * @return false 检查不通过   true 检查通过
 */
public boolean checkUserOrder_rent(ReturnDataNew returnData,long ui_id,long pi_id,int money,String area_code){
	//首先检查该用户账户上是否钱足够担负的起预约超时扣款
	User_info user_info = user_infoDao.selectByKey(ui_id);
	if(user_info == null){
		//用户不存在
		returnData.setReturnData(errorcode_data, "用户不存在", ""); 
		return false;
	}
	if(user_info.getUi_autopay() == 0){//是否自动支付 ：0 ：不是 1：是
		//没有开启自动扣款
		returnData.setReturnData(errorcode_data, "没有开启自动扣款", ""); 
		return false;
	}
	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	if(park_info == null){
		//该停车场不存在
		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
		return false;
	}
	
	if(user_info.getUi_vc() - money < 0){
		//账户余额不足
		returnData.setReturnData(errorcode_data, "账户余额不足", ""); 
		return false;
	}
	
	return true;
}


/**
 * 获取普通订单
 */
public Pay_park QueryByOrderId(String orderid,long ui_id){
	try {
		String sql = "select *  from pay_park where ui_id=? and  my_order=? limit 1";
		return getMySelfService().queryUniqueT(sql, Pay_park.class,ui_id ,orderid);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("获取普通订单 QueryByOrderId(String orderid,long ui_id) is error"+e.getMessage(), e);
	}
	return null; 
}

/**
 * 获取普通订单
 *//*
public Pay_park QueryByOrderId(String orderid){
	try {
		String sql = "select *  from pay_park where   my_order=? limit 1";
		return getMySelfService().queryUniqueT(sql, Pay_park.class ,orderid);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("获取普通订单 QueryByOrderId(String orderid,long ui_id) is error"+e.getMessage(), e);
	}
	return null; 
}*/







/**
 * 获取用户下单
 * @param ui_id 用户ID
 * @param pi_id 车库表主键ID
 * @param car_code 车牌号
 * @param order_type 下单类型 0: 普通下单  1：预约下单
 * @return
 */
public Pay_park 	QueryPayPark(long ui_id,long pi_id,String car_code,int order_type,String area_code){
	    //入库之前先检查该用户对该车辆进行该停车场预约下单没有   如果已经进行了预约下单则直接更新对应得数据
		try {
			Pay_park pay_park = null;
			String sql = "select *  from pay_park where pi_id = ? and area_code=? and car_code=? and ui_id=? and pp_state=0 and  order_type=?  and is_del=0 and cancel_state=0 order by  ctime desc limit 1";
			pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,car_code,ui_id,order_type);
			return pay_park;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("	QueryPayPark(long ui_id,long pi_id,String car_code,int order_type) is error"+e.getMessage(), e); 
		}
		return null;
}









}
