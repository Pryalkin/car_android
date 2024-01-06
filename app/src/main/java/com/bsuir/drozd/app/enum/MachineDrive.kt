package com.bsuir.drozd.app.enum

enum class MachineDrive(val wheel: String) {


    REAR_WHEEL_DRIVE("Заднеприводный"),
    FRONT_WHEEL_DRIVE("Переднеприводный"),
    ALL_WHEEL_DRIVE("Полноприводный");

    override fun toString(): String {
        return wheel
    }

}