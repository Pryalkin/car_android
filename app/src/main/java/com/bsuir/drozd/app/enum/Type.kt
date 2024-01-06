package com.bsuir.drozd.app.enum

enum class Type(val type: String,
                val number: Int)

{
    LETTER("ПИСЬМО", 1),
    PACKAGE("ПОСЫЛКА", 2);

    fun getValue(): Int{
        return number
    }

    override fun toString(): String {
        return type
    }
}