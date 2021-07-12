package com.hind.flickrgallery.businesslogic.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object FlickrServiceBuilder {

    private val baseUrl = "https://www.flickr.com/services/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun<T> buildService(service: Class<T>):T{
        return retrofit.create(service)
    }
}