package com.park.bean;

import java.io.*;
import java.util.*;

//user_feedback
@SuppressWarnings({"serial"})
public class User_feedback implements Cloneable , Serializable{

    //public static String[] carrays ={"id","ui_id","content","ctime","utime","note"};

    public long id;//BIGINT    主键ID
    public long ui_id;//BIGINT    用户主键ID
    public String content="";//VARCHAR    反馈信息
    public java.util.Date ctime=new java.util.Date();//DATETIME    创建时间
    public java.util.Date utime=new java.util.Date();//DATETIME    更新时间
    public String note="";//VARCHAR    备注



    public long getId(){
        return id;
    }

    public void setId(long value){
        this.id= value;
    }

    public long getUi_id(){
        return ui_id;
    }

    public void setUi_id(long value){
        this.ui_id= value;
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

    public java.util.Date getCtime(){
        return ctime;
    }

    public void setCtime(java.util.Date value){
    	if(value == null){
           value = new java.util.Date();
        }
        this.ctime= value;
    }

    public java.util.Date getUtime(){
        return utime;
    }

    public void setUtime(java.util.Date value){
    	if(value == null){
           value = new java.util.Date();
        }
        this.utime= value;
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



    public static User_feedback newUser_feedback(long id, long ui_id, String content, java.util.Date ctime, java.util.Date utime, String note) {
        User_feedback ret = new User_feedback();
        ret.setId(id);
        ret.setUi_id(ui_id);
        ret.setContent(content);
        ret.setCtime(ctime);
        ret.setUtime(utime);
        ret.setNote(note);
        return ret;    
    }

    public void assignment(User_feedback user_feedback) {
        long id = user_feedback.getId();
        long ui_id = user_feedback.getUi_id();
        String content = user_feedback.getContent();
        java.util.Date ctime = user_feedback.getCtime();
        java.util.Date utime = user_feedback.getUtime();
        String note = user_feedback.getNote();

        this.setId(id);
        this.setUi_id(ui_id);
        this.setContent(content);
        this.setCtime(ctime);
        this.setUtime(utime);
        this.setNote(note);

    }

    @SuppressWarnings("unused")
    public static void getUser_feedback(User_feedback user_feedback ){
        long id = user_feedback.getId();
        long ui_id = user_feedback.getUi_id();
        String content = user_feedback.getContent();
        java.util.Date ctime = user_feedback.getCtime();
        java.util.Date utime = user_feedback.getUtime();
        String note = user_feedback.getNote();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(User_feedback user_feedback){
        long id = user_feedback.getId();
        long ui_id = user_feedback.getUi_id();
        String content = user_feedback.getContent();
        java.util.Date ctime = user_feedback.getCtime();
        java.util.Date utime = user_feedback.getUtime();
        String note = user_feedback.getNote();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("id",id);
        _ret.put("ui_id",ui_id);
        _ret.put("content",content);
        _ret.put("ctime",ctime);
        _ret.put("utime",utime);
        _ret.put("note",note);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public User_feedback clone2(){
        try{
            return (User_feedback) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
