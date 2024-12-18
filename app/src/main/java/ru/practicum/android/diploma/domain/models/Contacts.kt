package ru.practicum.android.diploma.domain.models

data class Contacts(
    val email: String? = null,
    val name: String? = null,
    val phones: ArrayList<Phones> = arrayListOf()
)
