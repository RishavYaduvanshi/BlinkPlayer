package com.rishav.blynkplayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

public class Splash extends AppCompatActivity {
    ImageView imageView;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(1024, 1024);
        ImageView imageView = (ImageView)findViewById(R.id.icon1);
        Glide.with((FragmentActivity)this).load(R.drawable.tenor).into(imageView);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent((Context)Splash.this, VideoList.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        },4000L);
    }
}
