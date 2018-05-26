package io.github.superbderrick.kotlinvideoplayer.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import io.github.superbderrick.kotlinvideoplayer.Data.GetMediaData
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R

class VideoGridFragment : Fragment(), AdapterView.OnItemClickListener {

    private val LOG_TAG : String = "VideoGridFragment"

    private lateinit var mMediaList: ArrayList<MediaData>

    private lateinit var mGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_video, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI(view)

        mMediaList = ArrayList()

        GetMediaData().getVideoArrayData(activity, mMediaList)


    }

    fun initializeUI(view: View?){
        if(view != null){
            mGridView = view.findViewById(R.id.video_grid_view)
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    companion object {

    }
}
