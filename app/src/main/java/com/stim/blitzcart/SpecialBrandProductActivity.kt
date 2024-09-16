
package com.stim.blitzcart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stim.blitzcart.databinding.ActivitySpecialBrandProductBinding

class SpecialBrandProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecialBrandProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecialBrandProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Your implementation for SpecialBrandProductActivity
    }
}
