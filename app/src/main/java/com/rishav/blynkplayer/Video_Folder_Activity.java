package com.rishav.blynkplayer;



import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;

public class Video_Folder_Activity extends AppCompatActivity {
    String myfolderName;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ArrayList<VideoFiles> videoFilesArrayList = new ArrayList<>();

    VideoFolderAdapter videoFolderAdapter;

    public ArrayList<VideoFiles> getAllVideos(Context paramContext, String paramString) {
        ArrayList<VideoFiles> arrayList = new ArrayList();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String str = "%" + paramString + "%";
        Cursor cursor = paramContext.getContentResolver().query(uri, new String[] { "_id", "_data", "title", "_size", "date_added", "duration", "_display_name", "bucket_display_name" }, "_data like?", new String[] { str }, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String str3 = cursor.getString(0);
                String str1 = cursor.getString(1);
                String str4 = cursor.getString(2);
                String str5 = cursor.getString(3);
                String str6 = cursor.getString(4);
                String str7 = cursor.getString(5);
                String str8 = cursor.getString(6);
                String str2 = cursor.getString(7);
                VideoFiles videoFiles = new VideoFiles(str3, str1, str4, str8, str5, str6, str7);
                Log.e("path", str1);
                if (paramString.endsWith(str2))
                    arrayList.add(videoFiles);
            }
            cursor.close();
        }
        return arrayList;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_video_folder);
       recyclerView = (RecyclerView)findViewById(R.id.FolderRv);
      toolbar = (MaterialToolbar)findViewById(R.id.topAppBar);
        String str = getIntent().getStringExtra("folderName");
        myfolderName = str;
        int i = str.lastIndexOf("/");
        str = myfolderName.substring(i + 1);
        getSupportActionBar().hide();
        toolbar.setTitle(str);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
        str = myfolderName;
        if (str != null)
            videoFilesArrayList = getAllVideos((Context)this, str);
        if (videoFilesArrayList.size() > 0) {
            VideoFolderAdapter videoFolderAdapter = new VideoFolderAdapter(this, videoFilesArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(videoFolderAdapter);

        }
    }
}
