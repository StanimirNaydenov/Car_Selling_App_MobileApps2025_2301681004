package com.example.car_selling_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.car_selling_app.data.Car
import com.example.car_selling_app.data.CarViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val viewModel: CarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCars)
        val adapter = CarAdapter(
            onItemClicked = { car ->
                val intent = Intent(this, AddCarActivity::class.java)
                intent.putExtra("EXTRA_CAR_ID", car.id)
                startActivity(intent)
            },
            onItemLongClicked = { car ->
                showOptionsDialog(car)
            }
        )
        recyclerView.adapter = adapter

        viewModel.allCars.observe(this) { cars ->
            adapter.submitList(cars)
        }

        findViewById<FloatingActionButton>(R.id.fabAddCar).setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showOptionsDialog(car: Car) {
        val options = arrayOf("Share", "Delete")
        AlertDialog.Builder(this)
            .setTitle("Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> shareCar(car)
                    1 -> showDeleteDialog(car)
                }
            }
            .show()
    }

    private fun shareCar(car: Car) {
        val shareText = "Check out this ${car.make} ${car.model} from ${car.year} for ${car.price} €!\n${car.description}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Share car via"))
    }

    private fun showDeleteDialog(car: Car) {
        AlertDialog.Builder(this)
            .setTitle("Delete Car")
            .setMessage("Are you sure you want to delete this car listing?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.delete(car)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
