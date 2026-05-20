package com.example.car_selling_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.car_selling_app.data.Car
import com.example.car_selling_app.data.CarViewModel

class MainActivity : BaseActivity() {

    private val viewModel: CarViewModel by viewModels()
    private var activeCarsLiveData: LiveData<List<Car>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCommonUI()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCars)
        val adapter = CarAdapter(
            onItemClicked = { car ->
                val intent = Intent(this, CarDetailActivity::class.java)
                intent.putExtra("EXTRA_CAR_ID", car.id)
                startActivity(intent)
            },
            onItemLongClicked = { car ->
                showOptionsDialog(car)
            }
        )
        recyclerView.adapter = adapter

        // Initial observation: All cars
        observeCars(viewModel.allCars)

        setupBrandFilters()

        // Swipe to like/unlike
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder): Boolean = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentList = (recyclerView.adapter as CarAdapter).currentList
                    if (position < currentList.size) {
                        val car = currentList[position]
                        val newLikedStatus = !car.isLiked
                        
                        // Update database
                        viewModel.updateLikedStatus(car.id, newLikedStatus)
                        
                        val message = if (newLikedStatus) "Added to favorites" else "Removed from favorites"
                        Toast.makeText(this@MainActivity, "${car.make} $message", Toast.LENGTH_SHORT).show()
                    }
                }
                
                // CRITICAL: Always notify changed to bring the item back from swiped state
                // We do it with a small delay or post to let ItemTouchHelper finish its animation
                recyclerView.post {
                    recyclerView.adapter?.notifyItemChanged(position)
                }
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(recyclerView)
    }

    private fun observeCars(liveData: LiveData<List<Car>>, title: String = "Top Deals") {
        val adapter = findViewById<RecyclerView>(R.id.recyclerViewCars).adapter as CarAdapter
        val titleTextView = findViewById<TextView>(R.id.textViewListTitle)
        val seeAllTextView = findViewById<TextView>(R.id.textViewSeeAll)
        
        titleTextView.text = title
        seeAllTextView.visibility = if (title == "Top Deals") View.VISIBLE else View.GONE
        
        // Remove previous observer to avoid multiple refreshes and "disappearing" items
        activeCarsLiveData?.removeObservers(this)
        activeCarsLiveData = liveData
        
        activeCarsLiveData?.observe(this) { cars ->
            adapter.submitList(cars) {
                updateNoCarsVisibility(cars.isEmpty())
            }
        }
    }

    private fun setupBrandFilters() {
        findViewById<LinearLayout>(R.id.brandMercedes).setOnClickListener { observeCars(viewModel.getCarsByMake("Mercedes"), "Mercedes") }
        findViewById<LinearLayout>(R.id.brandBMW).setOnClickListener { observeCars(viewModel.getCarsByMake("BMW"), "BMW") }
        findViewById<LinearLayout>(R.id.brandAudi).setOnClickListener { observeCars(viewModel.getCarsByMake("Audi"), "Audi") }
        findViewById<LinearLayout>(R.id.brandToyota).setOnClickListener { observeCars(viewModel.getCarsByMake("Toyota"), "Toyota") }
    }

    private fun updateNoCarsVisibility(isEmpty: Boolean) {
        val noCarsText = findViewById<TextView>(R.id.textViewNoCarsFound)
        noCarsText.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    fun resetToAllCars() {
        observeCars(viewModel.allCars)
    }

    private fun showOptionsDialog(car: Car) {
        val options = arrayOf("Edit", "Share", "Delete")
        AlertDialog.Builder(this)
            .setTitle("Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, AddCarActivity::class.java)
                        intent.putExtra("EXTRA_CAR_ID", car.id)
                        startActivity(intent)
                    }
                    1 -> shareCar(car)
                    2 -> showDeleteDialog(car)
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
