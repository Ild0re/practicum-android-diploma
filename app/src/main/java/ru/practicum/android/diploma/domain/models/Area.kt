package ru.practicum.android.diploma.domain.models

data class Area(
    val id: String,
    val name: String,
    val url: String,
    val areas: List<Area>
)
