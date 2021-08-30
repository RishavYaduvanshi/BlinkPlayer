package com.rishav.blynkplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FilesFragment extends Fragment {
    boolean fbl = false;

    RecyclerView recyclerView;

    VideoAdapter videoAdapter;

    View view;

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_files, paramViewGroup, false);
        this.view = view;
        this.recyclerView = (RecyclerView)view.findViewById(R.id.filesRv);
        if (VideoList.videoFiles != null && VideoList.videoFiles.size() > 0) {
            VideoAdapter videoAdapter = new VideoAdapter(getContext(), VideoList.videoFiles);
            this.videoAdapter = videoAdapter;
            this.recyclerView.setAdapter(videoAdapter);
            this.recyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        return this.view;
    }
}
