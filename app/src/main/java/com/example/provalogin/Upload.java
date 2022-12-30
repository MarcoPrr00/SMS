package com.example.provalogin;

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload(){

    }

    public Upload(String name, String imageUrl){
        mName=name;
        mImageUrl=imageUrl;

    }

    public String getName(){
        return mName;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public void setName(String name){
        mName=name;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl=imageUrl;
    }

}
