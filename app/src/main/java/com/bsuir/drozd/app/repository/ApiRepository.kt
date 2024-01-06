package com.bsuir.drozd.app.repository

import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.CarDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.setting.AppSettings
import com.bsuir.drozd.sources.exception.BackendException
import com.bsuir.drozd.sources.exception.InvalidCredentialsException
import com.bsuir.drozd.sources.model.api.ApiSource
import okhttp3.MultipartBody
import retrofit2.Response

class ApiRepository(
    private val apiSource: ApiSource,
    private val appSettings: AppSettings
) {

    fun getRole(): String?{
        return appSettings.getCurrentRole()
    }

    fun getUsername(): String? {
        return appSettings.getCurrentUsername()
    }

    suspend fun getCategory(category: String): Response<List<CarAnswerDTO>> {
        val res: Response<List<CarAnswerDTO>> = try {
            apiSource.getCategory(category)
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

    suspend fun createCar(car: CarDTO, image: MultipartBody.Part): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            apiSource.createCar(car, image)
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

    suspend fun getCar(id: Long): Response<CarAnswerDTO> {
        val res: Response<CarAnswerDTO> = try {
            apiSource.getCar(id)
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

    suspend fun application(applicationDTO: ApplicationDTO): Response<List<CarAnswerDTO>> {
        val res: Response<List<CarAnswerDTO>> = try {
            apiSource.application(applicationDTO)
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

    suspend fun getApp(): Response<List<CarAnswerDTO>> {
        val res: Response<List<CarAnswerDTO>> = try {
            apiSource.getApp()
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

}