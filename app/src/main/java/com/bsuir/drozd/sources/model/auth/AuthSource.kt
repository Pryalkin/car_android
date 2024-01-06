package com.bsuir.drozd.sources.model.auth

import com.bsuir.drozd.app.dto.UserDTO
import com.bsuir.drozd.app.dto.answer.LoginUserAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import retrofit2.Response

interface AuthSource {
    suspend fun registration(userDTO: UserDTO): Response<HttpResponse>
    suspend fun login(userDTO: UserDTO): Response<LoginUserAnswerDTO>
}