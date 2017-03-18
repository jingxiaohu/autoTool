/**  
* @Title: TestEXE.java
* @Package qz.test.actiontest
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年4月1日 上午10:48:56
* @version V1.0  
*/ 
package qz.test.util;

import java.io.File;
import java.net.URL;
import java.security.KeyStore;

import org.junit.Test;

/**
 * @ClassName: TestEXE
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年4月1日 上午10:48:56
 *
 */
public class TestEXE {
	@Test
	public void doExe(){
		  Runtime rn = Runtime.getRuntime();
		  Process process = null;
		  try {
			  //String sys_path = System.getProperty("user.dir");
			  //testapk/ihiyo.com.keystore
			  ///D:/android/AndroidProject/intimes_app/webapp/WEB-INF/classes/
			  URL apk_path = TestEXE.class.getClassLoader().getResource("");
			  String path = "";
			  if(apk_path != null){
				  path = apk_path.getPath();
				  path = path.substring(1,path.indexOf("WEB-INF"))+"testapk"+File.separator;
				  System.out.println(path);
			  }
			  KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			  
			  String command = "jarsigner ";
			  command += "–verbose –keystore %sihiyo.com.keystore –signedjar %shiyo_android.apk %sok.apk %smine.keystore";
			  command = String.format(command, path,path,path,path);
			  System.out.println(command);
			  process = rn.exec(command);
			  System.out.println(process.toString());
			  System.out.println("");
			  /*InputStream fis = process.getInputStream();
	          BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	          String line = null;
	          while ((line = br.readLine()) != null) {
	               System.out.println(line);
	           }*/
		  } catch (Exception e) {
			  e.printStackTrace();
		      System.out.println("Error win exec!");
		  }finally{
			  if(process != null){
				  process.destroy();
			  }
		  }
	}

}
