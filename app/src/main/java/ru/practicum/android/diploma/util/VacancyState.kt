package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface VacancyState {
    object Loading : VacancyState
    data class Error(val message: String) : VacancyState
    data class Content(val data: Vacancy) : VacancyState
}
