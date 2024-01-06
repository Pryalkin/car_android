package com.bsuir.drozd.sources.model.api

import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.CarDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ApiSource {
    suspend fun getCategory(category: String): Response<List<CarAnswerDTO>>
    suspend fun createCar(car: CarDTO, image: MultipartBody.Part): Response<HttpResponse>
    suspend fun getCar(id: Long): Response<CarAnswerDTO>
    suspend fun application(applicationDTO: ApplicationDTO): Response<List<CarAnswerDTO>>
    suspend fun getApp(): Response<List<CarAnswerDTO>>
}