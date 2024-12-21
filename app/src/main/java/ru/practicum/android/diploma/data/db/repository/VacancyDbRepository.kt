package ru.practicum.android.diploma.data.db.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacancyDbRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    suspend fun getVacancy(): Flow<Resource<List<Vacancy>>>
    suspend fun getVacancyById(vacancyId: String): Flow<Vacancy>
    suspend fun getVacancyIds(): Flow<List<String>>
    suspend fun updateVacancy(vacancy: VacancyEntity)
}
