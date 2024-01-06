package com.bsuir.drozd.app.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.drozd.Singletons
import com.bsuir.drozd.Singletons.apiRepository
import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.CarDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.repository.ApiRepository
import com.bsuir.drozd.app.utils.MutableLiveEvent
import com.bsuir.drozd.app.utils.MutableUnitLiveEvent
import com.bsuir.drozd.app.utils.publishEvent
import com.bsuir.drozd.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class ApiViewModel(
    private val homeRepository: ApiRepository = Singletons.apiRepository,
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()


    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    private val _cars = MutableLiveData<List<CarAnswerDTO>>()
    val cars = _cars.share()

    private val _apps = MutableLiveData<List<CarAnswerDTO>>()
    val apps = _apps.share()

    private val _car = MutableLiveData<CarAnswerDTO>()
    val car = _car.share()

    fun getRole(): String {
        return homeRepository.getRole()!!
    }

    private fun showToast(mes: String) = _message.publishEvent(mes)


    fun getUsername(): String {
        return homeRepository.getUsername()!!
    }

    fun getCategory(category: String) {
        viewModelScope.launch {
            var res: Response<List<CarAnswerDTO>> = apiRepository.getCategory(category)
            if (res.isSuccessful){
                _cars.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
            }
        }
    }

    fun getApp() {
        viewModelScope.launch {
            var res: Response<List<CarAnswerDTO>> = apiRepository.getApp()
            if (res.isSuccessful){
                _apps.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
            }
        }
    }

    fun createCar(car: CarDTO, image: MultipartBody.Part) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = homeRepository.createCar(car, image)
            if (res.isSuccessful){
                showToast("Вы успешно зарегистрировали автомобиль!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getCar(id: Long) {
        viewModelScope.launch {
            var res: Response<CarAnswerDTO> = homeRepository.getCar(id)
            if (res.isSuccessful){
                _car.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }


    fun application(applicationDTO: ApplicationDTO) {
        viewModelScope.launch {
            var res: Response<List<CarAnswerDTO>> = homeRepository.application(applicationDTO)
            if (res.isSuccessful){
                _cars.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }




}