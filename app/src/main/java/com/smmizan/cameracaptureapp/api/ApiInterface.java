package com.smmizan.cameracaptureapp.api;

import com.smmizan.cameracaptureapp.model.ImageModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("imageUpload.php")
    Call<ImageModel> uploadImage(@Field("title") String title, @Field("image") String Image);
}
