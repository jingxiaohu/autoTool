package jxh.autocoder;

import com.highbeauty.sql.spring.builder.ABuilder;
/**
 * 
* @ClassName: JxhCoderAuto
* @Description: TODO(数据库表字段自动生成bean、dao)
* @author 敬小虎
* @date 2015年3月5日 下午2:24:48
*
 */
public class JxhCoderAuto {
	public static void main(String[] args) throws Throwable { 
		boolean src = true;
		String pkg = "com.park.";
		String[] tablenames = {"user_info","car_in_out","fault_record","park_coupon","park_device","park_info","pay_park","rental_charging_rule","user_moneyback","user_park_coupon","user_login_log",
				"sms_running","sms_validate","user_feedback","user_carcode","pay_month_park",
				"china_area","park_heartbeat",
				"user_vc_act","park_userinfo",
				"parkinfo_partner","user_cash_apply","user_pay","app_version"};
		String ip = "127.0.0.1";
		int port = 3306;
		String user = "root";
		String password = "root";
		String databaseName = "stopcar";
		ABuilder.AutoCoder(src, pkg, tablenames, ip, port, user, password, databaseName);
	}

}
