package com.example.youtubetto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.youtubetto.adapter.CustomListViewAdapter
import com.example.youtubetto.dto.IYoutubeObject
import com.example.youtubetto.dto.SubscribedChannel
import com.google.android.gms.tasks.Task

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.streams.toList


val format = Json { ignoreUnknownKeys = true }

class SubscriptionsActivity : AppCompatActivity() {
    private companion object{
        var LOG_TAG = "SubscriptionsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscriptions)
        Log.d(LOG_TAG,getIntent().getStringExtra("token").toString())
        val authToken = getIntent().getStringExtra("token").toString()


        val listView : ListView = findViewById(R.id.subscriptions_videos_list)
        val textView = findViewById<TextView>(R.id.text)

        val queue = Volley.newRequestQueue(this)
        val subscribed_channels_url = "https://www.googleapis.com/youtube/v3/subscriptions?mine=true&part=snippet&maxResults=50"

        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, subscribed_channels_url, null,
            { response ->
                val items : JSONArray = response.getJSONArray("items")
                val subscribedList : ArrayList<IYoutubeObject> = ArrayList()

                for (i in 0 until items.length()) {
                    subscribedList.add(SubscribedChannel(items.getJSONObject(i)))
                }

                listView.adapter = CustomListViewAdapter(this, subscribedList)

                listView.setOnItemClickListener { parent, view, position, id ->
                    val intent  = Intent(this, ChannelActivity::class.java).apply {
                        putExtra("channel", (parent.adapter.getItem(position) as SubscribedChannel).id)
                        putExtra("channelTitle", (parent.adapter.getItem(position) as SubscribedChannel).title)
                        putExtra("token", authToken)
                    }
                    startActivity(intent)
                }

                },
            { textView.text = "That didn't work!" }) {
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