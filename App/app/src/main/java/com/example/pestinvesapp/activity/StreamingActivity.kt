package com.example.pestinvesapp.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.pestinvesapp.R
import com.example.pestinvesapp.databinding.ActivityStreamingBinding

class StreamingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStreamingBinding
    var streamingUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStreamingBinding.inflate(layoutInflater)

        val uri: Uri = Uri.parse(streamingUrl)

        binding.videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)

        binding.videoView.setMediaController(mediaController)
        setContentView(binding.root)
    }
}