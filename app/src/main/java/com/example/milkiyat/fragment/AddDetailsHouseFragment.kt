package com.example.milkiyat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.milkiyat.R
import com.example.milkiyat.databinding.FragmentAddDetailsHouseBinding

class AddDetailsHouseFragment : Fragment() {

    private lateinit var binding: FragmentAddDetailsHouseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentAddDetailsHouseBinding.inflate(layoutInflater)
        val view = binding.root


        return view
    }


}