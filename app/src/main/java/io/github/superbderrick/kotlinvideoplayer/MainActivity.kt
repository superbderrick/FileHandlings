package io.github.superbderrick.kotlinvideoplayer

import android.content.Intent
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
import io.github.superbderrick.kotlinvideoplayer.AudioPlayer.AudioPlayerFragment
import io.github.superbderrick.kotlinvideoplayer.VideoPlayer.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private val LOG_TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        Log.d(LOG_TAG, "onCreate: finished")
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

                var i: Intent = Intent(this, VideoPlayerActivity::class.java)
                startActivity(i)
            }
            R.id.exoPlayer -> {
                Toast.makeText(applicationContext, "exoPlayer", Toast.LENGTH_SHORT).show()
            }
            R.id.androidMediaPlayer_Audio -> {
                //Toast.makeText(applicationContext, "androidMediaPlayer_Audio", Toast.LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction().replace(R.id.container, AudioPlayerFragment.newInstance()).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
