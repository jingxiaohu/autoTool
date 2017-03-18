
package com.park.mvc.v1.action.pay.old;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 建设银行龙支付 -- 用户充值
 * @author jingxiaohu
 *
 */
public class CopyOfLZF_PayAction extends BaseV1Action {
	private String notify="http://app.ie-colorful.com/v1/notify_lzf.php";
	private String url = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain";
	private String pub = "30819d300d06092a864886f70d010101050003818b0030818702818100cd0b7cdab739d49af0ee8bf88f5bcfc8432d8c6818f0821d34e74bf52081977ec2a30cddba61b84fda72b6ec883283a14431410e7ff90449bd6e8fca88d3828a83d47a600e1e33881bcde003a65f9547acb998a0d971c4006e6c25222aed818d11b861381b7f80f8dc6d81303c25a8f17d35f2559a0802790f2b22c3dc5026c3020111";
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;
	//商户代码
	private String MERCHANTID="105510548160013";
	//商户柜台代码
	private String POSID="426295203";
	//分行代码
	private String BRANCHID="510000000";
	//定单号 由商户提供，最长30位
	private String ORDERID="";
	//付款金额  由商户提供，按实际金额给出
	private String PAYMENT="";
	//币种 缺省为01－人民币（只支持人民币支付）
	private String CURCODE="01";
	//交易码 由建行统一分配为520100
	private String TXCODE="520100";
	//备注1 网银不处理，直接传到城综网
	private String REMARK1="";
	//备注2 网银不处理，直接传到城综网
	private String REMARK2="";
	//接口类型 0- 非钓鱼接口 1- 防钓鱼接口 目前该字段以银行开关为准，如果有该字段则需要传送以下字段。
	private String TYPE="1";
	//网关类型 
	private String GATEWAY="";
	//CLIENTIP=128.128.80.125  客户端IP 客户在商户系统中的IP
	private String CLIENTIP="";
	// 客户注册信息 客户在商户系统中注册的信息，中文需使用escape编码  (我们记录客户的用户ID)
	private String REGINFO="";
	//商品信息 客户购买的商品
	private String PROINFO="wubobi";
	//商户URL 商户送空值即可，银行从后台读取商户设置的一级域名，如www.ccb.com则设为： “ccb.com”，最多允许设置三个不同的域名，格式为：****.com| ****.com.cn|****.net）
	private String REFERER="";
	//分期期数 信用卡支付分期期数， 一般为3、6、12等，必须为大于1的整数，当分期期数为空或无该字段上送时，则视为普通的网上支付 当分期期数为空或无该字段上送时，该字段不参与MAC校验，否则参与MAC校验。
	private String INSTALLNUM="";
	//客户端标识 商户客户端的intent-filter/schema，格式如下：comccbpay+商户代码(即MERCHANTID字段值)+商户自定义的标示app的字符串 商户自定义的标示app的字符串，只能为字母或数字。
	private String THIRDAPPINFO="comccbpay"+MERCHANTID+"wubo";
	//订单超时时间 格式：YYYYMMDDHHMMSS如：20120214143005银行系统时间> TIMEOUT时拒绝交易，若送空值则不判断超时。当该字段有值时参与MAC校验，否则不参与MAC校验。
	private String TIMEOUT="20161128101226";
	//银行代码  打开了“跨行付商户端模式”开关的商户方可上送该字段，若上送了该字段则直接跳转至该字段代表的银行界面，具体见附录1。当该字段有值时参与MAC校验，否则不参与MAC校验。该字段仅对PC跨行生效，手机跨行无需上送该字段
	private String ISSINSCODE="UnionPay";
	//MAC校验域 采用标准MD5算法，由商户实现
	private String MAC="";
	/**
	 * 用户---建行龙支付充值
	 * @return
	 * MERCHANTID=105320148140002
	 * &POSID=100001135
	 * &BRANCHID=320000000
	 * &ORDERID=88487
	 * &PAYMENT=0.01
	 * &CURCODE=01
	 * &TXCODE=520100
	 * &REMARK1=
	 * &REMARK2=
	 * &TYPE=1
	 * &GATEWAY=
	 * &CLIENTIP=128.128.80.125
	 * &REGINFO=xiaofeixia
	 * &PROINFO=digital
	 * &REFERER=
	 * &INSTALLNUM=3
	 * &SMERID=111
	 * &SMERNAME=%u5DE5%u827A%u7F8E%u672F%u5546%u5E97
	 * &SMERTYPEID=112
	 * &SMERTYPE=%u5BBE%u9986%u9910%u5A31%u7C7B
	 * &TRADECODE=001
	 * &TRADENAME=%u6D88%u8D39&SMEPROTYPE=1
	 * &PRONAME=%u5DE5%u827A%u54C1
	 * &THIRDAPPINFO=comccbpay105320148140002alipay
	 * &TIMEOUT=20161028101226
	 * &ISSINSCODE=ICBC%20
	 * &MAC=b2a1adfc9f9a44b57731440e31710740
	 */
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
			String request_url = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?"
					+ "MERCHANTID="+MERCHANTID
					+"&POSID="+POSID
					+"&BRANCHID="+BRANCHID
					+"&ORDERID="+ORDERID
					+"&PAYMENT="+PAYMENT
					+"&CURCODE="+CURCODE
					+"&TXCODE="+TXCODE
					+"&REMARK1="+REMARK1
					+"&REMARK2="+REMARK2
					+"&TYPE="+TYPE
					+"&GATEWAY="+GATEWAY
					+"&CLIENTIP="+CLIENTIP
					+"&REGINFO="+REGINFO
					+"&PROINFO="+PROINFO
					+"&REFERER="+REFERER
//					+"&INSTALLNUM="+INSTALLNUM
					+"&THIRDAPPINFO="+THIRDAPPINFO
					+"&TIMEOUT="+TIMEOUT;
//					+"&pub="+pub.substring(pub.length()-30, pub.length());
//					+"&ISSINSCODE="+ISSINSCODE
//					+"&MAC=b2a1adfc9f9a44b57731440e31710740";
			//MAC校验域 采用标准MD5算法，由商户实现
			String str = MAC( MERCHANTID, POSID, BRANCHID, ORDERID, PAYMENT
					, CURCODE, TXCODE, REMARK1, REMARK2, TYPE, pub.substring(pub.length()-30, pub.length()),
					 GATEWAY, CLIENTIP, REGINFO, PROINFO, REFERER);
			String MAC = RequestUtil.MD5(str);
			request_url +=MAC;
			
			
			String ip = getIpAddr(getRequest());
			if(ip == null || "".equals(ip) || ip.startsWith("192.168")){  
				return null;
			}
			//元 转变成分
			int pay_price_fen = pay_price*100;
//			returnData = userPayBiz.lzf_charge(returnData,pay_type,ui_id,pay_price_fen,version,system_type,subject,ip,token);
			if(returnData != null && !"0".equalsIgnoreCase(returnData.getErrorno())){
				//订单生成成功
//				JSONObject obj = JSONObject.parseObject(returnData.getData().toString());
			    returnData.setReturnData(errorcode_param, "支付宝下单失败", "");
			}
			sendResp(returnData);
			return null;
		
		} catch (Exception e) {
			log.error("LZF_PayAction.lzf_charge is error 2.6	用户充值（GAMESDK-JAVA）- P",e);
			returnData.setReturnData("1", "-1", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	
/*	*//**
	 * 建行龙支付WEB MD5
	 *//*
	public String MAC(String MERCHANTID,String POSID,String BRANCHID,String ORDERID,String PAYMENT
			,String CURCODE,String TXCODE,String REMARK1,String REMARK2,String TYPE,String PUB,
			String GATEWAY,String CLIENTIP,String REGINFO,String PROINFO,String REFERER){
		return "MERCHANTID="+MERCHANTID+"&POSID="+POSID
				+"&BRANCHID="+BRANCHID+"&ORDERID="+ORDERID
				+"&PAYMENT="+PAYMENT+"&CURCODE="+CURCODE
				+"&TXCODE="+TXCODE+"&REMARK1="+REMARK1
				+"&REMARK2="+REMARK2+"&TYPE="+TYPE
				+"&PUB="+PUB+"&GATEWAY="+GATEWAY
				+"&CLIENTIP="+CLIENTIP+"&REGINFO="+REGINFO
				+"&PROINFO="+PROINFO+"&REFERER=";
	}*/
	/**
	 * 建行龙支付WEB MD5
	 */
	public static String MAC(String MERCHANTID,String POSID,String BRANCHID,String ORDERID,String PAYMENT
			,String CURCODE,String TXCODE,String REMARK1,String REMARK2,String TYPE,String PUB,
			String GATEWAY,String CLIENTIP,String REGINFO,String PROINFO,String REFERER){
		return "MERCHANTID="+MERCHANTID+"&POSID="+POSID
				+"&BRANCHID="+BRANCHID+"&ORDERID="+ORDERID
				+"&PAYMENT="+PAYMENT+"&CURCODE="+CURCODE
				+"&TXCODE="+TXCODE+"&REMARK1="+REMARK1
				+"&REMARK2="+REMARK2+"&TYPE="+TYPE
				+"&PUB="+PUB+"&GATEWAY="+GATEWAY
				+"&CLIENTIP="+CLIENTIP+"&REGINFO="+REGINFO
				+"&PROINFO="+PROINFO+"&REFERER=";
	}
	/********************接收参数区*************************/
	private int pay_type;//支付类型1:支付l宝2：微信3：银联',
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
	/************************get set 方法区****************************/
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
	public static void main(String[] args) throws Exception {
		//商户代码
		 String MERCHANTID="105510548160013";
		//商户柜台代码
		 String POSID="426295203";
		//分行代码
		 String BRANCHID="510000000";
		//定单号 由商户提供，最长30位
		 String ORDERID="2014114911482375";
		//付款金额  由商户提供，按实际金额给出
		 String PAYMENT="0.01";
		//币种 缺省为01－人民币（只支持人民币支付）
		 String CURCODE="01";
		//交易码 由建行统一分配为520100
		 String TXCODE="520100";
		//备注1 网银不处理，直接传到城综网
		 String REMARK1="";
		//备注2 网银不处理，直接传到城综网
		 String REMARK2="";
		//接口类型 0- 非钓鱼接口 1- 防钓鱼接口 目前该字段以银行开关为准，如果有该字段则需要传送以下字段。
		 String TYPE="1";
		//网关类型 
		 String GATEWAY="";
		//CLIENTIP=128.128.80.125  客户端IP 客户在商户系统中的IP
		 String CLIENTIP="218.88.30.86";
		// 客户注册信息 客户在商户系统中注册的信息，中文需使用escape编码  (我们记录客户的用户ID)
		 String REGINFO="";
		//商品信息 客户购买的商品
		 String PROINFO="吾泊充值";
		//商户URL 商户送空值即可，银行从后台读取商户设置的一级域名，如www.ccb.com则设为： “ccb.com”，最多允许设置三个不同的域名，格式为：****.com| ****.com.cn|****.net）
		 String REFERER="";
		//分期期数 信用卡支付分期期数， 一般为3、6、12等，必须为大于1的整数，当分期期数为空或无该字段上送时，则视为普通的网上支付 当分期期数为空或无该字段上送时，该字段不参与MAC校验，否则参与MAC校验。
		 String INSTALLNUM="";
		//客户端标识 商户客户端的intent-filter/schema，格式如下：comccbpay+商户代码(即MERCHANTID字段值)+商户自定义的标示app的字符串 商户自定义的标示app的字符串，只能为字母或数字。
		 String THIRDAPPINFO="comccbpay"+MERCHANTID+"wubo";
		//订单超时时间 格式：YYYYMMDDHHMMSS如：20120214143005银行系统时间> TIMEOUT时拒绝交易，若送空值则不判断超时。当该字段有值时参与MAC校验，否则不参与MAC校验。
		 String TIMEOUT="20161128101226";
		//银行代码  打开了“跨行付商户端模式”开关的商户方可上送该字段，若上送了该字段则直接跳转至该字段代表的银行界面，具体见附录1。当该字段有值时参与MAC校验，否则不参与MAC校验。该字段仅对PC跨行生效，手机跨行无需上送该字段
		 String ISSINSCODE="UnionPay";
		String pub = "30819d300d06092a864886f70d010101050003818b0030818702818100cd0b7cdab739d49af0ee8bf88f5bcfc8432d8c6818f0821d34e74bf52081977ec2a30cddba61b84fda72b6ec883283a14431410e7ff90449bd6e8fca88d3828a83d47a600e1e33881bcde003a65f9547acb998a0d971c4006e6c25222aed818d11b861381b7f80f8dc6d81303c25a8f17d35f2559a0802790f2b22c3dc5026c3020111";
		System.out.println(pub.substring(pub.length()-30, pub.length()));
		
		
		String request_url = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?"
				+ "MERCHANTID="+MERCHANTID
				+"&POSID="+POSID
				+"&BRANCHID="+BRANCHID
				+"&ORDERID="+ORDERID
				+"&PAYMENT="+PAYMENT
				+"&CURCODE="+CURCODE
				+"&TXCODE="+TXCODE
				+"&REMARK1="+REMARK1
				+"&REMARK2="+REMARK2
				+"&TYPE="+TYPE
				+"&GATEWAY="+GATEWAY
				+"&CLIENTIP="+CLIENTIP
				+"&REGINFO="+REGINFO
				+"&PROINFO="+URLEncoder.encode(URLEncoder.encode(PROINFO,"UTF-8"),"UTF-8")
				+"&REFERER="+REFERER;
//				+"&INSTALLNUM="+INSTALLNUM
//				+"&THIRDAPPINFO="+THIRDAPPINFO
//				+"&TIMEOUT="+TIMEOUT
//				+"&pub="+pub.substring(pub.length()-30, pub.length());
//				+"&ISSINSCODE="+ISSINSCODE;
//				+"&MAC=b2a1adfc9f9a44b57731440e31710740";
		//MAC校验域 采用标准MD5算法，由商户实现
		String str = MAC( MERCHANTID, POSID, BRANCHID, ORDERID, PAYMENT
				, CURCODE, TXCODE, REMARK1, REMARK2, TYPE, pub.substring(pub.length()-30, pub.length()),
				 GATEWAY, CLIENTIP, REGINFO, URLEncoder.encode(PROINFO,"UTF-8"), REFERER);
		String MAC = RequestUtil.MD5(str);
		request_url +="&MAC="+MAC;
		System.out.println(request_url);
	}
	
}
