package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   音频管理测试
 * @author jingxiaohu
 *
 */
public class Write_DeviceBugActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "1";
	private String userid = "4";
	private String devicemac = "98:2f:3c:ab:c8:a1";
	@Test
	public void vod() throws Exception{
		String url = BaseUrl+"devicebug.php";
		PostMethod post  = new PostMethod(url);
		
		
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		
		
		//post.addParameter("mac", source);
		post.addParameter("info", "test");
		post.addParameter("classname", "");
		post.addParameter("methodname","");
		post.addParameter("type", "1");
		post.addParameter("devicetime", System.currentTimeMillis()+"");
		
		
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
	
	
	@Test
	public void fmblue_record() throws Exception{
		String url = BaseUrl+"fmblue_record.php";
		PostMethod post  = new PostMethod(url);
		
		
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		
		
		 String type="0";//档位 0：蓝牙 1：FM
		 String zj_name="";//蓝牙专辑名称
		 String song="";//歌曲名称
		 String singer="";//歌手名称
		 String duration="";//总时长
		 String playtime="";//当前播放时长
		 String effect="1";//音效
		 String fm_signal="";//信号强弱
		 String fm_point="";//频点
		 String fm_scale="";//信兆比
		 String fm_name="";//频点对于的电台名称
		 String address="";//地址
		
		
		
		post.addParameter("type", type);
		post.addParameter("zj_name", zj_name);
		post.addParameter("song", song);
		post.addParameter("duration",duration);
		post.addParameter("playtime",playtime);
		post.addParameter("effect", effect);
		post.addParameter("fm_signal", fm_signal);
		post.addParameter("fm_point", fm_point);
		post.addParameter("fm_scale",fm_scale);
		post.addParameter("fm_name",fm_name);
		post.addParameter("address", address);
		
		
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
			log.error("fmblue_record is error"+e.getMessage(), e); 
		}finally{
			if(post!=null){
				post.releaseConnection();
				//释放链接
				HC.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}
}
