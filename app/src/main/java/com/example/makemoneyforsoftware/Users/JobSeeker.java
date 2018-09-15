package com.example.makemoneyforsoftware.Users;

import cn.bmob.v3.BmobObject;

public class JobSeeker extends BmobObject{
    private String tele;
    private String psd;
    private String url;
    private String name;
    private String mail;
    private String sex;
    private String birthday;
    private String location;

    public JobSeeker(){
        this.tele="";
        this.psd="";
        this.url="";
        this.name="";
        this.mail="";
        this.sex="";
        this.birthday="";
        this.location="";
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
