package ru.practicum.android.diploma.domain.models

data class Filter(
    val country: String?,
    val area: String?,
    val scope: String?,
    val salary: String?,
    val isOnlyWithSalary: Boolean
)
