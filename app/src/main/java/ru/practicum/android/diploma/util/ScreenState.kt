package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.VacancyItem

sealed interface ScreenState {
    object Loading : ScreenState
    data class Empty(val message: String) : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content(val data: List<VacancyItem>) : ScreenState
}
