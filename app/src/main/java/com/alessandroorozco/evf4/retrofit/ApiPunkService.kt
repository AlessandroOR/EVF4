package com.alessandroorozco.evf4.retrofit


import com.alessandroorozco.evf4.adapter.Beer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPunkService {

    companion object {
        private const val BASE_URL = "https://api.punkapi.com/v2/"

        fun create(): ApiPunkService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiPunkService::class.java)
        }
    }

    @GET("beers")
    fun getBeers(): Call<List<Beer>>

    @GET("beers/{id}")
    fun getBeerById(@Path("id") id: Int): Call<List<Beer>>
}