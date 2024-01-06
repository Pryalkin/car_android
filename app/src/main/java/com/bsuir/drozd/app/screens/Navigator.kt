package com.bsuir.drozd.app.screens

import com.bsuir.drozd.app.dto.answer.CarAnswerDTO

interface Navigator {

    fun showDetailCar(car: CarAnswerDTO)

}