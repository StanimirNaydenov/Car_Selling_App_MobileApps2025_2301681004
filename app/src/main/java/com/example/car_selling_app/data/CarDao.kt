package com.example.car_selling_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY id DESC")
    fun getAllCars(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("SELECT * FROM cars WHERE id = :id")
    suspend fun getCarById(id: Int): Car?

    @Query("SELECT * FROM cars WHERE isLiked = 1 ORDER BY id DESC")
    fun getLikedCars(): Flow<List<Car>>

    @Query("UPDATE cars SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLikedStatus(id: Int, isLiked: Boolean)

    @Query("UPDATE cars SET isLiked = 0")
    suspend fun clearAllLiked()

    @Query("SELECT * FROM cars WHERE make = :make ORDER BY id DESC")
    fun getCarsByMake(make: String): Flow<List<Car>>
}
