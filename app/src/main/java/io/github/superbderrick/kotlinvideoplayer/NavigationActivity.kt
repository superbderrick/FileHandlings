package io.github.superbderrick.kotlinvideoplayer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import io.github.superbderrick.kotlinvideoplayer.AudioManager.MediaPlayerHolder
import io.github.superbderrick.kotlinvideoplayer.AudioManager.PlaybackInfoListener
import io.github.superbderrick.kotlinvideoplayer.AudioManager.PlayerAdapter
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import android.widget.ScrollView



class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private val LOG_TAG: String = "NavigationActivity"
    //Locate Playback mp3 file
    private val MEDIA_RES_ID: Int = R.raw.dfff

    private lateinit var mTextDebug: TextView
    private lateinit var mSeekbarAudio: SeekBar
    private lateinit var mScrollContainer: ScrollView
    private lateinit var mPlayerAdapter: PlayerAdapter
    private var mUserIsSeeking: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initializeUI()
        initializeSeekbar()
        initializePlaybackController()
        Log.d(LOG_TAG, "onCreate: finished")
    }

    override fun onStart() {
        super.onStart()
        mPlayerAdapter.loadMedia(MEDIA_RES_ID)
        Log.d(LOG_TAG, "onStart: create MediaPlayer")
    }

    override fun onStop() {
        super.onStop()
        /*
        [isChangingConfigurations]
        Check to see whether this activity is in the process of being destroyed in order to be recreated with a new configuration.
         */
        if(isChangingConfigurations() && mPlayerAdapter.isPlaying()){
            Log.d(LOG_TAG, "onStop: don't release MediaPlayer as screen is rotating & playing")
        }else{
            mPlayerAdapter.release()
            Log.d(LOG_TAG, "onStop: release MediaPlayer")
        }
    }

    fun initializeUI(){
        mTextDebug = findViewById(R.id.text_debug)
        val mPlayBtn: Button = findViewById(R.id.button_play)
        val mPauseBtn: Button = findViewById(R.id.button_pause)
        val mResetBtn: Button = findViewById(R.id.button_reset)
        mSeekbarAudio = findViewById(R.id.seekbar_audio)
        mScrollContainer = findViewById(R.id.scroll_container)

        mPlayBtn.setOnClickListener(this)
        mPauseBtn.setOnClickListener(this)
        mResetBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.button_play -> {
                    //Log.v(LOG_TAG, "play!!")
                    mPlayerAdapter.play()
                }
                R.id.button_pause -> {
                    //Log.v(LOG_TAG, "pause!!")
                    mPlayerAdapter.pause()
                }
                R.id.button_reset -> {
                    //Log.v(LOG_TAG, "reset!!")
                    mPlayerAdapter.reset()
                }
            }
        }
    }

    private fun initializePlaybackController(){
        var mMediaPlayerHolder: MediaPlayerHolder = MediaPlayerHolder(this)
        Log.d(LOG_TAG, "initializePlaybackController: created MediaPlayerHolder")
        mMediaPlayerHolder.setPlaybackInfoListener(PlaybackListener())
        mPlayerAdapter = mMediaPlayerHolder
        Log.d(LOG_TAG, "initializePlaybackController: MediaPlayerHolder progress callback set")
    }

    private fun initializeSeekbar(){
        mSeekbarAudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.androidMediaPlayer_Video -> {
                // Handle the camera action
                Toast.makeText(applicationContext, "androidMediaPlayer_Video", Toast.LENGTH_SHORT).show()
            }
            R.id.exoPlayer -> {
                Toast.makeText(applicationContext, "exoPlayer", Toast.LENGTH_SHORT).show()
            }
            R.id.androidMediaPlayer_Audio -> {
                Toast.makeText(applicationContext, "androidMediaPlayer_Audio", Toast.LENGTH_SHORT).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
            mSeekbarAudio.setMax(duration)
            Log.d(LOG_TAG, String.format("setPlaybackDuration: setMax(%d)", duration))
        }

        override fun onPositionChanged(position: Int) {
            super.onPositionChanged(position)
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(position, true);
                Log.d(LOG_TAG, String.format("setPlaybackPosition: setProgress(%d)", position))
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
}
