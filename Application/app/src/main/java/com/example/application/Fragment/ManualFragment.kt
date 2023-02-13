package com.example.application.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.application.R
import com.example.application.databinding.FragmentManualBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class ManualFragment : Fragment() {

    private lateinit var binding : FragmentManualBinding
    private lateinit var firebase : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManualBinding.inflate(layoutInflater)
        firebase = Firebase.database.reference

        binding.btnFw.setOnClickListener {
            firebase.child("moveCommand").setValue("forward")
        }

        binding.btnTl.setOnClickListener {
            firebase.child("moveCommand").setValue("turnleft")
        }

        binding.btnTr.setOnClickListener {
            firebase.child("moveCommand").setValue("turnright")
        }

        binding.btnBw.setOnClickListener {
            firebase.child("moveCommand").setValue("backward")
        }

        return binding.root

    }

}