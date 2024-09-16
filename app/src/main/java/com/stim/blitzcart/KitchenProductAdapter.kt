package com.stim.blitzcart

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KitchenProductAdapter(private val kitchenProductList: List<KitchenProductModel>) :
    RecyclerView.Adapter<KitchenProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kitchen_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitchenProduct = kitchenProductList[position]

        // Set kitchen product image and name
        holder.kitchenProductImage.setImageResource(kitchenProduct.icon)
        holder.kitchenProductName.text = kitchenProduct.name

        // Handle click event for kitchen product item
        holder.itemView.setOnClickListener {
            // Navigate to KitchenActivity with relevant information
            val intent = Intent(it.context, KitchenActivity::class.java)
            intent.putExtra("kitchenProductId", kitchenProduct.name)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return kitchenProductList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kitchenProductImage: ImageView = itemView.findViewById(R.id.kitchenProductImage)
        val kitchenProductName: TextView = itemView.findViewById(R.id.kitchenProductName)
    }
}
