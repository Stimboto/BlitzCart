package com.stim.blitzcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        // Set product image and name
        holder.productImage.setImageResource(product.icon)
        holder.productName.text = product.name

        // Set click listener for the product item
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(product.name) // Pass product ID or name
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.onItemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
    }
}
