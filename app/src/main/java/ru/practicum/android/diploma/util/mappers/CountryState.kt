package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.domain.models.Area

sealed interface CountryState {
    data class Empty(val message: String) : CountryState
    data class Error(val message: String) : CountryState
    data class Content(val data: List<Area>?, val found: String) : CountryState
}
