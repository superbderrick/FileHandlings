package io.github.superbderrick.kotlinvideoplayer.AudioPlayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import io.github.superbderrick.kotlinvideoplayer.R

/**
 * Created by derrick on 14/05/2018.
 */
class AudioPlayerFragment: Fragment(), View.OnClickListener{

    private val LOG_TAG: String = "AudioPlayerFragment"

    private val MEDIA_RES_ID: Int = R.raw.dfff

    private lateinit var mTextDebug: TextView
    private lateinit var mAudioSeekbar: SeekBar
    private lateinit var mScrollContainer: ScrollView
    private lateinit var mPlayerAdapter: PlayerAdapter
    private var mUserIsSeeking: Boolean = false

    private lateinit var mPlayBtn: Button
    private lateinit var mPauseBtn: Button
    private lateinit var mResetBtn: Button

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_audio, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI(view)
        initializeSeekbar()
        initializePlaybackController()
    }

    private fun initializeUI(v: View?){
        if(v != null){
            mTextDebug = v.findViewById(R.id.text_debug)
            mPlayBtn = v.findViewById(R.id.button_play)
            mPauseBtn = v.findViewById(R.id.button_pause)
            mResetBtn = v.findViewById(R.id.button_reset)
            mAudioSeekbar = v.findViewById(R.id.seekbar_audio)
            mScrollContainer = v.findViewById(R.id.scroll_container)
        }

        mPlayBtn.setOnClickListener(this)
        mPauseBtn.setOnClickListener(this)
        mResetBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.button_play -> {
                    mPlayerAdapter.play()
                }
                R.id.button_pause -> {
                    mPlayerAdapter.pause()
                }
                R.id.button_reset -> {
                    mPlayerAdapter.reset()
                }
            }
        }
    }

    private fun initializeSeekbar(){
        mAudioSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            var userSelectedPosition: Int = 0

            override fun onStartTrackingTouch(seekBar: SeekBar){
                mUserIsSeeking = true
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    userSelectedPosition = progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mUserIsSeeking = false;
                mPlayerAdapter.seekTo(userSelectedPosition)
            }
        })
    }

    private fun initializePlaybackController(){
        var mMediaPlayerHolder: MediaPlayerHolder = MediaPlayerHolder(activity)

        mMediaPlayerHolder.setPlaybackInfoListener(PlaybackListener())
        mPlayerAdapter = mMediaPlayerHolder

    }

    inner class PlaybackListener: PlaybackInfoListener(){

        override fun onLogUpdated(formattedMessage: String?) {
            super.onLogUpdated(formattedMessage)
            if(mTextDebug != null){
                mTextDebug.append(formattedMessage)
                mTextDebug.append("\n")
                // Moves the scrollContainer focus to the end.
                mScrollContainer.post { mScrollContainer.fullScroll(ScrollView.FOCUS_DOWN) }
            }
        }

        override fun onDurationChanged(duration: Int) {
            super.onDurationChanged(duration)
            mAudioSeekbar.setMax(duration)
        }

        override fun onPositionChanged(position: Int) {
            super.onPositionChanged(position)
            if (!mUserIsSeeking) {
                mAudioSeekbar.setProgress(position)
            }
        }

        override fun onStateChanged(state: Int) {
            super.onStateChanged(state)
            var stateToString: String = PlaybackInfoListener.convertStateToString(state)
            onLogUpdated(String.format("onStateChanged(%s)", stateToString))
        }

        override fun onPlaybackCompleted() {
            super.onPlaybackCompleted()
        }
    }

    override fun onStart() {
        super.onStart()
        mPlayerAdapter.loadMedia(MEDIA_RES_ID)
    }

    override fun onStop() {
        super.onStop()
        /*
    [isChangingConfigurations]
    Check to see whether this activity is in the process of being destroyed in order to be recreated with a new configuration.
     */
        if(activity.isChangingConfigurations() && mPlayerAdapter.isPlaying()){
            Log.d(LOG_TAG, "onStop: don't release MediaPlayer as screen is rotating & playing")
        }else{
            mPlayerAdapter.release()
            Log.d(LOG_TAG, "onStop: release MediaPlayer")
        }
    }

    companion object {
        fun newInstance(): AudioPlayerFragment = AudioPlayerFragment()
    }
}