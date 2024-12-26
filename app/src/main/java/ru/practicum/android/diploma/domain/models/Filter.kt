package ru.practicum.android.diploma.domain.models

data class Filter(
    val country: Area?,
    val area: Area?,
    val scope: Industry?,
    val salary: String?,
    val isOnlyWithSalary: Boolean
)
