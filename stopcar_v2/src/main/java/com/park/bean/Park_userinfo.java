package com.park.bean;

import java.io.*;
import java.util.*;

//park_userinfo
@SuppressWarnings({"serial"})
public class Park_userinfo implements Cloneable , Serializable{

    //public static String[] carrays ={"pu_id","nd","name","tel","bank_no","bank_name","bank_sub_name","pda_num","money","ctime","utime","loginname","password","note","money_online","money_offline"};

    public long pu_id;//BIGINT    
    public String nd="";//VARCHAR    商户编号
    public String name="";//VARCHAR    PDA老板姓名
    public String tel="";//VARCHAR    PDA老板手机号码
    public String bank_no="";//VARCHAR    PDA老板银行卡卡号
    public String bank_name="";//VARCHAR    PDA老板开户行名称
    public String bank_sub_name="";//VARCHAR    PDA老板开户行支行名称
    public int pda_num;//INT    PDA老板拥有的PDA个数
    public int money;//INT    商户平台总金额（单位分）
    public java.util.Date ctime=new java.util.Date();//DATETIME    创建时间
    public java.util.Date utime=new java.util.Date();//DATETIME    更新时间
    public String loginname="";//VARCHAR    账号
    public String password="";//VARCHAR    密码
    public String note="";//VARCHAR    备注
    public long money_online;//BIGINT    线上累计金额
    public long money_offline;//BIGINT    线下累计金额



    public long getPu_id(){
        return pu_id;
    }

    public void setPu_id(long value){
        this.pu_id= value;
    }

    public String getNd(){
        return nd;
    }

    public void setNd(String value){
    	if(value == null){
           value = "";
        }
        this.nd= value;
    }

    public String getName(){
        return name;
    }

    public void setName(String value){
    	if(value == null){
           value = "";
        }
        this.name= value;
    }

    public String getTel(){
        return tel;
    }

    public void setTel(String value){
    	if(value == null){
           value = "";
        }
        this.tel= value;
    }

    public String getBank_no(){
        return bank_no;
    }

    public void setBank_no(String value){
    	if(value == null){
           value = "";
        }
        this.bank_no= value;
    }

    public String getBank_name(){
        return bank_name;
    }

    public void setBank_name(String value){
    	if(value == null){
           value = "";
        }
        this.bank_name= value;
    }

    public String getBank_sub_name(){
        return bank_sub_name;
    }

    public void setBank_sub_name(String value){
    	if(value == null){
           value = "";
        }
        this.bank_sub_name= value;
    }

    public int getPda_num(){
        return pda_num;
    }

    public void setPda_num(int value){
        this.pda_num= value;
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

    public java.util.Date getUtime(){
        return utime;
    }

    public void setUtime(java.util.Date value){
    	if(value == null){
           value = new java.util.Date();
        }
        this.utime= value;
    }

    public String getLoginname(){
        return loginname;
    }

    public void setLoginname(String value){
    	if(value == null){
           value = "";
        }
        this.loginname= value;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String value){
    	if(value == null){
           value = "";
        }
        this.password= value;
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

    public long getMoney_online(){
        return money_online;
    }

    public void setMoney_online(long value){
        this.money_online= value;
    }

    public long getMoney_offline(){
        return money_offline;
    }

    public void setMoney_offline(long value){
        this.money_offline= value;
    }



    public static Park_userinfo newPark_userinfo(long pu_id, String nd, String name, String tel, String bank_no, String bank_name, String bank_sub_name, int pda_num, int money, java.util.Date ctime, java.util.Date utime, String loginname, String password, String note, long money_online, long money_offline) {
        Park_userinfo ret = new Park_userinfo();
        ret.setPu_id(pu_id);
        ret.setNd(nd);
        ret.setName(name);
        ret.setTel(tel);
        ret.setBank_no(bank_no);
        ret.setBank_name(bank_name);
        ret.setBank_sub_name(bank_sub_name);
        ret.setPda_num(pda_num);
        ret.setMoney(money);
        ret.setCtime(ctime);
        ret.setUtime(utime);
        ret.setLoginname(loginname);
        ret.setPassword(password);
        ret.setNote(note);
        ret.setMoney_online(money_online);
        ret.setMoney_offline(money_offline);
        return ret;    
    }

    public void assignment(Park_userinfo park_userinfo) {
        long pu_id = park_userinfo.getPu_id();
        String nd = park_userinfo.getNd();
        String name = park_userinfo.getName();
        String tel = park_userinfo.getTel();
        String bank_no = park_userinfo.getBank_no();
        String bank_name = park_userinfo.getBank_name();
        String bank_sub_name = park_userinfo.getBank_sub_name();
        int pda_num = park_userinfo.getPda_num();
        int money = park_userinfo.getMoney();
        java.util.Date ctime = park_userinfo.getCtime();
        java.util.Date utime = park_userinfo.getUtime();
        String loginname = park_userinfo.getLoginname();
        String password = park_userinfo.getPassword();
        String note = park_userinfo.getNote();
        long money_online = park_userinfo.getMoney_online();
        long money_offline = park_userinfo.getMoney_offline();

        this.setPu_id(pu_id);
        this.setNd(nd);
        this.setName(name);
        this.setTel(tel);
        this.setBank_no(bank_no);
        this.setBank_name(bank_name);
        this.setBank_sub_name(bank_sub_name);
        this.setPda_num(pda_num);
        this.setMoney(money);
        this.setCtime(ctime);
        this.setUtime(utime);
        this.setLoginname(loginname);
        this.setPassword(password);
        this.setNote(note);
        this.setMoney_online(money_online);
        this.setMoney_offline(money_offline);

    }

    @SuppressWarnings("unused")
    public static void getPark_userinfo(Park_userinfo park_userinfo ){
        long pu_id = park_userinfo.getPu_id();
        String nd = park_userinfo.getNd();
        String name = park_userinfo.getName();
        String tel = park_userinfo.getTel();
        String bank_no = park_userinfo.getBank_no();
        String bank_name = park_userinfo.getBank_name();
        String bank_sub_name = park_userinfo.getBank_sub_name();
        int pda_num = park_userinfo.getPda_num();
        int money = park_userinfo.getMoney();
        java.util.Date ctime = park_userinfo.getCtime();
        java.util.Date utime = park_userinfo.getUtime();
        String loginname = park_userinfo.getLoginname();
        String password = park_userinfo.getPassword();
        String note = park_userinfo.getNote();
        long money_online = park_userinfo.getMoney_online();
        long money_offline = park_userinfo.getMoney_offline();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(Park_userinfo park_userinfo){
        long pu_id = park_userinfo.getPu_id();
        String nd = park_userinfo.getNd();
        String name = park_userinfo.getName();
        String tel = park_userinfo.getTel();
        String bank_no = park_userinfo.getBank_no();
        String bank_name = park_userinfo.getBank_name();
        String bank_sub_name = park_userinfo.getBank_sub_name();
        int pda_num = park_userinfo.getPda_num();
        int money = park_userinfo.getMoney();
        java.util.Date ctime = park_userinfo.getCtime();
        java.util.Date utime = park_userinfo.getUtime();
        String loginname = park_userinfo.getLoginname();
        String password = park_userinfo.getPassword();
        String note = park_userinfo.getNote();
        long money_online = park_userinfo.getMoney_online();
        long money_offline = park_userinfo.getMoney_offline();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("pu_id",pu_id);
        _ret.put("nd",nd);
        _ret.put("name",name);
        _ret.put("tel",tel);
        _ret.put("bank_no",bank_no);
        _ret.put("bank_name",bank_name);
        _ret.put("bank_sub_name",bank_sub_name);
        _ret.put("pda_num",pda_num);
        _ret.put("money",money);
        _ret.put("ctime",ctime);
        _ret.put("utime",utime);
        _ret.put("loginname",loginname);
        _ret.put("password",password);
        _ret.put("note",note);
        _ret.put("money_online",money_online);
        _ret.put("money_offline",money_offline);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public Park_userinfo clone2(){
        try{
            return (Park_userinfo) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
