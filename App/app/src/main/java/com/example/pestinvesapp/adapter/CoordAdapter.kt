package com.example.pestinvesapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pestinvesapp.activity.MainActivity
import com.example.pestinvesapp.activity.UpdateCoordActivity
import com.example.pestinvesapp.databinding.CoordItemLayoutBinding
import com.example.pestinvesapp.dataclass.Coord
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

                binding.btnDeleteCoord.setOnClickListener {
                    val item = coordList[adapterPosition]
                    val contextView: Context = view.context
                    val alertDialog = AlertDialog.Builder(contextView)
                    val createClient = PestInvesAPI.createClient()
                    with(alertDialog){
                        setTitle("Are you sure?")
                        setPositiveButton("No") { dialog, which -> }
                        setNegativeButton("Yes") { dialog, which ->
                            createClient.deleteCoord(
                                item.idCoord
                            ).enqueue(object : Callback<Coord> {
                                override fun onResponse(call: Call<Coord>, response: Response<Coord>) {
                                    if(response.isSuccessful){
                                        Toast.makeText(contextView, "Delete Coord Successful", Toast.LENGTH_SHORT).show()
                                        val mainPage = Intent(contextView, MainActivity::class.java)
                                        contextView.startActivity(mainPage)
                                    } else {
                                        Log.e("CoordAdapter.kt", "เกิด error ผลลัพธ์ไม่เสร็จสมบูรณ์ไอสัส")
                                    }
                                }

                                override fun onFailure(call: Call<Coord>, t: Throwable) {
                                    Log.e("CoordAdapter.kt", "เกิด error บางอย่างไอ้สัส")
                                }

                            })
                        }
                    }
                    alertDialog.show()
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