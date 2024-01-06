package com.bsuir.drozd.app.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.drozd.Singletons
import com.bsuir.drozd.app.dto.answer.LoginUserAnswerDTO
import com.bsuir.drozd.app.dto.utils.HttpResponse
import com.bsuir.drozd.app.repository.AuthRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class LogoutViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository
): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            delay(5000)
            authRepository.logout();
        }

    }

}