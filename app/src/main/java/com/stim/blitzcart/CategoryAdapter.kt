package com.stim.blitzcart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CategoryAdapter(private val context: Context, private val categories: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.categoryIcon)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryIcon.setImageResource(category.iconResourceId)
        holder.categoryName.text = category.categoryName

        holder.itemView.setOnClickListener {
            // Handle category item click
            val intent = Intent(context, ShopActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.categoryName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = categories.size
}
