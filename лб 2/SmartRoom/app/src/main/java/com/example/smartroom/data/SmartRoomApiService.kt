package com.example.smartroom.data

import retrofit2.http.*

interface SmartRoomApiService {
    @GET("api/sensors/latest")
    suspend fun getLatest(): SensorData

    @GET("api/sensors/history")
    suspend fun getHistory(
        @Query("from") from: String = "2024-01-01",
        @Query("to") to: String
    ): List<SensorData>

    @GET("api/light/status")
    suspend fun getLightStatus(): LightStatus

    @POST("api/light")
    suspend fun setLight(
        @Query("state") state: Boolean
    ): LightStatus
}
