package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface ScreenState {
    object Loading : ScreenState
    data class Empty(val message: String) : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content(val data: List<Vacancy>, val found: String) : ScreenState
}
