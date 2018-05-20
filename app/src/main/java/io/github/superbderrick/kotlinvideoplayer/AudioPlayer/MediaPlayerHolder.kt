package io.github.superbderrick.kotlinvideoplayer.AudioPlayer

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MediaPlayerHolder: PlayerAdapter{

    private val PLAYBACK_POSITION_REFRESH_INTERVAL_MS: Long = 1000

    private lateinit var mContext: Context
    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mPlaybackInfoListener: PlaybackInfoListener
    private lateinit var mExecutor: ScheduledExecutorService
    private lateinit var mSeekbarPositionUpdateTask: Runnable

    private var mResourceId: Int = 0

    constructor(context: Context){
        mContext = context.applicationContext
    }

    private fun initializeMediaPlayer(){
            mMediaPlayer = MediaPlayer()
            (mMediaPlayer as MediaPlayer).setOnCompletionListener(object: MediaPlayer.OnCompletionListener{
                override fun onCompletion(mediaPlayer: MediaPlayer?) {
                    stopUpdatingCallbackWithPosition(true)
                    logToUI("MediaPlayer playback completed");
                    if(mPlaybackInfoListener != null){
                        mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        mPlaybackInfoListener.onPlaybackCompleted();
                    }
                }
            })

            logToUI("mMediaPlayer = new MediaPlayer()")
    }

    fun setPlaybackInfoListener(listener: PlaybackInfoListener){
        mPlaybackInfoListener = listener
    }

    private fun startUpdatingCallbackWithPosition(){
            mExecutor = Executors.newSingleThreadScheduledExecutor();

            mSeekbarPositionUpdateTask = object: Runnable {
                override fun run(){
                    //updateProgressCallbackTask();
                }
            }
        mExecutor.scheduleAtFixedRate(mSeekbarPositionUpdateTask, 0, PLAYBACK_POSITION_REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS)
    }

    private fun stopUpdatingCallbackWithPosition(resetUIPlaybackPosition: Boolean){
            mExecutor.shutdown()
            if (resetUIPlaybackPosition && mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(0);
            }
    }

    override fun loadMedia(resourceId: Int?) {
        mResourceId = if(resourceId != null) resourceId else -1

        initializeMediaPlayer()

        var assetFileDescriptor: AssetFileDescriptor = mContext.resources.openRawResourceFd(mResourceId)

        try {
            logToUI("load() {1. setDataSource}")
            mMediaPlayer.setDataSource(assetFileDescriptor.fileDescriptor , assetFileDescriptor.startOffset ,assetFileDescriptor.length)
            assetFileDescriptor.close()

        }catch (e: Exception){
            logToUI(e.toString());
        }

        try{
            logToUI("load() {2. prepare}")
            mMediaPlayer.prepare()
        }catch (e: Exception){
            logToUI(e.toString());
        }

        initializeProgressCallback()
        logToUI("initializeProgressCallback()")
    }

    override fun release() {
        if (mMediaPlayer != null) {
            logToUI("release() and mMediaPlayer = null");
            mMediaPlayer.release();
        }
    }

    override fun isPlaying(): Boolean {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    override fun play() {
        if(mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            logToUI(String.format("playbackStart() %s",
                    mContext.getResources().getResourceEntryName(mResourceId)));
            mMediaPlayer.start();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }
    }

    override fun reset() {
        if(mMediaPlayer != null){
            logToUI("playbackReset()");
            mMediaPlayer.reset();
            loadMedia(mResourceId);
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    override fun pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
            logToUI("playbackPause()");
        }
    }

    override fun initializeProgressCallback() {
        val duration: Int = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
            logToUI(String.format("firing setPlaybackDuration(%d sec)",
                    TimeUnit.MILLISECONDS.toSeconds(duration.toLong())));
            logToUI("firing setPlaybackPosition(0)");
        }
    }

    override fun seekTo(position: Int) {
        if (mMediaPlayer != null) {
            logToUI(String.format("seekTo() %d ms", position));
            mMediaPlayer.seekTo(position);
        }
    }

    private fun updateProgressCallbackTask(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying) {
            val currentPosition = mMediaPlayer.currentPosition
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition)
            }
        }
    }

    private fun logToUI(message: String) {
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onLogUpdated(message)
        }
    }
}