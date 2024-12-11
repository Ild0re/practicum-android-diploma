package ru.practicum.android.diploma.data.dto

data class VacancyResponse(
    val searchType: String,
    val expression: String,
    val results: List<VacancyDto>
): Response()

