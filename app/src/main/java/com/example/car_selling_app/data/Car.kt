package com.example.car_selling_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val make: String,
    val model: String,
    val year: Int,
    val price: Double,
    val description: String,
    val mileage: Int,
    val engineType: String,
    val transmission: String,
    val location: String,
    val horsepower: Int,
    val imagePaths: String, // Store as comma-separated strings or JSON
    val isLiked: Boolean = false
)
