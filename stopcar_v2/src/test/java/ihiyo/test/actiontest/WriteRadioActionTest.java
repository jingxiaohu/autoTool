package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   音频管理测试
 * @author jingxiaohu
 *
 */
public class WriteRadioActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "2";
	private String userid = "4";
	private String devicemac = "98:2f:3c:ab:c8:a1";
	@Test
	public void vod() throws Exception{
		String url = BaseUrl+"radiorecord.php";
		PostMethod post  = new PostMethod(url);
		
		
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		//抓取的来源
		String source = "qingting";
		post.addParameter("source", source);
		//音频ID
		String id = "1722";
		post.addParameter("id", id);
		//type 	0:点播 1：直播
		String type  = "1";
		post.addParameter("type", type);
		//分类ID
		long ciid = 78;
		post.addParameter("ciid", ciid+"");
		//专辑ID
		long zjid = 1772;
		post.addParameter("zjid", zjid+"");
		
		
		
		int xx = HC.executeMethod(post);
		System.out.println("*请求状态code="+xx); 
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				System.out.println(new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("checkLoginname is error"+e.getMessage(), e); 
		}finally{
			if(post!=null){
				post.releaseConnection();
				//释放链接
				HC.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}
}
