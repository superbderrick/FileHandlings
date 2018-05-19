package io.github.superbderrick.kotlinvideoplayer.VideoPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

public class VideoController implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

    private MediaPlayer mMediaPlayer = null;

    public VideoController(){

    }

    public void openVideo(String aVideoPath, SurfaceHolder aSurfaceHolder){
        try{
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(aVideoPath);
            mMediaPlayer.setDisplay(aSurfaceHolder);

            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepare();
        }catch (Exception e){

        }
    }

    public void handleVideo(){
        if(mMediaPlayer.isPlaying() == true){
            pauseVideo();
        }else{
            startVideo();
        }
    }

    private void startVideo(){
        if(mMediaPlayer == null){
            return;
        }
        mMediaPlayer.start();
    }

    private void pauseVideo(){
        if(mMediaPlayer == null){
            return;
        }
        mMediaPlayer.pause();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }
}
