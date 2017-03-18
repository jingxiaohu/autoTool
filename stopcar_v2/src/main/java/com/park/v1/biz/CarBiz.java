
package com.park.v1.biz;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.park.DataSource.DynamicDataSourceHolder;
import com.park.DataSource.TargetDataSource;
import com.park.Transaction.BaseTransaction;
import com.park.bean.Park_heartbeat;
import com.park.bean.Park_info;
import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.ReturnDataNew;
import com.park.bean.User_carcode;
import com.park.bean.User_info;
import com.park.bean.User_moneyback;
import com.park.core.Constants;
import com.park.util.FileUtil;
import com.park.util.RequestUtil;
import com.park.v1.biz.common.PayMonthParkPB;
import com.park.v1.biz.common.PayParkPB;

/**
 * 处理车辆信息的业务逻辑管理类
 * @author jingxiaohu
 *
 */
@Service
public class CarBiz extends BaseBiz{

	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	@Autowired
	private PayParkPB payParkPB;
	@Autowired
	private PayMonthParkPB payMonthParkPB;
	/**
	 * 用户添加绑定车牌号
	 * @param returnData
	 * @param ui_id
	 * @param car_code
	 */
	public void bindcar(ReturnDataNew returnData, long ui_id, String car_code,int act) {
		// TODO Auto-generated method stub
        try {
        	//首先判断该量车是否已经被绑定了
        	User_carcode user_carcode  =  queryUserCarBycode(car_code);
        	
        	
        	//act 1:添加车牌 2：删除车牌
        	if(act == 2){
        		//删除该车牌绑定
        		if(user_carcode != null){
        			//车牌在订单中使用可以删除该使用的车牌号（出库需扫车牌号，所以不能删除）
        			List<Pay_park> list1 =  payParkPB.selectAllPayParkBYcar_code(ui_id, car_code);
        			if(list1 != null && list1.size() > 0){
        				returnData.setReturnData(errorcode_data, "还有未支付的临停订单，不允许删除该车牌绑定,亲!", ""); 
            			return;
        			}
        			List<Pay_park> list2 =  payMonthParkPB.selectAllpayMonthParkBYcar_code(ui_id, car_code);
        			if(list2 != null && list2.size() > 0){
        				returnData.setReturnData(errorcode_data, "还有未支付的租赁订单，不允许删除该车牌绑定,亲!", ""); 
            			return;
        			}
        			
        			int count = user_carcodeDao.deleteByKey(user_carcode.getUc_id());
        			if(count != 1){
        				returnData.setReturnData(errorcode_data, "删除该车牌绑定失败", ""); 
            			return;
        			}
        		}
        		returnData.setReturnData(errorcode_success, "删除该车牌绑定成功", ""); 
    			return;
        	}
        	
        	if(user_carcode != null ){
        		if(user_carcode.getUi_id() == ui_id){
        			returnData.setReturnData(errorcode_data, "您已经绑定了该车牌号了", ""); 
        			return;
        		}
        		
        		returnData.setReturnData(errorcode_data, "该车牌号已经被绑定了,如果您的车牌被他（她）绑定，可以去申述", ""); 
    			return;
        	}
        	//入库操作
        	Date date = new Date();
        	user_carcode =  new User_carcode();
        	user_carcode.setCtime(date);
        	user_carcode.setUtime(date);
        	user_carcode.setUi_id(ui_id);
        	user_carcode.setCar_code(car_code);
        	int id = user_carcodeDao.insert(user_carcode);
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "绑定失败", ""); 
    			return;
        	}
        	user_carcode.setUc_id(id);
			returnData.setReturnData(errorcode_success, "绑定成功", ""); 
			return;
			
		} catch (Exception e) {
			log.error("CarBiz bindcar is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
    /**
     * 用户申诉退费
     * @param returnData
     * @param ui_id
     * @param order_id
     * @param pi_id
     * @param um_money
     * @param car_code
     * @param um_state
     * @param check_state
     * @param admin_userid
     */
	public void usermoneyback(ReturnDataNew returnData, long ui_id, String order_id,
			long pi_id, int um_money, String car_code, int um_state,
			int check_state, long admin_userid,int is_rent,String area_code,int type,String content) {
		// TODO Auto-generated method stub
        try {
        	Date date = new Date();
        	int money = 0;
        	//首先判断用户是否存在
        	User_info user_info  =  user_infoDao.selectByKey(ui_id);
        	if(user_info == null ){
        		returnData.setReturnData(errorcode_data, "用户不存在", ""); 
    			return;
        	}
        	if(is_rent == 2){
        		//租赁订单
        		Pay_month_park  pay_month_park = payMonthParkPB.selectOnePayMonthPark(order_id);
        		if(pay_month_park == null){
            		//该订单不存在
            		returnData.setReturnData(errorcode_data, "该订单不存在", ""); 
        			return;
            	}
        		//超过24小时不能进行申诉
        		if(date.getTime() -  pay_month_park.getCtime().getTime() > 24*60*60*1000){
        			//超过24小时不能进行申诉
            		returnData.setReturnData(errorcode_data, "超过24小时不能进行申诉", ""); 
        			return;
        		}
        		
        		
        		money = pay_month_park.getMoney();
        		
            	if(pay_month_park.getPp_state() == 0){
            		//必须是已经付款了的订单才能进行申述
            		returnData.setReturnData(errorcode_data, "该订单还没有进行扣款，申述无效", ""); 
        			return;
            	}
            	if(pay_month_park.getAllege_state() == 1){
            		//已经申述
            		returnData.setReturnData(errorcode_data, "该订单你已申述", ""); 
        			return;
            	}
            	//申述状态0:未申述1：申述中2：申述失败3：申述成功
            	pay_month_park.setAllege_state(1);
            	int count = pay_month_parkDao.updateByKey(pay_month_park);
            	if(count < 1){
            		returnData.setReturnData(errorcode_data, "申述失败", ""); 
        			return;
            	}
        	}else{
        		//临停订单
            	//检查该订单是否已经被该用户申述过了
            	Pay_park pay_park =  payParkPB.selectOnePayPark(order_id);
            	if(pay_park == null){
            		//该订单不存在
            		returnData.setReturnData(errorcode_data, "该订单不存在", ""); 
        			return;
            	}
        		//超过24小时不能进行申诉
        		if(date.getTime() -  pay_park.getCtime().getTime() > 24*60*60*1000){
        			//超过24小时不能进行申诉
            		returnData.setReturnData(errorcode_data, "超过24小时不能进行申诉", ""); 
        			return;
        		}
            	money = pay_park.getMoney();
            	
            	if(pay_park.getPp_state() == 0){
            		//必须是已经付款了的订单才能进行申述
            		returnData.setReturnData(errorcode_data, "该订单还没有进行扣款，申述无效", ""); 
        			return;
            	}
            	if(pay_park.getAllege_state() == 1){
            		//已经申述
            		returnData.setReturnData(errorcode_data, "该订单你已申述", ""); 
        			return;
            	}
            	//申述状态0:未申述1：申述中2：申述失败3：申述成功
            	pay_park.setAllege_state(1);
            	int count = pay_parkDao.updateByKey(pay_park);
            	if(count < 1){
            		returnData.setReturnData(errorcode_data, "申述失败", ""); 
        			return;
            	}
        	}
        	
        	

        	//入库操作
        	User_moneyback user_moneyback =  new User_moneyback();
        	user_moneyback.setAdmin_userid(admin_userid);
        	user_moneyback.setCar_code(car_code);
        	user_moneyback.setCheck_state(check_state);
        	user_moneyback.setPi_id(pi_id);
        	user_moneyback.setOrder_id(order_id);
        	user_moneyback.setUi_id(ui_id);
        	user_moneyback.setUm_money(money);
        	user_moneyback.setUm_state(um_state);
        	user_moneyback.setCtime(date);
        	user_moneyback.setUtime(date);
        	user_moneyback.setArea_code(area_code);
        	user_moneyback.setType(type);
        	user_moneyback.setContent(content);
        	user_moneyback.setIs_rent(is_rent);
        	int id = user_moneybackDao.insert(user_moneyback); 
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "用户申述失败", ""); 
    			return;
        	}
        	user_moneyback.setUm_id(id);
			returnData.setReturnData(errorcode_success, "用户申述成功", ""); 
			return;
			
		} catch (Exception e) {
			log.error("CarBiz usermoneyback is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	
    /**
     * 读取用户绑定的车牌号
     * @param returnData
     * @param ui_id
     */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void read_bindcar(ReturnDataNew returnData, long ui_id) {
		// TODO Auto-generated method stub
        try {
        	//首先判断用户是否存在
        	User_info user_info  =  user_infoDao.selectByKey(ui_id);
        	if(user_info == null ){
        		returnData.setReturnData(errorcode_data, "用户不存在", ""); 
    			return;
        	}
        	//查询该用户所绑定的车牌号码
        	List<User_carcode> list = QueryUserCarCode(ui_id);
        	
			returnData.setReturnData(errorcode_success, "读取用户绑定的车牌号成功", list); 
			return;
			
		} catch (Exception e) {
			log.error("CarBiz read_bindcar is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
		
	}
	
	   /**
	    * 更新用户车辆信息
	    * @param returnData
	    * @param ui_id
	    * @param car_code
	    * @param uc_id
	    * @param lienceFile
	    * @param lienceFileName
	    * @param car_brand
	    * @param uc_color
	    */
		public void update_car(ReturnDataNew returnData, long ui_id,
				String car_code, long uc_id, File lience,
				String lienceFileName,String lienceContentType, String car_brand, int uc_color) {
			// TODO Auto-generated method stub
	        try {
	        	//首先判断是否已经绑定了该量车
	        	User_carcode user_carcode  =  queryUserCar( ui_id, car_code);
	        	if(user_carcode == null){
	        		//您未绑定该车牌号
	        		returnData.setReturnData(errorcode_data, "您未绑定该车牌号", ""); 
	    			return;
	        	}
	        	if(!RequestUtil.checkObjectBlank(car_brand)){
					//品牌
	        		user_carcode.setCar_brand(car_brand);
				}
	        	if(uc_color > 0){
	        		//颜色
	        		user_carcode.setUc_color(uc_color);	
	        	}
				if(!RequestUtil.checkObjectBlank(lience) && !RequestUtil.checkObjectBlank(lienceFileName)){
					//行驶证
					//上传文件
					String url = FileUtil.uploadScaleAvatarImage(lience, lienceFileName, Constants.USER_LIENCE, Constants.LIENCE_WIDTH, Constants.LIENCE_HIGHT,null);
					if(url == null){
						returnData.setReturnData(errorcode_data, "上传行驶证失败", null); 
						return ;
					}
					user_carcode.setRun_url(url);
				}
				
				int count =  user_carcodeDao.updateByKey(user_carcode);
				if(count < 1){
					//更新失败
					returnData.setReturnData(errorcode_data, "更新用户车辆信息失败", ""); 
					return;
					
				}
				returnData.setReturnData(errorcode_success, "更新用户车辆信息成功", user_carcode); 
				return;
				
			} catch (Exception e) {
				log.error("CarBiz update_car is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
		}
		
		
	   /**
	    * 检查某停车场某车牌号是否已经付款
	    * @param returnData
	    * @param pi_id
	    * @param car_code
	    * @param area_code
	    */
		@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
		public void read_checkpay(ReturnDataNew returnData, long pi_id,
				String car_code, String area_code,String orderid) {
			// TODO Auto-generated method stub
			try {
				JSONObject returnobj = new JSONObject();
				long ui_id = 0;
				
				//从订单表中 获取该用户的停车缴费订单信息
				Pay_park pay_park = null;
				String sql = "select *  from  pay_park where pi_id=? and area_code=? and car_code=? and my_order=?  order by ctime desc limit 1";
				pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,car_code,orderid);
				if(pay_park == null){
					//检查该车辆是否是 分时段包月车辆
					sql = "select *  from pay_month_park where pi_id=? and area_code=? and car_code=? and my_order=? order by ctime desc limit 1";
					Pay_month_park pay_month_park = getMySelfService().queryUniqueT(sql, Pay_month_park.class, pi_id,area_code,car_code,orderid);
					if(pay_month_park == null){
						//该车辆未出入
						returnData.setReturnData(errorcode_data, "该车辆未出入", ""); 
						return;
					}else{
						ui_id = pay_month_park.getUi_id();
						Date date = new Date();
						//检查该车辆分时段包月是否已经逾期
						if(pay_month_park.getEnd_time().getTime() < date.getTime()){
							//已经逾期 则进行逾期缴费处理----按临停计费处理
							long outtime_minute = 0;//超时时长（单位分钟）
							//设置逾期金额  且进行更新
							
							
						}
						
					}
				}else{
					ui_id = pay_park.getUi_id();
				}
				
				
				
				//订单信息
				returnobj.put("ui_id", ui_id);
				returnobj.put("car_code", car_code);
				returnobj.put("state", pay_park.getPp_state());//是否支付  0：未支付 1：已经支付
				returnobj.put("money", pay_park.getMoney());//支付金额 单位 分
				 //返回结果
				returnData.setReturnData(errorcode_success, "查询是否已经付款成功", returnobj); 
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("carBiz.read_checkpay is error"+e.getMessage(), e);
			}
		}
	
		
	   /**
	    * 停车场心跳表
	    * @param returnData
	    * @param pi_id
	    * @param area_code
	    * @param num
	    * @param total
	    * @param is_rent
	    */
		public void park_heartbeat(ReturnDataNew returnData, long pi_id,
				String area_code, int is_rent,long time,int park_type,Integer carport_yet,Integer carport_space,Integer carport_total,Integer moth_car_num,Integer moth_car_num_space,Integer time_car_num,Integer time_car_num_space,Integer expect_car_num) {
			// TODO Auto-generated method stub
			try {
				Date date = new Date();
				Park_heartbeat ph = new Park_heartbeat();
				ph.setArea_code(area_code);
				ph.setCtime(date);
				ph.setCtime_num(date.getTime());
				ph.setIs_rent(is_rent);
				ph.setPi_id(pi_id);
				if(carport_total != null && carport_total.intValue() > 0){
				  ph.setCarport_total(carport_total.intValue());
				}  
				if(carport_space != null && carport_space.intValue() > 0){
				   ph.setCarport_space(carport_space.intValue());
				}
				if(carport_yet != null && carport_yet.intValue() > 0){
					ph.setCarport_yet(carport_yet.intValue());
				}
				if(moth_car_num != null && moth_car_num.intValue() > 0){
				   ph.setMoth_car_num(moth_car_num.intValue());
				}
				if(moth_car_num_space != null && moth_car_num_space.intValue() > 0){
				   ph.setMoth_car_num_space(moth_car_num_space.intValue());
				}
				if(time_car_num != null && time_car_num.intValue() > 0){
					ph.setTime_car_num(time_car_num.intValue());
				}
				
				if(time_car_num_space != null && time_car_num_space.intValue() > 0){
				   ph.setTime_car_num_space(time_car_num_space.intValue()); 
				}
				int id = park_heartbeatDao.insert(ph);
				if(id > 0 ){
					//校准该停车场的车位数
					Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
		        	if(park_info != null ){
					    if(carport_total != null && carport_total.intValue() > 0){
						  ph.setCarport_total(carport_total.intValue());
						}  
						if(carport_space != null && carport_space.intValue() > 0){
						   ph.setCarport_space(carport_space.intValue());
						}
						if(carport_yet != null && carport_yet.intValue() > 0){
							ph.setCarport_yet(carport_yet.intValue());
						}
						if(moth_car_num != null && moth_car_num.intValue() > 0){
						   ph.setMoth_car_num(moth_car_num.intValue());
						}
						if(moth_car_num_space != null && moth_car_num_space.intValue() > 0){
						   ph.setMoth_car_num_space(moth_car_num_space.intValue());
						}
						if(time_car_num != null && time_car_num.intValue() > 0){
							ph.setTime_car_num(time_car_num.intValue());
						}
						
						if(time_car_num_space != null && time_car_num_space.intValue() > 0){
						   ph.setTime_car_num_space(time_car_num_space.intValue()); 
						}
		        		park_info.setIs_fault(0);
		        		int count = park_infoDao.updateByKey(park_info, ReturnParkTableName(area_code));
		        		if(count < 1){
		        			returnData.setReturnData(errorcode_data, "停车场车位数校准失败", ""); 
							return;
		        		}else{
		        			if(park_info.getPark_type() == 0){
			        			Map<String,Object> map = new HashMap<String,Object>();
			        			map.put("park_info", park_info);
			        			map.put("time", date.getTime());
			        			returnData.setReturnData(errorcode_success, "停车场心跳发送成功", map); 
			    				return;
		        			}else{
		        				//返回结果
		        				returnData.setReturnData(errorcode_data, "停车场心跳发送失败", ""); 
		    					return;
		        			}
		        		}
		        	}
					//插入成功
					 //返回结果
					returnData.setReturnData(errorcode_success, "停车场心跳发送成功", ""); 
					return;
				}else{
					//插入失败
					 //返回结果
					returnData.setReturnData(errorcode_data, "停车场心跳发送失败", ""); 
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("carBiz.park_heartbeat is error"+e.getMessage(), e);
			}
		}
		
		
	   /**
	    * 获取用户申诉退费审核结果
	    * @param returnData
	    * @param ui_id
	    * @param order_id
	    * @param is_rent
	    */
	   @TargetDataSource(value=DynamicDataSourceHolder.SLAVE)
		public void read_usermoneyback(ReturnDataNew returnData, long ui_id,
				String order_id, int is_rent) {
			// TODO Auto-generated method stub
	        try {
	        	//首先判断用户是否存在
	        	String sql = "select *  from user_moneyback where order_id=? and ui_id=? and is_rent=?";
	        	
	        	User_moneyback user_moneyback = getMySelfService().queryUniqueT(sql,User_moneyback.class, order_id, ui_id,is_rent);
				if(user_moneyback == null){
					//无该申述记录
					returnData.setReturnData(errorcode_data, "无该申述记录","","1"); 
					return;
				}
	        	returnData.setReturnData(errorcode_success, "获取成功", user_moneyback); 
				return;
				
			} catch (Exception e) {
				log.error("CarBiz read_usermoneyback is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
		}
	/**************************分离出来的方法*****************************/

	//查询用户和车牌号绑定关系
    public User_carcode queryUserCar(long ui_id,String car_code){
    	try {
			//首先判断是否已经绑定了该量车
			String sql = "select *  from user_carcode where ui_id=? and car_code=? limit 1";
			User_carcode user_carcode = getMySelfService().queryUniqueT(sql, User_carcode.class, ui_id,car_code);
			return user_carcode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryUserCar is error"+e.getMessage(), e);
		}
		return null;
    }
	//查询用户和车牌号绑定关系
    public User_carcode queryUserCarBycode(String car_code){
    	try {
			//首先判断是否已经绑定了该量车
			String sql = "select *  from user_carcode where car_code=? limit 1";
			User_carcode user_carcode = getMySelfService().queryUniqueT(sql, User_carcode.class,car_code);
			return user_carcode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryUserCarBycode is error"+e.getMessage(), e);
		}
		return null;
    }

    /**
     * 查询用户的车牌号码
     */
   public List<User_carcode> 	QueryUserCarCode(long ui_id){
	 //查询该用户所绑定的车牌号码
	try {
		//首先判断是否已经绑定了该量车
	   	String sql = "select *  from user_carcode where ui_id=?";
	   	List<User_carcode> list = getMySelfService().queryListT(sql, User_carcode.class, ui_id);
	   	return list;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("QueryUserCarCode is error"+e.getMessage(), e);
	}
	return null;
   }

   
   


   





}
