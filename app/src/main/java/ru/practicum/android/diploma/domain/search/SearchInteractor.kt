package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface SearchInteractor {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Pair<VacancyResponse?, String?>>
    // заменить VacancyDto на модель Vacancy
}
