package ru.practicum.android.diploma.data.dto

data class LanguagesDto (var id: String? = null,
                         var level: LevelDto?  = LevelDto(),
                         var name: String? = null): Response()
