package ru.practicum.android.diploma.data.dto

data class PhonesDto(
    val city: String,
    val comment: String?,
    val country: String,
    val number: String
) : Response()
