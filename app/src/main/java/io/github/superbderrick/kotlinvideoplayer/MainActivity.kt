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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //Go to Each sample
        when (item.itemId) {
            R.id.androidMediaPlayer_Video -> {

                var intent: Intent = Intent(this, VideoPlayerActivity::class.java)
                startActivity(intent)
            }
            R.id.exoPlayer -> {
                Toast.makeText(applicationContext, "ExoPlayer", Toast.LENGTH_SHORT).show()
            }
            R.id.androidMediaPlayer_Audio -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, AudioPlayerFragment.newInstance()).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }
}