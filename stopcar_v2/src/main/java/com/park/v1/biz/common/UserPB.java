package com.park.v1.biz.common;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.park.bean.Park_userinfo;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_info;
import com.park.bean.User_vc_act;
import com.park.exception.QzException;
import com.park.v1.biz.BaseBiz;

/**
 * 用户公用方法
 * @author jingxiaohu
 *
 */
@Service
public class UserPB extends BaseBiz{
	/**
	 * 变更用户钱包金额
	 * @param ui_id
	 * @param ui_nd
	 * @param type 0:新增 1:减少
	 * @param money
	 * @return
	 */
	public User_info updateUserMoney(long ui_id,String ui_nd,int type,long money){
		try {
			User_info user_info = user_infoDao.selectByKey(ui_id);
			if(user_info == null || money < 1){
				return null;
			}
			if(type == 0){
				//增加
				user_info.setUi_vc(user_info.getUi_vc() + money);
			}else{
				//减少
				if(user_info.getUi_vc() - money >= 0){
					user_info.setUi_vc(user_info.getUi_vc() - money);
				}else{
					return null;
				}
			}
			int count = user_infoDao.updateByKey(user_info);
			if(count < 1){
				return null;
			}
			return user_info;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("UserPB.updateUserMoney 变更用户钱包金额", e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param act_type 用户行为  0：订单支付  1：充值
	 * @param money
	 * @param order_id
	 * @param order_type
	 * @param ui_id
	 * @param returnData
	 * @throws QzException
	 */
	public void recordVC(int act_type,int money,String order_id,int order_type,long ui_id,ReturnDataNew returnData) throws QzException{
    	try {
			//记录该次用户虚拟币更改记录
			User_vc_act  va = new User_vc_act();
			Date date = new Date();
			va.setAct_type(act_type);//用户行为  0：订单支付  1：暂定
			va.setIs_add(act_type);//增加还是减少  0：减少  1：增加
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
	
	
	/**
	 * 更新商户账户金额
	 * @param type 0:现金  1：线上
	 * @throws QzException
	 */
	public void upManchentMoney(long pu_id,String pu_nd,int money,int type) throws QzException{
    	try {
    		Date date = new Date();
    		Park_userinfo park_userinfo = park_userinfoDao.selectByKey(pu_id);
    		if(park_userinfo == null || money < 1){
    			return;
    		}
    		if(type == 0){
    			//现金
    			park_userinfo.setMoney_offline(park_userinfo.getMoney_offline()+money); 
    		}else{
    			//线上
        		park_userinfo.setMoney(park_userinfo.getMoney()+money);
        		park_userinfo.setMoney_online(park_userinfo.getMoney()+money);
    		}
    		park_userinfo.setUtime(date);
			int count = park_userinfoDao.updateByKey(park_userinfo);
			if(count < 1){
				//更新失败
				throw new QzException("upManchentMoney 更新商户账户金额失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new QzException("upManchentMoney 更新商户账户金额失败",e);
		}
	}
	
}
