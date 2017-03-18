/**  
* @Title: TestApkAuto.java
* @Package qz.test.actiontest
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年4月1日 下午1:39:31
* @version V1.0  
*/ 
package qz.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.junit.Test;

import sun.misc.BASE64Encoder;
import sun.security.pkcs.PKCS8Key;

/**
 * @ClassName: TestApkAuto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年4月1日 下午1:39:31
 *
 */
public class TestApkAuto {
	    @Test
	    public void dd() throws Exception{ 
	    	  URL apk_path = TestEXE.class.getClassLoader().getResource("");
			  String path = "";
			  if(apk_path != null){
				  path = apk_path.getPath();
				  path = path.substring(1,path.indexOf("WEB-INF"))+"testapk";
				  path = path.replace("/", File.separator)+File.separator; 
				  System.out.println(path);
			  }
	    	testMakeKeyStore(path+"android.keystore","jingxiaohu","123456","123456");
	    }
	    public  void testMakeKeyStore(String...paramArrayOfString ) throws Exception{
				    if (paramArrayOfString.length < 4) {
				      System.out.println("请提供keystore文件名、别名及密码！示例：java -jar ks2x509.jar xyz.keystore myAlias password1 password2");
				      return; 
				    } 
				    String str1 = paramArrayOfString[0].trim();//xyz.keystore
				    String str2 = paramArrayOfString[1].trim();//xyz.keystore 别名  myAlias
				    String str3 = paramArrayOfString[2].trim();//password1
				    String str4 = paramArrayOfString[3].trim();//password2
				    File localFile1 = new File(str1);
				    String str5 = localFile1.getAbsolutePath();
				    File localFile2 = new File(str5);
				    str5 = localFile2.getParent();

				    String str6 = localFile1.getName().substring(0, localFile1.getName().lastIndexOf("."));

				    KeyStore localKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				    FileInputStream localFileInputStream;
				    try { 
				    	localFileInputStream = new FileInputStream(localFile1);
				    } catch (Exception localException) {
				      System.out.println("打开keystore文件时出错！");
				      return;
				    }

				    System.out.println("正在开始从keystore文件里提取公私钥内容....");
				    localKeyStore.load(localFileInputStream, str3.toCharArray());

				    BASE64Encoder localBASE64Encoder = new BASE64Encoder();

				    Certificate localCertificate = localKeyStore.getCertificate(str2);
				    Key localKey = localKeyStore.getKey(str2, str4.toCharArray());

				    KeyPair localKeyPair = new KeyPair(localCertificate.getPublicKey(), (PrivateKey)localKey);
				    System.out.println("正在写入*.x509.pem文件！");
				    FileOutputStream localFileOutputStream1 = new FileOutputStream(str5 + File.separator + str6 + ".x509.pem");
				    localFileOutputStream1.write("-----BEGIN CERTIFICATE-----\n".getBytes());
				    localFileOutputStream1.write(localBASE64Encoder.encode(localCertificate.getEncoded()).getBytes());
				    localFileOutputStream1.write("\n".getBytes());
				    localFileOutputStream1.write("-----END CERTIFICATE-----".getBytes());
				    localFileOutputStream1.close();

				    System.out.println("正在写入*.pk8文件！");
				    PKCS8Key localPKCS8Key = (PKCS8Key)localKeyPair.getPrivate();
				    FileOutputStream localFileOutputStream2 = new FileOutputStream(str5 + File.separator + str6 + ".pk8");
//				    localFileOutputStream2.write(localKeyPair.getPrivate().getEncoded());
				    localFileOutputStream2.write(localPKCS8Key.getEncoded());
				    localFileOutputStream2.close();
				    System.out.println("转化完成！");
				  }
	}
	
