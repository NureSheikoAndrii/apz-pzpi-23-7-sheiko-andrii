package com.example.smartroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.data.RetrofitClient
import com.example.smartroom.data.SensorData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val api = RetrofitClient.apiService

    private val _sensorData = MutableStateFlow<SensorData?>(null)
    val sensorData: StateFlow<SensorData?> = _sensorData.asStateFlow()

    private val _historyData = MutableStateFlow<List<SensorData>>(emptyList())
    val historyData: StateFlow<List<SensorData>> = _historyData.asStateFlow()

    private val _isLightOn = MutableStateFlow(false)
    val isLightOn: StateFlow<Boolean> = _isLightOn.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAllData()
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(10000)
                loadAllData()
            }
        }
    }

    fun loadAllData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val latest = api.getLatest()
                val history = api.getHistory(
                    from = "2024-01-01",
                    to = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                )
                val lightStatus = api.getLightStatus()

                _sensorData.value = latest
                _historyData.value = history
                _isLightOn.value = lightStatus.isOn
            } catch (e: Exception) {
                _error.value = "Помилка: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLight() {
        viewModelScope.launch {
            try {
                val newState = !_isLightOn.value
                val response = api.setLight(newState)
                _isLightOn.value = response.isOn
            } catch (e: Exception) {
                _error.value = "Не вдалося керувати світлом: ${e.message}"
            }
        }
    }

    fun refresh() {
        loadAllData()
    }
}