package com.bsuir.drozd.app.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.drozd.Singletons
import com.bsuir.drozd.app.dto.UserDTO
import com.bsuir.drozd.app.dto.answer.LoginUserAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.repository.AuthRepository
import com.bsuir.drozd.app.utils.MutableLiveEvent
import com.bsuir.drozd.app.utils.MutableUnitLiveEvent
import com.bsuir.drozd.app.utils.publishEvent
import com.bsuir.drozd.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun login(userDTO: UserDTO) {
        viewModelScope.launch {
            var res: Response<LoginUserAnswerDTO> = authRepository.login(userDTO)
            if (res.isSuccessful){
                showAuthToast("Вы успешно вошли в систему!")
                launchTabsScreen()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showAuthToast(mes)
            }
        }
    }

    private fun showAuthToast(mes: String) = _message.publishEvent(mes)

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent()
}