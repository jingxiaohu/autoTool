package com.park.v1.biz.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.User_park_coupon;
import com.park.v1.biz.BaseBiz;

/**
 * 普通订单公用方法
 * @author jingxiaohu
 *
 */
@Service
public class PayMonthParkPB extends BaseBiz{
	@Autowired
	protected ParkCouponPB parkCouponPB;
	@Autowired
	private ParkInfoPB parkInfoPB;
	/**
	 * 通过订单编号获取某条订单详情
	 */
	public Pay_month_park selectOnePayMonthPark(String orderid){
		try {
			String sql = "select *  from pay_month_park where my_order=?";
			return getMySelfService().queryUniqueT(sql, Pay_month_park.class,orderid);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			log.error("PayMonthParkPB.selectOnePayMonthPark 通过订单编号获取某条订单详情错误", e);
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
		int hours = 0;//超时时间 单位小时
		if(diff_time > 0){
			//超时
			//超时时间
			hours = (int)diff_time /(3600*1000) ;
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
	 * 第三方回调通知 更改租赁订单扣款成功
	 */
	public Pay_month_park upPayMonthParkNotify(String orderid,String other_orderid,long money){
		try {
			String sql = "select *  from pay_month_park where my_order=? limit 1";
			Pay_month_park pay_month_park =  getMySelfService().queryUniqueT(sql, Pay_month_park.class,orderid);
			if(pay_month_park == null){
				return null;
			} 
			//验证是否金额一致 如果不一致有可能是被抓包  恶意刷我们的钱包
			if(pay_month_park.getMoney() != (int)money){
				//金额不匹配
				return null;
			}
			if(pay_month_park.getPp_state() == 0){
				if(pay_month_park.getUpc_id() > 0){
					//如果使用了优惠券 那么需要更改优惠券的使用状态
					
					User_park_coupon user_park_coupon =  user_park_couponDao.selectByKey(pay_month_park.getUpc_id());
		        	if(user_park_coupon != null){
		        		
		            	if(!parkCouponPB.upUserParkCouponState(user_park_coupon.getUpc_id())){
		    				//更新失败
		    				return null;
		            	}
		        	}
				}
				
				
				pay_month_park.setPp_state(1);
				pay_month_park.setOther_order(other_orderid);
				pay_month_park.setUtime(new Date());
				int count = pay_month_parkDao.updateByKey(pay_month_park);
				if(count == 1){
					//更新成功  （by jxh 2016-11-24 这里需要给商户账户上面资金增加 这块业务放到 车辆出库的地方处理  ）
					return pay_month_park;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("PayParkUtil.upPayMonthParkNotify 第三方回调通知::通过订单编号第三方回调通知 更改租赁订单扣款成功错误", e);
		}
		return null;
	}
	
	/**
	 * 获取某用户对应得某绑定车牌还没有支付的订单
	 */
	public List<Pay_park> selectAllpayMonthParkBYcar_code(long ui_id,String car_code){
		try {
			String sql = "select *  from pay_month_park where ui_id=? and car_code=? and ((pp_state=0  and  is_over=0 and cancel_state=0)  or (pp_state=1 and  UNIX_TIMESTAMP(end_time) - unix_timestamp() >= 0))";
			return getMySelfService().queryListT(sql, Pay_park.class,ui_id,car_code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("PayMonthParkPB.selectAllpayMonthParkBYcar_code 获取某用户对应得某绑定车牌还没有支付的订单错误", e);
		}
		return null;
	}


	/**
	 *  调度处理租赁订单超过了结束时间更新
	 */
	public void upRentOrderOutTime() {
		// TODO Auto-generated method stub
		try {
			String sql = "select *  from pay_month_park where  is_expire=0 and  pp_state=1  and cancel_state=0  and  unix_timestamp()-UNIX_TIMESTAMP(end_time) > 0";
			List<Pay_month_park> list =  getMySelfService().queryListT(sql, Pay_month_park.class);
			if(list != null && list.size() > 0){
				//有预约超时的 订单
				for (Pay_month_park pay_month_park : list) {
					try {
		        		//修改成关闭
						pay_month_park.setIs_expire(1);//是否已到期（0：没有到期 1：已经到期）',
						pay_month_parkDao.updateByKey(pay_month_park);
						//这里更新租赁车辆出入库的数量变化
						sql = "update park_info set time_car_num_space=time_car_num_space+1 where pi_id="+pay_month_park.getPi_id()+"  and  time_car_num > 0 and   time_car_num_space >= 0 and (time_car_num_space+1 <= time_car_num)";
						getMySelfService().execute(sql);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("处理 upRentOrderOutTime  调度处理租赁订单超过了结束时间更新失败",e); 
						return;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("调度处理租赁订单超过了结束时间更新 upRentOrderOutTime() is error"+e.getMessage(), e);
		}
	}
	
}
