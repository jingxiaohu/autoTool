package qz.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AntZipUtil {
	public static void zipFile(String zipFileName, String inputFileName)
			throws Exception {
		org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(
				new FileOutputStream(zipFileName));
		out.setEncoding("GBK");
		File inputFile = new File(inputFileName);
		zipIt(out, inputFile, "", true);
		out.close();
	}

	/*
	 * 能支持中文的压缩 参数base 开始为"" first 开始为true
	 */
	public static void zipIt(org.apache.tools.zip.ZipOutputStream out, File f,
			String base, boolean first) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (first) {
				first = false;
			} else {
				base = base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				zipIt(out, fl[i], base + fl[i].getName(), first);
			}
		} else {
			if (first) {
				base = f.getName();
			}
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	public static void unZipFile(String unZipFileName, String unZipPath)
			throws Exception {
		org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
				unZipFileName);
		unZipFileByOpache(zipFile, unZipPath);
	}

	/*
	 * 解压文件 unZip为解压路径
	 */
	public static void unZipFileByOpache(org.apache.tools.zip.ZipFile zipFile,
			String unZipRoot) throws Exception, IOException {
		java.util.Enumeration e = zipFile.getEntries();
		org.apache.tools.zip.ZipEntry zipEntry;
		while (e.hasMoreElements()) {
			zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
			InputStream fis = zipFile.getInputStream(zipEntry);
			if (zipEntry.isDirectory()) {
			} else {
				File file = new File(unZipRoot + File.separator
						+ zipEntry.getName());
				File parentFile = file.getParentFile();
				parentFile.mkdirs();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len;
				while ((len = fis.read(b, 0, b.length)) != -1) {
					fos.write(b, 0, len);
				}
				fos.close();
				fis.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		zipFile("d:/temp/folders.zip", "D:/temp/folders");// 压缩入口
		// unZipFile("d:/javaFile.zip","e:/");//解压入口
	}
}
