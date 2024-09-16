package com.stim.blitzcart

// BrandAdapter.kt
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BrandAdapter(private val brandList: List<BrandModel>) :
    RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    // ViewHolder
    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val brandNameTextView: TextView = itemView.findViewById(R.id.brandNameTextView)
        val shopNowButton: Button = itemView.findViewById(R.id.shopNowButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brand_poster, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brandList[position]

        // Populate UI elements in the ViewHolder
        holder.posterImageView.setImageResource(brand.posterResId)
        holder.brandNameTextView.text = brand.brandName

        holder.itemView.setOnClickListener {
            // Handle click event, e.g., navigate to a new activity with products of the selected brand
            val intent = Intent(holder.itemView.context, ShopActivity::class.java)
            intent.putExtra("brandName", brand.brandName)
            holder.itemView.context.startActivity(intent)
        }

        // Populate UI elements in the ViewHolder
        holder.posterImageView.setImageResource(brand.posterResId)
        holder.brandNameTextView.text = brand.brandName

        // Set click listener for the "Shop Now" button
        holder.shopNowButton.setOnClickListener {
            // Handle "Shop Now" button click, e.g., navigate to a new activity with products of the selected brand
            val intent = Intent(holder.itemView.context, ShopActivity::class.java)
            intent.putExtra("brandName", brand.brandName)
            holder.itemView.context.startActivity(intent)
        }

        // Set click listener for the entire item view
        holder.itemView.setOnClickListener {
            // Handle click event, e.g., navigate to a different activity or perform another action
            // This click listener is separate from the "Shop Now" button click
        }
    }

    override fun getItemCount(): Int {
        return brandList.size
    }
}
