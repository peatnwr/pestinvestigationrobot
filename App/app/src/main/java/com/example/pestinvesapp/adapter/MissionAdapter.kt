package com.example.pestinvesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pestinvesapp.databinding.HeaderMissionItemLayoutBinding
import com.example.pestinvesapp.dataclass.Mission
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MissionAdapter(val missionList: ArrayList<Mission>?, val context: Context):
    RecyclerView.Adapter<MissionAdapter.ViewHolder>() {
    class ViewHolder(view: View, val binding: HeaderMissionItemLayoutBinding):
        RecyclerView.ViewHolder(view) {
            init {
                binding.btnEditMission.setOnClickListener {

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
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date: Date = inputFormat.parse(dateTimeString)

        val datePart: String = outputDateFormat.format(date)
        val timePart: String = outputTimeFormat.format(date)

        return Pair(datePart, timePart)
    }
}