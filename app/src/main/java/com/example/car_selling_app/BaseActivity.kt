package com.example.car_selling_app

import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity : AppCompatActivity() {

    protected fun setupCommonUI() {
        // Setup Header Logo
        findViewById<TextView>(R.id.headerLogoText)?.let { textView ->
            val fullText = "CarMatrix"
            val spannableString = SpannableString(fullText)
            val color = ContextCompat.getColor(this, R.color.matrix_blue)
            spannableString.setSpan(
                ForegroundColorSpan(color),
                3,
                9,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannableString
        }

        // Setup Bottom Navigation
        findViewById<BottomNavigationView>(R.id.bottomNavigation)?.let { nav ->
            // Set correct selected item based on current activity
            nav.selectedItemId = when (this) {
                is MainActivity -> R.id.nav_home
                is LikedActivity -> R.id.nav_liked
                is AddCarActivity -> R.id.nav_add_car
                else -> nav.selectedItemId
            }

            nav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        if (this !is MainActivity) {
                            startActivity(Intent(this, MainActivity::class.java))
                            if (this !is AddCarActivity) finish()
                        }
                        true
                    }
                    R.id.nav_liked -> {
                        if (this !is LikedActivity) {
                            startActivity(Intent(this, LikedActivity::class.java))
                            if (this !is AddCarActivity) finish()
                        }
                        true
                    }
                    R.id.nav_add_car -> {
                        if (this !is AddCarActivity) {
                            startActivity(Intent(this, AddCarActivity::class.java))
                        }
                        true
                    }
                    else -> true
                }
            }
        }
    }
}
