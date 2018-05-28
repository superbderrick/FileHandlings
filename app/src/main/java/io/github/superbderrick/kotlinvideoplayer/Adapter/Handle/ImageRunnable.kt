package io.github.superbderrick.kotlinvideoplayer.Adapter.Handle

import android.app.Activity
import android.graphics.Bitmap
import android.widget.ImageView
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData

class ImageRunnable(mediaData: MediaData, imageHashMap: HashMap<ImageView?, String>) : Runnable{
    var mMediaData: MediaData = mediaData
    var mImageHashMap: HashMap<ImageView?, String> = imageHashMap

    override fun run() {
        if(isImageViewValid(mMediaData) == false){
            return
        }

        var bmp: Bitmap? = null
        bmp = ImageUtils.getVideoThumbnail(mMediaData)

        var bd : ImageViewRunnable = ImageViewRunnable(
                bmp,
                mMediaData,
                mImageHashMap);
        var a : Activity? = null
        mMediaData.imageView?.let {
            imageView -> a =  imageView.context as Activity
        }
        a?.runOnUiThread(bd)
    }

    fun isImageViewValid(aMediaData: MediaData) : Boolean {
        var mediaPath: String? = null
        aMediaData.imageView?.let { imageView -> mediaPath = mImageHashMap.get(imageView) }

        if(mediaPath != null && mediaPath.equals(aMediaData.mediaPath) == false){
            return false
        }
        return true
    }
}