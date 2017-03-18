
package com.park.mvc.v1.action.pay.old;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.alibaba.fastjson.JSONObject;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 2.6	用户充值（GAMESDK-JAVA）- P
* @ClassName: ReChargeAction
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年3月11日 上午9:49:26
*
 */
public class ReChargeAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 2.6	用户充值（GAMESDK-JAVA）- P
	* @Title: ReCharge
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@Action(value = "recharge")
	public String ReCharge(){
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
			if(ip == null || "".equals(ip) || ip.startsWith("192.168")){  
				return null;
			}
			//元 转变成分
			int pay_price_fen = pay_price*100;
//			returnData = gameSdkBiz.ReChargeAction_ReCharge(returnData,pay_type,ui_id,pay_price_fen,version,system_type,subject,imei,t,ssid,ip,token);
			if(returnData != null && "0".equalsIgnoreCase(returnData.getErrorno())){
				//订单生成成功
				JSONObject obj = JSONObject.parseObject(returnData.getData().toString());
				//订单号
				String out_trade_no = obj.getString("ip_order_no");
				if(out_trade_no != null){
					//商户订单号，商户网站订单系统中唯一订单号，必填
//			        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			        //订单名称，必填
//			        String subject = new String(getRequest().getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			        //付款金额，必填
//			        String total_fee = new String(getRequest().getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
			        //收银台页面上，商品展示的超链接，必填
//			        String show_url = new String(getRequest().getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
			        //商品描述，可空
//			        String body = new String(getRequest().getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
					//////////////////////////////////////////////////////////////////////////////////
					//把请求参数打包成数组
			        double total_fee_ = pay_price*1.0;
			        
					Map<String, String> sParaTemp = new HashMap<String, String>();
//					sParaTemp.put("service", AlipayConfig.service);
					sParaTemp.put("service", "mobile.securitypay.pay");
//					sParaTemp.put("service", "create_direct_pay_by_user");
					
			        sParaTemp.put("partner", AlipayConfig.partner);
			        sParaTemp.put("seller_id", AlipayConfig.seller_id);
			        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
					sParaTemp.put("payment_type", AlipayConfig.payment_type);
//					sParaTemp.put("notify_url", AlipayConfig.notify_url);
					sParaTemp.put("notify_url", Constants.PayNotify_ZFB_Url);
					sParaTemp.put("return_url", AlipayConfig.return_url);
					sParaTemp.put("out_trade_no", out_trade_no);
					/*if(!RequestUtil.checkObjectBlank(subject)){
						//避免汉子的问题
						subject = URLDecoder.decode(subject, Constants.SYSTEM_CHARACTER);
					}*/
					sParaTemp.put("subject", "");
					sParaTemp.put("total_fee", total_fee_+"");
					sParaTemp.put("show_url", show_url);
					sParaTemp.put("body", "即时到账");
					//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
			        //如sParaTemp.put("参数名","参数值");
					//建立请求
					String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
//					String sHtmlText = AlipaySubmit.buildRequestget(sParaTemp);
					if(sHtmlText != null && !"error".equalsIgnoreCase(sHtmlText)){
							log.error("Alipay sHtmlText="+sHtmlText);
							//服务器插入订单成功
							/*JSONObject objddata = new JSONObject();
							objddata.put("alipyurl", sHtmlText);
							returnData.setReturnData(errorcode_success, "支付宝下单成功", objddata);
							sendResp(returnData);
							return null;*/
							sendRespHtml(sHtmlText);
							return null;
					}
			    }
			}
			
			
			returnData.setReturnData(errorcode_param, "支付宝下单失败", "");
			sendResp(returnData);
			return null;
		
		} catch (Exception e) {
			log.error("ReChargeAction_ReCharge is error 2.6	用户充值（GAMESDK-JAVA）- P",e);
			returnData.setReturnData("1", "-1", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	/********************接收参数区*************************/
	public int pay_type;//支付类型1:支付l宝2：微信3：银联',
	public int pay_price;//充值金额 单位 分
	public String version;//当前版本
	public int system_type;//操作系统类型（IOS Android web） 1:android 2:IOS 3:web
	public long t;//时间戳ms
	public String token;//令牌
	//收银台页面上，商品展示的超链接，必填
	public String show_url ="";
	// 页面跳转同步通知页面路径
    public String return_url = "";
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
}
