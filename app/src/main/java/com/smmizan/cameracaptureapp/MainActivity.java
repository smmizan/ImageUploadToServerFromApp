package com.smmizan.cameracaptureapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bNormal,bHigh,bSendImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bNormal = findViewById(R.id.bCaptureNormal);
        bHigh = findViewById(R.id.bCaptureHigh);
        bSendImage = findViewById(R.id.bSendImageToServer);

        bNormal.setOnClickListener(this);
        bHigh.setOnClickListener(this);
        bSendImage.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bCaptureNormal:
                startActivity(new Intent(MainActivity.this,CaptureNormalImages.class));
                break;
            case R.id.bCaptureHigh:
                startActivity(new Intent(MainActivity.this,HighQuaityImages.class));
                break;
            case R.id.bSendImageToServer:
                startActivity(new Intent(MainActivity.this,ImageUploadFromAppToServer.class));
                break;
        }

    }
}