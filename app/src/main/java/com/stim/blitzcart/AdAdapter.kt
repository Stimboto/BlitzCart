package com.stim.blitzcart

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class AdAdapter(private val adList: List<AdModel>) : RecyclerView.Adapter<AdAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ad = adList[position]

        // Set ad image or video
        holder.adImage.setImageResource(ad.resource)

        // Set click listener for ad image
        holder.adImage.setOnClickListener {
            // Handle click action here, e.g., navigate to SPECIAL brand activity
            val context = holder.itemView.context
            val intent = Intent(context, SpecialBrandActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return adList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adImage: ImageView = itemView.findViewById(R.id.adImage)
    }
}
