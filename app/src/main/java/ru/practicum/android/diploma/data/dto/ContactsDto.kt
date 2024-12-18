package ru.practicum.android.diploma.data.dto

data class ContactsDto(
    val email: String? = null,
    val name: String? = null,
    val phones: ArrayList<PhonesDto> = arrayListOf()
)
