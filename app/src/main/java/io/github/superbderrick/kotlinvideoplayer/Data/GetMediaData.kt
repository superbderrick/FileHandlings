package io.github.superbderrick.kotlinvideoplayer.Data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class GetMediaData(){

    fun getVideoArrayData(aContext : Context, aMediaData : ArrayList<MediaData>){
        var videoCursor: Cursor

        var videoColumns = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA)

        videoCursor = aContext.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoColumns,
                null,
                null,
                null
        )

        if(videoCursor.moveToFirst()){
            var videoId: Int = 0
            var videoPath: String? = null

            var videoColumnIndex: Int = videoCursor.getColumnIndex(MediaStore.Video.Media._ID)
            var videoPathIndex: Int = videoCursor.getColumnIndex(MediaStore.Video.Media.DATA)

            do{
                videoId = videoCursor.getInt(videoColumnIndex)
                videoPath = videoCursor.getString(videoPathIndex)

                var info : MediaData = MediaData(videoId, videoPath)
                aMediaData.add(info)
            }while (videoCursor.moveToNext())
        }
    }
}