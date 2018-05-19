package io.github.superbderrick.kotlinvideoplayer.VideoPlayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.SurfaceHolder

class VideoController : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

    private lateinit var mMediaPlayer: MediaPlayer

    constructor(){

    }

    public fun openVideo(aVideoPath: String, aSurfaceHolder: SurfaceHolder?){
        try{
            mMediaPlayer = MediaPlayer()

            mMediaPlayer.setDataSource(aVideoPath)
            mMediaPlayer.setDisplay(aSurfaceHolder)

            mMediaPlayer.setOnCompletionListener(this)
            mMediaPlayer.setOnPreparedListener(this)
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer.prepare()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    public fun handleVideo(){
        if(mMediaPlayer.isPlaying() == true){
            pauseVideo()
        }else{
            startVideo()
        }
    }

    public fun startVideo(){
        if(mMediaPlayer == null){
            return
        }
        mMediaPlayer.start()
    }

    public fun pauseVideo(){
        if(mMediaPlayer == null){
            return
        }
        mMediaPlayer.pause()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        if(mediaPlayer != null){
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        if(mediaPlayer != null){
            mediaPlayer.start()
        }
    }
}