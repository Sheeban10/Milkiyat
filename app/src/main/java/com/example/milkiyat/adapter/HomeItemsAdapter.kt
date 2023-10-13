package com.example.milkiyat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milkiyat.R
import com.example.milkiyat.model.ItemList
import com.squareup.picasso.Picasso

class HomeItemsAdapter(val itemList : ArrayList<ItemList>) :RecyclerView.Adapter<HomeItemsAdapter.ItemsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val itemList = itemList[position]

        Picasso.get().load(itemList.itemListImage).into(holder.itemImage)
        holder.itemAbout.text = itemList.itemListAbout
        holder.itemPrice.text = itemList.itemListPrice
        holder.itemLocation.text = itemList.itemListLocation
    }


    class ItemsViewHolder(view : View): RecyclerView.ViewHolder(view){
        val itemImage : ImageView = view.findViewById(R.id.ivItemImage)
        val itemAbout : TextView = view.findViewById(R.id.tvItemAbout)
        val itemPrice : TextView = view.findViewById(R.id.tvitemPrice)
        val itemLocation : TextView = view.findViewById(R.id.tvItemLocation)
    }
}