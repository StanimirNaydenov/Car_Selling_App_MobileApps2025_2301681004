package com.example.car_selling_app

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.car_selling_app.data.Car
import com.example.car_selling_app.data.CarViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class AddCarActivity : AppCompatActivity() {

    private val viewModel: CarViewModel by viewModels()
    private var selectedImageUris: List<Uri> = emptyList()
    private var editingCarId: Int = -1
    private var existingImagePaths: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        val titleView = findViewById<TextView>(R.id.textViewTitle)
        val editMake = findViewById<EditText>(R.id.editTextMake)
        val editModel = findViewById<EditText>(R.id.editTextModel)
        val editYear = findViewById<EditText>(R.id.editTextYear)
        val editPrice = findViewById<EditText>(R.id.editTextPrice)
        val editMileage = findViewById<EditText>(R.id.editTextMileage)
        val editHorsepower = findViewById<EditText>(R.id.editTextHorsepower)
        val editLocation = findViewById<EditText>(R.id.editTextLocation)
        val editDescription = findViewById<EditText>(R.id.editTextDescription)
        
        val layoutMake = findViewById<TextInputLayout>(R.id.layoutMake)
        val layoutModel = findViewById<TextInputLayout>(R.id.layoutModel)
        val layoutYear = findViewById<TextInputLayout>(R.id.layoutYear)
        val layoutPrice = findViewById<TextInputLayout>(R.id.layoutPrice)
        val layoutMileage = findViewById<TextInputLayout>(R.id.layoutMileage)
        val layoutHorsepower = findViewById<TextInputLayout>(R.id.layoutHorsepower)
        val layoutLocation = findViewById<TextInputLayout>(R.id.layoutLocation)
        val layoutEngine = findViewById<TextInputLayout>(R.id.layoutEngineType)
        val layoutTransmission = findViewById<TextInputLayout>(R.id.layoutTransmission)

        val spinnerEngine = findViewById<AutoCompleteTextView>(R.id.spinnerEngineType)
        val spinnerTransmission = findViewById<AutoCompleteTextView>(R.id.spinnerTransmission)
        
        val buttonAddPhotos = findViewById<Button>(R.id.buttonAddPhotos)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // Setup Spinners
        val engines = arrayOf("Дизел", "Бензин", "Хибрид", "Електричество")
        spinnerEngine.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, engines))

        val transmissions = arrayOf("Автоматик", "Ръчни скорости")
        spinnerTransmission.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, transmissions))

        // Check if we are editing
        editingCarId = intent.getIntExtra("EXTRA_CAR_ID", -1)
        if (editingCarId != -1) {
            titleView.text = "Edit Listing"
            buttonSave.text = "Update Car"
            lifecycleScope.launch {
                val car = viewModel.getCarById(editingCarId)
                car?.let {
                    editMake.setText(it.make)
                    editModel.setText(it.model)
                    editYear.setText(it.year.toString())
                    editPrice.setText(it.price.toString())
                    editMileage.setText(it.mileage.toString())
                    editHorsepower.setText(it.horsepower.toString())
                    editLocation.setText(it.location)
                    editDescription.setText(it.description)
                    spinnerEngine.setText(it.engineType, false)
                    spinnerTransmission.setText(it.transmission, false)
                    existingImagePaths = it.imagePaths
                    if (existingImagePaths.isNotEmpty()) {
                        buttonAddPhotos.text = "Keep existing photos (${existingImagePaths.split(",").size})"
                    }
                }
            }
        }

        // Photo Picker logic
        val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            if (uris.isNotEmpty()) {
                selectedImageUris = uris
                buttonAddPhotos.text = "Снимки: ${uris.size} избрани"
            }
        }

        buttonAddPhotos.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        buttonSave.setOnClickListener {
            val layouts = listOf(layoutMake, layoutModel, layoutYear, layoutPrice, layoutMileage, layoutHorsepower, layoutLocation, layoutEngine, layoutTransmission)
            layouts.forEach { it.error = null }

            var isValid = true
            if (editMake.text.isNullOrBlank()) { layoutMake.error = "Mandatory"; isValid = false }
            if (editModel.text.isNullOrBlank()) { layoutModel.error = "Mandatory"; isValid = false }
            if (editYear.text.isNullOrBlank()) { layoutYear.error = "Mandatory"; isValid = false }
            if (editPrice.text.isNullOrBlank()) { layoutPrice.error = "Mandatory"; isValid = false }
            if (editMileage.text.isNullOrBlank()) { layoutMileage.error = "Mandatory"; isValid = false }
            if (editHorsepower.text.isNullOrBlank()) { layoutHorsepower.error = "Mandatory"; isValid = false }
            if (editLocation.text.isNullOrBlank()) { layoutLocation.error = "Mandatory"; isValid = false }
            if (spinnerEngine.text.isNullOrBlank()) { layoutEngine.error = "Mandatory"; isValid = false }
            if (spinnerTransmission.text.isNullOrBlank()) { layoutTransmission.error = "Mandatory"; isValid = false }

            if (!isValid) {
                Toast.makeText(this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Copy images if new ones selected, otherwise use existing
            val finalImagePaths = if (selectedImageUris.isNotEmpty()) {
                selectedImageUris.mapNotNull { saveImageToInternalStorage(it) }.joinToString(",")
            } else {
                existingImagePaths
            }

            val car = Car(
                id = if (editingCarId != -1) editingCarId else 0,
                make = editMake.text.toString(),
                model = editModel.text.toString(),
                year = editYear.text.toString().toIntOrNull() ?: 0,
                price = editPrice.text.toString().toDoubleOrNull() ?: 0.0,
                mileage = editMileage.text.toString().toIntOrNull() ?: 0,
                horsepower = editHorsepower.text.toString().toIntOrNull() ?: 0,
                location = editLocation.text.toString(),
                engineType = spinnerEngine.text.toString(),
                transmission = spinnerTransmission.text.toString(),
                description = editDescription.text.toString(),
                imagePaths = finalImagePaths
            )
            
            if (editingCarId != -1) {
                viewModel.update(car)
            } else {
                viewModel.insert(car)
            }
            finish()
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val fileName = "car_${UUID.randomUUID()}.jpg"
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
