/**  
* @Title: IntimesApkWriteChannel.java
* @Package qz.test.util
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年4月1日 下午3:31:13
* @version V1.0  
*/ 
package qz.test.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.junit.Test;

/**
 * 中时代appsdk自动打包 写文件
 * @ClassName: IntimesApkWriteChannel
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年4月1日 下午3:31:13
 *
 */
public class IntimesApkWriteChannel {
	private String encoding = "GBK";  
	private String FilefirstName = "assets"; 
	private String FileDestName = "intimes";
	private String ApkSrcPath = "testapk";
	//apk解压压缩临时路径
	private String TempPath = "apktemp";
	@Test
	public void test_junit() throws Exception{
		  String path = "";
		  String projectpath_root = "";
		  URL apk_path = TestEXE.class.getClassLoader().getResource("");
		  if(apk_path != null){
			  path = apk_path.getPath();
			  projectpath_root = path.substring(1,path.indexOf("WEB-INF")-1);
			  path =projectpath_root+File.separator+ApkSrcPath;
			  path = path.replace("/", File.separator)+File.separator; 
			  System.out.println(path);
		  }
		MakeNewApk("23_79",path+"quzhuan.apk","quzhuan", projectpath_root.replace("/", File.separator));
	}
	
	
	/**
	 * 自动打包写文件
	 * @throws IOException 
	 */
	public void MakeNewApk(String data,String zipFilepath,String apkname, String projectpath_root) throws IOException{
		//解压  D:\android\AndroidProject\intimes_app\webapp\apktemp\23_79
		String apksavepath = projectpath_root+File.separator+TempPath;
		String destDir = apksavepath+File.separator+data;
		unzip(zipFilepath,destDir);
		//写文件
		writeChannelToFile(destDir, data);
		//压缩成apk
		zip(destDir, apksavepath+File.separator+apkname+".apk");
		//删除临时文件夹中的 解压文件
		
	}
	
	
	
	/**
	 * 写渠道id_游戏ID字符串到配置文件夹下面 assets
	* @Title: writeChannelToFile
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param basepath : assets/intimes
	* @param @param data: 渠道ID_游戏ID
	* @param @throws IOException    设定文件
	* @return void    返回类型
	* 例如：23_79.txt   渠道ID_游戏ID
	* @throws
	 */
	public void writeChannelToFile(String basepath,String data) throws IOException{ 
		//需要写入文件的文件夹地址
		String filepath = basepath+File.separator+FilefirstName+File.separator+FileDestName;
    	File filepath_file = new File(filepath);
    	if(!filepath_file.exists()){
    		filepath_file.mkdirs();
    	}
    	String filename = filepath+File.separator+data+".txt";
    	File file = new File(filename);
    	if(!file.exists()){
    		file.setWritable(true);
			//创建文件
    		file.createNewFile();	
    	}
	}
	
	
	
	/****************下面进行压缩和解压
	 * @throws IOException ********************/
   
    
    // 压缩  
    public  void zip(String srcPathname, String zipFilepath)  
            throws BuildException, RuntimeException, IOException {   
        File file = new File(srcPathname);  
        if (!file.exists())  
            throw new RuntimeException("source file or directory "  
                    + srcPathname + " does not exist.");  
  
        Project proj = new Project();  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(proj);  
        // 判断是目录还是文件  
        if (file.isDirectory()) {  
            fileSet.setDir(file);  
            // ant中include/exclude规则在此都可以使用  
            // 比如:  
            // fileSet.setExcludes("**/*.txt");  
            // fileSet.setIncludes("**/*.xls");  
        } else {  
            fileSet.setFile(file);  
        }  
  
        Zip zip = new Zip();  
        zip.setProject(proj);  
        zip.setDestFile(new File(zipFilepath));  
        zip.addFileset(fileSet);  
        zip.setEncoding(encoding);  
        zip.execute();  
        //删除临时文件夹中的 解压文件
        FileUtils.deleteDirectory(new File(srcPathname));
    }  
  
    // 解压缩  
    public  void unzip(String zipFilepath, String destDir)  
            throws BuildException, RuntimeException {  
        if (!new File(zipFilepath).exists())  
            throw new RuntimeException("zip file " + zipFilepath  
                    + " does not exist.");  
  
        Project proj = new Project();  
        Expand expand = new Expand();  
        expand.setProject(proj);  
        expand.setTaskType("unzip");  
        expand.setTaskName("unzip");  
        expand.setEncoding(encoding);  
  
        expand.setSrc(new File(zipFilepath));  
        expand.setDest(new File(destDir));  
        expand.setOverwrite(true);//是否覆盖   
        expand.execute();  
    }  
}
