package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustryState {
    data class Empty(val message: String) : IndustryState
    data class Error(val message: String) : IndustryState
    data class Content(val data: List<Industry>?, val found: String) : IndustryState
}
