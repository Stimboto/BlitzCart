package com.stim.blitzcart

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class AdvertisementAdapter2(private val adsList2: List<AdvertisementModel2>) :
    RecyclerView.Adapter<AdvertisementAdapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_advertisement_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ad2 = adsList2[position]

        // Set advertisement image
        holder.advertisementImage.setImageResource(ad2.imageUrl)

        // Handle click event for the second advertisement item
        holder.itemView.setOnClickListener {
            // Open the brand website
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad2.websiteUrl))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return adsList2.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val advertisementImage: ImageView = itemView.findViewById(R.id.advertisementImage2)
    }
}
