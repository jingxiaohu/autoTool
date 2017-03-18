package com.jxh.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

import com.jxh.Log.BaseLog;
import com.jxh.util.RandomUtil;

/**
 * 图片处理工具类
 * @author jingxiaohu
 * 2017-3-9
 */
public class ImgUtil extends BaseLog{
	/**
	 * 图片指定宽高保存 
	 * @param toSavePath:目标图片存储目录 例如：/opt/img
	 * @param imageIn:源图片流
	 * @param width:目标图片宽
	 * @param height:目标图标高
	 * @param imgType:源图片类型 例如: jpeg 、 jpg 、png、gif
	 * @throws IOException
	 */
	public static String ImgSize(String toSavePath,InputStream imageIn,int width,int height,String imgType) throws IOException{
		//图片文件名称  命名规则  宽_高
		String imageName = RandomUtil.return16()+"_"+width+"_"+height+"."+imgType;
		Thumbnails.of(imageIn).size(width, height).toFile(toSavePath+File.separator+imageName);
		return imageName;
	}
	
	/**
	 * 图片指定压缩比例保存 
	 * @param toSavePath:目标图片存储目录 例如：/opt/img
	 * @param imageIn:源图片流
	 * @param scaleWidth:目标图片宽缩放比例
	 * @param scaleHeight:目标图标高缩放比例
	 * @param imgType:源图片类型 例如: jpeg 、 jpg 、png、gif
	 * @throws IOException
	 */
	public static String ImgScale(String toSavePath,InputStream imageIn,Double scaleWidth,Double scaleHeight,String imgType) throws IOException{
		//图片文件名称 
		String imageName = RandomUtil.return16()+"."+imgType;
		Thumbnails.of(imageIn).scale(scaleWidth, scaleHeight).toFile(toSavePath+File.separator+imageName);
		return imageName;
	}
	
}
