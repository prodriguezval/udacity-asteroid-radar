package com.udacity.asteroidradar.infrastructure.network.nasa

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"

private val jsonConverter = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val nasaHttpService = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(jsonConverter))
    .build()

interface NasaApiService {
    @GET("planetary/apod?api_key=${BuildConfig.NASA_API_KEY}")
    suspend fun getImageOfTheDay(): PictureOfDay

    @GET("neo/rest/v1/feed?api_key=${BuildConfig.NASA_API_KEY}")
    suspend fun getAsteroidData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): String

}

object NasaApi {
    val httpService: NasaApiService by lazy {
        nasaHttpService.create(NasaApiService::class.java)
    }
}