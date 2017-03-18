package qz.test.actiontest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.park.core.Constants;
import com.park.util.RequestUtil;
/**
 * 2.0	用户唯一识别主键生成（JAVA）- P  by jxh 
 * @author 敬小虎
 *
 */
public class AutoMakeUidActionTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception { 
		/*HttpPostUtil post = new HttpPostUtil("http://192.168.1.100:8090/quzhuan/auto_make_uid.do");
		post.addTextParameter("ui_imei", "12312314141"); 
		post.addTextParameter("ui_sim_serial", "12312314141"); 
		post.addTextParameter("m", RequestUtil.MD5("12312314141"+Constants.SYSTEM_KEY)); 
		byte[] b = post.send();
		String result = new String(b,"UTF-8");
		System.out.println( result);*/
		
		
		HttpClient hc=new HttpClient();
	    PostMethod post2 = new PostMethod("http://localhost:8090/auto_make_uid.do");
		post2.addParameter("ui_imei", "12312314141"); 
		post2.addParameter("ui_sim_serial", "12312314141");  
		post2.addParameter("m", RequestUtil.MD5("12312314141"+Constants.SYSTEM_KEY)); 
		hc.executeMethod(post2);
		//HttpUtils.executeHttpClient(post2);
		try {
			String ds = post2.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				System.out.println(new String(post2.getResponseBodyAsString().getBytes("iso-8859-1"), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*HttpClient hc=new HttpClient();
		GetMethod gm=new GetMethod("http://127.0.0.1:8080/quzhuan/auto_make_uid.do");
		gm.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//设置信息
		hc.executeMethod(gm);
		System.out.print(gm.getResponseBodyAsString()); */
		
		//System.out.println(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
	}

}
