package com.bsuir.drozd.sources.model.auth

import com.bsuir.drozd.app.dto.UserDTO
import com.bsuir.drozd.app.dto.answer.LoginUserAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/registration")
    suspend fun registration(@Body userDTO: UserDTO): Response<HttpResponse>

    @POST("auth/login")
    suspend fun login(@Body userDTO: UserDTO): Response<LoginUserAnswerDTO>

}