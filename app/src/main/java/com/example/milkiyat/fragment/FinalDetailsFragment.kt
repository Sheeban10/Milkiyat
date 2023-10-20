package com.example.milkiyat.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.milkiyat.R
import com.example.milkiyat.databinding.FragmentFinalDetailsBinding


class FinalDetailsFragment : Fragment() {

    private lateinit var binding : FragmentFinalDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinalDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        val locationText = arguments?.getString("location")
        val etTitle = arguments?.getString("title")
        val etDescriptionHouse = arguments?.getString("description")
        val etPrice = arguments?.getString("price")

        Log.d("Details", "$locationText, $etTitle, $etDescriptionHouse, $etPrice")

        binding.tvLocationText.setText(locationText)

        return view
    }


}