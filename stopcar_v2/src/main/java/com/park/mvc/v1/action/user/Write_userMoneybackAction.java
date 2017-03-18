
package com.park.mvc.v1.action.user;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户申诉退费
 * @author jingxiaohu
 *
 */
public class Write_userMoneybackAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 用户申诉退费
	 * @return
	 */
	@Action(value = "usermoneyback")
	public String usermoneyback(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(order_id)){
				// pp_id;//停车下订单表主键ID
				returnData.setReturnData(errorcode_param, " order_id="+order_id+"  order_id is null", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				// pi_id;//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(um_money < 1){
				//um_money;//退款金额(单位 分)
				returnData.setReturnData(errorcode_param, " um_money="+um_money+"  um_money is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code 退款车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(content)){
				//content 申诉内容详情
				content  = URLDecoder.decode(content, Constants.SYSTEM_CHARACTER);
			}
			
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,car_code);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			carBiz.usermoneyback(returnData,ui_id,order_id,pi_id,um_money,car_code,um_state,check_state,admin_userid,is_rent,area_code,type,content);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_userMoneybackAction usermoneyback  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String order_id;//停车下订单表主键ID
	private long pi_id;//停车场主键ID
	private int um_money;//退款金额(单位 分)
	private String car_code;//退款车牌号
	private int um_state;//退款状态 0：未退款 1：已退款
	private int check_state;//审核状态 0：未审核 1：已审核
	private long admin_userid;//审核人后台管理表主键ID
	private int is_rent;//是否是租赁订单 0：临停订单  1：预约临停订单  2：租赁包月订单
	private String area_code;//地区区域编号
	private int type;//申诉类型 0：临停扣款问题 1：预约超时扣费问题  2：其它
	private String content;//申诉原因
	/************************get set 方法区****************************/


	public String getCar_code() {
		return car_code;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}
	public long getPi_id() {
		return pi_id;
	}
	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}
	public int getUm_money() {
		return um_money;
	}
	public void setUm_money(int um_money) {
		this.um_money = um_money;
	}
	public int getUm_state() {
		return um_state;
	}
	public void setUm_state(int um_state) {
		this.um_state = um_state;
	}
	public int getCheck_state() {
		return check_state;
	}
	public void setCheck_state(int check_state) {
		this.check_state = check_state;
	}
	public long getAdmin_userid() {
		return admin_userid;
	}
	public void setAdmin_userid(long admin_userid) {
		this.admin_userid = admin_userid;
	}
	public int getIs_rent() {
		return is_rent;
	}
	public void setIs_rent(int is_rent) {
		this.is_rent = is_rent;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}



	



	
	
	
}
