package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import java.io.Serializable;

public class User implements Serializable{
    private String tele;
    private String url;
    private String name;
    private String mail;
    private String sex;
    private String birthday;
    private String location;
    private int Jobid;

    public int getJobid() {
        return Jobid;
    }

    public void setJobid(int jobid) {
        Jobid = jobid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
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
}
