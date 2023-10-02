package com.example.pestinvesapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pestinvesapp.databinding.ActivityAddMissionBinding
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.fragment.DatePickerFragment
import com.example.pestinvesapp.fragment.TimePickerFragment
import com.example.pestinvesapp.interfaces.PestInvesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddMissionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddMissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddMissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectDate.setOnClickListener {
            val selectedDateFragment = DatePickerFragment()
            selectedDateFragment.show(supportFragmentManager, "Select Date")
        }

        binding.btnSelectTime.setOnClickListener {
            val selectedTimeFragment = TimePickerFragment()
            selectedTimeFragment.show(supportFragmentManager, "Select Time")
        }

        binding.btnPrevoiusPageAddMissionPage.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnSaveMission.setOnClickListener {
            val api: PestInvesAPI = Retrofit.Builder()
                .baseUrl("http://192.168.43.89:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PestInvesAPI::class.java)
            api.addMission(
                binding.edtMissionName.text.toString(),
                "${binding.btnSelectDate.text} ${binding.btnSelectTime.text}"
            ).enqueue(object : Callback<Mission>{
                override fun onResponse(
                    call: Call<Mission>,
                    response: Response<Mission>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Add Mission Successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@AddMissionActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                        finish()
                    }
                }

                override fun onFailure(call: Call<Mission>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    Log.e("Some error", "${binding.btnSelectDate.text} ${binding.btnSelectTime.text}")
                }

            })
        }
    }
}