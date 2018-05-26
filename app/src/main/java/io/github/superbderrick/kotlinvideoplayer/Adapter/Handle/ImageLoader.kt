package io.github.superbderrick.kotlinvideoplayer.Adapter.Handle

import android.widget.ImageView
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ImageLoader{
    val D_RES_ID: Int = R.drawable.default_video_image
    lateinit var mExecutor: ExecutorService
    lateinit var mImageViewMap: HashMap<ImageView?, String>

    constructor(){
        mImageViewMap = HashMap()
        mExecutor = Executors.newFixedThreadPool(10)
    }

    fun displayImage(mediaData: MediaData){
        mImageViewMap.put(mediaData.imageView, mediaData.mediaPath)
        mExecutor.submit(ImageRunnable(mediaData, mImageViewMap))

        mediaData.imageView?.let{imageView -> imageView.setImageResource(D_RES_ID) }
    }
}