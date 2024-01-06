package com.bsuir.drozd.sources.model.api

import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.CarDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.sources.backend.BackendRetrofitSource
import com.bsuir.drozd.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.Response

class RetrofitApiSource(
    config: RetrofitConfig
) : BackendRetrofitSource(config), ApiSource {

    private val apiApi = retrofit.create(ApiApi::class.java)

    override suspend fun getCategory(category: String): Response<List<CarAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        apiApi.getCategory(category)
    }

    override suspend fun createCar(car: CarDTO, image: MultipartBody.Part): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        apiApi.createCar(
            car.category,
            car.brand,
            car.engineCapacity,
            car.volumeCapacity,
            car.machineDrive,
            car.color,
            car.consumption,
            car.numberOfSeats,
            car.price,
            image)
    }

    override suspend fun getCar(id: Long): Response<CarAnswerDTO> = wrapRetrofitExceptions {
        delay(1000)
        apiApi.getCar(id)
    }

    override suspend fun application(applicationDTO: ApplicationDTO): Response<List<CarAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        apiApi.application(applicationDTO)
    }

    override suspend fun getApp(): Response<List<CarAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        apiApi.getApp()
    }


}