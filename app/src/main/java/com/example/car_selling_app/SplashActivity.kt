package com.example.car_selling_app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashText = findViewById<TextView>(R.id.splashText)
        val fullText = "CarMatrix"
        val spannableString = SpannableString(fullText)
        
        // Color "Matrix" (index 3 to 9)
        val color = ContextCompat.getColor(this, R.color.matrix_blue)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            3,
            9,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        
        splashText.text = spannableString

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
