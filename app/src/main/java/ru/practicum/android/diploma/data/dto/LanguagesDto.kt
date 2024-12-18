package ru.practicum.android.diploma.data.dto

data class LanguagesDto(val id: String? = null,
                         val level: LevelDto?  = LevelDto(),
                         val name: String? = null) : Response()
