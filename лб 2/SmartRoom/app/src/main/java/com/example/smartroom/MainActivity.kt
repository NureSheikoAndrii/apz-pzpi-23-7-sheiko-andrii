package com.example.smartroom

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smartroom.data.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var temperatureText: TextView
    private lateinit var humidityText: TextView
    private lateinit var lightLevelText: TextView
    private lateinit var lightButton: Button
    private lateinit var refreshButton: Button

    private var isLightOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperatureText = findViewById(R.id.temperatureText)
        humidityText = findViewById(R.id.humidityText)
        lightLevelText = findViewById(R.id.lightLevelText)
        lightButton = findViewById(R.id.lightButton)
        refreshButton = findViewById(R.id.refreshButton)

        refreshButton.setOnClickListener {
            loadData()
            loadLightStatus()
        }

        lightButton.setOnClickListener {
            toggleLight()
        }

        loadData()
        loadLightStatus()
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val data = RetrofitClient.apiService.getLatest()
                temperatureText.text = "${data.temperature}°C"
                humidityText.text = "${data.humidity}%"
                lightLevelText.text = "${data.lightLevel} lux"
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadLightStatus() {
        lifecycleScope.launch {
            try {
                val status = RetrofitClient.apiService.getLightStatus()
                isLightOn = status.isOn
                updateLightButton()
            } catch (e: Exception) {
                // Ігноруємо
            }
        }
    }

    private fun toggleLight() {
        lifecycleScope.launch {
            try {
                val newState = !isLightOn
                val response = RetrofitClient.apiService.setLight(newState)
                isLightOn = response.isOn
                updateLightButton()
                Toast.makeText(this@MainActivity, if (isLightOn) "Світло ввімкнено" else "Світло вимкнено", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Не вдалося керувати світлом", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateLightButton() {
        if (isLightOn) {
            lightButton.text = "🔆 Вимкнути світло"
            lightButton.setBackgroundColor(resources.getColor(android.R.color.holo_orange_dark))
            window.decorView.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        } else {
            lightButton.text = "💡 Увімкнути світло"
            lightButton.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            window.decorView.setBackgroundColor(resources.getColor(android.R.color.black))
        }
    }
}