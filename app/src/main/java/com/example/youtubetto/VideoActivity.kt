package com.example.youtubetto

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubeInitializationResult


class VideoActivity : YouTubeBaseActivity() {

    private lateinit var youtubePlayer: YouTubePlayerView
    private lateinit var btnPlayer: Button

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val authToken = getIntent().getStringExtra("token").toString()
        val videoId = intent.getStringExtra("video")
        val videoTitle = intent.getStringExtra("videoTitle")

        youtubePlayer = findViewById(R.id.youtubePlayer)
        btnPlayer = findViewById(R.id.btnPlay)


        val titleTextView = findViewById<TextView>(R.id.title)
        titleTextView.text = videoTitle


        youtubePlayerInit = object  : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1!!.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                p1.loadVideo(videoId)
                p1.setPlayerStateChangeListener(object:YouTubePlayer.PlayerStateChangeListener{
                    override fun onLoading() {}
                    override fun onLoaded(p0: String?) {}
                    override fun onAdStarted() {}
                    override fun onVideoStarted() {}
                    override fun onVideoEnded() {
                        p1.cueVideo(videoId)
                    }
                    override fun onError(p0: YouTubePlayer.ErrorReason?) {}
                })
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, p1?.toString(), Toast.LENGTH_LONG).show()
            }

        }
        btnPlayer.setOnClickListener {
            youtubePlayer.initialize(authToken, youtubePlayerInit)
        }
    }
}