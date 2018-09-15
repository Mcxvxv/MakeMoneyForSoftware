package com.example.makemoneyforsoftware.ReleaseJobPackage;

public class JobType {
    private int imgId;
    private String text;

    public JobType(int imgId, String text) {
        this.imgId = imgId;
        this.text = text;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
