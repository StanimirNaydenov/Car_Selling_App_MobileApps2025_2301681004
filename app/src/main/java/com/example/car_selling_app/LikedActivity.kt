package com.example.car_selling_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.car_selling_app.data.CarViewModel

class LikedActivity : BaseActivity() {

    private val viewModel: CarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked)
        setupCommonUI()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLiked)
        val adapter = CarAdapter(
            onItemClicked = { car ->
                val intent = Intent(this, AddCarActivity::class.java)
                intent.putExtra("EXTRA_CAR_ID", car.id)
                startActivity(intent)
            },
            onItemLongClicked = { /* Optional */ }
        )
        recyclerView.adapter = adapter

        viewModel.likedCars.observe(this) { cars ->
            adapter.submitList(cars)
        }

        findViewById<Button>(R.id.buttonClearAll).setOnClickListener {
            viewModel.clearAllLiked()
            Toast.makeText(this, "All liked cars removed", Toast.LENGTH_SHORT).show()
        }

        // Swipe to remove from liked
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder): Boolean = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val car = adapter.currentList[position]
                    viewModel.updateLikedStatus(car.id, false)
                    Toast.makeText(this@LikedActivity, "${car.make} премахната от любими", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(recyclerView)
    }
}
