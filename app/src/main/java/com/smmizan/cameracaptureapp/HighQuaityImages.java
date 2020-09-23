package com.smmizan.cameracaptureapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HighQuaityImages extends AppCompatActivity {

    String currentImagePath = null;

    Button button,bDisplay,bUpload;
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE =101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //imageView = findViewById(R.id.imageCapture);
        button = findViewById(R.id.bCapture);
        bDisplay = findViewById(R.id.bDisplay);
        bUpload = findViewById(R.id.bDisplay);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager())!= null)
                {
                    File imageFile = null;

                    try {
                        imageFile = getImageFile();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if(imageFile != null){
                        Uri imageUri = FileProvider.getUriForFile(HighQuaityImages.this,"com.example.android.fileprovider",imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

                    }

                }

            }
        });






        bDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDisplay = new Intent(HighQuaityImages.this,DisplayImages.class);
                iDisplay.putExtra("image_path",currentImagePath);
                startActivity(iDisplay);

            }
        });

    }


    private File getImageFile() throws Exception{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName,".jpg",storageDir);
        currentImagePath = imageFile.getAbsolutePath();

        Log.e("mizan name",imageName);
        Log.e("mizan path",currentImagePath);

        return imageFile;

    }
}
