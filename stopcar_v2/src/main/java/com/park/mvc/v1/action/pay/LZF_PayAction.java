
package com.park.mvc.v1.action.pay;

import org.apache.struts2.convention.annotation.Action;

import com.alipay.api.internal.util.StringUtils;
import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 建设银行龙支付 -- 用户充值
 * @author jingxiaohu
 *
 */
public class LZF_PayAction extends BaseV1Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4402176190279046970L;
//	private String notify="http://app.ie-colorful.com/v1/notify_lzf.php";
	private String pub = "30819d300d06092a864886f70d010101050003818b0030818702818100cd0b7cdab739d49af0ee8bf88f5bcfc8432d8c6818f0821d34e74bf52081977ec2a30cddba61b84fda72b6ec883283a14431410e7ff90449bd6e8fca88d3828a83d47a600e1e33881bcde003a65f9547acb998a0d971c4006e6c25222aed818d11b861381b7f80f8dc6d81303c25a8f17d35f2559a0802790f2b22c3dc5026c3020111";
	@Action(value = "lzf_charge")
	public String lzf_charge(){
		
		ReturnDataNew returnData = new ReturnDataNew();
		//非空验证
		if(System.currentTimeMillis()-t >3*60*1000){
			//如果请求超出120秒则认为是 废弃请求
			returnData.setReturnData(errorcode_param, "是 废弃请求", "");
			sendResp(returnData);
			return null;
		}
		if(pay_price < 1){
			//pay_price必须大于0
			returnData.setReturnData(errorcode_param, "pay_price必须大于0", "");
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(token)){
			//token不能为空
			returnData.setReturnData(errorcode_param, "token不能为空", "");
			sendResp(returnData);
			return null;
		}
		//MD5验证
		if(sign != null){
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,pay_type,pay_price,t,token);
			//MD5散列(pay_type+uid+pay_price+t+token+app_key）
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
		}
		try {
			String ip = getIpAddr(getRequest());
			System.out.println("ip==="+ip);
			if(ip == null || "".equals(ip) || ip.startsWith("192.168")){  
				return null;
			}
			//元 转变成分
//			int pay_price_fen = pay_price*100;
			int pay_price_fen = pay_price;
			userPayBiz.lzf_charge(returnData,pay_type,ui_id,pay_price_fen,version,system_type,subject,ip,token,pub.substring(pub.length()-30, pub.length()),type,orderid);
			sendResp(returnData);
			return null;
		
		} catch (Exception e) {
			log.error("LZF_PayAction.lzf_charge is error 2.6	用户充值（GAMESDK-JAVA）- P",e);
			returnData.setReturnData("1", "-1", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	/********************接收参数区*************************/
	private int pay_type;//支付类型 1:支付宝  2：微信  3：银联  4：钱包 5:龙支付 
	private int pay_price;//充值金额 单位 分
	private int version;//当前版本编号
	private String subject;//商品名称
	private int system_type;//操作系统类型（IOS Android web） 1:android 2:IOS 3:web
	private long t;//时间戳ms
	private String token;//令牌
	//收银台页面上，商品展示的超链接，必填
	private String show_url ="";
	// 页面跳转同步通知页面路径
	private String return_url = "";
	private int type;//是支付 还是 充值  1：充值  2：普通订单支付  3：租赁订单支付
	private String orderid;//付款 订单ID
	/************************get set 方法区****************************/
	
	
	public int getPay_type() {
		return pay_type;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	public int getPay_price() {
		return pay_price;
	}
	public void setPay_price(int pay_price) {
		this.pay_price = pay_price;
	}
	public int getTerminal_type() {
		return system_type;
	}
	public void setTerminal_type(int terminal_type) {
		this.system_type = terminal_type;
	}
	public long getT() {
		return t;
	}
	public void setT(long t) {
		this.t = t;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
