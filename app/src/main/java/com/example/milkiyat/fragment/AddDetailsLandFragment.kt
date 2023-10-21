package com.example.milkiyat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val locationData = arguments?.getString("locationData").toString()
        binding.tvLocationText.text = locationData

        binding.btnNext.setOnClickListener {

            if (binding.etTitle.text!!.isNotEmpty() && binding.etDescriptionLand.text!!.isNotEmpty() && binding.etPrice.text!!.isNotEmpty()) {
                uploadImages()
            } else {
                Toast.makeText(requireContext(), "Please fill all details", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun uploadImages() {

        val category = arguments?.getString("category")
        val imagesFragment = AddPhotosFragment()
        val bundle = Bundle()
        bundle.putString("category", category)
        bundle.putString("location", binding.tvLocationText.text.toString())
        bundle.putString("title", binding.etTitle.text.toString())
        bundle.putString("description", binding.etDescriptionLand.text.toString())
        bundle.putString("price", binding.etPrice.text.toString())
        imagesFragment.arguments = bundle

        val transactionFragment = parentFragmentManager.beginTransaction()

        transactionFragment
            .replace(R.id.frameLayout, imagesFragment)
            .commit()

        transactionFragment.addToBackStack(null)
    }

}