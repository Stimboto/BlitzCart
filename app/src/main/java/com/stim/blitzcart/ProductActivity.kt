package com.stim.blitzcart

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Retrieve product data from intent
        val productName = intent.getStringExtra("productId")

        // Update UI with product details (sample, replace with your actual UI components)
        val productDetailsTextView: TextView = findViewById(R.id.txtProductName)
        productDetailsTextView.text = "Product Details: $productName"
    }
}

