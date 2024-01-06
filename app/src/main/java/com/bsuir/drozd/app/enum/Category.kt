package com.bsuir.drozd.app.enum

enum class Category(val category: String) {

    COMFORT_CLASS("Комфорт класс"),
    BUSINESS_CLASS("Бизнес класс"),
    PREMIUM("Премиум"),
    SUV("Внедорожники"),
    CONVERTIBLES("Кабриолеты"),
    SPORT("Спорт"),
    MINIBUSES("Микроавтобусы");

    override fun toString(): String {
        return category
    }

}