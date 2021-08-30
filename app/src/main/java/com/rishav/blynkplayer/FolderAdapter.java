package com.rishav.blynkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyHolder> {
    private ArrayList<String> folderName;

    private Context mcontext;

    private ArrayList<VideoFiles> videoFiles;

    public FolderAdapter(ArrayList<String> paramArrayList, ArrayList<VideoFiles> paramArrayList1, Context paramContext) {
        this.folderName = paramArrayList;
        this.videoFiles = paramArrayList1;
        this.mcontext = paramContext;
    }
    @NonNull
    @Override
    public FolderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(this.mcontext).inflate(R.layout.folder_items, parent, false));
    }
    int NumberOfFiles(String paramString) {
        int i = 0;
        for (VideoFiles videoFiles : videoFiles) {
            int j = i;
            if (videoFiles.getPath().substring(0, videoFiles.getPath().lastIndexOf("/")).endsWith(paramString))
                j = i + 1;
            i = j;
        }
        return i;
    }
    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.MyHolder holder, int position) {
        int i = ((String)folderName.get(position)).lastIndexOf("/");
        String str = ((String)folderName.get(position)).substring(i + 1);
        holder.folderName.setText(str);
        holder.counterFile.setText(String.valueOf(NumberOfFiles(str)) + " Videos");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Intent intent = new Intent(mcontext, Video_Folder_Activity.class);
                intent.putExtra("folderName", str);
                mcontext.startActivity(intent);
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderName.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView counterFile;

        TextView folderName;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            folderName = (TextView)itemView.findViewById(R.id.folderName);
            counterFile = (TextView)itemView.findViewById(R.id.count);
        }
    }
}

