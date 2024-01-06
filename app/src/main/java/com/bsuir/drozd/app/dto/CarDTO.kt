package com.bsuir.drozd.app.dto

data class CarDTO(
    val category: String,
    val brand: String,
    val engineCapacity: Int,
    val volumeCapacity: Double,
    val machineDrive: String,
    val color: String,
    val consumption: Double,
    val numberOfSeats: Int,
    val price: Double
    )
