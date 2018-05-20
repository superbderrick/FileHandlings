package io.github.superbderrick.kotlinvideoplayer.VideoPlayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class VideoController : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

    private val LOG_TAG : String = "VideoController"

    private val PLAYBACK_POSITION_REFRESH_INTERVAL_MS: Long = 1000

    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mSeekBar: SeekBar

    private lateinit var mExecutor: ScheduledExecutorService
    private lateinit var mSeekbarPositionUpdateTask: Runnable

    constructor(){

    }

    public fun openVideo(aVideoPath: String, aSurfaceHolder: SurfaceHolder?, aSeekbar: SeekBar){
        try{
            mMediaPlayer = MediaPlayer()

            mMediaPlayer.setDataSource(aVideoPath)
            mMediaPlayer.setDisplay(aSurfaceHolder)

            mMediaPlayer.setOnCompletionListener(this)
            mMediaPlayer.setOnPreparedListener(this)
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer.prepare()

            mSeekBar = aSeekbar
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    public fun handleVideo(videocontrolview : LinearLayout){
        if(videocontrolview.visibility == View.VISIBLE){
            videocontrolview.visibility = View.INVISIBLE
        }else{
            videocontrolview.visibility = View.VISIBLE
        }
    }

    public fun startVideo(){
        if(mMediaPlayer == null){
            return
        }
        startUpdatingCallbackWithPosition()
        mMediaPlayer.start()
    }

    public fun pauseVideo(){
        if(mMediaPlayer == null){
            return
        }
        mMediaPlayer.pause()
    }

    public fun releaseVideo(){
        if(mMediaPlayer == null){
            return
        }
        mMediaPlayer.release()
    }
    public fun seekVideo(position: Int){
        if(mMediaPlayer == null){
            return
        }
        mMediaPlayer.seekTo(position)
    }

    public fun durationVideo(): Int{
        if(mMediaPlayer == null){
            return 0
        }
        return mMediaPlayer.duration
    }

    public fun getVideoStatus(): Boolean{
        return mMediaPlayer.isPlaying
    }

    private fun startUpdatingCallbackWithPosition(){
        mExecutor = Executors.newSingleThreadScheduledExecutor()
        mSeekbarPositionUpdateTask = object : Runnable{
            override fun run(){
                mSeekBar.setProgress(mMediaPlayer.currentPosition)
            }
        }
        mExecutor.scheduleAtFixedRate(mSeekbarPositionUpdateTask, 0, PLAYBACK_POSITION_REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS)
    }

    /*
    MediaPlayer.OnPreparedListener
     */
    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mSeekBar.max = durationVideo()

        if(mediaPlayer != null){
            startVideo()
        }
    }
    //MediaPlayer.OnPreparedListener

    /*
    MediaPlayer.OnCompletionListener
     */
    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        if(mediaPlayer != null){
            releaseVideo()
        }
    }
    //MediaPlayer.OnCompletionListener
}