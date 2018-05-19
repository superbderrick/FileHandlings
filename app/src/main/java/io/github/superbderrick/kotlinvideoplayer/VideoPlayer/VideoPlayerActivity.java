package io.github.superbderrick.kotlinvideoplayer.VideoPlayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import io.github.superbderrick.kotlinvideoplayer.R;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback{
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private VideoController mVideoController;
    private String mVideoPath = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        mSurfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        mSurfaceView.setOnClickListener(this);

        mSurfaceHolder= mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mVideoController = new VideoController();

        mVideoController.openVideo(mVideoPath, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.surfaceview:
                mVideoController.handleVideo();
                break;
            default:

                break;
        }
    }
}
