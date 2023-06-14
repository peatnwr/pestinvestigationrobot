package com.example.pestinvesapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pestinvesapp.R
import com.example.pestinvesapp.adapter.MissionAdapter
import com.example.pestinvesapp.databinding.ActivityMainBinding
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
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

    private fun getDateData() {
        missionList.clear()
        val api : PestInvesAPI = Retrofit.Builder()
            .baseUrl("http://192.168.43.89:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PestInvesAPI::class.java)

        api.receiveAllDate()
            .enqueue(object : Callback<List<Mission>> {
                override fun onResponse(call: Call<List<Mission>>, response: Response<List<Mission>>) {
                    response.body()?.forEach {
                        missionList.add(Mission(it.idMission, it.missionName, it.datetime))
                    }
                    binding.rcvMission.adapter = MissionAdapter(missionList, applicationContext)
                }

                override fun onFailure(call: Call<List<Mission>>, t: Throwable) {

                }

            })
    }
}