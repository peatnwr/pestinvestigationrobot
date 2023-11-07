package com.example.pestinvesapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.Toast
import com.example.pestinvesapp.R
import com.example.pestinvesapp.databinding.ActivityStreamingBinding
import com.example.pestinvesapp.dataclass.Status
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StreamingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStreamingBinding
    val createClient = PestInvesAPI.createClient()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val manualMenu = menu?.findItem(R.id.manualMode)
        manualMenu?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId) {
            R.id.automaticMode -> {
                createClient.changeToAutomatic()
                    .enqueue(object : Callback<Status> {
                        override fun onResponse(call: Call<Status>, response: Response<Status>) {
                            if(response.isSuccessful) {
                                val automaticPage = Intent(this@StreamingActivity, MainActivity::class.java)
                                startActivity(automaticPage)
                            }
                        }

                        override fun onFailure(call: Call<Status>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                    })
            }

            R.id.manualMode -> {
                createClient.changeToManual()
                    .enqueue(object : Callback<Status> {
                        override fun onResponse(call: Call<Status>, response: Response<Status>) {
                            if(response.isSuccessful){
                                val manualPage = Intent(this@StreamingActivity, ManualActivity::class.java)
                                startActivity(manualPage)
                            }
                        }

                        override fun onFailure(call: Call<Status>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        return super.onOptionsItemSelected(item)
    }
}