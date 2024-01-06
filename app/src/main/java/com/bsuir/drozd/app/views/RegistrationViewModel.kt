package com.bsuir.drozd.app.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.drozd.Singletons
import com.bsuir.drozd.app.dto.UserDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.repository.AuthRepository
import com.bsuir.drozd.app.utils.MutableLiveEvent
import com.bsuir.drozd.app.utils.publishEvent
import com.bsuir.drozd.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistrationViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()

    fun registration(userDTO: UserDTO){
        viewModelScope.launch {
            var res: Response<HttpResponse> = authRepository.registration(userDTO)
            if (res.isSuccessful){
                showAuthToast("Пользователь успешно зарегистрирован!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showAuthToast(mes)
            }
        }
    }

    private fun showAuthToast(mes: String) = _message.publishEvent(mes)

}