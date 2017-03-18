package com.park.action.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

/**
 * 
 * @Title: 读取Android APK的包名
 * @author 敬小虎
 * @date 2014年3月30日 下午1:59:09
 * @version V1.0
 */
public class ReadPackage {
	// ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
	public static float complexToFloat(int complex) {
		return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };
	
	/**
	 * 解压 zip 文件(apk可以当成一个zip文件)，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现
	 * @param apkUrl
	 * @return
	 */
	public static String[] returnPackageName(String basepath,String apkUrl) {
		// [0]:版本号;[1]包名
		String[] st = new String[2];
		ZipFile zipFile = null;
		File file = null;
		//首先判断 该URL是否是一个 HTTP链接
		if(apkUrl == null || "".equalsIgnoreCase(apkUrl)){
			return null;
		}
		if(apkUrl.contains("http")){ 
			try {
				URL url = new URL(apkUrl);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				file = new File(basepath+File.separator+sf.format(new Date())+".apk");
				if(!file.exists()){
					file.createNewFile();
				}
				FileUtils.copyURLToFile(url, file);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		try {
			if(file == null){
				file = new File(apkUrl);
			}
			zipFile = new ZipFile(file);
			Enumeration<?> enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (zipEntry.isDirectory())
					continue;
					if ("AndroidManifest.xml".equals(zipEntry.getName())) {
						try {
							AXmlResourceParser parser = new AXmlResourceParser();
							parser.open(zipFile.getInputStream(zipEntry));
							while (true) {
								int type = parser.next();
								if (type == XmlPullParser.END_DOCUMENT) {
									break;
								}
								switch (type) {
								case XmlPullParser.START_TAG: {
									for (int i = 0; i != parser
											.getAttributeCount(); ++i) {
										if ("versionName".equals(parser
												.getAttributeName(i))) {
											st[0] = getAttributeValue(parser, i);
										} else if ("package".equals(parser
												.getAttributeName(i))) {
											st[1] = getAttributeValue(parser, i);
										}
									}
								}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		} catch (IOException e) {
		} finally {
			if(zipFile != null){
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return st;
	}
	
	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}
	
	
	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}
}
