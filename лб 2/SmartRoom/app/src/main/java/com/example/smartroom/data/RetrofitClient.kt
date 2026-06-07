package com.example.smartroom.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {
    // Для емулятора Android:
    private const val BASE_URL = "https://10.0.2.2:7185/"

    // Створюємо TrustManager, який довіряє всім сертифікатам
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    // Створюємо SSLContext, який використовує наш TrustManager
    private val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    // Налаштовуємо OkHttpClient з ігноруванням SSL помилок
    private val client = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true }  // Ігноруємо перевірку hostname
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .build()

    val apiService: SmartRoomApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartRoomApiService::class.java)
    }
}