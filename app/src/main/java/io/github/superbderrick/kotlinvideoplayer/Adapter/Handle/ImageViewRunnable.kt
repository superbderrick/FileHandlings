package io.github.superbderrick.kotlinvideoplayer.Adapter.Handle

import android.graphics.Bitmap
import android.widget.ImageView
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R

class ImageViewRunnable(aBitmap: Bitmap, aMediaData: MediaData, aImageViewMap: HashMap<ImageView?, String>) : Runnable{
    val D_RES_ID: Int = R.mipmap.ic_launcher;

    var mBitmap: Bitmap = aBitmap
    var mMediaData: MediaData = aMediaData
    var mImageViewMap: HashMap<ImageView?, String> = aImageViewMap

    override fun run() {
        if(isImageViewValid() == false)
            return
        if(mBitmap != null){
            mMediaData.imageView?.let { imageView -> imageView.setImageBitmap(mBitmap) }
        }else{
            mMediaData.imageView?.let { imageView -> imageView.setImageResource(D_RES_ID) }
        }
    }

    fun isImageViewValid() : Boolean{
        var mediaPath: String? = null
        mMediaData.imageView?.let { imageView -> mediaPath = mImageViewMap.get(imageView) }
        if(mediaPath == null || mediaPath.equals(mMediaData.mediaPath) == false){
            return false
        }
        return true
    }
}