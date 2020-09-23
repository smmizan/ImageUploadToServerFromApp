package com.smmizan.cameracaptureapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.smmizan.cameracaptureapp.api.ApiClient;
import com.smmizan.cameracaptureapp.api.ApiInterface;
import com.smmizan.cameracaptureapp.model.ImageModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadFromAppToServer extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    EditText editText;
    Button bImageSelect,bImageUpload;
    Bitmap bitmap;
    private static final int IMG_REQ = 777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_from_app_to_server);

        imageView = findViewById(R.id.upload_image);
        editText = findViewById(R.id.image_title);
        bImageSelect = findViewById(R.id.b_image_choice);
        bImageUpload = findViewById(R.id.b_image_Upload);
        bImageSelect.setOnClickListener(this);
        bImageUpload.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.b_image_choice:

                selectImagesFromGellary();

                break;
            case R.id.b_image_Upload:
                ImageUpload();
                break;

        }

    }

    private void selectImagesFromGellary()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQ && resultCode == RESULT_OK && data!=null)
        {
            Uri uri = data.getData();
            //String imageFile = new File(getRealPathFromURI(selectedImageURI));

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                bImageSelect.setEnabled(false);
                bImageUpload.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


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
                Toast.makeText(ImageUploadFromAppToServer.this, "server response : "+imageModel.getResource(), Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                bImageSelect.setEnabled(true);
                bImageUpload.setEnabled(false);
                editText.setText("");
                Log.e("mizan","photo sending server successfully!");
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(ImageUploadFromAppToServer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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