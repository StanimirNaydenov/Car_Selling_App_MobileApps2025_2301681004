package com.example.car_selling_app

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.car_selling_app.data.Car

class CarAdapter(
    private val onItemClicked: (Car) -> Unit,
    private val onItemLongClicked: (Car) -> Unit
) : ListAdapter<Car, CarAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(car)
        holder.itemView.setOnClickListener { onItemClicked(car) }
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(car)
            true
        }
    }

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val makeModel: TextView = itemView.findViewById(R.id.textViewMakeModel)
        private val specs: TextView = itemView.findViewById(R.id.textViewSpecs)
        private val priceEUR: TextView = itemView.findViewById(R.id.textViewPriceEUR)
        private val priceBGN: TextView = itemView.findViewById(R.id.textViewPriceBGN)
        private val yearEngine: TextView = itemView.findViewById(R.id.textViewYearEngine)
        private val location: TextView = itemView.findViewById(R.id.textViewLocation)
        private val carImage: ImageView = itemView.findViewById(R.id.imageViewCar)

        fun bind(car: Car) {
            makeModel.text = "${car.make} ${car.model}"
            specs.text = "${car.mileage} км / ${car.horsepower} HP / ${car.transmission}"
            
            val priceEur = car.price
            val priceBgn = priceEur * 1.95583
            
            priceEUR.text = String.format("%.0f €", priceEur)
            priceBGN.text = String.format("%.2f лв.", priceBgn)
            
            yearEngine.text = "${car.year} г., ${car.engineType}"
            location.text = car.location
            
            // Load the first image from the paths
            val firstImagePath = car.imagePaths.split(",").firstOrNull { it.isNotEmpty() }
            if (firstImagePath != null) {
                carImage.load(Uri.parse(firstImagePath)) {
                    placeholder(R.mipmap.ic_launcher)
                    error(R.mipmap.ic_launcher)
                }
            } else {
                carImage.setImageResource(R.mipmap.ic_launcher)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }
}
