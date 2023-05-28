package com.example.pestinvesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pestinvesapp.activity.UpdateCoordActivity
import com.example.pestinvesapp.databinding.CoordItemLayoutBinding
import com.example.pestinvesapp.dataclass.Coord

class CoordAdapter(val coordList: ArrayList<Coord>, val context: Context):
    RecyclerView.Adapter<CoordAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: CoordItemLayoutBinding):
        RecyclerView.ViewHolder(view) {
            init {
                binding.btnEditCoord.setOnClickListener {
                    val item = coordList[adapterPosition]
                    val contextView: Context = view.context
                    val editMapsActivity = Intent(contextView, UpdateCoordActivity::class.java)
                    editMapsActivity.putExtra("coordId", item.idCoord)
                    editMapsActivity.putExtra("latData", item.lat)
                    editMapsActivity.putExtra("longiData", item.longi)
                    contextView.startActivity(editMapsActivity)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CoordItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.txtLat.text = "Lat: " + coordList!![position].lat.toString()
        binding.txtLongi.text = "Longi: " + coordList!![position].longi.toString()
    }

    override fun getItemCount(): Int {
        return coordList!!.size
    }
}