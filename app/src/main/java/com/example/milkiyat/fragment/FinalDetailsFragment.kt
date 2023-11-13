package com.example.milkiyat.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.milkiyat.MainActivity
import com.example.milkiyat.adapter.UploadImagesAdapter
import com.example.milkiyat.databinding.FragmentFinalDetailsBinding
import com.example.milkiyat.model.ItemDetails
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


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

        val loadingLayout = binding.llLoading
        val loading = binding.lottieLoading
        val success = binding.success
        success.visibility = View.INVISIBLE
        loadingLayout.visibility = View.INVISIBLE
        loading.visibility = View.INVISIBLE

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
            loadingLayout.visibility = View.VISIBLE
            loading.visibility = View.VISIBLE
            binding.btnPost.visibility = View.INVISIBLE

            val itemDetails = ItemDetails(
                images = images,
                category = category!!,
                location = locationText!!,
                title = etTitle!!,
                description = etDescription!!,
                price = etPrice!!
            )
            addItemToFirestore(itemDetails)
        }

        return view
    }

    private fun addItemToFirestore(itemDetails: ItemDetails) {
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("ItemDetails").document()

        uploadImagesToStorage(itemDetails.images ?: emptyList()) { imageUrls ->

            val data = hashMapOf(
                "images" to imageUrls,
                "category" to itemDetails.category,
                "location" to itemDetails.location,
                "title" to itemDetails.title,
                "description" to itemDetails.description,
                "price" to itemDetails.price
            )
            docRef.set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Item added successfully!")
                    binding.success.visibility = View.VISIBLE
                    binding.lottieLoading.visibility = View.INVISIBLE
                    Handler().postDelayed(
                        {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                        }, 3000
                    )

                }
                .addOnFailureListener {
                    binding.llLoading.visibility = View.INVISIBLE
                    binding.lottieLoading.visibility = View.INVISIBLE
                    binding.btnPost.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show()
                }
        }
    }

    /*private fun uploadImagesToStorage(images: List<Uri>, onComplete: (List<String>) -> Unit) {

        val storageRef = FirebaseStorage.getInstance().reference

        val uploadTasks = mutableListOf<UploadTask>()

        val downloadUrls = mutableListOf<String>()

        for ((index, imageUri) in images.withIndex()) {
            val imageName = "image_$index.jpg"
            val imageRef = storageRef.child("images/$imageName")

            val uploadTask = imageRef.putFile(imageUri)

            uploadTasks.add(uploadTask)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result.toString()
                    downloadUrls.add(downloadUrl)

                    // If all images are uploaded and download URLs are obtained, trigger the onComplete callback
                    if (downloadUrls.size == images.size) {
                        onComplete.invoke(downloadUrls)
                    }
                } else {
                    // Handle error
                    Log.e(TAG, "Error uploading image", task.exception)
                }
            }
        }

    }*/

    private fun uploadImagesToStorage(images: List<Uri>, onComplete: (List<String>) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val downloadUrls = mutableListOf<String>()

        val uploadTasks = images.mapIndexed { index, imageUri ->
            val imageName = "image_$index.jpg" // You can customize the image name as needed
            val imageRef = storageRef.child("images/$imageName")

            imageRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }
        }

        // Wait for all tasks to complete
        Tasks.whenAll(uploadTasks)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Extract download URLs from completed tasks
                    for (uploadTask in uploadTasks) {
                        val downloadUrl = uploadTask.result.toString()
                        downloadUrls.add(downloadUrl)
                    }

                    onComplete.invoke(downloadUrls)
                } else {
                    // Handle error
                    Log.e(TAG, "Error uploading images", task.exception)
                }
            }
    }

}