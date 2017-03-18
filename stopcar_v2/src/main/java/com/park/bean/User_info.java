package com.park.bean;

import java.io.*;
import java.util.*;

//user_info
@SuppressWarnings({"serial"})
public class User_info implements Cloneable , Serializable{

    //public static String[] carrays ={"ui_id","uuid","ui_tel","ui_password","ui_nickname","ui_avatar","ui_sex","driving_licence","ui_name","ui_address","bind_tel","ui_vc","ui_rmb","coupon_num","ui_state","ui_autopay","pay_source","ui_level","ui_score","ui_mood","ui_intro","ui_age","ui_token","ui_email","ui_high","ui_degree","ctime","utime","ui_flag","ui_online","ui_integrity","note"};

    public long ui_id;//BIGINT    
    public String uuid="";//VARCHAR    用户uuid
    public String ui_tel="";//VARCHAR    手机号码/用户账号
    public String ui_password="";//VARCHAR    用户密码(MD5散列)
    public String ui_nickname="";//VARCHAR    用户昵称
    public String ui_avatar="";//VARCHAR    用户头像
    public String ui_sex="";//VARCHAR    用户性别:male男women女no未知
    public String driving_licence="";//VARCHAR    驾驶证号码
    public String ui_name="";//VARCHAR    姓名
    public String ui_address="";//VARCHAR    住址
    public String bind_tel="";//VARCHAR    绑定手机号码
    public long ui_vc;//BIGINT    爱泊币（100爱泊=1元）
    public long ui_rmb;//BIGINT    爱泊币（100爱泊=1元）
    public long coupon_num;//BIGINT    优费卷数量
    public int ui_state;//INT    用户状态:0：正常1：禁用
    public int ui_autopay;//INT    是否自动支付：0：不是1：是
    public int pay_source;//INT    支付类型1:支付l宝2：微信3：银联
    public int ui_level;//INT    用户等级
    public long ui_score;//BIGINT    用户积分
    public String ui_mood="";//VARCHAR    用户心情
    public String ui_intro="";//VARCHAR    个人简介
    public int ui_age;//INT    用户年龄
    public String ui_token="";//VARCHAR    用户授权码
    public String ui_email="";//VARCHAR    用户邮箱
    public int ui_high;//INT    用户身高
    public String ui_degree="";//VARCHAR    
    public java.util.Date ctime=new java.util.Date();//DATETIME    
    public java.util.Date utime=new java.util.Date();//DATETIME    
    public int ui_flag;//INT    注册来源0:android1:ios2:web
    public long ui_online;//BIGINT    l累计在线时长(分钟)
    public int ui_integrity;//INT    用户诚信度按100来计算
    public String note="";//VARCHAR    备注



    public long getUi_id(){
        return ui_id;
    }

    public void setUi_id(long value){
        this.ui_id= value;
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String value){
    	if(value == null){
           value = "";
        }
        this.uuid= value;
    }

    public String getUi_tel(){
        return ui_tel;
    }

    public void setUi_tel(String value){
    	if(value == null){
           value = "";
        }
        this.ui_tel= value;
    }

    public String getUi_password(){
        return ui_password;
    }

    public void setUi_password(String value){
    	if(value == null){
           value = "";
        }
        this.ui_password= value;
    }

    public String getUi_nickname(){
        return ui_nickname;
    }

    public void setUi_nickname(String value){
    	if(value == null){
           value = "";
        }
        this.ui_nickname= value;
    }

    public String getUi_avatar(){
        return ui_avatar;
    }

    public void setUi_avatar(String value){
    	if(value == null){
           value = "";
        }
        this.ui_avatar= value;
    }

    public String getUi_sex(){
        return ui_sex;
    }

    public void setUi_sex(String value){
    	if(value == null){
           value = "";
        }
        this.ui_sex= value;
    }

    public String getDriving_licence(){
        return driving_licence;
    }

    public void setDriving_licence(String value){
    	if(value == null){
           value = "";
        }
        this.driving_licence= value;
    }

    public String getUi_name(){
        return ui_name;
    }

    public void setUi_name(String value){
    	if(value == null){
           value = "";
        }
        this.ui_name= value;
    }

    public String getUi_address(){
        return ui_address;
    }

    public void setUi_address(String value){
    	if(value == null){
           value = "";
        }
        this.ui_address= value;
    }

    public String getBind_tel(){
        return bind_tel;
    }

    public void setBind_tel(String value){
    	if(value == null){
           value = "";
        }
        this.bind_tel= value;
    }

    public long getUi_vc(){
        return ui_vc;
    }

    public void setUi_vc(long value){
        this.ui_vc= value;
    }

    public long getUi_rmb(){
        return ui_rmb;
    }

    public void setUi_rmb(long value){
        this.ui_rmb= value;
    }

    public long getCoupon_num(){
        return coupon_num;
    }

    public void setCoupon_num(long value){
        this.coupon_num= value;
    }

    public int getUi_state(){
        return ui_state;
    }

    public void setUi_state(int value){
        this.ui_state= value;
    }

    public int getUi_autopay(){
        return ui_autopay;
    }

    public void setUi_autopay(int value){
        this.ui_autopay= value;
    }

    public int getPay_source(){
        return pay_source;
    }

    public void setPay_source(int value){
        this.pay_source= value;
    }

    public int getUi_level(){
        return ui_level;
    }

    public void setUi_level(int value){
        this.ui_level= value;
    }

    public long getUi_score(){
        return ui_score;
    }

    public void setUi_score(long value){
        this.ui_score= value;
    }

    public String getUi_mood(){
        return ui_mood;
    }

    public void setUi_mood(String value){
    	if(value == null){
           value = "";
        }
        this.ui_mood= value;
    }

    public String getUi_intro(){
        return ui_intro;
    }

    public void setUi_intro(String value){
    	if(value == null){
           value = "";
        }
        this.ui_intro= value;
    }

    public int getUi_age(){
        return ui_age;
    }

    public void setUi_age(int value){
        this.ui_age= value;
    }

    public String getUi_token(){
        return ui_token;
    }

    public void setUi_token(String value){
    	if(value == null){
           value = "";
        }
        this.ui_token= value;
    }

    public String getUi_email(){
        return ui_email;
    }

    public void setUi_email(String value){
    	if(value == null){
           value = "";
        }
        this.ui_email= value;
    }

    public int getUi_high(){
        return ui_high;
    }

    public void setUi_high(int value){
        this.ui_high= value;
    }

    public String getUi_degree(){
        return ui_degree;
    }

    public void setUi_degree(String value){
    	if(value == null){
           value = "";
        }
        this.ui_degree= value;
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

    public int getUi_flag(){
        return ui_flag;
    }

    public void setUi_flag(int value){
        this.ui_flag= value;
    }

    public long getUi_online(){
        return ui_online;
    }

    public void setUi_online(long value){
        this.ui_online= value;
    }

    public int getUi_integrity(){
        return ui_integrity;
    }

    public void setUi_integrity(int value){
        this.ui_integrity= value;
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



    public static User_info newUser_info(long ui_id, String uuid, String ui_tel, String ui_password, String ui_nickname, String ui_avatar, String ui_sex, String driving_licence, String ui_name, String ui_address, String bind_tel, long ui_vc, long ui_rmb, long coupon_num, int ui_state, int ui_autopay, int pay_source, int ui_level, long ui_score, String ui_mood, String ui_intro, int ui_age, String ui_token, String ui_email, int ui_high, String ui_degree, java.util.Date ctime, java.util.Date utime, int ui_flag, long ui_online, int ui_integrity, String note) {
        User_info ret = new User_info();
        ret.setUi_id(ui_id);
        ret.setUuid(uuid);
        ret.setUi_tel(ui_tel);
        ret.setUi_password(ui_password);
        ret.setUi_nickname(ui_nickname);
        ret.setUi_avatar(ui_avatar);
        ret.setUi_sex(ui_sex);
        ret.setDriving_licence(driving_licence);
        ret.setUi_name(ui_name);
        ret.setUi_address(ui_address);
        ret.setBind_tel(bind_tel);
        ret.setUi_vc(ui_vc);
        ret.setUi_rmb(ui_rmb);
        ret.setCoupon_num(coupon_num);
        ret.setUi_state(ui_state);
        ret.setUi_autopay(ui_autopay);
        ret.setPay_source(pay_source);
        ret.setUi_level(ui_level);
        ret.setUi_score(ui_score);
        ret.setUi_mood(ui_mood);
        ret.setUi_intro(ui_intro);
        ret.setUi_age(ui_age);
        ret.setUi_token(ui_token);
        ret.setUi_email(ui_email);
        ret.setUi_high(ui_high);
        ret.setUi_degree(ui_degree);
        ret.setCtime(ctime);
        ret.setUtime(utime);
        ret.setUi_flag(ui_flag);
        ret.setUi_online(ui_online);
        ret.setUi_integrity(ui_integrity);
        ret.setNote(note);
        return ret;    
    }

    public void assignment(User_info user_info) {
        long ui_id = user_info.getUi_id();
        String uuid = user_info.getUuid();
        String ui_tel = user_info.getUi_tel();
        String ui_password = user_info.getUi_password();
        String ui_nickname = user_info.getUi_nickname();
        String ui_avatar = user_info.getUi_avatar();
        String ui_sex = user_info.getUi_sex();
        String driving_licence = user_info.getDriving_licence();
        String ui_name = user_info.getUi_name();
        String ui_address = user_info.getUi_address();
        String bind_tel = user_info.getBind_tel();
        long ui_vc = user_info.getUi_vc();
        long ui_rmb = user_info.getUi_rmb();
        long coupon_num = user_info.getCoupon_num();
        int ui_state = user_info.getUi_state();
        int ui_autopay = user_info.getUi_autopay();
        int pay_source = user_info.getPay_source();
        int ui_level = user_info.getUi_level();
        long ui_score = user_info.getUi_score();
        String ui_mood = user_info.getUi_mood();
        String ui_intro = user_info.getUi_intro();
        int ui_age = user_info.getUi_age();
        String ui_token = user_info.getUi_token();
        String ui_email = user_info.getUi_email();
        int ui_high = user_info.getUi_high();
        String ui_degree = user_info.getUi_degree();
        java.util.Date ctime = user_info.getCtime();
        java.util.Date utime = user_info.getUtime();
        int ui_flag = user_info.getUi_flag();
        long ui_online = user_info.getUi_online();
        int ui_integrity = user_info.getUi_integrity();
        String note = user_info.getNote();

        this.setUi_id(ui_id);
        this.setUuid(uuid);
        this.setUi_tel(ui_tel);
        this.setUi_password(ui_password);
        this.setUi_nickname(ui_nickname);
        this.setUi_avatar(ui_avatar);
        this.setUi_sex(ui_sex);
        this.setDriving_licence(driving_licence);
        this.setUi_name(ui_name);
        this.setUi_address(ui_address);
        this.setBind_tel(bind_tel);
        this.setUi_vc(ui_vc);
        this.setUi_rmb(ui_rmb);
        this.setCoupon_num(coupon_num);
        this.setUi_state(ui_state);
        this.setUi_autopay(ui_autopay);
        this.setPay_source(pay_source);
        this.setUi_level(ui_level);
        this.setUi_score(ui_score);
        this.setUi_mood(ui_mood);
        this.setUi_intro(ui_intro);
        this.setUi_age(ui_age);
        this.setUi_token(ui_token);
        this.setUi_email(ui_email);
        this.setUi_high(ui_high);
        this.setUi_degree(ui_degree);
        this.setCtime(ctime);
        this.setUtime(utime);
        this.setUi_flag(ui_flag);
        this.setUi_online(ui_online);
        this.setUi_integrity(ui_integrity);
        this.setNote(note);

    }

    @SuppressWarnings("unused")
    public static void getUser_info(User_info user_info ){
        long ui_id = user_info.getUi_id();
        String uuid = user_info.getUuid();
        String ui_tel = user_info.getUi_tel();
        String ui_password = user_info.getUi_password();
        String ui_nickname = user_info.getUi_nickname();
        String ui_avatar = user_info.getUi_avatar();
        String ui_sex = user_info.getUi_sex();
        String driving_licence = user_info.getDriving_licence();
        String ui_name = user_info.getUi_name();
        String ui_address = user_info.getUi_address();
        String bind_tel = user_info.getBind_tel();
        long ui_vc = user_info.getUi_vc();
        long ui_rmb = user_info.getUi_rmb();
        long coupon_num = user_info.getCoupon_num();
        int ui_state = user_info.getUi_state();
        int ui_autopay = user_info.getUi_autopay();
        int pay_source = user_info.getPay_source();
        int ui_level = user_info.getUi_level();
        long ui_score = user_info.getUi_score();
        String ui_mood = user_info.getUi_mood();
        String ui_intro = user_info.getUi_intro();
        int ui_age = user_info.getUi_age();
        String ui_token = user_info.getUi_token();
        String ui_email = user_info.getUi_email();
        int ui_high = user_info.getUi_high();
        String ui_degree = user_info.getUi_degree();
        java.util.Date ctime = user_info.getCtime();
        java.util.Date utime = user_info.getUtime();
        int ui_flag = user_info.getUi_flag();
        long ui_online = user_info.getUi_online();
        int ui_integrity = user_info.getUi_integrity();
        String note = user_info.getNote();
    }

    public Map<String,Object> toMap(){
        return toEnMap(this);
    }

    public static Map<String,Object> toEnMap(User_info user_info){
        long ui_id = user_info.getUi_id();
        String uuid = user_info.getUuid();
        String ui_tel = user_info.getUi_tel();
        String ui_password = user_info.getUi_password();
        String ui_nickname = user_info.getUi_nickname();
        String ui_avatar = user_info.getUi_avatar();
        String ui_sex = user_info.getUi_sex();
        String driving_licence = user_info.getDriving_licence();
        String ui_name = user_info.getUi_name();
        String ui_address = user_info.getUi_address();
        String bind_tel = user_info.getBind_tel();
        long ui_vc = user_info.getUi_vc();
        long ui_rmb = user_info.getUi_rmb();
        long coupon_num = user_info.getCoupon_num();
        int ui_state = user_info.getUi_state();
        int ui_autopay = user_info.getUi_autopay();
        int pay_source = user_info.getPay_source();
        int ui_level = user_info.getUi_level();
        long ui_score = user_info.getUi_score();
        String ui_mood = user_info.getUi_mood();
        String ui_intro = user_info.getUi_intro();
        int ui_age = user_info.getUi_age();
        String ui_token = user_info.getUi_token();
        String ui_email = user_info.getUi_email();
        int ui_high = user_info.getUi_high();
        String ui_degree = user_info.getUi_degree();
        java.util.Date ctime = user_info.getCtime();
        java.util.Date utime = user_info.getUtime();
        int ui_flag = user_info.getUi_flag();
        long ui_online = user_info.getUi_online();
        int ui_integrity = user_info.getUi_integrity();
        String note = user_info.getNote();
    
        Map<String,Object>  _ret = new HashMap<String,Object>();
        _ret.put("ui_id",ui_id);
        _ret.put("uuid",uuid);
        _ret.put("ui_tel",ui_tel);
        _ret.put("ui_password",ui_password);
        _ret.put("ui_nickname",ui_nickname);
        _ret.put("ui_avatar",ui_avatar);
        _ret.put("ui_sex",ui_sex);
        _ret.put("driving_licence",driving_licence);
        _ret.put("ui_name",ui_name);
        _ret.put("ui_address",ui_address);
        _ret.put("bind_tel",bind_tel);
        _ret.put("ui_vc",ui_vc);
        _ret.put("ui_rmb",ui_rmb);
        _ret.put("coupon_num",coupon_num);
        _ret.put("ui_state",ui_state);
        _ret.put("ui_autopay",ui_autopay);
        _ret.put("pay_source",pay_source);
        _ret.put("ui_level",ui_level);
        _ret.put("ui_score",ui_score);
        _ret.put("ui_mood",ui_mood);
        _ret.put("ui_intro",ui_intro);
        _ret.put("ui_age",ui_age);
        _ret.put("ui_token",ui_token);
        _ret.put("ui_email",ui_email);
        _ret.put("ui_high",ui_high);
        _ret.put("ui_degree",ui_degree);
        _ret.put("ctime",ctime);
        _ret.put("utime",utime);
        _ret.put("ui_flag",ui_flag);
        _ret.put("ui_online",ui_online);
        _ret.put("ui_integrity",ui_integrity);
        _ret.put("note",note);
        return _ret;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public User_info clone2(){
        try{
            return (User_info) this.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
