package io.github.superbderrick.kotlinvideoplayer.Adapter.Handle

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.util.UniversalTimeScale.toLong
import android.provider.MediaStore
import io.github.superbderrick.kotlinvideoplayer.Data.MediaData
import io.github.superbderrick.kotlinvideoplayer.R

object ImageUtils{
    fun getVideoThumbnail(aMediaData: MediaData) : Bitmap{
        var context: Context? = null
        var thumbnailCR: ContentResolver? = null

        aMediaData.imageView?.let {imageView ->  context = imageView.context}
        context?.let{context -> thumbnailCR = context.contentResolver}

        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inSampleSize = 1

        var thumbnailBm : Bitmap = MediaStore.Video.Thumbnails.getThumbnail(thumbnailCR,
                aMediaData.mediaId.toLong(),
                MediaStore.Images.Thumbnails.MINI_KIND,
                options)
        var bitmap: Bitmap = getCenterBitmap(thumbnailBm)

        var videoImg: Drawable? = null
        context?.let { context -> videoImg = context.resources.getDrawable(R.drawable.play, context.theme)}

        var videoBitmap: Bitmap = (videoImg as BitmapDrawable).bitmap

        return overlayBitmap(bitmap, videoBitmap)
    }

    fun overlayBitmap(aBmp1 : Bitmap, aBmp2 : Bitmap) : Bitmap{
        var bmOverlay : Bitmap = Bitmap.createBitmap(aBmp1.width, aBmp1.height, aBmp1.config)
        var canvas : Canvas = Canvas(bmOverlay)
        canvas.drawBitmap(aBmp1, Matrix(), null)
        canvas.drawBitmap(aBmp2,
                ((aBmp1.getWidth() - aBmp2.getWidth())/2).toFloat(),
                ((aBmp1.getHeight() - aBmp2.getHeight())/2).toFloat(),
                null)
        return bmOverlay
    }

    fun getCenterBitmap(src: Bitmap): Bitmap{
        var bitmap: Bitmap

        var width: Int = src.width
        var height: Int = src.height

        var m : Matrix = Matrix()

        if(width >= height){
            bitmap = Bitmap.createBitmap(src,
                    width/2 - height/2,
                    0,
                    height,
                    height,
                    m,
                    true)
        }else{
            bitmap = Bitmap.createBitmap(src,
                    0,
                    height/2 - width/2,
                    width,
                    width,
                    m,
                    true)
        }

        return bitmap
    }
}