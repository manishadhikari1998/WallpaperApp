package com.manish.wallsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity2 extends AppCompatActivity {
    ImageView imageview;
    TextView textView, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageview = findViewById(R.id.imageview);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        Intent intent = getIntent();
        int data = intent.getIntExtra("likes",0);
        int data2 = intent.getIntExtra("views",0);
        int  data3 = intent.getIntExtra("downloads",0);
        String data4 = intent.getStringExtra("imageUrl");

        Glide.with(MainActivity2.this).load(data4).into(imageview);
        textView.setText("Likes"+"\n"+data);
        textView2.setText("Views"+"\n"+data2);
        textView3.setText("Downloads"+"\n"+data3);



    }
}