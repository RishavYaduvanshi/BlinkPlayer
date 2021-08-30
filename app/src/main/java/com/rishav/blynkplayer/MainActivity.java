package com.rishav.blynkplayer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {
    AudioManager audioManager;

    Button btn;

    private Helper helper;

    int r = 1;

    Slider seekBarb;

    SeekBar seekBarv;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(128);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        Button button = (Button)findViewById(R.id.rot);
        this.btn = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                if (r == 1) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                   r = 0;
                } else {
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                   r = 1;
                }
            }
        });
        getSupportActionBar().hide();
        getWindow().setFlags(1024, 1024);
        helper = (Helper)findViewById(R.id.lookme);
        seekBarb = (Slider)findViewById(R.id.brightness);
        seekBarv = (SeekBar)findViewById(R.id.volume);
        seekBarb.setValue(0.005F);
//        this.seekBarb.addOnChangeListener((BaseOnChangeListener)new Slider.OnChangeListener() {
//            public void onValueChange(Slider param1Slider, float param1Float, boolean param1Boolean) {
//                params.screenBrightness = param1Float / 1000.0F;
//                Log.e("bri", String.valueOf(param1Float / 1000.0F));
//                MainActivity.this.getWindow().setAttributes(params);
//            }
//        });
//        final AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService("audio");
//        int i = audioManager.getStreamMaxVolume(3);
//        int j = audioManager.getStreamVolume(3);
//        this.seekBarv.setMax(i);
//        this.seekBarv.setProgress(j);
        seekBarv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
                audioManager.setStreamVolume(3, param1Int, 0);
            }

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {}
        });
        String str = getIntent().getStringExtra("path");
        helper.init(this);
        helper.setVideoURI(Uri.parse(str));
        Log.e("url", str);
        helper.start();
        helper.setLookMe();
    }
}
