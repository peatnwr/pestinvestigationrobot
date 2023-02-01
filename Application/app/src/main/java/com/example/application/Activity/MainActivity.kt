package com.example.application.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.application.Fragment.ManualFragment
import com.example.application.Fragment.MapsFragment
import com.example.application.R
import com.example.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState==null){
            replaceFragment(ManualFragment())
        }

        binding.btNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.manualMenu -> replaceFragment(ManualFragment())
                R.id.gpsMenu -> replaceFragment(MapsFragment())
            }
            true
        }
    }

    private fun replaceFragment(someFragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, someFragment)
        fragmentTransaction.commit()
    }
}