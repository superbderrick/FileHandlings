package io.github.superbderrick.kotlinvideoplayer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.github.superbderrick.kotlinvideoplayer.Adapter.Handle.ImageLoader
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R

class MediaAdapter(val context: Context) : BaseAdapter(){

    var mContext: Context = context
    var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    var mImageLoader: ImageLoader = ImageLoader()

    lateinit var mMediaList: ArrayList<MediaData>

    fun initData(aMediaList: ArrayList<MediaData>){
        mMediaList = aMediaList
    }

    override fun getCount(): Int {
        return mMediaList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mediaData: MediaData

        var view = convertView

        view = mInflater.inflate(R.layout.thumbnail, null)
        mediaData = MediaData(mMediaList.get(position).mediaId, mMediaList.get(position).aMediaPath)
        mediaData.imageView = view.findViewById(R.id.thumbnail)
        view.setTag(mediaData)

        mImageLoader.displayImage(mediaData)
        return view
    }
}