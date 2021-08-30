package com.rishav.blynkplayer;


import android.annotation.SuppressLint;
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

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.MyViewHolder> {
    static ArrayList<VideoFiles> folderVideoFiles;

    private Context mcontext;

    SharedData sharedData;

    View view;

    public VideoFolderAdapter(Context paramContext, ArrayList<VideoFiles> paramArrayList) {
        this.mcontext = paramContext;
        folderVideoFiles = paramArrayList;
        this.sharedData = new SharedData(paramContext);
    }

    public int getItemCount() {
        return folderVideoFiles.size();
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
    @NonNull
    @Override
    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int position) {
        paramMyViewHolder.fileName.setText((folderVideoFiles.get(position)).getTitle());
        Glide.with(mcontext).load(new File((folderVideoFiles.get(position)).getPath()))
                .thumbnail(0.05F)
                .into(paramMyViewHolder.thumbnail);
        String path = folderVideoFiles.get(position).getPath();
        System.out.println("timee"+folderVideoFiles.get(position).getDuration());
        try {
            long l = Long.parseLong(folderVideoFiles.get(position).getDuration());
            paramMyViewHolder.videoDuration.setText(getTimer(l));
        }catch (Exception e){

        }

        paramMyViewHolder.itemView.setOnClickListener(param1View -> {
            Intent intent;
            if (sharedData.isBl()) {
                intent = new Intent(mcontext, SimPlayer.class);
            } else {
                intent = new Intent(mcontext, MainActivity.class);
            }
            intent.putExtra("position", position).putExtra("path", path);
            mcontext.startActivity(intent);
            Log.d("path",path);
        });
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        this.view = LayoutInflater.from(this.mcontext).inflate(R.layout.video_items, paramViewGroup, false);
        return new MyViewHolder(this.view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;

        ImageView thumbnail;

        TextView videoDuration;

        public MyViewHolder(View param1View) {
            super(param1View);
            this.thumbnail = (ImageView)param1View.findViewById(R.id.thumbnail);
            this.fileName = (TextView)param1View.findViewById(R.id.Video_file_name);
            this.videoDuration = (TextView)param1View.findViewById(R.id.videoduration);
        }
    }
}
