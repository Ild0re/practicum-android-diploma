package ru.practicum.android.diploma.domain.models

data class Contacts (var email: String? = null,
                     var name: String? = null,
                     var phones : ArrayList<Phones> = arrayListOf())
