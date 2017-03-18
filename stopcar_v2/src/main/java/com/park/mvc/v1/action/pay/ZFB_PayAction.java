
package com.park.mvc.v1.action.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.util.SignUtils;
import com.alipay.vo.AlipayAppBean;
import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 支付宝 -- 用户充值
 * @author jingxiaohu
 *
 */
public class ZFB_PayAction extends BaseV1Action {
	/**
	 * 
	 */
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final long serialVersionUID = -4402176190279046970L;
//	private String notify="http://app.ie-colorful.com/v1/notify_lzf.php";
	private static String APP_ID = "2016112103042572";
	private static String seller_id = "2088421595009690";
	private static String APP_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEhzyauJHud3EgMJICw6eEnW4nx2aywzdaYL5l+iGZRyX8rgPoCDQNfMoXL6TombDpay6MrsHROf03myoKFSuB2ygHpE2Y7H00rbYSzKWi4uojgXWf6ZrXhFnkO7oBaxUvjwrRp0l+pX46uRFQJLGkx59U1r1CaTqYolYSkzFEJQIDAQAB";
	private static String APP_PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMSHPJq4ke53cSAwkgLDp4SdbifHZrLDN1pgvmX6IZlHJfyuA+gINA18yhcvpOiZsOlrLoyuwdE5/TebKgoVK4HbKAekTZjsfTStthLMpaLi6iOBdZ/pmteEWeQ7ugFrFS+PCtGnSX6lfjq5EVAksaTHn1TWvUJpOpiiVhKTMUQlAgMBAAECgYAQUncLFpcwKgVgOgh2aE+KaRTUAvCZmjMHs488oviVZV5XTRCP/dZs5FdYc6GLm/AggsFb98urF9ja/G0SZ020ltPKAUL9ETftu8ysf7EDQxsLbavfuHy270WHyzDgy9Waw3rTwjTdPuPpenutlCSJqcotZx4a7VgtBv3mEnj9oQJBAPb0r/nmJ+5Fzgm6ko4CFycaCIossWqXt5JeWgPo3eZrsA/IqQTf5OTDNBQEYygtiuET3Ffgxnib4Z+SG7LOZAkCQQDLucUz7bRtrz40fs2ApiKD+G83GCPcikcHvKdkOz7bm2g2PFDAe/gcxDogjS527+jgfC0Kel8WbfipK9tkT349AkA2svPXcjcd+7ArT3vuoF/odUe28zdI2Nn8PZHKk+Wyh9+zX0qwnbbhRKtgU6hy2cONHw0LGepcBIrxATfJXxWhAkEAjgO5AYMBlLhln4iJTtYBF4f2VyyfyxwlebI76fYW0lWaJryS+isxATSU5J4mNsj0yJAngbdeU69jeOJWtK1pbQJBANhlXbvv+8JJPF4lkhfvCL3S5Nitwhuh8T/hYt2B/Ry7Ll5eReH1ryNKni3yzCyNncN/a6z7M4ENN3WUrvYB/FM=";
	private static String notify_url="http://"+Constants.domain+"/v1/notify_zfb.php";
	
	@Action(value = "zfb_charge")
	public String zfb_charge(){
		
		ReturnDataNew returnData = new ReturnDataNew();
		//非空验证
		if(System.currentTimeMillis()-t >3*60*1000){
			//如果请求超出120秒则认为是 废弃请求
			returnData.setReturnData(errorcode_param, "是 废弃请求", "");
			sendResp(returnData);
			return null;
		}
		if(type < 1){
			//type必须大于0
			returnData.setReturnData(errorcode_param, "type必须大于0", "");
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
			int pay_price_fen = pay_price;
			userPayBiz.zfb_charge(returnData,pay_type,ui_id,pay_price_fen,version,system_type,subject,ip,token,type,orderid);
			
			if(returnData != null && "0".equalsIgnoreCase(returnData.getErrorno())){
				JSONObject obj = (JSONObject) JSONObject.toJSON(returnData.getData());
				String out_trade_no = obj.getString("order_id");
				//这里需要进行支付宝签名参数后返回给客户端
				AlipayAppBean appbean = new AlipayAppBean();
				appbean.setOut_trade_no(out_trade_no);
				if(RequestUtil.checkObjectBlank(subject)){
					subject="吾泊停车";
				}else{
					appbean.setSubject(subject);
				}
				String timestamp =sf.format(new Date()); 
				appbean.setSeller_id(seller_id);
				appbean.setTotal_amount((pay_price_fen*1.0)/100); 
				appbean.setType(type);
				Map<String, String> params = buildOrderParamMap(APP_ID,appbean,timestamp,notify_url,type);
				String sign = AlipaySignature.rsaSign(params, APP_PRIVATE_KEY, "UTF-8");
				String orderParam = buildOrderParam(params);
				String orderInfo = orderParam+ "&sign=" + URLEncoder.encode(sign, "UTF-8");
				obj.put("orderInfo", orderInfo);
				obj.put("sign", sign);
				obj.put("timestamp", timestamp);
				returnData.setData(obj);
				sendResp(returnData);
				return null;
			}
			returnData.setReturnData(errorcode_data, "支付失败", "");
			sendResp(returnData);
			return null;
		
		} catch (Exception e) {
			log.error("ZFB_PayAction.zfb_charge is error 用户支付宝充值",e);
			returnData.setReturnData("1", "-1", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	/**
	 * 构造支付订单参数列表
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static  Map<String, String> buildOrderParamMap(String app_id,AlipayAppBean appbean,String timestamp,String notify_url,int type) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);

		keyValues.put("biz_content",JSONObject.toJSONString(appbean));
		
		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", "RSA");

		keyValues.put("timestamp",timestamp);

		keyValues.put("version", "1.0");
		
		keyValues.put("notify_url", notify_url);
		//设置透传参数
		keyValues.put("passback_params", type+"");
		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static  String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
	    Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static  String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	/********************接收参数区*************************/
	private int pay_type;//支付类型 1:支付宝  2：微信  3：银联  4：钱包 5:龙支付 
	private int pay_price;//充值金额 单位 分
	private int version;//当前版本编号
	private String subject;//商品名称
	private int system_type;//操作系统类型（IOS Android web） 1:android 2:IOS 3:web
	private long t;//时间戳ms
	private String token;//令牌
	private int type;//是支付 还是 充值  1：充值  2：普通订单支付  3：租赁订单支付
	private String orderid;//付款 订单ID
	
	

	/************************get set 方法区****************************/
	
	public int getType() {
		return type;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public void setType(int type) {
		this.type = type;
	}
	public int getPay_type() {
		return pay_type;
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
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map
	 *            待签名授权信息
	 * 
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	/*public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<String,String>();
		params.put("a", "123");
		String sign = AlipaySignature.rsaSign(params, APP_PRIVATE_KEY, "UTF-8");
		params.put("sign", sign);
		System.out.println(AlipaySignature.rsaCheckV2(params, APP_PUBLIC_KEY, "UTF-8"));
	}*/
}
