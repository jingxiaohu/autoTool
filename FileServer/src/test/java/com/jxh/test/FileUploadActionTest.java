package com.jxh.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jxh.constant.MyConstant;

/**
 * 文件上传
 * @author jingxiaohu
 *
 */
public class FileUploadActionTest extends BaseActionTest{
	
	@Test  
    public void fileupload() {  
		int dtype = 0;
        Map<String, String> maps = new HashMap<String, String>();  
    	Integer width = 100; //宽 像素
    	Integer height = 200; //高 像素
    	String sign = getSignature(MyConstant.SYSTEM_KEY, dtype);
    	maps.put("sign", sign);
        
        maps.put("width", width+"");  
        maps.put("height", height+"");  
        
        List<File> fileLists = new ArrayList<File>();  
        File  file = new File("D://temp//lience.png");
        System.out.println(file.isFile());
        System.out.println(file.exists());
        if(file.exists() && file.isFile()){
        	fileLists.add(file); 
        }
         
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpPost("http://localhost:8080/v1/fileupload.php", maps, fileLists);  
        System.out.println("reponse content:" + responseContent);  
    }  
  
}
