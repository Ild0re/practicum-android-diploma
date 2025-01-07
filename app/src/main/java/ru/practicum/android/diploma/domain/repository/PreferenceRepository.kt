package ru.practicum.android.diploma.domain.repository

interface PreferenceRepository {
    fun saveSalaryClose(value: Boolean)
    fun getSalaryClose(): Boolean
}
