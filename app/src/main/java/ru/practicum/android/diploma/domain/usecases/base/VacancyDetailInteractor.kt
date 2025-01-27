package ru.practicum.android.diploma.domain.usecases.base

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDetailInteractor {
    suspend fun getVacancyById(id: String): Flow<Pair<Vacancy?, String?>>
}
