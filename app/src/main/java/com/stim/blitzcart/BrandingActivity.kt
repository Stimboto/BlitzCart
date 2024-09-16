
package com.stim.blitzcart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_brand_poster)

        // Get the brand name passed from HomeActivity
        val brandName = intent.getStringExtra("brandName")

        // Set the toolbar title to the selected brand
        supportActionBar?.title = brandName

        // Fetch products based on the selected brand (replace this with your logic)
        val productList = getProductsByBrand(brandName)

        // Set up RecyclerView for displaying products
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProduct)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(productList)
    }

    // Replace this with your logic to fetch products based on the selected brand
    private fun getProductsByBrand(brandName: String?): List<ProductModel> {
        // Example data, replace with your actual product data retrieval logic
        return when (brandName) {
            "Brand A" -> listOf(
                ProductModel("Product 1", R.drawable.ic_p1),
                ProductModel("Product 2", R.drawable.ic_p1),
                ProductModel("Product 3", R.drawable.ic_p1)
            )
            "Brand B" -> listOf(
                ProductModel("Product 4", R.drawable.ic_p1),
                ProductModel("Product 5", R.drawable.ic_p1),
                ProductModel("Product 6", R.drawable.ic_p1)
            )
            // Add more cases for other brands
            else -> emptyList()
        }
    }
}
