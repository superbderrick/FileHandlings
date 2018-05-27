package io.github.superbderrick.kotlinvideoplayer.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import io.github.superbderrick.kotlinvideoplayer.Adapter.MediaAdapter
import io.github.superbderrick.kotlinvideoplayer.Data.GetMediaData
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R
import io.github.superbderrick.kotlinvideoplayer.VideoPlayer.VideoPlayerActivity

class VideoGridFragment : Fragment(), AdapterView.OnItemClickListener {

    private val LOG_TAG : String = "VideoGridFragment"

    private lateinit var mMediaList: ArrayList<MediaData>

    private lateinit var mGridView: GridView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_video, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMediaList = ArrayList<MediaData>()

        GetMediaData().getVideoArrayData(this.context, mMediaList)

        /*
        for(media: MediaData in mMediaList){
            Log.v(LOG_TAG, media.mediaId.toString())
            Log.v(LOG_TAG, media.mediaPath)
        }
        */

        initializeUI(view)

        var mediaAdapter: MediaAdapter = MediaAdapter(this.context)
        mediaAdapter.initData(mMediaList)
        mGridView.adapter = mediaAdapter
        mGridView.setOnItemClickListener(this)
    }

    fun initializeUI(view: View?){
        if(view != null){
            mGridView = view.findViewById(R.id.video_grid_view)
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, aView: View?, aPos: Int, aId: Long) {
        showVideoPlayer(mMediaList.get(aPos).mediaPath)
    }

    fun showVideoPlayer(aVideoPath: String){
        var intent: Intent = Intent(this.context, VideoPlayerActivity::class.java)
        intent.putExtra("VIDEO_PATH", aVideoPath)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): VideoGridFragment = VideoGridFragment()
    }
}
