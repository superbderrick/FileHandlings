package io.github.superbderrick.kotlinvideoplayer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import io.github.superbderrick.kotlinvideoplayer.AudioPlayer.AudioPlayerFragment
import io.github.superbderrick.kotlinvideoplayer.Fragment.VideoGridFragment
import io.github.superbderrick.kotlinvideoplayer.VideoPlayer.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private val LOG_TAG: String = "MainActivity"
    private val STORAGE_PERMISSION_CODE: Int = 1000

    private lateinit var mView: View;
    private lateinit var mContext: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        mContext = this

        initUIComponent()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    fun initUIComponent(){
        mView = findViewById(R.id.drawer_layout);
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
                checkReadStoragePermission();
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

    private fun showVideoList(){
        /*
        var intent: Intent = Intent(this, VideoPlayerActivity::class.java)
        startActivity(intent)
        */
        supportFragmentManager.beginTransaction().replace(R.id.container, VideoGridFragment.newInstance()).commit()

        supportActionBar?.hide()
    }

    //Runtime Permission
    private fun checkReadStoragePermission(){
        //Android version after mashmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            /*
            Check the users already permission granted or not
             */
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                /*
                If user not allow permission granted Before, These function restitue true
                 */
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
                }else{  //First time to permission
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
                }
            }else{  //These App already has permission
                showVideoList()
            }
        }else{  //Lower Android Version, don't need permission
            showVideoList()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_PERMISSION_CODE -> {
                //User accept the permission
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showVideoList()
                }
                else{   //User don't accept the permission
                    Snackbar.make(mView, "These Permission is necessary If u want, Click Again", Snackbar.LENGTH_LONG)
                            .setAction("Again", object: View.OnClickListener{
                                override fun onClick(p0: View?) {
                                    ActivityCompat.requestPermissions(mContext, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
                                }
                            }).show()
                }
            }
        }
    }
}