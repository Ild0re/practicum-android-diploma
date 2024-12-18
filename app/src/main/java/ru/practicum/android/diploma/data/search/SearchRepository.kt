package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<VacancyList>>
}
