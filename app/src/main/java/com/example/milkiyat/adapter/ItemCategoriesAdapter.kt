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

class ItemCategoriesAdapter(val itemList : ArrayList<ItemList>) :RecyclerView.Adapter<ItemCategoriesAdapter.ItemsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_list, parent, false)

        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val itemList = itemList[position]

//        Picasso.get().load(itemList.images[0]).error(R.drawable.house).into(holder.itemImage)
        holder.itemTitle.text = itemList.title
        holder.itemPrice.text = itemList.price
        holder.itemLocation.text = itemList.location
    }


    class ItemsViewHolder(view : View): RecyclerView.ViewHolder(view){
        val itemImage : ImageView = view.findViewById(R.id.ivItemImage)
        val itemTitle : TextView = view.findViewById(R.id.tvItemTitle)
        val itemPrice : TextView = view.findViewById(R.id.tvitemPrice)
        val itemLocation : TextView = view.findViewById(R.id.tvItemLocation)
    }
}

