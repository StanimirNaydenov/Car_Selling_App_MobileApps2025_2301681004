package com.example.car_selling_app.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {
    private val carDao = CarDatabase.getDatabase(application).carDao()
    val allCars: LiveData<List<Car>> = carDao.getAllCars().asLiveData()

    fun insert(car: Car) = viewModelScope.launch {
        carDao.insertCar(car)
    }

    fun update(car: Car) = viewModelScope.launch {
        carDao.updateCar(car)
    }

    fun delete(car: Car) = viewModelScope.launch {
        carDao.deleteCar(car)
    }

    suspend fun getCarById(id: Int): Car? {
        return carDao.getCarById(id)
    }
}
