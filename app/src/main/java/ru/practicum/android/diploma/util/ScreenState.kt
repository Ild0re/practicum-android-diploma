package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.VacancyDto

sealed interface ScreenState {
    object Loading : ScreenState
    data class Empty(val message: String) : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content(val data: List<VacancyDto>) : ScreenState
    // заменить VacancyDto на модель Vacancy
}
