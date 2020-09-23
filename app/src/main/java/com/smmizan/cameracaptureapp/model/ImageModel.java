package com.smmizan.cameracaptureapp.model;

import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("title")
    private String Title;


    @SerializedName("image")
    private String Image;


    @SerializedName("response")
    private String Resource;


    public String getResource() {
        return Resource;
    }
}
