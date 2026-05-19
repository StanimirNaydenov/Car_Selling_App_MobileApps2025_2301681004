package com.example.car_selling_app.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {
    private val carDao = CarDatabase.getDatabase(application).carDao()
    val allCars: LiveData<List<Car>> = carDao.getAllCars().asLiveData()
    val likedCars: LiveData<List<Car>> = carDao.getLikedCars().asLiveData()

    fun insert(car: Car) = viewModelScope.launch {
        carDao.insertCar(car)
    }

    fun update(car: Car) = viewModelScope.launch {
        carDao.updateCar(car)
    }

    fun updateLikedStatus(id: Int, isLiked: Boolean) = viewModelScope.launch {
        carDao.updateLikedStatus(id, isLiked)
    }

    fun clearAllLiked() = viewModelScope.launch {
        carDao.clearAllLiked()
    }

    fun getCarsByMake(make: String): LiveData<List<Car>> {
        return carDao.getCarsByMake(make).asLiveData()
    }

    fun delete(car: Car) = viewModelScope.launch {
        carDao.deleteCar(car)
    }

    suspend fun getCarById(id: Int): Car? {
        return carDao.getCarById(id)
    }
}
