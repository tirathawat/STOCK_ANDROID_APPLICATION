package com.thirathawat.mystock.services

import com.thirathawat.mystock.API_PRODUCT
import com.thirathawat.mystock.API_PRODUCT_PARAMS_ID
import com.thirathawat.mystock.models.ProductRequest
import com.thirathawat.mystock.models.ProductResponse
import retrofit2.Call
import retrofit2.http.*


interface APIService {

    @GET(API_PRODUCT)
    fun getProduct(): Call<List<ProductResponse>>

    @POST(API_PRODUCT)
    fun addProduct(@Body product: ProductRequest): Call<Any>

    @PUT("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
    fun editProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int, @Body product: ProductRequest): Call<Any>

    @DELETE("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
    fun deleteProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int): Call<Any>
}