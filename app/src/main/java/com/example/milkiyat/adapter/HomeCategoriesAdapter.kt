package com.example.milkiyat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milkiyat.R
import com.example.milkiyat.model.Categories
import com.squareup.picasso.Picasso

class HomeCategoriesAdapter(val itemList : ArrayList<Categories>, val onCategoryClickListener: (Categories) -> Unit) : RecyclerView.Adapter<HomeCategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(view : View) :RecyclerView.ViewHolder(view){
        val categoriesImage : ImageView = view.findViewById(R.id.imgCategories)
        val categoriesName : TextView = view.findViewById(R.id.tvCategoriesName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_categories_list, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categories = itemList[position]

        Picasso.get().load(categories.categoryImage).into(holder.categoriesImage)
        holder.categoriesName.text = categories.categoryName

        holder.itemView.setOnClickListener {
            onCategoryClickListener.invoke(categories)
        }
    }
}