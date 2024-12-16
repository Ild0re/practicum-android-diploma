package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<List<VacancyDto>>>
    // заменить VacancyDto на модель Vacancy
}
