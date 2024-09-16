package com.stim.blitzcart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            // User is already logged in, navigate to the home page or main activity
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // finish the current login activity to prevent going back
        } else {
            // User is not logged in, show the branding image with skip button
            setContentView(R.layout.activity_main)

            val viewPager: ViewPager = findViewById(R.id.viewPager)
            val skipButton: Button = findViewById(R.id.btnSkip)

            // List of image resources
            val imageList = listOf(R.drawable.your_branding_page1, R.drawable.your_branding_page2, R.drawable.your_branding_image)

            // Set up the ViewPager with the adapter
            val adapter = ImagePagerAdapter(this, imageList)
            viewPager.adapter = adapter

            // Set up a listener to check if the last page is reached
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    // Check if it's the last page
                    skipButton.visibility = if (position == imageList.size - 1) View.VISIBLE else View.GONE
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            // Skip button click listener
            skipButton.setOnClickListener {
                // Navigate to the login activity
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // finish the current activity if not needed
            }
        }
    }
}
