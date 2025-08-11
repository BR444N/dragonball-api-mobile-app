package com.br444n.dragonball.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val api: DragonBallApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DragonBallApiService::class.java)
    }
}