package com.bsuir.drozd.sources.model.api

import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiApi {

    @POST("api/category")
    suspend fun getCategory(@Query("category") category: String): Response<List<CarAnswerDTO>>

    @GET("api/app")
    suspend fun getApp(): Response<List<CarAnswerDTO>>

    @POST("api/application")
    suspend fun application(@Body applicationDTO: ApplicationDTO): Response<List<CarAnswerDTO>>

    @POST("api/car/get")
    suspend fun getCar(@Query("id") id: Long): Response<CarAnswerDTO>

    @Multipart
    @POST("api/car/create")
    suspend fun createCar(
        @Query("category") category: String,
        @Query("brand") brand: String,
        @Query("engineCapacity") engineCapacity: Int,
        @Query("volumeCapacity") volumeCapacity: Double,
        @Query("machineDrive") machineDrive: String,
        @Query("color") color: String,
        @Query("consumption") consumption: Double,
        @Query("numberOfSeats") numberOfSeats: Int,
        @Query("price") price: Double,
        @Part file: MultipartBody.Part
    ): Response<HttpResponse>

}