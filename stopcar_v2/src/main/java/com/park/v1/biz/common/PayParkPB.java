package com.park.v1.biz.common;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.Transaction.BaseTransaction;
import com.park.bean.Car_in_out;
import com.park.bean.Park_info;
import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.Rental_charging_rule;
import com.park.bean.User_park_coupon;
import com.park.bean.User_pay;
import com.park.exception.QzException;
import com.park.v1.biz.BaseBiz;

/**
 * 普通订单公用方法
 * @author jingxiaohu
 *
 */
@Service
public class PayParkPB extends BaseBiz{
	@Autowired
	protected ParkCouponPB parkCouponPB;
	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	/**
	 * 通过订单编号获取某条订单详情
	 */
	public Pay_park selectOnePayPark(String orderid){
		try {
			String sql = "select *  from pay_park where my_order=?";
			return getMySelfService().queryUniqueT(sql, Pay_park.class,orderid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("PayParkUtil.selectOnePayPark 通过订单编号获取某条订单详情错误", e);
		}
		return null;
	}
	
	
	/**
	 * 获取当前临停车费用
	 */
	public int returnCarMoney(Pay_park pay_park){
		Date date = new Date();
		//时间差 
		long diff_time = date.getTime() - pay_park.getCtime().getTime() - pay_park.getStart_time()*60*1000;
		pay_park.setFinal_time((int)diff_time/(60*1000));
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
			if(hours < 1){
				//不到一个小时就按一个小时计算
				hours = 1;
			}
		}
		//总计费金额
		int total_money = pay_park.getStart_price()+pay_park.getCharging()*hours;
		return total_money;
	}
	
	
	
	/**
	 * 用户充值::通过订单编号获取某条用户充值订单详情
	 */
	public User_pay selectOneUserPay(String orderid){
		try {
			String sql = "select *  from user_pay where order_id=? limit 1";
			return getMySelfService().queryUniqueT(sql, User_pay.class,orderid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("PayParkUtil.selectOneUserPay 用户充值::通过订单编号获取某条用户充值订单详情错误", e);
		}
		return null;
	}
	
	/**
	 * 第三方回调通知 更改普通订单扣款成功
	 */
	public Pay_park upPayParkNotify(String orderid,String other_orderid,long money) {
		try {
			String sql = "select *  from pay_park where my_order=? limit 1";
			Pay_park pay_park =  getMySelfService().queryUniqueT(sql, Pay_park.class,orderid);
			if(pay_park == null){
				return null;
			}
			//验证是否金额一致 如果不一致有可能是被抓包  恶意刷我们的钱包
			if(pay_park.getMoney() != (int)money){
				//金额不匹配
				return null;
			}
			
			if(pay_park.getPp_state() == 0){
				if(pay_park.getUpc_id() > 0){
					//如果使用了优惠券 那么需要更改优惠券的使用状态
					
					User_park_coupon user_park_coupon =  user_park_couponDao.selectByKey(pay_park.getUpc_id());
		        	if(user_park_coupon != null){
		        		
		            	if(!parkCouponPB.upUserParkCouponState(user_park_coupon.getUpc_id())){
		    				//更新失败
		            		//throw new QzException("record_user_vc_act 优惠券更新使用状态失败");
		    				return null;
		            	}
		        	}
				}
				
				
				pay_park.setPp_state(1);
				pay_park.setOther_order(other_orderid);
				pay_park.setUtime(new Date());
				int count = pay_parkDao.updateByKey(pay_park);
				if(count == 1){
					//更新成功  （by jxh 2016-11-24 这里需要给商户账户上面资金增加 这块业务放到 车辆出库的地方处理  ）
					return pay_park;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("PayParkUtil.upPayParkNotify 第三方回调通知::通过订单编号第三方回调通知 更改普通订单扣款成功错误", e);
		}
		return null;
	}
	
	
	
	/**
	    * 获取用户下单
	    * @param ui_id 用户ID
	    * @param pi_id 车库表主键ID
	    * @param car_code 车牌号
	    * @param order_type 下单类型 0: 普通下单  1：预约下单 2：租赁包月订单
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
	   
	   
	   /**
	    * 获取用户还没有支付的最后一条某停车场的订单
	    * @param ui_id 用户ID
	    * @param pi_id 车库表主键ID
	    * @param car_code 车牌号
	    * @return
	    */
	   public Pay_park 	QueryPayPark(long ui_id,long pi_id,String car_code,String area_code){
		    //入库之前先检查该用户对该车辆进行该停车场预约下单没有   如果已经进行了预约下单则直接更新对应得数据
			try {
				Pay_park pay_park = null;
				String sql = "select *  from pay_park where pi_id = ? and  area_code=? and car_code=? and ui_id=? and pp_state=0 and cancel_state=0  and is_del=0 order by  ctime desc limit 1";
				pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,car_code,ui_id);
				return pay_park;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("获取用户下单全部	QueryPayPark(long ui_id,long pi_id,String car_code) is error"+e.getMessage(), e); 
			}
			return null;
	   }
		/**
		 * 摄像头扫描到下单
		 */
	    public void cameraCarOrder(Car_in_out car_in_out, Park_info park_info) throws QzException{
	    	try {
	    		String area_code = park_info.getArea_code();
	    		long ui_id = car_in_out.getUi_id();
				//车牌号
				String car_code = car_in_out.getCar_code();
				//获取该停车场的计费规则 rcr_type ：停车类型 0：普通车位停车 1：时间段包月停车
				Rental_charging_rule  charging_rule =  queryChargeRule( car_in_out.getPi_id(),0,car_in_out.getCar_type(),area_code,car_in_out.getCar_code_color());
				if(charging_rule == null){
					//下单失败
					return ;
				}
				Date date  = new Date();
				Pay_park pay_park = new Pay_park();
				pay_park.setStart_price(charging_rule.getStart_price());//起步价格（单位 分）
				pay_park.setStart_time(charging_rule.getStart_time());//起步时长（分钟）
				pay_park.setCharging(charging_rule.getCharging());//计费价格(单位 分)
				pay_park.setCharging_time(charging_rule.getCharging_time());//计费时长(分钟)
				
				pay_park.setAllege_state(0);//申述状态 0:未申述 1：已申述
				pay_park.setArrive_time(date);//到场时间
//	    	    pay_park.setLeave_time(date);//离场时间
				pay_park.setCar_code(car_code);//车牌号
				pay_park.setCtime(date);//创建时间
				pay_park.setMy_order(returnUUID());//我们自己生成的订单号
				pay_park.setOrder_type(0);//下单类型 0: 普通下单  1：预约下单 2：租赁包月订单
//	    	    pay_park.setOther_order(value);//第三方的 支付单号
//	    	    pay_park.setPay_source(value);//支付类型 1:支付宝 2：微信 3：银联
//	    	    pay_park.setPay_type(value);//支付类型 0:手动输入密码支付 1：快捷支付（服务器可以请求第三方直接扣款）
//	    	    pay_park.setPhone_type(value);//手机类型 0:android 1：IOS
				pay_park.setPi_id(car_in_out.getPi_id());//支付停车场主键ID
				pay_park.setPp_state(0);//支付状态 0:未支付 1：已经支付
//	    	    pay_park.setPp_versioncode(value);//当前APPSDK版本号 （内部升级版本代号）
				pay_park.setUi_id(ui_id);//用户ID
				pay_park.setUtime(date);//更新时间
//	    	    pay_park.setExpect_info(value);//预定提示信息
//	       	    pay_park.setExpect_money(value);//预定价格
//	    	    pay_park.setExpect_time(value);//预定时间
//	    	    pay_park.setMoney(value);//支付金额（单位 分）
//				pay_park.setNote("自动下订单"); 
				pay_park.setAddress_name(park_info.getAddress_name());//停车场地址
				pay_park.setPi_name(park_info.getPi_name());//停车场名称
				pay_park.setArea_code(area_code);//省市县编号
				pay_park.setPark_type(park_info.getPark_type());
				
				//省市县区域代码
				pay_park.setArea_code(car_in_out.getArea_code());
				
				
				pay_park.setFree_minute(charging_rule.getFree_minute());//多少分钟之内进出免费
    			pay_park.setIs_free_minute(charging_rule.getIs_free_minute());//多少分钟之内进出免费是否开启  0:不开启  1：开启
				
    			
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
				}
				//设置车辆进入时候的 订单 
				car_in_out.setOrder_id(pay_park.getMy_order());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("ParkinfoBiz.cameraCarOrder is error"+e.getMessage(), e);
				throw new QzException("摄像头扫描到下单 失败", e);
			}

	    	
	    }
	    
	    
	    
	    
	    
		/**
		 * 租赁分时间段包月--产生了临停订单
		 */
	    public Pay_park MakeAutoOrder(long pi_id,String area_code,String car_code,long ui_id,long difftime) throws QzException{
	    	try {
	    		//获取该车辆在该停车场的入库信息
	    		String sql = "select * from car_in_out where pi_id=? and area_code=? and car_code=? and is_rent=1 and is_enter=0 order by ctime desc limit 1";
	    		Car_in_out car_in_out = getMySelfService().queryUniqueT(sql, Car_in_out.class, pi_id,area_code,car_code);
	    		if(car_in_out == null){
	    			return null;
	    		}
	    		//获取停车场基本信息
	    		Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	        	if(park_info == null ){
	    			return null;
	        	}
	    		
				//车牌号
				//获取该停车场的计费规则 rcr_type ：停车类型 0：普通车位停车 1：时间段包月停车
				Rental_charging_rule  charging_rule =  queryChargeRule( car_in_out.getPi_id(),0,car_in_out.getCar_type(),area_code,car_in_out.getCar_code_color());
				if(charging_rule == null){
					//下单失败
					return null;
				}
				
				
				Date date  = new Date();
				Pay_park pay_park = new Pay_park();
				pay_park.setStart_price(charging_rule.getStart_price());//起步价格（单位 分）
				pay_park.setStart_time(charging_rule.getStart_time());//起步时长（分钟）
				pay_park.setCharging(charging_rule.getCharging());//计费价格(单位 分)
				pay_park.setCharging_time(charging_rule.getCharging_time());//计费时长(分钟)
				
				pay_park.setAllege_state(0);//申述状态 0:未申述 1：已申述
				Calendar cl = Calendar.getInstance(Locale.CHINA);
				cl.setTimeInMillis(date.getTime() - difftime);;
				pay_park.setArrive_time(cl.getTime());//到场时间==== 租赁分时段包月产生临停费用  到达时间设置为 当前时间减去 超时时间
//	    	    pay_park.setLeave_time(date);//离场时间
				pay_park.setCar_code(car_code);//车牌号
				pay_park.setCtime(date);//创建时间
				pay_park.setMy_order(returnUUID());//我们自己生成的订单号
				pay_park.setOrder_type(0);//下单类型 0: 普通下单  1：预约下单 2：租赁包月订单
//	    	    pay_park.setOther_order(value);//第三方的 支付单号
//	    	    pay_park.setPay_source(value);//支付类型 1:支付宝 2：微信 3：银联
//	    	    pay_park.setPay_type(value);//支付类型 0:手动输入密码支付 1：快捷支付（服务器可以请求第三方直接扣款）
//	    	    pay_park.setPhone_type(value);//手机类型 0:android 1：IOS
				pay_park.setPi_id(car_in_out.getPi_id());//支付停车场主键ID
				pay_park.setPp_state(0);//支付状态 0:未支付 1：已经支付
//	    	    pay_park.setPp_versioncode(value);//当前APPSDK版本号 （内部升级版本代号）
				pay_park.setUi_id(ui_id);//用户ID
				pay_park.setUtime(date);//更新时间
//	    	    pay_park.setExpect_info(value);//预定提示信息
//	       	    pay_park.setExpect_money(value);//预定价格
//	    	    pay_park.setExpect_time(value);//预定时间
//	    	    pay_park.setMoney(value);//支付金额（单位 分）
//				pay_park.setNote("自动下订单"); 
				pay_park.setAddress_name(park_info.getAddress_name());//停车场地址
				pay_park.setPi_name(park_info.getPi_name());//停车场名称
				pay_park.setArea_code(area_code);//省市县编号
				pay_park.setPark_type(park_info.getPark_type());
				
				//省市县区域代码
				pay_park.setArea_code(car_in_out.getArea_code());
				
				pay_park.setFree_minute(charging_rule.getFree_minute());//多少分钟之内进出免费
    			pay_park.setIs_free_minute(charging_rule.getIs_free_minute());//多少分钟之内进出免费是否开启  0:不开启  1：开启
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
				}
				return pay_park;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("ParkinfoBiz.cameraCarOrder is error"+e.getMessage(), e);
				throw new QzException("摄像头扫描到下单 失败", e);
			}
	    }
	    
	    
	    
		/**
		 * 查询场地规则数据
		 * @param pi_id
		 * @param rcr_type ：停车类型 0：普通车位停车 1：时间段包月停车
		 * @param car_type 车牌类型 0：未知车牌:、1：蓝牌小汽车、2：: 黑牌小汽车、3：单排黄牌、4：双排黄牌、 5： 警车车牌、6：武警车牌、7：个性化车牌、8：单 排军车牌、9：双排军车牌、10：使馆车牌、11： 香港进出中国大陆车牌、12：农用车牌、13：教 练车牌、14：澳门进出中国大陆车牌、15：双层 武警车牌、16：武警总队车牌、17：双层武警总 队车牌
		 * @param car_code_color      车牌颜色 0：未知、1：蓝色、2：黄色、3：白色、 4：黑色、5：绿色
		 * @return
		 */
		public Rental_charging_rule queryChargeRule(long pi_id,int rcr_type,int car_type,String area_code,int car_code_color){
			try {
//				String sql = "select *  from  rental_charging_rule where pi_id=? and area_code=? and rcr_state=0 and rcr_type=? and car_code_color=? and car_type=? limit 1";
//				Rental_charging_rule cr = getMySelfService().queryUniqueT(sql, Rental_charging_rule.class,pi_id,area_code,rcr_type,car_code_color,car_type);
				String sql = "select *  from  rental_charging_rule where pi_id=? and area_code=? and rcr_state=0 and rcr_type=? and car_code_color=? limit 1";
				Rental_charging_rule cr = getMySelfService().queryUniqueT(sql, Rental_charging_rule.class,pi_id,area_code,rcr_type,car_code_color);
				
				return cr;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("queryRentChargeRuleByPiId is error "+e.getMessage(), e);
			}
			return null; 
		}
		
		/**
		 * 获取某车辆在某停车场最近一次的未到期的租赁订单
		 */
		public Pay_month_park queryRentOrder(long pi_id,String car_code,String area_code,long ui_id,Date arrive_time){
			try {
				String sql = "select * from pay_month_park  where pi_id=? and car_code=? and area_code=? and pp_state=1 and ui_id=? and end_time>? order by ctime desc limit 1";
				return		getMySelfService().queryUniqueT(sql, Pay_month_park.class, pi_id,car_code ,area_code,ui_id,arrive_time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("queryRentOrder is error "+e.getMessage(), e);
			}
			return null;
		}
/****************************下面是任务调度器处理的工具方法**************************************/		
		/**
		 * 从普通订单表里面获取 预约订单 超时的  进行扣款处理
		 */
		public void upExpectOrderOutTime(){
			try {
				String sql = "select *  from pay_park where  pp_state=0  and cancel_state=0  and order_type=1  and  unix_timestamp()-UNIX_TIMESTAMP(ctime)>expect_time*60";
				List<Pay_park> list =  getMySelfService().queryListT(sql, Pay_park.class);
				if(list != null && list.size() > 0){
					//有预约超时的 订单
					for (Pay_park pay_park : list) {
						//处理扣款等
						try {
			        		//事物处理  
			        		baseTransaction.getCarTransaction().ExpectOrderCheckTask(pay_park);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.error("事物处理 ExpectOrderCheckTask  预付款超时扣款失败",e); 
							return;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("从普通订单表里面获取 预约订单 超时的  进行扣款处理 upExpectOrderOutTime() is error"+e.getMessage(), e);
			}
		}
		

		
		/**
		 * 获取某用户对应得某绑定车牌还没有支付的订单
		 */
		public List<Pay_park> selectAllPayParkBYcar_code(long ui_id,String car_code){
			try {
				String sql = "select *  from pay_park where ui_id=? and car_code=? and pp_state=0  and  is_over=0 and cancel_state=0";
				return getMySelfService().queryListT(sql, Pay_park.class,ui_id,car_code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("PayParkPB.selectAllPayParkBYcar_code 获取某用户对应得某绑定车牌还没有支付的订单错误", e);
			}
			return null;
		}
}
