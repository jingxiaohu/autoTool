package com.park.v1.biz.common;

import org.springframework.stereotype.Service;

import com.park.bean.Park_info;
import com.park.v1.biz.BaseBiz;

/**
 * 停车场公用方法
 * @author jingxiaohu
 *
 */
@Service
public class ParkInfoPB extends BaseBiz{
	
	/**
	 * 停车场车辆个数变更
	 * @param park_info 停车场基本信息
	 * @param is_enter 0：   入库   1：出库
	 * @param type 1:临停 2：租赁  3：预约  4：取消预约
	 * @return
	 */
	public boolean upCarNum(Park_info park_info,int is_enter,int type){ 
		try {
			if(is_enter == 0){
				/**
				 * 首先判断是    租赁  还是   临停   还是预约  取消预约
				 */
				switch(type){
				
				case 1:{
					//临停
					//入库  停车场空余车位数 减少
					if(park_info.getCarport_space()-1 >= 0){
						park_info.setCarport_space(park_info.getCarport_space()-1);//空闲车位数
						if(park_info.getCarport_yet() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_yet(park_info.getCarport_yet() + 1);//已停车位数
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}else{
						park_info.setCarport_space(0); 
						if(park_info.getCarport_yet() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_yet(park_info.getCarport_yet() + 1);//已停车位数
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				case 2:{
					//租赁
					//入库  停车场空余车位数 减少
					if(park_info.getTime_car_num_space() - 1 >= 0){
						park_info.setTime_car_num_space(park_info.getTime_car_num_space()-1);//空闲车位数
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}else{
						park_info.setTime_car_num_space(0);//空闲车位数
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				case 3:{
					//预约
					//入库  停车场空余车位数 减少
					if(park_info.getCarport_space() - 1 >= 0){
						park_info.setCarport_space(park_info.getCarport_space()-1);//空闲车位数
						if(park_info.getCarport_yet() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_yet(park_info.getCarport_yet() + 1);//已停车位数
						}
						if(park_info.getExpect_car_num() - 1 >= 0){
							park_info.setExpect_car_num(park_info.getExpect_car_num()-1);//已预约车位数
						}else{
							park_info.setExpect_car_num(0); 
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}else{
						park_info.setCarport_space(0); 
						if(park_info.getCarport_yet() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_yet(park_info.getCarport_yet() + 1);//已停车位数
						}
						if(park_info.getExpect_car_num() - 1 >= 0){
							park_info.setExpect_car_num(park_info.getExpect_car_num()-1);//已预约车位数
						}else{
							park_info.setExpect_car_num(0); 
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				case 4:{

					break;
				}
				default: return false ;
			}
		}
			
			if(is_enter == 1){
				
				/**
				 * 首先判断是    租赁  还是   临停   还是预约  取消预约
				 */
				switch(type){
				
				case 1:{
					//临停
					//出库  停车场空余车位数 增加
					if(park_info.getCarport_yet()-1 >= 0){
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space() + 1);//空闲车位数
						}
						park_info.setCarport_yet(park_info.getCarport_yet()-1);//已停车位数
						//如果该停车场为露天停车场 则不进行计算费用 扣费在扣费确认接口里面处理
						if(park_info.getPark_type() == 1){
							//露天停车场
							int count  = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
							if(count == 1){
								//更新失败
								return true;
							}
						}
					}else{
						park_info.setCarport_yet(0); 
						
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space()+1);//空闲车位数
						}
						
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					
					break;
				}
				case 2:{
					//租赁
					//出库  停车场空余车位数 增加
					if(park_info.getTime_car_num_space() + 1 < park_info.getTime_car_num()){
						
						park_info.setTime_car_num_space(park_info.getTime_car_num_space() + 1);//空闲车位数
						
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				case 3:{
					//预约
					//出库  停车场空余车位数 减少
					if(park_info.getCarport_yet() - 1 >= 0){
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space() + 1);//空闲车位数
						}
						park_info.setCarport_yet(park_info.getCarport_yet() - 1);//已停车位数
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}else{
						park_info.setCarport_yet(0); 
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space() + 1);//空闲车位数
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				case 4:{
					//取消预约
					//出库  停车场空余车位数 减少
					if(park_info.getCarport_yet() - 1 >= 0){
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space() + 1);//空闲车位数
						}
						park_info.setCarport_yet(park_info.getCarport_yet() - 1);//已停车位数
						
						if(park_info.getExpect_car_num() - 1 >= 0){
							park_info.setExpect_car_num(park_info.getExpect_car_num()-1);//已预约车位数
						}else{
							park_info.setExpect_car_num(0); 
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}else{
						park_info.setCarport_yet(0); 
						if(park_info.getCarport_space() + 1 <=  park_info.getCarport_total()){
							park_info.setCarport_space(park_info.getCarport_space() + 1);//空闲车位数
						}
						if(park_info.getExpect_car_num() - 1 >= 0){
							park_info.setExpect_car_num(park_info.getExpect_car_num()-1);//已预约车位数
						}else{
							park_info.setExpect_car_num(0); 
						}
						int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(park_info.getArea_code()));
						if(count == 1){
							//更新成功
							return true;
						}
					}
					break;
				}
				default: return false ;
			}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkInfoPB.upCarNum is error", e); 
		}
		
		
		return false;
	}
	
	

}
