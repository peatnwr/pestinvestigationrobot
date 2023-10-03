package com.example.pestinvesapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pestinvesapp.activity.AddCoordToMissionActivity
import com.example.pestinvesapp.activity.MainActivity
import com.example.pestinvesapp.databinding.HeaderMissionItemLayoutBinding
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MissionAdapter(val missionList: ArrayList<Mission>, val context: Context):
    RecyclerView.Adapter<MissionAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: HeaderMissionItemLayoutBinding):
        RecyclerView.ViewHolder(view) {
            init {
                binding.btnEditMission.setOnClickListener {
                    val item = missionList[adapterPosition]
                    val contextView: Context = view.context
                    val addCoordToMissionPage = Intent(contextView, AddCoordToMissionActivity::class.java)
                    addCoordToMissionPage.putExtra("missionId", item.idMission.toString())
                    contextView.startActivity(addCoordToMissionPage)
                }

                binding.btnDeleteMission.setOnClickListener {
                    val item = missionList[adapterPosition]
                    val contextView: Context = view.context
                    val api : PestInvesAPI = Retrofit.Builder()
                        .baseUrl("http://192.168.43.89:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PestInvesAPI::class.java)
                    api.deleteMission(
                        item.idMission.toString().toInt()
                    ).enqueue(object : Callback<Mission> {
                        override fun onResponse(call: Call<Mission>, response: Response<Mission>) {
                            if (response.isSuccessful){
                                val mainActivity = Intent(contextView, MainActivity::class.java)
                                contextView.startActivity(mainActivity)
                            }
                        }

                        override fun onFailure(call: Call<Mission>, t: Throwable) {
                            Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("MissionAdapter.kt Error Alert :", t.message.toString())
                        }

                    })
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HeaderMissionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        val (date, time) = reFormatDateTime(missionList!![position].datetime)

        binding.txtMissionName.text = missionList!![position].missionName
        binding.txtDate.text = "Date: " + date
        binding.txtTime.text = "Time: " + time
    }

    override fun getItemCount(): Int {
        return missionList!!.size
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