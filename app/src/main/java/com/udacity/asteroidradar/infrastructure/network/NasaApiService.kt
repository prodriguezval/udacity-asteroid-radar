package com.udacity.asteroidradar.infrastructure.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.main.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val jsonConverter = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val nasaHttpService = Retrofit
    .Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(jsonConverter))
    .build()

interface NasaApiService {
    @GET("planetary/apod?api_key=${Constants.NASA_API_KEY}")
    suspend fun getPictureOfDay(): PictureOfDay

    @GET("neo/rest/v1/feed?api_key=${Constants.NASA_API_KEY}")
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