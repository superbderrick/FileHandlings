package io.github.superbderrick.kotlinvideoplayer.Data

import android.widget.ImageView

data class MediaData(val aMediaId: Int, val aMediaPath: String){
    var mediaId: Int = aMediaId
    var mediaPath: String = aMediaPath
    var imageView:ImageView? = null
}