package com.example.makemoneyforsoftware;

import android.app.Application;

import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;


public class MyApplication extends Application {
    public String appVersion = "v1.0";

    //当前登录用户
    private CurrentUser loginUser = new CurrentUser();

    public CurrentUser getLoginUser(){
        return loginUser;
    }

    public void userLogin(CurrentUser user){
        loginUser.setObjectid(user.getObjectid());
        loginUser.setTele(user.getTele());
        loginUser.setPsd(user.getPsd());
        loginUser.setUrl(user.getUrl());
        loginUser.setName(user.getName());
        loginUser.setMail(user.getMail());
        loginUser.setSex(user.getSex());
        loginUser.setLocation(user.getLocation());
        loginUser.setBirthday(user.getBirthday());
        loginUser.setStorename(user.getStorename());
        loginUser.setOpendate(user.getOpendate());
    }

    public void userLogout(){
        loginUser = new CurrentUser();
    }
}
