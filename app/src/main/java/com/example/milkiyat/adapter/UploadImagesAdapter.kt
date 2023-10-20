package com.example.milkiyat.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.milkiyat.R
import com.squareup.picasso.Picasso

class UploadImagesAdapter(val imageList : ArrayList<Uri>) : RecyclerView.Adapter<UploadImagesAdapter.UploadImagesViewHolder>() {

    class UploadImagesViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val uploadImage : ImageView = itemView.findViewById(R.id.rvUploadImages)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImagesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_upload_images, parent, false)

        return UploadImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: UploadImagesViewHolder, position: Int) {
        val imageUrl = imageList[position]
        Picasso.get().load(imageUrl).into(holder.uploadImage)

        holder.uploadImage.setOnLongClickListener {
            imageList.remove(imageUrl)
            notifyDataSetChanged()
            true
        }


    }
}