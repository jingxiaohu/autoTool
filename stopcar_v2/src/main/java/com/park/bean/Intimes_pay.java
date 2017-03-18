package com.park.bean;

import java.io.*;
import java.util.*;

//intimes_pay
@SuppressWarnings({"serial"})
public class Intimes_pay implements Cloneable , Serializable{

    //public static String[] carrays ={"ip_id","ip_order_no","ip_alipay_no","ip_source","ip_source_str","ip_version","ip_terminal_type","ip_return_url","ii_id","ip_type","ip_price","ip_createtime","ip_createtime_str","ip_endtime","ip_endtime_str","ip_updatetime","ip_updatetime_str","ip_state","ip_ip","ip_imei","ip_item_type","ip_prop_name","pi_id","ci_id","gg_id","ip_ssid","cd_id","bank_account","ip_note"};

    public long ip_id;//BIGINT    
    public String ip_order_no="";//VARCHAR    
    public String ip_alipay_no="";//VARCHAR    
    public int ip_source;//INT    0:无来源1：支付宝2:微信3：银联
    public String ip_source_str="";//VARCHAR    例如：支付宝、余额宝等等
    public String ip_version="";//VARCHAR    描述当前SDK升级的版本号例如：v1.02
    public int ip_terminal_type;//INT    终端类型：0:web：1:PC方式;wap：2手机WAP;3mobile：手机客户端应用方式(默认)
    public String ip_return_url="";//VARCHAR    
    public long ii_id;//BIGINT    
    public int ip_type;//INT    0:RMB充值
    public double ip_price;//DOUBLE    
    public long ip_createtime;//BIGINT    
    public String ip_createtime_str="";//VARCHAR    
    public long ip_endtime;//BIGINT    
    public String ip_endtime_str="";//VARCHAR    
    public long ip_updatetime;//BIGINT    
    public String ip_updatetime_str="";//VARCHAR    
    public int ip_state;//INT    0等待付款1交易成功2已经退款
    public String ip_ip="";//VARCHAR    
    public String ip_imei="";//VARCHAR    
    public int ip_item_type;//INT    0:未指定1：gamesdk2:嗨游SDK
    public String ip_prop_name="";//VARCHAR    
    public long pi_id;//BIGINT    
    public long ci_id;//BIGINT    
    public long gg_id;//BIGINT    
    public String ip_ssid="";//VARCHAR    
    public long cd_id;//BIGINT    
    public int bank_account;//INT    公司内部支付渠道的注册账号类型(0:候总团队1:孙伟团队)
    public String ip_note="";//VARCHAR    



    public long getIp_id(){
        return ip_id;
    }

    public void setIp_id(long value){
        this.ip_id= value;
    }

    public String getIp_order_no(){
        return ip_order_no;
    }

    public void setIp_order_no(String value){
    	if(value == null){
           value = "";
        }
        this.ip_order_no= value;
    }

    public String getIp_alipay_no(){
        return ip_alipay_no;
    }

    public void setIp_alipay_no(String value){
    	if(value == null){
           value = "";
        }
        this.ip_alipay_no= value;
    }

    public int getIp_source(){
        return ip_source;
    }

    public void setIp_source(int value){
        this.ip_source= value;
    }

    public String getIp_source_str(){
        return ip_source_str;
    }

    public void setIp_source_str(String value){
    	if(value == null){
           value = "";
        }
        this.ip_source_str= value;
    }

    public String getIp_version(){
        return ip_version;
    }

    public void setIp_version(String value){
    	if(value == null){
           value = "";
        }
        this.ip_version= value;
    }

    public int getIp_terminal_type(){
        return ip_terminal_type;
    }

    public void setIp_terminal_type(int value){
        this.ip_terminal_type= value;
    }

    public String getIp_return_url(){
        return ip_return_url;
    }

    public void setIp_return_url(String value){
    	if(value == null){
           value = "";
        }
        this.ip_return_url= value;
    }

    public long getIi_id(){
        return ii_id;
    }

    public void setIi_id(long value){
        this.ii_id= value;
    }

    public int getIp_type(){
        return ip_type;
    }

    public void setIp_type(int value){
        this.ip_type= value;
    }

    public double getIp_price(){
        return ip_price;
    }

    public void setIp_price(double value){
        this.ip_price= value;
    }

    public long getIp_createtime(){
        return ip_createtime;
    }

    public void setIp_createtime(long value){
        this.ip_createtime= value;
    }

    public String getIp_createtime_str(){
        return ip_createtime_str;
    }

    public void setIp_createtime_str(String value){
    	if(value == null){
           value = "";
        }
        this.ip_createtime_str= value;
    }

    public long getIp_endtime(){
        return ip_endtime;
    }

    public void setIp_endtime(long value){
        this.ip_endtime= value;
    }

    public String getIp_endtime_str(){
        return ip_endtime_str;
    }

    public void setIp_endtime_str(String value){
    	if(value == null){
           value = "";
        }
        this.ip_endtime_str= value;
    }

    public long getIp_updatetime(){
        return ip_updatetime;
    }

    public void setIp_updatetime(long value){
        this.ip_updatetime= value;
    }

    public String getIp_updatetime_str(){
        return ip_updatetime_str;
    }

    public void setIp_updatetime_str(String value){
    	if(value == null){
           value = "";
        }
        this.ip_updatetime_str= value;
    }

    public int getIp_state(){
        return ip_state;
    }

    public void setIp_state(int value){
        this.ip_state= value;
    }

    public String getIp_ip(){
        return ip_ip;
    }

    public void setIp_ip(String value){
    	if(value == null){
           value = "";
        }
        this.ip_ip= value;
    }

    public String getIp_imei(){
        return ip_imei;
    }

    public void setIp_imei(String value){
    	if(value == null){
           value = "";
        }
        this.ip_imei= value;
    }

    public int getIp_item_type(){
        return ip_item_type;
    }

    public void setIp_item_type(int value){
        this.ip_item_type= value;
    }

    public String getIp_prop_name(){
        return ip_prop_name;
    }

    public void setIp_prop_name(String value){
    	if(value == null){
           value = "";
        }
        this.ip_prop_name= value;
    }

    public long getPi_id(){
        return pi_id;
    }

    public void setPi_id(long value){
        this.pi_id= value;
    }

    public long getCi_id(){
        return ci_id;
    }

    public void setCi_id(long value){
        this.ci_id= value;
    }

    public long getGg_id(){
        return gg_id;
    }

    public void setGg_id(long value){
        this.gg_id= value;
    }

    public String getIp_ssid(){
        return ip_ssid;
    }

    public void setIp_ssid(String value){
    	if(value == null){
           value = "";
        }
        this.ip_ssid= value;
    }

    public long getCd_id(){
        return cd_id;
    }

    public void setCd_id(long value){
        this.cd_id= value;
    }

    public int getBank_account(){
        return bank_account;
    }

    public void setBank_account(int value){
        this.bank_account= value;
    }

    public String getIp_note(){
        return ip_note;
    }

    public void setIp_note(String value){
    	if(value == null){
           value = "";
        }
        this.ip_note= value;
    }



    public static Intimes_pay newIntimes_pay(long ip_id, String ip_order_no, String ip_alipay_no, int ip_source, String ip_source_str, String ip_version, int ip_terminal_type, String ip_return_url, long ii_id, int ip_type, double ip_price, long ip_createtime, String ip_createtime_str, long ip_endtime, String ip_endtime_str, long ip_updatetime, String ip_updatetime_str, int ip_state, String ip_ip, String ip_imei, int ip_item_type, String ip_prop_name, long pi_id, long ci_id, long gg_id, String ip_ssid, long cd_id, int bank_account, String ip_note) {
        Intimes_pay ret = new Intimes_pay();
        ret.setIp_id(ip_id);
        ret.setIp_order_no(ip_order_no);
        ret.setIp_alipay_no(ip_alipay_no);
        ret.setIp_source(ip_source);
        ret.setIp_source_str(ip_source_str);
        ret.setIp_version(ip_version);
        ret.setIp_terminal_type(ip_terminal_type);
        ret.setIp_return_url(ip_return_url);
        ret.setIi_id(ii_id);
        ret.setIp_type(ip_type);
        ret.setIp_price(ip_price);
        ret.setIp_createtime(ip_createtime);
        ret.setIp_createtime_str(ip_createtime_str);
        ret.setIp_endtime(ip_endtime);
        ret.setIp_endtime_str(ip_endtime_str);
        ret.setIp_updatetime(ip_updatetime);
        ret.setIp_updatetime_str(ip_updatetime_str);
        ret.setIp_state(ip_state);
        ret.setIp_ip(ip_ip);
        ret.setIp_imei(ip_imei);
        ret.setIp_item_type(ip_item_type);
        ret.setIp_prop_name(ip_prop_name);
        ret.setPi_id(pi_id);
        ret.setCi_id(ci_id);
        ret.setGg_id(gg_id);
        ret.setIp_ssid(ip_ssid);
        ret.setCd_id(cd_id);
        ret.setBank_account(bank_account);
        ret.setIp_note(ip_note);
        return ret;    
    }

    public void assignment(Intimes_pay intimes_pay) {
        long ip_id = intimes_pay.getIp_id();
        String ip_order_no = intimes_pay.getIp_order_no();
        String ip_alipay_no = intimes_pay.getIp_alipay_no();
        int ip_source = intimes_pay.getIp_source();
        String ip_source_str = intimes_pay.getIp_source_str();
        String ip_version = intimes_pay.getIp_version();
        int ip_terminal_type = intimes_pay.getIp_terminal_type();
        String ip_return_url = intimes_pay.getIp_return_url();
        long ii_id = intimes_pay.getIi_id();
        int ip_type = intimes_pay.getIp_type();
        double ip_price = intimes_pay.getIp_price();
        long ip_createtime = intimes_pay.getIp_createtime();
        String ip_createtime_str = intimes_pay.getIp_createtime_str();
        long ip_endtime = intimes_pay.getIp_endtime();
        String ip_endtime_str = intimes_pay.getIp_endtime_str();
        long ip_updatetime = intimes_pay.getIp_updatetime();
        String ip_updatetime_str = intimes_pay.getIp_updatetime_str();
        int ip_state = intimes_pay.getIp_state();
        String ip_ip = intimes_pay.getIp_ip();
        String ip_imei = intimes_pay.getIp_imei();
        int ip_item_type = intimes_pay.getIp_item_type();
        String ip_prop_name = intimes_pay.getIp_prop_name();
        long pi_id = intimes_pay.getPi_id();
        long ci_id = intimes_pay.getCi_id();
        long gg_id = intimes_pay.getGg_id();
        String ip_ssid = intimes_pay.getIp_ssid();
        long cd_id = intimes_pay.getCd_id();
        int bank_account = intimes_pay.getBank_account();
        String ip_note = intimes_pay.getIp_note();

        this.setIp_id(ip_id);
        this.setIp_order_no(ip_order_no);
        this.setIp_alipay_no(ip_alipay_no);
        this.setIp_source(ip_source);
        this.setIp_source_str(ip_source_str);
        this.setIp_version(ip_version);
        this.setIp_terminal_type(ip_terminal_type);
        this.setIp_return_url(ip_return_url);
        this.setIi_id(ii_id);
        this.setIp_type(ip_type);
        this.setIp_price(ip_price);
        this.setIp_createtime(ip_createtime);
        this.setIp_createtime_str(ip_createtime_str);
        this.setIp_endtime(ip_endtime);
        this.setIp_endtime_str(ip_endtime_str);
        this.setIp_updatetime(ip_updatetime);
        this.setIp_updatetime_str(ip_updatetime_str);
        this.setIp_state(ip_state);
        this.setIp_ip(ip_ip);
        this.setIp_imei(ip_imei);
        this.setIp_item_type(ip_item_type);
        this.setIp_prop_name(ip_prop_name);
        this.setPi_id(pi_id);
        this.setCi_id(ci_id);
        this.setGg_id(gg_id);
        this.setIp_ssid(ip_ssid);
        this.setCd_id(cd_id);
        this.setBank_account(bank_account);
        this.setIp_note(ip_note);

    }

    @SuppressWarnings("unused")
    public static void getIntimes_pay(Intimes_pay intimes_pay ){
        long ip_id = intimes_pay.getIp_id();
        String ip_order_no = intimes_pay.getIp_order_no();
        String ip_alipay_no = intimes_pay.getIp_alipay_no();
        int ip_source = intimes_pay.getIp_source();
        String ip_source_str = intimes_pay.getIp_source_str();
        String ip_version = intimes_pay.getIp_version();
        int ip_terminal_type = intimes_pay.getIp_terminal_type();
        String ip_return_url = intimes_pay.getIp_return_url();
        long ii_id = intimes_pay.getIi_id();
        int ip_type = intimes_pay.getIp_type();
        double ip_price = intimes_pay.getIp_price();
        long ip_createtime = intimes_pay.getIp_createtime();
        String ip_createtime_str = intimes_pay.getIp_createtime_str();
        long ip_endtime = intimes_pay.getIp_endtime();
        String ip_endtime_str = intimes_pay.getIp_endtime_str();
        long ip_updatetime = intimes_pay.getIp_updatetime();
        String ip_updatetime_str = intimes_pay.getIp_updatetime_str();
        int ip_state = intimes_pay.getIp_state();
        String ip_ip = intimes_pay.getIp_ip();
        String ip_imei = intimes_pay.getIp_imei();
        int ip_item_type = intimes_pay.getIp_item_type();
        String ip_prop_name = intimes_pay.getIp_prop_name();
        long pi_id = intimes_pay.getPi_id();
        long ci_id = intimes_pay.getCi_id();
        long gg_id = intimes_pay.getGg_id();
        String ip_ssid = intimes_pay.getIp_ssid();
        long cd_id = intimes_pay.getCd_id();
        int bank_account = intimes_pay.getBank_account();
        String ip_note = intimes_pay.getIp_note();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(Intimes_pay intimes_pay){
        long ip_id = intimes_pay.getIp_id();
        String ip_order_no = intimes_pay.getIp_order_no();
        String ip_alipay_no = intimes_pay.getIp_alipay_no();
        int ip_source = intimes_pay.getIp_source();
        String ip_source_str = intimes_pay.getIp_source_str();
        String ip_version = intimes_pay.getIp_version();
        int ip_terminal_type = intimes_pay.getIp_terminal_type();
        String ip_return_url = intimes_pay.getIp_return_url();
        long ii_id = intimes_pay.getIi_id();
        int ip_type = intimes_pay.getIp_type();
        double ip_price = intimes_pay.getIp_price();
        long ip_createtime = intimes_pay.getIp_createtime();
        String ip_createtime_str = intimes_pay.getIp_createtime_str();
        long ip_endtime = intimes_pay.getIp_endtime();
        String ip_endtime_str = intimes_pay.getIp_endtime_str();
        long ip_updatetime = intimes_pay.getIp_updatetime();
        String ip_updatetime_str = intimes_pay.getIp_updatetime_str();
        int ip_state = intimes_pay.getIp_state();
        String ip_ip = intimes_pay.getIp_ip();
        String ip_imei = intimes_pay.getIp_imei();
        int ip_item_type = intimes_pay.getIp_item_type();
        String ip_prop_name = intimes_pay.getIp_prop_name();
        long pi_id = intimes_pay.getPi_id();
        long ci_id = intimes_pay.getCi_id();
        long gg_id = intimes_pay.getGg_id();
        String ip_ssid = intimes_pay.getIp_ssid();
        long cd_id = intimes_pay.getCd_id();
        int bank_account = intimes_pay.getBank_account();
        String ip_note = intimes_pay.getIp_note();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("ip_id",ip_id);
        _ret.put("ip_order_no",ip_order_no);
        _ret.put("ip_alipay_no",ip_alipay_no);
        _ret.put("ip_source",ip_source);
        _ret.put("ip_source_str",ip_source_str);
        _ret.put("ip_version",ip_version);
        _ret.put("ip_terminal_type",ip_terminal_type);
        _ret.put("ip_return_url",ip_return_url);
        _ret.put("ii_id",ii_id);
        _ret.put("ip_type",ip_type);
        _ret.put("ip_price",ip_price);
        _ret.put("ip_createtime",ip_createtime);
        _ret.put("ip_createtime_str",ip_createtime_str);
        _ret.put("ip_endtime",ip_endtime);
        _ret.put("ip_endtime_str",ip_endtime_str);
        _ret.put("ip_updatetime",ip_updatetime);
        _ret.put("ip_updatetime_str",ip_updatetime_str);
        _ret.put("ip_state",ip_state);
        _ret.put("ip_ip",ip_ip);
        _ret.put("ip_imei",ip_imei);
        _ret.put("ip_item_type",ip_item_type);
        _ret.put("ip_prop_name",ip_prop_name);
        _ret.put("pi_id",pi_id);
        _ret.put("ci_id",ci_id);
        _ret.put("gg_id",gg_id);
        _ret.put("ip_ssid",ip_ssid);
        _ret.put("cd_id",cd_id);
        _ret.put("bank_account",bank_account);
        _ret.put("ip_note",ip_note);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public Intimes_pay clone2(){
        try{
            return (Intimes_pay) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
