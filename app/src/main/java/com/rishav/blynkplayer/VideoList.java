package com.rishav.blynkplayer;



import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class VideoList extends AppCompatActivity {
    static ArrayList<String> folderList;

    static ArrayList<VideoFiles> videoFiles = new ArrayList<>();

    BottomNavigationView bottomNavigationView;

    SharedData sd;

    static {
        folderList = new ArrayList<>();
    }

    public ArrayList<File> findVideo(File paramFile) {
        ArrayList<File> arrayList = new ArrayList();
        for (File paraFile : paramFile.listFiles()) {
            if (paraFile.isDirectory()) {
                arrayList.addAll(findVideo(paraFile));
            } else if (paraFile.getName().endsWith(".mp4")) {
                arrayList.add(paraFile);
                Log.d("find", paraFile.getPath());
            }
        }
        return arrayList;
    }

    public ArrayList<VideoFiles> getAllVideos(Context paramContext) {
        ArrayList<VideoFiles> arrayList = new ArrayList();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = paramContext.getContentResolver().query(uri, new String[] { "_id", "_data", "title", "_size", "date_added", "duration", "_display_name" }, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String str2 = cursor.getString(0);
                String str1 = cursor.getString(1);
                String str3 = cursor.getString(2);
                String str4 = cursor.getString(3);
                String str5 = cursor.getString(4);
                String str6 = cursor.getString(5);
                VideoFiles videoFiles = new VideoFiles(str2, str1, str3, cursor.getString(6), str4, str5, str6);
                Log.e("path", str1);
                str1 = str1.substring(0, str1.lastIndexOf("/"));
                if (!folderList.contains(str1))
                    folderList.add(str1);
                arrayList.add(videoFiles);
            }
            Collections.sort(folderList);
            cursor.close();
        }
        return arrayList;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_video_list);
        this.sd = new SharedData(getApplicationContext());
        getSupportActionBar().setTitle("BLINK Player");
        getSupportActionBar().setIcon(R.drawable.play);
        this.bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        runtimePermission();
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem param1MenuItem) {
                int i = param1MenuItem.getItemId();
                if (i != R.id.fileslist) {
                    if (i == R.id.folderlist) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainfragment, new FolderFragment());
                        fragmentTransaction.commit();
                        param1MenuItem.setChecked(true);
                    }
                } else {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainfragment, new FilesFragment());
                    fragmentTransaction.commit();
                    param1MenuItem.setChecked(true);
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.top_app_bar, paramMenu);
        final MenuItem menuItem = paramMenu.findItem(R.id.normal);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem param1MenuItem) {
                VideoList.this.sd.setBl(!VideoList.this.sd.isBl());
                if (!VideoList.this.sd.isBl()) {
                    menuItem.setTitle("Normal");
                } else {
                    menuItem.setTitle("Blink");
                }
                return false;
            }
        });
        if (!this.sd.isBl()) {
            menuItem.setTitle("Normal");
        } else {
            menuItem.setTitle("Blink");
        }
        return super.onCreateOptionsMenu(paramMenu);
    }

    public void runtimePermission() {
        Dexter.withActivity((Activity)this).withPermission("android.permission.READ_EXTERNAL_STORAGE").withListener(new PermissionListener() {
            public void onPermissionDenied(PermissionDeniedResponse param1PermissionDeniedResponse) {
                Log.d("ERROR", param1PermissionDeniedResponse.toString());
            }

            public void onPermissionGranted(PermissionGrantedResponse param1PermissionGrantedResponse) {
                VideoList videoList = VideoList.this;
                VideoList.videoFiles = videoList.getAllVideos((Context)videoList);
                Log.d("onpermision", VideoList.videoFiles.toString());
                FragmentTransaction fragmentTransaction = VideoList.this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainfragment, new FolderFragment());
                fragmentTransaction.commit();
            }

            public void onPermissionRationaleShouldBeShown(PermissionRequest param1PermissionRequest, PermissionToken param1PermissionToken) {
                param1PermissionToken.continuePermissionRequest();
            }
        }).check();
    }
}
