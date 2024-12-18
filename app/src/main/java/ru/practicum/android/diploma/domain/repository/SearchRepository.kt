package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<Vacancy>>
}
