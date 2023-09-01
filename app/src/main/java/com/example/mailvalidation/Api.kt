package com.example.mailvalidation

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MailBoxService{
    @GET("check")
    suspend fun checkEmail(@Query("access_key") key:String,
                        @Query("email") email:String) : EmailResponse
}

object Api {
    const val API_KEY = BuildConfig.API_KEY
    private val BASE_URL = "http://apilayer.net/api/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit = Retrofit.Builder().
            addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL).client(httpClient).build()

    val service : MailBoxService by lazy{retrofit.create(MailBoxService::class.java)}


}