package com.example.mature

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailActivity : AppCompatActivity() {
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setupImageSlider()
        setupButtons()
    }

    private fun setupImageSlider() {
        val images = listOf(
            R.drawable.shoe_1,
            R.drawable.shoe_2,
            R.drawable.shoe_3,
            R.drawable.shoe_4,
            R.drawable.shoe_5
        )

        val viewPager = findViewById<ViewPager2>(R.id.imageSlider)
        viewPager.adapter = ProductImageAdapter(images)

        // Настройка индикатора
        val tabLayout = findViewById<TabLayout>(R.id.sliderIndicator)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.favoriteButton).apply {
            setOnClickListener {
                isFavorite = !isFavorite
                setIconTintResource(if (isFavorite) R.color.red else R.color.black)
                setIconResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
            }
        }

        findViewById<MaterialButton>(R.id.addToCartButton).setOnClickListener {
            Toast.makeText(this, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
        }
    }
}