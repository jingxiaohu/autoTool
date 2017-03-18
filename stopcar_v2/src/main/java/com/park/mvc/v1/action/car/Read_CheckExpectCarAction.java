
package com.park.mvc.v1.action.car;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 检查该车牌号是否是预约车
 * @author jingxiaohu
 *
 */
public class Read_CheckExpectCarAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 检查该车牌号是否是预约车或者租赁车
	 * @return
	 */
	@Action(value = "check_expectcar")
	public String check_expectcar(){
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
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,car_code);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.check_expectcar(returnData,area_code,pi_id,car_code,car_type,car_code_color);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_bindcarAction read_bindcar  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
    private String area_code;//省市县编号 140107  
	private long pi_id;//场地主键ID
	private String car_code;//车牌号 (第一期：车牌号---对应一个人)
	private int car_type;//车牌类型:车牌类型 0：未知车牌:、1：蓝牌小汽车、2：: 黑牌小汽车、3：单排黄牌、4：双排黄牌、 5： 警车车牌、6：武警车牌、7：个性化车牌、8：单 排军车牌、9：双排军车牌、10：使馆车牌、11： 香港进出中国大陆车牌、12：农用车牌、13：教 练车牌、14：澳门进出中国大陆车牌、15：双层 武警车牌、16：武警总队车牌、17：双层武警总 队车牌
	private int car_code_color;//车牌颜色 0：未知、1：蓝色、2：黄色、3：白色、 4：黑色、5：绿色
	/************************get set 方法区****************************/


	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public long getPi_id() {
		return pi_id;
	}
	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}
	public String getCar_code() {
		return car_code;
	}
	public void setCar_code(String car_code) {
		this.car_code = car_code;
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
	



	
	
	
}
