package com.example.milkiyat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.milkiyat.R
import com.example.milkiyat.databinding.ActivityAddLocationBinding
import com.example.milkiyat.databinding.FragmentAddDetailsLandBinding


class AddDetailsLandFragment : Fragment() {

    private lateinit var binding: FragmentAddDetailsLandBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddDetailsLandBinding.inflate(layoutInflater)
        val view = binding.root





        return view
    }


}