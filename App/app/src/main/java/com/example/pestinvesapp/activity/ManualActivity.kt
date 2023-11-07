package com.example.pestinvesapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.pestinvesapp.R
import com.example.pestinvesapp.databinding.ActivityManualBinding
import com.example.pestinvesapp.dataclass.Status
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManualActivity : AppCompatActivity() {

    private lateinit var binding : ActivityManualBinding
    val createClient = PestInvesAPI.createClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManualBinding.inflate(layoutInflater)

        binding.btnForward.setOnClickListener {
            createClient.enableForward()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        if(response.isSuccessful){
                            binding.btnBackward.isVisible = false
                            binding.btnSbackward.isVisible = false
                            binding.btnTurnright.isVisible = false
                            binding.btnSturnright.isVisible = false
                            binding.btnTurnleft.isVisible = false
                            binding.btnSturnleft.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnSforward.setOnClickListener {
            createClient.disableForward()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        if(response.isSuccessful){
                            binding.btnForward.isVisible = true
                            binding.btnSforward.isVisible = true
                            binding.btnBackward.isVisible = true
                            binding.btnSbackward.isVisible = true
                            binding.btnTurnleft.isVisible = true
                            binding.btnSturnleft.isVisible = true
                            binding.btnTurnright.isVisible = true
                            binding.btnSturnright.isVisible = true
                        }
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnBackward.setOnClickListener {
            createClient.enableBackward()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = false
                        binding.btnSforward.isVisible = false
                        binding.btnTurnright.isVisible = false
                        binding.btnSturnright.isVisible = false
                        binding.btnTurnleft.isVisible = false
                        binding.btnSturnleft.isVisible = false
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnSbackward.setOnClickListener {
            createClient.disableBackward()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = true
                        binding.btnSforward.isVisible = true
                        binding.btnBackward.isVisible = true
                        binding.btnSbackward.isVisible = true
                        binding.btnTurnleft.isVisible = true
                        binding.btnSturnleft.isVisible = true
                        binding.btnTurnright.isVisible = true
                        binding.btnSturnright.isVisible = true
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnTurnleft.setOnClickListener {
            createClient.enableTurnleft()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = false
                        binding.btnSforward.isVisible = false
                        binding.btnBackward.isVisible = false
                        binding.btnSbackward.isVisible = false
                        binding.btnTurnright.isVisible = false
                        binding.btnSturnright.isVisible = false
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnSturnleft.setOnClickListener {
            createClient.disableTurnleft()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = true
                        binding.btnSforward.isVisible = true
                        binding.btnBackward.isVisible = true
                        binding.btnSbackward.isVisible = true
                        binding.btnTurnleft.isVisible = true
                        binding.btnSturnleft.isVisible = true
                        binding.btnTurnright.isVisible = true
                        binding.btnSturnright.isVisible = true
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnTurnright.setOnClickListener {
            createClient.enableTurnright()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = false
                        binding.btnSforward.isVisible = false
                        binding.btnBackward.isVisible = false
                        binding.btnSbackward.isVisible = false
                        binding.btnTurnleft.isVisible = false
                        binding.btnSturnleft.isVisible = false
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.btnSturnright.setOnClickListener {
            createClient.disableTurnright()
                .enqueue(object : Callback<Status> {
                    override fun onResponse(call: Call<Status>, response: Response<Status>) {
                        binding.btnForward.isVisible = true
                        binding.btnSforward.isVisible = true
                        binding.btnBackward.isVisible = true
                        binding.btnSbackward.isVisible = true
                        binding.btnTurnleft.isVisible = true
                        binding.btnSturnleft.isVisible = true
                        binding.btnTurnright.isVisible = true
                        binding.btnSturnright.isVisible = true
                    }

                    override fun onFailure(call: Call<Status>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

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
                                val automaticPage = Intent(this@ManualActivity, MainActivity::class.java)
                                startActivity(automaticPage)
                            }
                        }

                        override fun onFailure(call: Call<Status>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                    })
            }

            R.id.stramingMode -> {
                val streamingMode = Intent(this@ManualActivity, StreamingActivity::class.java)
                startActivity(streamingMode)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}