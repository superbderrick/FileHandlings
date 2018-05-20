package io.github.superbderrick.kotlinvideoplayer.VideoPlayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import io.github.superbderrick.kotlinvideoplayer.R

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener, SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener{

    private val LOG_TAG = "VideoPlayerActivity"

    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mSurfaceHolder: SurfaceHolder
    private lateinit var mVideoController: VideoController
    private lateinit var mSeekbarVideo: SeekBar
    private var mUserSelectedPosition: Int = 0
    private var mUserIsSeeking: Boolean = false

    private lateinit var mVideoControllerView: LinearLayout
    private lateinit var mBackwardBtn: ImageButton
    private lateinit var mPlayBtn: ImageButton
    private lateinit var mForwardBtn: ImageButton

    private val mVideoPath: String = "http://cdn-fms.rbs.com.br/vod/hls_sample1_manifest.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playback)

        setUIComponents();

        mSurfaceView.setOnClickListener(this)
        mSeekbarVideo.setOnSeekBarChangeListener(this)

        mSurfaceHolder = mSurfaceView.holder
        mSurfaceHolder.addCallback(this)
    }

    private fun setUIComponents(){
        mSurfaceView = findViewById(R.id.surfaceview)
        mSeekbarVideo = findViewById(R.id.seekbar_video)

        mVideoControllerView = findViewById(R.id.videoController_view)

        mBackwardBtn = findViewById(R.id.backward_btn)
        mPlayBtn = findViewById(R.id.play_btn)
        mForwardBtn = findViewById(R.id.forward_btn)

        mBackwardBtn.setOnClickListener(this)
        mPlayBtn.setOnClickListener(this)
        mForwardBtn.setOnClickListener(this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        mVideoController = VideoController()
        mVideoController.openVideo(mVideoPath, surfaceHolder, mSeekbarVideo)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    /*
    Seekbar callback
     */
    override fun onStartTrackingTouch(p0: SeekBar?) {
        mUserIsSeeking = true
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(fromUser){
            mUserSelectedPosition = progress;
        }
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        mUserIsSeeking = false
        mVideoController.seekVideo(mUserSelectedPosition)
    }
    //Seekbar callback

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.surfaceview -> {
                    mVideoController.handleVideo()
                }

                R.id.backward_btn -> {

                }

                R.id.play_btn -> {

                }

                R.id.forward_btn -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mVideoController.releaseVideo()
    }

    override fun onStop() {
        super.onStop()
        mVideoController.pauseVideo()
    }
}