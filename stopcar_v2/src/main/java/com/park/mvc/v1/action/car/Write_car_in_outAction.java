
package com.park.mvc.v1.action.car;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 车辆入库出库记录
 * @author jingxiaohu
 *
 */
public class Write_car_in_outAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 车辆入库出库记录
	 * @return
	 */
	@Action(value = "record_car_in_out")
	public String record_car_in_out(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				//场地主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(in_out)){
				//in_out;//出口或者入口 入口：enter  出口：exit
				returnData.setReturnData(errorcode_param, " in_out="+in_out+"  in_out is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(in_out_code)){
				//in_out_code;//出入口编号  例如(A出口 B入口)
				returnData.setReturnData(errorcode_param, " in_out_code="+in_out_code+"  in_out_code is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code;//车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			if(RequestUtil.checkObjectBlank(is_enter)){
				//is_enter;//入库或者出库 ：0：   入库 1：出库
				returnData.setReturnData(errorcode_param, " is_enter="+is_enter+"  is_enter is null", null);
				sendResp(returnData);
				return null;
			}
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,in_out);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.record_car_in_out(returnData,pi_id,pd_id,car_code,is_enter,in_out,in_out_code,car_type,car_code_color,area_code,out_type,is_local_month);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_car_in_outAction record_car_in_out  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	/**
	 * 开闸----该接口是否废弃需要  郑虎确认  暂时废弃掉
	 * @return
	 */
	@Action(value = "open_signo")
	public String open_signo(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				//场地主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code;//车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,is_cash);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.open_signo(returnData,pi_id,car_code,car_type,car_code_color,area_code,is_cash);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_car_in_outAction open_signo 开闸  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String area_code;//省市县编号 140107  
	
	private long pi_id;//场地主键ID
	private long pd_id;//出入口设备主键ID
	private String car_code;//车牌号 (第一期：车牌号---对应一个人)
	private int is_enter;//入库或者出库 ：0：   入库   1：出库
	private String in_out;//出口或者入口 入口：enter  出口：exit
	private String in_out_code;//出入口编号 A /B/C
	private int car_type;//车牌类型:车牌类型 0：未知车牌:、1：蓝牌小汽车、2：: 黑牌小汽车、3：单排黄牌、4：双排黄牌、 5： 警车车牌、6：武警车牌、7：个性化车牌、8：单 排军车牌、9：双排军车牌、10：使馆车牌、11： 香港进出中国大陆车牌、12：农用车牌、13：教 练车牌、14：澳门进出中国大陆车牌、15：双层 武警车牌、16：武警总队车牌、17：双层武警总 队车牌
	private int car_code_color;//车牌颜色 0：未知、1：蓝色、2：黄色、3：白色、 4：黑色、5：绿色
	private int out_type;//出库类型 0:正常出库 1：现金支付出库 2：异常出库
	private int is_local_month;//是否是道闸本地包月车辆 0:不是 1：是
	//开闸
	private int is_cash;//是否现金支付 0：在线支付  1：现金支付
	/************************get set 方法区****************************/

	public long getPi_id() {
		return pi_id;
	}






	public int getIs_local_month() {
		return is_local_month;
	}



	public void setIs_local_month(int is_local_month) {
		this.is_local_month = is_local_month;
	}



	public int getOut_type() {
		return out_type;
	}



	public void setOut_type(int out_type) {
		this.out_type = out_type;
	}



	public int getIs_cash() {
		return is_cash;
	}



	public void setIs_cash(int is_cash) {
		this.is_cash = is_cash;
	}



	public String getArea_code() {
		return area_code;
	}



	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}



	public int getCar_type() {
		return car_type;
	}



	public void setCar_type(int car_type) {
		this.car_type = car_type;
	}



	public int getCar_code_color() {
		return car_code_color;
	}



	public void setCar_code_color(int car_code_color) {
		this.car_code_color = car_code_color;
	}



	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public String getIn_out() {
		return in_out;
	}


	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}


	public String getIn_out_code() {
		return in_out_code;
	}


	public void setIn_out_code(String in_out_code) {
		this.in_out_code = in_out_code;
	}






	public String getCar_code() {
		return car_code;
	}



	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}



	public int getIs_enter() {
		return is_enter;
	}



	public void setIs_enter(int is_enter) {
		this.is_enter = is_enter;
	}



	public long getPd_id() {
		return pd_id;
	}



	public void setPd_id(long pd_id) {
		this.pd_id = pd_id;
	}


	



	
	
	
}
