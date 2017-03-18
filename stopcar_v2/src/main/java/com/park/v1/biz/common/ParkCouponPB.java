package com.park.v1.biz.common;

import org.springframework.stereotype.Service;

import com.park.bean.User_park_coupon;
import com.park.v1.biz.BaseBiz;

/**
 * 优惠券公用方法
 * @author jingxiaohu
 *
 */
@Service
public class ParkCouponPB extends BaseBiz{
	
	/**
	 * 更新优惠券使用状态 为已经使用
	 * @param upc_id :用户优惠券主键ID
	 * @param upc_state : 使用状态 0：未使用 1：已使用
	 * @return
	 */
	public boolean upUserParkCouponState(long upc_id){ 
		try {
			User_park_coupon user_park_coupon = user_park_couponDao.selectByKey(upc_id);
			if(user_park_coupon != null){
				//upc_state : 使用状态 0：未使用 1：已使用
				user_park_coupon.setUpc_state(1);
				int count = user_park_couponDao.updateByKey(user_park_coupon);
				if(count == 1){
					//更新成功
					return true;
				}
			}
			
			return false;	
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkInfoPB.upUserParkCouponState is error", e); 
		}
		return false;
	}
	
	

}
