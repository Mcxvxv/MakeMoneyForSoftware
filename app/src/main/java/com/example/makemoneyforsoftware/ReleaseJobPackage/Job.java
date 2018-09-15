package com.example.makemoneyforsoftware.ReleaseJobPackage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job implements Serializable{
    private int jobid;
    private String businessmantele;
    private String jobtitle;
    private String jobtype;
    private String jobsalary;
    private String jobrequirenum;
    private String jobrequiresex;
    private String jobrequirecycle;
    private String jobrequireage;
    private String jobdiscrip;
    private String jobcontent;
    private String jobstartday;
    private String jobendday;
    private String jobworktime;
    private String joblocation;
    private String jobreleaseday;

    public Job() {
        this.jobid = 0;
        this.businessmantele="";
        this.jobtitle="标题";
        this.jobtype="类型";
        this.jobsalary="工资";
        this.jobrequirenum="不限";
        this.jobrequiresex="不限";
        this.jobrequirecycle="日结";
        this.jobrequireage="不限";
        this.jobdiscrip="这是描述";
        this.jobcontent="这是内容";
        this.jobstartday="无";
        this.jobendday="无";
        this.jobworktime="报名后通知";
        this.joblocation="无";
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        this.jobreleaseday=dateFormat.format(date);
    }

    public String getJobreleaseday() {
        return jobreleaseday;
    }

    public void setJobreleaseday(String jobreleaseday) {
        this.jobreleaseday = jobreleaseday;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getBusinessmantele() {
        return businessmantele;
    }

    public void setBusinessmantele(String businessmantele) {
        this.businessmantele = businessmantele;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getJobsalary() {
        return jobsalary;
    }

    public void setJobsalary(String jobsalary) {
        this.jobsalary = jobsalary;
    }

    public String getJobrequirenum() {
        return jobrequirenum;
    }

    public void setJobrequirenum(String jobrequirenum) {
        this.jobrequirenum = jobrequirenum;
    }

    public String getJobrequiresex() {
        return jobrequiresex;
    }

    public void setJobrequiresex(String jobrequiresex) {
        this.jobrequiresex = jobrequiresex;
    }

    public String getJobrequirecycle() {
        return jobrequirecycle;
    }

    public void setJobrequirecycle(String jobrequirecycle) {
        this.jobrequirecycle = jobrequirecycle;
    }

    public String getJobrequireage() {
        return jobrequireage;
    }

    public void setJobrequireage(String jobrequireage) {
        this.jobrequireage = jobrequireage;
    }

    public String getJobdiscrip() {
        return jobdiscrip;
    }

    public void setJobdiscrip(String jobdiscrip) {
        this.jobdiscrip = jobdiscrip;
    }

    public String getJobcontent() {
        return jobcontent;
    }

    public void setJobcontent(String jobcontent) {
        this.jobcontent = jobcontent;
    }

    public String getJobstartday() {
        return jobstartday;
    }

    public void setJobstartday(String jobstartday) {
        this.jobstartday = jobstartday;
    }

    public String getJobendday() {
        return jobendday;
    }

    public void setJobendday(String jobendday) {
        this.jobendday = jobendday;
    }

    public String getJobworktime() {
        return jobworktime;
    }

    public void setJobworktime(String jobworktime) {
        this.jobworktime = jobworktime;
    }

    public String getJoblocation() {
        return joblocation;
    }

    public void setJoblocation(String joblocation) {
        this.joblocation = joblocation;
    }
}
