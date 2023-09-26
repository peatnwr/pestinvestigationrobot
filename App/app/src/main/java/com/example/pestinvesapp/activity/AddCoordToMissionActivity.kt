package com.example.pestinvesapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pestinvesapp.R
import com.example.pestinvesapp.adapter.CoordAdapter
import com.example.pestinvesapp.databinding.ActivityAddCoordToMissionBinding
import com.example.pestinvesapp.dataclass.Coord
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class AddCoordToMissionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddCoordToMissionBinding
    var coordList = arrayListOf<Coord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCoordToMissionBinding.inflate(layoutInflater)

        binding.rcvCoord.adapter = CoordAdapter(this.coordList, this@AddCoordToMissionActivity)
        binding.rcvCoord.layoutManager = LinearLayoutManager(applicationContext)
        binding.rcvCoord.addItemDecoration(
            DividerItemDecoration(binding.rcvCoord.getContext(),
                DividerItemDecoration.VERTICAL)
        )

        binding.btnPreviousPageAddCoordToMissionPage.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnAddCoordToMission.setOnClickListener {
            val data = intent.extras
            val idMission = data?.get("missionId")
            val mapPage = Intent(this@AddCoordToMissionActivity, MapsActivity::class.java)
            mapPage.putExtra("missionId", idMission.toString())
            startActivity(mapPage)
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        getMissionData()
        getCoordData()
    }

    private fun getCoordData() {
        coordList.clear()
        val data = intent.extras
        val idMission = data?.get("missionId")
        val api: PestInvesAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PestInvesAPI::class.java)
        api.receiveAllCoord(
            idMission.toString()
        ).enqueue(object : Callback<List<Coord>> {
            override fun onResponse(call: Call<List<Coord>>, response: Response<List<Coord>>) {
                response.body()?.forEach {
                    coordList.add(Coord(it.idCoord, it.lat, it.longi, it.idMissionAtCoordTable))
                }
                binding.rcvCoord.adapter = CoordAdapter(coordList, applicationContext)
            }

            override fun onFailure(call: Call<List<Coord>>, t: Throwable) {}

        })
    }

    private fun getMissionData() {
        val data = intent.extras
        val idMission = data?.get("missionId")
        Log.d("missionId", idMission.toString())
        val api: PestInvesAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PestInvesAPI::class.java)
        api.getMissionInfo(
            idMission.toString()
        ).enqueue(object : Callback<List<Mission>> {
            override fun onResponse(call: Call<List<Mission>>, response: Response<List<Mission>>) {
                response.body()?.forEach {
                    binding.txtMissionId.text = "ID: " + it.idMission.toString()
                    binding.txtMissionNameAddCoordPage.text = "Mission Name: " + it.missionName

                    val (date, time) = reFormatDateTime(it.datetime)

                    binding.txtDateAddCoordPage.text = "Date: " + date
                    binding.txtTimeAddCoordPage.text = "Time: " + time
                }
            }

            override fun onFailure(call: Call<List<Mission>>, t: Throwable) {
                Log.e("Error", "Something went wrong at AddCoordToMissionActivity.kt")
            }

        })
    }

    private fun reFormatDateTime(dateTimeString: String): Pair<String, String> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        outputTimeFormat.timeZone = TimeZone.getTimeZone("Asia/Bangkok")

        val date: Date = inputFormat.parse(dateTimeString)

        val datePart: String = outputDateFormat.format(date)
        val timePart: String = outputTimeFormat.format(date)

        return Pair(datePart, timePart)
    }
}