package com.example.milkiyat.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.milkiyat.R
import com.example.milkiyat.adapter.UploadImagesAdapter
import com.example.milkiyat.databinding.FragmentFinalDetailsBinding


class FinalDetailsFragment : Fragment() {

    private lateinit var binding : FragmentFinalDetailsBinding
    private lateinit var uploadImagesAdapter : UploadImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinalDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        val rvImagesUploaded = binding.rvUploadImage

        val locationText = arguments?.getString("location")
        val etTitle = arguments?.getString("title")
        val etDescription = arguments?.getString("description")
        val etPrice = arguments?.getString("price")
        val images  = arguments?.getParcelableArrayList<Uri>("images")
        val category = arguments?.getString("category")

        Log.d("Details", "$category, $locationText, $etTitle, $etDescription, $etPrice, $images")

        binding.tvLocationText.setText(locationText)
        binding.tvTitle.setText(etTitle)
        binding.tvDescription.setText(etDescription)
        binding.tvprice.setText(etPrice)

        rvImagesUploaded.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        uploadImagesAdapter = images?.let { UploadImagesAdapter(it) }!!
        rvImagesUploaded.adapter = uploadImagesAdapter

        binding.btnPost.setOnClickListener {

        }

        return view
    }


}