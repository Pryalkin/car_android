package com.bsuir.drozd.app.dto.answer

data class CarAnswerDTO(

    val id: Long,
    val category: String,
    val brand: String,
    val engineCapacity: Integer,
    val volumeCapacity: Double,
    val machineDrive: String,
    val color: String,
    val consumption: Double,
    val numberOfSeats: Integer,
    val price: Double,
    val image: String

)
