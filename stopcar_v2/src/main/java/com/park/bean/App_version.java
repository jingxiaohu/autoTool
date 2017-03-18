package com.park.bean;

import java.io.*;
import java.util.*;

//app_version
@SuppressWarnings({"serial"})
public class App_version implements Cloneable , Serializable{

    //public static String[] carrays ={"id","cav_version","cav_version_external","cav_version_code","ctime","content","cav_md5","android_url","ios_url","note"};

    public long id;//BIGINT    主键ID
    public String cav_version="";//VARCHAR    渠道版本
    public String cav_version_external="";//VARCHAR    渠道外部版本
    public int cav_version_code;//INT    渠道版本内部编号
    public java.util.Date ctime=new java.util.Date();//DATETIME    创建时间
    public String content="";//TEXT    版本更新内容
    public String cav_md5="";//VARCHAR    版本MD5文件效验码
    public String android_url="";//VARCHAR    Android_app版本升级包URL地址
    public String ios_url="";//VARCHAR    Ios_app版本升级包URL地址
    public String note="";//VARCHAR    备注



    public long getId(){
        return id;
    }

    public void setId(long value){
        this.id= value;
    }

    public String getCav_version(){
        return cav_version;
    }

    public void setCav_version(String value){
    	if(value == null){
           value = "";
        }
        this.cav_version= value;
    }

    public String getCav_version_external(){
        return cav_version_external;
    }

    public void setCav_version_external(String value){
    	if(value == null){
           value = "";
        }
        this.cav_version_external= value;
    }

    public int getCav_version_code(){
        return cav_version_code;
    }

    public void setCav_version_code(int value){
        this.cav_version_code= value;
    }

    public java.util.Date getCtime(){
        return ctime;
    }

    public void setCtime(java.util.Date value){
    	if(value == null){
           value = new java.util.Date();
        }
        this.ctime= value;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String value){
    	if(value == null){
           value = "";
        }
        this.content= value;
    }

    public String getCav_md5(){
        return cav_md5;
    }

    public void setCav_md5(String value){
    	if(value == null){
           value = "";
        }
        this.cav_md5= value;
    }

    public String getAndroid_url(){
        return android_url;
    }

    public void setAndroid_url(String value){
    	if(value == null){
           value = "";
        }
        this.android_url= value;
    }

    public String getIos_url(){
        return ios_url;
    }

    public void setIos_url(String value){
    	if(value == null){
           value = "";
        }
        this.ios_url= value;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String value){
    	if(value == null){
           value = "";
        }
        this.note= value;
    }



    public static App_version newApp_version(long id, String cav_version, String cav_version_external, int cav_version_code, java.util.Date ctime, String content, String cav_md5, String android_url, String ios_url, String note) {
        App_version ret = new App_version();
        ret.setId(id);
        ret.setCav_version(cav_version);
        ret.setCav_version_external(cav_version_external);
        ret.setCav_version_code(cav_version_code);
        ret.setCtime(ctime);
        ret.setContent(content);
        ret.setCav_md5(cav_md5);
        ret.setAndroid_url(android_url);
        ret.setIos_url(ios_url);
        ret.setNote(note);
        return ret;    
    }

    public void assignment(App_version app_version) {
        long id = app_version.getId();
        String cav_version = app_version.getCav_version();
        String cav_version_external = app_version.getCav_version_external();
        int cav_version_code = app_version.getCav_version_code();
        java.util.Date ctime = app_version.getCtime();
        String content = app_version.getContent();
        String cav_md5 = app_version.getCav_md5();
        String android_url = app_version.getAndroid_url();
        String ios_url = app_version.getIos_url();
        String note = app_version.getNote();

        this.setId(id);
        this.setCav_version(cav_version);
        this.setCav_version_external(cav_version_external);
        this.setCav_version_code(cav_version_code);
        this.setCtime(ctime);
        this.setContent(content);
        this.setCav_md5(cav_md5);
        this.setAndroid_url(android_url);
        this.setIos_url(ios_url);
        this.setNote(note);

    }

    @SuppressWarnings("unused")
    public static void getApp_version(App_version app_version ){
        long id = app_version.getId();
        String cav_version = app_version.getCav_version();
        String cav_version_external = app_version.getCav_version_external();
        int cav_version_code = app_version.getCav_version_code();
        java.util.Date ctime = app_version.getCtime();
        String content = app_version.getContent();
        String cav_md5 = app_version.getCav_md5();
        String android_url = app_version.getAndroid_url();
        String ios_url = app_version.getIos_url();
        String note = app_version.getNote();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(App_version app_version){
        long id = app_version.getId();
        String cav_version = app_version.getCav_version();
        String cav_version_external = app_version.getCav_version_external();
        int cav_version_code = app_version.getCav_version_code();
        java.util.Date ctime = app_version.getCtime();
        String content = app_version.getContent();
        String cav_md5 = app_version.getCav_md5();
        String android_url = app_version.getAndroid_url();
        String ios_url = app_version.getIos_url();
        String note = app_version.getNote();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("id",id);
        _ret.put("cav_version",cav_version);
        _ret.put("cav_version_external",cav_version_external);
        _ret.put("cav_version_code",cav_version_code);
        _ret.put("ctime",ctime);
        _ret.put("content",content);
        _ret.put("cav_md5",cav_md5);
        _ret.put("android_url",android_url);
        _ret.put("ios_url",ios_url);
        _ret.put("note",note);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public App_version clone2(){
        try{
            return (App_version) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
