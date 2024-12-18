package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyList

interface SearchInteractor {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Pair<VacancyList?, String?>>
}
