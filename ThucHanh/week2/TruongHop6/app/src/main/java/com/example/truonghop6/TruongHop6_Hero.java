package com.example.truonghop6;

public class TruongHop6_Hero {
    private String mName;
    private int mImage;

    public TruongHop6_Hero(String mName, int mImage) {
        this.mName = mName;
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }
}