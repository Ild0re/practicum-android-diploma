package ru.practicum.android.diploma.data.dto

class AreaDto(
    val id: String,
    val name: String,
    val url: String,
    areas: List<AreaDto>?
) : Response()
