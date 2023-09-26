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
import com.example.pestinvesapp.R
import com.example.pestinvesapp.databinding.ActivityUpdateCoordBinding
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

class UpdateCoordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCoordBinding
    private var currentPoint: GeoPoint? = null
    val markerList = mutableListOf<Marker>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.extras
        val lat = data?.get("latData")
        val longi = data?.get("longiData")
        currentPoint = GeoPoint(lat.toString().toDouble(), longi.toString().toDouble())

        binding = ActivityUpdateCoordBinding.inflate(layoutInflater)
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        binding.mapViewUpdateCoordActivity.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        binding.mapViewUpdateCoordActivity.setBuiltInZoomControls(true)
        binding.mapViewUpdateCoordActivity.controller.setZoom(15.0)
        binding.mapViewUpdateCoordActivity.controller.setCenter(GeoPoint(currentPoint?.latitude.toString().toDouble(), currentPoint?.longitude.toString().toDouble()))
        val marker = Marker(binding.mapViewUpdateCoordActivity)
        marker.position = GeoPoint(currentPoint?.latitude.toString().toDouble(), currentPoint?.longitude.toString().toDouble())
        markerList.add(marker)
        binding.mapViewUpdateCoordActivity.overlays.add(marker)
        binding.mapViewUpdateCoordActivity.invalidate()

        binding.btnZoomInUpdateCoordActivity.setOnClickListener { binding.mapViewUpdateCoordActivity.controller.zoomIn() }
        binding.btnZoomOutUpdateCoordActivity.setOnClickListener { binding.mapViewUpdateCoordActivity.controller.zoomOut() }

        binding.btnBackPrevoiusPageUpdateCoordActivity.setOnClickListener { super.onBackPressed() }

        binding.btnSaveCoordUpdateCoordActivity.setOnClickListener {
            val data = intent.extras
            val coordId = data?.get("coordId")
            if(currentPoint != null) {
                if(hasLocationPermission()) {
                    val api: PestInvesAPI = Retrofit.Builder()
                        .baseUrl("http://192.168.43.89:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PestInvesAPI::class.java)
                    api.updateCoord(
                        coordId.toString().toInt(),
                        currentPoint?.latitude.toString().toDouble(),
                        currentPoint?.longitude.toString().toDouble()
                    ).enqueue(object : Callback<Coord> {
                        override fun onResponse(call: Call<Coord>, response: Response<Coord>) {
                            if(response.isSuccessful){
                                finish()
                            } else {
                                Log.e("UpdateCoordActivity.kt: ", "เกิด error ผลลัพธ์ไม่เสร็จสมบูรณ์ไอสัส")
                            }
                        }

                        override fun onFailure(call: Call<Coord>, t: Throwable) {
                            Log.e("UpdateCoordActivity.kt: ", "บางอย่างเกิดข้อผิดพลาดไอ้สัส")
                        }

                    })
                } else {
                    requirePermission()
                }
            } else {
                Toast.makeText(this@UpdateCoordActivity, "You didn't select point", Toast.LENGTH_LONG).show()
            }
        }

        binding.mapViewUpdateCoordActivity.setOnTouchListener { v, event ->
            cleanMarkers()
            when(event.action) {
                MotionEvent.ACTION_UP -> {
                    val marker = Marker(binding.mapViewUpdateCoordActivity)
                    val latitude = binding.mapViewUpdateCoordActivity.projection.fromPixels(event.x.toInt(), event.y.toInt()).latitude
                    val longitude = binding.mapViewUpdateCoordActivity.projection.fromPixels(event.x.toInt(), event.y.toInt()).longitude

                    currentPoint = GeoPoint(latitude, longitude)
                    marker.position = GeoPoint(latitude, longitude)
                    markerList.add(marker)
                    binding.mapViewUpdateCoordActivity.overlays.add(marker)
                    binding.mapViewUpdateCoordActivity.invalidate()

                    true
                }
             else -> false
            }
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.mapViewUpdateCoordActivity.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapViewUpdateCoordActivity.onPause()
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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val data = intent.extras
                val coordId = data?.get("coordId")
                val api: PestInvesAPI = Retrofit.Builder()
                    .baseUrl("http://192.168.43.89:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PestInvesAPI::class.java)
                api.updateCoord(
                    coordId.toString().toInt(),
                    currentPoint?.latitude.toString().toDouble(),
                    currentPoint?.longitude.toString().toDouble()
                ).enqueue(object : Callback<Coord> {
                    override fun onResponse(call: Call<Coord>, response: Response<Coord>) {
                        if(response.isSuccessful){
                            finish()
                        } else {
                            Log.e("UpdateCoordActivity.kt: ", "เกิด error ผลลัพธ์ไม่เสร็จสมบูรณ์ไอสัส")
                        }
                    }

                    override fun onFailure(call: Call<Coord>, t: Throwable) {
                        Log.e("UpdateCoordActivity.kt: ", "บางอย่างเกิดข้อผิดพลาดไอ้สัส")
                    }

                })
            } else {
                Toast.makeText(this@UpdateCoordActivity, "Location access is not allowed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanMarkers() {
        for (marker in markerList) {
            binding.mapViewUpdateCoordActivity.overlays.remove(marker)
        }
        markerList.clear()
        binding.mapViewUpdateCoordActivity.invalidate()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}