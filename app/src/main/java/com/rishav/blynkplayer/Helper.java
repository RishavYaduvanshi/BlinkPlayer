package com.rishav.blynkplayer;


import android.app.Activity;
import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;
import java.io.IOException;

public class Helper extends VideoView {
    private Context activityContext;

    private CameraSource cameraSource;

    int n = 0;

    public Helper(Context paramContext) {
        super(paramContext);
    }

    public Helper(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public Helper(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    private void createCameraSource() {
        FaceDetector faceDetector = (new FaceDetector.Builder(this.activityContext)).setTrackingEnabled(true).setClassificationType(1).setMode(0).build();
        faceDetector.setProcessor((Detector.Processor)new LargestFaceFocusingProcessor((Detector)faceDetector, new EyesTracker()));
        this.cameraSource = (new CameraSource.Builder(this.activityContext, (Detector)faceDetector)).setRequestedPreviewSize(1024, 768).setFacing(1).setRequestedFps(30.0F).build();
        try {
            if (ActivityCompat.checkSelfPermission(this.activityContext, "android.permission.CAMERA") != 0) {
                ActivityCompat.requestPermissions((Activity)this.activityContext, new String[] { "android.permission.CAMERA" }, 1);
                Toast.makeText(this.activityContext, "Grant Permission and restart app", Toast.LENGTH_LONG).show();
            } else {
                this.cameraSource.start();
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void destroy() {
        CameraSource cameraSource = this.cameraSource;
        if (cameraSource != null)
            cameraSource.release();
    }

    public void init(Context paramContext) {
        this.activityContext = paramContext;
        createCameraSource();
    }

    public boolean isPlaying() {
        return super.isPlaying();
    }

    public void pause() {
        super.pause();
    }

    public void paused() {
        CameraSource cameraSource = this.cameraSource;
        if (cameraSource != null)
            cameraSource.stop();
        if (isPlaying())
            pause();
    }

    public void resume() {
        if (this.cameraSource != null)
            try {
                if (ActivityCompat.checkSelfPermission(this.activityContext, "android.permission.CAMERA") != 0) {
                    ActivityCompat.requestPermissions((Activity)this.activityContext, new String[] { "android.permission.CAMERA" }, 1);
                    Toast.makeText(this.activityContext, "Grant Permission and restart app", Toast.LENGTH_LONG).show();
                }
                this.cameraSource.start();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
    }

    public void setLookMe() {
        try {
            if (ActivityCompat.checkSelfPermission(this.activityContext, "android.permission.CAMERA") != 0) {
                ActivityCompat.requestPermissions((Activity)this.activityContext, new String[] { "android.permission.CAMERA" }, 1);
                Toast.makeText(this.activityContext, "Grant Permission and restart app", Toast.LENGTH_LONG).show();
            }
            this.cameraSource.start();
            Log.d("ReadThis", "camera-source started, outside");
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void start() {
        super.start();
    }

    private class EyesTracker extends Tracker<Face> {
        private final float THRESHOLD = 0.75F;

        int open = 1;

        long t = 0L;

        private EyesTracker() {}

        public void onDone() {
            super.onDone();
        }

        public void onMissing(Detector.Detections<Face> param1Detections) {
            super.onMissing(param1Detections);
            Helper.this.pause();
        }

        public void onUpdate(Detector.Detections<Face> param1Detections, Face param1Face) {
            if (param1Face.getIsLeftEyeOpenProbability() > 0.75F || param1Face.getIsRightEyeOpenProbability() > 0.75F) {
                this.t = 0L;
                if (!Helper.this.isPlaying() && Helper.this.n == 0) {
                    Helper.this.start();
                } else if (!Helper.this.isPlaying() && Helper.this.n == 1) {
                    this.open = 0;
                }
                return;
            }
            long l = this.t + 1L;
            this.t = l;
            if (l > 5L)
                if (Helper.this.isPlaying()) {
                    Helper.this.pause();
                    Helper.this.n = 1;
                } else if (!Helper.this.isPlaying() && this.open == 0) {
                    Helper.this.n = 0;
                    this.open = 1;
                }
        }
    }
}
