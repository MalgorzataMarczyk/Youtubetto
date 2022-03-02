package com.example.youtubetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.youtubetto.adapter.CustomListViewAdapter
import com.example.youtubetto.dto.ChannelVideo
import com.example.youtubetto.dto.IYoutubeObject
import com.example.youtubetto.dto.SubscribedChannel
import org.json.JSONArray

class ChannelActivity : AppCompatActivity() {

    private companion object{
        var LOG_TAG = "ChannelActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        val authToken = getIntent().getStringExtra("token").toString()
        val channelId = getIntent().getStringExtra("channel").toString()
        val channelTitle = getIntent().getStringExtra("channelTitle").toString()

        val listView : ListView = findViewById(R.id.channel_videos_list)
        findViewById<TextView>(R.id.channel_name).setText(channelTitle)

        val queue = Volley.newRequestQueue(this)
        val subscribed_channels_url = "https://youtube.googleapis.com/youtube/v3/search?channelId=${channelId}&part=snippet,id&order=date"

        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, subscribed_channels_url, null,
            { response ->
                val items : JSONArray = response.getJSONArray("items")
                val videoList : ArrayList<IYoutubeObject> = ArrayList()

                for (i in 0 until items.length()){
                    videoList.add(ChannelVideo(items.getJSONObject(i)))
                }

                listView.adapter = CustomListViewAdapter(this, videoList)
                listView.setOnItemClickListener { parent, view, position, id ->
                    val intent  = Intent(this, VideoActivity::class.java).apply {
                        putExtra("video", (parent.adapter.getItem(position) as ChannelVideo).id)
                        putExtra("videoTitle", (parent.adapter.getItem(position) as ChannelVideo).title)
                        putExtra("videoThumbnailUrl", (parent.adapter.getItem(position) as ChannelVideo).photoUrl)
                        putExtra("videoPublishTime", (parent.adapter.getItem(position) as ChannelVideo).publishTime)
                        putExtra("token", authToken)
                    }
                    startActivity(intent)
                }
            },
            {}) {
            override fun getHeaders(): MutableMap<String, String> {
                val accessToken = authToken
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer " + accessToken
                Log.d("", headers.toString())
                return headers
            }
        }
        queue.add(jsonObjectRequest)

    }

}