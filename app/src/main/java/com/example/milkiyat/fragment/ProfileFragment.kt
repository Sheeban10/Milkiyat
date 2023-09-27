package com.example.milkiyat.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.milkiyat.LoginActivity
import com.example.milkiyat.R
import com.example.milkiyat.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var photoData : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()

        val nameData = arguments?.getString("Name")
        Log.d("ProfileFragment", "Name received: $nameData")
        binding.userName.text = nameData

        photoData = binding.profileImage
        val photoUrl = arguments?.getString("Photo")
        Log.d("ProfileFragment", "Pic received: $photoUrl")
        Picasso.get().load(photoUrl).into(photoData)



        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity as Context,LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }


}