
package com.stim.blitzcart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stim.blitzcart.databinding.ActivitySpecialBrandOwnerBinding

class SpecialBrandOwnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecialBrandOwnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecialBrandOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Your implementation for SpecialBrandOwnerActivity
    }
}
