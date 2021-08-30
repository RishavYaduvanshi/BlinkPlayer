package com.rishav.blynkplayer;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    static ArrayList<VideoFiles> videoFiles;

    private Context mcontext;

    SharedData sharedData;

    View view;

    public VideoAdapter(Context paramContext, ArrayList<VideoFiles> paramArrayList) {
        this.mcontext = paramContext;
        videoFiles = paramArrayList;
        this.sharedData = new SharedData(paramContext);
    }
    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(this.mcontext).inflate(R.layout.folder_items, parent, false);
        return new MyViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        holder.fileName.setText(videoFiles.get(position).getTitle());
        Glide.with(mcontext).load(new File(videoFiles.get(position).getPath())).thumbnail(0.05F).into(holder.thumbnail);
        long l = Long.parseLong(videoFiles.get(position).getDuration());
        String path = videoFiles.get(position).getPath();
        holder.videoDuration.setText(getTimer(l));
        holder.itemView.setOnClickListener(param1View -> {
            Intent intent;
            if (sharedData.isBl()) {
                intent = new Intent(mcontext, SimPlayer.class);
            } else {
                intent = new Intent(mcontext, MainActivity.class);
            }
            intent.putExtra("position", position).putExtra("path", path);
            VideoAdapter.this.mcontext.startActivity(intent);
            Log.d("path", path);
        });
    }

    @Override
    public int getItemCount() {
        return videoFiles.size();
    }
    public String getTimer(long paramLong) {
        String str2;
        String str1 = "";
        int i = (int)(paramLong / 3600000L);
        int j = (int)(paramLong % 3600000L) / 60000;
        int k = (int)(paramLong % 3600000L) % 60000 / 1000;
        if (i > 0)
            str1 = i + ":";
        if (k < 10) {
            str2 = "0" + k;
        } else {
            str2 = "" + k;
        }
        return str1 + j + ":" + str2;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fileName;

        ImageView thumbnail;

        TextView videoDuration;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.folder_image);
            fileName = (TextView)itemView.findViewById(R.id.folderName);
            videoDuration = (TextView)itemView.findViewById(R.id.count);
        }
    }
}

