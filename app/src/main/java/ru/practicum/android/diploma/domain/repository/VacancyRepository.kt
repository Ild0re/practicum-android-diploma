package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {
    suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<VacancyList>>
    suspend fun getVacancyById(id: String): Flow<Resource<Vacancy>>
    suspend fun getFavouritesFromIds(data: Vacancy): Vacancy
}
