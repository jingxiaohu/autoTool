
package com.park.v1.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.park.DataSource.DynamicDataSourceHolder;
import com.park.DataSource.TargetDataSource;
import com.park.Transaction.BaseTransaction;
import com.park.bean.ReturnDataNew;

/**
 * 优惠券的业务逻辑管理类
 * @author jingxiaohu
 *
 */
@Service
public class CouponBiz extends BaseBiz{

	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	
    /**
     * 读取我的优惠券列表
     * @param returnData
     * @param dtype
     * @param ui_id
     */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void ReturnreadMycoupon(ReturnDataNew returnData, int dtype,
			long ui_id,int page,int size) {
		// TODO Auto-generated method stub
        try {
			if(page < 1){
				page = 1;
			}
        	int start = (page-1)*size;
			//首先验证用户是否存在
			String sql = "select *,end_time > now() as  is_effect from user_park_coupon where ui_id =:ui_id order by ctime  desc limit "+start+","+size;
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ui_id", ui_id+"");
			List<Map<String,Object>> list = getMySelfService().queryForList(sql, paramMap);
			returnData.setReturnData("0", "获取成功", list);
			return;
			
		} catch (Exception e) {
			log.error("CouponBiz ReturnreadMycoupon is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	
	
	/**************************分离出来的方法*****************************/










}
