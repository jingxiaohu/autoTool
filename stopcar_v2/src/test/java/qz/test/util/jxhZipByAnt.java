package qz.test.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
  
  
/**  
 * 利用Apache ant.jar中的ant包进行Zip压缩和解压  
 * 这个更为简单  
 */  
public class jxhZipByAnt {   
	public static Logger logger = Logger.getLogger(jxhZipByAnt.class); 
	public static String APK_NAME= "quzhuan"; 
    public static void writeUserId(long ui_id,long versioncode,String basepath) throws Exception {  
//    	long ui_id = 100002;//用户ID
//    	String basepath = Constants.SYSTEM_ROOT_PATH; 
		String public_key = basepath + "secretkey" + File.separator+ "testkey.x509.pem";
		String self_key =  basepath + "secretkey" + File.separator+ "testkey.pk8";
    	String src_path_file = basepath+File.separator+"quzhuan_src"+File.separator+"quzhuan_src.apk";//源文件APK路径
    	String src_jy_path =  basepath+File.separator+"quzhuan_src"+File.separator+String.valueOf(ui_id);//源文件APK解压路径
    	String dest_ys_path = basepath+File.separator+"quzhuan_dest";//目标文件压缩路径
    	
        //第一：首先解压源文件
    	boolean is_stop = unZip(src_jy_path, src_path_file); 
    	if(is_stop){
    		//停止
    		return;
    	}
        //第二：向解压的文件assets/refer/里面写入TXT
    	writeTXT(src_jy_path+File.separator+"assets",ui_id) ;
        //第三：压缩解压后的文件夹
    	 zip(src_jy_path, dest_ys_path+File.separator+String.valueOf(ui_id)+"_ys.apk");   
       
        //第五：APK重新签名
     	//"d:/platform.x509.pem","d:/platform.pk8", "d:/todaytest/quzhuan.apk", "d:/quzhuanzip/quzhuan.apk"
     	File file = new File(dest_ys_path+File.separator+String.valueOf(ui_id)+".apk");
     	if(file.exists()){
     		file.delete();
     		System.out.println("delete older recommend apk  is ok !"); 
     	}
    	SignApk.ReSignature(public_key, self_key, dest_ys_path+File.separator+String.valueOf(ui_id)+"_ys.apk", dest_ys_path+File.separator+String.valueOf(ui_id)+"_"+versioncode+".apk");
     	//第六:删除签名前的压缩文件
    	FileUtils.deleteQuietly(new File(dest_ys_path+File.separator+String.valueOf(ui_id)+"_ys.apk"));
    	//第四：删除解压后的文件夹
     	deleteDirectory(src_jy_path);
       
    }   
       
    /**  
     * 解压缩  
     * @param destDir 生成的目标目录下   c:/a  
     * @param sourceZip 源zip文件      c:/upload.zip  
     * 结果则是 将upload.zip文件解压缩到c:/a目录下  
     */  
    public static boolean unZip(String destDir,String sourceZip){   
           
         try {   
        	File f = new File(destDir);   
        	if(f.exists()){
        		//停止
             	return true;
            }   
            Project prj1 = new Project();   
               
            Expand expand = new Expand();   
               
            expand.setProject(prj1);   
               
            expand.setSrc(new File(sourceZip));   
               
            expand.setOverwrite(true);//是否覆盖   
           
            expand.setDest(f);   
               
            expand.execute();   
        } catch (Exception e) {   
            logger.error("jxhZipByAnt.unZip(String destDir,String sourceZip) is error", e);
        }
		return false;    
    }   
  
  
    /**  
     * 压缩  
     *   
     * @param sourceFile  
     *            压缩的源文件 如: c:/upload  
     * @param targetZip  
     *            生成的目标文件 如：c:/upload.zip  
     */  
    public static void zip(String sourceFile,String targetZip){   
           
          try {
			Project prj = new Project();   
			     
			  Zip zip = new Zip();   
			     
			  zip.setProject(prj);   
			     
			  zip.setDestFile(new File(targetZip));//设置生成的目标zip文件File对象   
			     
			  FileSet fileSet = new FileSet();   
			     
			  fileSet.setProject(prj);   
			     
			  fileSet.setDir(new File(sourceFile));//设置将要进行压缩的源文件File对象   
			     
			  //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件   
			     
			  //fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件   
			     
			     
			  zip.addFileset(fileSet);   
  
			  zip.execute();
		} catch (BuildException e) {
			 logger.error("jxhZipByAnt.zip(String sourceFile,String targetZip) is error", e);
		}   
  
    }   
    /**
     * 写文件
     * @throws IOException 
     */
    public static void writeTXT(String basepath,long ui_id) throws IOException{ 
    	String filepath = basepath+File.separator+"referer";
    	File filepath_file = new File(filepath);
    	if(!filepath_file.exists()){
    		filepath_file.mkdirs();
    	}
    	String filename = filepath+File.separator+String.valueOf(ui_id)+".txt";
    	File file = new File(filename);
    	if(!file.exists()){
    		file.setWritable(true);
			//创建文件
    		file.createNewFile();	
    	}
    }
    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public  static void deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return ;  
        }  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
            	files[i].delete();  
            } //删除子目录  
            else {  
                deleteDirectory(files[i].getAbsolutePath());  
            }  
        }  
        //删除当前目录  
       dirFile.delete();
         
    } 
}  
