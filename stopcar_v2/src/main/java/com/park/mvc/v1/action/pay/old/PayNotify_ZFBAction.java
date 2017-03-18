
package com.park.mvc.v1.action.pay.old;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.alipay.util.AlipayNotify;
import com.park.mvc.v1.action.BaseV1Action;

/**
 * 2.7	支付宝通知监听接口（GAMESDK-JAVA）- P
* @ClassName: PayNotify_QQAction
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年3月13日 上午11:49:27
*
 */
public class PayNotify_ZFBAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 2.7	支付宝通知监听接口（GAMESDK-JAVA）- P
	* @Title: PayNotify_zfb
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@Action(value = "paynotify_zfb")
	public String PayNotify_ZFB(){
		String returnData = null;
		//非空验证
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = getRequest().getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			if(params != null){
				log.error("Alipay paynotify params="+params.toString());
			}
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(getRequest().getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(getRequest().getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			String trade_status = new String(getRequest().getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			//总金额
			String total_fee = new String(getRequest().getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
			//时间
			String notify_time = new String(getRequest().getParameter("notify_time").getBytes("ISO-8859-1"),"UTF-8");
			//通知ID
			String notify_id = new String(getRequest().getParameter("notify_id").getBytes("ISO-8859-1"),"UTF-8");
			//加密类型
			String sign_type = new String(getRequest().getParameter("sign_type").getBytes("ISO-8859-1"),"UTF-8");
			//折扣
			String discount = "1";
			if(getRequest().getParameter("discount") == null){
				discount = "1";
			}else{
				discount = new String(getRequest().getParameter("discount").getBytes("ISO-8859-1"),"UTF-8");
			}
			//退款状态 
			String refund_status = null;
			if(getRequest().getParameter("refund_status") != null){
				refund_status = new String(getRequest().getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
			}
			//商品名称
			String subject = new String(getRequest().getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
			//买家支付宝用户号
			String buyer_id = new String(getRequest().getParameter("buyer_id").getBytes("ISO-8859-1"),"UTF-8");
			//买家支付宝账号
			String buyer_email = new String(getRequest().getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//付款完成后，支付宝系统发送该交易状态通知
				}

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				if(trade_status.equals("TRADE_SUCCESS")){
					//进行更新通知状态
					//调用下单接口
					int trans_amount = (int) (Double.parseDouble(total_fee)*100);
					String ip = getIpAddr(getRequest());
					/*if(ip == null || "".equals(ip) || ip.startsWith("192.168")){  
						return null;
					}*/
					returnData = gameSdkBiz.PayNotify_ZFBAction_PayNotify_ZFB(returnData,notify_time,notify_id,sign_type,trade_status,out_trade_no,trade_no,discount,refund_status,subject,buyer_id,buyer_email,trans_amount,ip);
					sendResp(returnData);
					return null;
				}	
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				sendResp("fail");
				return null;
			}
			
		} catch (Exception e) {
			log.error("PayNotify_ZFBAction_PayNotify_ZFB is error 2.7	支付宝通知监听接口（GAMESDK-JAVA）- P",e);
			returnData = "fail"; 
		}
		sendResp(returnData);
		return null; 
	}
	
	 /**
	  * 验证签名
	 * @Title: ValidateSign
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	  *//*
	 public   boolean ValidateSign() {
		*//**
		 * MD5签名计算公式： sign = Md5(原字符串&key=商户密钥). toUpperCase
		 *//*
		    String params = getQueryString(getRequest());
		    if(params == null || params.indexOf("&") == -1){
		    	return false;
		    }
			String[] paramsArray = params.split("&");
			//出去空值
			List<String> paramsList = new ArrayList<String>();
			for (String str : paramsArray) {
				String[] xx = str.split("=");
				if(xx != null && xx.length == 2 && !"sign_type".equalsIgnoreCase(xx[0]) && !"sign".equalsIgnoreCase(xx[0])){
					paramsList.add(str+"&");
				}
			}
			Collections.sort(paramsList);// 进行自然升序排列
			params = Arrays.toString(paramsList.toArray());// 输出数组字符串
			return RSA.verify(params, sign, AlipayConfig.alipay_public_key, AlipayConfig.input_charset);
		}

	*//**
	 * 
	* @Title: returnValidate
	* @Description: TODO(参数验证字方法)
	* @param @param returnData
	* @param @return    设定文件
	* @return ReturnData    返回类型
	* @throws
	 *//*
	@SuppressWarnings("unchecked")
	private boolean returnValidate(){
		if(RequestUtil.checkObjectBlank(notify_id) || RequestUtil.checkObjectBlank(out_trade_no) 
				|| RequestUtil.checkObjectBlank(trade_no)){
			return false;
		}
		if(total_fee < 1 || price < 1 || RequestUtil.checkObjectBlank(seller_id) || RequestUtil.checkObjectBlank(buyer_id) 
				|| RequestUtil.checkObjectBlank(sign)){
			return false;
		}
		//进行数字签名验证
		if(ValidateSign()){ 
			return false;
		}
		if(!AlipayNotify.verify(getRequest().getParameterMap())){
			return false;
		}
		return true; 
		
	}
	
	
	
	
	*//********************接收参数区*************************//*
	*//**
	 * http://notify.java.jpxx.org/index.jsp?discount=0.00&payment_type=1&
	 * subject
	 * =测试&trade_no=2013082244524842&buyer_email=dlwdgl@gmail.com&gmt_create
	 * =2013-08-22
	 * 14:45:23&notify_type=trade_status_sync&quantity=1&out_trade_no
	 * =082215222612710&seller_id=2088501624816263&notify_time=2013-08-22
	 * 14:45:24
	 * &body=测试测试&trade_status=TRADE_SUCCESS&is_total_fee_adjust=N&total_fee
	 * =1.00&gmt_payment=2013-08-22
	 * 14:45:24&seller_email=xxx@alipay.com&price=1.00
	 * &buyer_id=2088602315385429&
	 * notify_id=64ce1b6ab92d00ede0ee56ade98fdf2f4c&use_coupon
	 * =N&sign_type=RSA&sign=1glihU9DPWee+UJ82u3+mw3Bdnr9u01at0M/xJnPsGuHh+
	 * JA5bk3zbWaoWhU6GmLab3dIM4JNdktTcEUI9
	 * /FBGhgfLO39BKX/eBCFQ3bXAmIZn4l26fiwoO613BptT44GTEtnPiQ6
	 * +tnLsGlVSrFZaLB9FVhrGfipH2SWJcnwYs=
	 *//*

	
	public String notify_time;//通知时间  通知的发送时间。格式为yyyy-MM-dd HH:mm:ss。2013-08-22 14:45:24
	
	public String notify_id;//通知ID
	
	public String sign_type;//签名方式 ：默认 rsa
	//签名
	public String sign;
	
	
	*//**
	 * WAIT_BUYER_PAY 交易创建，等待买家付款。 TRADE_CLOSED 􀁺 在指定 时间段内未支付时关闭的交易； 􀁺 在交易完
	 * 成全额退款成功时关闭的交易。 TRADE_SUCCESS 交易成功，且可对该交易做操作，如：多级分润、退款等。 TRADE_FINISHED
	 * 交易成功且结束，即不可再做任何操作。
	 *//*
	public String trade_status;//交易状态
	
	public String out_trade_no;//订单ID
	public String discount;//折扣
	*//**
	 * REFUND_SUCCESS 退款成功： 􀁺 全额退款情况：trade_status=
	 * TRADE_CLOSED，而refund_status=REFUND_SUCCESS 􀁺 非全额退 款情况：trade_status=
	 * TRADE_SUCCESS，而refund_status=REFUND_SUCCESS REFUND_CLOSED 退款关闭
	 *//*
	public String refund_status;//退款状态
	
	public String subject;//商品名称
	
	public String trade_no;//支付宝交易号
	
    
	public String buyer_id;//买家支付宝用户号
	
	public String buyer_email;//买家支付宝账号
	
	public double total_fee;//交易金额
	
	public int quantity;//购买数量
	
	public double price;//商品单价
	
    public String seller_id;//卖家用户号

	

    
	*//************************get set 方法区****************************//*

	

	*//**
	 * @return the sign
	 *//*
	public String getSign() {
		return sign;
	}



	*//**
	 * @return the seller_id
	 *//*
	public String getSeller_id() {
		return seller_id;
	}

	*//**
	 * @param seller_id the seller_id to set
	 *//*
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	*//**
	 * @return the notify_time
	 *//*
	public String getNotify_time() {
		return notify_time;
	}

	*//**
	 * @param notify_time the notify_time to set
	 *//*
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	*//**
	 * @return the notify_id
	 *//*
	public String getNotify_id() {
		return notify_id;
	}

	*//**
	 * @param notify_id the notify_id to set
	 *//*
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	*//**
	 * @return the sign_type
	 *//*
	public String getSign_type() {
		return sign_type;
	}

	*//**
	 * @param sign_type the sign_type to set
	 *//*
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	*//**
	 * @return the trade_status
	 *//*
	public String getTrade_status() {
		return trade_status;
	}

	*//**
	 * @param trade_status the trade_status to set
	 *//*
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	*//**
	 * @return the out_trade_no
	 *//*
	public String getOut_trade_no() {
		return out_trade_no;
	}

	*//**
	 * @param out_trade_no the out_trade_no to set
	 *//*
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	*//**
	 * @return the discount
	 *//*
	public String getDiscount() {
		return discount;
	}

	*//**
	 * @param discount the discount to set
	 *//*
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	*//**
	 * @return the refund_status
	 *//*
	public String getRefund_status() {
		return refund_status;
	}

	*//**
	 * @param refund_status the refund_status to set
	 *//*
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	*//**
	 * @return the subject
	 *//*
	public String getSubject() {
		return subject;
	}

	*//**
	 * @param subject the subject to set
	 *//*
	public void setSubject(String subject) {
		this.subject = subject;
	}

	*//**
	 * @return the trade_no
	 *//*
	public String getTrade_no() {
		return trade_no;
	}

	*//**
	 * @param trade_no the trade_no to set
	 *//*
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	*//**
	 * @return the buyer_id
	 *//*
	public String getBuyer_id() {
		return buyer_id;
	}

	*//**
	 * @param buyer_id the buyer_id to set
	 *//*
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	*//**
	 * @return the buyer_email
	 *//*
	public String getBuyer_email() {
		return buyer_email;
	}

	*//**
	 * @param buyer_email the buyer_email to set
	 *//*
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	*//**
	 * @return the total_fee
	 *//*
	public double getTotal_fee() {
		return total_fee;
	}

	*//**
	 * @param total_fee the total_fee to set
	 *//*
	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}

	*//**
	 * @return the quantity
	 *//*
	public int getQuantity() {
		return quantity;
	}

	*//**
	 * @param quantity the quantity to set
	 *//*
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	*//**
	 * @return the price
	 *//*
	public double getPrice() {
		return price;
	}

	*//**
	 * @param price the price to set
	 *//*
	public void setPrice(double price) {
		this.price = price;
	}

	*//**
	 * @param sign the sign to set
	 *//*
	public void setSign(String sign) {
		this.sign = sign;
	}*/
	





	
	

	




}
