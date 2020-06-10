package com.example.mobile_lab4

import android.content.ContentUris
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_resource_list.*


class ResourceListActivity : AppCompatActivity() {

    lateinit var mediaType: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_list)

        mediaType = intent.getStringExtra(Constants.MEDIA_TYPE_EXTRA)

        if (mediaType == Constants.MUSIC_TYPE) {
            val list = getMusicResourceList()
            recycler_view.layoutManager = LinearLayoutManager(this)
            val listAdapter = ResourceRecyclerAdapter(list) {
                val intent = Intent(this, MusicActivity::class.java)
                intent.putExtra(Constants.MEDIA_PATH_EXTRA, it.uri)
                startActivity(intent)
            }
            recycler_view.adapter = listAdapter
        } else {
            val list = getVideoResourceList()
            recycler_view.layoutManager = LinearLayoutManager(this)
            val listAdapter = ResourceRecyclerAdapter(list) {
                val intent = Intent(this, VideoActivity::class.java)
                intent.putExtra(Constants.MEDIA_PATH_EXTRA, it.uri)
                startActivity(intent)
            }
            recycler_view.adapter = listAdapter
        }


    }

    fun getMusicResourceList(): MutableList<MediaResource> {
        val list = mutableListOf<MediaResource>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val c = contentResolver.query(uri, null, null, null, null)
        if (c != null) {
            val idIndex = c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val titleIndex = c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val durationIndex = c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            while (c.moveToNext()) {
                val id = c.getLong(idIndex)
                val title = c.getString(titleIndex)
                val duration = c.getString(durationIndex)
                var durationFormatted = ""
                if (duration != null) {
                    var min = (duration.toInt() / 1000 / 60).toString()
                    var seconds = (duration.toInt() / 1000 % 60).toString()
                    if (seconds.toInt() < 10)
                        seconds = "0$seconds"
                    durationFormatted = "$min:$seconds"
                }

                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                list.add(MediaResource(uri,title,durationFormatted))
            }
        }

        c?.close()
        return list
    }

    fun getVideoResourceList(): MutableList<MediaResource> {
        val list = mutableListOf<MediaResource>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val c = contentResolver.query(uri, null, null, null, null)
        if (c != null) {
            val idIndex = c.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
            val titleIndex = c.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.TITLE)
            val durationIndex = c.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)
            while (c.moveToNext()) {
                val id = c.getLong(idIndex)
                val title = c.getString(titleIndex)
                val duration = c.getString(durationIndex)
                var durationFormatted = ""
                if (duration != null) {
                    var min = (duration.toInt() / 1000 / 60).toString()
                    var seconds = (duration.toInt() / 1000 % 60).toString()
                    if (seconds.toInt() < 10)
                        seconds = "0$seconds"
                    durationFormatted = "$min:$seconds"
                }

                val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                list.add(MediaResource(uri,title,durationFormatted))
            }
        }

        c?.close()
        return list
    }
}
