package com.smmizan.cameracaptureapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.smmizan.cameracaptureapp.api.ApiClient;
import com.smmizan.cameracaptureapp.api.ApiInterface;
import com.smmizan.cameracaptureapp.model.ImageModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureNormalImages extends AppCompatActivity {

    Button button,bDisplay,bUpload;
    ImageView imageView;

    private static final int REQUEST_IMAGE_CAPTURE =101;

    String currentImagePath = null;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_normal_images);


        imageView = findViewById(R.id.imageCapture);
        button = findViewById(R.id.bCapture);
        bDisplay = findViewById(R.id.bDisplay);
        bUpload = findViewById(R.id.imgUpload);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

                }
            }
        });



        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUpload();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }


    }








    private void ImageUpload()
    {
        String Images = imageToString();
        String ImageTitle = "IMG-CAP";
        //String ImageTitle = editText.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageModel> imageModelCall = apiInterface.uploadImage(ImageTitle,Images);

        imageModelCall.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                //Toast.makeText(ImageUploadFromAppToServer.this, "yes", Toast.LENGTH_SHORT).show();
                ImageModel imageModel = response.body();
                Toast.makeText(CaptureNormalImages.this, "server response : "+imageModel.getResource(), Toast.LENGTH_SHORT).show();
                Log.e("mizan","photo sending server successfully!");
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(CaptureNormalImages.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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