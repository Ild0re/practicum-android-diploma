package ru.practicum.android.diploma.domain.models

data class VacancyList(
    val item: List<Vacancy>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val perPages: Int
)
