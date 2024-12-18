package ru.practicum.android.diploma.data.dto

data class ContactsDto (var email: String? = null,
                        var name: String? = null,
                        var phones : ArrayList<PhonesDto> = arrayListOf())
