package com.rishav.blynkplayer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class SimPlayer extends AppCompatActivity {
    VideoView videoView;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_sim_player);

        getSupportActionBar().hide();
        getWindow().setFlags(1024, 1024);
        videoView = (VideoView)findViewById(R.id.videoview);
        String str = getIntent().getStringExtra("path");
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(str);
        videoView.start();
    }
}
