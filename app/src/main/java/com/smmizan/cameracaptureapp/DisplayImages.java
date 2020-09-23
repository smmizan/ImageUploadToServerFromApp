package com.smmizan.cameracaptureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smmizan.cameracaptureapp.api.ApiClient;
import com.smmizan.cameracaptureapp.api.ApiInterface;
import com.smmizan.cameracaptureapp.model.ImageModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayImages extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button button;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_images);


        imageView = findViewById(R.id.imageCapture);
        textView = findViewById(R.id.imgName);
        button = findViewById(R.id.imgUpload);

        bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
        imageView.setImageBitmap(bitmap);

        //textView.setText();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUpload();
            }
        });

    }





    private void ImageUpload()
    {
        String Images = imageToString();
        String ImageTitle = "IMG-4";
        //String ImageTitle = editText.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageModel> imageModelCall = apiInterface.uploadImage(ImageTitle,Images);

        imageModelCall.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                //Toast.makeText(ImageUploadFromAppToServer.this, "yes", Toast.LENGTH_SHORT).show();
                ImageModel imageModel = response.body();
                Toast.makeText(DisplayImages.this, "server response : "+imageModel.getResource(), Toast.LENGTH_SHORT).show();
                Log.e("mizan","photo sending server successfully!");
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(DisplayImages.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("mizan",t.getMessage());

            }
        });
    }




    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte,Base64.DEFAULT);

    }






}