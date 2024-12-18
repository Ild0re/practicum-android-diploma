package ru.practicum.android.diploma.data.dto

data class PhonesDto(
    val city: String? = null,
    val comment: String? = null,
    val country: String? = null,
    val number: String? = null
) : Response()
