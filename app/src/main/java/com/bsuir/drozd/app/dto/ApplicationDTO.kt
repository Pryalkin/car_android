package com.bsuir.drozd.app.dto

data class ApplicationDTO(

    val carId: Long,
    val category: String,
    val name: String,
    var surname: String,
    var patronymic: String,
    var passport: String,
    var mobile: Int,
    var amountOfDays: Int

)
