package qz.test.util;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
/**
 * apache ant  轻松实现文件压缩/解压缩 
 * Title:action
 * Description:描述 
 * Copyright: Copyright (c) 2014
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-5-12
 */
public class ApacheAntZipUtil {  
    public final static String encoding = "GBK";  
  
    // 压缩  
    public static void zip(String srcPathname, String zipFilepath)  
            throws BuildException, RuntimeException {  
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
    }  
  
    // 解压缩  
    public static void unzip(String zipFilepath, String destDir)  
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
