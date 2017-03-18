package qz.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.park.core.Constants;
import com.park.exception.QzException;

/**
 * 解压APK 打包APK
 * @author Administrator
 *
 */
public class ZipUtil {

public static Logger logger = Logger.getLogger(JXH_ZipUtil.class);
    private static final String BASE_DIR = "";  
    //一次性读取的字节数
    private static final int BUFFER=1024;
    /**符号"/"用来作为目录标识判断符*/  
    private static final String PATH = "/";  
    /**需要添加文件的目录assets*/  
      
    
    
    
    
    
    /**  
    * 解压缩zip文件   
    * @param src_path_fileName 要解压的文件名 包含路径 如："c:\\test.zip"  
    * @param desc_path 解压后存放文件的路径 如："c:\\temp"  
    * @throws Exception  
    */    
    public static void unZip(String src_path_fileName, String desc_path) throws Exception{      
       ZipFile zipFile = new ZipFile(src_path_fileName);     
       Enumeration<?> emu = zipFile.getEntries();  
       while(emu.hasMoreElements()){    
            ZipEntry entry = (ZipEntry) emu.nextElement();    
            if (entry.isDirectory()){    
                new File(desc_path+entry.getName()).mkdirs();    
                continue;    
            }    
            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));    
               
            File file = new File(desc_path + entry.getName());    
            File parent = file.getParentFile();    
            if(parent != null && (!parent.exists())){    
                parent.mkdirs();    
            }    
            FileOutputStream fos = new FileOutputStream(file);    
            BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);    
        
            byte [] buf = new byte[BUFFER];    
            int len = 0;    
            while((len=bis.read(buf,0,BUFFER))!=-1){    
                fos.write(buf,0,len);    
            }    
            bos.flush();    
            bos.close();    
            bis.close();    
           }    
           zipFile.close();    
    }    
      
    /** 
     * 压缩文件 
     *  
     * @param srcFile 
     * @param destPath 
     * @throws Exception 
     */  
    public static void compress(String srcFile, String destPath) throws Exception {  
        compress(new File(srcFile), new File(destPath));  
    }  
      
    /** 
     * 压缩 
     *  
     * @param srcFile 
     *            源路径 
     * @param destPath 
     *            目标路径 
     * @throws Exception 
     */  
    public static void compress(File srcFile, File destFile) throws Exception {  
       // 对输出文件做CRC32校验  
        try {
			CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());  
			ZipOutputStream zos = new ZipOutputStream(cos);  
			compress(srcFile, zos, BASE_DIR);  
  
			zos.flush();  
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
    }  
      
    /** 
     * 压缩 
     * @param srcFile  源路径 
     * @param zos ZipOutputStream 
     * @param basePath  压缩包内相对路径 
     * @throws Exception 
     */  
    private static void compress(File srcFile, ZipOutputStream zos,String basePath) throws Exception {  
    	System.out.println(srcFile.getAbsolutePath()); 
        if (srcFile.isDirectory()) {  
            compressDir(srcFile, zos, basePath);  
        } else {  
            compressFile(srcFile, zos, basePath);  
        }  
    }  
      
    /** 
     * 压缩目录 
     *  
     * @param dir 
     * @param zos 
     * @param basePath 
     * @throws Exception 
     */  
    private static void compressDir(File dir, ZipOutputStream zos,String basePath) throws Exception {  
        File[] files = dir.listFiles();  
        // 构建空目录  
        if (files.length < 1) {  
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + File.separator);  
            zos.putNextEntry(entry);  
            zos.closeEntry();  
        }  
        String dirName = "";  
        String path = "";  
        for (File file : files) {  
            //当父文件包名为空时，则不把包名添加至路径中（主要是解决压缩时会把父目录文件也打包进去）  
            if(basePath!=null && !"".equals(basePath)){  
                dirName=dir.getName();   
            }  
            path = basePath + dirName + File.separator;  
            // 递归压缩  
            compress(file, zos, path);  
        }  
    }  
  
    /** 
     * 文件压缩 
     *  
     * @param file 
     *            待压缩文件 
     * @param zos 
     *            ZipOutputStream 
     * @param dir 
     *            压缩文件中的当前路径 
     * @throws Exception 
     */  
    private static void compressFile(File file, ZipOutputStream zos, String dir)throws Exception {  
        /** 
         * 压缩包内文件名定义 
         * <pre> 
         * 如果有多级目录，那么这里就需要给出包含目录的文件名 
         * 如果用WinRAR打开压缩包，中文名将显示为乱码 
         * </pre> 
         */  
        if("/".equals(dir))dir="";  
        else if(dir.startsWith("/"))dir=dir.substring(1,dir.length());  
          
        ZipEntry entry = new ZipEntry(dir + file.getName());  
        zos.putNextEntry(entry);  
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
        int count;  
        byte data[] = new byte[BUFFER];  
        while ((count = bis.read(data, 0, BUFFER)) != -1) {  
            zos.write(data, 0, count);  
        }  
        bis.close();  
  
        zos.closeEntry();  
    }  
      
    /**
     * 向assets\referer文件夹中写入推荐人用户ID
     * @param ui_id
     * @param LAST_TARGET_PATH : 压缩并添加了文件之后的路径
     * @throws Exception
     */
    public synchronized static void writeUserId(long ui_id,String APK_NAME) throws Exception{ 
    	String base_path = "D://";
    	//解压前的绝对路径
    	String source_file_PATH = "D://apk_test/src/"+APK_NAME;
    	//解压后存放的绝对路径
    	String target_PATH = "D://apk_test/desc/";
    	//首先没有建立的目录先进行目录建立
    	File target_PATH_file = new File(target_PATH);
    	if(!target_PATH_file.exists()){
    		target_PATH_file.mkdirs();
    	}
    	
    	//解压
    	unZip(source_file_PATH,target_PATH); 
    	
    	
    	/*String filepath = path_jy_end+SIGN_PATH_NAME+File.separator+"referer";
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
    	//压缩文件
    	String start_apk = xxx_mid+File.separator+String.valueOf(ui_id)+".apk";
    	compress(path_jy_end,start_apk); 
    	//删除之前解压后的文件
    	deleteDirectory(BASE_PATH+JY_TARGET_PATH);
    	//对压缩过后的APK进行重新签名
    	//"d:/platform.x509.pem","d:/platform.pk8", "d:/todaytest/quzhuan.apk", "d:/quzhuanzip/quzhuan.apk"
    	SignApk.ReSignature(public_key, self_key, start_apk, LAST_PATH);
    	//删除文件
    	FileUtils.deleteQuietly(new File(start_apk));*/
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
    
    /**
     * 删除自己需要修改的文件名
     * @param args
     * @throws Exception 
     */
    public static void updateFileName(String path) throws Exception{
    	File file = new File(path);
/*    	Reader reader = new FileReader(file);
    	BufferedReader br = new BufferedReader(reader);
    	String str = null;
    	while((str=br.readLine()) != null){
    		System.out.println(str); 
    	}
    	br.close();
    	reader.close();
    	*/
    	List<String> list = FileUtils.readLines(file, Constants.SYSTEM_CHARACTER);
    	int index = 0;
    	if(list != null && list.size() > 0){
    		for (int i = 0; i < list.size(); i++) {
    			if(list.get(i).indexOf("assets") != -1){
					 index = i;
				}
			}
    	}
    	list.remove(index);list.remove(index+1);
    	FileUtils.writeLines(file, list);
    	
    }
    
    
    /*****
     * 用户完善资料后就给他生成一个推荐的APK包
     * @param args
     * @throws Exception
     */
  public static  void makeRecommendAPK(long ui_id) throws QzException{
		// String realpath =
		// ServletActionContext.getServletContext().getRealPath("/");
		String realpath = Constants.SYSTEM_ROOT_PATH;
		JXH_ZipUtil.setPublic_key(realpath + "secretkey" + File.separator+ "platform.x509.pem");
		JXH_ZipUtil.setSelf_key(realpath + "secretkey" + File.separator+ "platform.pk8");
		String start_path = "quzhuan_src";// 相对目录
		String last_path = realpath + "quzhuan_dest" + File.separator + ui_id;// 重新签名后的地址
		try {
			JXH_ZipUtil.writeUserId(ui_id, realpath, start_path, start_path+ File.separator + ui_id, last_path);
		} catch (Exception e) {
			System.out.println("用户完善资料后就给他生成一个推荐的APK包 失败"); 
			e.printStackTrace();
			throw new QzException("用户完善资料后就给他生成一个推荐的APK包 失败");
		}
  }  
    
    
    
    public static void main(String[] args)throws Exception{  
    	long ui_id = 1895949L;
    	writeUserId(ui_id,"quzhuan.apk");
    }  
}  

