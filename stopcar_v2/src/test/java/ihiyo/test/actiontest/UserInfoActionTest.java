package ihiyo.test.actiontest;

import java.io.File;
import java.net.URLEncoder;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

/**
 *   用户基本信息管理测试
 * @author jingxiaohu
 *
 */
public class UserInfoActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP 3:web
	private String dtype = "1";
	private String devicemac="d8:47:10:46:02:5e";
	private String userid = "0";
	String url = BaseUrl+"user.php";
	/**
	 * 发送验证码
	 * @throws Exception 
	 */
	@Test
	public void send_verify_code() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "send_verify_code";
		//分类点播
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("username", "15882345446");
		post.addParameter("vclass", "user_register");//change_pass/user_register/change_tel
		
		String result  = doPost(post);
		//{"errorno":"0","errormsg":"验证码发送成功","username":"15882345446","verify_list":"d4825425350e18709d27325799606899","resend_time":"120"}
	}
	/**
	 * 重发验证码
	 * @throws Exception 
	 */
	@Test
	public void resend_verify_code() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "resend_verify_code";
		//分类点播
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("username", "15882345446");
		post.addParameter("verify_list", "d4825425350e18709d27325799606899");
		post.addParameter("vclass", "user_register");//change_pass/user_register/change_tel
		
		String result  = doPost(post);
		//{"errorno":"0","errormsg":"验证码发送成功","username":"15882345446","verify_list":"d4825425350e18709d27325799606899","resend_time":"120"}
	}
	/**
	 * 用户注册
	 * @throws Exception 
	 */
	@Test
	public void user_register() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "user_register";
		//分类点播
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("username", "15882345446");
		post.addParameter("verify_code", "979109");
		post.addParameter("verify_list", "d4825425350e18709d27325799606899");
		post.addParameter("password", "123456");
		post.addParameter("repassword", "123456");
		
		String result = doPost(post);
		//{"categories":[{"id":"0","icon":"","clevel":"0","name":"电台"},{"id":1,"icon":"","clevel":"0","name":"小说"},{"id":2,"icon":"","clevel":"0","name":"音乐"},{"id":3,"icon":"","clevel":"0","name":"新闻"},{"id":4,"icon":"","clevel":"0","name":"相声小品"},{"id":5,"icon":"","clevel":"0","name":"脱口秀"},{"id":6,"icon":"","clevel":"0","name":"情感"},{"id":7,"icon":"","clevel":"0","name":"健康"},{"id":8,"icon":"","clevel":"0","name":"军事"},{"id":9,"icon":"","clevel":"0","name":"历史"},{"id":10,"icon":"","clevel":"0","name":"儿童"},{"id":11,"icon":"","clevel":"0","name":"娱乐"},{"id":12,"icon":"","clevel":"0","name":"女性"},{"id":13,"icon":"","clevel":"0","name":"搞笑"},{"id":14,"icon":"","clevel":"0","name":"教育"},{"id":15,"icon":"","clevel":"0","name":"外语"},{"id":16,"icon":"","clevel":"0","name":"公开课"},{"id":17,"icon":"","clevel":"0","name":"评书"},{"id":18,"icon":"","clevel":"0","name":"戏曲"},{"id":19,"icon":"","clevel":"0","name":"财经"},{"id":20,"icon":"","clevel":"0","name":"科技"},{"id":21,"icon":"","clevel":"0","name":"汽车"},{"id":22,"icon":"","clevel":"0","name":"体育"},{"id":23,"icon":"","clevel":"0","name":"校园"},{"id":24,"icon":"","clevel":"0","name":"游戏"},{"id":25,"icon":"","clevel":"0","name":"动漫"},{"id":26,"icon":"","clevel":"0","name":"广播剧"},{"id":27,"icon":"","clevel":"0","name":"主播"},{"id":28,"icon":"","clevel":"0","name":"电影"},{"id":29,"icon":"","clevel":"0","name":"声价百万"},{"id":30,"icon":"","clevel":"0","name":"旅游"},{"id":31,"icon":"","clevel":"0","name":"互动"},{"id":80,"icon":"","clevel":"0","name":"自媒体"},{"id":81,"icon":"","clevel":"0","name":"品牌电台"},{"id":82,"icon":"","clevel":"0","name":"古典音乐"},{"id":83,"icon":"","clevel":"0","name":"悬疑"},{"id":84,"icon":"","clevel":"0","name":"怀旧"},{"id":85,"icon":"","clevel":"0","name":"小清新"},{"id":86,"icon":"","clevel":"0","name":"励志"},{"id":87,"icon":"","clevel":"0","name":"无聊"},{"id":88,"icon":"","clevel":"0","name":"听觉营养"},{"id":89,"icon":"","clevel":"0","name":"宅着"},{"id":90,"icon":"","clevel":"0","name":"开车"},{"id":91,"icon":"","clevel":"0","name":"在路上"},{"id":92,"icon":"","clevel":"0","name":"工作"},{"id":93,"icon":"","clevel":"0","name":"运动"},{"id":94,"icon":"","clevel":"0","name":"早晨"},{"id":95,"icon":"","clevel":"0","name":"洗澡"},{"id":96,"icon":"","clevel":"0","name":"睡前"},{"id":97,"icon":"","clevel":"0","name":"播客"},{"id":98,"icon":"","clevel":"0","name":"有声小说"},{"id":99,"icon":"","clevel":"0","name":"凤凰独家"},{"id":100,"icon":"","clevel":"0","name":"凤凰汇"},{"id":101,"icon":"","clevel":"0","name":"谈话"},{"id":102,"icon":"","clevel":"0","name":"评书曲艺"},{"id":103,"icon":"","clevel":"0","name":"文史军事"},{"id":104,"icon":"","clevel":"0","name":"亲子"},{"id":105,"icon":"","clevel":"0","name":"财经科技"},{"id":106,"icon":"","clevel":"0","name":"电台直播"},{"id":107,"icon":"","clevel":"0","name":"粤语"},{"id":108,"icon":"","clevel":"0","name":"生活百科"},{"id":109,"icon":"","clevel":"0","name":"URadio"},{"id":110,"icon":"","clevel":"0","name":"专题"}],"data":{"username":"15882345446","uid":121,"password":"e10adc3949ba59abbe56e057f20f883e","niname":"AfmQW38G","toppic":""},"errorno":"0","errormsg":"success"}
	}
	/**
	 * 用户登录
	 * @throws Exception 
	 */
	@Test
	public void user_login() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "user_login";
		//分类点播
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("username", "15882345446");
		post.addParameter("password", "123456");
		String result = doPost(post);
		//{"data":{"username":"15882345446","uid":121,"password":"e10adc3949ba59abbe56e057f20f883e","niname":"AfmQW38G","toppic":""},"errormsg":"登录成功","errorno":"0"}
	}
	/**
	 * 用户重置密码
	 * @throws Exception 
	 */
	@Test
	public void change_pass() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "change_pass";
		//分类点播
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("username", "15882345446");
		post.addParameter("verify_code", "979109");
		post.addParameter("verify_list", "d4825425350e18709d27325799606899");
		post.addParameter("password", "123456");
		post.addParameter("repassword", "123456");
		
		String result = doPost(post);
		//{"data":{"username":"15882345446","uid":121,"password":"e10adc3949ba59abbe56e057f20f883e","niname":"AfmQW38G","toppic":""},"errormsg":"success","errorno":"0"}
	}
	/**
	 * 用户修改头像
	 * @throws Exception 
	 */
	@Test
	public void change_topic() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "change_topic";
		File file = new File("D://temp/jing.png");
	    String telephone = "15882345446";
		String result = doPost( post, Apitype, file, telephone);
		//{"errorno":"0","errormsg":"success"}
	}
	/**
	 * 用户重置手机
	 * @throws Exception 
	 */
	@Test
	public void change_tel() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "change_tel";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("tel", "15882345446");
		post.addParameter("password", "123456");
		post.addParameter("uid", "121");
		
		String result = doPost(post);
		//{"data":{"username":"15882345447","uid":121,"password":"e10adc3949ba59abbe56e057f20f883e","niname":"AfmQW38G","toppic":"http://smaradio1.changhong.com/img/avatar/2016/head15882345446_04484.png"},"errorno":"0","errormsg":"更改手机号码成功"}
	}
	/**
	 * 用户修改昵称
	 * @throws Exception 
	 */
	@Test
	public void change_niname() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "change_niname";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("username", "15882345446");
		post.addParameter("niname", URLEncoder.encode("小虎", "UTF-8"));
		
		String result = doPost(post);
		//{"data":{"username":"15882345446","uid":121,"password":"e10adc3949ba59abbe56e057f20f883e","niname":"小虎","toppic":"http://smaradio1.changhong.com/img/avatar/2016/head15882345446_04484.png"},"errorno":"0","errormsg":"昵称修改成功"}
	}
	
	public String doPost(PostMethod post) throws Exception{
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid",userid);
		post.addParameter("devicemac", devicemac);
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
				return null;
			}else{
				ds = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
				System.out.println(ds);
				return ds;
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
		return null; 
	}
	
	public String doPost(PostMethod post,String Apitype,File file,String telephone) throws Exception{
		Part[] parts = {
		    	 //new StringPart("text_content", "大叔大叔大叔大叔的萨达十大萨达十大","utf-8"),
				//new StringPart("status", URLEncoder.encode(status, "utf-8"),
				new StringPart("apiid", apiid),
				new StringPart("apikey", apikey),
				new StringPart("dtype", dtype),
				new StringPart("userid",userid),
				new StringPart("userid",userid),
		    	new StringPart("Apitype", Apitype),
		    	new StringPart("username", telephone),
		        new FilePart("toppic", file)
		    };  
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
				return null;
			}else{
				ds = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
				System.out.println(ds);
				return ds;
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
		return null; 
	}
}
