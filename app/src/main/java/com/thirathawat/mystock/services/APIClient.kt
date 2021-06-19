package com.thirathawat.mystock.services

import com.thirathawat.mystock.BASE_URL
import com.thirathawat.mystock.IMAGE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getImageURL() = BASE_URL + IMAGE_URL
}