package com.example.pestinvesapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pestinvesapp.R
import com.example.pestinvesapp.adapter.MissionAdapter
import com.example.pestinvesapp.databinding.ActivityMainBinding
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.dataclass.Status
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val createClient = PestInvesAPI.createClient()
    var missionList = arrayListOf<Mission>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.rcvMission.adapter = MissionAdapter(this.missionList, applicationContext)
        binding.rcvMission.layoutManager = LinearLayoutManager(applicationContext)
        binding.rcvMission.addItemDecoration(
            DividerItemDecoration(binding.rcvMission.getContext(),
                DividerItemDecoration.VERTICAL)
        )

        binding.btnAddMission.setOnClickListener {
            var addMissionPage = Intent(this@MainActivity, AddMissionActivity::class.java)
            startActivity(addMissionPage)
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        getDateData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val automaticMenu = menu?.findItem(R.id.automaticMode)
        automaticMenu?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId) {
            R.id.manualMode -> {
                createClient.changeToManual()
                    .enqueue(object : Callback<Status> {
                        override fun onResponse(call: Call<Status>, response: Response<Status>) {
                            if(response.isSuccessful){
                                val manualPage = Intent(this@MainActivity, ManualActivity::class.java)
                                startActivity(manualPage)
                            }
                        }

                        override fun onFailure(call: Call<Status>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }

            R.id.stramingMode -> {
                val streamingMode = Intent(this@MainActivity, StreamingActivity::class.java)
                startActivity(streamingMode)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getDateData() {
        missionList.clear()
        createClient.receiveAllDate()
            .enqueue(object : Callback<List<Mission>> {
                override fun onResponse(call: Call<List<Mission>>, response: Response<List<Mission>>) {
                    response.body()?.forEach {
                        missionList.add(Mission(it.idMission, it.missionName, it.datetime))
                    }
                    binding.rcvMission.adapter = MissionAdapter(missionList, applicationContext)
                }

                override fun onFailure(call: Call<List<Mission>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("Error ActivityMain.kt", t.message.toString())
                }

            })
    }
}