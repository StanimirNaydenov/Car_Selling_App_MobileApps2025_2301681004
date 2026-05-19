package com.example.car_selling_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.car_selling_app.data.Car
import com.example.car_selling_app.data.CarViewModel
import kotlinx.coroutines.launch

class CarDetailActivity : BaseActivity() {

    private val viewModel: CarViewModel by viewModels()
    private var currentCar: Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        setupCommonUI()

        val carId = intent.getIntExtra("EXTRA_CAR_ID", -1)
        if (carId == -1) {
            finish()
            return
        }

        lifecycleScope.launch {
            val car = viewModel.getCarById(carId)
            if (car == null) {
                finish()
                return@launch
            }
            currentCar = car
            displayCarDetails(car)
        }

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarDetail)?.let {
            it.setNavigationOnClickListener { finish() }
        }
    }

    private fun displayCarDetails(car: Car) {
        // Setup Images
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerImages)
        val imagePaths = car.imagePaths.split(",").filter { it.isNotEmpty() }
        viewPager.adapter = ImageAdapter(imagePaths)

        // Basic Info
        findViewById<TextView>(R.id.textViewDetailMakeModel).text = "${car.make} ${car.model}"
        findViewById<TextView>(R.id.textViewDetailPrice).text = String.format("%.0f €", car.price)

        // Specs
        findViewById<TextView>(R.id.textDetailYear).text = car.year.toString()
        findViewById<TextView>(R.id.textDetailEngine).text = car.engineType
        findViewById<TextView>(R.id.textDetailPower).text = "${car.horsepower} HP"
        findViewById<TextView>(R.id.textDetailTransmission).text = car.transmission
        findViewById<TextView>(R.id.textDetailMileage).text = "${car.mileage} km"

        // Description
        findViewById<TextView>(R.id.textViewDetailDescription).text = car.description

        // Like Button
        val likeButton = findViewById<ImageButton>(R.id.buttonLike)
        updateLikeButtonUI(car.isLiked)
        likeButton.setOnClickListener {
            val newStatus = !(currentCar?.isLiked ?: false)
            currentCar = currentCar?.copy(isLiked = newStatus)
            viewModel.updateLikedStatus(car.id, newStatus)
            updateLikeButtonUI(newStatus)
            val msg = if (newStatus) "Added to favorites" else "Removed from favorites"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // Share Button
        findViewById<ImageButton>(R.id.buttonShare).setOnClickListener {
            val shareText = "Check out this ${car.make} ${car.model} for ${car.price} €!\n${car.description}"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(intent, "Share car via"))
        }

        // Call Button (Example number)
        findViewById<Button>(R.id.buttonCall).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+359888888888")
            }
            startActivity(intent)
        }
    }

    private fun updateLikeButtonUI(isLiked: Boolean) {
        val likeButton = findViewById<ImageButton>(R.id.buttonLike)
        likeButton.setImageResource(R.drawable.ic_star)
        if (isLiked) {
            likeButton.setColorFilter(ContextCompat.getColor(this, R.color.matrix_blue))
        } else {
            likeButton.setColorFilter(ContextCompat.getColor(this, R.color.gray_text))
        }
    }
}
