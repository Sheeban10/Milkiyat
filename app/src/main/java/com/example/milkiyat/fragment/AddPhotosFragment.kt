package com.example.milkiyat.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.milkiyat.R
import com.example.milkiyat.adapter.UploadImagesAdapter
import com.example.milkiyat.databinding.FragmentAddPhotosBinding

class AddPhotosFragment : Fragment() {

    private  lateinit var binding : FragmentAddPhotosBinding
    private lateinit var uploadImagesAdapter : UploadImagesAdapter
    private val selectedImages = ArrayList<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotosBinding.inflate(layoutInflater)
        val view = binding.root

        val rvUploadImages = binding.rvUploadImage

        rvUploadImages.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        uploadImagesAdapter = UploadImagesAdapter(selectedImages)
        rvUploadImages.adapter = uploadImagesAdapter

        binding.tvImageDelete.visibility = View.INVISIBLE

        binding.btnAddImages.setOnClickListener {
            pickImagesFromGallery()
        }

        val locationText = arguments?.getString("location")
        val etTitle = arguments?.getString("title")
        val etDescriptionHouse = arguments?.getString("description")
        val etPrice = arguments?.getString("price")

        Log.d("Details", "$locationText, $etTitle, $etDescriptionHouse, $etPrice")

        binding.btnNext.setOnClickListener {
            val finalDetailsFragment = FinalDetailsFragment()
            val bundle = Bundle()
            bundle.putString("location", locationText.toString())
            bundle.putString("title", etTitle.toString())
            bundle.putString("description", etDescriptionHouse.toString())
            bundle.putString("price", etPrice.toString())

            finalDetailsFragment.arguments = bundle
            val transactionFragment = parentFragmentManager.beginTransaction()

            transactionFragment
                .replace(R.id.frameLayout, finalDetailsFragment)
                .commit()

            transactionFragment.addToBackStack(null)
        }



        return view
    }

    private fun pickImagesFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK_IMAGES && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                binding.tvImageDelete.visibility = View.VISIBLE
                // Multiple images selected
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    if (selectedImages.size < 15) { // Check the maximum limit
                        selectedImages.add(imageUri)
                    } else {
                        // Handle the case when the limit is reached
                        Toast.makeText(requireContext(), "Maximum 15 images allowed", Toast.LENGTH_SHORT).show()
                        break // Exit the loop
                    }
                }
            } else if (data?.data != null) {
                // Single image selected
                val imageUri = data.data
                if (imageUri != null && selectedImages.size < 15) {
                    selectedImages.add(imageUri)
                } else if (selectedImages.size >= 15) {
                    Toast.makeText(requireContext(), "Maximum 15 images allowed", Toast.LENGTH_SHORT).show()
                }
            }

            uploadImagesAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val REQUEST_PICK_IMAGES = 1
    }

}