package com.rishav.blynkplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FolderFragment extends Fragment {
    boolean fbl;

    FolderAdapter folderAdapter;

    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_folder, paramViewGroup, false);
        this.recyclerView = (RecyclerView)view.findViewById(R.id.folderRv);
        if (VideoList.folderList != null && VideoList.folderList.size() > 0 && VideoList.videoFiles != null) {
            FolderAdapter folderAdapter = new FolderAdapter(VideoList.folderList, VideoList.videoFiles, getContext());
            this.folderAdapter = folderAdapter;
            this.recyclerView.setAdapter(folderAdapter);
            this.recyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        return view;
    }
}
