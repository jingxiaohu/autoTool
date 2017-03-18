package qz.test.util;

  
  
/**  
 * 利用Apache ant.jar中的ant包进行Zip压缩和解压  
 * 这个更为简单  
 */  
public class jxhZipByAntTool {   
	public static String APK_NAME= "quzhuan"; 
	public static void main(String[] args) throws Exception { 
		//"d:/platform.x509.pem","d:/platform.pk8", "d:/todaytest/quzhuan.apk", "d:/quzhuanzip/quzhuan.apk"
		String str = "E:\\jxh\\workspace\\quzhuan\\webapp\\secretkey\\";
//		String str = "D:\\Users\\Administrator\\workspace\\quzhuan\\webapp\\secretkey\\";
		String public_key = str+"testkey.x509.pem";
		String self_key = str+"testkey.pk8";
		String filePathSrc = "F:\\quzhuan_src.apk";
		String filePathDest = "F:\\quzhuan.apk";
    	SignApk.ReSignature(public_key, self_key, filePathSrc, filePathDest);
	}
}  
