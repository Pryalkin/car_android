package com.bsuir.drozd.app.repository

import com.bsuir.drozd.app.dto.UserDTO
import com.bsuir.drozd.app.dto.answer.LoginUserAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.setting.AppSettings
import com.bsuir.drozd.sources.exception.BackendException
import com.bsuir.drozd.sources.exception.InvalidCredentialsException
import com.bsuir.drozd.sources.model.auth.AuthSource
import retrofit2.Response

class AuthRepository(
    private val authSource: AuthSource,
    private val appSettings: AppSettings
) {

    suspend fun login(userDTO: UserDTO): Response<LoginUserAnswerDTO> {
        val res: Response<LoginUserAnswerDTO> = try {
            authSource.login(userDTO)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        appSettings.setCurrentToken(res.headers().get("Jwt-Token"))
        appSettings.setCurrentUsername(res.body()?.username)
        appSettings.setCurrentRole(res.body()?.role)
        return res
    }

    suspend fun registration(userDTO: UserDTO): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            authSource.registration(userDTO)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        return res
    }

    fun logout(){
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }

}