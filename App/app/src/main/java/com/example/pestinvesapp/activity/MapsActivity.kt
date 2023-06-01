package com.example.pestinvesapp.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pestinvesapp.databinding.ActivityMapsBinding
import com.example.pestinvesapp.dataclass.Coord
import com.example.pestinvesapp.interfaces.PestInvesAPI
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMapsBinding
    private var currentPoint: GeoPoint? = null
    val markerList = mutableListOf<Marker>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        binding.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        binding.mapView.setBuiltInZoomControls(true)
        binding.mapView.controller.setZoom(15.0)
        binding.mapView.controller.setCenter(GeoPoint(16.432502210397754, 102.8240378218489))

        binding.btnZoomIn.setOnClickListener { binding.mapView.controller.zoomIn() }

        binding.btnZoomOut.setOnClickListener { binding.mapView.controller.zoomOut() }

        binding.btnSaveCoord.setOnClickListener {
            val data = intent.extras
            val idMission = data?.get("missionId")
            if (currentPoint != null) {
                if (hasLocationPermission()) {
                    val api: PestInvesAPI = Retrofit.Builder()
                        .baseUrl("http://192.168.1.30:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PestInvesAPI::class.java)
                    api.addCoordToMission(
                        idMission.toString(),
                        currentPoint?.latitude.toString().toDouble(),
                        currentPoint?.longitude.toString().toDouble()
                    ).enqueue(object : Callback<Coord> {
                        override fun onResponse(call: Call<Coord>, response: Response<Coord>) {
                            if(response.isSuccessful){
                                finish()
                            } else {
                                Log.e("MapsActivity.kt Alert: ", "เกิด error ผลลัพธ์ไม่เสร็จสมบูรณ์ไอสัส")
                            }
                        }

                        override fun onFailure(call: Call<Coord>, t: Throwable) {
                            Log.e("MapsActivity.kt Alert: ", "เกิด error อะไรสักอย่างไอ้สัส")
                        }

                    })
                } else {
                    requirePermission()
                }
            } else {
                Toast.makeText(this@MapsActivity, "You didn't select point", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnBackPrevoiusPageMapsActivity.setOnClickListener {
            super.onBackPressed()
        }

        binding.mapView.setOnTouchListener { v, event ->
            cleanMarkers()
            when(event.action) {
                MotionEvent.ACTION_UP -> {
                    val marker = Marker(binding.mapView)
                    val latitude = binding.mapView.projection.fromPixels(event.x.toInt(), event.y.toInt()).latitude
                    val longitude = binding.mapView.projection.fromPixels(event.x.toInt(), event.y.toInt()).longitude

                    currentPoint = GeoPoint(latitude, longitude)
                    marker.position = GeoPoint(latitude, longitude)
                    markerList.add(marker)
                    binding.mapView.overlays.add(marker)
                    binding.mapView.invalidate()

                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requirePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val data = intent.extras
            val idMission = data?.get("missionId")
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val api: PestInvesAPI = Retrofit.Builder()
                    .baseUrl("http://192.168.1.30:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PestInvesAPI::class.java)
                api.addCoordToMission(
                    idMission.toString(),
                    currentPoint?.latitude.toString().toDouble(),
                    currentPoint?.longitude.toString().toDouble()
                ).enqueue(object : Callback<Coord> {
                    override fun onResponse(call: Call<Coord>, response: Response<Coord>) {
                        if(response.isSuccessful){
                            finish()
                        } else {
                            Log.e("MapsActivity.kt Alert: ", "เกิด error ผลลัพธ์ไม่เสร็จสมบูรณ์ไอสัส")
                        }
                    }

                    override fun onFailure(call: Call<Coord>, t: Throwable) {
                        Log.e("MapsActivity.kt Alert: ", "เกิด error อะไรสักอย่างไอ้สัส")
                    }

                })
            } else {
                Toast.makeText(this@MapsActivity, "Location access is not allowed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanMarkers() {
        for (marker in markerList) {
            binding.mapView.overlays.remove(marker)
        }
        markerList.clear()
        binding.mapView.invalidate()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}