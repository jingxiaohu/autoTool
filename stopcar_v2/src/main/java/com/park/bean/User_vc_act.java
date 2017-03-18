package com.park.bean;

import java.io.*;
import java.util.*;

//user_vc_act
@SuppressWarnings({"serial"})
public class User_vc_act implements Cloneable , Serializable{

    //public static String[] carrays ={"id","ui_id","order_id","order_type","act_type","money","ctime","state","is_add","note"};

    public long id;//BIGINT    主键ID
    public long ui_id;//BIGINT    用户ID
    public String order_id="";//VARCHAR    订单ID
    public int order_type;//INT    下单类型0:普通下单1：预约下单2：租赁包月订单
    public int act_type;//INT    用户行为0：订单支付1：充值
    public int money;//INT    交易金额（单位分）
    public java.util.Date ctime=new java.util.Date();//DATETIME    创建时间
    public int state;//INT    处理状态0：未处理1：已处理
    public int is_add;//INT    增加还是减少0：减少1：增加
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

    public String getOrder_id(){
        return order_id;
    }

    public void setOrder_id(String value){
    	if(value == null){
           value = "";
        }
        this.order_id= value;
    }

    public int getOrder_type(){
        return order_type;
    }

    public void setOrder_type(int value){
        this.order_type= value;
    }

    public int getAct_type(){
        return act_type;
    }

    public void setAct_type(int value){
        this.act_type= value;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int value){
        this.money= value;
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

    public int getState(){
        return state;
    }

    public void setState(int value){
        this.state= value;
    }

    public int getIs_add(){
        return is_add;
    }

    public void setIs_add(int value){
        this.is_add= value;
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



    public static User_vc_act newUser_vc_act(long id, long ui_id, String order_id, int order_type, int act_type, int money, java.util.Date ctime, int state, int is_add, String note) {
        User_vc_act ret = new User_vc_act();
        ret.setId(id);
        ret.setUi_id(ui_id);
        ret.setOrder_id(order_id);
        ret.setOrder_type(order_type);
        ret.setAct_type(act_type);
        ret.setMoney(money);
        ret.setCtime(ctime);
        ret.setState(state);
        ret.setIs_add(is_add);
        ret.setNote(note);
        return ret;    
    }

    public void assignment(User_vc_act user_vc_act) {
        long id = user_vc_act.getId();
        long ui_id = user_vc_act.getUi_id();
        String order_id = user_vc_act.getOrder_id();
        int order_type = user_vc_act.getOrder_type();
        int act_type = user_vc_act.getAct_type();
        int money = user_vc_act.getMoney();
        java.util.Date ctime = user_vc_act.getCtime();
        int state = user_vc_act.getState();
        int is_add = user_vc_act.getIs_add();
        String note = user_vc_act.getNote();

        this.setId(id);
        this.setUi_id(ui_id);
        this.setOrder_id(order_id);
        this.setOrder_type(order_type);
        this.setAct_type(act_type);
        this.setMoney(money);
        this.setCtime(ctime);
        this.setState(state);
        this.setIs_add(is_add);
        this.setNote(note);

    }

    @SuppressWarnings("unused")
    public static void getUser_vc_act(User_vc_act user_vc_act ){
        long id = user_vc_act.getId();
        long ui_id = user_vc_act.getUi_id();
        String order_id = user_vc_act.getOrder_id();
        int order_type = user_vc_act.getOrder_type();
        int act_type = user_vc_act.getAct_type();
        int money = user_vc_act.getMoney();
        java.util.Date ctime = user_vc_act.getCtime();
        int state = user_vc_act.getState();
        int is_add = user_vc_act.getIs_add();
        String note = user_vc_act.getNote();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(User_vc_act user_vc_act){
        long id = user_vc_act.getId();
        long ui_id = user_vc_act.getUi_id();
        String order_id = user_vc_act.getOrder_id();
        int order_type = user_vc_act.getOrder_type();
        int act_type = user_vc_act.getAct_type();
        int money = user_vc_act.getMoney();
        java.util.Date ctime = user_vc_act.getCtime();
        int state = user_vc_act.getState();
        int is_add = user_vc_act.getIs_add();
        String note = user_vc_act.getNote();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("id",id);
        _ret.put("ui_id",ui_id);
        _ret.put("order_id",order_id);
        _ret.put("order_type",order_type);
        _ret.put("act_type",act_type);
        _ret.put("money",money);
        _ret.put("ctime",ctime);
        _ret.put("state",state);
        _ret.put("is_add",is_add);
        _ret.put("note",note);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public User_vc_act clone2(){
        try{
            return (User_vc_act) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
