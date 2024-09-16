package com.stim.blitzcart
import android.widget.TextView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_kitchen.*

class KitchenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen)

        // Retrieve kitchen product data from intent
        val kitchenProductId = intent.getStringExtra("kitchenProductId")

        // Update UI with kitchen product details (sample, replace with your actual UI components)
        val kitchenProductDetailsTextView: TextView = findViewById(R.id.kitchenProductDetailsTextView)
        kitchenProductDetailsTextView.text = "Kitchen Product Details: $kitchenProductId"
    }
}
