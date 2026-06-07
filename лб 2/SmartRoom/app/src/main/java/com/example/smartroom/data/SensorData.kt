package com.example.smartroom.data

import com.google.gson.annotations.SerializedName

data class SensorData(
    @SerializedName("id") val id: Int,
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("lightLevel") val lightLevel: Int,
    @SerializedName("createdAt") val createdAt: String
)

data class LightStatus(
    @SerializedName("light") val isOn: Boolean
)